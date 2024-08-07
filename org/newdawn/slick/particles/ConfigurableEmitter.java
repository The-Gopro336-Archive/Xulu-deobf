package org.newdawn.slick.particles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;
import org.newdawn.slick.util.Log;

public class ConfigurableEmitter implements ParticleEmitter {
   // $FF: synthetic field
   public ConfigurableEmitter.Range spawnCount = new ConfigurableEmitter.Range(5.0F, 5.0F);
   // $FF: synthetic field
   private ParticleSystem engine;
   // $FF: synthetic field
   public ConfigurableEmitter.SimpleValue startAlpha = new ConfigurableEmitter.SimpleValue(255.0F);
   // $FF: synthetic field
   public ConfigurableEmitter.LinearInterpolator scaleY;
   // $FF: synthetic field
   public ConfigurableEmitter.RandomValue spread = new ConfigurableEmitter.RandomValue(360.0F);
   // $FF: synthetic field
   private boolean enabled = true;
   // $FF: synthetic field
   public ConfigurableEmitter.Range emitCount = new ConfigurableEmitter.Range(1000.0F, 1000.0F);
   // $FF: synthetic field
   public String name;
   // $FF: synthetic field
   private Image image;
   // $FF: synthetic field
   public ConfigurableEmitter.SimpleValue windFactor = new ConfigurableEmitter.SimpleValue(0.0F);
   // $FF: synthetic field
   public String imageName = "";
   // $FF: synthetic field
   public ArrayList colors = new ArrayList();
   // $FF: synthetic field
   private int nextSpawn = 0;
   // $FF: synthetic field
   public boolean useAdditive = false;
   // $FF: synthetic field
   public ConfigurableEmitter.Range speed = new ConfigurableEmitter.Range(50.0F, 50.0F);
   // $FF: synthetic field
   private float y;
   // $FF: synthetic field
   public ConfigurableEmitter.SimpleValue gravityFactor = new ConfigurableEmitter.SimpleValue(0.0F);
   // $FF: synthetic field
   private boolean updateImage;
   // $FF: synthetic field
   private int timeout;
   // $FF: synthetic field
   public ConfigurableEmitter.SimpleValue growthFactor = new ConfigurableEmitter.SimpleValue(0.0F);
   // $FF: synthetic field
   public ConfigurableEmitter.Range initialDistance = new ConfigurableEmitter.Range(0.0F, 0.0F);
   // $FF: synthetic field
   public ConfigurableEmitter.Range xOffset = new ConfigurableEmitter.Range(0.0F, 0.0F);
   // $FF: synthetic field
   public ConfigurableEmitter.LinearInterpolator velocity;
   // $FF: synthetic field
   public int usePoints = 1;
   // $FF: synthetic field
   protected float adjusty;
   // $FF: synthetic field
   protected boolean wrapUp = false;
   // $FF: synthetic field
   public ConfigurableEmitter.Range initialLife = new ConfigurableEmitter.Range(1000.0F, 1000.0F);
   // $FF: synthetic field
   protected float adjustx;
   // $FF: synthetic field
   public boolean useOriented = false;
   // $FF: synthetic field
   private static String relativePath = "";
   // $FF: synthetic field
   private int leftToEmit;
   // $FF: synthetic field
   private int particleCount;
   // $FF: synthetic field
   protected boolean completed = false;
   // $FF: synthetic field
   private float x;
   // $FF: synthetic field
   protected boolean adjust;
   // $FF: synthetic field
   public ConfigurableEmitter.SimpleValue endAlpha = new ConfigurableEmitter.SimpleValue(0.0F);
   // $FF: synthetic field
   public ConfigurableEmitter.SimpleValue angularOffset = new ConfigurableEmitter.SimpleValue(0.0F);
   // $FF: synthetic field
   public ConfigurableEmitter.LinearInterpolator alpha;
   // $FF: synthetic field
   public ConfigurableEmitter.Range length = new ConfigurableEmitter.Range(1000.0F, 1000.0F);
   // $FF: synthetic field
   public ConfigurableEmitter.Range spawnInterval = new ConfigurableEmitter.Range(100.0F, 100.0F);
   // $FF: synthetic field
   public ConfigurableEmitter.Range yOffset = new ConfigurableEmitter.Range(0.0F, 0.0F);
   // $FF: synthetic field
   public ConfigurableEmitter.Range initialSize = new ConfigurableEmitter.Range(10.0F, 10.0F);
   // $FF: synthetic field
   public ConfigurableEmitter.LinearInterpolator size;

