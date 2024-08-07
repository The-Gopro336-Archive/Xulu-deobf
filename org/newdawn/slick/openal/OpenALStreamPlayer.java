package org.newdawn.slick.openal;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.OpenALException;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class OpenALStreamPlayer {
   // $FF: synthetic field
   public static final int BUFFER_COUNT = 3;
   // $FF: synthetic field
   private IntBuffer bufferNames;
   // $FF: synthetic field
   private boolean loop;
   // $FF: synthetic field
   private int source;
   // $FF: synthetic field
   private float positionOffset;
   // $FF: synthetic field
   private AudioInputStream audio;
   // $FF: synthetic field
   private URL url;
   // $FF: synthetic field
   private float pitch;
   // $FF: synthetic field
   private ByteBuffer bufferData = BufferUtils.createByteBuffer(81920);
   // $FF: synthetic field
   private static final int sectionSize = 81920;
   // $FF: synthetic field
   private int remainingBufferCount;
   // $FF: synthetic field
   private IntBuffer unqueued = BufferUtils.createIntBuffer(1);
   // $FF: synthetic field
   private String ref;
   // $FF: synthetic field
   private byte[] buffer = new byte[81920];
   // $FF: synthetic field
   private boolean done = true;

   public String getSource() {
      return this.url == null ? this.ref : this.url.toString();
   }

   public void play(boolean var1) throws IOException {
      this.loop = var1;
      this.initStreams();
      this.done = false;
      AL10.alSourceStop(this.source);
      this.removeBuffers();
      this.startPlayback();
   }

   private void startPlayback() {
      AL10.alSourcei(this.source, 4103, 0);
      AL10.alSourcef(this.source, 4099, this.pitch);
      this.remainingBufferCount = 3;

      for(int var1 = 0; var1 < 3; ++var1) {
         this.stream(this.bufferNames.get(var1));
      }

      AL10.alSourceQueueBuffers(this.source, this.bufferNames);
      AL10.alSourcePlay(this.source);
   }

   public float getPosition() {
      return this.positionOffset + AL10.alGetSourcef(this.source, 4132);
   }

   public boolean setPosition(float var1) {
      try {
         if (this.getPosition() > var1) {
            this.initStreams();
         }

         float var2 = (float)this.audio.getRate();
         float var3;
         if (this.audio.getChannels() > 1) {
            var3 = 4.0F;
         } else {
            var3 = 2.0F;
         }

         while(this.positionOffset < var1) {
            int var4 = this.audio.read(this.buffer);
            if (var4 == -1) {
               if (this.loop) {
                  this.initStreams();
               } else {
                  this.done = true;
               }

               return false;
            }

            float var5 = (float)var4 / var3 / var2;
            this.positionOffset += var5;
         }

         this.startPlayback();
         return true;
      } catch (IOException var6) {
         Log.error((Throwable)var6);
         return false;
      }
   }

   public void update() {
      if (!this.done) {
         float var1 = (float)this.audio.getRate();
         float var2;
         if (this.audio.getChannels() > 1) {
            var2 = 4.0F;
         } else {
            var2 = 2.0F;
         }

         int var4;
         for(int var3 = AL10.alGetSourcei(this.source, 4118); var3 > 0; --var3) {
            this.unqueued.clear();
            AL10.alSourceUnqueueBuffers(this.source, this.unqueued);
            var4 = this.unqueued.get(0);
            float var5 = (float)AL10.alGetBufferi(var4, 8196) / var2 / var1;
            this.positionOffset += var5;
            if (this.stream(var4)) {
               AL10.alSourceQueueBuffers(this.source, this.unqueued);
            } else {
               --this.remainingBufferCount;
               if (this.remainingBufferCount == 0) {
                  this.done = true;
               }
            }
         }

         var4 = AL10.alGetSourcei(this.source, 4112);
         if (var4 != 4114) {
            AL10.alSourcePlay(this.source);
         }

      }
   }

   public boolean done() {
      return this.done;
   }

   private void removeBuffers() {
      IntBuffer var1 = BufferUtils.createIntBuffer(1);

      for(int var2 = AL10.alGetSourcei(this.source, 4117); var2 > 0; --var2) {
         AL10.alSourceUnqueueBuffers(this.source, var1);
      }

   }

   public OpenALStreamPlayer(int var1, URL var2) {
      this.source = var1;
      this.url = var2;
      this.bufferNames = BufferUtils.createIntBuffer(3);
      AL10.alGenBuffers(this.bufferNames);
   }

   public void setup(float var1) {
      this.pitch = var1;
   }

   public boolean stream(int var1) {
      try {
         int var2 = this.audio.read(this.buffer);
         if (var2 != -1) {
            this.bufferData.clear();
            this.bufferData.put(this.buffer, 0, var2);
            this.bufferData.flip();
            int var3 = this.audio.getChannels() > 1 ? 4355 : 4353;

            try {
               AL10.alBufferData(var1, var3, this.bufferData, this.audio.getRate());
            } catch (OpenALException var5) {
               Log.error(String.valueOf((new StringBuilder()).append("Failed to loop buffer: ").append(var1).append(" ").append(var3).append(" ").append(var2).append(" ").append(this.audio.getRate())), var5);
               return false;
            }
         } else {
            if (!this.loop) {
               this.done = true;
               return false;
            }

            this.initStreams();
            this.stream(var1);
         }

         return true;
      } catch (IOException var6) {
         Log.error((Throwable)var6);
         return false;
      }
   }

   private void initStreams() throws IOException {
      if (this.audio != null) {
         this.audio.close();
      }

      OggInputStream var1;
      if (this.url != null) {
         var1 = new OggInputStream(this.url.openStream());
      } else {
         var1 = new OggInputStream(ResourceLoader.getResourceAsStream(this.ref));
      }

      this.audio = var1;
      this.positionOffset = 0.0F;
   }

   public OpenALStreamPlayer(int var1, String var2) {
      this.source = var1;
      this.ref = var2;
      this.bufferNames = BufferUtils.createIntBuffer(3);
      AL10.alGenBuffers(this.bufferNames);
   }
}
