package com.neponies.client;

import com.neponies.NEPoniesConfig;
import com.neponies.VillagerPonyEntityAccessor;
import com.neponies.util.PonyConfigBridge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.Text;

import static net.minecraft.entity.Entity.CUSTOM_NAME;

public class ClientPonyConfigImpl {

    public static void init() {
        PonyConfigBridge.CAN_SHOW_PROFESSION = ClientPonyConfigImpl::canShowProfession;
        PonyConfigBridge.CAN_SHOW_CUSTOM_NAME = ClientPonyConfigImpl::canShowCustomName;
    }

    public static int getCustomNameRenderDistance() {
        return NEPoniesConfig.getPonyCustomNameRenderDistance();
    }

    public static int getProfessionRenderDistance() {
        return NEPoniesConfig.getProfessionRenderDistance();
    }

    public static boolean isWithinCustomNameDistance(Entity viewer, Entity entity) {
        return viewer.squaredDistanceTo(entity) <= squareDistance(getCustomNameRenderDistance());
    }

    public static boolean isWithinProfessionDistance(Entity viewer, Entity entity) {
        return viewer.squaredDistanceTo(entity) <= squareDistance(getProfessionRenderDistance());
    }

    public static boolean hasVisibleNameLabel(VillagerEntity villager, VillagerPonyEntityAccessor pony, Entity viewer) {
        if (viewer == null || !isWithinCustomNameDistance(viewer, villager)) {
            return false;
        }

        return pony.canShowCustomPonyName() || villager.hasCustomName();
    }

    private static boolean canShowProfession(VillagerPonyEntityAccessor villager) {
        if (!NEPoniesConfig.isPonyVillagerInOriginalModEnabled().get()) return false;
        if (villager instanceof VillagerEntity entity && entity.isBaby()) return false;

        return !getProfessionLabel(villager).getString().isEmpty();
    }

    private static boolean canShowCustomName(VillagerPonyEntityAccessor accessor) {
        if (accessor instanceof Entity villager) {

            boolean hasVanillaCustomName = (villager.dataTracker.get(CUSTOM_NAME)).isPresent();
            if (!NEPoniesConfig.isPonyCustomNamesEnabled.get()) return false;
            if (!NEPoniesConfig.isPonyVillagerInOriginalModEnabled().get()) return false;
            if (hasVanillaCustomName) return false;

            Text name = accessor.getPonyCustomName();
            return name != null && !name.getString().isEmpty();
        }

        return false;
    }

    public static Text getProfessionLabel(VillagerPonyEntityAccessor villager) {
        boolean showProfession = NEPoniesConfig.isProfessionInPonyCustomNamesEnabled.get();
        boolean showLevel = NEPoniesConfig.isProfessionLevelInPonyCustomNamesEnabled.get();

        if (!showProfession && !showLevel) {
            return Text.empty();
        }

        Text profession = villager.getProfessionName();
        Text level = villager.getProfessionLevelName();
        boolean hasProfession = !profession.getString().isEmpty();
        boolean hasLevel = !level.getString().isEmpty();

        if (showProfession && showLevel) {
            if (hasProfession && hasLevel) {
                return profession.copy().append(Text.literal(" - ")).append(level);
            }
            if (hasProfession) {
                return profession;
            }
            if (hasLevel) {
                return level;
            }
            return Text.empty();
        }

        if (showProfession) {
            return hasProfession ? profession : Text.empty();
        }

        return hasLevel ? level : Text.empty();
    }

    private static double squareDistance(int distance) {
        return (double) distance * distance;
    }
}
