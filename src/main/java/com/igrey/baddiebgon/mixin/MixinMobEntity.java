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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.igrey.baddiebgon.BaddieBGon.ITEMS;
import static com.igrey.baddiebgon.BaddieBGon.config;

@Mixin(MobEntity.class)
public abstract class MixinMobEntity extends LivingEntity {
    private static String previousFilter = null;
    private static Pattern pattern = null;
    private static List<String> additionalHostileMobs = Arrays.asList(new String[]{ "entity.minecraft.slime", "entity.minecraft.ghast" });

    protected MixinMobEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    private static void initializeRegex() {
        // Cache pattern
        if (!config.banHostileMobRegex.equals(previousFilter)) {
            previousFilter = config.banHostileMobRegex;

            if (config.banHostileMobRegex.length() > 0) {
                pattern = Pattern.compile(config.banHostileMobRegex);
            } else {
                pattern = null;
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "checkDespawn()V")
    private void checkDespawn(CallbackInfo info) {
        initializeRegex();

        String entityTypeId = this.getType().toString();

        if (pattern != null) {
            // Ban mob by ID
            if (pattern.matcher(entityTypeId).matches()) {
                this.discard();

                return;
            }
        }

        boolean isHostile = additionalHostileMobs.indexOf(entityTypeId) > -1 || HostileEntity.class.isAssignableFrom(this.getClass());
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
