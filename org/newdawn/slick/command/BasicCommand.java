package org.newdawn.slick.command;

public class BasicCommand implements Command {
   // $FF: synthetic field
   private String name;

   public boolean equals(Object var1) {
      return var1 instanceof BasicCommand ? ((BasicCommand)var1).name.equals(this.name) : false;
   }

   public BasicCommand(String var1) {
      this.name = var1;
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public String toString() {
      return String.valueOf((new StringBuilder()).append("[Command=").append(this.name).append("]"));
   }

   public String getName() {
      return this.name;
   }
}
