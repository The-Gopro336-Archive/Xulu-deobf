package org.newdawn.slick.openal;

import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.util.Log;

public class DeferredSound extends AudioImpl implements DeferredResource {
   // $FF: synthetic field
   private Audio target;
   // $FF: synthetic field
   private InputStream in;
   // $FF: synthetic field
   public static final int MOD = 3;
   // $FF: synthetic field
   private int type;
   // $FF: synthetic field
   public static final int OGG = 1;
   // $FF: synthetic field
   public static final int AIF = 4;
   // $FF: synthetic field
   private String ref;
   // $FF: synthetic field
   public static final int WAV = 2;

   public String getDescription() {
      return this.ref;
   }

   private void checkTarget() {
      if (this.target == null) {
         throw new RuntimeException("Attempt to use deferred sound before loading");
      }
   }

   public int playAsSoundEffect(float var1, float var2, boolean var3, float var4, float var5, float var6) {
      this.checkTarget();
      return this.target.playAsSoundEffect(var1, var2, var3, var4, var5, var6);
   }

   public void stop() {
      this.checkTarget();
      this.target.stop();
   }

   public int playAsMusic(float var1, float var2, boolean var3) {
      this.checkTarget();
      return this.target.playAsMusic(var1, var2, var3);
   }

   public void load() throws IOException {
      boolean var1 = SoundStore.get().isDeferredLoading();
      SoundStore.get().setDeferredLoading(false);
      if (this.in != null) {
         switch(this.type) {
         case 1:
            this.target = SoundStore.get().getOgg(this.in);
            break;
         case 2:
            this.target = SoundStore.get().getWAV(this.in);
            break;
         case 3:
            this.target = SoundStore.get().getMOD(this.in);
            break;
         case 4:
            this.target = SoundStore.get().getAIF(this.in);
            break;
         default:
            Log.error(String.valueOf((new StringBuilder()).append("Unrecognised sound type: ").append(this.type)));
         }
      } else {
         switch(this.type) {
         case 1:
            this.target = SoundStore.get().getOgg(this.ref);
            break;
         case 2:
            this.target = SoundStore.get().getWAV(this.ref);
            break;
         case 3:
            this.target = SoundStore.get().getMOD(this.ref);
            break;
         case 4:
            this.target = SoundStore.get().getAIF(this.ref);
            break;
         default:
            Log.error(String.valueOf((new StringBuilder()).append("Unrecognised sound type: ").append(this.type)));
         }
      }

      SoundStore.get().setDeferredLoading(var1);
   }

   public DeferredSound(String var1, InputStream var2, int var3) {
      this.ref = var1;
      this.type = var3;
      if (var1.equals(var2.toString())) {
         this.in = var2;
      }

      LoadingList.get().add(this);
   }

   public int playAsSoundEffect(float var1, float var2, boolean var3) {
      this.checkTarget();
      return this.target.playAsSoundEffect(var1, var2, var3);
   }

   public boolean isPlaying() {
      this.checkTarget();
      return this.target.isPlaying();
   }
}
