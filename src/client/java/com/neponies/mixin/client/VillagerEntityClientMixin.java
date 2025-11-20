package com.neponies.mixin.client;

import com.neponies.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityClientMixin extends MerchantEntity {

    public VillagerEntityClientMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

}