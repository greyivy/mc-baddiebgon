package com.igrey.baddiebgon.mixin;

import com.igrey.baddiebgon.BaddieBGonItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;

import java.util.regex.Pattern;

import static com.igrey.baddiebgon.BaddieBGon.ITEMS;
import static com.igrey.baddiebgon.BaddieBGon.config;

@Mixin(HostileEntity.class)
public abstract class MixinHostileEntity extends MobEntity {
    protected MixinHostileEntity() {
        super(null, null);
    }

    private static String previousFilter = null;
    private static Pattern pattern = null;

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

    @Override
    public void checkDespawn() {
        initializeRegex();

        if (pattern != null) {
            // Ban mob by ID
            String entityTypeId = this.getType().toString();

            if (pattern.matcher(entityTypeId).matches()) {
                this.discard();

                return;
            }
        }

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