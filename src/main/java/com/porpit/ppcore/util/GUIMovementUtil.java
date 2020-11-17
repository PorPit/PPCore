package com.porpit.ppcore.util;

import net.minecraft.client.Minecraft;
import org.lwjgl.util.vector.Vector2f;

public class GUIMovementUtil {

    private static final float BASE_SPEED=0.3f;

    public static float moveTo(float before,float now,float taget,float speed){

        boolean dirFlag=taget>=before;

        if(dirFlag){
            if(now<before){
                now=before;
            }
            if(now>taget){
                now=taget;
            }
        }else {
            if(now>before){
                now=before;
            }
            if(now<taget){
                now=taget;
            }
        }


        float speedFloat = 100F / (float) Minecraft.getDebugFPS();
        float totalLenth = Math.abs(taget-before);
        float nowLenth = Math.abs(now-before);
        float remainLenth = Math.abs(totalLenth-nowLenth);

        if(nowLenth/totalLenth<0.1){
            speed=BASE_SPEED+(speed-BASE_SPEED)*nowLenth/totalLenth*10;
        }
        if(remainLenth/totalLenth<0.1){
            speed=BASE_SPEED+(speed-BASE_SPEED)*(remainLenth/totalLenth*10);
        }
        speed=speed*speedFloat;

        if (totalLenth-nowLenth < speed) {
            now=taget;
        } else {
            now=now+ (dirFlag?1:-1)* speed;
        }
        return now;


    }

    public static void moveTo(Vector2f before, Vector2f now, Vector2f taget, float speed) {
        if(now.equals(taget)){return ;}

        float speedFloat = 100F / (float) Minecraft.getDebugFPS();

        float totalLenth = getDistance(before, taget);
        float nowLenth = getDistance(before, now);
        float remainLenth = totalLenth-nowLenth;

        float totalLenthX = Math.abs(before.x - taget.x);
        float totalLenthY = Math.abs(before.y - taget.y);

        if(nowLenth/totalLenth<0.1){
            speed=BASE_SPEED+(speed-BASE_SPEED)*nowLenth/totalLenth*10;
        }
        if(remainLenth/totalLenth<0.1){
            speed=BASE_SPEED+(speed-BASE_SPEED)*(remainLenth/totalLenth*10);
        }
        speed=speed*speedFloat;

        if (totalLenth-nowLenth < speed) {
            now.set(taget);
        } else {
            float nextLenth=  nowLenth+speed;

            float nextLenthX=totalLenthX*nextLenth/totalLenth;
            float nextLenthY=totalLenthY*nextLenth/totalLenth;

            float nextX=before.x+nextLenthX*(taget.x>before.x?1f:-1f);
            float nextY=before.y+nextLenthY*(taget.y>before.y?1f:-1f);

            now.set(nextX,nextY);

        }

    }

    public static float getDistance(Vector2f p1, Vector2f p2) {
        float _x = Math.abs(p1.x - p2.x);
        float _y = Math.abs(p1.y - p2.y);
        return (float) Math.sqrt(_x * _x + _y * _y);
    }
}