   public void resetState() {
      this.wrapUp = false;
      this.replay();
   }

   public boolean completed() {
      if (this.engine == null) {
         return false;
      } else if (this.length.isEnabled()) {
         return this.timeout > 0 ? false : this.completed;
      } else if (this.emitCount.isEnabled()) {
         return this.leftToEmit > 0 ? false : this.completed;
      } else {
         return this.wrapUp ? this.completed : false;
      }
   }

   public void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public void updateParticle(Particle var1, int var2) {
      ++this.particleCount;
      var1.x += this.adjustx;
      var1.y += this.adjusty;
      var1.adjustVelocity(this.windFactor.getValue(0.0F) * 5.0E-5F * (float)var2, this.gravityFactor.getValue(0.0F) * 5.0E-5F * (float)var2);
      float var3 = var1.getLife() / var1.getOriginalLife();
      float var4 = 1.0F - var3;
      float var5 = 0.0F;
      float var6 = 1.0F;
      Color var7 = null;
      Color var8 = null;

      float var12;
      for(int var9 = 0; var9 < this.colors.size() - 1; ++var9) {
         ConfigurableEmitter.ColorRecord var10 = (ConfigurableEmitter.ColorRecord)this.colors.get(var9);
         ConfigurableEmitter.ColorRecord var11 = (ConfigurableEmitter.ColorRecord)this.colors.get(var9 + 1);
         if (var4 >= var10.pos && var4 <= var11.pos) {
            var7 = var10.col;
            var8 = var11.col;
            var12 = var11.pos - var10.pos;
            var5 = var4 - var10.pos;
            var5 /= var12;
            var5 = 1.0F - var5;
            var6 = 1.0F - var5;
         }
      }

      float var13;
      if (var7 != null) {
         var13 = var7.r * var5 + var8.r * var6;
         float var14 = var7.g * var5 + var8.g * var6;
         float var15 = var7.b * var5 + var8.b * var6;
         if (this.alpha.isActive()) {
            var12 = this.alpha.getValue(var4) / 255.0F;
         } else {
            var12 = this.startAlpha.getValue(0.0F) / 255.0F * var3 + this.endAlpha.getValue(0.0F) / 255.0F * var4;
         }

         var1.setColor(var13, var14, var15, var12);
      }

      if (this.size.isActive()) {
         var13 = this.size.getValue(var4);
         var1.setSize(var13);
      } else {
         var1.adjustSize((float)var2 * this.growthFactor.getValue(0.0F) * 0.001F);
      }

      if (this.velocity.isActive()) {
         var1.setSpeed(this.velocity.getValue(var4));
      }

      if (this.scaleY.isActive()) {
         var1.setScaleY(this.scaleY.getValue(var4));
      }

   }

   public void setImageName(String var1) {
      if (var1.length() == 0) {
         var1 = null;
      }

      this.imageName = var1;
      if (var1 == null) {
         this.image = null;
      } else {
         this.updateImage = true;
      }

   }

   public String getImageName() {
      return this.imageName;
   }

   public void setPosition(float var1, float var2) {
      this.setPosition(var1, var2, true);
   }

   public static void setRelativePath(String var0) {
      if (!var0.endsWith("/")) {
         var0 = String.valueOf((new StringBuilder()).append(var0).append("/"));
      }

      relativePath = var0;
   }

   public void replay() {
      this.reset();
      this.nextSpawn = 0;
      this.leftToEmit = (int)this.emitCount.random();
      this.timeout = (int)this.length.random();
   }

   public ConfigurableEmitter(String var1) {
      this.name = var1;
      this.leftToEmit = (int)this.emitCount.random();
      this.timeout = (int)this.length.random();
      this.colors.add(new ConfigurableEmitter.ColorRecord(0.0F, Color.white));
      this.colors.add(new ConfigurableEmitter.ColorRecord(1.0F, Color.red));
      ArrayList var2 = new ArrayList();
      var2.add(new Vector2f(0.0F, 0.0F));
      var2.add(new Vector2f(1.0F, 255.0F));
      this.alpha = new ConfigurableEmitter.LinearInterpolator(var2, 0, 255);
      var2 = new ArrayList();
      var2.add(new Vector2f(0.0F, 0.0F));
      var2.add(new Vector2f(1.0F, 255.0F));
      this.size = new ConfigurableEmitter.LinearInterpolator(var2, 0, 255);
      var2 = new ArrayList();
      var2.add(new Vector2f(0.0F, 0.0F));
      var2.add(new Vector2f(1.0F, 1.0F));
      this.velocity = new ConfigurableEmitter.LinearInterpolator(var2, 0, 1);
      var2 = new ArrayList();
      var2.add(new Vector2f(0.0F, 0.0F));
      var2.add(new Vector2f(1.0F, 1.0F));
      this.scaleY = new ConfigurableEmitter.LinearInterpolator(var2, 0, 1);
   }

