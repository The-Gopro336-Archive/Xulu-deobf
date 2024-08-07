package com.elementars.eclient.util;

import com.elementars.eclient.module.render.LogoutSpots;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Predicate;

public class SpotSet extends HashSet {
   public Pair removeIfReturn(Predicate var1) {
      HashSet var2 = new HashSet(this);
      boolean var3 = this.removeIf(var1);
      if (!var3) {
         return new Pair(false, (Object)null);
      } else {
         LogoutSpots.LogoutPos var4 = null;
         Iterator var5 = var2.iterator();

         while(var5.hasNext()) {
            LogoutSpots.LogoutPos var6 = (LogoutSpots.LogoutPos)var5.next();
            if (!this.contains(var6)) {
               var4 = var6;
               break;
            }
         }

         return new Pair(true, var4);
      }
   }
}
