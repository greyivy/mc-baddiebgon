package com.igrey.baddiebgon.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.igrey.baddiebgon.BaddieBGon.config;

@Mixin(SpawnHelper.class)
public abstract class MixinSpawnHelper {
    @Inject(at = @At("HEAD"), method = "canSpawn(Lnet/minecraft/entity/SpawnRestriction$Location;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/EntityType;)Z", cancellable = true)
    private static void canSpawn(SpawnRestriction.Location location, WorldView world, BlockPos pos, EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
        // Prevent mobs banned by ID from spawning altogether
        if (entityType != null && config.getPattern() != null) {
            String entityTypeId = entityType.toString();

            if (config.getPattern().matcher(entityTypeId).matches()) {
                cir.setReturnValue(false);
            }
        }
    }
}