   public ConfigurableEmitter duplicate() {
      ConfigurableEmitter var1 = null;

      try {
         ByteArrayOutputStream var2 = new ByteArrayOutputStream();
         ParticleIO.saveEmitter((OutputStream)var2, this);
         ByteArrayInputStream var3 = new ByteArrayInputStream(var2.toByteArray());
         var1 = ParticleIO.loadEmitter((InputStream)var3);
         return var1;
      } catch (IOException var4) {
         Log.error(String.valueOf((new StringBuilder()).append("Slick: ConfigurableEmitter.duplicate(): caught exception ").append(var4.toString())));
         return null;
      }
   }

   public void setPosition(float var1, float var2, boolean var3) {
      if (var3) {
         this.adjust = true;
         this.adjustx -= this.x - var1;
         this.adjusty -= this.y - var2;
      }

      this.x = var1;
      this.y = var2;
   }

   public float getY() {
      return this.y;
   }

   public boolean usePoints(ParticleSystem var1) {
      return this.usePoints == 1 && var1.usePoints() || this.usePoints == 2;
   }

   public void addColorPoint(float var1, Color var2) {
      this.colors.add(new ConfigurableEmitter.ColorRecord(var1, var2));
   }

   public String toString() {
      return String.valueOf((new StringBuilder()).append("[").append(this.name).append("]"));
   }

   public void update(ParticleSystem var1, int var2) {
      this.engine = var1;
      if (!this.adjust) {
         this.adjustx = 0.0F;
         this.adjusty = 0.0F;
      } else {
         this.adjust = false;
      }

      if (this.updateImage) {
         this.updateImage = false;

         try {
            this.image = new Image(String.valueOf((new StringBuilder()).append(relativePath).append(this.imageName)));
         } catch (SlickException var15) {
            this.image = null;
            Log.error((Throwable)var15);
         }
      }

      if ((this.wrapUp || this.length.isEnabled() && this.timeout < 0 || this.emitCount.isEnabled() && this.leftToEmit <= 0) && this.particleCount == 0) {
         this.completed = true;
      }

      this.particleCount = 0;
      if (!this.wrapUp) {
         if (this.length.isEnabled()) {
            if (this.timeout < 0) {
               return;
            }

            this.timeout -= var2;
         }

         if (!this.emitCount.isEnabled() || this.leftToEmit > 0) {
            this.nextSpawn -= var2;
            if (this.nextSpawn < 0) {
               this.nextSpawn = (int)this.spawnInterval.random();
               int var3 = (int)this.spawnCount.random();

               for(int var4 = 0; var4 < var3; ++var4) {
                  Particle var5 = var1.getNewParticle(this, this.initialLife.random());
                  var5.setSize(this.initialSize.random());
                  var5.setPosition(this.x + this.xOffset.random(), this.y + this.yOffset.random());
                  var5.setVelocity(0.0F, 0.0F, 0.0F);
                  float var6 = this.initialDistance.random();
                  float var7 = this.speed.random();
                  if (var6 != 0.0F || var7 != 0.0F) {
                     float var8 = this.spread.getValue(0.0F);
                     float var9 = var8 + this.angularOffset.getValue(0.0F) - this.spread.getValue() / 2.0F - 90.0F;
                     float var10 = (float)FastTrig.cos(Math.toRadians((double)var9)) * var6;
                     float var11 = (float)FastTrig.sin(Math.toRadians((double)var9)) * var6;
                     var5.adjustPosition(var10, var11);
                     float var12 = (float)FastTrig.cos(Math.toRadians((double)var9));
                     float var13 = (float)FastTrig.sin(Math.toRadians((double)var9));
                     var5.setVelocity(var12, var13, var7 * 0.001F);
                  }

                  if (this.image != null) {
                     var5.setImage(this.image);
                  }

                  ConfigurableEmitter.ColorRecord var16 = (ConfigurableEmitter.ColorRecord)this.colors.get(0);
                  var5.setColor(var16.col.r, var16.col.g, var16.col.b, this.startAlpha.getValue(0.0F) / 255.0F);
                  var5.setUsePoint(this.usePoints);
                  var5.setOriented(this.useOriented);
                  if (this.emitCount.isEnabled()) {
                     --this.leftToEmit;
                     if (this.leftToEmit <= 0) {
                        break;
                     }
                  }
               }
            }

         }
      }
   }

