package com.neponies.mixin.client;

import com.minelittlepony.client.render.entity.npc.VillagerPonyRenderer;
import com.neponies.MixinFlags;

import com.neponies.NEPRace;
import com.neponies.RaceConverter;
import com.neponies.VillagerPonyEntityAccessor;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;

import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerPonyRenderer.class)
public abstract class VillagerPonyRendererMixin {

    /**
     * #4A3827
     * Before rendering: enable the global flag overridenVillagerRace
     */
    @Inject(method = "render", at = @At("HEAD"))
    private void beforeRender(MobEntity entity, float par2, float par3, MatrixStack par4, VertexConsumerProvider par5, int par6, CallbackInfo ci) {
        if (entity instanceof VillagerEntity villager && villager instanceof VillagerPonyEntityAccessor accessor) {
            MixinFlags.overridenVillagerRace = RaceConverter.toMLP(accessor.getPonyRace());
        }
    }

    /**
     * #4A3827
     * After rendering: disable the global flag overridenVillagerRace
     */
    @Inject(method = "render", at = @At("RETURN"))
    private void afterRender(MobEntity entity, float par2, float par3, MatrixStack par4, VertexConsumerProvider par5, int par6, CallbackInfo ci) {
        MixinFlags.overridenVillagerRace = null;
    }
}