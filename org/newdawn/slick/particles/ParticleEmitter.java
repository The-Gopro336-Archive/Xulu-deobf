package org.newdawn.slick.particles;

import org.newdawn.slick.Image;

public interface ParticleEmitter {
   boolean completed();

   void update(ParticleSystem var1, int var2);

   void updateParticle(Particle var1, int var2);

   boolean isOriented();

   void wrapUp();

   void resetState();

   boolean usePoints(ParticleSystem var1);

   Image getImage();

   boolean isEnabled();

   boolean useAdditive();

   void setEnabled(boolean var1);
}
