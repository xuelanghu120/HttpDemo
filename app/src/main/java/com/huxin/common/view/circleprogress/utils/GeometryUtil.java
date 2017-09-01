package com.huxin.common.view.circleprogress.utils;

import android.graphics.PointF;

public class GeometryUtil {
    public static PointF[] getIntersectionPoints(PointF pMiddle, float radius, Double lineK,boolean isStart) {
        PointF[] points = new PointF[2];

        float radian, xOffset = 0, yOffset = 0;
        if (lineK != null) {
            radian = (float) Math.atan(lineK);
            xOffset = (float) (Math.sin(radian) * radius);
            yOffset = (float) (Math.cos(radian) * radius);
        } else {
            xOffset = radius;
            yOffset = 0;
        }
        if (!isStart){
            points[0] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset);
            points[1] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset);
        }else {
            points[0] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset+24);
            points[1] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset+10);
        }


        return points;
    }
}