package com.elementars.eclient.module.combat;

import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.MathUtil;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class FuckedDetect extends Module {
   // $FF: synthetic field
   private final Value blue = this.register(new Value("Blue", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value green = this.register(new Value("Green", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value red = this.register(new Value("Red", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value drawOwn = this.register(new Value("Draw Own", this, false));
   // $FF: synthetic field
   public Set fuckedPlayers;
   // $FF: synthetic field
   private final Value distance = this.register(new Value("Draw Distance", this, 20, 0, 30));
   // $FF: synthetic field
   private final Value renderMode = this.register(new Value("RenderMode", this, "Solid", new ArrayList(Arrays.asList("Solid", "Flat", "Outline", "Full"))));
   // $FF: synthetic field
   private final Value alpha = this.register(new Value("Alpha", this, 70, 0, 255));
   // $FF: synthetic field
   private final Value drawFriends = this.register(new Value("Draw Friends", this, false));

   public void onWorldRender(RenderEvent var1) {
      Iterator var2 = this.fuckedPlayers.iterator();

      while(var2.hasNext()) {
         EntityPlayer var3 = (EntityPlayer)var2.next();
         this.drawBlock(new BlockPos(var3.posX, var3.posY, var3.posZ), (Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue());
      }

   }

   public FuckedDetect() {
      super("FuckedDetector", "Detects when people are fucked", 0, Category.COMBAT, true);
   }

   public static Vec3d interpolateEntity(Entity var0, float var1) {
      return new Vec3d(var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * (double)var1, var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * (double)var1, var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * (double)var1);
   }

   public void onUpdate() {
      if (!mc.player.isDead && mc.player != null && this.isToggled()) {
         this.getList();
      }
   }

   private void drawBlock(BlockPos var1, int var2, int var3, int var4) {
      if (((String)this.renderMode.getValue()).equalsIgnoreCase("Solid")) {
         XuluTessellator.prepare(7);
         XuluTessellator.drawBox(var1, var2, var3, var4, (Integer)this.alpha.getValue(), 63);
         XuluTessellator.release();
      } else if (((String)this.renderMode.getValue()).equalsIgnoreCase("Flat")) {
         XuluTessellator.prepare(7);
         XuluTessellator.drawBox(var1, var2, var3, var4, (Integer)this.alpha.getValue(), 1);
         XuluTessellator.release();
      } else {
         IBlockState var5;
         Vec3d var6;
         if (((String)this.renderMode.getValue()).equalsIgnoreCase("Outline")) {
            var5 = mc.world.getBlockState(var1);
            var6 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
            XuluTessellator.drawBoundingBox(var5.getSelectedBoundingBox(mc.world, var1).grow(0.0020000000949949026D).offset(-var6.x, -var6.y, -var6.z), 1.5F, var2, var3, var4, (Integer)this.alpha.getValue());
         } else if (((String)this.renderMode.getValue()).equalsIgnoreCase("Full")) {
            var5 = mc.world.getBlockState(var1);
            var6 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
            XuluTessellator.drawFullBox(var5.getSelectedBoundingBox(mc.world, var1).grow(0.0020000000949949026D).offset(-var6.x, -var6.y, -var6.z), var1, 1.5F, var2, var3, var4, (Integer)this.alpha.getValue(), 255);
         }
      }

   }

   private boolean canPlaceCrystal(BlockPos var1) {
      BlockPos var2 = var1.add(0, 1, 0);
      BlockPos var3 = var1.add(0, 2, 0);
      return (mc.world.getBlockState(var1).getBlock() == Blocks.BEDROCK || mc.world.getBlockState(var1).getBlock() == Blocks.OBSIDIAN) && mc.world.getBlockState(var2).getBlock() == Blocks.AIR && mc.world.getBlockState(var3).getBlock() == Blocks.AIR && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(var2)).isEmpty() && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(var3)).isEmpty();
   }

   public Set getList() {
      this.fuckedPlayers.clear();
      Iterator var1 = mc.world.playerEntities.iterator();

      while(true) {
         EntityPlayer var2;
         do {
            do {
               do {
                  do {
                     do {
                        if (!var1.hasNext()) {
                           return this.fuckedPlayers;
                        }

                        var2 = (EntityPlayer)var1.next();
                     } while(!EntityUtil.isLiving(var2));
                  } while(var2.getHealth() <= 0.0F);
               } while(!this.checkHole(var2));
            } while(!(Boolean)this.drawOwn.getValue() && var2.getName() == mc.player.getName());
         } while(!(Boolean)this.drawFriends.getValue() && Friends.isFriend(var2.getName()));

         if (!(mc.player.getDistance(var2) > (float)(Integer)this.distance.getValue())) {
            this.fuckedPlayers.add(var2);
         }
      }
   }

   public Boolean checkHole(EntityPlayer var1) {
      BlockPos var2 = new BlockPos(var1.posX, var1.posY - 1.0D, var1.posZ);
      if (mc.world.getBlockState(var2).getBlock() == Blocks.AIR) {
         return false;
      } else if (this.canPlaceCrystal(var2.south()) || this.canPlaceCrystal(var2.south().south()) && mc.world.getBlockState(var2.add(0, 1, 1)).getBlock() == Blocks.AIR) {
         return true;
      } else if (this.canPlaceCrystal(var2.east()) || this.canPlaceCrystal(var2.east().east()) && mc.world.getBlockState(var2.add(1, 1, 0)).getBlock() == Blocks.AIR) {
         return true;
      } else {
         return this.canPlaceCrystal(var2.west()) || this.canPlaceCrystal(var2.west().west()) && mc.world.getBlockState(var2.add(-1, 1, 0)).getBlock() == Blocks.AIR ? true : this.canPlaceCrystal(var2.north()) || this.canPlaceCrystal(var2.north().north()) && mc.world.getBlockState(var2.add(0, 1, -1)).getBlock() == Blocks.AIR;
      }
   }

   public void onEnable() {
      this.fuckedPlayers = new HashSet();
   }
}
