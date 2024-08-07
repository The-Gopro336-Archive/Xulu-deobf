package org.reflections.vfs;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Predicate;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;

public class UrlTypeVFS implements Vfs.UrlType {
   // $FF: synthetic field
   final String VFSZIP = "vfszip";
   // $FF: synthetic field
   final String VFSFILE = "vfsfile";
   // $FF: synthetic field
   public static final String[] REPLACE_EXTENSION = new String[]{".ear/", ".jar/", ".war/", ".sar/", ".har/", ".par/"};
   // $FF: synthetic field
   Predicate realFile = (var0) -> {
      return var0.exists() && var0.isFile();
   };

   public Vfs.Dir createDir(URL var1) {
      try {
         URL var2 = this.adaptURL(var1);
         return new ZipDir(new JarFile(var2.getFile()));
      } catch (Exception var5) {
         try {
            return new ZipDir(new JarFile(var1.getFile()));
         } catch (IOException var4) {
            if (Reflections.log != null) {
               Reflections.log.warn("Could not get URL", var5);
               Reflections.log.warn("Could not get URL", var4);
            }

            return null;
         }
      }
   }

   public URL adaptURL(URL var1) throws MalformedURLException {
      if ("vfszip".equals(var1.getProtocol())) {
         return this.replaceZipSeparators(var1.getPath(), this.realFile);
      } else {
         return "vfsfile".equals(var1.getProtocol()) ? new URL(var1.toString().replace("vfsfile", "file")) : var1;
      }
   }

   URL replaceZipSeparators(String var1, Predicate var2) throws MalformedURLException {
      int var3 = 0;

      while(var3 != -1) {
         var3 = this.findFirstMatchOfDeployableExtention(var1, var3);
         if (var3 > 0) {
            File var4 = new File(var1.substring(0, var3 - 1));
            if (var2.test(var4)) {
               return this.replaceZipSeparatorStartingFrom(var1, var3);
            }
         }
      }

      throw new ReflectionsException(String.valueOf((new StringBuilder()).append("Unable to identify the real zip file in path '").append(var1).append("'.")));
   }

   public boolean matches(URL var1) {
      return "vfszip".equals(var1.getProtocol()) || "vfsfile".equals(var1.getProtocol());
   }

   int findFirstMatchOfDeployableExtention(String var1, int var2) {
      Pattern var3 = Pattern.compile("\\.[ejprw]ar/");
      Matcher var4 = var3.matcher(var1);
      return var4.find(var2) ? var4.end() : -1;
   }

   URL replaceZipSeparatorStartingFrom(String var1, int var2) throws MalformedURLException {
      String var3 = var1.substring(0, var2 - 1);
      String var4 = var1.substring(var2);
      int var5 = 1;
      String[] var6 = REPLACE_EXTENSION;
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         for(String var9 = var6[var8]; var4.contains(var9); ++var5) {
            var4 = var4.replace(var9, String.valueOf((new StringBuilder()).append(var9.substring(0, 4)).append("!")));
         }
      }

      String var10 = "";

      for(var7 = 0; var7 < var5; ++var7) {
         var10 = String.valueOf((new StringBuilder()).append(var10).append("zip:"));
      }

      if (var4.trim().length() == 0) {
         return new URL(String.valueOf((new StringBuilder()).append(var10).append("/").append(var3)));
      } else {
         return new URL(String.valueOf((new StringBuilder()).append(var10).append("/").append(var3).append("!").append(var4)));
      }
   }
}
