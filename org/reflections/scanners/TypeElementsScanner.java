package org.reflections.scanners;

import java.util.Iterator;
import org.reflections.Store;
import org.reflections.util.Utils;

public class TypeElementsScanner extends AbstractScanner {
   // $FF: synthetic field
   private boolean publicOnly = true;
   // $FF: synthetic field
   private boolean includeAnnotations = true;
   // $FF: synthetic field
   private boolean includeMethods = true;
   // $FF: synthetic field
   private boolean includeFields = true;

   public TypeElementsScanner includeAnnotations() {
      return this.includeAnnotations(true);
   }

   public void scan(Object var1, Store var2) {
      String var3 = this.getMetadataAdapter().getClassName(var1);
      if (this.acceptResult(var3)) {
         this.put(var2, var3, "");
         Iterator var4;
         Object var5;
         String var6;
         if (this.includeFields) {
            var4 = this.getMetadataAdapter().getFields(var1).iterator();

            while(var4.hasNext()) {
               var5 = var4.next();
               var6 = this.getMetadataAdapter().getFieldName(var5);
               this.put(var2, var3, var6);
            }
         }

         if (this.includeMethods) {
            var4 = this.getMetadataAdapter().getMethods(var1).iterator();

            label39:
            while(true) {
               do {
                  if (!var4.hasNext()) {
                     break label39;
                  }

                  var5 = var4.next();
               } while(this.publicOnly && !this.getMetadataAdapter().isPublic(var5));

               var6 = String.valueOf((new StringBuilder()).append(this.getMetadataAdapter().getMethodName(var5)).append("(").append(Utils.join(this.getMetadataAdapter().getParameterNames(var5), ", ")).append(")"));
               this.put(var2, var3, var6);
            }
         }

         if (this.includeAnnotations) {
            var4 = this.getMetadataAdapter().getClassAnnotationNames(var1).iterator();

            while(var4.hasNext()) {
               var5 = var4.next();
               this.put(var2, var3, String.valueOf((new StringBuilder()).append("@").append(var5)));
            }
         }

      }
   }

   public TypeElementsScanner includeMethods() {
      return this.includeMethods(true);
   }

   public TypeElementsScanner includeFields(boolean var1) {
      this.includeFields = var1;
      return this;
   }

   public TypeElementsScanner publicOnly(boolean var1) {
      this.publicOnly = var1;
      return this;
   }

   public TypeElementsScanner includeAnnotations(boolean var1) {
      this.includeAnnotations = var1;
      return this;
   }

   public TypeElementsScanner publicOnly() {
      return this.publicOnly(true);
   }

   public TypeElementsScanner includeFields() {
      return this.includeFields(true);
   }

   public TypeElementsScanner includeMethods(boolean var1) {
      this.includeMethods = var1;
      return this;
   }
}
