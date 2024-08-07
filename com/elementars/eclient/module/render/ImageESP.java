package com.elementars.eclient.module.render;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.VectorUtils;
import dev.xulu.settings.Value;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import javax.imageio.ImageIO;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ImageESP extends Module {
   // $FF: synthetic field
   private ResourceLocation waifu;
   // $FF: synthetic field
   private final Value noRenderPlayers = this.register(new Value("No Render Players", this, false));
   // $FF: synthetic field
   private ICamera camera;
   // $FF: synthetic field
   private final Value imageUrl;

   public void onDisable() {
      EVENT_BUS.unregister(this);
   }

   private boolean shouldDraw(EntityLivingBase var1) {
      return !var1.equals(mc.player) && var1.getHealth() > 0.0F && EntityUtil.isPlayer(var1);
   }

   private BufferedImage getImage(Object var1, ImageESP.ThrowingFunction var2) {
      try {
         return (BufferedImage)var2.apply(var1);
      } catch (IOException var5) {
         var5.printStackTrace();
         return null;
      }
   }

   public void onLoad() {
      BufferedImage var1 = null;

      try {
         if (this.getFile(((ImageESP.CachedImage)this.imageUrl.getValue()).getName()) != null) {
            var1 = this.getImage(this.getFile(((ImageESP.CachedImage)this.imageUrl.getValue()).getName()), ImageIO::read);
         }

         if (var1 == null) {
            this.LOGGER.warn("Failed to load image");
         } else {
            DynamicTexture var2 = new DynamicTexture(var1);
            var2.loadTexture(mc.getResourceManager());
            this.waifu = mc.getTextureManager().getDynamicTextureLocation(String.valueOf((new StringBuilder()).append("XULU_").append(((ImageESP.CachedImage)this.imageUrl.getValue()).name())), var2);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   private InputStream getFile(String var1) {
      return ImageESP.class.getResourceAsStream(var1);
   }

   public void onEnable() {
      EVENT_BUS.register(this);
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onRenderGameOverlayEvent(Text var1) {
      if (this.waifu != null) {
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
            VectorUtils.ScreenPos var14;
            VectorUtils.ScreenPos var15;
            do {
               EntityPlayer var10;
               do {
                  do {
                     do {
                        if (!var9.hasNext()) {
                           return;
                        }

                        var10 = (EntityPlayer)var9.next();
                     } while(var10 == mc.getRenderViewEntity());
                  } while(!var10.isEntityAlive());
               } while(!this.camera.isBoundingBoxInFrustum(var10.getEntityBoundingBox()));

               Vec3d var12 = EntityUtil.getInterpolatedPos(var10, var1.getPartialTicks());
               Vec3d var13 = var12.add(new Vec3d(0.0D, var10.getRenderBoundingBox().maxY - var10.posY, 0.0D));
               var14 = VectorUtils._toScreen(var13.x, var13.y, var13.z);
               var15 = VectorUtils._toScreen(var12.x, var12.y, var12.z);
            } while(!var14.isVisible && !var15.isVisible);

            int var16;
            int var17 = var16 = var15.y - var14.y;
            int var18 = (int)((double)var14.x - (double)var16 / 1.8D);
            int var19 = var14.y;
            mc.renderEngine.bindTexture(this.waifu);
            GlStateManager.color(255.0F, 255.0F, 255.0F);
            Gui.drawScaledCustomSizeModalRect(var18, var19, 0.0F, 0.0F, var16, var17, var16, var17, (float)var16, (float)var17);
         }
      }
   }

   @SubscribeEvent
   public void onRenderPlayer(Pre var1) {
      if ((Boolean)this.noRenderPlayers.getValue() && !var1.getEntity().equals(mc.player)) {
         var1.setCanceled(true);
      }

   }

   public ImageESP() {
      super("ImageESP", "overlay cute images over players", 0, Category.RENDER, true);
      this.imageUrl = this.register(new Value("Image", this, ImageESP.CachedImage.LAUREN, ImageESP.CachedImage.values())).onChanged((var1) -> {
         this.waifu = null;
         this.onLoad();
      });
      this.camera = new Frustum();
      this.onLoad();
   }

   @FunctionalInterface
   private interface ThrowingFunction {
      Object apply(Object var1) throws IOException;
   }

   private static enum CachedImage {
      // $FF: synthetic field
      LAUREN("/images/lauren.png");

      // $FF: synthetic field
      String name;
      // $FF: synthetic field
      WAIFU("/images/waifu.png"),
      // $FF: synthetic field
      LOTUS("/images/lotus.png"),
      // $FF: synthetic field
      DELTA("/images/delta.png"),
      // $FF: synthetic field
      WAIFU2("/images/waifu2.png"),
      // $FF: synthetic field
      ZHN("/images/zhn.png"),
      // $FF: synthetic field
      LOGAN("/images/logan.png"),
      // $FF: synthetic field
      XULU("/images/xulutransparent.png"),
      // $FF: synthetic field
      PETER("/images/peter.png"),
      // $FF: synthetic field
      BIGOUNCE("/images/bigounce.png"),
      // $FF: synthetic field
      TRIANGLE("/images/triangle.png"),
      // $FF: synthetic field
      JOINT("/images/joint.png"),
      // $FF: synthetic field
      OMEGA("/images/omega.png");

      public String getName() {
         return this.name;
      }

      private CachedImage(String var3) {
         this.name = var3;
      }
   }
}
