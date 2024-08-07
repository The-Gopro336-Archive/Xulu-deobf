package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.Plane;
import com.elementars.eclient.util.RenderUtils;
import com.elementars.eclient.util.VectorUtils;
import com.elementars.eclient.util.XuluTessellator;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class Waypoints extends Module {
   // $FF: synthetic field
   private final Value render = this.register(new Value("Render", this, true));
   // $FF: synthetic field
   public static final Set WAYPOINTS = new HashSet();
   // $FF: synthetic field
   private final Value alpha = this.register(new Value("Alpha", this, 150, 0, 255));
   // $FF: synthetic field
   private final Value green = this.register(new Value("Green", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value blue = this.register(new Value("Blue", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Coordinates", new String[]{"Coordinates", "Distance", "Safe"}));
   // $FF: synthetic field
   private final Value red = this.register(new Value("Red", this, 255, 0, 255));
   // $FF: synthetic field
   private final RenderUtils renderUtils = new RenderUtils();
   // $FF: synthetic field
   private final Value cf = this.register(new Value("Custom Font", this, false));

   public void onRender() {
      synchronized(WAYPOINTS) {
         WAYPOINTS.forEach((var1) -> {
            if (mc.player.dimension == var1.dimension) {
               this.renderNametag2(var1);
            }

         });
      }
   }

   private void renderNametag2(Waypoints.Waypoint var1) {
      String var2 = String.valueOf((new StringBuilder()).append(var1.getName()).append(((String)this.mode.getValue()).equalsIgnoreCase("Safe") ? "" : String.valueOf((new StringBuilder()).append(" ").append(((String)this.mode.getValue()).equalsIgnoreCase("Coordinates") ? String.valueOf((new StringBuilder()).append(ChatFormatting.GRAY).append("(").append(var1.getPos().x).append(", ").append(var1.getPos().y).append(", ").append(var1.getPos().z).append(")")) : String.valueOf((new StringBuilder()).append(ChatFormatting.GRAY).append("").append(Math.round(mc.player.getDistance((double)var1.getPos().x, (double)var1.getPos().y, (double)var1.getPos().z))))))));
      Plane var3 = VectorUtils.toScreen((double)var1.getPos().getX() + 0.5D, (double)var1.getPos().getY() + 1.5D, (double)var1.getPos().getZ() + 0.5D);
      if ((Boolean)this.cf.getValue()) {
         Xulu.cFontRenderer.drawStringWithShadow(var2, (double)((float)var3.getX() - (float)(Xulu.cFontRenderer.getStringWidth(var2) / 2)), (double)((float)var3.getY() - Xulu.cFontRenderer.getHeight() / 2.0F), (new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue())).getRGB());
      } else {
         fontRenderer.drawStringWithShadow(var2, (float)var3.getX() - (float)(fontRenderer.getStringWidth(var2) / 2), (float)var3.getY() - (float)(fontRenderer.FONT_HEIGHT / 2), (new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue())).getRGB());
      }

   }

   public void onDisable() {
      EVENT_BUS.unregister(this);
   }

   public Waypoints() {
      super("Waypoints", "Shows locations of waypoints", 0, Category.RENDER, true);
   }

   public void onEnable() {
      EVENT_BUS.register(this);
   }

   public void onWorldRender(RenderEvent var1) {
      if ((Boolean)this.render.getValue()) {
         GlStateManager.pushMatrix();
         synchronized(WAYPOINTS) {
            WAYPOINTS.forEach((var1x) -> {
               if (mc.player.dimension == var1x.dimension) {
                  XuluTessellator.prepare(7);
                  XuluTessellator.drawBox(var1x.getPos(), (new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue(), (Integer)this.alpha.getValue())).getRGB(), 63);
                  XuluTessellator.release();
               }

            });
         }

         GlStateManager.popMatrix();
      }
   }

   public static class Waypoint {
      // $FF: synthetic field
      final AxisAlignedBB bb;
      // $FF: synthetic field
      final BlockPos pos;
      // $FF: synthetic field
      final UUID id;
      // $FF: synthetic field
      final String name;
      // $FF: synthetic field
      final int dimension;

      public String getName() {
         return this.name;
      }

      public int getDimension() {
         return this.dimension;
      }

      public int hashCode() {
         return this.getId().hashCode();
      }

      public BlockPos getPos() {
         return this.pos;
      }

      public UUID getId() {
         return this.id;
      }

      public AxisAlignedBB getBb() {
         return this.bb;
      }

      public Waypoint(UUID var1, String var2, BlockPos var3, AxisAlignedBB var4, int var5) {
         this.id = var1;
         this.name = var2;
         this.pos = var3;
         this.bb = var4;
         this.dimension = var5;
      }

      public boolean equals(Object var1) {
         return this == var1 || var1 instanceof Waypoints.Waypoint && this.getId().equals(((Waypoints.Waypoint)var1).getId());
      }
   }
}
