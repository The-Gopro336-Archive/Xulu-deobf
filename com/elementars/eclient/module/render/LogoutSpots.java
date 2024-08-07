package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventPlayerConnect;
import com.elementars.eclient.event.events.LocalPlayerUpdateEvent;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorTextUtils;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Pair;
import com.elementars.eclient.util.RenderUtils;
import com.elementars.eclient.util.SpotSet;
import com.elementars.eclient.util.Wrapper;
import com.elementars.eclient.util.XuluTessellator;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.UUID;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class LogoutSpots extends Module {
   // $FF: synthetic field
   private final Value max_distance = this.register(new Value("Max Distance", this, 320, 0, 1000));
   // $FF: synthetic field
   private final Value box = this.register(new Value("Box", this, false));
   // $FF: synthetic field
   private final Value blue;
   // $FF: synthetic field
   private final SpotSet spots;
   // $FF: synthetic field
   private final Value color;
   // $FF: synthetic field
   private final RenderUtils renderUtils = new RenderUtils();
   // $FF: synthetic field
   private final Value cf = this.register(new Value("Custom Font", this, false));
   // $FF: synthetic field
   private final Value render = this.register(new Value("Render", this, true));
   // $FF: synthetic field
   private final Value red;
   // $FF: synthetic field
   private final Value print_message = this.register(new Value("Print Message", this, true));
   // $FF: synthetic field
   private final Value coords = this.register(new Value("Coordinates", this, true));
   // $FF: synthetic field
   private final Value watermark = this.register(new Value("Watermark", this, true));
   // $FF: synthetic field
   private final Value green;

   public void renderNametag(EntityPlayer var1, double var2, double var4, double var6) {
      GlStateManager.pushMatrix();
      FontRenderer var8 = Wrapper.getMinecraft().fontRenderer;
      String var9 = String.valueOf((new StringBuilder()).append(var1.getName()).append(" ").append((Boolean)this.coords.getValue() ? String.valueOf((new StringBuilder()).append(ChatFormatting.GRAY).append("(").append((int)var1.posX).append(", ").append((int)var1.posY).append(", ").append((int)var1.posZ).append(")")) : String.valueOf((new StringBuilder()).append(ChatFormatting.GRAY).append("").append(Math.round(mc.player.getDistance(var1))))));
      var9 = var9.replace(".0", "");
      float var10 = mc.player.getDistance(var1);
      float var11 = (var10 / 5.0F <= 2.0F ? 2.0F : var10 / 5.0F * 1.5075471F) * 2.5F * 0.005075472F;
      GL11.glTranslated((double)((float)var2), (double)((float)var4) + 2.5D + (var10 / 5.0F > 2.0F ? (double)(var10 / 12.0F) - 0.7D : 0.0D), (double)((float)var6));
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-mc.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(mc.renderManager.playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1.0F : 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-var11, -var11, var11);
      this.renderUtils.disableGlCap(2896, 2929);
      this.renderUtils.enableGlCap(3042);
      GL11.glBlendFunc(770, 771);
      int var12;
      if ((Boolean)this.cf.getValue()) {
         var12 = Xulu.cFontRenderer.getStringWidth(var9) / 2 + 1;
      } else {
         var12 = var8.getStringWidth(var9) / 2;
      }

      if ((Boolean)this.box.getValue()) {
         Gui.drawRect(-var12 - 2, 10, var12 + 1, 20, ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 120));
      }

      if ((Boolean)this.cf.getValue()) {
         Xulu.cFontRenderer.drawStringWithShadow(var9, (double)(-var12), 10.0D, (new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue())).getRGB());
      } else {
         mc.fontRenderer.drawStringWithShadow(var9, (float)(-var12), 11.0F, (new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue())).getRGB());
      }

      this.renderUtils.resetCaps();
      GlStateManager.resetColor();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public LogoutSpots() {
      super("LogoutSpot", "show where a player logs out", 0, Category.RENDER, true);
      this.color = this.register(new Value("Color", this, "White", ColorTextUtils.colors));
      this.red = this.register(new Value("Red", this, 255, 0, 255));
      this.green = this.register(new Value("Green", this, 0, 0, 255));
      this.blue = this.register(new Value("Blue", this, 0, 0, 255));
      this.spots = new SpotSet();
   }

   public void onEnable() {
      EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onWorldUnload(Unload var1) {
      this.reset();
   }

   private void reset() {
      synchronized(this.spots) {
         this.spots.clear();
      }
   }

   @EventTarget
   public void onPlayerDisconnect(EventPlayerConnect.Leave var1) {
      if (mc.world != null) {
         EntityPlayer var2 = mc.world.getPlayerEntityByUUID(var1.getUuid());
         if (var2 != null && mc.player != null && !mc.player.equals(var2)) {
            AxisAlignedBB var3 = var2.getEntityBoundingBox();
            synchronized(this.spots) {
               if (this.spots.add(new LogoutSpots.LogoutPos(var1.getUuid(), var2.getName(), new Vec3d(var3.maxX, var3.maxY, var3.maxZ), new Vec3d(var3.minX, var3.minY, var3.minZ), var3, var2)) && (Boolean)this.print_message.getValue()) {
                  if ((Boolean)this.watermark.getValue()) {
                     Command.sendChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append(String.format("%s has disconnected!", var2.getName()))));
                  } else {
                     Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append(String.format("%s has disconnected!", var2.getName()))));
                  }
               }
            }
         }

      }
   }

   @EventTarget
   public void onPlayerUpdate(LocalPlayerUpdateEvent var1) {
      if ((Integer)this.max_distance.getValue() > 0) {
         synchronized(this.spots) {
            this.spots.removeIf((var1x) -> {
               return mc.player.getPositionVector().distanceTo(var1x.getTopVec()) > (double)(Integer)this.max_distance.getValue();
            });
         }
      }

   }

   @EventTarget
   public void onPlayerConnect(EventPlayerConnect.Join var1) {
      synchronized(this.spots) {
         Pair var3 = this.spots.removeIfReturn((var1x) -> {
            return var1x.getId().equals(var1.getUuid());
         });
         if ((Boolean)var3.getKey() && (Boolean)this.print_message.getValue()) {
            double var4 = ((LogoutSpots.LogoutPos)var3.getValue()).player.lastTickPosX;
            double var6 = ((LogoutSpots.LogoutPos)var3.getValue()).player.lastTickPosY;
            double var8 = ((LogoutSpots.LogoutPos)var3.getValue()).player.lastTickPosZ;
            if ((Boolean)this.watermark.getValue()) {
               Command.sendChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append(String.format("%s has joined (%s, %s, %s)!", var1.getName(), (int)var4, (int)var6, (int)var8))));
            } else {
               Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append(String.format("%s has joined (%s, %s, %s)!", var1.getName(), (int)var4, (int)var6, (int)var8))));
            }
         }

      }
   }

   public void onWorldRender(RenderEvent var1) {
      if ((Boolean)this.render.getValue()) {
         GlStateManager.pushMatrix();
         synchronized(this.spots) {
            this.spots.forEach((var1x) -> {
               double var2 = var1x.lastTickPosX + (var1x.posX - var1x.lastTickPosX) * (double)mc.timer.renderPartialTicks - mc.renderManager.renderPosX;
               double var4 = var1x.lastTickPosY + (var1x.posY - var1x.lastTickPosY) * (double)mc.timer.renderPartialTicks - mc.renderManager.renderPosY;
               double var6 = var1x.lastTickPosZ + (var1x.posZ - var1x.lastTickPosZ) * (double)mc.timer.renderPartialTicks - mc.renderManager.renderPosZ;
               AxisAlignedBB var8 = var1x.bb;
               AxisAlignedBB var9 = new AxisAlignedBB(var8.minX - var1x.posX + var2 - 0.05D, var8.minY - var1x.posY + var4, var8.minZ - var1x.posZ + var6 - 0.05D, var8.maxX - var1x.posX + var2 + 0.05D, var8.maxY - var1x.posY + var4 + 0.15D, var8.maxZ - var1x.posZ + var6 + 0.05D);
               XuluTessellator.drawBoundingBox(var9, 1.5F, (new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue())).getRGB());
               this.renderNametag(var1x.player, var2, var4, var6);
            });
         }

         GlStateManager.popMatrix();
      }
   }

   @SubscribeEvent
   public void onWorldLoad(Load var1) {
      this.reset();
   }

   public void onDisable() {
      EVENT_BUS.unregister(this);
      this.reset();
   }

   public class LogoutPos {
      // $FF: synthetic field
      final String name;
      // $FF: synthetic field
      final EntityPlayer player;
      // $FF: synthetic field
      final double posZ;
      // $FF: synthetic field
      final double lastTickPosX;
      // $FF: synthetic field
      final double lastTickPosZ;
      // $FF: synthetic field
      final double lastTickPosY;
      // $FF: synthetic field
      final AxisAlignedBB bb;
      // $FF: synthetic field
      final Vec3d maxs;
      // $FF: synthetic field
      final double posX;
      // $FF: synthetic field
      final Vec3d mins;
      // $FF: synthetic field
      final double posY;
      // $FF: synthetic field
      final UUID id;

      public boolean equals(Object var1) {
         return this == var1 || var1 instanceof LogoutSpots.LogoutPos && this.getId().equals(((LogoutSpots.LogoutPos)var1).getId());
      }

      public int hashCode() {
         return this.getId().hashCode();
      }

      // $FF: synthetic method
      LogoutPos(UUID var2, String var3, Vec3d var4, Vec3d var5, AxisAlignedBB var6, EntityPlayer var7, Object var8) {
         this(var2, var3, var4, var5, var6, var7);
      }

      public AxisAlignedBB getBb() {
         return this.bb;
      }

      public UUID getId() {
         return this.id;
      }

      public EntityPlayer getPlayer() {
         return this.player;
      }

      private LogoutPos(UUID var2, String var3, Vec3d var4, Vec3d var5, AxisAlignedBB var6, EntityPlayer var7) {
         this.id = var2;
         this.name = var3;
         this.maxs = var4;
         this.mins = var5;
         this.bb = var6;
         this.player = var7;
         this.posX = var7.posX;
         this.posY = var7.posY;
         this.posZ = var7.posZ;
         this.lastTickPosX = var7.lastTickPosX;
         this.lastTickPosY = var7.lastTickPosY;
         this.lastTickPosZ = var7.lastTickPosZ;
      }

      public Vec3d getMaxs() {
         return this.maxs;
      }

      public Vec3d getMins() {
         return this.mins;
      }

      public Vec3d getTopVec() {
         return new Vec3d((this.getMins().x + this.getMaxs().x) / 2.0D, this.getMaxs().y, (this.getMins().z + this.getMaxs().z) / 2.0D);
      }

      public String getName() {
         return this.name;
      }
   }
}
