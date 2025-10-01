package com.neponies;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;

import java.util.List;

import static com.neponies.NotEnoughPonies.isDebug;

public class DebugUtils {
    /**
     * Debug method: finds nearby villagers and spawns a child between first two adults.
     * Only executes if debug mode is enabled.
     */
    public static void runDebugBreeding(LivingEntity player, ServerWorld world) {
        if (!isDebug) return;

        // Define a search box around the player
        Box searchBox = new Box(
                player.getX() - 10, player.getY() - 5, player.getZ() - 10,
                player.getX() + 10, player.getY() + 5, player.getZ() + 10
        );

        List<VillagerEntity> villagers = world.getEntitiesByClass(VillagerEntity.class, searchBox,
                v -> !v.isBaby());

        if (villagers.size() < 2) return; // Require at least two adults

        VillagerEntity parent1 = villagers.get(0);
        VillagerEntity parent2 = villagers.get(1);

        // Spawn a child and set its position & breeding age
        VillagerEntity baby = parent1.createChild(world, parent2);
        if (baby != null) {
            baby.refreshPositionAndAngles(parent1.getX(), parent1.getY(), parent1.getZ(), parent1.getYaw(), parent1.getPitch());
            baby.setBreedingAge(-24000); // Set child as a newborn ready for growth
            world.spawnEntity(baby);

            NotEnoughPonies.LOG.debug("Spawned debug baby between {} and {}", parent1.getName().getString(), parent2.getName().getString());

            player.sendMessage(
                    Text.literal("Spawned debug baby between "
                            + parent1.getName().getString()
                            + " and "
                            + parent2.getName().getString())
            );
        }
    }

}
