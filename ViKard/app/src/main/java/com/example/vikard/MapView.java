package com.example.vikard;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MapView extends Fragment{

    private GoogleMap mMap;


    private final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);

    private Marker markerPerth;
    private Marker markerSydney;
    private Marker markerBrisbane;


    private void initMap()
    {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                       mMap.clear();
                       mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                       mMap.addMarker(markerOptions);
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_view, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
        // Add some markers to the map, and add a data object to each marker.
        markerBrisbane = googleMap.addMarker(new MarkerOptions()
            .position(PERTH)
            .title("Perth"));


        markerSydney = googleMap.addMarker(new MarkerOptions()
            .position(SYDNEY)
            .title("Sydney"));

            }
        });

        return view;
    }


}