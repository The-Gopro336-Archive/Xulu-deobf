package org.newdawn.slick.particles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;

public class ParticleSystem {
   // $FF: synthetic field
   public static final int BLEND_ADDITIVE = 1;
   // $FF: synthetic field
   private boolean removeCompletedEmitters;
   // $FF: synthetic field
   private static final int DEFAULT_PARTICLES = 100;
   // $FF: synthetic field
   private float y;
   // $FF: synthetic field
   private boolean visible;
   // $FF: synthetic field
   private ArrayList removeMe;
   // $FF: synthetic field
   private int blendingMode;
   // $FF: synthetic field
   protected HashMap particlesByEmitter;
   // $FF: synthetic field
   protected ArrayList emitters;
   // $FF: synthetic field
   private Image sprite;
   // $FF: synthetic field
   private boolean usePoints;
   // $FF: synthetic field
   public static final int BLEND_COMBINE = 2;
   // $FF: synthetic field
   private float x;
   // $FF: synthetic field
   protected SGL GL;
   // $FF: synthetic field
   private Color mask;
   // $FF: synthetic field
   private String defaultImageName;
   // $FF: synthetic field
   protected int maxParticlesPerEmitter;
   // $FF: synthetic field
   protected Particle dummy;
   // $FF: synthetic field
   private int pCount;

   public float getPositionX() {
      return this.x;
   }

   public ParticleSystem(String var1, int var2) {
      this(var1, var2, (Color)null);
   }

   public void setRemoveCompletedEmitters(boolean var1) {
      this.removeCompletedEmitters = var1;
   }

   public boolean usePoints() {
      return this.usePoints;
   }

   public void moveAll(ParticleEmitter var1, float var2, float var3) {
      ParticleSystem.ParticlePool var4 = (ParticleSystem.ParticlePool)this.particlesByEmitter.get(var1);

      for(int var5 = 0; var5 < var4.particles.length; ++var5) {
         if (var4.particles[var5].inUse()) {
            var4.particles[var5].move(var2, var3);
         }
      }

   }

   public void removeEmitter(ParticleEmitter var1) {
      this.emitters.remove(var1);
      this.particlesByEmitter.remove(var1);
   }

   public void setBlendingMode(int var1) {
      this.blendingMode = var1;
   }

   protected Particle createParticle(ParticleSystem var1) {
      return new Particle(var1);
   }

   public void setPosition(float var1, float var2) {
      this.x = var1;
      this.y = var2;
   }

