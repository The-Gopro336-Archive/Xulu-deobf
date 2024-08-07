package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;

public class RenderEvent extends Event {
   // $FF: synthetic field
   private final Vec3d renderPos;
   // $FF: synthetic field
   private final Tessellator tessellator;

   public void resetTranslation() {
      this.setTranslation(this.renderPos);
   }

   public BufferBuilder getBuffer() {
      return this.tessellator.getBuffer();
   }

   public RenderEvent(Tessellator var1, Vec3d var2) {
      this.tessellator = var1;
      this.renderPos = var2;
   }

   public Vec3d getRenderPos() {
      return this.renderPos;
   }

   public Tessellator getTessellator() {
      return this.tessellator;
   }

   public void setTranslation(Vec3d var1) {
      this.getBuffer().setTranslation(-var1.x, -var1.y, -var1.z);
   }
}
