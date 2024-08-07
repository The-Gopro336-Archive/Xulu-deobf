package org.newdawn.slick.muffin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.newdawn.slick.util.Log;

public class WebstartMuffin implements Muffin {
   public HashMap loadFile(String var1) throws IOException {
      HashMap var2 = new HashMap();

      try {
         if (var1.endsWith("Number")) {
         }

         return var2;
      } catch (Exception var4) {
         Log.error((Throwable)var4);
         throw new IOException("Failed to load state from webstart muffin");
      }
   }

   public void saveFile(HashMap var1, String var2) throws IOException {
      try {
         Set var4 = var1.keySet();
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            if (var2.endsWith("Number")) {
               double var7 = (Double)var1.get(var6);
            } else {
               String var10 = (String)var1.get(var6);
            }
         }

      } catch (Exception var9) {
         Log.error((Throwable)var9);
         throw new IOException("Failed to store map of state data");
      }
   }
}
