package com.example.wayfinding.classes;

public class Region {
    String UUID;
    int[] startPoint;

    Region(String UUID, int[] startPoint){
        this.UUID = UUID;
        this.startPoint = startPoint;
    }
}
