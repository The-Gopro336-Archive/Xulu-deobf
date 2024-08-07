package com.elementars.eclient.module.player;

import com.elementars.eclient.event.Event;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventClickBlock;
import com.elementars.eclient.event.events.EventPlayerDamageBlock;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.combat.MiddleClickPearl;
import com.elementars.eclient.util.Timer;
import dev.xulu.settings.Value;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class Speedmine extends Module {
   // $FF: synthetic field
   private final Value boxAlpha;
   // $FF: synthetic field
   private boolean isMining;
   // $FF: synthetic field
   public Value noDelay;
   // $FF: synthetic field
   public Value outline;
   // $FF: synthetic field
   public Value noBreakAnim;
   // $FF: synthetic field
   public Value pickaxe;
   // $FF: synthetic field
   private final Value lineWidth;
   // $FF: synthetic field
   public Value mode;
   // $FF: synthetic field
   public Value doubleBreak;
   // $FF: synthetic field
   public Value allow;
   // $FF: synthetic field
   public IBlockState currentBlockState;
   // $FF: synthetic field
   private final Timer timer;
   // $FF: synthetic field
   public Value reset;
   // $FF: synthetic field
   public Value damage;
   // $FF: synthetic field
   public Value noSwing;
   // $FF: synthetic field
   private BlockPos lastPos;
   // $FF: synthetic field
   private static Speedmine INSTANCE = new Speedmine();
   // $FF: synthetic field
   public Value webSwitch;
   // $FF: synthetic field
   public BlockPos currentPos;
   // $FF: synthetic field
   private EnumFacing lastFacing;
   // $FF: synthetic field
   public Value box;
   // $FF: synthetic field
   public Value tweaks = this.register(new Value("Tweaks", this, true));
   // $FF: synthetic field
   public Value noTrace;
   // $FF: synthetic field
   public Value render;

   @EventTarget
   public void onBlockEvent(EventPlayerDamageBlock var1) {
      if (mc.player != null && mc.world != null) {
         if ((Boolean)this.tweaks.getValue()) {
            if (canBreak(var1.getPos())) {
               if ((Boolean)this.reset.getValue()) {
                  mc.playerController.isHittingBlock = false;
               }

               switch((Speedmine.Mode)this.mode.getValue()) {
               case PACKET:
                  if (this.currentPos == null) {
                     this.currentPos = var1.getPos();
                     this.currentBlockState = mc.world.getBlockState(this.currentPos);
                     this.timer.reset();
                  }

                  mc.player.swingArm(EnumHand.MAIN_HAND);
                  mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.START_DESTROY_BLOCK, var1.getPos(), var1.getFacing()));
                  mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.STOP_DESTROY_BLOCK, var1.getPos(), var1.getFacing()));
                  var1.setCancelled(true);
                  break;
               case DAMAGE:
                  if (mc.playerController.curBlockDamageMP >= (Float)this.damage.getValue()) {
                     mc.playerController.curBlockDamageMP = 1.0F;
                  }
                  break;
               case INSTANT:
                  mc.player.swingArm(EnumHand.MAIN_HAND);
                  mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.START_DESTROY_BLOCK, var1.getPos(), var1.getFacing()));
                  mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.STOP_DESTROY_BLOCK, var1.getPos(), var1.getFacing()));
                  mc.playerController.onPlayerDestroyBlock(var1.getPos());
                  mc.world.setBlockToAir(var1.getPos());
               }
            }

            if ((Boolean)this.doubleBreak.getValue()) {
               BlockPos var2 = var1.getPos().add(0, 1, 0);
               if (canBreak(var2) && mc.player.getDistance((double)var2.getX(), (double)var2.getY(), (double)var2.getZ()) <= 5.0D) {
                  mc.player.swingArm(EnumHand.MAIN_HAND);
                  mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.START_DESTROY_BLOCK, var2, var1.getFacing()));
                  mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.STOP_DESTROY_BLOCK, var2, var1.getFacing()));
                  mc.playerController.onPlayerDestroyBlock(var2);
                  mc.world.setBlockToAir(var2);
               }
            }
         }

      }
   }

   public void showAnimation() {
      this.showAnimation(false, (BlockPos)null, (EnumFacing)null);
   }

   public static boolean canBreak(BlockPos var0) {
      IBlockState var1 = mc.world.getBlockState(var0);
      Block var2 = var1.getBlock();
      return var2.getBlockHardness(var1, mc.world, var0) != -1.0F;
   }

   public void onUpdate() {
      if (mc.world != null && mc.player != null) {
         if (this.currentPos != null) {
            if (mc.world.getBlockState(this.currentPos).equals(this.currentBlockState) && mc.world.getBlockState(this.currentPos).getBlock() != Blocks.AIR) {
               if ((Boolean)this.webSwitch.getValue() && this.currentBlockState.getBlock() == Blocks.WEB && mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
                  MiddleClickPearl.switchToHotbarSlot(ItemSword.class, false);
               }
            } else {
               this.currentPos = null;
               this.currentBlockState = null;
            }
         }

         if ((Boolean)this.noDelay.getValue()) {
            mc.playerController.blockHitDelay = 0;
         }

         if (this.isMining && this.lastPos != null && this.lastFacing != null && (Boolean)this.noBreakAnim.getValue()) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.ABORT_DESTROY_BLOCK, this.lastPos, this.lastFacing));
         }

         if ((Boolean)this.reset.getValue() && mc.gameSettings.keyBindUseItem.isKeyDown() && !(Boolean)this.allow.getValue()) {
            mc.playerController.isHittingBlock = false;
         }

      }
   }

   @EventTarget
   public void onClickBlock(EventClickBlock var1) {
      if (mc.player != null && mc.world != null) {
         if ((Boolean)this.reset.getValue() && mc.playerController.curBlockDamageMP > 0.1F) {
            mc.playerController.isHittingBlock = true;
         }

      }
   }

   private void setInstance() {
      INSTANCE = this;
   }

   public static Speedmine getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new Speedmine();
      }

      return INSTANCE;
   }

   private void showAnimation(boolean var1, BlockPos var2, EnumFacing var3) {
      this.isMining = var1;
      this.lastPos = var2;
      this.lastFacing = var3;
   }

   @EventTarget
   public void onPacketSend(EventSendPacket var1) {
      if (mc.world != null && mc.player != null) {
         if (var1.getEventState() == Event.State.PRE) {
            if ((Boolean)this.noSwing.getValue() && var1.getPacket() instanceof CPacketAnimation) {
               var1.setCancelled(true);
            }

            if ((Boolean)this.noBreakAnim.getValue() && var1.getPacket() instanceof CPacketPlayerDigging) {
               CPacketPlayerDigging var2 = (CPacketPlayerDigging)var1.getPacket();
               if (var2 != null && var2.getPosition() != null) {
                  try {
                     Iterator var3 = mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(var2.getPosition())).iterator();

                     while(var3.hasNext()) {
                        Entity var4 = (Entity)var3.next();
                        if (var4 instanceof EntityEnderCrystal) {
                           this.showAnimation();
                           return;
                        }
                     }
                  } catch (Exception var5) {
                  }

                  if (var2.getAction().equals(Action.START_DESTROY_BLOCK)) {
                     this.showAnimation(true, var2.getPosition(), var2.getFacing());
                  }

                  if (var2.getAction().equals(Action.STOP_DESTROY_BLOCK)) {
                     this.showAnimation();
                  }
               }
            }
         }

      }
   }

   public Speedmine() {
      super("Speedmine", "Speeds up mining.", 0, Category.PLAYER, true);
      this.mode = this.register(new Value("Mode", this, Speedmine.Mode.PACKET, Speedmine.Mode.values())).visibleWhen((var1) -> {
         return (Boolean)this.tweaks.getValue();
      });
      this.reset = this.register(new Value("Reset", this, true));
      this.damage = this.register(new Value("Damage", this, 0.7F, 0.0F, 1.0F)).visibleWhen((var1) -> {
         return this.mode.getValue() == Speedmine.Mode.DAMAGE && (Boolean)this.tweaks.getValue();
      });
      this.noBreakAnim = this.register(new Value("NoBreakAnim", this, false));
      this.noDelay = this.register(new Value("NoDelay", this, false));
      this.noSwing = this.register(new Value("NoSwing", this, false));
      this.noTrace = this.register(new Value("NoTrace", this, false));
      this.allow = this.register(new Value("AllowMultiTask", this, false));
      this.pickaxe = this.register(new Value("Pickaxe", this, true)).visibleWhen((var1) -> {
         return (Boolean)this.noTrace.getValue();
      });
      this.doubleBreak = this.register(new Value("DoubleBreak", this, false));
      this.webSwitch = this.register(new Value("WebSwitch", this, false));
      this.render = this.register(new Value("Render", this, false));
      this.box = this.register(new Value("Box", this, false)).visibleWhen((var1) -> {
         return (Boolean)this.render.getValue();
      });
      this.outline = this.register(new Value("Outline", this, true)).visibleWhen((var1) -> {
         return (Boolean)this.render.getValue();
      });
      this.boxAlpha = this.register(new Value("BoxAlpha", this, 85, 0, 255)).visibleWhen((var1) -> {
         return (Boolean)this.box.getValue() && (Boolean)this.render.getValue();
      });
      this.lineWidth = this.register(new Value("LineWidth", this, 1.0F, 0.1F, 5.0F)).visibleWhen((var1) -> {
         return (Boolean)this.outline.getValue() && (Boolean)this.render.getValue();
      });
      this.timer = new Timer();
      this.isMining = false;
      this.lastPos = null;
      this.lastFacing = null;
      this.setInstance();
   }

   public String getHudInfo() {
      return ((Speedmine.Mode)this.mode.getValue()).name();
   }

   public static enum Mode {
      // $FF: synthetic field
      DAMAGE,
      // $FF: synthetic field
      INSTANT,
      // $FF: synthetic field
      PACKET;
   }
}
