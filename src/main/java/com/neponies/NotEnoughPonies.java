package com.neponies;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.neponies.DebugUtils.runDebugBreeding;

public class NotEnoughPonies implements ModInitializer {

    public static final String MOD_ID = "notenoughponies";
    public static final Identifier DEBUG_BREED_PACKET = new Identifier(MOD_ID, "debug_breed");
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
    public static final boolean isDebug = Boolean.getBoolean("mymod.debug");

    @Override
    public void onInitialize() {
        if (isDebug) {
            ServerPlayNetworking.registerGlobalReceiver(DEBUG_BREED_PACKET, (server, player, handler, buf, responseSender) -> {
                server.execute(() -> {
                    if (player.getWorld() instanceof ServerWorld serverWorld) {
                        runDebugBreeding(player, serverWorld);
                    }
                });
            });
        }
    }
}