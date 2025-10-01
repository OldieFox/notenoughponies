package com.neponies.mixin;

import com.minelittlepony.api.config.PonyConfig;
import com.minelittlepony.common.util.settings.Config;
import com.neponies.NEPoniesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;

@Mixin(PonyConfig.class)
public class PonyConfigMixin extends Config {

    protected PonyConfigMixin(Adapter adapter, Path path) {
        super(adapter, path);
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

        NEPoniesConfig.isPonyVillagerSoundsEnabled = ((ConfigAccessor) (Object) this)
                .invokeValue("settings", "replaceVillagerSounds", true)
                .addComment("Toggle whether vanilla villager sounds are replaced with custom modded sounds");
    }


}
