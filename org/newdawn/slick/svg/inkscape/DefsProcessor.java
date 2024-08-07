package org.newdawn.slick.svg.inkscape;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.svg.Diagram;
import org.newdawn.slick.svg.Gradient;
import org.newdawn.slick.svg.Loader;
import org.newdawn.slick.svg.ParsingException;
import org.newdawn.slick.util.Log;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DefsProcessor implements ElementProcessor {
   public void process(Loader var1, Element var2, Diagram var3, Transform var4) throws ParsingException {
      NodeList var5 = var2.getElementsByTagName("pattern");

      NodeList var8;
      Element var9;
      String var10;
      String var11;
      for(int var6 = 0; var6 < var5.getLength(); ++var6) {
         Element var7 = (Element)var5.item(var6);
         var8 = var7.getElementsByTagName("image");
         if (var8.getLength() == 0) {
            Log.warn("Pattern 1981 does not specify an image. Only image patterns are supported.");
         } else {
            var9 = (Element)var8.item(0);
            var10 = var7.getAttribute("id");
            var11 = var9.getAttributeNS("http://www.w3.org/1999/xlink", "href");
            var3.addPatternDef(var10, var11);
         }
      }

      NodeList var23 = var2.getElementsByTagName("linearGradient");
      ArrayList var24 = new ArrayList();

      String var18;
      for(int var25 = 0; var25 < var23.getLength(); ++var25) {
         var9 = (Element)var23.item(var25);
         var10 = var9.getAttribute("id");
         Gradient var28 = new Gradient(var10, false);
         var28.setTransform(Util.getTransform(var9, "gradientTransform"));
         if (this.stringLength(var9.getAttribute("x1")) > 0) {
            var28.setX1(Float.parseFloat(var9.getAttribute("x1")));
         }

         if (this.stringLength(var9.getAttribute("x2")) > 0) {
            var28.setX2(Float.parseFloat(var9.getAttribute("x2")));
         }

         if (this.stringLength(var9.getAttribute("y1")) > 0) {
            var28.setY1(Float.parseFloat(var9.getAttribute("y1")));
         }

         if (this.stringLength(var9.getAttribute("y2")) > 0) {
            var28.setY2(Float.parseFloat(var9.getAttribute("y2")));
         }

         String var12 = var9.getAttributeNS("http://www.w3.org/1999/xlink", "href");
         if (this.stringLength(var12) > 0) {
            var28.reference(var12.substring(1));
            var24.add(var28);
         } else {
            NodeList var13 = var9.getElementsByTagName("stop");

            for(int var14 = 0; var14 < var13.getLength(); ++var14) {
               Element var15 = (Element)var13.item(var14);
               float var16 = Float.parseFloat(var15.getAttribute("offset"));
               String var17 = Util.extractStyle(var15.getAttribute("style"), "stop-color");
               var18 = Util.extractStyle(var15.getAttribute("style"), "stop-opacity");
               int var19 = Integer.parseInt(var17.substring(1), 16);
               Color var20 = new Color(var19);
               var20.a = Float.parseFloat(var18);
               var28.addStep(var16, var20);
            }

            var28.getImage();
         }

         var3.addGradient(var10, var28);
      }

      var8 = var2.getElementsByTagName("radialGradient");

      int var26;
      for(var26 = 0; var26 < var8.getLength(); ++var26) {
         Element var27 = (Element)var8.item(var26);
         var11 = var27.getAttribute("id");
         Gradient var29 = new Gradient(var11, true);
         var29.setTransform(Util.getTransform(var27, "gradientTransform"));
         if (this.stringLength(var27.getAttribute("cx")) > 0) {
            var29.setX1(Float.parseFloat(var27.getAttribute("cx")));
         }

         if (this.stringLength(var27.getAttribute("cy")) > 0) {
            var29.setY1(Float.parseFloat(var27.getAttribute("cy")));
         }

         if (this.stringLength(var27.getAttribute("fx")) > 0) {
            var29.setX2(Float.parseFloat(var27.getAttribute("fx")));
         }

         if (this.stringLength(var27.getAttribute("fy")) > 0) {
            var29.setY2(Float.parseFloat(var27.getAttribute("fy")));
         }

         if (this.stringLength(var27.getAttribute("r")) > 0) {
            var29.setR(Float.parseFloat(var27.getAttribute("r")));
         }

         String var30 = var27.getAttributeNS("http://www.w3.org/1999/xlink", "href");
         if (this.stringLength(var30) > 0) {
            var29.reference(var30.substring(1));
            var24.add(var29);
         } else {
            NodeList var31 = var27.getElementsByTagName("stop");

            for(int var32 = 0; var32 < var31.getLength(); ++var32) {
               Element var33 = (Element)var31.item(var32);
               float var34 = Float.parseFloat(var33.getAttribute("offset"));
               var18 = Util.extractStyle(var33.getAttribute("style"), "stop-color");
               String var35 = Util.extractStyle(var33.getAttribute("style"), "stop-opacity");
               int var36 = Integer.parseInt(var18.substring(1), 16);
               Color var21 = new Color(var36);
               var21.a = Float.parseFloat(var35);
               var29.addStep(var34, var21);
            }

            var29.getImage();
         }

         var3.addGradient(var11, var29);
      }

      for(var26 = 0; var26 < var24.size(); ++var26) {
         ((Gradient)var24.get(var26)).resolve(var3);
         ((Gradient)var24.get(var26)).getImage();
      }

   }

   public boolean handles(Element var1) {
      return var1.getNodeName().equals("defs");
   }

   private int stringLength(String var1) {
      return var1 == null ? 0 : var1.length();
   }
}
