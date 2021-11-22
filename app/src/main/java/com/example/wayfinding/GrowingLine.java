package com.example.wayfinding;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.wayfinding.classes.CheckPoint;
import com.example.wayfinding.classes.Map;
import com.example.wayfinding.classes.Node;

public class GrowingLine extends View {

    private int x1,y1,x2,y2;
    private List<PointF> listOfPoints;
    private int inte = 0;
    private Paint paint;
    Stack<Node> path;

    public GrowingLine (Context context, AttributeSet attrs) {
        super(context, attrs);

        Map map = new Map();

        Hashtable<String, CheckPoint> checkpoints = new Hashtable<>();
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

        path = map.findPath("Elevator","Coffee Shop");

        listOfPoints = new ArrayList<>();
        paint = new Paint();
        paint.setColor(Color.parseColor("#21BBA6"));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10);

    }
    public static int hash(int x,int y){
        return x*40+y;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //	starting point
        x1 = 50;
        y1 = 50;

        //	ending point
        x2 = getWidth() / 2 + getWidth() / 4;
        y2 = getHeight() / 2 + getHeight() / 4;

        Log.d("line xy xy", x1 + " : "+y1+" : "+x2 + " : "+y2);

        divideLineIntoEqualParts();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0;i<path.size()/2-1;i++){
            Node node1 = path.pop();
            Node node2 = path.pop();
            canvas.drawLine(node1.getPosition()[0],node1.getPosition()[1],
                    node2.getPosition()[0],node2.getPosition()[1],paint);
        }
    }

    //	dividing line into 50 equal parts
    private void divideLineIntoEqualParts() {

        /*

         * (x,y) = (x1 + k(x2 - x1),y1 + k(y2 - y1))
         * */

        listOfPoints.clear();
        for (int k = 1; k <= 50; k++) {
            listOfPoints.add(new PointF(x1 + ((k * (x2 - x1)) / 50),y1 + (k * (y2 - y1)) / 50));
        }

        Log.d("listOfPoints : size : ",listOfPoints.size()+"");
    }
}
