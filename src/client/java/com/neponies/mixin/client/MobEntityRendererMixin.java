package com.neponies.mixin.client;

import com.neponies.client.ClientPonyConfigImpl;
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

        boolean nameEnabled = pony.canShowCustomPonyName() || villager.hasCustomName();
        boolean professionEnabled = pony.canShowProfessionName();

        if (!nameEnabled && !professionEnabled) return;

        boolean showCustomName = nameEnabled && ClientPonyConfigImpl.isWithinCustomNameDistance(client.player, entity);
        boolean showProfession = professionEnabled && ClientPonyConfigImpl.isWithinProfessionDistance(client.player, entity);

        if (!showCustomName && !showProfession) {
            cir.setReturnValue(false);
            return;
        }

        cir.setReturnValue(client.player.canSee(entity));
    }
}
