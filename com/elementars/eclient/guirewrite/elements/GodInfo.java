package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.combat.AutoCrystal;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.ListHelper;
import com.elementars.eclient.util.Wrapper;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class GodInfo extends Element {
   // $FF: synthetic field
   private final Value cf = this.register(new Value("Custom Font", this, false));
   // $FF: synthetic field
   private Vec3d[] offset = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(0.0D, -1.0D, 0.0D)};

   public void onRender() {
      int var1 = mc.player.inventory.mainInventory.stream().filter((var0) -> {
         return var0.getItem() == Items.TOTEM_OF_UNDYING;
      }).mapToInt(ItemStack::func_190916_E).sum();
      if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
         ++var1;
      }

      ArrayList var2 = new ArrayList(Arrays.asList("HTR", "PLR", String.valueOf(var1), String.valueOf((new StringBuilder()).append("PING ").append(mc.getConnection() != null && mc.player != null && mc.getConnection().getPlayerInfo(mc.player.entityUniqueID) != null ? mc.getConnection().getPlayerInfo(mc.player.entityUniqueID).getResponseTime() : "-1"))));
      EntityPlayer var3 = (EntityPlayer)mc.world.playerEntities.stream().filter((var0) -> {
         return !var0.getName().equals(mc.player.getName());
      }).filter((var0) -> {
         return !Friends.isFriend(var0.getName());
      }).min(Comparator.comparing((var0) -> {
         return mc.player.getDistance(var0);
      })).orElse((Object)null);
      if (var3 != null) {
         var2.add("LBY");
      }

      this.width = (double)(fontRenderer.getStringWidth(ListHelper.longest((List)var2)) + 2);
      this.height = (double)((fontRenderer.FONT_HEIGHT + 1) * var2.size() + 1);
      float var4 = (float)this.y;

      for(Iterator var5 = var2.iterator(); var5.hasNext(); var4 += 10.0F) {
         String var6 = (String)var5.next();
         if ((Boolean)this.cf.getValue()) {
            Xulu.cFontRenderer.drawStringWithShadow(var6, (double)((float)this.x + 1.0F), (double)(var4 + 1.0F), ColorUtils.changeAlpha(this.getColor(var6, var3), (Integer)Global.hudAlpha.getValue()));
         } else {
            float var10002 = (float)this.x + 1.0F;
            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(var6, var10002, var4 + 1.0F, ColorUtils.changeAlpha(this.getColor(var6, var3), (Integer)Global.hudAlpha.getValue()));
         }
      }

   }

   private int getColor(String var1, EntityPlayer var2) {
      byte var4 = -1;
      switch(var1.hashCode()) {
      case 71878:
         if (var1.equals("HTR")) {
            var4 = 0;
         }
         break;
      case 75171:
         if (var1.equals("LBY")) {
            var4 = 2;
         }
         break;
      case 79318:
         if (var1.equals("PLR")) {
            var4 = 1;
         }
      }

      switch(var4) {
      case 0:
         if (var2 != null && mc.player.getDistance(var2) <= (Float)Xulu.VALUE_MANAGER.getValueT("Hit Range", AutoCrystal.class).getValue()) {
            return (new Color(0, 255, 0)).getRGB();
         }

         return (new Color(255, 0, 0)).getRGB();
      case 1:
         if (var2 != null && mc.player.getDistance(var2) <= (Float)Xulu.VALUE_MANAGER.getValueT("Hit Range", AutoCrystal.class).getValue()) {
            return (new Color(0, 255, 0)).getRGB();
         }

         return (new Color(255, 0, 0)).getRGB();
      case 2:
         if (var2 != null && this.checkSurround(var2)) {
            return (new Color(0, 255, 0)).getRGB();
         }

         return (new Color(255, 0, 0)).getRGB();
      default:
         int var3;
         if (var1.startsWith("PING")) {
            var3 = Integer.valueOf(var1.substring(5));
            return var3 > 100 ? (new Color(255, 0, 0)).getRGB() : (new Color(0, 255, 0)).getRGB();
         } else {
            try {
               var3 = Integer.valueOf(var1);
               return var3 > 0 ? (new Color(0, 255, 0)).getRGB() : (new Color(255, 0, 0)).getRGB();
            } catch (Exception var5) {
               var5.printStackTrace();
               return -1;
            }
         }
      }
   }

   public boolean checkSurround(EntityPlayer var1) {
      if (mc.player != null && mc.world != null) {
         boolean var2 = true;
         Vec3d[] var3 = this.offset;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Vec3d var6 = var3[var5];
            if (mc.world.getBlockState((new BlockPos(var1.getPositionVector())).add(var6.x, var6.y, var6.z)).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState((new BlockPos(var1.getPositionVector())).add(var6.x, var6.y, var6.z)).getBlock() != Blocks.BEDROCK) {
               var2 = false;
            }
         }

         return var2;
      } else {
         return false;
      }
   }

   public GodInfo() {
      super("GodInfo");
   }
}
