package org.reflections.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.adapters.JavaReflectionAdapter;
import org.reflections.adapters.JavassistAdapter;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.serializers.Serializer;
import org.reflections.serializers.XmlSerializer;

public class ConfigurationBuilder implements Configuration {
   // $FF: synthetic field
   private ClassLoader[] classLoaders;
   // $FF: synthetic field
   private boolean expandSuperTypes = true;
   // $FF: synthetic field
   protected MetadataAdapter metadataAdapter;
   // $FF: synthetic field
   private Set urls = new HashSet();
   // $FF: synthetic field
   private Predicate inputsFilter;
   // $FF: synthetic field
   private Serializer serializer;
   // $FF: synthetic field
   private ExecutorService executorService;
   // $FF: synthetic field
   private Set scanners = new HashSet(Arrays.asList(new TypeAnnotationsScanner(), new SubTypesScanner()));

   public Set getUrls() {
      return this.urls;
   }

   public static ConfigurationBuilder build(Object... var0) {
      ConfigurationBuilder var1 = new ConfigurationBuilder();
      ArrayList var2 = new ArrayList();
      Iterator var7;
      Object var8;
      if (var0 != null) {
         Object[] var3 = var0;
         int var4 = var0.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object var6 = var3[var5];
            if (var6 != null) {
               if (var6.getClass().isArray()) {
                  Object[] var18 = (Object[])((Object[])var6);
                  int var19 = var18.length;

                  for(int var9 = 0; var9 < var19; ++var9) {
                     Object var10 = var18[var9];
                     if (var10 != null) {
                        var2.add(var10);
                     }
                  }
               } else if (var6 instanceof Iterable) {
                  var7 = ((Iterable)var6).iterator();

                  while(var7.hasNext()) {
                     var8 = var7.next();
                     if (var8 != null) {
                        var2.add(var8);
                     }
                  }
               } else {
                  var2.add(var6);
               }
            }
         }
      }

      ArrayList var12 = new ArrayList();
      Iterator var13 = var2.iterator();

      while(var13.hasNext()) {
         Object var15 = var13.next();
         if (var15 instanceof ClassLoader) {
            var12.add((ClassLoader)var15);
         }
      }

      ClassLoader[] var14 = var12.isEmpty() ? null : (ClassLoader[])var12.toArray(new ClassLoader[var12.size()]);
      FilterBuilder var16 = new FilterBuilder();
      ArrayList var17 = new ArrayList();
      var7 = var2.iterator();

      while(var7.hasNext()) {
         var8 = var7.next();
         if (var8 instanceof String) {
            var1.addUrls(ClasspathHelper.forPackage((String)var8, var14));
            var16.includePackage((String)var8);
         } else if (var8 instanceof Class) {
            if (Scanner.class.isAssignableFrom((Class)var8)) {
               try {
                  var1.addScanners((Scanner)((Class)var8).newInstance());
               } catch (Exception var11) {
               }
            }

            var1.addUrls(ClasspathHelper.forClass((Class)var8, var14));
            var16.includePackage((Class)var8);
         } else if (var8 instanceof Scanner) {
            var17.add((Scanner)var8);
         } else if (var8 instanceof URL) {
            var1.addUrls((URL)var8);
         } else if (!(var8 instanceof ClassLoader)) {
            if (var8 instanceof Predicate) {
               var16.add((Predicate)var8);
            } else if (var8 instanceof ExecutorService) {
               var1.setExecutorService((ExecutorService)var8);
            } else if (Reflections.log != null) {
               throw new ReflectionsException(String.valueOf((new StringBuilder()).append("could not use param ").append(var8)));
            }
         }
      }

      if (var1.getUrls().isEmpty()) {
         if (var14 != null) {
            var1.addUrls(ClasspathHelper.forClassLoader(var14));
         } else {
            var1.addUrls(ClasspathHelper.forClassLoader());
         }
      }

      var1.filterInputsBy(var16);
      if (!var17.isEmpty()) {
         var1.setScanners((Scanner[])var17.toArray(new Scanner[var17.size()]));
      }

      if (!var12.isEmpty()) {
         var1.addClassLoaders((Collection)var12);
      }

