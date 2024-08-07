package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorTextUtils;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.LagCompensator;
import com.elementars.eclient.util.PotionUtil;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;

public class Info extends Element {
   // $FF: synthetic field
   private final Value POTIONS;
   // $FF: synthetic field
   private final Value TPS;
   // $FF: synthetic field
   private final Value DURABILITY;
   // $FF: synthetic field
   DecimalFormat df;
   // $FF: synthetic field
   private final Value color2;
   // $FF: synthetic field
   private final Value TIME;
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Aligned", this, "Right", new String[]{"Right", "Left"}));
   // $FF: synthetic field
   private final Value SERVER_IP;
   // $FF: synthetic field
   private final Value t24 = this.register(new Value("24hr time", this, false));
   // $FF: synthetic field
   private final Value rainbow = this.register(new Value("Rainbow", this, false));
   // $FF: synthetic field
   private final Value FPS;
   // $FF: synthetic field
   private final Value PING;
   // $FF: synthetic field
   DecimalFormat df2;
   // $FF: synthetic field
   private final Value order = this.register(new Value("Ordering", this, "Down", new String[]{"Up", "Down"}));
   // $FF: synthetic field
   private final Value ALPHABETICAL;
   // $FF: synthetic field
   private final Value SPEED;

   public void onRender() {
      float var1 = (float)this.y;
      int var2 = ColorUtil.getClickGUIColor().getRGB();
      if ((Boolean)this.rainbow.getValue()) {
         var2 = Xulu.rgb;
      }

      float var3 = mc.timer.tickLength / 1000.0F;
      ItemStack var4 = mc.player.getHeldItemMainhand();
      ArrayList var5 = new ArrayList();
      HashMap var6 = new HashMap();
      Object var7 = new ArrayList();
      if ((Boolean)this.FPS.getValue()) {
         var5.add(String.valueOf((new StringBuilder()).append("FPS: ").append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)this.color2.getValue()).substring(1)).append(Minecraft.getDebugFPS())));
      }

      if ((Boolean)this.PING.getValue()) {
         var5.add(String.valueOf((new StringBuilder()).append("Ping: ").append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)this.color2.getValue()).substring(1)).append(mc.getConnection() != null && mc.player != null && mc.getConnection().getPlayerInfo(mc.player.entityUniqueID) != null ? mc.getConnection().getPlayerInfo(mc.player.entityUniqueID).getResponseTime() : "-1").append("ms")));
      }

      if ((Boolean)this.TPS.getValue()) {
         var5.add(String.valueOf((new StringBuilder()).append("TPS: ").append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)this.color2.getValue()).substring(1)).append(this.df2.format((double)LagCompensator.INSTANCE.getTickRate()))));
      }

      if ((Boolean)this.SPEED.getValue()) {
         var5.add(String.valueOf((new StringBuilder()).append("Speed: ").append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)this.color2.getValue()).substring(1)).append(this.df.format((double)(MathHelper.sqrt(Math.pow(coordsDiff('x'), 2.0D) + Math.pow(coordsDiff('z'), 2.0D)) / var3) * 3.6D)).append(" hm/h")));
      }

      if ((Boolean)this.TIME.getValue()) {
         var5.add(String.valueOf((new StringBuilder()).append("Time: ").append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)this.color2.getValue()).substring(1)).append((Boolean)this.t24.getValue() ? (new SimpleDateFormat("k:mm")).format(new Date()) : (new SimpleDateFormat("h:mm a")).format(new Date()))));
      }

      if ((Boolean)this.DURABILITY.getValue() && this.isToolArmor(var4.getItem())) {
         var5.add(String.valueOf((new StringBuilder()).append("Durability: ").append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)this.color2.getValue()).substring(1)).append(var4.getMaxDamage() - var4.getItemDamage())));
      }

      if ((Boolean)this.SERVER_IP.getValue()) {
         var5.add(String.valueOf((new StringBuilder()).append("Server IP: ").append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)this.color2.getValue()).substring(1)).append(mc.getCurrentServerData() == null ? "None" : mc.getCurrentServerData().serverIP)));
      }

      Iterator var8;
      FontRenderer var10001;
      if ((Boolean)this.POTIONS.getValue()) {
         var8 = mc.player.getActivePotionEffects().iterator();

         String var10;
         while(var8.hasNext()) {
            PotionEffect var9 = (PotionEffect)var8.next();
            var10 = String.valueOf((new StringBuilder()).append(PotionUtil.getPotionName(var9.getPotion())).append(" ").append(var9.getAmplifier() + 1 != 1 ? String.valueOf((new StringBuilder()).append(var9.getAmplifier() + 1).append(" ")) : "").append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)this.color2.getValue()).substring(1)).append(this.getTime(var9.getDuration() / 20).length() == 1 ? "0" : "").append(this.getTime(var9.getDuration() / 20)));
            var6.put(var10, var9);
         }

         if (Xulu.CustomFont) {
            var7 = (List)var6.keySet().stream().sorted(Comparator.comparing((var0) -> {
               return Xulu.cFontRenderer.getStringWidth(var0);
            })).collect(Collectors.toList());
         } else {
            Stream var10000 = var6.keySet().stream();
            var10001 = fontRenderer;
            var10001.getClass();
            var7 = (List)var10000.sorted(Comparator.comparing(var10001::func_78256_a)).collect(Collectors.toList());
         }

         if ((Boolean)this.ALPHABETICAL.getValue()) {
            String[] var17 = (String[])((List)var7).toArray(new String[0]);
            int var18 = ((List)var7).size();

            int var12;
            for(int var11 = 0; var11 < var18; ++var11) {
               for(var12 = var11 + 1; var12 < var18; ++var12) {
                  if (var17[var11].compareTo(var17[var12]) > 0) {
                     var10 = var17[var11];
                     var17[var11] = var17[var12];
                     var17[var12] = var10;
                  }
               }
            }

            ((List)var7).clear();
            String[] var20 = var17;
            var12 = var17.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               String var14 = var20[var13];

               try {
                  ((List)var7).add(var14);
               } catch (Exception var16) {
               }
            }

            if (((String)this.order.getValue()).equalsIgnoreCase("Down")) {
               Collections.reverse((List)var7);
            }
         }
      }

      this.width = 50.0D;
      this.height = 50.0D;
      String var19;
      if (Xulu.CustomFont) {
         var5.sort(Comparator.comparing((var0) -> {
            return Xulu.cFontRenderer.getStringWidth(var0);
         }));
         Collections.reverse(var5);
         if (((String)this.order.getValue()).equalsIgnoreCase("Down")) {
            var1 += 39.0F;
         }

         for(var8 = ((List)var7).iterator(); var8.hasNext(); var1 += (float)(((String)this.order.getValue()).equalsIgnoreCase("Up") ? 10 : -10)) {
            var19 = (String)var8.next();
            Xulu.cFontRenderer.drawStringWithShadow(var19, this.x - (double)(((String)this.mode.getValue()).equalsIgnoreCase("Right") ? 0 : Xulu.cFontRenderer.getStringWidth(var19)) + (((String)this.mode.getValue()).equalsIgnoreCase("Right") ? 1.0D : this.getFrame().width - 2.0D), (double)(var1 + 1.0F), ColorUtils.changeAlpha(((PotionEffect)var6.get(var19)).getPotion().getLiquidColor(), (Integer)Global.hudAlpha.getValue()));
         }

         for(var8 = var5.iterator(); var8.hasNext(); var1 += (float)(((String)this.order.getValue()).equalsIgnoreCase("Up") ? 10 : -10)) {
            var19 = (String)var8.next();
            Xulu.cFontRenderer.drawStringWithShadow(var19, this.x - (double)(((String)this.mode.getValue()).equalsIgnoreCase("Right") ? 0 : Xulu.cFontRenderer.getStringWidth(var19)) + (((String)this.mode.getValue()).equalsIgnoreCase("Right") ? 1.0D : this.getFrame().width - 2.0D), (double)(var1 + 1.0F), ColorUtils.changeAlpha(var2, (Integer)Global.hudAlpha.getValue()));
         }
      } else {
         var10001 = fontRenderer;
         var10001.getClass();
         var5.sort(Comparator.comparing(var10001::func_78256_a));
         Collections.reverse(var5);
         if (((String)this.order.getValue()).equalsIgnoreCase("Down")) {
            var1 += 39.0F;
         }

         for(var8 = ((List)var7).iterator(); var8.hasNext(); var1 += (float)(((String)this.order.getValue()).equalsIgnoreCase("Up") ? 10 : -10)) {
            var19 = (String)var8.next();
            fontRenderer.drawStringWithShadow(var19, (float)this.x - (float)(((String)this.mode.getValue()).equalsIgnoreCase("Right") ? 0 : fontRenderer.getStringWidth(var19)) + (float)(((String)this.mode.getValue()).equalsIgnoreCase("Right") ? 1.0D : this.getFrame().width - 2.0D), var1 + 1.0F, ColorUtils.changeAlpha(((PotionEffect)var6.get(var19)).getPotion().getLiquidColor(), (Integer)Global.hudAlpha.getValue()));
         }

         for(var8 = var5.iterator(); var8.hasNext(); var1 += (float)(((String)this.order.getValue()).equalsIgnoreCase("Up") ? 10 : -10)) {
            var19 = (String)var8.next();
            fontRenderer.drawStringWithShadow(var19, (float)this.x - (float)(((String)this.mode.getValue()).equalsIgnoreCase("Right") ? 0 : fontRenderer.getStringWidth(var19)) + (float)(((String)this.mode.getValue()).equalsIgnoreCase("Right") ? 1.0D : this.getFrame().width - 2.0D), var1 + 1.0F, ColorUtils.changeAlpha(var2, (Integer)Global.hudAlpha.getValue()));
         }
      }

   }

   public String getTime(int var1) {
      int var2;
      for(var2 = 0; var1 > 59; ++var2) {
         var1 -= 60;
      }

      return String.valueOf((new StringBuilder()).append(var2).append(":").append(this.df.format((long)var1).length() == 1 ? String.valueOf((new StringBuilder()).append("0").append(this.df.format((long)var1))) : this.df.format((long)var1)));
   }

   public boolean isToolArmor(Item var1) {
      return var1 instanceof ItemArmor || var1 == Items.DIAMOND_SWORD || var1 == Items.DIAMOND_PICKAXE || var1 == Items.DIAMOND_AXE || var1 == Items.DIAMOND_SHOVEL || var1 == Items.DIAMOND_HOE || var1 == Items.IRON_SWORD || var1 == Items.IRON_PICKAXE || var1 == Items.IRON_AXE || var1 == Items.IRON_SHOVEL || var1 == Items.IRON_HOE || var1 == Items.GOLDEN_SWORD || var1 == Items.GOLDEN_PICKAXE || var1 == Items.GOLDEN_AXE || var1 == Items.GOLDEN_SHOVEL || var1 == Items.GOLDEN_HOE || var1 == Items.STONE_SWORD || var1 == Items.STONE_PICKAXE || var1 == Items.STONE_AXE || var1 == Items.STONE_SHOVEL || var1 == Items.STONE_HOE || var1 == Items.WOODEN_SWORD || var1 == Items.WOODEN_PICKAXE || var1 == Items.WOODEN_AXE || var1 == Items.WOODEN_SHOVEL || var1 == Items.WOODEN_HOE;
   }

   public Info() {
      super("Info");
      this.color2 = this.register(new Value("2nd Color", this, "LightGray", ColorTextUtils.colors));
      this.FPS = this.register(new Value("Fps", this, true));
      this.PING = this.register(new Value("Ping", this, true));
      this.TPS = this.register(new Value("Tps", this, true));
      this.SPEED = this.register(new Value("Speed", this, true));
      this.TIME = this.register(new Value("Time", this, true));
      this.DURABILITY = this.register(new Value("Durability", this, true));
      this.SERVER_IP = this.register(new Value("Server IP", this, true));
      this.POTIONS = this.register(new Value("Potions", this, true));
      this.ALPHABETICAL = this.register(new Value("ABC Potions", this, false));
      this.df = new DecimalFormat("#.#");
      this.df2 = new DecimalFormat("#.###");
   }

   public static double coordsDiff(char var0) {
      switch(var0) {
      case 'x':
         return mc.player.posX - mc.player.prevPosX;
      case 'z':
         return mc.player.posZ - mc.player.prevPosZ;
      default:
         return 0.0D;
      }
   }
}
