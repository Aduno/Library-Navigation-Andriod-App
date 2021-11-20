package com.example.wayfinding.classes;

import java.util.Arrays;

public class Node implements Comparable<Node>{
    protected Node parent;
    protected int[] position;
    protected double F;
    protected double G;
    protected double H;

    public Node(Node parent, int[] coord) {
        this.parent = parent;
        this.position = coord;
        F = 0;
        G = 0;
        H = 0;
    }

    //getters
    public double getF() {
        return F;
    }

    public double getG() {
        return G;
    }

    public double getH() {
        return H;
    }

    public Node getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(!(obj instanceof Node))return false;

        Node other = (Node) obj;
        return position[0] == other.position[0] && position[1] == other.position[1];
    }

    public boolean equals(Node node){
        return position[0] ==node.position[0] && position[1]==node.position[1];
    }
    @Override
    public int compareTo(Node o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (this.F > o.getF()) {
            return 1;
        } else if (this.F == o.getF()) {
            return 0;
        } else {
            return -1;
        }
    }
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(position);
        return hash;
    }
}

