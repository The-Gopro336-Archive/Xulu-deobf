package org.newdawn.slick.opengl;

import java.io.IOException;
import java.io.InputStream;

public class TextureLoader {
   public static Texture getTexture(String var0, InputStream var1, int var2) throws IOException {
      return getTexture(var0, var1, false, var2);
   }

   public static Texture getTexture(String var0, InputStream var1) throws IOException {
      return getTexture(var0, var1, false, 9729);
   }

   public static Texture getTexture(String var0, InputStream var1, boolean var2, int var3) throws IOException {
      return InternalTextureLoader.get().getTexture(var1, String.valueOf((new StringBuilder()).append(var1.toString()).append(".").append(var0)), var2, var3);
   }

   public static Texture getTexture(String var0, InputStream var1, boolean var2) throws IOException {
      return getTexture(var0, var1, var2, 9729);
   }
}
