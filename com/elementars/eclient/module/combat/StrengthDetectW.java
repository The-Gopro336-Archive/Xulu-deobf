package com.elementars.eclient.module.combat;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventDrinkPotion;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.EntityUtil;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class StrengthDetectW extends Module {
   // $FF: synthetic field
   public Set strengthedPlayers;
   // $FF: synthetic field
   public Set renderPlayers;

   @EventTarget
   public void onItem(EventDrinkPotion var1) {
      Command.sendChatMessage("drink event");
      if (var1.getEntityLivingBase() instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)var1.getEntityLivingBase();
         if (var1.getStack().getItem() == Items.POTIONITEM) {
            Iterator var3 = PotionUtils.getEffectsFromStack(var1.getStack()).iterator();

            while(var3.hasNext()) {
               PotionEffect var4 = (PotionEffect)var3.next();
               if (var4.getPotion().equals(MobEffects.STRENGTH)) {
                  this.strengthedPlayers.add(var2);
               }
            }
         }
      }

   }

   @SubscribeEvent
   public void onUseItem(Finish var1) {
      Command.sendChatMessage("finish use item");
      if (var1.getEntityLiving() instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)var1.getEntityLiving();
         if (var1.getItem().getItem() == Items.POTIONITEM) {
            Iterator var3 = PotionUtils.getEffectsFromStack(var1.getItem()).iterator();

            while(var3.hasNext()) {
               PotionEffect var4 = (PotionEffect)var3.next();
               if (var4.getPotion().equals(MobEffects.STRENGTH)) {
                  this.strengthedPlayers.add(var2);
               }
            }
         }
      }

   }

   public void onDisable() {
      Xulu.EVENT_MANAGER.unregister(this);
      EVENT_BUS.unregister(this);
   }

   public void onUpdate() {
      if (StrengthDetect.mc.player != null) {
         Iterator var1 = StrengthDetect.mc.world.playerEntities.iterator();

         while(var1.hasNext()) {
            EntityPlayer var2 = (EntityPlayer)var1.next();
            if (EntityUtil.isLiving(var2) && !(var2.getHealth() <= 0.0F)) {
               if (var2.isPotionActive(MobEffects.STRENGTH) && !this.strengthedPlayers.contains(var2)) {
                  Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Â§4[").append(var2.getDisplayNameString()).append("]Â§r is now strong")));
                  this.strengthedPlayers.add(var2);
               }

               if (this.strengthedPlayers.contains(var2) && !var2.isPotionActive(MobEffects.STRENGTH)) {
                  Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Â§3[").append(var2.getDisplayNameString()).append("]Â§r is no longer strong")));
                  this.strengthedPlayers.remove(var2);
               }

               this.checkRender();
            }
         }

      }
   }

   public void onWorldRender(RenderEvent var1) {
      if (mc.getRenderManager().options != null) {
         boolean var2 = mc.getRenderManager().options.thirdPersonView == 2;
         float var3 = mc.getRenderManager().playerViewY;
         Iterator var4 = this.strengthedPlayers.iterator();

         while(var4.hasNext()) {
            EntityPlayer var5 = (EntityPlayer)var4.next();
            if (var5.getName() == StrengthDetect.mc.player.getName()) {
               return;
            }

            GlStateManager.pushMatrix();
            Vec3d var6 = EntityUtil.getInterpolatedPos(var5, var1.getPartialTicks());
            GlStateManager.translate(var6.x - StrengthDetect.mc.getRenderManager().renderPosX, var6.y - StrengthDetect.mc.getRenderManager().renderPosY, var6.z - StrengthDetect.mc.getRenderManager().renderPosZ);
            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-var3, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float)(var2 ? -1 : 1), 1.0F, 0.0F, 0.0F);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
            GL11.glColor3f(1.0F, 0.2F, 0.2F);
            GlStateManager.disableTexture2D();
            GL11.glLineWidth(4.0F);
            GL11.glEnable(2848);
            GL11.glBegin(2);
            GL11.glVertex2d((double)(-var5.width / 2.0F), 0.0D);
            GL11.glVertex2d((double)(-var5.width / 2.0F), (double)var5.height);
            GL11.glVertex2d((double)(var5.width / 2.0F), (double)var5.height);
            GL11.glVertex2d((double)(var5.width / 2.0F), 0.0D);
            GL11.glEnd();
            GlStateManager.popMatrix();
         }

         GlStateManager.enableDepth();
         GlStateManager.depthMask(true);
         GlStateManager.disableTexture2D();
         GlStateManager.enableBlend();
         GlStateManager.disableAlpha();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.shadeModel(7425);
         GlStateManager.disableDepth();
         GlStateManager.enableCull();
         GlStateManager.glLineWidth(1.0F);
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
      }
   }

   public void checkRender() {
      try {
         this.renderPlayers.clear();
         Iterator var1 = StrengthDetect.mc.world.playerEntities.iterator();

         EntityPlayer var2;
         while(var1.hasNext()) {
            var2 = (EntityPlayer)var1.next();
            if (EntityUtil.isLiving(var2) && !(var2.getHealth() <= 0.0F)) {
               this.renderPlayers.add(var2);
            }
         }

         var1 = this.strengthedPlayers.iterator();

         while(var1.hasNext()) {
            var2 = (EntityPlayer)var1.next();
            if (!this.renderPlayers.contains(var2)) {
               Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Â§3[").append(var2.getDisplayNameString()).append("]Â§r is (probably) no longer strong")));
               this.strengthedPlayers.remove(var2);
            }
         }
      } catch (Exception var3) {
      }

   }

   @EventTarget
   public void onPacket(EventReceivePacket var1) {
      EntityPlayer var3;
      if (var1.getPacket() instanceof SPacketEntityEffect) {
         Command.sendChatMessage("OO");
         SPacketEntityEffect var2 = (SPacketEntityEffect)var1.getPacket();
         if (Potion.getPotionById(var2.getEffectId()) == MobEffects.STRENGTH) {
            Command.sendChatMessage("is this strength");
            var3 = mc.world.getEntityByID(var2.getEntityId()) instanceof EntityPlayer ? (EntityPlayer)mc.world.getEntityByID(var2.getEntityId()) : null;
            if (var3 != null) {
               Command.sendChatMessage("we got a player right?");
               this.strengthedPlayers.add(var3);
            }
         }
      }

      if (var1.getPacket() instanceof SPacketEntityStatus) {
         Command.sendChatMessage("status");
         SPacketEntityStatus var7 = (SPacketEntityStatus)var1.getPacket();
         if (var7.getOpCode() == 9 && var7.getEntity(world) instanceof EntityPlayer) {
            Command.sendChatMessage("use item status");
            var3 = (EntityPlayer)var7.getEntity(world);
            if (var3.getHeldItemMainhand().getItem() == Items.POTIONITEM) {
               Command.sendChatMessage("holding a potion?");
               Iterator var4 = PotionUtils.getEffectsFromStack(var3.getHeldItemMainhand()).iterator();

               while(var4.hasNext()) {
                  PotionEffect var5 = (PotionEffect)var4.next();
                  if (var5.getPotion().equals(MobEffects.STRENGTH)) {
                     Command.sendChatMessage("we got strength");
                     this.strengthedPlayers.add(var3);
                  }
               }
            }
         }
      }

   }

   public StrengthDetectW() {
      super("StrengthDetectW", "test", 0, Category.COMBAT, true);
   }

   public void onEnable() {
      this.strengthedPlayers = new HashSet();
      this.renderPlayers = new HashSet();
      Xulu.EVENT_MANAGER.register(this);
      EVENT_BUS.register(this);
   }
}
