package com.example.wayfinding.classes;

import java.util.Iterator;

public class PriorityQueue<E> extends java.util.PriorityQueue<Node>{
    public PriorityQueue(){
        super();
    }
    public boolean contains(Node n){
        Iterator<Node> itr = this.iterator();
        while(itr.hasNext()){
            Node node = itr.next();
            if(node.position[0]==n.position[0]&&node.position[1]==n.position[1]){
                return true;
            }
        }
        return false;
    }
}
