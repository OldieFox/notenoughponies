package com.neponies.mixin.client;

import com.minelittlepony.client.render.entity.feature.ElytraFeature;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ElytraFeature.class)
public abstract class ElytraFeatureMixin<T extends LivingEntity> {


    /** *
     *  Adds an additional Trinkets check so Elytra renders if equipped via Trinkets.
     */

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"
            )
    )
    private Item injectedElytraCheck(ItemStack stack, MatrixStack matrices, VertexConsumerProvider consumers,
                                     int light, T entity, float limbDistance, float limbAngle,
                                     float tickDelta, float age, float headYaw, float headPitch) {
        if (stack.isOf(Items.ELYTRA)) {
            return Items.ELYTRA;
        }

        if (entityHasElytraFromTrinkets(entity)) {
            return Items.ELYTRA;
        }

        return stack.getItem();
    }

    @Unique
    private static boolean entityHasElytraFromTrinkets(LivingEntity entity) {
        try {
            return TrinketsApi.getTrinketComponent(entity)
                    .map(comp -> !comp.getEquipped(stack -> stack.isOf(Items.ELYTRA)).isEmpty())
                    .orElse(false);
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }
}
