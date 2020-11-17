package com.porpit.ppcore.util;

import net.minecraft.util.math.AxisAlignedBB;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3f;

public class MathHelper {
    public static Vector3f one3f(){
        return new Vector3f(1,1,1);
    }
    public static Vector3f zero3f(){
        return new Vector3f(0,0,0);
    }


    public static AxisAlignedBB rotateAABBByY(AxisAlignedBB aabb,Vector2d yPoint,int angle){
        Vector2d p1=calcNewPoint(new Vector2d(aabb.maxX,aabb.minZ),yPoint,angle);
        Vector2d p2=calcNewPoint(new Vector2d(aabb.minX,aabb.maxZ),yPoint,angle);
        return new AxisAlignedBB(p1.x,aabb.minY,p1.y,p2.x,aabb.maxY,p2.y);
    }

    public static AxisAlignedBB rotateAABBByY(AxisAlignedBB aabb,int angle){

        return rotateAABBByY(aabb,new Vector2d(0.5,0.5),angle);
    }
    private static Vector2d calcNewPoint(Vector2d p, Vector2d pCenter, double angle) {
        // calc arc
        double l =  ((angle * Math.PI) / 180);

        //sin/cos value
        double cosv =  Math.cos(l);
        double sinv =  Math.sin(l);

        // calc new point
        double newX =  ((p.x - pCenter.x) * cosv - (p.y - pCenter.y) * sinv + pCenter.x);
        double newY = ((p.x - pCenter.x) * sinv + (p.y - pCenter.y) * cosv + pCenter.y);
        return new Vector2d( newX,  newY);
    }
}
