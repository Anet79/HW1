package com.example.hw01.Fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.example.hw01.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class FragmentMap extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat = -37.67073140377376;
    private double lng = 144.84332141711963;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map , container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return  view;
    }

    /**
     * An activity that displays a Google map with a marker (pin) to indicate a particular location.
     */
//        private TopTen topTen;
//        public FragmentMap(TopTen topTen){
//            this.topTen=topTen;
//
//        }


//      @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            // Retrieve the content view that renders the map.
//            setContentView(R.layout.fragment_map);
//
//            // Get the SupportMapFragment and request notification when the map is ready to be used.
//            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.map);
//            mapFragment.getMapAsync(this);
//        }
        /**
         * Manipulates the map when it's available.
         * The API invokes this callback when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user receives a prompt to install
         * Play services inside the SupportMapFragment. The API invokes this method after the user has
         * installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;
            LatLng melbourne = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions()
                    .position(melbourne)
                    .title("The player played her"));
//            LatLng laLang = new LatLng(10, 30);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne, 14.0f));

            // Add a marker in Sydney, Australia,
            // and move the map's camera to the same location.
//            if(topTen!=null){
//                ArrayList<Record>records =topTen.getRecords();
//                for(int i=0;i<records.size();i++){
//                    Record record = records.get(i);
//                    LatLng latLng = new LatLng(record.getPosition().getLatitude(), record.getPosition().getLongitude());
//                    googleMap.addMarker(new MarkerOptions()
//                            .position(latLng));
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                }
//            }

        }

    public void getLocation(double longitude, double latitude) {
            lat = latitude;
            lng = longitude;
            onMapReady(mMap);

    }
}
