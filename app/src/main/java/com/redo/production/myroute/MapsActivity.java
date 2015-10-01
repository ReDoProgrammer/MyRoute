package com.redo.production.myroute;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

import static java.lang.Math.acos;
import static java.lang.Math.toRadians;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng startPoint=null,currentPoint;
    TextView txvDistance,txvSpeed,txvTimeLeft;
    double distance=0;
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


    public double getDistance(LatLng fP, LatLng lP)
    {
        double l1 = toRadians(fP.latitude);
        double l2 = toRadians(lP.latitude);
        double g1 = toRadians(fP.longitude);
        double g2 = toRadians(lP.longitude);

        double dist = acos(Math.sin(l1) * Math.sin(l2) +  Math.cos(l1) *  Math.cos(l2) *  Math.cos(g1 - g2));
        if(dist < 0) {
            dist = dist + Math.PI;
        }
        return Math.round(dist * 6378100);
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

                    LatLng prePoint=null;
                    if(currentPoint!=null)
                        prePoint=currentPoint;
                    else
                        prePoint=startPoint;
                    //updating current location
                    currentPoint =  new LatLng(arg0.getLatitude(), arg0.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(currentPoint).title("Current point"));




                    //set camera
                    currentPoint = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                    CameraPosition currentPos = new CameraPosition.Builder()
                            .target(currentPoint).zoom(17).bearing(90).tilt(30).build();
                    mMap.animateCamera(
                            CameraUpdateFactory.newCameraPosition(currentPos));



                    //update distance

                    distance+=getDistance(prePoint, currentPoint);
                    txvDistance.setText(Double.toString(distance)+"m");


                }
            });

        }

    }
}
