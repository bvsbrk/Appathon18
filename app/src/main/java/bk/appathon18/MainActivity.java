package bk.appathon18;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnCameraMoveStartedListener {


    GoogleMap googleMap;
    MapView mapView;
    Button navigateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigateButton = findViewById(R.id.navigateButton);
        mapView = findViewById(R.id.google_map_view);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        this.googleMap = googleMap;
        googleMap.setOnMapClickListener(this);
        googleMap.setOnCameraMoveStartedListener(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom((new LatLng(12.970537, 79.159960)), 17);
            googleMap.moveCamera(cameraPosition);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        navigateButton.animate().alpha(1f).translationY(-100).setDuration(300);
    }

    public void navigateClick(View view) {
        float alpha = view.getAlpha();
        if (alpha != 0f) {

        }
    }


    @Override
    public void onCameraMoveStarted(int i) {
        navigateButton.animate().alpha(0f).translationY(100).setDuration(300);
    }
}
