package com.elementars.eclient.util;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.util.Iterator;

public class SettingLookup {
   public static Value getSettingFromMod(Module var0, String var1) {
      Iterator var2 = Xulu.VALUE_MANAGER.getValues().iterator();

      Value var3;
      do {
         if (!var2.hasNext()) {
            System.err.println(String.valueOf((new StringBuilder()).append("[Xulu] Error Setting NOT found: '").append(var1).append("'!")));
            return null;
         }

         var3 = (Value)var2.next();
      } while(!Xulu.VALUE_MANAGER.getSettingsByMod(var0).contains(var3) || !var3.getName().equals(var1));

      return var3;
   }
}
