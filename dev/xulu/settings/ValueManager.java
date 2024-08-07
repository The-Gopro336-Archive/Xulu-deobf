package dev.xulu.settings;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ValueList;
import java.util.ArrayList;
import java.util.Iterator;

public class ValueManager {
   // $FF: synthetic field
   private ArrayList values = new ArrayList();

   public Value getValueByMod(Module var1, String var2) {
      Iterator var3 = this.getValues().iterator();

      Value var4;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         var4 = (Value)var3.next();
      } while(!var4.getParentMod().equals(var1) || !var4.getName().equalsIgnoreCase(var2));

      return var4;
   }

   public Value register(Value var1) {
      this.values.add(var1);
      return var1;
   }

   public ArrayList getValuesByMod(Module var1) {
      ValueList var2 = new ValueList();
      Iterator var3 = this.getValues().iterator();

      while(var3.hasNext()) {
         Value var4 = (Value)var3.next();
         if (var4.getParentMod().equals(var1)) {
            var2.add(var4);
         }
      }

      if (var2.isEmpty()) {
         return null;
      } else {
         return var2;
      }
   }

   public Value getValueT(String var1, Class var2) {
      Iterator var3 = this.getValues().iterator();

      Value var4;
      do {
         if (!var3.hasNext()) {
            System.err.println(String.valueOf((new StringBuilder()).append("[Xulu] Error Setting NOT found: '").append(var1).append("'!")));
            return null;
         }

         var4 = (Value)var3.next();
      } while(!var4.getName().equalsIgnoreCase(var1) || !var4.getParentMod().equals(Xulu.MODULE_MANAGER.getModule(var2)));

      return var4;
   }

   public ArrayList getSettingsByMod(Module var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.getValues().iterator();

      while(var3.hasNext()) {
         Value var4 = (Value)var3.next();
         if (var4.getParentMod().equals(var1)) {
            var2.add(var4);
         }
      }

      if (var2.isEmpty()) {
         return null;
      } else {
         return var2;
      }
   }

   public Value getValueByName(String var1) {
      Iterator var2 = this.getValues().iterator();

      Value var3;
      do {
         if (!var2.hasNext()) {
            System.err.println(String.valueOf((new StringBuilder()).append("[Xulu] Error Setting NOT found: '").append(var1).append("'!")));
            return null;
         }

         var3 = (Value)var2.next();
      } while(!var3.getName().equalsIgnoreCase(var1));

      return var3;
   }

   public ArrayList getValues() {
      return this.values;
   }
}
