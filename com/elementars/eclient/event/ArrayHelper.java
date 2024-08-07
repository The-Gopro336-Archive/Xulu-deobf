package com.elementars.eclient.event;

import java.util.Iterator;

public class ArrayHelper implements Iterable {
   // $FF: synthetic field
   private Object[] elements;

   public void clear() {
      this.elements = (Object[])(new Object[0]);
   }

   public void add(Object var1) {
      if (var1 != null) {
         Object[] var2 = new Object[this.size() + 1];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            if (var3 < this.size()) {
               var2[var3] = this.get(var3);
            } else {
               var2[var3] = var1;
            }
         }

         this.set((Object[])var2);
      }

   }

   public void set(Object[] var1) {
      this.elements = var1;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public Object get(int var1) {
      return this.array()[var1];
   }

   public void remove(Object var1) {
      if (this.contains(var1)) {
         Object[] var2 = new Object[this.size() - 1];
         boolean var3 = true;

         for(int var4 = 0; var4 < this.size(); ++var4) {
            if (var3 && this.get(var4).equals(var1)) {
               var3 = false;
            } else {
               var2[var3 ? var4 : var4 - 1] = this.get(var4);
            }
         }

         this.set((Object[])var2);
      }

   }

   public Iterator iterator() {
      return new Iterator() {
         // $FF: synthetic field
         private int index = 0;

         public Object next() {
            return ArrayHelper.this.get(this.index++);
         }

         public void remove() {
            ArrayHelper.this.remove(ArrayHelper.this.get(this.index));
         }

         public boolean hasNext() {
            return this.index < ArrayHelper.this.size() && ArrayHelper.this.get(this.index) != null;
         }
      };
   }

   public boolean contains(Object var1) {
      Object[] var2;
      int var3 = (var2 = this.array()).length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object var5 = var2[var4];
         if (var5.equals(var1)) {
            return true;
         }
      }

      return false;
   }

   public Object[] array() {
      return (Object[])this.elements;
   }

   public int size() {
      return this.array().length;
   }

   public ArrayHelper() {
      this.elements = (Object[])(new Object[0]);
   }

   public ArrayHelper(Object[] var1) {
      this.elements = var1;
   }
}
