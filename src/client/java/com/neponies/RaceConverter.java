package com.neponies;

import com.minelittlepony.api.pony.meta.Race;

public final class RaceConverter {

    private RaceConverter() {}

    /**
     *  MLP Race → NEPRace
     */
    public static NEPRace toNEP(Race mlpRace) {
        if (mlpRace == null) return null;

        try {
            return NEPRace.valueOf(mlpRace.name());
        } catch (IllegalArgumentException ex) {
            return null; // можно заменить на fallback
        }
    }

    /**
     *  NEPRace → MLP Race
     */
    public static Race toMLP(NEPRace nepRace) {
        if (nepRace == null) return null;

        try {
            return Race.valueOf(nepRace.name());
        } catch (IllegalArgumentException ex) {
            return null; // можно заменить на fallback
        }
    }
}
