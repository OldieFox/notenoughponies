package com.neponies;

import com.minelittlepony.api.pony.meta.Race;

public class MixinFlags {

    // #G7M4Z Playing custom villager sounds with the option to toggle them in the config

    // #A9X2QK Displays a custom pony name above the pony, visible to the player.
    // If the player sets a custom name tag, the pony's custom name will be disabled.
    // Also display the profession as a second label and adjust offsets for baby villagers

    // #4A3827 Override the pony villager's race
    public static Race overridenVillagerRace = null;
}