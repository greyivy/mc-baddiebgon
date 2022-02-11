package com.igrey.baddiebgon.mixin;

import com.igrey.baddiebgon.BaddieBGonItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.igrey.baddiebgon.BaddieBGon.ITEMS;
import static com.igrey.baddiebgon.BaddieBGon.config;

@Mixin(MobEntity.class)
public abstract class MixinMobEntity extends LivingEntity {
    protected MixinMobEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "checkDespawn()V")
    private void checkDespawn(CallbackInfo info) {
        String entityTypeId = this.getType().toString();

        if (config.getPattern() != null) {
            // Ban mob by ID
            if (config.getPattern().matcher(entityTypeId).matches()) {
                this.discard();

                return;
            }
        }

        boolean isHostile = config.additionalHostileMobs.indexOf(entityTypeId) > -1 || HostileEntity.class.isAssignableFrom(this.getClass());
        if (!isHostile) return; // Allow peaceful mobs to spawn

        for (PlayerEntity player : this.world.getPlayers()) {
            int maxEffectiveDistance = -1;

            // Get Baddie-B-Gon item w/ max effective distance
            for (ItemStack stack : player.getInventory().main) {
                for (BaddieBGonItem item : ITEMS) {
                    if (stack.isItemEqualIgnoreDamage(new ItemStack(item))) {
                        maxEffectiveDistance = Math.max(maxEffectiveDistance, item.getEffectiveDistance());
                    }
                }
            }

            if (maxEffectiveDistance > 0 && player.distanceTo(this) <= maxEffectiveDistance) {
                // Discard baddies in range
                this.discard();

                return;
            }
        }

        super.checkDespawn();
    }
}
