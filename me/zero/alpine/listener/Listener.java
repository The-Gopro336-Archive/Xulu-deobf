package me.zero.alpine.listener;

import java.util.function.Predicate;
import net.jodah.typetools.TypeResolver;

public final class Listener implements EventHook {
   // $FF: synthetic field
   private final byte priority;
   // $FF: synthetic field
   private final EventHook hook;
   // $FF: synthetic field
   private final Predicate[] filters;
   // $FF: synthetic field
   private final Class target;

   public final byte getPriority() {
      return this.priority;
   }

   @SafeVarargs
   public Listener(EventHook var1, byte var2, Predicate... var3) {
      this.hook = var1;
      this.priority = var2;
      this.target = TypeResolver.resolveRawArgument(EventHook.class, var1.getClass());
      this.filters = var3;
   }

   public final Class getTarget() {
      return this.target;
   }

   @SafeVarargs
   public Listener(EventHook var1, Predicate... var2) {
      this(var1, (byte)3, var2);
   }

   public final void invoke(Object var1) {
      if (this.filters.length > 0) {
         Predicate[] var2 = this.filters;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Predicate var5 = var2[var4];
            if (!var5.test(var1)) {
               return;
            }
         }
      }

      this.hook.invoke(var1);
   }
}
