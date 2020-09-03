package com.example.colaboradores.colaborators;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.colaboradores.R;
import com.example.colaboradores.model.Colaborator;
import com.example.colaboradores.model.Data;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MapsColaborators extends AppCompatActivity implements OnMapReadyCallback {

    List<Colaborator> colaborators;
    private boolean isAll;
    private GoogleMap mMap;
    private Colaborator colaborator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_colaborators);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Gson gson = new Gson();
        isAll = getIntent().getExtras().getBoolean("isAll",false);
        if (isAll){

            Type founderListType = new TypeToken<ArrayList<Colaborator>>() {
            }.getType();
            colaborators = gson.fromJson(getIntent().getExtras().getString("data"), founderListType);
        }else{
            colaborator = gson.fromJson(getIntent().getExtras().getString("data"),Colaborator.class);
            colaborators = new ArrayList<>();
            colaborators.add(colaborator);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        try {
            addMarkers();
        }catch (Exception e){
            e.printStackTrace();
            Log.d("error","error en: "+e);
        }

    }

    private void addMarkers() {
        for (int i = 0; i < colaborators.size(); i++) {
            Double lat =Double.valueOf(colaborators.get(i).getLocation().getLat());
            Double lng = Double.valueOf(colaborators.get(i).getLocation().getLog());
            String placeName = colaborators.get(i).getName();
            //String vicinity = results.get(i).vicinity;
            MarkerOptions markerOptions = new MarkerOptions();
            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName);
            Marker m = mMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}
