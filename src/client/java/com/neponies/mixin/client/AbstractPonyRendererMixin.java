package com.neponies.mixin.client;

import com.minelittlepony.client.render.entity.AbstractPonyRenderer;
import com.neponies.client.ClientPonyConfigImpl;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.neponies.Constants.*;
import static com.neponies.PonyUtils.canLoadCustomPonySkin;
import static com.neponies.PonyUtils.findCustomTexture;

import com.neponies.VillagerPonyEntityAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = AbstractPonyRenderer.class)
public abstract class AbstractPonyRendererMixin<T extends MobEntity>  extends EntityRenderer<T> {


    protected AbstractPonyRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    /**
     * Override the pony texture with a custom one
     */

    @Inject(
            method = "getTexture(Lnet/minecraft/entity/mob/MobEntity;)Lnet/minecraft/util/Identifier;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onGetTexture(T entity, CallbackInfoReturnable<Identifier> cir) {
        if (entity instanceof VillagerEntity villager && canLoadCustomPonySkin(villager)) {
            Identifier custom = findCustomTexture(villager);
            if (custom != null) {
                cir.setReturnValue(custom);
            }
        }
    }

    /**
     * A9X2QK
     * Make the baby label smaller
     */

    @Inject(
            method = "renderLabelIfPresent",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/MobEntityRenderer;renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void injectScaleAfterTranslate(T entity, Text name, MatrixStack stack, VertexConsumerProvider renderContext, int maxDistance, CallbackInfo ci) {
        if (!(entity instanceof VillagerEntity villager)) return;
        if(villager.isBaby()){
            float scale =  BABY_LABEL_SCALE;
            stack.scale(scale, scale, scale);
        }
    }

    /**
     * #A9X2QK
     * Render villager pony profession if enabled.
     */
    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    private void renderProfessionOnlyLabel(T entity, Text name, MatrixStack stack,
                                           VertexConsumerProvider renderContext, int maxDistance, CallbackInfo ci) {
        if (!(entity instanceof VillagerEntity villager)) return;

        VillagerPonyEntityAccessor pony = (VillagerPonyEntityAccessor) villager;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) {
            return;
        }

        if (!pony.canShowProfessionName()
                || ClientPonyConfigImpl.hasVisibleNameLabel(villager, pony, mc.player)
                || !ClientPonyConfigImpl.isWithinProfessionDistance(mc.player, entity)) {
            return;
        }

        Text professionText = ClientPonyConfigImpl.getProfessionLabel(pony);
        if (professionText.getString().isEmpty()) {
            return;
        }

        stack.push();
        float scale = PROFESSION_LABEL_SCALE * (villager.isBaby() ? BABY_LABEL_SCALE : 1.0f);
        stack.scale(scale, scale, scale);
        stack.translate(0, PROFESSION_Y_OFFSET, 0);
        super.renderLabelIfPresent(entity, professionText, stack, renderContext, maxDistance);
        stack.pop();
        ci.cancel();
    }

    @Inject(method = "renderLabelIfPresent", at = @At("RETURN"), cancellable = true)
    private void renderProfessionLabel(T entity, Text name, MatrixStack stack,
                                       VertexConsumerProvider renderContext, int maxDistance, CallbackInfo ci) {
        if (!(entity instanceof VillagerEntity villager)) return;

        VillagerPonyEntityAccessor pony = (VillagerPonyEntityAccessor) villager;
        if (!pony.canShowProfessionName()) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        if (!ClientPonyConfigImpl.hasVisibleNameLabel(villager, pony, mc.player)
                || !ClientPonyConfigImpl.isWithinProfessionDistance(mc.player, entity)) {
            return;
        }

        Text professionText = ClientPonyConfigImpl.getProfessionLabel(pony);
        if (professionText.getString().isEmpty()) {
            return;
        }
        stack.push();
        float scale = PROFESSION_LABEL_SCALE;
        stack.scale(scale, scale, scale);

        stack.translate(0, PROFESSION_Y_OFFSET, 0);
        super.renderLabelIfPresent(entity, professionText, stack, renderContext, maxDistance);
        stack.pop();
    }

}
