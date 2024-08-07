package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.AngleHelper;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.Plane;
import com.elementars.eclient.util.VectorUtils;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Arrows extends Module {
   // $FF: synthetic field
   public final Value Fb;
   // $FF: synthetic field
   public final Value mode;
   // $FF: synthetic field
   public final Value green;
   // $FF: synthetic field
   public final Value Fg;
   // $FF: synthetic field
   public final Value hostile;
   // $FF: synthetic field
   public final Value black;
   // $FF: synthetic field
   public final Value blue;
   // $FF: synthetic field
   public final Value colorMode;
   // $FF: synthetic field
   public final Value sizeY;
   // $FF: synthetic field
   public final Value friendly;
   // $FF: synthetic field
   public final Value sizeX;
   // $FF: synthetic field
   public final Value red;
   // $FF: synthetic field
   public final Value alpha;
   // $FF: synthetic field
   public final Value players;
   // $FF: synthetic field
   public final Value sizeT;
   // $FF: synthetic field
   public final Value antialias;
   // $FF: synthetic field
   public final Value rainbow;
   // $FF: synthetic field
   public final Value outline;
   // $FF: synthetic field
   public final Value Fr;

   public void onRender() {
      ScaledResolution var1 = new ScaledResolution(mc);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.disableTexture2D();
      GlStateManager.enableAlpha();
      if ((Boolean)this.antialias.getValue()) {
         GL11.glEnable(2881);
         GL11.glEnable(2848);
      }

      Arrows.Mode var2 = (Arrows.Mode)this.mode.getValue();
      double var3 = var1.getScaledWidth_double() / 2.0D;
      double var5 = var1.getScaledHeight_double() / 2.0D;
      Stream var10000 = mc.world.loadedEntityList.stream().filter((var0) -> {
         return !Objects.equals(var0, mc.player);
      });
      EntityLivingBase.class.getClass();
      var10000.filter(EntityLivingBase.class::isInstance).map((var1x) -> {
         return new Arrows.EntityRelations(var1x);
      }).filter((var0) -> {
         return !var0.getRelationship().equals(Arrows.RelationState.INVALID);
      }).filter(Arrows.EntityRelations::isOptionEnabled).forEach((var7) -> {
         Entity var8 = var7.getEntity();
         Arrows.RelationState var9 = var7.getRelationship();
         Vec3d var10 = EntityUtil.getInterpolatedEyePos(var8, mc.getRenderPartialTicks());
         Plane var11 = VectorUtils.toScreen(var10);
         Color var12 = var7.getColor();
         if (((String)this.colorMode.getValue()).equalsIgnoreCase("Tracers")) {
            float var13 = mc.player.getDistance(var8);
            Color var14;
            if (var13 <= 32.0F) {
               var14 = new Color(1.0F - var13 / 32.0F / 2.0F, var13 / 32.0F, 0.0F);
               GL11.glColor4f((float)var14.getRed() / 255.0F, (float)var14.getGreen() / 255.0F, (float)var14.getBlue() / 255.0F, (float)(Integer)this.alpha.getValue() / 255.0F);
            } else {
               var14 = new Color(0.0F, 0.9F, 0.0F);
               GL11.glColor4f((float)var14.getRed() / 255.0F, (float)var14.getGreen() / 255.0F, (float)var14.getBlue() / 255.0F, (float)(Integer)this.alpha.getValue() / 255.0F);
            }
         } else {
            GL11.glColor4f((float)var12.getRed() / 255.0F, (float)var12.getGreen() / 255.0F, (float)var12.getBlue() / 255.0F, (float)(Integer)this.alpha.getValue() / 255.0F);
         }

         GlStateManager.translate(0.0F, 0.0F, var7.getDepth());
         if ((var2.equals(Arrows.Mode.BOTH) || var2.equals(Arrows.Mode.ARROWS)) && !var11.isVisible()) {
            double var48 = var3 - (double)(Integer)this.sizeX.getValue();
            double var15 = var5 - (double)(Integer)this.sizeY.getValue();
            double var17 = (var11.getX() - var3) / var48;
            double var19 = (var11.getY() - var5) / var15;
            double var21 = Math.abs(Math.sqrt(var17 * var17 + var19 * var19));
            double var23 = var17 / var21;
            double var25 = var19 / var21;
            double var27 = var3 + var23 * var48;
            double var29 = var5 + var25 * var15;
            double var31 = var27 - var3;
            double var33 = var29 - var5;
            double var35 = var1.getScaledWidth_double();
            double var37 = 0.0D;
            double var39 = Math.sqrt(var35 * var35 + var37 * var37);
            double var41 = Math.sqrt(var31 * var31 + var33 * var33);
            double var43 = Math.toDegrees(Math.acos((var35 * var31 + var37 * var33) / (var39 * var41)));
            if (var43 == Double.NaN) {
               var43 = 0.0D;
            }

            if (var29 < var5) {
               var43 *= -1.0D;
            }

            var43 = (double)((float)AngleHelper.normalizeInDegrees(var43));
            int var45 = var9.equals(Arrows.RelationState.PLAYER) ? 8 : 5;
            GlStateManager.pushMatrix();
            GlStateManager.translate(var27, var29, 0.0D);
            GlStateManager.rotate((float)var43, 0.0F, 0.0F, (float)var45 / 2.0F);
            if (((String)this.colorMode.getValue()).equalsIgnoreCase("Tracers")) {
               float var46 = mc.player.getDistance(var8);
               Color var47;
               if (var46 <= 32.0F) {
                  var47 = new Color(1.0F - var46 / 32.0F / 2.0F, var46 / 32.0F, 0.0F);
                  GL11.glColor4f((float)var47.getRed() / 255.0F, (float)var47.getGreen() / 255.0F, (float)var47.getBlue() / 255.0F, (float)(Integer)this.alpha.getValue() / 255.0F);
               } else {
                  var47 = new Color(0.0F, 0.9F, 0.0F);
                  GL11.glColor4f((float)var47.getRed() / 255.0F, (float)var47.getGreen() / 255.0F, (float)var47.getBlue() / 255.0F, (float)(Integer)this.alpha.getValue() / 255.0F);
               }
            } else {
               GL11.glColor4f((float)var12.getRed() / 255.0F, (float)var12.getGreen() / 255.0F, (float)var12.getBlue() / 255.0F, (float)(Integer)this.alpha.getValue() / 255.0F);
            }

            GlStateManager.glBegin(4);
            GL11.glVertex2d(0.0D, 0.0D);
            GL11.glVertex2d((double)(-var45), (double)((float)(-var45) / (Float)this.sizeT.getValue()));
            GL11.glVertex2d((double)(-var45), (double)((float)var45 / (Float)this.sizeT.getValue()));
            GlStateManager.glEnd();
            if ((Boolean)this.outline.getValue()) {
               GL11.glPushAttrib(1048575);
               GL11.glPolygonMode(1032, 6913);
               GL11.glLineWidth(1.5F);
               if ((Boolean)this.black.getValue()) {
                  GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
               }

               GlStateManager.glBegin(4);
               GL11.glVertex2d(0.0D, 0.0D);
               GL11.glVertex2d((double)(-var45), (double)((float)(-var45) / (Float)this.sizeT.getValue()));
               GL11.glVertex2d((double)(-var45), (double)((float)var45 / (Float)this.sizeT.getValue()));
               GlStateManager.glEnd();
               GL11.glPopAttrib();
            }

            GlStateManager.popMatrix();
         }

         if (var2.equals(Arrows.Mode.BOTH) || var2.equals(Arrows.Mode.LINES)) {
            GlStateManager.glBegin(1);
            GL11.glVertex2d(var3, var5);
            GL11.glVertex2d(var11.getX(), var11.getY());
            GlStateManager.glEnd();
         }

         GlStateManager.translate(0.0F, 0.0F, -var7.getDepth());
      });
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GL11.glDisable(2881);
      GL11.glDisable(2848);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public Arrows() {
      super("Arrows", "2d Tracers", 0, Category.RENDER, true);
      this.mode = this.register(new Value("Mode", this, Arrows.Mode.ARROWS, Arrows.Mode.values()));
      this.sizeX = this.register(new Value("Dimension X", this, 450, 0, 1000));
      this.sizeY = this.register(new Value("Dimension Y", this, 285, 0, 1000));
      this.sizeT = this.register(new Value("Triangle Size", this, 2.0F, 1.0F, 5.0F));
      this.rainbow = this.register(new Value("Rainbow Players", this, false));
      this.colorMode = this.register(new Value("Color Mode", this, "RGB", new String[]{"RGB", "Tracers"}));
      this.red = this.register(new Value("Player Red", this, 0, 0, 255)).visibleWhen((var1) -> {
         return ((String)this.colorMode.getValue()).equalsIgnoreCase("RGB");
      });
      this.green = this.register(new Value("Player Green", this, 255, 0, 255)).visibleWhen((var1) -> {
         return ((String)this.colorMode.getValue()).equalsIgnoreCase("RGB");
      });
      this.blue = this.register(new Value("Player Blue", this, 0, 0, 255)).visibleWhen((var1) -> {
         return ((String)this.colorMode.getValue()).equalsIgnoreCase("RGB");
      });
      this.Fr = this.register(new Value("Friend Red", this, 0, 0, 255));
      this.Fg = this.register(new Value("Friend Green", this, 200, 0, 255));
      this.Fb = this.register(new Value("Friend Blue", this, 255, 0, 255));
      this.alpha = this.register(new Value("Alpha", this, 255, 0, 255));
      this.outline = this.register(new Value("Outline", this, false));
      this.black = this.register(new Value("Black Outline", this, true)).visibleWhen((var1) -> {
         return (Boolean)this.outline.getValue();
      });
      this.antialias = this.register(new Value("Antialias", this, true));
      this.players = this.register(new Value("Players", this, true));
      this.hostile = this.register(new Value("Mobs", this, true));
      this.friendly = this.register(new Value("Animals", this, true));
   }

   static enum Mode {
      // $FF: synthetic field
      LINES,
      // $FF: synthetic field
      BOTH,
      // $FF: synthetic field
      ARROWS;
   }

   private class EntityRelations implements Comparable {
      // $FF: synthetic field
      private final Entity entity;
      // $FF: synthetic field
      private final Arrows.RelationState relationship;

      public float getDepth() {
         switch(this.relationship) {
         case PLAYER:
            return 15.0F;
         case HOSTILE:
            return 10.0F;
         default:
            return 0.0F;
         }
      }

      public Color getColor() {
         switch(this.relationship) {
         case PLAYER:
            if (Friends.isFriend(this.getEntity().getName())) {
               return new Color((Integer)Arrows.this.Fr.getValue(), (Integer)Arrows.this.Fg.getValue(), (Integer)Arrows.this.Fb.getValue());
            } else {
               if ((Boolean)Arrows.this.rainbow.getValue()) {
                  return new Color(Xulu.rgb);
               }

               return new Color((Integer)Arrows.this.red.getValue(), (Integer)Arrows.this.green.getValue(), (Integer)Arrows.this.blue.getValue());
            }
         case HOSTILE:
            return Color.RED;
         default:
            return Color.YELLOW;
         }
      }

      public int compareTo(Arrows.EntityRelations var1) {
         return this.getRelationship().compareTo(var1.getRelationship());
      }

      public Entity getEntity() {
         return this.entity;
      }

      public Arrows.RelationState getRelationship() {
         return this.relationship;
      }

      public boolean isOptionEnabled() {
         switch(this.relationship) {
         case PLAYER:
            return (Boolean)Arrows.this.players.getValue();
         case HOSTILE:
            return (Boolean)Arrows.this.hostile.getValue();
         default:
            return (Boolean)Arrows.this.friendly.getValue();
         }
      }

      public EntityRelations(Entity var2) {
         Objects.requireNonNull(var2);
         this.entity = var2;
         if (EntityUtil.isFakeLocalPlayer(var2)) {
            this.relationship = Arrows.RelationState.INVALID;
         } else if (var2 instanceof EntityPlayer) {
            this.relationship = Arrows.RelationState.PLAYER;
         } else if (EntityUtil.isPassive(var2)) {
            this.relationship = Arrows.RelationState.FRIENDLY;
         } else {
            this.relationship = Arrows.RelationState.HOSTILE;
         }

      }
   }

   public static enum RelationState {
      // $FF: synthetic field
      INVALID,
      // $FF: synthetic field
      HOSTILE,
      // $FF: synthetic field
      FRIENDLY,
      // $FF: synthetic field
      PLAYER;
   }
}
