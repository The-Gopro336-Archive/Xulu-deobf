package org.newdawn.slick.util;

import java.io.PrintStream;
import java.util.Date;

public class DefaultLogSystem implements LogSystem {
   // $FF: synthetic field
   public static PrintStream out;

   public void info(String var1) {
      out.println(String.valueOf((new StringBuilder()).append(new Date()).append(" INFO:").append(var1)));
   }

   public void error(String var1, Throwable var2) {
      this.error(var1);
      this.error(var2);
   }

   public void warn(String var1, Throwable var2) {
      this.warn(var1);
      var2.printStackTrace(out);
   }

   public void warn(String var1) {
      out.println(String.valueOf((new StringBuilder()).append(new Date()).append(" WARN:").append(var1)));
   }

   public void error(String var1) {
      out.println(String.valueOf((new StringBuilder()).append(new Date()).append(" ERROR:").append(var1)));
   }

   public void error(Throwable var1) {
      out.println(String.valueOf((new StringBuilder()).append(new Date()).append(" ERROR:").append(var1.getMessage())));
      var1.printStackTrace(out);
   }

   static {
      out = System.out;
   }

   public void debug(String var1) {
      out.println(String.valueOf((new StringBuilder()).append(new Date()).append(" DEBUG:").append(var1)));
   }
}
