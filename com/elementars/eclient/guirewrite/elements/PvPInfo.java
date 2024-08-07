package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.ListHelper;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PvPInfo extends Element {
   // $FF: synthetic field
   private Vec3d[] offset = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(0.0D, -1.0D, 0.0D)};
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));
   // $FF: synthetic field
   public static boolean place = false;
   // $FF: synthetic field
   public static boolean attack = false;
   // $FF: synthetic field
   public static boolean surround = false;

   public PvPInfo() {
      super("PvPInfo");
   }

   public String getFromBoolean(boolean var1) {
      return var1 ? String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("TRUE")) : String.valueOf((new StringBuilder()).append(ChatFormatting.RED).append("FALSE"));
   }

   private String getCaura() {
      boolean var1 = false;
      if (Xulu.MODULE_MANAGER.getModuleByName("AutoCrystal") != null) {
         var1 = Xulu.MODULE_MANAGER.getModuleByName("AutoCrystal").isToggled();
         if (var1) {
            return String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("ON"));
         }
      }

      if (Xulu.MODULE_MANAGER.getModuleByName("AutoCrystalO") != null) {
         var1 = Xulu.MODULE_MANAGER.getModuleByName("AutoCrystalO").isToggled();
         if (var1) {
            return String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("ON"));
         }
      }

      if (Xulu.MODULE_MANAGER.getModuleByName("AutoCrystalX") != null) {
         var1 = Xulu.MODULE_MANAGER.getModuleByName("AutoCrystalX").isToggled();
         if (var1) {
            return String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("ON"));
         }
      }

      return String.valueOf((new StringBuilder()).append(ChatFormatting.RED).append("OFF"));
   }

   public void onRender() {
      this.checkSurround();
      float var1 = (float)this.y;
      int var2 = ColorUtil.getClickGUIColor().getRGB();
      int var3 = mc.player.inventory.mainInventory.stream().filter((var0) -> {
         return var0.getItem() == Items.TOTEM_OF_UNDYING;
      }).mapToInt(ItemStack::func_190916_E).sum();
      if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
         ++var3;
      }

      String[] var4 = new String[]{String.valueOf((new StringBuilder()).append("ATT: ").append(this.getFromBoolean(attack))), String.valueOf((new StringBuilder()).append("PLC: ").append(this.getFromBoolean(place))), String.valueOf((new StringBuilder()).append("FOB: ").append(this.getFromBoolean(surround))), String.valueOf((new StringBuilder()).append("PING: ").append(this.getPing((long)(mc.getConnection() != null && mc.player != null && mc.getConnection().getPlayerInfo(mc.player.entityUniqueID) != null ? mc.getConnection().getPlayerInfo(mc.player.entityUniqueID).getResponseTime() : -1)))), String.valueOf((new StringBuilder()).append("TOTEMS: ").append(this.getTotems(var3))), String.valueOf((new StringBuilder()).append("AT: ").append(this.getAutoTrap())), String.valueOf((new StringBuilder()).append("SU: ").append(this.getSurround())), String.valueOf((new StringBuilder()).append("CA: ").append(this.getCaura()))};
      this.width = (double)(fontRenderer.getStringWidth(ListHelper.longest(var4)) + 2);
      this.height = (double)((fontRenderer.FONT_HEIGHT + 1) * var4.length + 1);
      if ((Boolean)this.rainbow.getValue()) {
         var2 = Xulu.rgb;
      }

      String[] var5;
      int var6;
      int var7;
      String var8;
      if (Xulu.CustomFont) {
         var5 = var4;
         var6 = var4.length;

         for(var7 = 0; var7 < var6; ++var7) {
            var8 = var5[var7];
            Xulu.cFontRenderer.drawStringWithShadow(var8, this.x + 1.0D, (double)(var1 + 1.0F), ColorUtils.changeAlpha(var2, (Integer)Global.hudAlpha.getValue()));
            var1 += 10.0F;
         }
      } else {
         var5 = var4;
         var6 = var4.length;

         for(var7 = 0; var7 < var6; ++var7) {
            var8 = var5[var7];
            fontRenderer.drawStringWithShadow(var8, (float)this.x + 1.0F, var1 + 1.0F, ColorUtils.changeAlpha(var2, (Integer)Global.hudAlpha.getValue()));
            var1 += 10.0F;
         }
      }

   }

   private String getPing(long var1) {
      return var1 > 100L ? String.valueOf((new StringBuilder()).append("").append(ChatFormatting.RED).append(var1)) : String.valueOf((new StringBuilder()).append("").append(ChatFormatting.GREEN).append(var1));
   }

   private String getTotems(int var1) {
      return var1 > 0 ? String.valueOf((new StringBuilder()).append("").append(ChatFormatting.GREEN).append(var1)) : String.valueOf((new StringBuilder()).append("").append(ChatFormatting.RED).append(var1));
   }

   private String getAutoTrap() {
      if (Xulu.MODULE_MANAGER.getModuleByName("AutoTrap") != null) {
         return Xulu.MODULE_MANAGER.getModuleByName("AutoTrap").isToggled() ? String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("ON")) : String.valueOf((new StringBuilder()).append(ChatFormatting.RED).append("OFF"));
      } else {
         return "NULL";
      }
   }

   public void checkSurround() {
      if (mc.player != null && mc.world != null) {
         boolean var1 = true;
         Vec3d[] var2 = this.offset;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Vec3d var5 = var2[var4];
            if (mc.world.getBlockState((new BlockPos(mc.player.getPositionVector())).add(var5.x, var5.y, var5.z)).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState((new BlockPos(mc.player.getPositionVector())).add(var5.x, var5.y, var5.z)).getBlock() != Blocks.BEDROCK) {
               var1 = false;
            }
         }

         surround = var1;
      }
   }

   private String getSurround() {
      if (Xulu.MODULE_MANAGER.getModuleByName("Surround") != null) {
         return Xulu.MODULE_MANAGER.getModuleByName("Surround").isToggled() ? String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("ON")) : String.valueOf((new StringBuilder()).append(ChatFormatting.RED).append("OFF"));
      } else {
         return "NULL";
      }
   }
}
