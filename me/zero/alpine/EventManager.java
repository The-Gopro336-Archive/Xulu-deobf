package me.zero.alpine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class EventManager implements EventBus {
   // $FF: synthetic field
   private final Map SUBSCRIPTION_CACHE = new HashMap();
   // $FF: synthetic field
   private final Map SUBSCRIPTION_MAP = new HashMap();
   // $FF: synthetic field
   private final List ATTACHED_BUSES = new ArrayList();

   public void subscribe(Object var1) {
      List var2 = (List)this.SUBSCRIPTION_CACHE.computeIfAbsent(var1, (var0) -> {
         return (List)Arrays.stream(var0.getClass().getDeclaredFields()).filter(EventManager::isValidField).map((var1) -> {
            return asListener(var0, var1);
         }).filter(Objects::nonNull).collect(Collectors.toList());
      });
      var2.forEach(this::subscribe);
      if (!this.ATTACHED_BUSES.isEmpty()) {
         this.ATTACHED_BUSES.forEach((var1x) -> {
            var1x.subscribe(var1);
         });
      }

   }

   public void subscribe(Object... var1) {
      Arrays.stream(var1).forEach(this::subscribe);
   }

   public void attach(EventBus var1) {
      if (!this.ATTACHED_BUSES.contains(var1)) {
         this.ATTACHED_BUSES.add(var1);
      }

   }

   public void unsubscribe(Object... var1) {
      Arrays.stream(var1).forEach(this::unsubscribe);
   }

   public void unsubscribe(Object var1) {
      List var2 = (List)this.SUBSCRIPTION_CACHE.get(var1);
      if (var2 != null) {
         this.SUBSCRIPTION_MAP.values().forEach((var1x) -> {
            Objects.requireNonNull(var2);
            var1x.removeIf(var2::contains);
         });
         if (!this.ATTACHED_BUSES.isEmpty()) {
            this.ATTACHED_BUSES.forEach((var1x) -> {
               var1x.unsubscribe(var1);
            });
         }

      }
   }

   public void post(Object var1) {
      List var2 = (List)this.SUBSCRIPTION_MAP.get(var1.getClass());
      if (var2 != null) {
         var2.forEach((var1x) -> {
            var1x.invoke(var1);
         });
      }

      if (!this.ATTACHED_BUSES.isEmpty()) {
         this.ATTACHED_BUSES.forEach((var1x) -> {
            var1x.post(var1);
         });
      }

   }

   private static boolean isValidField(Field var0) {
      return var0.isAnnotationPresent(EventHandler.class) && Listener.class.isAssignableFrom(var0.getType());
   }

   public void unsubscribe(Iterable var1) {
      var1.forEach(this::unsubscribe);
   }

   public void detach(EventBus var1) {
      if (this.ATTACHED_BUSES.contains(var1)) {
         this.ATTACHED_BUSES.remove(var1);
      }

   }

   public void subscribe(Iterable var1) {
      var1.forEach(this::subscribe);
   }

   private static Listener asListener(Object var0, Field var1) {
      try {
         boolean var2 = var1.isAccessible();
         var1.setAccessible(true);
         Listener var3 = (Listener)var1.get(var0);
         var1.setAccessible(var2);
         if (var3 == null) {
            return null;
         } else if (var3.getPriority() <= 5 && var3.getPriority() >= 1) {
            return var3;
         } else {
            throw new RuntimeException("Event Priority out of bounds! %s");
         }
      } catch (IllegalAccessException var5) {
         return null;
      }
   }

   private void subscribe(Listener var1) {
      List var2 = (List)this.SUBSCRIPTION_MAP.computeIfAbsent(var1.getTarget(), (var0) -> {
         return new ArrayList();
      });

      int var3;
      for(var3 = 0; var3 < var2.size() && var1.getPriority() >= ((Listener)var2.get(var3)).getPriority(); ++var3) {
      }

      var2.add(var3, var1);
   }
}
