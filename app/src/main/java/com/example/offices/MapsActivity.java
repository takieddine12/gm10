package com.example.offices;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ArrayList<LatLng> arrayList;

    private BottomSheetBehavior bottomSheetBehavior;
    private int[] images1 = new int[]{R.drawable.image1,R.drawable.ic_launcher_background};
    private int[] images2 = new int[]{R.drawable.image2,R.drawable.ic_launcher_background};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        arrayList = Markers.getMarkers();

        View bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        for (int i = 0; i < arrayList.size(); i++) {
            mMap.addMarker(
                    new MarkerOptions()
                            .position(arrayList.get(i))
                            .title(Markers.getTitles().get(i))
                            .icon(BitmapFromVector(this,R.drawable.icon)));
        //    mMap.setInfoWindowAdapter(new CustomInfoWindow());
        }

        if (!arrayList.isEmpty()) {
            LatLng lastMarkerLocation = arrayList.get(arrayList.size() - 1);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastMarkerLocation, 6.0f));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    bottomSheetBehavior.setPeekHeight(100);
                    if(marker.getPosition().latitude == 37.89858){
                        showDialog(marker);
                    } else {
                        showDialog(marker);
                    }
                    return true;
                }
            });

        }

    }



    private void showDialog(Marker marker){
            ViewPager viewPager = findViewById(R.id.vPager);
            TextView name = findViewById(R.id.name);
            TextView lat = findViewById(R.id.lat);
            TextView lng = findViewById(R.id.lng);

            name.setText(marker.getTitle());
            lat.setText("Lat : " + String.valueOf(marker.getPosition().latitude));
            lng.setText("Lng : " + String.valueOf(marker.getPosition().longitude));

            ImageSlider imageSlider;
            if(marker.getPosition().latitude == 37.89858){
                imageSlider = new ImageSlider(viewPager,images1);
                viewPager.setAdapter(imageSlider);
            } else {
                imageSlider = new ImageSlider(viewPager,images2);
                viewPager.setAdapter(imageSlider);
            }
        }
    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {

        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private class  ImageSlider extends PagerAdapter {

        private final ViewPager viewPager;
        private final int[] images;
        public ImageSlider(ViewPager viewPager,int[] images){
            this.viewPager = viewPager;
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(MapsActivity.this);
            imageView.setImageResource(images[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewPager.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            viewPager.removeView((ImageView) object);
        }
    }
   
}




//    public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
//        private final View markerView;
//
//        @SuppressLint("InflateParams")
//        public CustomInfoWindow() {
//            markerView = getLayoutInflater().inflate(R.layout.custom_window_layout, null);
//
//        }
//
//        @Nullable
//        @Override
//        public View getInfoContents(@NonNull Marker marker) {
//            return null;
//        }
//
//        @Nullable
//        @Override
//        public View getInfoWindow(@NonNull Marker marker) {
//            render(marker, markerView);
//            return markerView;
//        }
//
//        @SuppressLint("SetTextI18n")
//        private void render(Marker marker, View view) {
//            ViewPager viewPager = view.findViewById(R.id.vPager);
//            TextView name = view.findViewById(R.id.name);
//            TextView lat = view.findViewById(R.id.lat);
//            TextView lng = view.findViewById(R.id.lng);
//
//            name.setText(marker.getTitle());
//            lat.setText("Lat : " + String.valueOf(marker.getPosition().latitude));
//            lng.setText("Lng : " + String.valueOf(marker.getPosition().longitude));
//
//            ImageSlider imageSlider;
//            if(marker.getPosition().latitude == 37.89858){
//                imageSlider = new ImageSlider(viewPager,images1);
//                viewPager.setAdapter(imageSlider);
//            } else {
//                imageSlider = new ImageSlider(viewPager,images2);
//                viewPager.setAdapter(imageSlider);
//            }
//        }
//    }