   private void loadSystemParticleImage() {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            try {
               if (ParticleSystem.this.mask != null) {
                  ParticleSystem.this.sprite = new Image(ParticleSystem.this.defaultImageName, ParticleSystem.this.mask);
               } else {
                  ParticleSystem.this.sprite = new Image(ParticleSystem.this.defaultImageName);
               }
            } catch (SlickException var2) {
               Log.error((Throwable)var2);
               ParticleSystem.this.defaultImageName = null;
            }

            return null;
         }
      });
   }

   public ParticleSystem(Image var1) {
      this((Image)var1, 100);
   }

   public void release(Particle var1) {
      if (var1 != this.dummy) {
         ParticleSystem.ParticlePool var2 = (ParticleSystem.ParticlePool)this.particlesByEmitter.get(var1.getEmitter());
         var2.available.add(var1);
      }

   }

   public void reset() {
      Iterator var1 = this.particlesByEmitter.values().iterator();

      while(var1.hasNext()) {
         ParticleSystem.ParticlePool var2 = (ParticleSystem.ParticlePool)var1.next();
         var2.reset(this);
      }

      for(int var5 = 0; var5 < this.emitters.size(); ++var5) {
         ParticleEmitter var3 = (ParticleEmitter)this.emitters.get(var5);
         var3.resetState();
      }

   }

   public static void setRelativePath(String var0) {
      ConfigurableEmitter.setRelativePath(var0);
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   public int getBlendingMode() {
      return this.blendingMode;
   }

   public void setDefaultImageName(String var1) {
      this.defaultImageName = var1;
      this.sprite = null;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public ParticleSystem(String var1, int var2, Color var3) {
      this.GL = Renderer.get();
      this.removeMe = new ArrayList();
      this.particlesByEmitter = new HashMap();
      this.emitters = new ArrayList();
      this.blendingMode = 2;
      this.removeCompletedEmitters = true;
      this.visible = true;
      this.maxParticlesPerEmitter = var2;
      this.mask = var3;
      this.setDefaultImageName(var1);
      this.dummy = this.createParticle(this);
   }

   public void setUsePoints(boolean var1) {
      this.usePoints = var1;
   }

   public void update(int var1) {
      if (this.sprite == null && this.defaultImageName != null) {
         this.loadSystemParticleImage();
      }

      this.removeMe.clear();
      ArrayList var2 = new ArrayList(this.emitters);

      ParticleEmitter var4;
      for(int var3 = 0; var3 < var2.size(); ++var3) {
         var4 = (ParticleEmitter)var2.get(var3);
         if (var4.isEnabled()) {
            var4.update(this, var1);
            if (this.removeCompletedEmitters && var4.completed()) {
               this.removeMe.add(var4);
               this.particlesByEmitter.remove(var4);
            }
         }
      }

      this.emitters.removeAll(this.removeMe);
      this.pCount = 0;
      if (!this.particlesByEmitter.isEmpty()) {
         Iterator var7 = this.particlesByEmitter.keySet().iterator();

         while(true) {
            do {
               if (!var7.hasNext()) {
                  return;
               }

               var4 = (ParticleEmitter)var7.next();
            } while(!var4.isEnabled());

            ParticleSystem.ParticlePool var5 = (ParticleSystem.ParticlePool)this.particlesByEmitter.get(var4);

            for(int var6 = 0; var6 < var5.particles.length; ++var6) {
               if (var5.particles[var6].life > 0.0F) {
                  var5.particles[var6].update(var1);
                  ++this.pCount;
               }
            }
         }
      }
   }

   public void releaseAll(ParticleEmitter var1) {
      if (!this.particlesByEmitter.isEmpty()) {
         Iterator var2 = this.particlesByEmitter.values().iterator();

         while(var2.hasNext()) {
            ParticleSystem.ParticlePool var3 = (ParticleSystem.ParticlePool)var2.next();

            for(int var4 = 0; var4 < var3.particles.length; ++var4) {
               if (var3.particles[var4].inUse() && var3.particles[var4].getEmitter() == var1) {
                  var3.particles[var4].setLife(-1.0F);
                  this.release(var3.particles[var4]);
               }
            }
         }
      }

   }

   public ParticleSystem(Image var1, int var2) {
      this.GL = Renderer.get();
      this.removeMe = new ArrayList();
      this.particlesByEmitter = new HashMap();
      this.emitters = new ArrayList();
      this.blendingMode = 2;
      this.removeCompletedEmitters = true;
      this.visible = true;
      this.maxParticlesPerEmitter = var2;
      this.sprite = var1;
      this.dummy = this.createParticle(this);
   }

   public int getEmitterCount() {
      return this.emitters.size();
   }

   public void addEmitter(ParticleEmitter var1) {
      this.emitters.add(var1);
      ParticleSystem.ParticlePool var2 = new ParticleSystem.ParticlePool(this, this.maxParticlesPerEmitter);
      this.particlesByEmitter.put(var1, var2);
   }

   public void render() {
      this.render(this.x, this.y);
   }

   public ParticleSystem duplicate() throws SlickException {
      for(int var1 = 0; var1 < this.emitters.size(); ++var1) {
         if (!(this.emitters.get(var1) instanceof ConfigurableEmitter)) {
            throw new SlickException("Only systems contianing configurable emitters can be duplicated");
         }
      }

      ParticleSystem var6 = null;

      try {
         ByteArrayOutputStream var2 = new ByteArrayOutputStream();
         ParticleIO.saveConfiguredSystem((OutputStream)var2, this);
         ByteArrayInputStream var3 = new ByteArrayInputStream(var2.toByteArray());
         var6 = ParticleIO.loadConfiguredSystem((InputStream)var3);
         return var6;
      } catch (IOException var5) {
         Log.error("Failed to duplicate particle system");
         throw new SlickException("Unable to duplicated particle system", var5);
      }
   }

   public ParticleSystem(String var1) {
      this((String)var1, 100);
   }

   public int getParticleCount() {
      return this.pCount;
   }

   public Particle getNewParticle(ParticleEmitter var1, float var2) {
      ParticleSystem.ParticlePool var3 = (ParticleSystem.ParticlePool)this.particlesByEmitter.get(var1);
      ArrayList var4 = var3.available;
      if (var4.size() > 0) {
         Particle var5 = (Particle)var4.remove(var4.size() - 1);
         var5.init(var1, var2);
         var5.setImage(this.sprite);
         return var5;
      } else {
         Log.warn("Ran out of particles (increase the limit)!");
         return this.dummy;
      }
   }

   public ParticleEmitter getEmitter(int var1) {
      return (ParticleEmitter)this.emitters.get(var1);
   }

   public void render(float var1, float var2) {
      if (this.sprite == null && this.defaultImageName != null) {
         this.loadSystemParticleImage();
      }

      if (this.visible) {
         this.GL.glTranslatef(var1, var2, 0.0F);
         if (this.blendingMode == 1) {
            this.GL.glBlendFunc(770, 1);
         }

         if (this.usePoints()) {
            this.GL.glEnable(2832);
            TextureImpl.bindNone();
         }

         for(int var3 = 0; var3 < this.emitters.size(); ++var3) {
            ParticleEmitter var4 = (ParticleEmitter)this.emitters.get(var3);
            if (var4.isEnabled()) {
               if (var4.useAdditive()) {
                  this.GL.glBlendFunc(770, 1);
               }

               ParticleSystem.ParticlePool var5 = (ParticleSystem.ParticlePool)this.particlesByEmitter.get(var4);
               Image var6 = var4.getImage();
               if (var6 == null) {
                  var6 = this.sprite;
               }

               if (!var4.isOriented() && !var4.usePoints(this)) {
                  var6.startUse();
               }

               for(int var7 = 0; var7 < var5.particles.length; ++var7) {
                  if (var5.particles[var7].inUse()) {
                     var5.particles[var7].render();
                  }
               }

               if (!var4.isOriented() && !var4.usePoints(this)) {
                  var6.endUse();
               }

               if (var4.useAdditive()) {
                  this.GL.glBlendFunc(770, 771);
               }
            }
         }

         if (this.usePoints()) {
            this.GL.glDisable(2832);
         }

         if (this.blendingMode == 1) {
            this.GL.glBlendFunc(770, 771);
         }

         Color.white.bind();
         this.GL.glTranslatef(-var1, -var2, 0.0F);
      }
   }

   public float getPositionY() {
      return this.y;
   }

   public void removeAllEmitters() {
      for(int var1 = 0; var1 < this.emitters.size(); ++var1) {
         this.removeEmitter((ParticleEmitter)this.emitters.get(var1));
         --var1;
      }

   }

   private class ParticlePool {
      // $FF: synthetic field
      public Particle[] particles;
      // $FF: synthetic field
      public ArrayList available;

      public void reset(ParticleSystem var1) {
         this.available.clear();

         for(int var2 = 0; var2 < this.particles.length; ++var2) {
            this.available.add(this.particles[var2]);
         }

      }

      public ParticlePool(ParticleSystem var2, int var3) {
         this.particles = new Particle[var3];
         this.available = new ArrayList();

         for(int var4 = 0; var4 < this.particles.length; ++var4) {
            this.particles[var4] = ParticleSystem.this.createParticle(var2);
         }

         this.reset(var2);
      }
   }
}
