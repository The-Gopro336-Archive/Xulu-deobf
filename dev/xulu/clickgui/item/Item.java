package dev.xulu.clickgui.item;

import dev.xulu.clickgui.Labeled;
import dev.xulu.settings.Value;
import java.io.IOException;

public class Item implements Labeled {
   // $FF: synthetic field
   protected float y;
   // $FF: synthetic field
   protected float x;
   // $FF: synthetic field
   protected Value property;
   // $FF: synthetic field
   protected int height;
   // $FF: synthetic field
   protected int width;
   // $FF: synthetic field
   private final String label;

   public Item(String var1) {
      this.label = var1;
   }

   public void setWidth(int var1) {
      this.width = var1;
   }

   public void setLocation(float var1, float var2) {
      this.x = var1;
      this.y = var2;
   }

   public float getY() {
      return this.y;
   }

   public void mouseClicked(int var1, int var2, int var3) {
   }

   public final String getLabel() {
      return this.label;
   }

   public void drawScreen(int var1, int var2, float var3) {
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setHeight(int var1) {
      this.height = var1;
   }

   public void mouseReleased(int var1, int var2, int var3) {
   }

   public float getX() {
      return this.x;
   }

   public boolean keyTyped(char var1, int var2) throws IOException {
      return false;
   }

   public void setValue(Value var1) {
      this.property = var1;
   }
}
