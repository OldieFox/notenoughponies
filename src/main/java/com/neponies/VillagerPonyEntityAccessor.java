package com.neponies;

import com.minelittlepony.api.pony.meta.Race;
import net.minecraft.text.Text;

public interface VillagerPonyEntityAccessor {
    String getPonySkinID();
    void checkAndSetRace();
    Race getPonyRace();
    void setPonyRace(Race race);
    Text getPonyCustomName();
    Text getProfessionName();
    boolean canShowCustomPonyName();
    boolean canShowProfessionName();
    void setOnInitializeListener(Runnable listener);
}
