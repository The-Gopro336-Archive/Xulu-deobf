package com.elementars.eclient.cape;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Capes {
   // $FF: synthetic field
   public static ArrayList lines = new ArrayList();

   public static boolean isCapeUser(String var0) {
      return lines.contains(var0);
   }

   public static void getUsersCape() {
      try {
         URL var0 = new URL("https://www.pastebin.com/raw/MiWJDQRF");
         BufferedReader var1 = new BufferedReader(new InputStreamReader(var0.openStream()));

         String var2;
         while((var2 = var1.readLine()) != null) {
            lines.add(var2);
         }

         var1.close();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }
}
