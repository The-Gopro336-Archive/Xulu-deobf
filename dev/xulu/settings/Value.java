package dev.xulu.settings;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.Module;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Value {
   // $FF: synthetic field
   private Predicate visibleCheck = null;
   // $FF: synthetic field
   private Value.Mode mode;
   // $FF: synthetic field
   private Object value;
   // $FF: synthetic field
   private Object min;
   // $FF: synthetic field
   private Predicate filter = null;
   // $FF: synthetic field
   private Object max;
   // $FF: synthetic field
   private ArrayList options;
   // $FF: synthetic field
   private Module parent;
   // $FF: synthetic field
   private Consumer changeTask = null;
   // $FF: synthetic field
   private String name;
   // $FF: synthetic field
   private String filterError = null;

   public void setValue(Object var1) {
      if (this.value != var1) {
         if (this.filter != null && !this.filter.test(var1)) {
            if (this.filterError != null) {
               Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&c").append(this.filterError)));
            }

            return;
         }

         Object var2 = this.value;
         this.value = var1;
         if (this.changeTask != null) {
            this.changeTask.accept(new OnChangedValue(var2, var1));
         }
      }

   }

   public boolean isMode() {
      return this.mode == Value.Mode.MODE;
   }

   public Module getParentMod() {
      return this.parent;
   }

   public boolean isText() {
      return this.mode == Value.Mode.TEXT;
   }

   public void setEnumValue(String var1) {
      Enum[] var2 = (Enum[])((Enum)this.value).getClass().getEnumConstants();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Enum var5 = var2[var4];
         if (var5.name().equalsIgnoreCase(var1)) {
            Object var6 = this.value;
            this.value = var5;
            if (this.changeTask != null) {
               this.changeTask.accept(new OnChangedValue(var6, var5));
            }
         }
      }

   }

   public boolean isEnum() {
      return this.mode == Value.Mode.ENUM;
   }

   public Value onChanged(Consumer var1) {
      this.changeTask = var1;
      return this;
   }

   public Object getCorrectOption(String var1) {
      if (this.mode == Value.Mode.ENUM) {
         Iterator var2 = this.options.iterator();

         Object var3;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            var3 = var2.next();
         } while(!var3.toString().equalsIgnoreCase(var1));

         return var3;
      } else {
         return null;
      }
   }

   public ArrayList getOptions() {
      return this.options;
   }

   public String getCorrectString(String var1) {
      Iterator var2;
      if (this.value instanceof String) {
         var2 = this.options.iterator();

         String var4;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            var4 = (String)var2.next();
         } while(!var4.equalsIgnoreCase(var1));

         return var4;
      } else if (this.mode == Value.Mode.ENUM) {
         var2 = this.options.iterator();

         Object var3;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            var3 = var2.next();
         } while(!var3.toString().equalsIgnoreCase(var1));

         return Xulu.getTitle(var3.toString());
      } else {
         return null;
      }
   }

   public Value withFilterError(String var1) {
      this.filterError = var1;
      return this;
   }

   public String getName() {
      return this.name;
   }

   public Value newValueFilter(Predicate var1) {
      this.filter = var1;
      return this;
   }

   public Object getMax() {
      return this.max;
   }

   public Value(String var1, Module var2, Object var3, Object var4, Object var5) {
      this.name = var1;
      this.parent = var2;
      this.value = var3;
      this.min = var4;
      this.max = var5;
      this.mode = Value.Mode.NUMBER;
   }

   public boolean isToggle() {
      return this.mode == Value.Mode.TOGGLE;
   }

   public Value visibleWhen(Predicate var1) {
      this.visibleCheck = var1;
      return this;
   }

   public Object getMin() {
      return this.min;
   }

   public boolean isBind() {
      return this.mode == Value.Mode.BIND;
   }

   public Value(String var1, Module var2, Object var3, Object[] var4) {
      this.name = var1;
      this.parent = var2;
      this.value = var3;
      this.options = new ArrayList(Arrays.asList(var4));
      this.mode = Value.Mode.MODE;
      if (var3 instanceof Enum) {
         this.mode = Value.Mode.ENUM;
      }

   }

   public Value(String var1, Module var2, Object var3, ArrayList var4) {
      this.name = var1;
      this.parent = var2;
      this.value = var3;
      this.options = var4;
      this.mode = Value.Mode.MODE;
   }

   public Object getValue() {
      return this.value;
   }

   public boolean isVisible() {
      return this.visibleCheck == null ? true : this.visibleCheck.test(this.value);
   }

   public Value(String var1, Module var2, Object var3) {
      this.name = var1;
      this.parent = var2;
      this.value = var3;
      this.mode = Value.Mode.UNKNOWN;
      if (var3 instanceof Boolean) {
         this.mode = Value.Mode.TOGGLE;
      } else if (var3 instanceof Bind) {
         this.mode = Value.Mode.BIND;
      } else if (var3 instanceof TextBox) {
         this.mode = Value.Mode.TEXT;
      }

   }

   public boolean isNumber() {
      return this.mode == Value.Mode.NUMBER;
   }

   static enum Mode {
      // $FF: synthetic field
      BIND,
      // $FF: synthetic field
      ENUM,
      // $FF: synthetic field
      TEXT,
      // $FF: synthetic field
      MODE,
      // $FF: synthetic field
      TOGGLE,
      // $FF: synthetic field
      UNKNOWN,
      // $FF: synthetic field
      NUMBER;
   }
}
