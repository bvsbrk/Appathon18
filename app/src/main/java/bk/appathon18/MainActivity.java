package bk.appathon18;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnCameraMoveStartedListener {


    GoogleMap googleMap;
    MapView mapView;
    Button navigateButton;
    List<Address> addresses;
    Geocoder geocoder;
    LinearLayout bottomSheetLayout;
    BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomSheetLayout = findViewById(R.id.bottom_sheet_distress);
        sheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        setUpbottomSheet();
        geocoder = new Geocoder(this);
        navigateButton = findViewById(R.id.navigateButton);
        mapView = findViewById(R.id.google_map_view);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    private void setUpbottomSheet() {
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_DRAGGING: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_SETTLING: {
                        break;
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }


        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        this.googleMap = googleMap;
        this.googleMap.setMyLocationEnabled(true);
        showMyLocationButton();
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

    private void showMyLocationButton() {
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        locationButton.setVisibility(View.GONE);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        googleMap.clear();
        navigateButton.animate().alpha(1f).translationY(-100).setDuration(300);
        bottomSheetLayout.animate().alpha(0f).setDuration(300);
        MarkerOptions currentLocationMarker = new MarkerOptions();
        currentLocationMarker.position(latLng);
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (Exception ignored) {
        }
        if (addresses.size() != 0) {
            currentLocationMarker.title(addresses.get(0).getFeatureName());
        }
        Marker marker = googleMap.addMarker(currentLocationMarker);
        marker.showInfoWindow();
    }

    public void navigateClick(View view) {
        float alpha = view.getAlpha();
        if (alpha != 0f) {
            Toast.makeText(this, "alpha", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCameraMoveStarted(int i) {
        navigateButton.animate().alpha(0f).translationY(100).setDuration(300);
        bottomSheetLayout.animate().alpha(1f).setDuration(300);
    }
}
