package com.example.offices;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Markers {

    static ArrayList<LatLng> getMarkers() {
        ArrayList<LatLng> markerDetailsArrayList = new ArrayList<>();
        markerDetailsArrayList.add(new LatLng(37.89858, -121.62478));
        markerDetailsArrayList.add(new LatLng(37.10888, -121.01587));
        return markerDetailsArrayList;
    }

    static ArrayList<String> getTitles() {
        ArrayList<String> markerDetailsArrayList = new ArrayList<>();
        markerDetailsArrayList.add("Pepsi");
        markerDetailsArrayList.add("Coke Cola");
        return markerDetailsArrayList;
    }
}

class MarkerDetails {
    Double lat;
    Double lng;


    public MarkerDetails(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
