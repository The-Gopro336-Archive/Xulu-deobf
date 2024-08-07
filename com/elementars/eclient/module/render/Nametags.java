package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.enemy.Enemies;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.friend.Nicknames;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.combat.PopCounter;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.ColourHolder;
import com.elementars.eclient.util.RenderUtils;
import com.elementars.eclient.util.Wrapper;
import com.elementars.eclient.util.XuluTessellator;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class Nametags extends Module {
   // $FF: synthetic field
   private final Value red = this.register(new Value("Friend Red", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value friendMode = this.register(new Value("Friend Mode", this, "Text", new ArrayList(Arrays.asList("Text", "Box"))));
   // $FF: synthetic field
   private final Value Ered = this.register(new Value("Enemy Red", this, 200, 0, 255));
   // $FF: synthetic field
   private final Value Egreen = this.register(new Value("Enemy Green", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value Orainbow = this.register(new Value("Outline Rainbow", this, false));
   // $FF: synthetic field
   private final Value durability = this.register(new Value("Durability", this, true));
   // $FF: synthetic field
   private final Value enemies = this.register(new Value("Enemies", this, true));
   // $FF: synthetic field
   private final Value reversedHand = this.register(new Value("Reversed Hand", this, false));
   // $FF: synthetic field
   private final Value height = this.register(new Value("Height", this, 2.5F, 0.5F, 5.0F));
   // $FF: synthetic field
   private final Value cf = this.register(new Value("Custom Font", this, false));
   // $FF: synthetic field
   private final Value Eblue = this.register(new Value("Enemy Blue", this, 0, 0, 255));
   // $FF: synthetic field
   boolean shownItem;
   // $FF: synthetic field
   private final Value Owidth = this.register(new Value("Outline Width", this, 1.5F, 0.0F, 3.0F));
   // $FF: synthetic field
   private ICamera camera = new Frustum();
   // $FF: synthetic field
   private final Value gameMode = this.register(new Value("GameMode", this, true));
   // $FF: synthetic field
   public static Nametags INSTANCE;
   // $FF: synthetic field
   private final Value pingColor = this.register(new Value("Ping Color", this, true));
   // $FF: synthetic field
   private final Value maxText = this.register(new Value("Show Max Text", this, true));
   // $FF: synthetic field
   private final Value friends = this.register(new Value("Friends", this, true));
   // $FF: synthetic field
   private final Value armor = this.register(new Value("Armor", this, true));
   // $FF: synthetic field
   private final Value Ogreen = this.register(new Value("Outline Green", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value max = this.register(new Value("Show Max Enchants", this, true));
   // $FF: synthetic field
   private final RenderUtils renderUtils = new RenderUtils();
   // $FF: synthetic field
   private final Value outline = this.register(new Value("Outline", this, true));
   // $FF: synthetic field
   private final Value blue = this.register(new Value("Friend Blue", this, 130, 0, 255));
   // $FF: synthetic field
   private final Value invisibles = this.register(new Value("Invisibles", this, false));
   // $FF: synthetic field
   private final Value Oalpha = this.register(new Value("Outline Alpha", this, 150, 0, 255));
   // $FF: synthetic field
   private final Value scale = this.register(new Value("Scale", this, 0.05F, 0.01F, 0.09F));
   // $FF: synthetic field
   private final Value ping = this.register(new Value("Ping", this, true));
   // $FF: synthetic field
   private final Value Oblue = this.register(new Value("Outline Blue", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value health = this.register(new Value("Health", this, true));
   // $FF: synthetic field
   private final Value green = this.register(new Value("Friend Green", this, 130, 0, 255));
   // $FF: synthetic field
   private final Value Ored = this.register(new Value("Outline Red", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value item = this.register(new Value("Item Name", this, true));
   // $FF: synthetic field
   private final Value reversed = this.register(new Value("Reversed", this, false));
   // $FF: synthetic field
   private final Value pops = this.register(new Value("Pop Count", this, true));

   public void drawBorderRect(float var1, float var2, float var3, float var4, int var5, int var6, float var7) {
      drawGuiRect((double)(var1 + var7), (double)(var2 + var7), (double)(var3 - var7), (double)(var4 - var7), var6);
      drawGuiRect((double)var1, (double)var2, (double)(var1 + var7), (double)var4, var5);
      drawGuiRect((double)(var1 + var7), (double)var2, (double)var3, (double)(var2 + var7), var5);
      drawGuiRect((double)(var1 + var7), (double)(var4 - var7), (double)var3, (double)var4, var5);
      drawGuiRect((double)(var3 - var7), (double)(var2 + var7), (double)var3, (double)(var4 - var7), var5);
   }

   public float getNametagSize(EntityLivingBase var1) {
      ScaledResolution var2 = new ScaledResolution(mc);
      double var3 = (double)var2.getScaleFactor() / Math.pow((double)var2.getScaleFactor(), 2.0D);
      return (float)var3 + mc.player.getDistance(var1) / 7.0F;
   }

   public void renderEnchantText(EntityPlayer var1, ItemStack var2, int var3, int var4) {
      int var5 = var4;
      int var6 = var4;
      if ((var2.getItem() instanceof ItemArmor || var2.getItem() instanceof ItemSword || var2.getItem() instanceof ItemTool) && (Boolean)this.durability.getValue()) {
         float var7 = ((float)var2.getMaxDamage() - (float)var2.getItemDamage()) / (float)var2.getMaxDamage();
         float var8 = 1.0F - var7;
         int var9 = 100 - (int)(var8 * 100.0F);
         if ((Boolean)this.cf.getValue()) {
            Xulu.cFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(var9).append("%")), (double)(var3 * 2 + 4), (double)(var4 - 10), ColourHolder.toHex((int)(var8 * 255.0F), (int)(var7 * 255.0F), 0));
         } else {
            mc.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(var9).append("%")), (float)(var3 * 2 + 4), (float)(var4 - 10), ColourHolder.toHex((int)(var8 * 255.0F), (int)(var7 * 255.0F), 0));
         }
      }

      if ((Boolean)this.max.getValue() && this.isMaxEnchants(var2)) {
         GL11.glPushMatrix();
         GL11.glScalef(1.0F, 1.0F, 0.0F);
         if ((Boolean)this.maxText.getValue()) {
            if ((Boolean)this.cf.getValue()) {
               Xulu.cFontRenderer.drawStringWithShadow("Max", (double)(var3 * 2 + 7), (double)(var4 + 24), ColorUtils.Colors.RED);
            } else {
               mc.fontRenderer.drawStringWithShadow("Max", (float)(var3 * 2 + 7), (float)(var4 + 24), ColorUtils.Colors.RED);
            }
         }

         GL11.glScalef(1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      } else {
         NBTTagList var14 = var2.getEnchantmentTagList();
         if (var14 != null) {
            for(int var15 = 0; var15 < var14.tagCount(); ++var15) {
               short var16 = var14.getCompoundTagAt(var15).getShort("id");
               short var10 = var14.getCompoundTagAt(var15).getShort("lvl");
               Enchantment var11 = Enchantment.getEnchantmentByID(var16);
               if (var11 != null && !var11.isCurse()) {
                  String var12 = var10 == 1 ? var11.getTranslatedName(var10).substring(0, 3).toLowerCase() : String.valueOf((new StringBuilder()).append(var11.getTranslatedName(var10).substring(0, 2).toLowerCase()).append(var10));
                  var12 = String.valueOf((new StringBuilder()).append(var12.substring(0, 1).toUpperCase()).append(var12.substring(1)));
                  GL11.glPushMatrix();
                  GL11.glScalef(1.0F, 1.0F, 0.0F);
                  if ((Boolean)this.cf.getValue()) {
                     Xulu.cFontRenderer.drawStringWithShadow(var12, (double)(var3 * 2 + 3), (double)var6, -1);
                  } else {
                     mc.fontRenderer.drawStringWithShadow(var12, (float)(var3 * 2 + 3), (float)var6, -1);
                  }

                  GL11.glScalef(1.0F, 1.0F, 1.0F);
                  GL11.glPopMatrix();
                  var5 += 8;
                  var6 += 8;
               }
            }
         }

      }
   }

   public static void drawGuiRect(double var0, double var2, double var4, double var6, int var8) {
      float var9 = (float)(var8 >> 24 & 255) / 255.0F;
      float var10 = (float)(var8 >> 16 & 255) / 255.0F;
      float var11 = (float)(var8 >> 8 & 255) / 255.0F;
      float var12 = (float)(var8 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(var10, var11, var12, var9);
      GL11.glBegin(7);
      GL11.glVertex2d(var4, var2);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var0, var6);
      GL11.glVertex2d(var4, var6);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public String getShortName(String var1) {
      if (var1.equalsIgnoreCase("survival")) {
         return "S";
      } else if (var1.equalsIgnoreCase("creative")) {
         return "C";
      } else if (var1.equalsIgnoreCase("adventure")) {
         return "A";
      } else {
         return var1.equalsIgnoreCase("spectator") ? "SP" : "NONE";
      }
   }

   public String getHealth(float var1) {
      if (var1 > 18.0F) {
         return "a";
      } else if (var1 > 16.0F) {
         return "2";
      } else if (var1 > 12.0F) {
         return "e";
      } else if (var1 > 8.0F) {
         return "6";
      } else {
         return var1 > 5.0F ? "c" : "4";
      }
   }

   public static void fakeGuiRect(double var0, double var2, double var4, double var6, int var8) {
      double var9;
      if (var0 < var4) {
         var9 = var0;
         var0 = var4;
         var4 = var9;
      }

      if (var2 < var6) {
         var9 = var2;
         var2 = var6;
         var6 = var9;
      }

      float var15 = (float)(var8 >> 24 & 255) / 255.0F;
      float var10 = (float)(var8 >> 16 & 255) / 255.0F;
      float var11 = (float)(var8 >> 8 & 255) / 255.0F;
      float var12 = (float)(var8 & 255) / 255.0F;
      Tessellator var13 = Tessellator.getInstance();
      BufferBuilder var14 = var13.getBuffer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.color(var10, var11, var12, var15);
      var14.begin(7, DefaultVertexFormats.POSITION);
      var14.pos(var0, var6, 0.0D).endVertex();
      var14.pos(var4, var6, 0.0D).endVertex();
      var14.pos(var4, var2, 0.0D).endVertex();
      var14.pos(var0, var2, 0.0D).endVertex();
      var13.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public void renderItem(EntityPlayer var1, ItemStack var2, int var3, int var4, int var5, boolean var6) {
      GL11.glPushMatrix();
      GL11.glDepthMask(true);
      GlStateManager.clear(256);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      RenderHelper.enableStandardItemLighting();
      mc.getRenderItem().zLevel = -100.0F;
      GlStateManager.scale(1.0F, 1.0F, 0.01F);
      mc.getRenderItem().renderItemAndEffectIntoGUI(var2, var3, var4 / 2 - 12);
      if ((Boolean)this.durability.getValue()) {
         mc.getRenderItem().renderItemOverlays(mc.fontRenderer, var2, var3, var4 / 2 - 12);
      }

      mc.getRenderItem().zLevel = 0.0F;
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
      GlStateManager.scale(0.5D, 0.5D, 0.5D);
      GlStateManager.disableDepth();
      this.renderEnchantText(var1, var2, var3, var4 - 18);
      if (!this.shownItem && (Boolean)this.item.getValue() && var6) {
         if ((Boolean)this.cf.getValue()) {
            Xulu.cFontRenderer.drawStringWithShadow(var2.getDisplayName().equalsIgnoreCase("Air") ? "" : var2.getDisplayName(), (double)(var5 * 2 + 95 - Xulu.cFontRenderer.getStringWidth(var2.getDisplayName()) / 2), (double)(var4 - 37), -1);
         } else {
            mc.fontRenderer.drawStringWithShadow(var2.getDisplayName().equalsIgnoreCase("Air") ? "" : var2.getDisplayName(), (float)(var5 * 2 + 95 - mc.fontRenderer.getStringWidth(var2.getDisplayName()) / 2), (float)(var4 - 37), -1);
         }

         this.shownItem = true;
      }

      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GL11.glPopMatrix();
   }

   public void onWorldRender(RenderEvent var1) {
      if (mc.player != null) {
         double var2 = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double)var1.getPartialTicks();
         double var4 = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double)var1.getPartialTicks();
         double var6 = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double)var1.getPartialTicks();
         this.camera.setPosition(var2, var4, var6);
         ArrayList var8 = new ArrayList(mc.world.playerEntities);
         var8.sort(Comparator.comparing((var0) -> {
            return mc.player.getDistance((EntityPlayer)var0);
         }).reversed());
         Iterator var9 = var8.iterator();

         while(true) {
            double var16;
            EntityPlayer var10;
            NetworkPlayerInfo var11;
            double var12;
            double var14;
            do {
               do {
                  do {
                     do {
                        if (!var9.hasNext()) {
                           return;
                        }

                        var10 = (EntityPlayer)var9.next();
                        var11 = mc.player.connection.getPlayerInfo(var10.getGameProfile().getId());
                     } while(!this.camera.isBoundingBoxInFrustum(var10.getEntityBoundingBox()));
                  } while(var10 == mc.getRenderViewEntity());
               } while(!var10.isEntityAlive());

               var12 = var10.lastTickPosX + (var10.posX - var10.lastTickPosX) * (double)mc.timer.renderPartialTicks - mc.renderManager.renderPosX;
               var14 = var10.lastTickPosY + (var10.posY - var10.lastTickPosY) * (double)mc.timer.renderPartialTicks - mc.renderManager.renderPosY;
               var16 = var10.lastTickPosZ + (var10.posZ - var10.lastTickPosZ) * (double)mc.timer.renderPartialTicks - mc.renderManager.renderPosZ;
            } while(var11 != null && this.getShortName(var11.getGameType().getName()).equalsIgnoreCase("SP") && !(Boolean)this.invisibles.getValue());

            if (!var10.getName().startsWith("Body #")) {
               this.renderNametag(var10, var12, var14, var16);
            }
         }
      }
   }

   public String getPing(float var1) {
      if (var1 > 200.0F) {
         return "c";
      } else {
         return var1 > 100.0F ? "e" : "a";
      }
   }

   public boolean isMaxEnchants(ItemStack var1) {
      NBTTagList var2 = var1.getEnchantmentTagList();
      ArrayList var3 = new ArrayList();
      int var4 = 0;
      if (var2 != null) {
         for(int var5 = 0; var5 < var2.tagCount(); ++var5) {
            short var6 = var2.getCompoundTagAt(var5).getShort("id");
            short var7 = var2.getCompoundTagAt(var5).getShort("lvl");
            Enchantment var8 = Enchantment.getEnchantmentByID(var6);
            if (var8 != null) {
               var3.add(var8.getTranslatedName(var7));
            }
         }

         byte var9;
         Iterator var10;
         String var11;
         if (var1.getItem() == Items.DIAMOND_HELMET) {
            var9 = 5;
            var10 = var3.iterator();

            while(var10.hasNext()) {
               var11 = (String)var10.next();
               if (var11.equalsIgnoreCase("Protection IV")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Respiration III")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Aqua Affinity")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Unbreaking III")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Mending")) {
                  ++var4;
               }
            }

            return var4 >= var9;
         } else if (var1.getItem() == Items.DIAMOND_CHESTPLATE) {
            var9 = 3;
            var10 = var3.iterator();

            while(var10.hasNext()) {
               var11 = (String)var10.next();
               if (var11.equalsIgnoreCase("Protection IV")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Unbreaking III")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Mending")) {
                  ++var4;
               }
            }

            return var4 >= var9;
         } else if (var1.getItem() == Items.DIAMOND_LEGGINGS) {
            var9 = 3;
            var10 = var3.iterator();

            while(var10.hasNext()) {
               var11 = (String)var10.next();
               if (var11.equalsIgnoreCase("Blast Protection IV")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Unbreaking III")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Mending")) {
                  ++var4;
               }
            }

            return var4 >= var9;
         } else if (var1.getItem() == Items.DIAMOND_BOOTS) {
            var9 = 5;
            var10 = var3.iterator();

            while(var10.hasNext()) {
               var11 = (String)var10.next();
               if (var11.equalsIgnoreCase("Protection IV")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Feather Falling IV")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Depth Strider III")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Unbreaking III")) {
                  ++var4;
               }

               if (var11.equalsIgnoreCase("Mending")) {
                  ++var4;
               }
            }

            return var4 >= var9;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static void disableGL2D() {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   public Nametags() {
      super("NameTags", "Enhances nametags", 0, Category.RENDER, true);
      INSTANCE = this;
   }

   private String getName(EntityPlayer var1) {
      return Nicknames.hasNickname(var1.getName()) ? Nicknames.getNickname(var1.getName()) : var1.getName();
   }

   private void renderDurabilityText(EntityPlayer var1, ItemStack var2, int var3, int var4) {
      GL11.glPushMatrix();
      GL11.glDepthMask(true);
      GlStateManager.clear(256);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.scale(1.0F, 1.0F, 0.01F);
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
      GlStateManager.scale(0.5D, 0.5D, 0.5D);
      GlStateManager.disableDepth();
      if (var2.getItem() instanceof ItemArmor || var2.getItem() instanceof ItemSword || var2.getItem() instanceof ItemTool) {
         float var5 = ((float)var2.getMaxDamage() - (float)var2.getItemDamage()) / (float)var2.getMaxDamage();
         float var6 = 1.0F - var5;
         int var7 = 100 - (int)(var6 * 100.0F);
         if ((Boolean)this.cf.getValue()) {
            Xulu.cFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(var7).append("%")), (double)(var3 * 2 + 4), (double)(var4 - 10), ColourHolder.toHex((int)(var6 * 255.0F), (int)(var5 * 255.0F), 0));
         } else {
            mc.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(var7).append("%")), (float)(var3 * 2 + 4), (float)(var4 - 10), ColourHolder.toHex((int)(var6 * 255.0F), (int)(var5 * 255.0F), 0));
         }
      }

      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GL11.glPopMatrix();
   }

   public static void drawBorderedRect(double var0, double var2, double var4, double var6, double var8, int var10, int var11) {
      GlStateManager.pushMatrix();
      enableGL2D();
      fakeGuiRect(var0 + var8, var2 + var8, var4 - var8, var6 - var8, var10);
      fakeGuiRect(var0 + var8, var2, var4 - var8, var2 + var8, var11);
      fakeGuiRect(var0, var2, var0 + var8, var6, var11);
      fakeGuiRect(var4 - var8, var2, var4, var6, var11);
      fakeGuiRect(var0 + var8, var6 - var8, var4 - var8, var6, var11);
      disableGL2D();
      GlStateManager.popMatrix();
   }

   public void renderNametag(EntityPlayer var1, double var2, double var4, double var6) {
      boolean var8 = false;
      this.shownItem = false;
      GlStateManager.pushMatrix();
      FontRenderer var9 = Wrapper.getMinecraft().fontRenderer;
      NetworkPlayerInfo var10 = mc.player.connection.getPlayerInfo(var1.getGameProfile().getId());
      boolean var11 = Friends.isFriend(var1.getName()) && (Boolean)this.friends.getValue();
      boolean var12 = Enemies.isEnemy(var1.getName()) && (Boolean)this.enemies.getValue();
      String var13 = String.valueOf((new StringBuilder()).append((var11 || var12) && ((String)this.friendMode.getValue()).equalsIgnoreCase("Text") ? String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append(var11 ? "b" : "c")) : (var1.isSneaking() ? String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("9")) : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("r")))).append(this.getName(var1)).append((Boolean)this.gameMode.getValue() && var10 != null ? String.valueOf((new StringBuilder()).append(" [").append(this.getShortName(var10.getGameType().getName())).append("]")) : "").append((Boolean)this.ping.getValue() && var10 != null ? String.valueOf((new StringBuilder()).append(" ").append((Boolean)this.pingColor.getValue() ? String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append(this.getPing((float)var10.getResponseTime()))) : "").append(var10.getResponseTime()).append("ms")) : "").append((Boolean)this.health.getValue() ? String.valueOf((new StringBuilder()).append(" ").append(Command.SECTIONSIGN()).append(this.getHealth(var1.getHealth() + var1.getAbsorptionAmount())).append(MathHelper.ceil(var1.getHealth() + var1.getAbsorptionAmount()))) : "").append(PopCounter.INSTANCE.popMap.containsKey(var1) && (Boolean)this.pops.getValue() ? String.valueOf((new StringBuilder()).append(" ").append(ChatFormatting.DARK_RED).append("-").append(PopCounter.INSTANCE.popMap.get(var1))) : ""));
      var13 = var13.replace(".0", "");
      float var14 = mc.player.getDistance(var1);
      float var15 = (var14 / 5.0F <= 2.0F ? 2.0F : var14 / 5.0F * ((Float)this.scale.getValue() * 10.0F + 1.0F)) * 2.5F * ((Float)this.scale.getValue() / 10.0F);
      float var16 = (Float)this.scale.getValue() * this.getNametagSize(var1);
      GL11.glTranslated((double)((float)var2), (double)((float)var4 + (Float)this.height.getValue()) - (var1.isSneaking() ? 0.4D : 0.0D) + (var14 / 5.0F > 2.0F ? (double)(var14 / 12.0F) - 0.7D : 0.0D), (double)((float)var6));
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-mc.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(mc.renderManager.playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1.0F : 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-var15, -var15, var15);
      this.renderUtils.disableGlCap(2896, 2929);
      this.renderUtils.enableGlCap(3042);
      GL11.glBlendFunc(770, 771);
      int var17;
      if ((Boolean)this.cf.getValue()) {
         var17 = Xulu.cFontRenderer.getStringWidth(var13) / 2 + 1;
      } else {
         var17 = var9.getStringWidth(var13) / 2;
      }

      int var18 = (var11 || var12) && ((String)this.friendMode.getValue()).equalsIgnoreCase("Box") ? (var11 ? (new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue())).getRGB() : (new Color((Integer)this.Ered.getValue(), (Integer)this.Egreen.getValue(), (Integer)this.Eblue.getValue())).getRGB()) : ColorUtils.Colors.BLACK;
      int var19 = (new Color((Integer)this.Ored.getValue(), (Integer)this.Ogreen.getValue(), (Integer)this.Oblue.getValue(), (Integer)this.Oalpha.getValue())).getRGB();
      if ((Boolean)this.Orainbow.getValue()) {
         var19 = ColorUtils.changeAlpha(Xulu.rgb, (Integer)this.Oalpha.getValue());
      }

      Gui.drawRect(-var17 - 2, 10, var17 + 1, 20, ColorUtils.changeAlpha(var18, 120));
      if ((Boolean)this.outline.getValue()) {
         XuluTessellator.drawOutlineLine((double)(-var17 - 2), 10.0D, (double)(var17 + 1), 20.0D, (Float)this.Owidth.getValue(), var19);
      }

      if ((Boolean)this.cf.getValue()) {
         Xulu.cFontRenderer.drawStringWithShadow(var13, (double)(-var17), 10.0D, -1);
      } else {
         mc.fontRenderer.drawStringWithShadow(var13, (float)(-var17), 11.0F, -1);
      }

      int var20;
      int var21;
      Iterator var22;
      ItemStack var23;
      int var24;
      ItemStack var25;
      ItemStack var26;
      int var27;
      if ((Boolean)this.armor.getValue()) {
         var20 = -6;
         var21 = 0;
         var22 = var1.inventory.armorInventory.iterator();

         while(var22.hasNext()) {
            var23 = (ItemStack)var22.next();
            if (var23 != null) {
               var20 -= 8;
               if (var23.getItem() != Items.AIR) {
                  ++var21;
               }
            }
         }

         if (var1.getHeldItemOffhand().getItem() != Items.AIR) {
            ++var21;
         }

         label291: {
            label228: {
               var27 = var20 - 8;
               var20 += 8 * (5 - var21) - (var21 == 0 ? 4 : 0);
               if ((Boolean)this.reversedHand.getValue()) {
                  if (var1.getHeldItemOffhand().getItem() != Items.AIR) {
                     break label228;
                  }
               } else if (var1.getHeldItemMainhand().getItem() != Items.AIR) {
                  break label228;
               }

               if (!(Boolean)this.reversedHand.getValue()) {
                  this.shownItem = true;
               }
               break label291;
            }

            var20 -= 8;
            if ((Boolean)this.reversedHand.getValue()) {
               var23 = var1.getHeldItemOffhand().copy();
               this.renderItem(var1, var23, var20, -10, var27, false);
            } else {
               var23 = var1.getHeldItemMainhand().copy();
               this.renderItem(var1, var23, var20, -10, var27, true);
            }

            var20 += 16;
         }

         if ((Boolean)this.reversed.getValue()) {
            for(var24 = 0; var24 <= 3; ++var24) {
               var25 = (ItemStack)var1.inventory.armorInventory.get(var24);
               if (var25 != null && var25.getItem() != Items.AIR) {
                  var26 = var25.copy();
                  this.renderItem(var1, var26, var20, -10, var27, false);
                  var20 += 16;
               }
            }
         } else {
            for(var24 = 3; var24 >= 0; --var24) {
               var25 = (ItemStack)var1.inventory.armorInventory.get(var24);
               if (var25 != null && var25.getItem() != Items.AIR) {
                  var26 = var25.copy();
                  this.renderItem(var1, var26, var20, -10, var27, false);
                  var20 += 16;
               }
            }
         }

         label295: {
            if ((Boolean)this.reversedHand.getValue()) {
               if (var1.getHeldItemMainhand().getItem() == Items.AIR) {
                  break label295;
               }
            } else if (var1.getHeldItemOffhand().getItem() == Items.AIR) {
               break label295;
            }

            var20 += 0;
            ItemStack var28;
            if ((Boolean)this.reversedHand.getValue()) {
               var28 = var1.getHeldItemMainhand().copy();
               this.renderItem(var1, var28, var20, -10, var27, true);
            } else {
               var28 = var1.getHeldItemOffhand().copy();
               this.renderItem(var1, var28, var20, -10, var27, false);
            }

            var20 += 8;
         }

         GlStateManager.enableAlpha();
         GlStateManager.disableBlend();
         GlStateManager.enableTexture2D();
      } else if ((Boolean)this.durability.getValue()) {
         var20 = -6;
         var21 = 0;
         var22 = var1.inventory.armorInventory.iterator();

         while(var22.hasNext()) {
            var23 = (ItemStack)var22.next();
            if (var23 != null) {
               var20 -= 8;
               if (var23.getItem() != Items.AIR) {
                  ++var21;
               }
            }
         }

         if (var1.getHeldItemOffhand().getItem() != Items.AIR) {
            ++var21;
         }

         var27 = var20 - 8;
         var20 += 8 * (5 - var21) - (var21 == 0 ? 4 : 0);
         if ((Boolean)this.reversed.getValue()) {
            for(var24 = 0; var24 <= 3; ++var24) {
               var25 = (ItemStack)var1.inventory.armorInventory.get(var24);
               if (var25 != null && var25.getItem() != Items.AIR) {
                  var26 = var25.copy();
                  this.renderDurabilityText(var1, var26, var20, -10);
                  var20 += 16;
               }
            }
         } else {
            for(var24 = 3; var24 >= 0; --var24) {
               var25 = (ItemStack)var1.inventory.armorInventory.get(var24);
               if (var25 != null && var25.getItem() != Items.AIR) {
                  var26 = var25.copy();
                  this.renderDurabilityText(var1, var26, var20, -10);
                  var20 += 16;
               }
            }
         }

         GlStateManager.enableAlpha();
         GlStateManager.disableBlend();
         GlStateManager.enableTexture2D();
      }

      this.renderUtils.resetCaps();
      GlStateManager.resetColor();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static void enableGL2D() {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
   }
}
