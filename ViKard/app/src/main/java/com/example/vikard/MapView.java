package com.example.vikard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MapView extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        mMap =  googleMap;
    }

    //Tymczasowo zakomentowany, próbuje dojść do tego jak prosić użytkownika o zezwolenie na pobieranie lokalizacji



//    private FusedLocationProviderClient mFusedLocationProviderClient;
//    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
//    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
//    private Boolean mLocationPermissionGranted = false;
//
//    private void moveCamera(LatLng latLng, float zoom)
//    {
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
//    }
//
//    private void getLocationPermission()
//    {
//        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
//
//        if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//        {
//            if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//            {
//                mLocationPermissionGranted = true;
//            }
//            else
//            {
//                ActivityCompat.requestPermissions(this.getActivity(), permissions,1234);
//            }
//        }
//    }
//
//    private void getDeviceLocation() {
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//
//            Task location = mFusedLocationProviderClient.getLastLocation();
//            location.addOnCompleteListener(new OnCompleteListener() {
//                @Override
//                public void onComplete(@NonNull Task task) {
//                    Location currentLocation = (Location)task.getResult();
//                    initMap();
//
//                    moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),15f);
//                }
//            });
//
//
//        }
//    }


    private void initMap()
    {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(MapView.this);
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

        return view;
    }


}