package org.newdawn.slick.opengl;

import java.security.AccessController;
import java.security.PrivilegedAction;
import org.newdawn.slick.util.Log;

public class ImageDataFactory {
   // $FF: synthetic field
   private static final String PNG_LOADER = "org.newdawn.slick.pngloader";
   // $FF: synthetic field
   private static boolean pngLoaderPropertyChecked = false;
   // $FF: synthetic field
   private static boolean usePngLoader = true;

   private static void checkProperty() {
      if (!pngLoaderPropertyChecked) {
         pngLoaderPropertyChecked = true;

         try {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  String var1 = System.getProperty("org.newdawn.slick.pngloader");
                  if ("false".equalsIgnoreCase(var1)) {
                     ImageDataFactory.usePngLoader = false;
                  }

                  Log.info(String.valueOf((new StringBuilder()).append("Use Java PNG Loader = ").append(ImageDataFactory.usePngLoader)));
                  return null;
               }
            });
         } catch (Throwable var1) {
         }
      }

   }

   public static LoadableImageData getImageDataFor(String var0) {
      checkProperty();
      var0 = var0.toLowerCase();
      if (var0.endsWith(".tga")) {
         return new TGAImageData();
      } else if (var0.endsWith(".png")) {
         CompositeImageData var2 = new CompositeImageData();
         if (usePngLoader) {
            var2.add(new PNGImageData());
         }

         var2.add(new ImageIOImageData());
         return var2;
      } else {
         return new ImageIOImageData();
      }
   }
}
