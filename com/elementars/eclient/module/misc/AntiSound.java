package com.elementars.eclient.module.misc;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundEvent;

public class AntiSound extends Module {
   // $FF: synthetic field
   private final Value witherHurt = this.register(new Value("Wither Hurt", this, true));
   // $FF: synthetic field
   public final Value wither = this.register(new Value("Wither Ambient", this, true));
   // $FF: synthetic field
   private final Value punchKB = this.register(new Value("Knockback Punch", this, true));
   // $FF: synthetic field
   private final Value witherDeath = this.register(new Value("Wither Death", this, false));
   // $FF: synthetic field
   public final Value portal = this.register(new Value("Nether Portal", this, true));
   // $FF: synthetic field
   private final Value explosion = this.register(new Value("Explosion", this, false));
   // $FF: synthetic field
   public final Value witherSpawn = this.register(new Value("Wither Spawn", this, false));
   // $FF: synthetic field
   public final Value totem = this.register(new Value("Totem Pop", this, false));
   // $FF: synthetic field
   public final Value elytra = this.register(new Value("Elytra Wind", this, true));
   // $FF: synthetic field
   private final Value punches = this.register(new Value("Punches", this, true));
   // $FF: synthetic field
   private final Value punchW = this.register(new Value("Weak Punch", this, true));

   @EventTarget
   public void onRecieve(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketSoundEffect) {
         SPacketSoundEffect var2 = (SPacketSoundEffect)var1.getPacket();
         if (this.shouldCancelSound(var2.getSound())) {
            var1.setCancelled(true);
         }
      }

   }

   public void onDisable() {
      EVENT_BUS.unregister(this);
   }

   private boolean shouldCancelSound(SoundEvent var1) {
      if (var1 == SoundEvents.ENTITY_WITHER_AMBIENT && (Boolean)this.wither.getValue()) {
         return true;
      } else if (var1 == SoundEvents.ENTITY_WITHER_SPAWN && (Boolean)this.witherSpawn.getValue()) {
         return true;
      } else if (var1 == SoundEvents.ENTITY_WITHER_HURT && (Boolean)this.witherHurt.getValue()) {
         return true;
      } else if (var1 == SoundEvents.ENTITY_WITHER_DEATH && (Boolean)this.witherDeath.getValue()) {
         return true;
      } else if (var1 == SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE && (Boolean)this.punches.getValue()) {
         return true;
      } else if (var1 == SoundEvents.ENTITY_PLAYER_ATTACK_WEAK && (Boolean)this.punchW.getValue()) {
         return true;
      } else if (var1 == SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK && (Boolean)this.punchKB.getValue()) {
         return true;
      } else {
         return var1 == SoundEvents.ENTITY_GENERIC_EXPLODE && (Boolean)this.explosion.getValue();
      }
   }

   public AntiSound() {
      super("AntiSound", "Blacklists certain annoying sounds", 0, Category.MISC, true);
   }

   public void onEnable() {
      EVENT_BUS.register(this);
   }
}
