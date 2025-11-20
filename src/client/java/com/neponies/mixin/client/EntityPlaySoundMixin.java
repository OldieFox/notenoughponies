package com.neponies.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.neponies.NEPoniesConfig.isPonyVillagerInOriginalModEnabled;
import static com.neponies.NEPoniesConfig.isPonyVillagerSoundsEnabled;
import static com.neponies.NEPoniesConfig.isPonyVillagerAmbientSoundsEnabled;
import static com.neponies.VillagerSounds.getVillagerSound;


@Mixin(Entity.class)
public abstract class EntityPlaySoundMixin {

    /**
     * #G7M4Z
     * Replacing vanilla villager sound with modded sounds
     */

    @Inject(method = "playSound(Lnet/minecraft/sound/SoundEvent;FF)V", at = @At("HEAD"), cancellable = true)
    private void onPlaySound(SoundEvent sound, float volume, float pitch, CallbackInfo ci) {

        if (!isPonyVillagerInOriginalModEnabled().get()) return;

        Identifier id = Registries.SOUND_EVENT.getId(sound);
        if (id == null) return;

        if ("minecraft".equals(id.getNamespace()) && id.getPath().startsWith("entity.villager.")) {

            // Mute ambient sounds if configured
            if ("entity.villager.ambient".equals(id.getPath()) && !isPonyVillagerAmbientSoundsEnabled.get()) {
                ci.cancel();
                return;
            }

            // Replace sounds if configured
            if (isPonyVillagerSoundsEnabled.get()) {
                SoundEvent replacement = getVillagerSound(id);
                if (replacement != null && replacement != sound) {
                    // Custom sounds can be so irritating sometimes
                    float limitedPitch = pitch;
                    if ("entity.villager.ambient".equals(id.getPath())) {
                        limitedPitch = Math.max(0.9f, Math.min(pitch, 1.15f));
                    }
                    ci.cancel();
                    ((Entity) (Object) this).playSound(replacement, volume, limitedPitch);
                }
            }
        }
    }
}