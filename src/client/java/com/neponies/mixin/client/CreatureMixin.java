package com.neponies.mixin.client;


import com.minelittlepony.unicopia.entity.Creature;
import com.minelittlepony.unicopia.Race;
import com.minelittlepony.unicopia.entity.Living;
import com.neponies.NEPRace;
import com.neponies.PonyComponentInitializer;
import com.neponies.VillagerPonyEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Creature.class)
public abstract class CreatureMixin extends Living<LivingEntity>  {

    @Unique
    private Race overrideRace;

    @Unique
    private boolean needsRaceSync = true;

    protected CreatureMixin(LivingEntity entity) {
        super(entity);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initOverrideRaceInject(LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof VillagerPonyEntityAccessor villager) {
            villager.setOnInitializeListener(() -> {
                NEPRace mlpRace = villager.getPonyRace();
                this.overrideRace = convertNepRaceToUnicopia(mlpRace);
            });
        }
    }


    @Inject(method = "beforeUpdate", at = @At("HEAD"), remap = false)
    private void onBeforeUpdate(CallbackInfoReturnable<Boolean> cir) {
        if (this.needsRaceSync && !asWorld().isClient()) {
            LivingEntity entity = ((Living<LivingEntity>) (Object) this).asEntity();
            if (entity instanceof VillagerEntity) {
                PonyComponentInitializer.PONY_DATA.maybeGet(entity).ifPresentOrElse(
                        ponyComponent -> {
                            if (ponyComponent.getData().getSkinID() == null) {
                                if (entity instanceof VillagerPonyEntityAccessor accessor) {
                                    accessor.checkAndSetRace();
                                }
                            }

                            NEPRace mlpRace = ponyComponent.getData().getRace();
                            this.overrideRace = convertNepRaceToUnicopia(mlpRace);
                            this.needsRaceSync = false;
                        },
                        () -> {
                            this.needsRaceSync = true;
                        }
                );
            } else {
                this.needsRaceSync = false;
            }
        }
    }

    /**
     * Make Pegasus or Alicorn villagers walk correctly on Unicopia's clouds.
     */

    @Inject(method = "getSpecies", at = @At("HEAD"), cancellable = true, remap = false)
    private void replaceSpecies(CallbackInfoReturnable<Race> cir) {
        if (this.overrideRace != null) {
            cir.setReturnValue(this.overrideRace);
        }
    }

    @Unique
    private Race convertNepRaceToUnicopia(NEPRace mlpRace) {
        if (mlpRace.isHuman()) return Race.HUMAN;
        if (mlpRace.hasHorn() && mlpRace.hasWings()) return Race.ALICORN;
        if (mlpRace.hasHorn()) return Race.UNICORN;
        if (mlpRace.hasWings()) return Race.PEGASUS;
        return Race.EARTH;
    }
}
