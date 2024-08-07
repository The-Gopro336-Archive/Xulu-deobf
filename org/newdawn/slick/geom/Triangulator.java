package org.newdawn.slick.geom;

import java.io.Serializable;

public interface Triangulator extends Serializable {
   float[] getTrianglePoint(int var1, int var2);

   int getTriangleCount();

   void addPolyPoint(float var1, float var2);

   void startHole();

   boolean triangulate();
}
