package com.elementars.eclient.macro;

import java.util.ArrayList;

public class MacroManager {
   // $FF: synthetic field
   private ArrayList macros = new ArrayList();

   public void delMacro(int var1) {
      this.macros.stream().filter((var1x) -> {
         return var1x.getKey() == var1;
      }).forEach((var1x) -> {
         this.macros.remove(var1x);
      });
   }

   public void runMacros(int var1) {
      this.macros.stream().filter((var1x) -> {
         return var1x.getKey() == var1;
      }).forEach(Macro::runMacro);
   }

   public ArrayList getMacros() {
      return this.macros;
   }

   public void addMacro(String var1, int var2) {
      this.macros.add(new Macro(var1, var2));
   }
}
