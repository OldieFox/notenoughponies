package com.neponies.mixin;

import com.minelittlepony.common.util.settings.Config;
import com.minelittlepony.common.util.settings.Setting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Config.class)
public interface ConfigAccessor {
    @Invoker("value")
    <T> Setting<T> invokeValue(String category, String key, T def);
}