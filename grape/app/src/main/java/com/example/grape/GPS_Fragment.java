package com.example.grape;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.Manifest;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

public class GPS_Fragment extends Fragment implements OnMapReadyCallback {
    private MapView mapView = null;
    private GoogleMap mMap;
    private ImageButton myloc;
    FusedLocationProviderClient locationClient = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.gps_fragment, container, false);

        String[] REQUIRED_PERMISSIONS={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        myloc=v.findViewById(R.id.myloc);
        myloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hasFineLocationPermission= ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCoarseLocationPermission=ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
                if(hasFineLocationPermission==PackageManager.PERMISSION_GRANTED&&hasCoarseLocationPermission==PackageManager.PERMISSION_GRANTED){

                    locationClient=LocationServices.getFusedLocationProviderClient(getContext());
                    locationClient.getLastLocation().addOnSuccessListener(location -> {
                        if(location==null){
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

                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();


                    }else{//처음 권한 물을 떄
                        ActivityCompat.requestPermissions((MainActivity)getActivity(),REQUIRED_PERMISSIONS,100);
                    }
                }
            }
        });

        mapView=v.findViewById(R.id.map);
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
        mMap=googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.6512,127.0161),15f));

        //마커 생성
        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(new LatLng(37.6512, 127.0161))
                .title("마커"); // 타이틀.

        mMap.addMarker(makerOptions);
    }
}
