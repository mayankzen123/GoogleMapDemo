package com.example.administrator.googlemapdemo;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//    Map
//    http://tutorialsbuzz.com/2016/02/android-show-current-location-on-google-maps.html
//    https://stackoverflow.com/questions/17278015/google-maps-android-api-v2-visualizing-the-search-radius-with-the-helping-of-va
//
//    https://stackoverflow.com/questions/38453093/android-google-maps-change-circle-radius-smoothly
//    https://stackoverflow.com/questions/17277431/google-map-api-v2-resize-screen-to-match-a-circle-drawn
//
//    https://stackoverflow.com/questions/43251528/android-o-old-start-foreground-service-still-working
//
//    http://yasirameen.com/2017/09/understading-google-maps-marker/
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        MarkerOptions mMarkerOptions = new MarkerOptions();
        mMarkerOptions.position(new LatLng(-34, 151));
        mMarkerOptions.icon(BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_launcher));
        mMarkerOptions.anchor(0.5f, 0.5f);
        mMap.addMarker(mMarkerOptions);
        CircleOptions mOptions = new CircleOptions()
                .center(sydney).radius(1000)
                .strokeColor(0x110000FF).strokeWidth(1).fillColor(0x110000FF);

        final Circle circle = mMap.addCircle(mOptions);
        ValueAnimator vAnimator = new ValueAnimator();
        vAnimator.setRepeatCount(ValueAnimator.INFINITE);
        vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
        vAnimator.setIntValues(0, 100);
        vAnimator.setDuration(2000);
        vAnimator.setEvaluator(new IntEvaluator());
        vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                // Log.e("", "" + animatedFraction);
                circle.setRadius(animatedFraction * 100);
            }
        });
        vAnimator.start();
        CameraPosition camPosition = new CameraPosition.Builder()
                .target(sydney).zoom(15f).build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(camPosition));
    }

}
