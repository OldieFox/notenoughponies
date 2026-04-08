package com.neponies.mixin.client;

import com.neponies.VillagerPonyEntityAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.neponies.Constants.RENDER_PROFESSION_RADIUS;

@Mixin(MobEntityRenderer.class)
public abstract class MobEntityRendererMixin<T extends MobEntity, M extends EntityModel<T>> {

    /**
     * #A9X2QK
     * Controls the visibility of the pony villager's label.
     * Make displaying the name if the player can see a villager in the sight.
     * Hide the label if the player can't see the villager
     */
    @Inject(method = "hasLabel", at = @At("HEAD"), cancellable = true)
    private void customNameRender(T entity, CallbackInfoReturnable<Boolean> cir) {
        if (!(entity instanceof VillagerEntity villager)) return;

        VillagerPonyEntityAccessor pony = (VillagerPonyEntityAccessor) villager;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            cir.setReturnValue(false);
            return;
        }

        boolean showCustomName = pony.canShowCustomPonyName();
        boolean showProfession = pony.canShowProfessionName()
                && client.player.squaredDistanceTo(entity) <= RENDER_PROFESSION_RADIUS;

        if (!showCustomName && !showProfession) return;

        boolean canShow = client.player != null && client.player.canSee(entity);

        cir.setReturnValue(canShow);
    }
}
