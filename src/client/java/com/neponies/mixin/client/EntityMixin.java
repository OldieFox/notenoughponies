package com.neponies.mixin.client;

import com.neponies.VillagerPonyEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    protected World world;

    /**
     * #A9X2QK
     * Forces the custom pony name to always be visible
     */

    @Inject(method = "isCustomNameVisible", at = @At("HEAD"), cancellable = true)
    private void alwaysShowNameInject(CallbackInfoReturnable<Boolean> cir) {
        if (!world.isClient) return;

        if ((Object) this instanceof VillagerEntity villager) {
            VillagerPonyEntityAccessor pony = (VillagerPonyEntityAccessor) villager;
            if (pony.canShowCustomPonyName()) {
                cir.setReturnValue(true);
            }
        }
    }

    /**
     * #A9X2QK
     * Overrides the default Minecraft custom name with the modded pony name
     */
    @Inject(method = "getCustomName", at = @At("HEAD"), cancellable = true)
    private void getCustomNameInject(CallbackInfoReturnable<Text> cir) {
        if (!world.isClient) return;

        if ((Object) this instanceof VillagerEntity villager) {
            VillagerPonyEntityAccessor pony = (VillagerPonyEntityAccessor) villager;
            if (pony.canShowCustomPonyName()) {
                cir.setReturnValue(pony.getPonyCustomName());
            }
        }
    }

    /**
     * #A9X2QK
     * Override the MC custom name presence check to use the modded pony name
     */
    @Inject(method = "hasCustomName", at = @At("HEAD"), cancellable = true)
    private void hasCustomNameInject(CallbackInfoReturnable<Boolean> cir) {
        if (!world.isClient) return;

        if ((Object) this instanceof VillagerEntity villager) {
            VillagerPonyEntityAccessor pony = (VillagerPonyEntityAccessor) villager;
            if (pony.canShowCustomPonyName()) {
                cir.setReturnValue(true);
            }
        }
    }
}
