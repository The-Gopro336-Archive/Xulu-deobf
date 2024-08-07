package com.elementars.eclient.event;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.util.Wrapper;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

public abstract class Event {
   // $FF: synthetic field
   private boolean cancelled;
   // $FF: synthetic field
   private Event.State state;
   // $FF: synthetic field
   private final float partialTicks;

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void setCancelled(boolean var1) {
      this.cancelled = var1;
   }

   private static void call(Event var0) {
      ArrayHelper var1 = Xulu.EVENT_MANAGER.get(var0.getClass());
      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            Data var3 = (Data)var2.next();

            try {
               var3.target.invoke(var3.source, var0);
            } catch (IllegalAccessException var5) {
               var5.printStackTrace();
            } catch (InvocationTargetException var6) {
               var6.printStackTrace();
            }
         }
      }

   }

   public Event.State getEventState() {
      return this.state;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public Event call() {
      this.cancelled = false;
      call(this);
      return this;
   }

   public Event() {
      this.state = Event.State.PRE;
      this.partialTicks = Wrapper.getMinecraft().getRenderPartialTicks();
   }

   public void setState(Event.State var1) {
      this.state = var1;
   }

   public static enum State {
      // $FF: synthetic field
      PRE,
      // $FF: synthetic field
      POST;
   }
}
