package com.example.wayfinding.classes;

import java.util.Hashtable;
import java.util.Stack;

public class Algorithm {
    public static void main(String[] args){
        Map map = new Map();

        Hashtable<String,CheckPoint> checkpoints = new Hashtable<>();
        checkpoints.put("Elevator",new CheckPoint("1","Elevator",new int[]{1,0}));
        checkpoints.put("Coffee Shop",new CheckPoint("2","Coffee Shop",new int[]{1,3}));
        checkpoints.put("Service Desk",new CheckPoint("3","Service Desk",new int[]{10,21}));

        map.setCheckPoints(checkpoints);

        Hashtable<Integer,int[]> obstacles = new Hashtable<>();

        obstacles.put(hash(0,0),new int[]{0,0});
        obstacles.put(hash(0,1),new int[]{0,1});
        obstacles.put(hash(1,1),new int[]{1,1});
        obstacles.put(hash(2,1),new int[]{2,1});
        obstacles.put(hash(3,1),new int[]{3,1});
        map.setObstacles(obstacles);

        Stack<Node> path = map.findPath("Elevator","Coffee Shop");
        for(Node n : path){
            System.out.println(n.position[0]+","+n.position[1]);
        }
    }
    public static int hash(int x,int y){
        return x*40+y;
    }
}

