package com.example.grape;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDialog extends Dialog implements OnMapReadyCallback {
    private Context context;
    private MapDialogClickListener mapDialogClickListener;
    private MapView mapView = null;
    private GoogleMap mMap;
    private Button ok,no;
    public MapDialog(@NonNull Context context, MapDialogClickListener mapDialogClickListener){
        super(context);
        this.context=context;
        this.mapDialogClickListener=mapDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_dialog);

        ok=findViewById(R.id.Yes);
        no=findViewById(R.id.No);

        mapView=findViewById(R.id.map_dialog);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

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
