package com.example.grape;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ShowMapDialog extends Dialog implements OnMapReadyCallback {
    private Context context;
    private MapDialogClickListener mapDialogClickListener;
    private MapView mapView = null;
    private GoogleMap mMap;
    private Button ok;
    public double[] location = new double[2];
    private double X = 37.1;
    private double Y = 127.1;

    public ShowMapDialog(@NonNull Context context, MapDialogClickListener mapDialogClickListener){
        super(context);
        this.context=context;
        this.mapDialogClickListener=mapDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_map_dialog);

        ok=findViewById(R.id.Yes);
        location = this.mapDialogClickListener.provideLocation();

        mapView=findViewById(R.id.map_dialog);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        ok.setOnClickListener(v -> {
            this.mapDialogClickListener.onPositiveClick();
            dismiss();
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //위치 좌표
        X = location[0];
        Y = location[1];

        mMap=googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(X,Y),15f));//위치로 이동


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(new LatLng(X, Y))//위치에 핀찍기
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin60602));
        mMap.addMarker(markerOptions);

    }
}
