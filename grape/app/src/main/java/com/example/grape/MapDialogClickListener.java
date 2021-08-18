package com.example.grape;

public interface MapDialogClickListener {
    void onPositiveClick();
    void onNegativeClick();
    void sendLocation(double x, double y);
    double[] provideLocation();
}
