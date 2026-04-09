package com.neponies.mixin.client;

import com.minelittlepony.api.config.PonyConfig;
import com.minelittlepony.api.pony.meta.Race;
import com.minelittlepony.common.util.settings.Config;
import com.neponies.Constants;
import com.neponies.MixinFlags;
import com.neponies.NEPoniesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

@Mixin(value = PonyConfig.class, remap = false)
public class PonyConfigMixin extends Config {

    protected PonyConfigMixin(Config.Adapter adapter, Path path) {
        super(adapter, path);
    }


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



    /**
     * Insert mod configs to original MLP mod
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        PonyConfig self = (PonyConfig) (Object) this;

        NEPoniesConfig.isPonyCustomNamesEnabled = ((ConfigAccessor) (Object) this)
                .invokeValue("settings", "ponyCustomNamesEnabled", true)
                .addComment("Toggle displaying the pony custom villager names");

        NEPoniesConfig.isProfessionInPonyCustomNamesEnabled = ((ConfigAccessor) (Object) this)
                .invokeValue("settings", "professionInPonyCustomNames", true)
                .addComment("Toggle displaying profession in the pony custom villager name");

        NEPoniesConfig.isProfessionLevelInPonyCustomNamesEnabled = ((ConfigAccessor) (Object) this)
                .invokeValue("settings", "professionLevelInPonyCustomNames", false)
                .addComment("Toggle displaying profession level in the pony custom villager label");

        NEPoniesConfig.ponyCustomNameRenderDistance = ((ConfigAccessor) (Object) this)
                .invokeValue("settings", "ponyCustomNameRenderDistance", Constants.DEFAULT_NAME_RENDER_DISTANCE)
                .addComment("Maximum distance in blocks for displaying pony villager names");

        NEPoniesConfig.professionRenderDistance = ((ConfigAccessor) (Object) this)
                .invokeValue("settings", "professionRenderDistance", Constants.DEFAULT_PROFESSION_RENDER_DISTANCE)
                .addComment("Maximum distance in blocks for displaying villager professions and levels");

        NEPoniesConfig.isPonyVillagerSoundsEnabled = ((ConfigAccessor) (Object) this)
                .invokeValue("settings", "replaceVillagerSounds", true)
                .addComment("Toggle whether vanilla villager sounds are replaced with custom modded sounds");

        NEPoniesConfig.isPonyVillagerAmbientSoundsEnabled = ((ConfigAccessor) (Object) this)
                .invokeValue("settings", "ponyVillagerAmbientSoundsEnabled", true)
                .addComment("Toggle whether pony villager ambient sounds (greetings) are played");
    }
}
