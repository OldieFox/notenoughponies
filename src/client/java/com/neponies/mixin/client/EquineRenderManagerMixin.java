package com.neponies.mixin.client;

import com.minelittlepony.client.render.EquineRenderManager;
import com.neponies.VillagerPonyEntityAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.neponies.Constants.NAME_WITH_PROFESSION_Y_OFFSET;
import static com.neponies.Constants.RENDER_PROFESSION_RADIUS;

@Mixin(EquineRenderManager.class)
public abstract class EquineRenderManagerMixin<T extends LivingEntity> {

    /**
     * #A9X2QK
     * Slightly offset the nameplate upwards for babies,
     * and active professions.
     */
    @Inject(method = "getNamePlateYOffset", at = @At("RETURN"), cancellable = true)
    private void onGetNamePlateYOffset(T entity, CallbackInfoReturnable<Double> cir) {


        if (entity.isBaby()) {
            cir.setReturnValue(cir.getReturnValue() + 0.65);
        }
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        if (mc.player.squaredDistanceTo(entity) <= RENDER_PROFESSION_RADIUS
                && entity instanceof VillagerPonyEntityAccessor villager
                && villager.canShowProfessionName()
                && entity.hasCustomName()) {

            cir.setReturnValue(cir.getReturnValue() + NAME_WITH_PROFESSION_Y_OFFSET);
        }

    }
}
