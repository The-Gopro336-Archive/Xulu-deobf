package org.newdawn.slick.loading;

import java.util.ArrayList;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.util.Log;

public class LoadingList {
   // $FF: synthetic field
   private int total;
   // $FF: synthetic field
   private ArrayList deferred = new ArrayList();
   // $FF: synthetic field
   private static LoadingList single = new LoadingList();

   public void remove(DeferredResource var1) {
      Log.info(String.valueOf((new StringBuilder()).append("Early loading of deferred resource due to req: ").append(var1.getDescription())));
      --this.total;
      this.deferred.remove(var1);
   }

   public static LoadingList get() {
      return single;
   }

   public static void setDeferredLoading(boolean var0) {
      single = new LoadingList();
      InternalTextureLoader.get().setDeferredLoading(var0);
      SoundStore.get().setDeferredLoading(var0);
   }

   public int getTotalResources() {
      return this.total;
   }

   private LoadingList() {
   }

   public int getRemainingResources() {
      return this.deferred.size();
   }

   public DeferredResource getNext() {
      return this.deferred.size() == 0 ? null : (DeferredResource)this.deferred.remove(0);
   }

   public static boolean isDeferredLoading() {
      return InternalTextureLoader.get().isDeferredLoading();
   }

   public void add(DeferredResource var1) {
      ++this.total;
      this.deferred.add(var1);
   }
}
