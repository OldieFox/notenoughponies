package com.neponies;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

import static com.neponies.NotEnoughPonies.DEBUG_BREED_PACKET;

public class ClientDebugUtils {

    public static void runDebugBreeding() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(DEBUG_BREED_PACKET, buf);
    }

}
