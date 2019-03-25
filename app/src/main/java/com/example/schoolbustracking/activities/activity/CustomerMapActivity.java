package com.example.schoolbustracking.activities.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Model.ParentModel;
import com.example.schoolbustracking.activities.Utils.Common;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class CustomerMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    Toolbar mToolbar;
    private Button CallBusDriverButton;
    private DatabaseReference DriverLocationRef, StudentRef, DriverRef, DriverWorkingRef;
    private String studentId;
    private LatLng StudentPickUpLocation;
    Marker mCurrent, DriverMarker;

    boolean isDriverFound = false;
    String busno;
    int radius = 1; //1 km
    int distance = 1; //3km
    private static final int LIMIT = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mToolbar = findViewById(R.id.customerToolbar);
        mToolbar.setTitle("Customer");
//
        DriverLocationRef = FirebaseDatabase.getInstance().getReference("Driver Available");
        DriverWorkingRef = FirebaseDatabase.getInstance().getReference().child("Drivers Working");
        CallBusDriverButton = findViewById(R.id.callBtn);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        CallBusDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllAvailableBus();
            }
        });

//        GetDriver();
    }

    private void displayCustomerLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            final double latitude = mLastLocation.getLatitude();
            final double longitude = mLastLocation.getLongitude();


            //Add Marker
            if (mCurrent != null)
                mCurrent.remove(); //Remove already marker
            mCurrent = mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.child))
                    .position(new LatLng(latitude, longitude))
                    .title("Pickup Student From Here"));

            //Move carera to this position
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));

            //Drawer animation rotate marker
//                    rotateMarker(mCurrent, -360, mMap);
            loadAllAvailableBus();

        }
    }

    private void loadAllAvailableBus() {
        //Load all available Bus in distance 3 km
        GeoFire geoFire = new GeoFire(DriverLocationRef);
        LatLng customerLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(customerLocation.latitude, customerLocation.longitude), radius);
        geoQuery.removeAllListeners();
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, final GeoLocation location) {
//                if (!isDriverFound) {
//                    isDriverFound = true;
//                    busno = key;
//
//                    DriverRef = FirebaseDatabase.getInstance().getReference().child("Driver Available").child(busno);
//                    HashMap driverMap = new HashMap();
//                    driverMap.put("StudentId", Common.user_name);
//                    DriverRef.updateChildren(driverMap);
//
//                    GettingDriverLocation();
//                    CallBusDriverButton.setText("Looking for Driver Location...");
//                }

                FirebaseDatabase.getInstance().getReference()
                        .child(key)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ParentModel parentModel = dataSnapshot.getValue(ParentModel.class);

                                //Add driver to map
                                mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(location.latitude,location.longitude))
                                .flat(true)
                                .title("Bus is here"));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }

            @Override
            public void onKeyExited(String key) {
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                if (!isDriverFound) {
                    radius = radius + 1;
                    loadAllAvailableBus();
                }
            }

            @Override
            public void onGeoQueryReady() {
                    if (distance <=LIMIT) //distance
                    {
                        distance++;
                        loadAllAvailableBus();
                    }
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void GettingDriverLocation() {
        DriverWorkingRef.child(busno).child("l")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            List<Object> driverLocationMap = (List<Object>) dataSnapshot.getValue();
                            double LocationLat = 0;
                            double LocationLng = 0;
                            CallBusDriverButton.setText("Driver Found");

                            if (driverLocationMap.get(0) != null) {
                                LocationLat = Double.parseDouble(driverLocationMap.get(0).toString());

                            }
                            if (driverLocationMap.get(1) != null) {
                                LocationLng = Double.parseDouble(driverLocationMap.get(1).toString());

                            }

                            LatLng DriverLatLng = new LatLng(LocationLat, LocationLng);
                            if (DriverMarker != null) {
                                DriverMarker.remove();
                            }

                            Location location1 = new Location("");
                            location1.setLatitude(StudentPickUpLocation.latitude);
                            location1.setLongitude(StudentPickUpLocation.longitude);

                            Location location = new Location("");
                            location.setLatitude(DriverLatLng.latitude);
                            location.setLongitude(DriverLatLng.longitude);

                            float Distance = location1.distanceTo(location);
                            CallBusDriverButton.setText("Driver Found: " + String.valueOf(Distance));

                            DriverMarker = mMap.addMarker(new MarkerOptions().position(DriverLatLng).title("Bus is here"));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private void GetDriver() {
        DatabaseReference drivers = FirebaseDatabase.getInstance().getReference("Driver Available");
        GeoFire geoFire = new GeoFire(DriverLocationRef);
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude()), radius);
        geoQuery.removeAllListeners();
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                //If found
                if (!isDriverFound) {
                    isDriverFound = true;
                    busno = key;
                    CallBusDriverButton.setText("Call Driver");
                    Toast.makeText(CustomerMapActivity.this, "" + key, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                if (!isDriverFound) {
                    radius++;
                    GetDriver();
                }
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        displayCustomerLocation();
        loadAllAvailableBus();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        StudentPickUpLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(StudentPickUpLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
}
