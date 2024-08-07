package org.newdawn.slick.svg;

import java.util.Properties;
import org.newdawn.slick.Color;

public class NonGeometricData {
   // $FF: synthetic field
   public static final String STROKE_MITERLIMIT = "stroke-miterlimit";
   // $FF: synthetic field
   public static final String NONE = "none";
   // $FF: synthetic field
   private String metaData = "";
   // $FF: synthetic field
   public static final String STROKE_DASHARRAY = "stroke-dasharray";
   // $FF: synthetic field
   public static final String ID = "id";
   // $FF: synthetic field
   public static final String STROKE_DASHOFFSET = "stroke-dashoffset";
   // $FF: synthetic field
   public static final String STROKE_OPACITY = "stroke-opacity";
   // $FF: synthetic field
   public static final String FILL = "fill";
   // $FF: synthetic field
   public static final String STROKE_WIDTH = "stroke-width";
   // $FF: synthetic field
   public static final String STROKE = "stroke";
   // $FF: synthetic field
   private Properties props = new Properties();
   // $FF: synthetic field
   public static final String OPACITY = "opacity";

   public boolean isFilled() {
      return this.isColor("fill");
   }

   public boolean isColor(String var1) {
      return this.getAttribute(var1).startsWith("#");
   }

   public Color getAsColor(String var1) {
      if (!this.isColor(var1)) {
         throw new RuntimeException(String.valueOf((new StringBuilder()).append("Attribute ").append(var1).append(" is not specified as a color:").append(this.getAttribute(var1))));
      } else {
         int var2 = Integer.parseInt(this.getAttribute(var1).substring(1), 16);
         return new Color(var2);
      }
   }

   public float getAsFloat(String var1) {
      String var2 = this.getAttribute(var1);
      if (var2 == null) {
         return 0.0F;
      } else {
         try {
            return Float.parseFloat(var2);
         } catch (NumberFormatException var4) {
            throw new RuntimeException(String.valueOf((new StringBuilder()).append("Attribute ").append(var1).append(" is not specified as a float:").append(this.getAttribute(var1))));
         }
      }
   }

   public String getMetaData() {
      return this.metaData;
   }

   public boolean isStroked() {
      return this.isColor("stroke") && this.getAsFloat("stroke-width") > 0.0F;
   }

   public String getAsReference(String var1) {
      String var2 = this.getAttribute(var1);
      if (var2.length() < 7) {
         return "";
      } else {
         var2 = var2.substring(5, var2.length() - 1);
         return var2;
      }
   }

   public void addAttribute(String var1, String var2) {
      if (var2 == null) {
         var2 = "";
      }

      if (var1.equals("fill")) {
         var2 = this.morphColor(var2);
      }

      if (var1.equals("stroke-opacity") && var2.equals("0")) {
         this.props.setProperty("stroke", "none");
      }

      if (var1.equals("stroke-width")) {
         if (var2.equals("")) {
            var2 = "1";
         }

         if (var2.endsWith("px")) {
            var2 = var2.substring(0, var2.length() - 2);
         }
      }

      if (var1.equals("stroke")) {
         if ("none".equals(this.props.getProperty("stroke"))) {
            return;
         }

         if ("".equals(this.props.getProperty("stroke"))) {
            return;
         }

         var2 = this.morphColor(var2);
      }

      this.props.setProperty(var1, var2);
   }

   public NonGeometricData(String var1) {
      this.metaData = var1;
      this.addAttribute("stroke-width", "1");
   }

   private String morphColor(String var1) {
      if (var1.equals("")) {
         return "#000000";
      } else if (var1.equals("white")) {
         return "#ffffff";
      } else {
         return var1.equals("black") ? "#000000" : var1;
      }
   }

   public String getAttribute(String var1) {
      return this.props.getProperty(var1);
   }
}
