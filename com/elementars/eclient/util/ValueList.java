package com.elementars.eclient.util;

import dev.xulu.settings.Bind;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Iterator;

public class ValueList extends ArrayList {
   public boolean isEmpty() {
      if (super.isEmpty()) {
         return true;
      } else {
         boolean var1 = true;
         Iterator var2 = this.iterator();

         while(var2.hasNext()) {
            Value var3 = (Value)var2.next();
            if (!(var3.getValue() instanceof Bind)) {
               var1 = false;
               break;
            }
         }

         return var1;
      }
   }
}
