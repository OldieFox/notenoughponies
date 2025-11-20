package com.neponies;

import net.minecraft.text.Text;

public interface VillagerPonyEntityAccessor {
    String getPonySkinID();
    void checkAndSetRace();
    NEPRace getPonyRace();
    void setPonyRace(NEPRace race);
    Text getPonyCustomName();
    Text getProfessionName();
    boolean canShowCustomPonyName();
    boolean canShowProfessionName();
    void setOnInitializeListener(Runnable listener);
}
