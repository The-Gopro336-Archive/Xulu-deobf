package com.elementars.eclient.event;

import java.lang.reflect.Method;

public class Data {
   // $FF: synthetic field
   public final Method target;
   // $FF: synthetic field
   public final byte priority;
   // $FF: synthetic field
   public final Object source;

   public Data(Object var1, Method var2, byte var3) {
      this.source = var1;
      this.target = var2;
      this.priority = var3;
   }
}
