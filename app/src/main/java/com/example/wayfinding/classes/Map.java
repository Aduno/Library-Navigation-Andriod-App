package com.example.wayfinding.classes;

import java.util.*;

public class Map {
    private int[][] size;
    private Hashtable<Integer,int[]> obstacles;
    private Hashtable<String,CheckPoint> checkPoints;

    public Map(){
        size = new int[40][40];
        obstacles = new Hashtable<>();
        checkPoints = new Hashtable<>();
    }

    public void setObstacles(Hashtable<Integer, int[]> obstacles) {
        this.obstacles = obstacles;
    }

    public void setCheckPoints(Hashtable<String, CheckPoint> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public Stack<Node> findPath(String UUID1, String UUID2){
        int itr = 0;
        PriorityQueue<Node> openList = new PriorityQueue<>();
        HashMap<Integer,Node> closedList = new HashMap<>();

        Node start = new Node(null,checkPoints.get(UUID1).position);
        Node end = new Node(null, checkPoints.get(UUID2).position);
        Node current;

        openList.add(start);
        while(!openList.isEmpty()&&itr<100000){
            itr++;
            current = openList.remove();
            closedList.put(current.position[0]*40+current.position[1],current);

            if(current.equals(end)){
                Stack<Node> path = new Stack<>();
                while(current!=null){
                    path.add(current);
                    current = current.parent;
                }
                return path;
            }

            for(Node nb : neighbours(current)){
                if(checkCollision(nb) || closedList.containsKey(nb.position[0]*40+current.position[1])){
                    continue;
                }

                double movementCost = nb.G;
                if(movementCost < nb.G || !openList.contains(nb)){
                    nb.G = movementCost;
                    nb.H = Math.hypot(nb.position[0]-end.position[0],nb.position[1]-end.position[1]);
                    nb.F = nb.G + nb.H;

                    if(!openList.contains(nb)){
                        openList.add(nb);
                    }
                }
            }
        }
        return null;
    }
    private LinkedList<Node> neighbours(Node n){
        int[][] translations = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        LinkedList<Node> nbs = new LinkedList<>();
        for(int[] t : translations){
            nbs.add(new Node(n,new int[]{n.position[0]+t[0],n.position[1]+t[1]}));
        }
        return nbs;
    }
    private boolean checkCollision(Node n){
        return obstacles.containsKey(n.position[0]*40+n.position[1]);
    }
}
