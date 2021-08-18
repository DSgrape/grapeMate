package com.example.grape;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.Manifest;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class GPS_Fragment extends Fragment implements OnMapReadyCallback {
    private MapView mapView = null;
    private GoogleMap mMap;
    private com.google.android.material.floatingactionbutton.FloatingActionButton myloc;
    private FusedLocationProviderClient locationClient = null;
    private ImageButton favorite;
    private double X,Y;
    private String flag="";
    private String flag2="";

    private long backKeyPressedTime = 0;

    private String postId, category, title;
    private double[] location = new double[2];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.gps_fragment, container, false);

        String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        favorite=v.findViewById(R.id.favorite);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.6512,127.0161),15f));
            }
        });

        myloc=v.findViewById(R.id.myloc);
        myloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hasFineLocationPermission= ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCoarseLocationPermission=ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
                if(hasFineLocationPermission==PackageManager.PERMISSION_GRANTED&&hasCoarseLocationPermission==PackageManager.PERMISSION_GRANTED){

                    locationClient=LocationServices.getFusedLocationProviderClient(getContext());
                    locationClient.getLastLocation().addOnSuccessListener(location -> {
                        if(location == null){
                            Toast.makeText(getContext(),"위치 확인 실패\n위치 기능 사용을 확인해주세요",Toast.LENGTH_SHORT).show();
                        }else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),15f));
                        }
                    });

                }else {//권한이 허용되지 않았을 경우
                    if(ActivityCompat.shouldShowRequestPermissionRationale((MainActivity)getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){
                        //거부된 적 있을 떄
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("위치권한이 필요한 기능입니다.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((MainActivity)getActivity(),REQUIRED_PERMISSIONS,100);
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                    }else{//처음 권한 물을 때
                        ActivityCompat.requestPermissions((MainActivity)getActivity(),REQUIRED_PERMISSIONS,100);
                    }
                }
            }
        });

        mapView = v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // 덕성여대 학생일 경우
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.6512,127.0161),15f));

        // db 통신 후 카테고리, 제목, 좌표, postId 받아오기
        FirebaseDatabase.getInstance().getReference("grapeMate/post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    // post 하나씩 다 돌기
                    postId = String.valueOf(dataSnapshot.child("postId").getValue());
                    title = String.valueOf(dataSnapshot.child("title").getValue());
                    category = String.valueOf(dataSnapshot.child("postType").getValue());
                    location[0] = (double) dataSnapshot.child("mapX").getValue();
                    location[1] = (double) dataSnapshot.child("mapY").getValue();

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(location[0], location[1]))
                            .title("\u003c" + category + "\u003e" + title)
                            .snippet(postId)   //아이디를 받을 장소가 안보여서 그냥 여기 포스트아이디 넣고 받을까?
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin60602)));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("체크",marker.getTitle());
                Toast t=Toast.makeText(getContext(),"한번 더 클릭하면\n해당 포스트로 이동합니다.",Toast.LENGTH_SHORT);

                if(flag.equals(marker.getSnippet())){
                    t.cancel();
                    String postId = marker.getSnippet();
                    MainActivity main = (MainActivity) getContext();
                    FirebaseDatabase.getInstance().getReference("grapeMate/post").child(postId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String Uid = snapshot.child("id").getValue().toString();
                            main.ShowPost(postId, Uid);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    flag=marker.getSnippet();
                    t.show();

                }

/*
                if(System.currentTimeMillis()>backKeyPressedTime+500){
                    backKeyPressedTime = System.currentTimeMillis();

                }else {
                    Log.d("체크","포스트로");
                    String postId = marker.getSnippet();
                    MainActivity main = (MainActivity) getContext();
                    FirebaseDatabase.getInstance().getReference("grapeMate/post").child(postId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String Uid = snapshot.child("id").getValue().toString();
                            main.ShowPost(postId, Uid);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

 */
                return false;
            }
        });

    }
}
