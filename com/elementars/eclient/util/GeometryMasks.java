package com.elementars.eclient.util;

import java.util.HashMap;
import net.minecraft.util.EnumFacing;

public final class GeometryMasks {
   // $FF: synthetic field
   public static final HashMap FACEMAP = new HashMap();

   static {
      FACEMAP.put(EnumFacing.DOWN, 1);
      FACEMAP.put(EnumFacing.WEST, 16);
      FACEMAP.put(EnumFacing.NORTH, 4);
      FACEMAP.put(EnumFacing.SOUTH, 8);
      FACEMAP.put(EnumFacing.EAST, 32);
      FACEMAP.put(EnumFacing.UP, 2);
   }

   public static final class Quad {
      // $FF: synthetic field
      public static final int SOUTH = 8;
      // $FF: synthetic field
      public static final int EAST = 32;
      // $FF: synthetic field
      public static final int DOWN = 1;
      // $FF: synthetic field
      public static final int WEST = 16;
      // $FF: synthetic field
      public static final int NORTH = 4;
      // $FF: synthetic field
      public static final int ALL = 63;
      // $FF: synthetic field
      public static final int UP = 2;
   }

   public static final class Line {
      // $FF: synthetic field
      public static final int DOWN_NORTH = 5;
      // $FF: synthetic field
      public static final int DOWN_EAST = 33;
      // $FF: synthetic field
      public static final int DOWN_SOUTH = 9;
      // $FF: synthetic field
      public static final int SOUTH_WEST = 24;
      // $FF: synthetic field
      public static final int UP_NORTH = 6;
      // $FF: synthetic field
      public static final int UP_SOUTH = 10;
      // $FF: synthetic field
      public static final int DOWN_WEST = 17;
      // $FF: synthetic field
      public static final int UP_WEST = 18;
      // $FF: synthetic field
      public static final int NORTH_EAST = 36;
      // $FF: synthetic field
      public static final int SOUTH_EAST = 40;
      // $FF: synthetic field
      public static final int UP_EAST = 34;
      // $FF: synthetic field
      public static final int ALL = 63;
      // $FF: synthetic field
      public static final int NORTH_WEST = 20;
   }
}
