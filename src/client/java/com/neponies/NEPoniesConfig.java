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

    /** Toggle displaying profession level in the pony custom villager name */
    public static Setting<Boolean> isProfessionLevelInPonyCustomNamesEnabled;

    /** Toggle whether vanilla villager sounds are replaced with custom modded sounds */
    public static Setting<Boolean> isPonyVillagerSoundsEnabled;

    /** Toggle whether vanilla villager ambient sounds (greetings) are played */
    public static Setting<Boolean> isPonyVillagerAmbientSoundsEnabled;

    /** Maximum distance in blocks for displaying pony villager names */
    public static Setting<Integer> ponyCustomNameRenderDistance;

    /** Maximum distance in blocks for displaying villager professions */
    public static Setting<Integer> professionRenderDistance;


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

    public static int getPonyCustomNameRenderDistance() {
        return getClampedDistance(ponyCustomNameRenderDistance, Constants.DEFAULT_NAME_RENDER_DISTANCE);
    }

    public static int setPonyCustomNameRenderDistance(int distance) {
        return setClampedDistance(ponyCustomNameRenderDistance, distance);
    }

    public static int getProfessionRenderDistance() {
        return getClampedDistance(professionRenderDistance, Constants.DEFAULT_PROFESSION_RENDER_DISTANCE);
    }

    public static int setProfessionRenderDistance(int distance) {
        return setClampedDistance(professionRenderDistance, distance);
    }

    private static int getClampedDistance(Setting<Integer> setting, int defaultValue) {
        int value = setting == null ? defaultValue : setting.get();
        return clampDistance(value);
    }

    private static int setClampedDistance(Setting<Integer> setting, int distance) {
        int clamped = clampDistance(distance);
        if (setting != null) {
            setting.set(clamped);
        }
        return clamped;
    }

    private static int clampDistance(int distance) {
        return Math.max(Constants.MIN_LABEL_RENDER_DISTANCE,
                Math.min(Constants.MAX_LABEL_RENDER_DISTANCE, distance));
    }


}
