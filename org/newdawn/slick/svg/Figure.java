package org.newdawn.slick.svg;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Figure {
   // $FF: synthetic field
   private int type;
   // $FF: synthetic field
   private Shape shape;
   // $FF: synthetic field
   private Transform transform;
   // $FF: synthetic field
   private NonGeometricData data;
   // $FF: synthetic field
   public static final int ELLIPSE = 1;
   // $FF: synthetic field
   public static final int LINE = 2;
   // $FF: synthetic field
   public static final int RECTANGLE = 3;
   // $FF: synthetic field
   public static final int PATH = 4;
   // $FF: synthetic field
   public static final int POLYGON = 5;

   public Transform getTransform() {
      return this.transform;
   }

   public int getType() {
      return this.type;
   }

   public Shape getShape() {
      return this.shape;
   }

   public NonGeometricData getData() {
      return this.data;
   }

   public Figure(int var1, Shape var2, NonGeometricData var3, Transform var4) {
      this.shape = var2;
      this.data = var3;
      this.type = var1;
      this.transform = var4;
   }
}
