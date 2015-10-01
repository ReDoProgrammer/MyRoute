package com.redo.production.myroute;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng startPoint=null,currentPoint;
    TextView txvDistance,txvSpeed,txvTimeLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        txvDistance=(TextView) findViewById(R.id.txvDistance);
        txvSpeed=(TextView) findViewById(R.id.txvSpeed);
        txvTimeLeft =(TextView) findViewById(R.id.txvTimeLeft);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    public  void onReset(View v)
    {
        currentPoint=null;
        txvSpeed.setText("0asf");
        txvTimeLeft.setText("aba");
        txvDistance.setText("hoho");
    }



    private void setUpMapIfNeeded() {


        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setMyLocationEnabled(true);
        }


        if (mMap != null) {
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location arg0) {
                    //check startPoint is set or not?
                    if(startPoint==null)
                    {
                        startPoint = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(startPoint).title("Started point"));
                    }


                    //updating current location
                    currentPoint =  new LatLng(arg0.getLatitude(), arg0.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(currentPoint).title("Current point"));


                    //set camera
                    currentPoint = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                    CameraPosition currentPos = new CameraPosition.Builder()
                            .target(currentPoint).zoom(17).bearing(90).tilt(30).build();
                    mMap.animateCamera(
                            CameraUpdateFactory.newCameraPosition(currentPos));

                }
            });

        }

    }
}
