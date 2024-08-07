package org.reflections.vfs;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import org.reflections.ReflectionsException;
import org.reflections.util.Utils;

public class JarInputDir implements Vfs.Dir {
   // $FF: synthetic field
   long nextCursor = 0L;
   // $FF: synthetic field
   private final URL url;
   // $FF: synthetic field
   long cursor = 0L;
   // $FF: synthetic field
   JarInputStream jarInputStream;

   public void close() {
      Utils.close(this.jarInputStream);
   }

   public String getPath() {
      return this.url.getPath();
   }

   public JarInputDir(URL var1) {
      this.url = var1;
   }

   public Iterable getFiles() {
      return () -> {
         return new Iterator() {
            // $FF: synthetic field
            Vfs.File entry;

            {
               try {
                  JarInputDir.this.jarInputStream = new JarInputStream(JarInputDir.this.url.openConnection().getInputStream());
               } catch (Exception var3) {
                  throw new ReflectionsException("Could not open url connection", var3);
               }

               this.entry = null;
            }

            public Vfs.File next() {
               Vfs.File var1 = this.entry;
               this.entry = null;
               return var1;
            }

            private Vfs.File computeNext() {
               while(true) {
                  try {
                     JarEntry var1 = JarInputDir.this.jarInputStream.getNextJarEntry();
                     if (var1 == null) {
                        return null;
                     }

                     long var2 = var1.getSize();
                     if (var2 < 0L) {
                        var2 += 4294967295L;
                     }

                     JarInputDir var10000 = JarInputDir.this;
                     var10000.nextCursor += var2;
                     if (!var1.isDirectory()) {
                        return new JarInputFile(var1, JarInputDir.this, JarInputDir.this.cursor, JarInputDir.this.nextCursor);
                     }
                  } catch (IOException var4) {
                     throw new ReflectionsException("could not get next zip entry", var4);
                  }
               }
            }

            public boolean hasNext() {
               return this.entry != null || (this.entry = this.computeNext()) != null;
            }
         };
      };
   }
}
