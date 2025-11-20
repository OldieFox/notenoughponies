package com.neponies;

import net.minecraft.nbt.NbtCompound;
import java.util.Objects;

public class VillagerCustomPonyData {

    // -------------------------
    // Enum for NBT / Packet keys
    // -------------------------
    public enum Key {
        SKIN_ID("skin_id"),
        RACE("race"),
        FIRST_NAME("first_name"),
        SECOND_NAME("second_name");

        private final String text;

        Key(String text) {
            this.text = text;
        }

        public String asString() {
            return text;
        }
    }

    // -------------------------
    // Data fields
    // -------------------------

    private String skinID;
    private NEPRace race;
    private String firstName;
    private String secondName;

    // -------------------------
    // Constructors
    // -------------------------
    public VillagerCustomPonyData() {
        this(null, NEPRace.HUMAN, "", "");
    }

    public VillagerCustomPonyData(String skinID, NEPRace race, String firstName, String secondName) {
        this.skinID = skinID;
        this.race = race;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    // -------------------------
    // Getters
    // -------------------------
    public String getSkinID() { return skinID; }
    public NEPRace getRace() { return race; }
    public String getFirstName() { return firstName; }
    public String getSecondName() { return secondName; }


    // -------------------------
    // Setters
    // -------------------------
    public void setSkinID(String skinID) { this.skinID = skinID; }
    public void setRace(NEPRace race) { this.race = race; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setSecondName(String secondName) { this.secondName = secondName; }

    // -------------------------
    // NBT Serialization
    // -------------------------
    public NbtCompound writeNbt(NbtCompound tag) {
        tag.putString(Key.SKIN_ID.asString(), skinID != null ? skinID : "");
        tag.putString(Key.RACE.asString(), race.name());
        tag.putString(Key.FIRST_NAME.asString(), firstName);
        tag.putString(Key.SECOND_NAME.asString(), secondName);
        return tag;
    }

    public static VillagerCustomPonyData readNbt(NbtCompound tag) {
        String skinId = tag.getString(Key.SKIN_ID.asString());
        if (skinId.isEmpty()) skinId = null;

        NEPRace race = NEPRace.HUMAN; // Default to HUMAN if tag is missing to prevent crashes.
        if (tag.contains(Key.RACE.asString())) {
            try {
                race = NEPRace.valueOf(tag.getString(Key.RACE.asString()));
            } catch (IllegalArgumentException e) {
                // Ignore invalid race string in NBT.
            }
        }

        String first = tag.getString(Key.FIRST_NAME.asString());
        String second = tag.getString(Key.SECOND_NAME.asString());

        return new VillagerCustomPonyData(skinId, race, first, second);
    }
    // -------------------------
    // Equals / hashCode
    // -------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VillagerCustomPonyData)) return false;
        VillagerCustomPonyData that = (VillagerCustomPonyData) o;
        return Objects.equals(skinID, that.skinID) &&
                race == that.race &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(secondName, that.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skinID, race, firstName, secondName);
    }

    // -------------------------
    // DataTracker setup
    // -------------------------
    public static final int PONIES_SKINS_COUNT = 397;

}