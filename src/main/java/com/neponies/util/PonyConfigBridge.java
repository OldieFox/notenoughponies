package com.neponies.util;

import com.neponies.VillagerPonyEntityAccessor;
import com.neponies.mixin.VillagerEntityMixin;
import net.minecraft.entity.passive.VillagerEntity;

import java.util.function.Predicate;

public class PonyConfigBridge {

    public static Predicate<VillagerPonyEntityAccessor> CAN_SHOW_PROFESSION = (villager) -> false;
    public static Predicate<VillagerPonyEntityAccessor> CAN_SHOW_CUSTOM_NAME = (villager) -> false;

}