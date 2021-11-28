package com.example.wayfinding.classes;

public class Region {
    String UUID;
    int[] startPoint;
    Region(){
        UUID = null;
        startPoint = null;
    }
    Region(String UUID, int[] startPoint){
        this.UUID = UUID;
        this.startPoint = startPoint;
    }
}
