package com.assignment2.vaccinator.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment2.vaccinator.ui.appointment.AppointmentFragment;
import com.assignment2.vaccinator.R;
import com.assignment2.vaccinator.services.GetNearbyPlacesData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.Task;

// This class is a Google Maps Fragment Class to Load and use Maps with Bottom Navigation
// It implements multiple interfaces for using Google Maps and Google Places Api
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private GoogleApiClient client;
    protected LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public static final int REQUEST_LOCATION_CODE = 44;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    TextView hospitalName,hospitalAddress;
    LinearLayout bottomSheet;
    Button book;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for maps fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        hospitalName = v.findViewById(R.id.hospitalName);
        hospitalAddress = v.findViewById(R.id.hospitalAddress);
        bottomSheet = v.findViewById(R.id.bottom_sheet);
        book = v. findViewById(R.id.book_btn);

        // click listener for the book appointment on bottom sheet
        book.setOnClickListener(view -> {
            Bundle arguments = new Bundle();
            if(hospitalName.getText().toString().length()>0) {
                // Bundle arguments are used for passing Data between Fragments
                arguments.putString("hospital", hospitalName.getText().toString());
                AppointmentFragment appointmentFragment = new AppointmentFragment();
                appointmentFragment.setArguments(arguments);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.map_frag_container, appointmentFragment);
                // Adding current fragment to back Stack
                transaction.addToBackStack(null);
                // commit is used to execute the fragment transaction
                transaction.commit();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCurrentLocation(); // This function is used for getting current User Location
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.hospital_map);
        // setting call back function when map is ready for Use
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Setting the loaded Maps to the MapView(widget)
        map = googleMap;
        // Setting Focus to the current user location
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude,longitude),10
        ));
        // Setting the click listener for the markers on the Map
        map.setOnMarkerClickListener(this::onMarkerClick);
        // Click listener for the map (hide the bottom sheet when clicked anywhere on map)
        map.setOnMapClickListener(latLng -> {
            bottomSheet.setVisibility(View.GONE);
        });
        // if user has granted the location permission then connection to google places API
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            map.setMyLocationEnabled(true);
        }else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

    }
    // Function to get The longitude and latitude for current user location
    private void getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        // If location permission is allowed get the location else ask for user for permission
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // getting user last location
            // we have used the last location for better performance and saving battery
            // as getting current accurate location would require more resources
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(location -> {
                if(location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            });
        } else
        {
            // Requesting Location permission
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }

    // This method is evoked when the Google Places Api gets Connection(Line 179)
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        // Location Request object and setting its options
        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            // Setting the listener for getting the updated user location when changed
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this::onLocationChanged);
        }
        Object dataTransfer[] = new Object[2];
        // Object to get nearby places
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        String hospital = "hospital";
        // getting google places api URL for finding nearby Hospitals
        String url = getUrl(latitude, longitude, hospital);
        dataTransfer[0] = map;
        dataTransfer[1] = url;
        // Passing the Maps and URL to parse and get hospital Markers on the Map
        getNearbyPlacesData.execute(dataTransfer);
        Toast.makeText(getContext(), "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show();
    }

    // Method to connect the Google Places Api
    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();
    }

    // This method is called when the user takes an action on the requested Location Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            getCurrentLocation();
                            buildGoogleApiClient();
                        }
                        map.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Permission Denied" , Toast.LENGTH_LONG).show();
                }
        }
    }

    // This method is called when user clicks an maker on the maps
    // Bottom sheet will be displayed with the selected location details
    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("MarkerClicked",marker.getTitle());
        String[] info = marker.getTitle().split(":");
        hospitalName.setText(info[0]);
        hospitalAddress.setText(info[1]);
        bottomSheet.setVisibility(View.VISIBLE);
        return false;
    }

    // This method will be called when the user location changes
    @Override
    public void onLocationChanged(Location location) {

    }

    // Method to build the Places Api URl string
    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+getString(R.string.google_maps_key));

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

}