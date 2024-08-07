package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.Event;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventModelPlayerRender;
import com.elementars.eclient.event.events.EventModelRender;
import com.elementars.eclient.event.events.EventPostRenderLayers;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.EntityUtil;
import dev.xulu.settings.Value;
import java.awt.Color;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class Chams extends Module {
   // $FF: synthetic field
   public final Value width;
   // $FF: synthetic field
   public final Value b;
   // $FF: synthetic field
   public final Value Vb;
   // $FF: synthetic field
   public final Value a;
   // $FF: synthetic field
   private static Value players;
   // $FF: synthetic field
   public final Value Wg;
   // $FF: synthetic field
   public final Value Vg;
   // $FF: synthetic field
   public final Value lines;
   // $FF: synthetic field
   private static Value mobs;
   // $FF: synthetic field
   public static Value crystals;
   // $FF: synthetic field
   public static Value mode;
   // $FF: synthetic field
   public final Value g;
   // $FF: synthetic field
   public final Value Wr;
   // $FF: synthetic field
   private static Value animals;
   // $FF: synthetic field
   public final Value Wb;
   // $FF: synthetic field
   public final Value Vr;
   // $FF: synthetic field
   public final Value rainbow;
   // $FF: synthetic field
   public final Value r;
   // $FF: synthetic field
   public final Value hand;
   // $FF: synthetic field
   public final Value friendColor;

   public static boolean renderChams(Entity var0) {
      return ((String)mode.getValue()).equalsIgnoreCase("ESP") ? false : (var0 instanceof EntityPlayer ? (Boolean)players.getValue() : (EntityUtil.isPassive(var0) ? (Boolean)animals.getValue() : (Boolean)mobs.getValue()));
   }

   public Chams() {
      super("Chams", "See entities through walls", 0, Category.RENDER, true);
      mode = this.register(new Value("Mode", this, "ESP", new String[]{"ESP", "Normal", "Walls"}));
      this.Vr = this.register(new Value("Visible Red", this, 255, 0, 255)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("Walls");
      });
      this.Vg = this.register(new Value("Visible Green", this, 0, 0, 255)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("Walls");
      });
      this.Vb = this.register(new Value("Visible Blue", this, 0, 0, 255)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("Walls");
      });
      this.Wr = this.register(new Value("Wall Red", this, 0, 0, 255)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("Walls");
      });
      this.Wg = this.register(new Value("Wall Green", this, 255, 0, 255)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("Walls");
      });
      this.Wb = this.register(new Value("Wall Blue", this, 0, 0, 255)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("Walls");
      });
      this.hand = this.register(new Value("Hand", this, true)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("ESP");
      });
      this.lines = this.register(new Value("Lines", this, false)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("ESP");
      });
      this.width = this.register(new Value("Width", this, 1.0F, 0.0F, 10.0F)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("ESP");
      });
      this.friendColor = this.register(new Value("Friends", this, true)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("ESP");
      });
      this.rainbow = this.register(new Value("Rainbow", this, false)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("ESP");
      });
      this.r = this.register(new Value("Red", this, 0, 0, 255)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("ESP");
      });
      this.g = this.register(new Value("Green", this, 255, 0, 255)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("ESP");
      });
      this.b = this.register(new Value("Blue", this, 255, 0, 255)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("ESP");
      });
      this.a = this.register(new Value("Alpha", this, 63, 0, 255)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("ESP");
      });
      players = this.register(new Value("Players", this, true));
      animals = this.register(new Value("Animals", this, false));
      mobs = this.register(new Value("Mobs", this, false));
      crystals = this.register(new Value("Crystals", this, true)).visibleWhen((var0) -> {
         return ((String)mode.getValue()).equalsIgnoreCase("ESP") || ((String)mode.getValue()).equalsIgnoreCase("Walls");
      });
   }

   @EventTarget
   public void renderPre(EventModelRender var1) {
      if (((String)mode.getValue()).equalsIgnoreCase("Walls")) {
         if (var1.entity instanceof EntityOtherPlayerMP && !(Boolean)players.getValue()) {
            return;
         }

         if (EntityUtil.isPassive(var1.entity) && !(Boolean)animals.getValue()) {
            return;
         }

         if (!EntityUtil.isPassive(var1.entity) && !(Boolean)mobs.getValue()) {
            return;
         }

         GlStateManager.pushMatrix();
         GL11.glDisable(2929);
         GL11.glColor4f((float)(Integer)this.Wr.getValue() / 255.0F, (float)(Integer)this.Wg.getValue() / 255.0F, (float)(Integer)this.Wb.getValue() / 255.0F, 1.0F);
         GL11.glDisable(3553);
         var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
         GL11.glEnable(2929);
         GL11.glColor4f((float)(Integer)this.Vr.getValue() / 255.0F, (float)(Integer)this.Vg.getValue() / 255.0F, (float)(Integer)this.Vb.getValue() / 255.0F, 1.0F);
         var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
         GL11.glEnable(3553);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.popMatrix();
         var1.setCancelled(true);
      } else if (((String)mode.getValue()).equalsIgnoreCase("ESP")) {
         Color var2 = (Boolean)this.friendColor.getValue() && Friends.isFriend(var1.entity.getName()) ? new Color(0.27F, 0.7F, 0.92F) : ((Boolean)this.rainbow.getValue() ? new Color(Xulu.rgb) : new Color((Integer)this.r.getValue(), (Integer)this.g.getValue(), (Integer)this.b.getValue()));
         if (var1.getEventState() == Event.State.PRE && !(var1.entity instanceof EntityOtherPlayerMP)) {
            if (EntityUtil.isPassive(var1.entity) && (Boolean)animals.getValue()) {
               GL11.glPushMatrix();
               GL11.glEnable(32823);
               GL11.glPolygonOffset(1.0F, -100000.0F);
               GL11.glPushAttrib(1048575);
               if (!(Boolean)this.lines.getValue()) {
                  GL11.glPolygonMode(1028, 6914);
               } else {
                  GL11.glPolygonMode(1028, 6913);
               }

               GL11.glDisable(3553);
               GL11.glDisable(2896);
               GL11.glDisable(2929);
               GL11.glEnable(2848);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               GL11.glColor4f((float)var2.getRed() / 255.0F, (float)var2.getGreen() / 255.0F, (float)var2.getBlue() / 255.0F, (float)(Integer)this.a.getValue() / 255.0F);
               if ((Boolean)this.lines.getValue()) {
                  GL11.glLineWidth((Float)this.width.getValue());
               }

               var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
               GL11.glPopAttrib();
               GL11.glPolygonOffset(1.0F, 100000.0F);
               GL11.glDisable(32823);
               GL11.glPopMatrix();
               var1.setCancelled(true);
            } else if (!EntityUtil.isPassive(var1.entity) && (Boolean)mobs.getValue()) {
               GL11.glPushMatrix();
               GL11.glEnable(32823);
               GL11.glPolygonOffset(1.0F, -100000.0F);
               GL11.glPushAttrib(1048575);
               if (!(Boolean)this.lines.getValue()) {
                  GL11.glPolygonMode(1028, 6914);
               } else {
                  GL11.glPolygonMode(1028, 6913);
               }

               GL11.glDisable(3553);
               GL11.glDisable(2896);
               GL11.glDisable(2929);
               GL11.glEnable(2848);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               GL11.glColor4f((float)var2.getRed() / 255.0F, (float)var2.getGreen() / 255.0F, (float)var2.getBlue() / 255.0F, (float)(Integer)this.a.getValue() / 255.0F);
               if ((Boolean)this.lines.getValue()) {
                  GL11.glLineWidth((Float)this.width.getValue());
               }

               var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
               GL11.glPopAttrib();
               GL11.glPolygonOffset(1.0F, 100000.0F);
               GL11.glDisable(32823);
               GL11.glPopMatrix();
               var1.setCancelled(true);
            }
         }
      }

   }

   @EventTarget
   public void onPlayerModel(EventModelPlayerRender var1) {
      if (((String)mode.getValue()).equalsIgnoreCase("ESP") && (Boolean)players.getValue()) {
         Color var2 = (Boolean)this.friendColor.getValue() && Friends.isFriend(var1.entity.getName()) ? new Color(0.27F, 0.7F, 0.92F) : ((Boolean)this.rainbow.getValue() ? new Color(Xulu.rgb) : new Color((Integer)this.r.getValue(), (Integer)this.g.getValue(), (Integer)this.b.getValue()));
         switch(var1.getEventState()) {
         case PRE:
            GL11.glPushMatrix();
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0F, -1.0E7F);
            GL11.glPushAttrib(1048575);
            if (!(Boolean)this.lines.getValue()) {
               GL11.glPolygonMode(1028, 6914);
            } else {
               GL11.glPolygonMode(1028, 6913);
            }

            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f((float)var2.getRed() / 255.0F, (float)var2.getGreen() / 255.0F, (float)var2.getBlue() / 255.0F, (float)(Integer)this.a.getValue() / 255.0F / 2.0F);
            if ((Boolean)this.lines.getValue()) {
               GL11.glLineWidth((Float)this.width.getValue());
            }
            break;
         case POST:
            GL11.glPopAttrib();
            GL11.glPolygonOffset(1.0F, 1.0E7F);
            GL11.glDisable(32823);
            GL11.glPopMatrix();
         }
      }

   }

   @EventTarget
   public void renderPost(EventPostRenderLayers var1) {
      if (((String)mode.getValue()).equalsIgnoreCase("ESP")) {
         if (!var1.renderer.bindEntityTexture(var1.entity)) {
            return;
         }

         Color var2 = (Boolean)this.friendColor.getValue() && Friends.isFriend(var1.entity.getName()) ? new Color(0.27F, 0.7F, 0.92F) : ((Boolean)this.rainbow.getValue() ? new Color(Xulu.rgb) : new Color((Integer)this.r.getValue(), (Integer)this.g.getValue(), (Integer)this.b.getValue()));
         if (var1.getEventState() == Event.State.PRE && var1.entity instanceof EntityOtherPlayerMP && (Boolean)players.getValue()) {
            GL11.glPushMatrix();
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0F, -100000.0F);
            GL11.glPushAttrib(1048575);
            if (!(Boolean)this.lines.getValue()) {
               GL11.glPolygonMode(1028, 6914);
            } else {
               GL11.glPolygonMode(1028, 6913);
            }

            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f((float)var2.getRed() / 255.0F, (float)var2.getGreen() / 255.0F, (float)var2.getBlue() / 255.0F, (float)(Integer)this.a.getValue() / 255.0F / 2.0F);
            if ((Boolean)this.lines.getValue()) {
               GL11.glLineWidth((Float)this.width.getValue());
            }

            var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
            GL11.glPopAttrib();
            GL11.glPolygonOffset(1.0F, 100000.0F);
            GL11.glDisable(32823);
            GL11.glPopMatrix();
         }
      }

   }
}
