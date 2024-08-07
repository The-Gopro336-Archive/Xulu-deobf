package org.newdawn.slick.util;

import java.io.InputStream;
import java.net.URL;

public class ClasspathLocation implements ResourceLocation {
   public URL getResource(String var1) {
      String var2 = var1.replace('\\', '/');
      return ResourceLoader.class.getClassLoader().getResource(var2);
   }

   public InputStream getResourceAsStream(String var1) {
      String var2 = var1.replace('\\', '/');
      return ResourceLoader.class.getClassLoader().getResourceAsStream(var2);
   }
}
