package org.reflections.scanners;

import java.util.function.Predicate;
import org.reflections.Configuration;
import org.reflections.ReflectionsException;
import org.reflections.Store;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.util.Utils;
import org.reflections.vfs.Vfs;

public abstract class AbstractScanner implements Scanner {
   // $FF: synthetic field
   private Configuration configuration;
   // $FF: synthetic field
   private Predicate resultFilter = (var0) -> {
      return true;
   };

   public int hashCode() {
      return this.getClass().hashCode();
   }

   protected void put(Store var1, String var2, String var3) {
      var1.put(Utils.index(this.getClass()), var2, var3);
   }

   public boolean acceptResult(String var1) {
      return var1 != null && this.resultFilter.test(var1);
   }

   public void setConfiguration(Configuration var1) {
      this.configuration = var1;
   }

   public abstract void scan(Object var1, Store var2);

   public Predicate getResultFilter() {
      return this.resultFilter;
   }

   public boolean equals(Object var1) {
      return this == var1 || var1 != null && this.getClass() == var1.getClass();
   }

   protected MetadataAdapter getMetadataAdapter() {
      return this.configuration.getMetadataAdapter();
   }

   public void setResultFilter(Predicate var1) {
      this.resultFilter = var1;
   }

   public Object scan(Vfs.File var1, Object var2, Store var3) {
      if (var2 == null) {
         try {
            var2 = this.configuration.getMetadataAdapter().getOrCreateClassObject(var1);
         } catch (Exception var5) {
            throw new ReflectionsException(String.valueOf((new StringBuilder()).append("could not create class object from file ").append(var1.getRelativePath())), var5);
         }
      }

      this.scan(var2, var3);
      return var2;
   }

   public boolean acceptsInput(String var1) {
      return this.getMetadataAdapter().acceptsInput(var1);
   }

   public Configuration getConfiguration() {
      return this.configuration;
   }

   public Scanner filterResultsBy(Predicate var1) {
      this.setResultFilter(var1);
      return this;
   }
}