   public void wrapUp() {
      this.wrapUp = true;
   }

   public void replayCheck() {
      if (this.completed() && this.engine != null && this.engine.getParticleCount() == 0) {
         this.replay();
      }

   }

   public boolean useAdditive() {
      return this.useAdditive;
   }

   public boolean isOriented() {
      return this.useOriented;
   }

   public Image getImage() {
      return this.image;
   }

   public float getX() {
      return this.x;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void reset() {
      this.completed = false;
      if (this.engine != null) {
         this.engine.releaseAll(this);
      }

   }

   public class ColorRecord {
      // $FF: synthetic field
      public float pos;
      // $FF: synthetic field
      public Color col;

      public ColorRecord(float var2, Color var3) {
         this.pos = var2;
         this.col = var3;
      }
   }

   public class LinearInterpolator implements ConfigurableEmitter.Value {
      // $FF: synthetic field
      private int max;
      // $FF: synthetic field
      private ArrayList curve;
      // $FF: synthetic field
      private int min;
      // $FF: synthetic field
      private boolean active;

      public float getValue(float var1) {
         Vector2f var2 = (Vector2f)this.curve.get(0);

         for(int var3 = 1; var3 < this.curve.size(); ++var3) {
            Vector2f var4 = (Vector2f)this.curve.get(var3);
            if (var1 >= var2.getX() && var1 <= var4.getX()) {
               float var5 = (var1 - var2.getX()) / (var4.getX() - var2.getX());
               float var6 = var2.getY() + var5 * (var4.getY() - var2.getY());
               return var6;
            }

            var2 = var4;
         }

         return 0.0F;
      }

      public LinearInterpolator(ArrayList var2, int var3, int var4) {
         this.curve = var2;
         this.min = var3;
         this.max = var4;
         this.active = false;
      }

      public boolean isActive() {
         return this.active;
      }

      public int getMin() {
         return this.min;
      }

      public ArrayList getCurve() {
         return this.curve;
      }

      public int getMax() {
         return this.max;
      }

      public void setActive(boolean var1) {
         this.active = var1;
      }

      public void setCurve(ArrayList var1) {
         this.curve = var1;
      }

      public void setMax(int var1) {
         this.max = var1;
      }

      public void setMin(int var1) {
         this.min = var1;
      }
   }

   public class Range {
      // $FF: synthetic field
      private boolean enabled;
      // $FF: synthetic field
      private float min;
      // $FF: synthetic field
      private float max;

      public void setMin(float var1) {
         this.min = var1;
      }

      public float getMin() {
         return this.min;
      }

      // $FF: synthetic method
      Range(float var2, float var3, Object var4) {
         this(var2, var3);
      }

      public void setMax(float var1) {
         this.max = var1;
      }

      public boolean isEnabled() {
         return this.enabled;
      }

      private Range(float var2, float var3) {
         this.enabled = false;
         this.min = var2;
         this.max = var3;
      }

      public float getMax() {
         return this.max;
      }

      public float random() {
         return (float)((double)this.min + Math.random() * (double)(this.max - this.min));
      }

      public void setEnabled(boolean var1) {
         this.enabled = var1;
      }
   }

   public class RandomValue implements ConfigurableEmitter.Value {
      // $FF: synthetic field
      private float value;

      public void setValue(float var1) {
         this.value = var1;
      }

      // $FF: synthetic method
      RandomValue(float var2, Object var3) {
         this(var2);
      }

      private RandomValue(float var2) {
         this.value = var2;
      }

      public float getValue(float var1) {
         return (float)(Math.random() * (double)this.value);
      }

      public float getValue() {
         return this.value;
      }
   }

   public class SimpleValue implements ConfigurableEmitter.Value {
      // $FF: synthetic field
      private float value;
      // $FF: synthetic field
      private float next;

      public void setValue(float var1) {
         this.value = var1;
      }

      private SimpleValue(float var2) {
         this.value = var2;
      }

      // $FF: synthetic method
      SimpleValue(float var2, Object var3) {
         this(var2);
      }

      public float getValue(float var1) {
         return this.value;
      }
   }

   public interface Value {
      float getValue(float var1);
   }
}
