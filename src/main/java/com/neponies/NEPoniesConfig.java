package com.neponies;

import com.minelittlepony.api.config.PonyConfig;
import com.minelittlepony.common.util.settings.Setting;

import java.util.stream.StreamSupport;

public class NEPoniesConfig {

    // --- Pony villager configs ---
    /** Toggle displaying the pony custom villager names at all */
    public static Setting<Boolean> isPonyCustomNamesEnabled;

    /** Toggle displaying profession in the pony custom villager name */
    public static Setting<Boolean> isProfessionInPonyCustomNamesEnabled;

    /** Toggle whether vanilla villager sounds are replaced with custom modded sounds */
    public static Setting<Boolean> isPonyVillagerSoundsEnabled;


    /** Setting from original MLP mod for replacing vanilla Minecraft villager sounds */
    private static Setting<Boolean> _isPonyVillagerInOriginalModEnabled;

    public static Setting<Boolean> isPonyVillagerInOriginalModEnabled() {
        if (_isPonyVillagerInOriginalModEnabled == null) {
            PonyConfig config = PonyConfig.getInstance();
            Iterable<Setting<?>> entries = config.getCategory("entities").entries();

            _isPonyVillagerInOriginalModEnabled = StreamSupport.stream(entries.spliterator(), false)
                    .filter(s -> "villagers".equalsIgnoreCase(s.name()))
                    .map(s -> (Setting<Boolean>) s)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Villager setting not found in MLP config"));
        }
        return _isPonyVillagerInOriginalModEnabled;
    }


}
