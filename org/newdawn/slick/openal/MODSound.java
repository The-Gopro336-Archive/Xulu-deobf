package org.newdawn.slick.openal;

import ibxm.Module;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

public class MODSound extends AudioImpl {
   // $FF: synthetic field
   private SoundStore store;
   // $FF: synthetic field
   private Module module;

   public void poll() {
   }

   public int playAsMusic(float var1, float var2, boolean var3) {
      this.cleanUpSource();
      this.store.setCurrentMusicVolume(var2);
      this.store.setMOD(this);
      return this.store.getSource(0);
   }

   public float getPosition() {
      throw new RuntimeException("Positioning on modules is not currently supported");
   }

   public void stop() {
      this.store.setMOD((MODSound)null);
   }

   private void cleanUpSource() {
      AL10.alSourceStop(this.store.getSource(0));
      IntBuffer var1 = BufferUtils.createIntBuffer(1);

      for(int var2 = AL10.alGetSourcei(this.store.getSource(0), 4117); var2 > 0; --var2) {
         AL10.alSourceUnqueueBuffers(this.store.getSource(0), var1);
      }

      AL10.alSourcei(this.store.getSource(0), 4105, 0);
   }

   public boolean setPosition(float var1) {
      throw new RuntimeException("Positioning on modules is not currently supported");
   }

   public int playAsSoundEffect(float var1, float var2, boolean var3) {
      return -1;
   }

   public MODSound(SoundStore var1, InputStream var2) throws IOException {
      this.store = var1;
   }
}
