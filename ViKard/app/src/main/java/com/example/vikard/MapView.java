package com.example.vikard;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.vikard.data.SQLConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MapView extends Fragment {

    SQLConnection sql = new SQLConnection();
    Connection conn = sql.getConnection();


    List<LatLng> biedronkaList = null; //2
    List<LatLng> zabkaList = null; //3
    List<LatLng> douglasList = null; //5
    List<LatLng> bpList = null; //6
    List<LatLng> orlenList = null; //7
    List<LatLng> shellList = null; //8

    List<LatLng> shopsList = null; //8
    ArrayList<Integer> array = new ArrayList<>();

    private GoogleMap mMap;
    private Marker shopsMarkers;
    private Marker markersBiedronka;
    private Marker markersZabka;
    private Marker markersDouglas;
    private Marker markersBp;
    private Marker markersOrlen;
    private Marker markersShell;


    public List<LatLng> getAllPoints(int ShopsId) {
        List<LatLng> llPoints = new ArrayList<LatLng>();
        List<Integer> pointsId = getPointsId(ShopsId);
        for (Integer id : pointsId) {
            llPoints.add(getCoords(id));
        }
        return llPoints;
    }

    private List<Integer> getPointsId(int ShopsId) {
        ResultSet resultSet = null;
        String sqlQuery = "SELECT Id FROM ShopsCoords WHERE ShopsId = ?";
        PreparedStatement statement = null;
        List<Integer> pointList = new ArrayList<Integer>();
        try {
            statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, ShopsId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pointList.add(resultSet.getInt("Id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pointList;
    }

    private LatLng getCoords(int pointId) {
        ResultSet resultSet = null;
        String sqlQuery = "SELECT X,Y FROM ShopsCoords WHERE Id = ?";
        PreparedStatement statement = null;
        LatLng ll = null;
        try {
            statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, pointId);
            resultSet = statement.executeQuery();
            if (resultSet != null) {
                resultSet.next();
                double X = resultSet.getDouble("X");
                double Y = resultSet.getDouble("Y");
                ll = new LatLng(X, Y);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ll;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_view, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);


        getParentFragmentManager().setFragmentResultListener("123", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                array = result.getIntegerArrayList("123");
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i) == 2) {
                        biedronkaList = getAllPoints(2);
                    } else if (array.get(i) == 3) {
                        zabkaList = getAllPoints(3);
                    } else if (array.get(i) == 5) {
                        douglasList = getAllPoints(5);
                    } else if (array.get(i) == 6) {
                        bpList = getAllPoints(6);
                    } else if (array.get(i) == 7) {
                        orlenList = getAllPoints(7);
                    } else if (array.get(i) == 8) {
                        shellList = getAllPoints(8);
                    }


                }


            }
        });


        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                mMap = googleMap;

                    LatLng zut = new LatLng(53.44730722491076, 14.49199357001716);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(zut));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zut, 15));
                    mMap.addMarker(new MarkerOptions().position(zut).title("You are here!"));

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.


                }
                else
                {
                    mMap.setMyLocationEnabled(true);

                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                }

                // Add some markers to the map, and add a data object to each marker.

                if (biedronkaList != null) {
                    for (int i = 0; i < biedronkaList.size(); i++) {
                        markersBiedronka = googleMap.addMarker(new MarkerOptions()
                                .position(biedronkaList.get(i))
                                .title("Biedronka"));

                    }
                }
                if (zabkaList != null) {
                    for (int i = 0; i < zabkaList.size(); i++) {
                        markersZabka = googleMap.addMarker(new MarkerOptions()
                                .position(zabkaList.get(i))
                                .title("Zabka"));
                    }
                }

                if (douglasList != null) {
                    for (int i = 0; i < douglasList.size(); i++) {
                        markersDouglas = googleMap.addMarker(new MarkerOptions()
                                .position(douglasList.get(i))
                                .title("Douglas"));
                    }
                }

                if (bpList != null) {
                    for (int i = 0; i < bpList.size(); i++) {
                        markersBp = googleMap.addMarker(new MarkerOptions()
                                .position(bpList.get(i))
                                .title("BP"));
                    }
                }

                if (orlenList != null) {
                    for (int i = 0; i < orlenList.size(); i++) {
                        markersOrlen = googleMap.addMarker(new MarkerOptions()
                                .position(orlenList.get(i))
                                .title("PKN Orlen"));
                    }
                }

                if (shellList != null) {
                    for (int i = 0; i < shellList.size(); i++) {
                        markersShell = googleMap.addMarker(new MarkerOptions()
                                .position(shellList.get(i))
                                .title("Shell"));
                    }
                }


            }


        });
        return view;

    }



}

