package org.newdawn.slick.tests;

import java.nio.ByteOrder;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;

public class ImageBufferEndianTest extends BasicGame {
   // $FF: synthetic field
   private Image fromRed;
   // $FF: synthetic field
   private ImageBuffer redImageBuffer;
   // $FF: synthetic field
   private ImageBuffer blueImageBuffer;
   // $FF: synthetic field
   private String endian;
   // $FF: synthetic field
   private Image fromBlue;

   public ImageBufferEndianTest() {
      super("ImageBuffer Endian Test");
   }

   public void update(GameContainer var1, int var2) throws SlickException {
   }

   public void render(GameContainer var1, Graphics var2) throws SlickException {
      var2.setColor(Color.white);
      var2.drawString(String.valueOf((new StringBuilder()).append("Endianness is ").append(this.endian)), 10.0F, 100.0F);
      var2.drawString("Image below should be red", 10.0F, 200.0F);
      var2.drawImage(this.fromRed, 10.0F, 220.0F);
      var2.drawString("Image below should be blue", 410.0F, 200.0F);
      var2.drawImage(this.fromBlue, 410.0F, 220.0F);
   }

   public void init(GameContainer var1) throws SlickException {
      if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
         this.endian = "Big endian";
      } else if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         this.endian = "Little endian";
      } else {
         this.endian = "no idea";
      }

      this.redImageBuffer = new ImageBuffer(100, 100);
      this.fillImageBufferWithColor(this.redImageBuffer, Color.red, 100, 100);
      this.blueImageBuffer = new ImageBuffer(100, 100);
      this.fillImageBufferWithColor(this.blueImageBuffer, Color.blue, 100, 100);
      this.fromRed = this.redImageBuffer.getImage();
      this.fromBlue = this.blueImageBuffer.getImage();
   }

   public static void main(String[] var0) {
      try {
         AppGameContainer var1 = new AppGameContainer(new ImageBufferEndianTest());
         var1.setDisplayMode(800, 600, false);
         var1.start();
      } catch (SlickException var3) {
         var3.printStackTrace();
      }

   }

   private void fillImageBufferWithColor(ImageBuffer var1, Color var2, int var3, int var4) {
      for(int var5 = 0; var5 < var3; ++var5) {
         for(int var6 = 0; var6 < var4; ++var6) {
            var1.setRGBA(var5, var6, var2.getRed(), var2.getGreen(), var2.getBlue(), var2.getAlpha());
         }
      }

   }
}
