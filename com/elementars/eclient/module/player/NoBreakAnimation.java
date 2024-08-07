package com.elementars.eclient.module.player;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class NoBreakAnimation extends Module {
   // $FF: synthetic field
   private EnumFacing lastFacing = null;
   // $FF: synthetic field
   private BlockPos lastPos = null;
   // $FF: synthetic field
   private boolean isMining = false;

   @EventTarget
   public void onSend(EventSendPacket var1) {
      if (var1.getPacket() instanceof CPacketPlayerDigging) {
         CPacketPlayerDigging var2 = (CPacketPlayerDigging)var1.getPacket();
         Iterator var3 = mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(var2.getPosition())).iterator();

         while(var3.hasNext()) {
            Entity var4 = (Entity)var3.next();
            if (var4 instanceof EntityEnderCrystal) {
               this.resetMining();
               return;
            }

            if (var4 instanceof EntityLivingBase) {
               this.resetMining();
               return;
            }
         }

         if (var2.getAction().equals(Action.START_DESTROY_BLOCK)) {
            this.isMining = true;
            this.setMiningInfo(var2.getPosition(), var2.getFacing());
         }

         if (var2.getAction().equals(Action.STOP_DESTROY_BLOCK)) {
            this.resetMining();
         }
      }

   }

   public NoBreakAnimation() {
      super("NoBreakAnimation", "Prevents block break animation server side", 0, Category.PLAYER, true);
   }

   private void setMiningInfo(BlockPos var1, EnumFacing var2) {
      this.lastPos = var1;
      this.lastFacing = var2;
   }

   public void onUpdate() {
      if (!mc.gameSettings.keyBindAttack.isKeyDown()) {
         this.resetMining();
      } else {
         if (this.isMining && this.lastPos != null && this.lastFacing != null) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.ABORT_DESTROY_BLOCK, this.lastPos, this.lastFacing));
         }

      }
   }

   public void resetMining() {
      this.isMining = false;
      this.lastPos = null;
      this.lastFacing = null;
   }
}
