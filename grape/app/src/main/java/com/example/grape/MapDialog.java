package com.example.grape;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDialog extends Dialog implements OnMapReadyCallback {
    private Context context;
    private MapDialogClickListener mapDialogClickListener;
    private MapView mapView = null;
    private GoogleMap mMap;
    private Button ok,no;
    private Activity mainActiity;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fab;
    private FusedLocationProviderClient locationClient = null;

    public MapDialog(@NonNull Context context, Activity mainActivity, MapDialogClickListener mapDialogClickListener){
        super(context);
        this.context=context;
        this.mapDialogClickListener=mapDialogClickListener;
        this.mainActiity=mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_dialog);

        String[] REQUIRED_PERMISSIONS={android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        ok=findViewById(R.id.Yes);
        no=findViewById(R.id.No);
        fab=findViewById(R.id.map_fab);

        mapView=findViewById(R.id.map_dialog);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        fab.setOnClickListener(v -> {
            int hasFineLocationPermission= ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission=ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            if(hasFineLocationPermission== PackageManager.PERMISSION_GRANTED&&hasCoarseLocationPermission==PackageManager.PERMISSION_GRANTED){

                locationClient = LocationServices.getFusedLocationProviderClient(getContext());
                locationClient.getLastLocation().addOnSuccessListener(location -> {
                    if(location==null){
                        Toast.makeText(getContext(),"위치 확인 실패\n위치 기능 사용을 확인해주세요",Toast.LENGTH_SHORT).show();
                    }else {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),15f));
                    }
                });

            }else {//권한이 허용되지 않았을 경우
                if(ActivityCompat.shouldShowRequestPermissionRationale(mainActiity,Manifest.permission.ACCESS_FINE_LOCATION)){
                    //거부된 적 있을 떄
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("위치권한이 필요한 기능입니다.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(mainActiity,REQUIRED_PERMISSIONS,100);
                        }
                    });

                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();


                }else{//처음 권한 물을 떄
                    ActivityCompat.requestPermissions(mainActiity,REQUIRED_PERMISSIONS,100);
                }
            }
        });

        ok.setOnClickListener(v -> {
            this.mapDialogClickListener.onPositiveClick();
            dismiss();
        });
        no.setOnClickListener(v -> {
            this.mapDialogClickListener.onNegativeClick();
            dismiss();
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        double[] x = new double[2];

        mMap=googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.6512,127.0161),15f));

        mMap.setOnMapClickListener(point ->{
            mMap.clear();
            MarkerOptions markerOptions=new MarkerOptions();
            markerOptions
                    .position(point)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin60602));
            mMap.addMarker(markerOptions);
            x[0] =point.latitude;//x좌표
            x[1] =point.longitude;//y좌표 다이얼로그 클릭 리스너로 전달? 그냥 전달?
        });


    }
}
