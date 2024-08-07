package org.newdawn.slick.openal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AudioLoader {
   // $FF: synthetic field
   private static boolean inited = false;
   // $FF: synthetic field
   private static final String MOD = "MOD";
   // $FF: synthetic field
   private static final String WAV = "WAV";
   // $FF: synthetic field
   private static final String OGG = "OGG";
   // $FF: synthetic field
   private static final String XM = "XM";
   // $FF: synthetic field
   private static final String AIF = "AIF";

   public static void update() {
      init();
      SoundStore.get().poll(0);
   }

   public static Audio getStreamingAudio(String var0, URL var1) throws IOException {
      init();
      if (var0.equals("OGG")) {
         return SoundStore.get().getOggStream(var1);
      } else if (var0.equals("MOD")) {
         return SoundStore.get().getMOD(var1.openStream());
      } else if (var0.equals("XM")) {
         return SoundStore.get().getMOD(var1.openStream());
      } else {
         throw new IOException(String.valueOf((new StringBuilder()).append("Unsupported format for streaming Audio: ").append(var0)));
      }
   }

   private static void init() {
      if (!inited) {
         SoundStore.get().init();
         inited = true;
      }

   }

   public static Audio getAudio(String var0, InputStream var1) throws IOException {
      init();
      if (var0.equals("AIF")) {
         return SoundStore.get().getAIF(var1);
      } else if (var0.equals("WAV")) {
         return SoundStore.get().getWAV(var1);
      } else if (var0.equals("OGG")) {
         return SoundStore.get().getOgg(var1);
      } else {
         throw new IOException(String.valueOf((new StringBuilder()).append("Unsupported format for non-streaming Audio: ").append(var0)));
      }
   }
}
