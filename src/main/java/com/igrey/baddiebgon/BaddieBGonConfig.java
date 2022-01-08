package com.igrey.baddiebgon;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.regex.Pattern;

import static com.igrey.baddiebgon.BaddieBGon.MOD_ID;

@Config(name = MOD_ID)
public class BaddieBGonConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 5, max = 100)
    public int effectiveDistanceSpray = 50;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public String banHostileMobRegex = "";

    public void validatePostLoad() {
        try {
            Pattern.compile(banHostileMobRegex);
        } catch (Exception e) {
            banHostileMobRegex = "";
        }
    }
}
