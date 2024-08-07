package com.elementars.eclient.module.movement;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.MotionEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.util.Iterator;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.util.math.AxisAlignedBB;

public class Step extends Module {
   // $FF: synthetic field
   private final double[] twoblockPositions = new double[]{0.42D, 0.78D, 0.63D, 0.51D, 0.9D, 1.21D, 1.45D, 1.43D};
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Normal", new String[]{"Normal"}));
   // $FF: synthetic field
   private int packets;
   // $FF: synthetic field
   private final double[] onehalfblockPositions = new double[]{0.41999998688698D, 0.7531999805212D, 1.00133597911214D, 1.16610926093821D, 1.24918707874468D, 1.1707870772188D};
   // $FF: synthetic field
   private final double[] oneblockPositions = new double[]{0.41999998688698D, 0.7531999805212D};

   @EventTarget
   public void onWalkingUpdate(MotionEvent var1) {
      if (mc.player.collidedHorizontally || !((String)this.mode.getValue()).equalsIgnoreCase("Normal")) {
         if (mc.player.onGround && !mc.player.isOnLadder() && !mc.player.isInWater() && !mc.player.isInLava() && !mc.player.movementInput.jump && !mc.player.noClip) {
            if (mc.player.moveForward != 0.0F || mc.player.moveStrafing != 0.0F) {
               if (mc.player.collidedHorizontally && mc.player.onGround) {
                  ++this.packets;
               }

               double var2 = this.get_n_normal();
               if (((String)this.mode.getValue()).equalsIgnoreCase("Normal")) {
                  if (var2 < 0.0D || var2 > 2.0D) {
                     return;
                  }

                  double[] var4;
                  int var5;
                  int var6;
                  double var7;
                  if (var2 == 2.0D && this.packets > this.twoblockPositions.length - 2) {
                     var4 = this.twoblockPositions;
                     var5 = var4.length;

                     for(var6 = 0; var6 < var5; ++var6) {
                        var7 = var4[var6];
                        mc.player.connection.sendPacket(new Position(mc.player.posX, mc.player.posY + var7, mc.player.posZ, true));
                     }

                     mc.player.setPosition(mc.player.posX, mc.player.posY + 2.0D, mc.player.posZ);
                     this.packets = 0;
                  }

                  if (var2 == 1.5D && this.packets > this.onehalfblockPositions.length - 2) {
                     var4 = this.onehalfblockPositions;
                     var5 = var4.length;

                     for(var6 = 0; var6 < var5; ++var6) {
                        var7 = var4[var6];
                        mc.player.connection.sendPacket(new Position(mc.player.posX, mc.player.posY + var7, mc.player.posZ, true));
                     }

                     mc.player.setPosition(mc.player.posX, mc.player.posY + this.onehalfblockPositions[this.onehalfblockPositions.length - 1], mc.player.posZ);
                     this.packets = 0;
                  }

                  if (var2 == 1.0D && this.packets > this.oneblockPositions.length - 2) {
                     var4 = this.oneblockPositions;
                     var5 = var4.length;

                     for(var6 = 0; var6 < var5; ++var6) {
                        var7 = var4[var6];
                        mc.player.connection.sendPacket(new Position(mc.player.posX, mc.player.posY + var7, mc.player.posZ, true));
                     }

                     mc.player.setPosition(mc.player.posX, mc.player.posY + this.oneblockPositions[this.oneblockPositions.length - 1], mc.player.posZ);
                     this.packets = 0;
                  }
               }

            }
         }
      }
   }

   public double get_n_normal() {
      mc.player.stepHeight = 0.5F;
      double var1 = -1.0D;
      AxisAlignedBB var3 = mc.player.getEntityBoundingBox().offset(0.0D, 0.05D, 0.0D).grow(0.05D);
      if (!mc.world.getCollisionBoxes(mc.player, var3.offset(0.0D, 2.0D, 0.0D)).isEmpty()) {
         return 100.0D;
      } else {
         Iterator var4 = mc.world.getCollisionBoxes(mc.player, var3).iterator();

         while(var4.hasNext()) {
            AxisAlignedBB var5 = (AxisAlignedBB)var4.next();
            if (var5.maxY > var1) {
               var1 = var5.maxY;
            }
         }

         return var1 - mc.player.posY;
      }
   }

   public Step() {
      super("Step", "Step up blocks", 0, Category.MOVEMENT, true);
   }
}
