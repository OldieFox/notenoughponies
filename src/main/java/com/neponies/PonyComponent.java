package com.neponies;

import com.minelittlepony.api.pony.meta.Race;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;

public class PonyComponent implements Component, AutoSyncedComponent {
    private VillagerCustomPonyData data = new VillagerCustomPonyData();

    public VillagerCustomPonyData getData() {
        return data;
    }

    public void setData(VillagerCustomPonyData data) {
        this.data = data;
    }

    public Race getRace() {
        return data.getRace();
    }

    public String getSkinID() {
        return data.getSkinID();
    }

    public String getFirstName() {
        return data.getFirstName();
    }

    public String getSecondName() {
        return data.getSecondName();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.data = VillagerCustomPonyData.readNbt(tag);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        this.data.writeNbt(tag);
    }
}