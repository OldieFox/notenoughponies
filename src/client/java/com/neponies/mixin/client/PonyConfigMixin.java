package com.neponies.mixin.client;

import com.minelittlepony.api.config.PonyConfig;
import com.minelittlepony.api.pony.meta.Race;
import com.neponies.MixinFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PonyConfig.class, remap = false)
public class PonyConfigMixin {

    /**
     * #4A3827
     * The place where the overridden pony race is assigned for a villager
     * (can also apply to any pony entity)
     */

    @Inject(
            method = "getEffectiveRace",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void overrideEffectiveRace(Race race, CallbackInfoReturnable<Race> cir) {
        if (MixinFlags.overridenVillagerRace != null) {
            cir.setReturnValue(MixinFlags.overridenVillagerRace);
        }
    }
}
