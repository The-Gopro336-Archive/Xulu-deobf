package org.newdawn.slick.openal;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFormat.Encoding;
import org.lwjgl.LWJGLUtil;

public class AiffData {
   // $FF: synthetic field
   public final int format;
   // $FF: synthetic field
   public final ByteBuffer data;
   // $FF: synthetic field
   public final int samplerate;

   public static AiffData create(InputStream var0) {
      try {
         return create(AudioSystem.getAudioInputStream(var0));
      } catch (Exception var3) {
         LWJGLUtil.log("Unable to create from inputstream");
         var3.printStackTrace();
         return null;
      }
   }

   public static AiffData create(byte[] var0) {
      try {
         return create(AudioSystem.getAudioInputStream(new BufferedInputStream(new ByteArrayInputStream(var0))));
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static AiffData create(javax.sound.sampled.AudioInputStream var0) {
      AudioFormat var1 = var0.getFormat();
      boolean var2 = false;
      short var12;
      if (var1.getChannels() == 1) {
         if (var1.getSampleSizeInBits() == 8) {
            var12 = 4352;
         } else {
            if (var1.getSampleSizeInBits() != 16) {
               throw new RuntimeException("Illegal sample size");
            }

            var12 = 4353;
         }
      } else {
         if (var1.getChannels() != 2) {
            throw new RuntimeException("Only mono or stereo is supported");
         }

         if (var1.getSampleSizeInBits() == 8) {
            var12 = 4354;
         } else {
            if (var1.getSampleSizeInBits() != 16) {
               throw new RuntimeException("Illegal sample size");
            }

            var12 = 4355;
         }
      }

      byte[] var3 = new byte[var1.getChannels() * (int)var0.getFrameLength() * var1.getSampleSizeInBits() / 8];
      boolean var4 = false;
      int var5 = 0;

      int var13;
      try {
         while((var13 = var0.read(var3, var5, var3.length - var5)) != -1 && var5 < var3.length) {
            var5 += var13;
         }
      } catch (IOException var11) {
         return null;
      }

      ByteBuffer var6 = convertAudioBytes(var1, var3, var1.getSampleSizeInBits() == 16);
      AiffData var7 = new AiffData(var6, var12, (int)var1.getSampleRate());

      try {
         var0.close();
      } catch (IOException var10) {
      }

      return var7;
   }

   public static AiffData create(String var0) {
      return create(AiffData.class.getClassLoader().getResource(var0));
   }

   public static AiffData create(URL var0) {
      try {
         return create(AudioSystem.getAudioInputStream(new BufferedInputStream(var0.openStream())));
      } catch (Exception var2) {
         LWJGLUtil.log(String.valueOf((new StringBuilder()).append("Unable to create from: ").append(var0)));
         var2.printStackTrace();
         return null;
      }
   }

   private static ByteBuffer convertAudioBytes(AudioFormat var0, byte[] var1, boolean var2) {
      ByteBuffer var3 = ByteBuffer.allocateDirect(var1.length);
      var3.order(ByteOrder.nativeOrder());
      ByteBuffer var4 = ByteBuffer.wrap(var1);
      var4.order(ByteOrder.BIG_ENDIAN);
      byte var5;
      if (var2) {
         ShortBuffer var7 = var3.asShortBuffer();
         ShortBuffer var6 = var4.asShortBuffer();

         while(var6.hasRemaining()) {
            var7.put(var6.get());
         }
      } else {
         for(; var4.hasRemaining(); var3.put(var5)) {
            var5 = var4.get();
            if (var0.getEncoding() == Encoding.PCM_SIGNED) {
               var5 = (byte)(var5 + 127);
            }
         }
      }

      var3.rewind();
      return var3;
   }

   public static AiffData create(ByteBuffer var0) {
      try {
         Object var1 = null;
         byte[] var3;
         if (var0.hasArray()) {
            var3 = var0.array();
         } else {
            var3 = new byte[var0.capacity()];
            var0.get(var3);
         }

         return create(var3);
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public void dispose() {
      this.data.clear();
   }

   private AiffData(ByteBuffer var1, int var2, int var3) {
      this.data = var1;
      this.format = var2;
      this.samplerate = var3;
   }
}
