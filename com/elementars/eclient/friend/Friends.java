package com.elementars.eclient.friend;

import io.netty.util.internal.ConcurrentSet;
import java.util.Iterator;

public class Friends {
   // $FF: synthetic field
   private static ConcurrentSet friends = new ConcurrentSet();

   public static Friend getFriendByName(String var0) {
      Iterator var1 = friends.iterator();

      Friend var2;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         var2 = (Friend)var1.next();
      } while(!var2.username.equalsIgnoreCase(var0));

      return var2;
   }

   public static void addFriend(String var0) {
      friends.add(new Friend(var0));
   }

   public static ConcurrentSet getFriends() {
      return friends;
   }

   public static void delFriend(String var0) {
      friends.remove(getFriendByName(var0));
   }

   public static boolean isFriend(String var0) {
      return friends.stream().anyMatch((var1) -> {
         return var1.username.equalsIgnoreCase(var0);
      });
   }
}
