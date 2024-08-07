package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.StorageESP;
import com.elementars.eclient.util.OutlineUtils;
import com.google.common.collect.Maps;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.ReportedException;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TileEntityRendererDispatcher.class})
public class MixinTileEntityRenderDispatcher {
   @Shadow
   public final Map renderers = Maps.newHashMap();
   @Shadow
   private Tessellator batchBuffer = new Tessellator(2097152);
   @Shadow
   private boolean drawingBatch = false;

   @Shadow
   @Nullable
   public TileEntitySpecialRenderer getRenderer(@Nullable TileEntity var1) {
      return var1 != null && !var1.isInvalid() ? this.getRenderer(var1.getClass()) : null;
   }

   @Shadow
   public TileEntitySpecialRenderer getRenderer(Class var1) {
      TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer)this.renderers.get(var1);
      if (var2 == null && var1 != TileEntity.class) {
         var2 = this.getRenderer(var1.getSuperclass());
         this.renderers.put(var1, var2);
      }

      return var2;
   }

   @Inject(
      method = {"render(Lnet/minecraft/tileentity/TileEntity;DDDFIF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void Irender(TileEntity var1, double var2, double var4, double var6, float var8, int var9, float var10, CallbackInfo var11) {
      TileEntitySpecialRenderer var12 = this.getRenderer(var1);
      if (var12 != null) {
         try {
            StorageESP var13 = (StorageESP)Xulu.MODULE_MANAGER.getModuleByName("StorageESP");
            Value var17 = Xulu.VALUE_MANAGER.getValueByMod(var13, "All Tile Entities");
            if (var17 != null && !(Boolean)var17.getValue() && !(var1 instanceof TileEntityChest) && !(var1 instanceof TileEntityEnderChest) && !(var1 instanceof TileEntityShulkerBox)) {
               return;
            }

            if (var13 != null && var13.isToggled() && ((String)Xulu.VALUE_MANAGER.getValueByMod(var13, "Mode").getValue()).equalsIgnoreCase("Shader") && var1.hasWorld()) {
               Color var18;
               if ((Boolean)Xulu.VALUE_MANAGER.getValueByMod(var13, "Future Colors").getValue()) {
                  var18 = new Color(StorageESP.getTileEntityColorF(var1));
               } else {
                  var18 = new Color(StorageESP.getTileEntityColor(var1));
               }

               if (this.drawingBatch && var1.hasFastRenderer()) {
                  GL11.glLineWidth(5.0F);
                  var12.renderTileEntityFast(var1, var2, var4, var6, var8, var9, var10, this.batchBuffer.getBuffer());
                  OutlineUtils.renderOne((Float)Xulu.VALUE_MANAGER.getValueByMod(var13, "Line Width").getValue());
                  var12.renderTileEntityFast(var1, var2, var4, var6, var8, var9, var10, this.batchBuffer.getBuffer());
                  OutlineUtils.renderTwo();
                  var12.renderTileEntityFast(var1, var2, var4, var6, var8, var9, var10, this.batchBuffer.getBuffer());
                  OutlineUtils.renderThree();
                  OutlineUtils.renderFour(var18);
                  var12.renderTileEntityFast(var1, var2, var4, var6, var8, var9, var10, this.batchBuffer.getBuffer());
                  OutlineUtils.renderFive();
               } else {
                  GL11.glLineWidth(5.0F);
                  var12.render(var1, var2, var4, var6, var8, var9, var10);
                  OutlineUtils.renderOne((Float)Xulu.VALUE_MANAGER.getValueByMod(var13, "Line Width").getValue());
                  var12.render(var1, var2, var4, var6, var8, var9, var10);
                  OutlineUtils.renderTwo();
                  var12.render(var1, var2, var4, var6, var8, var9, var10);
                  OutlineUtils.renderThree();
                  OutlineUtils.renderFour(var18);
                  var12.render(var1, var2, var4, var6, var8, var9, var10);
                  OutlineUtils.renderFive();
               }
            }

            if (this.drawingBatch && var1.hasFastRenderer()) {
               var12.renderTileEntityFast(var1, var2, var4, var6, var8, var9, var10, this.batchBuffer.getBuffer());
            } else {
               var12.render(var1, var2, var4, var6, var8, var9, var10);
            }
         } catch (Throwable var16) {
            CrashReport var14 = CrashReport.makeCrashReport(var16, "Rendering Block Entity");
            CrashReportCategory var15 = var14.makeCategory("Block Entity Details");
            var1.addInfoToCrashReport(var15);
            throw new ReportedException(var14);
         }
      }

   }
}
