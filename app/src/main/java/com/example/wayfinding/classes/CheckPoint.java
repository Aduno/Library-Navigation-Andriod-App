package com.example.wayfinding.classes;

public class CheckPoint implements Comparable<CheckPoint>{
    int[] position;
    private String UUID;
    private String name;

    public CheckPoint(int[] position, String name){
        this.position = position;
    }

    public CheckPoint(String UUID, String name) {
        this.UUID = UUID;
        this.name = name;
    }
    public CheckPoint(String UUID, String name,int[] position) {
        this.UUID = UUID;
        this.name = name;
        this.position = position;
    }
    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(CheckPoint o) {
        return 0;
    }
}
