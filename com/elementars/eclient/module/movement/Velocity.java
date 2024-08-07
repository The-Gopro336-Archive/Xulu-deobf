package com.elementars.eclient.module.movement;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EntityEvent;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Module {
   // $FF: synthetic field
   private final Value horizontal = this.register(new Value("Horizontal", this, 0.0F, 0.0F, 100.0F));
   // $FF: synthetic field
   private final Value vertical = this.register(new Value("Vertical", this, 0.0F, 0.0F, 100.0F));

   @EventTarget
   public void onEntityCollision(EntityEvent.EntityCollision var1) {
      if (var1.getEntity() == mc.player) {
         if ((Float)this.horizontal.getValue() == 0.0F && (Float)this.vertical.getValue() == 0.0F) {
            var1.setCancelled(true);
            return;
         }

         var1.setX(-var1.getX() * (double)(Float)this.horizontal.getValue());
         var1.setY(0.0D);
         var1.setZ(-var1.getZ() * (double)(Float)this.horizontal.getValue());
      }

   }

   @EventTarget
   public void onPacket(EventReceivePacket var1) {
      if (mc.player != null) {
         if (var1.getPacket() instanceof SPacketEntityVelocity) {
            SPacketEntityVelocity var2 = (SPacketEntityVelocity)var1.getPacket();
            if (var2.getEntityID() == mc.player.entityId) {
               if ((Float)this.horizontal.getValue() == 0.0F && (Float)this.vertical.getValue() == 0.0F) {
                  var1.setCancelled(true);
               }

               var2.motionX = (int)((float)var2.motionX * (Float)this.horizontal.getValue());
               var2.motionY = (int)((float)var2.motionY * (Float)this.vertical.getValue());
               var2.motionZ = (int)((float)var2.motionZ * (Float)this.horizontal.getValue());
            }
         } else if (var1.getPacket() instanceof SPacketExplosion) {
            if ((Float)this.horizontal.getValue() == 0.0F && (Float)this.vertical.getValue() == 0.0F) {
               var1.setCancelled(true);
            }

            SPacketExplosion var3 = (SPacketExplosion)var1.getPacket();
            var3.motionX *= (Float)this.horizontal.getValue();
            var3.motionY *= (Float)this.vertical.getValue();
            var3.motionZ *= (Float)this.horizontal.getValue();
         }

      }
   }

   public Velocity() {
      super("Velocity", "Modifies knockback", 0, Category.MOVEMENT, true);
   }
}