      return var1;
   }

   public ConfigurationBuilder setSerializer(Serializer var1) {
      this.serializer = var1;
      return this;
   }

   public boolean shouldExpandSuperTypes() {
      return this.expandSuperTypes;
   }

   public ConfigurationBuilder addUrls(Collection var1) {
      this.urls.addAll(var1);
      return this;
   }

   public ConfigurationBuilder useParallelExecutor(int var1) {
      ThreadFactory var2 = new ThreadFactory() {
         // $FF: synthetic field
         private final AtomicInteger threadNumber = new AtomicInteger(1);

         public Thread newThread(Runnable var1) {
            Thread var2 = new Thread(var1);
            var2.setName(String.valueOf((new StringBuilder()).append("org.reflections-scanner-").append(this.threadNumber.getAndIncrement())));
            var2.setDaemon(true);
            return var2;
         }
      };
      this.setExecutorService(Executors.newFixedThreadPool(var1, var2));
      return this;
   }

   public Predicate getInputsFilter() {
      return this.inputsFilter;
   }

   public ConfigurationBuilder setExecutorService(ExecutorService var1) {
      this.executorService = var1;
      return this;
   }

   public ConfigurationBuilder setScanners(Scanner... var1) {
      this.scanners.clear();
      return this.addScanners(var1);
   }

   public ConfigurationBuilder setMetadataAdapter(MetadataAdapter var1) {
      this.metadataAdapter = var1;
      return this;
   }

   public ConfigurationBuilder setExpandSuperTypes(boolean var1) {
      this.expandSuperTypes = var1;
      return this;
   }

   public ConfigurationBuilder addClassLoaders(Collection var1) {
      return this.addClassLoaders((ClassLoader[])var1.toArray(new ClassLoader[var1.size()]));
   }

   public Set getScanners() {
      return this.scanners;
   }

   public void setClassLoaders(ClassLoader[] var1) {
      this.classLoaders = var1;
   }

   public ConfigurationBuilder filterInputsBy(Predicate var1) {
      this.inputsFilter = var1;
      return this;
   }

   public ExecutorService getExecutorService() {
      return this.executorService;
   }

   public ConfigurationBuilder addClassLoaders(ClassLoader... var1) {
      this.classLoaders = this.classLoaders == null ? var1 : (ClassLoader[])Stream.concat(Stream.concat(Arrays.stream(this.classLoaders), Arrays.stream(var1)), Stream.of(ClassLoader.class)).distinct().toArray((var0) -> {
         return new ClassLoader[var0];
      });
      return this;
   }

   public ConfigurationBuilder setUrls(URL... var1) {
      this.urls = new HashSet(Arrays.asList(var1));
      return this;
   }

   public ConfigurationBuilder forPackages(String... var1) {
      String[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         this.addUrls(ClasspathHelper.forPackage(var5));
      }

      return this;
   }

   public void setInputsFilter(Predicate var1) {
      this.inputsFilter = var1;
   }

   public ConfigurationBuilder useParallelExecutor() {
      return this.useParallelExecutor(Runtime.getRuntime().availableProcessors());
   }

   public Serializer getSerializer() {
      return this.serializer != null ? this.serializer : (this.serializer = new XmlSerializer());
   }

   public ConfigurationBuilder addScanners(Scanner... var1) {
      this.scanners.addAll(Arrays.asList(var1));
      return this;
   }

   public ClassLoader[] getClassLoaders() {
      return this.classLoaders;
   }

   public ConfigurationBuilder addUrls(URL... var1) {
      this.urls.addAll(new HashSet(Arrays.asList(var1)));
      return this;
   }

   public ConfigurationBuilder setUrls(Collection var1) {
      this.urls = new HashSet(var1);
      return this;
   }

   public ConfigurationBuilder addClassLoader(ClassLoader var1) {
      return this.addClassLoaders(var1);
   }

   public MetadataAdapter getMetadataAdapter() {
      if (this.metadataAdapter != null) {
         return this.metadataAdapter;
      } else {
         try {
            return this.metadataAdapter = new JavassistAdapter();
         } catch (Throwable var3) {
            if (Reflections.log != null) {
               Reflections.log.warn("could not create JavassistAdapter, using JavaReflectionAdapter", var3);
            }

            return this.metadataAdapter = new JavaReflectionAdapter();
         }
      }
   }
}
