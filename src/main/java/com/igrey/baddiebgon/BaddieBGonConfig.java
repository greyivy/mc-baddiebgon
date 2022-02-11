package com.igrey.baddiebgon;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.igrey.baddiebgon.BaddieBGon.LOGGER;
import static com.igrey.baddiebgon.BaddieBGon.MOD_ID;

@Config(name = MOD_ID)
public class BaddieBGonConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 5, max = 100)
    public int effectiveDistanceSpray = 50;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public String banHostileMobRegex = "";
    @ConfigEntry.Gui.Excluded
    private transient String previousBanHostileMobRegex = banHostileMobRegex;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public List<String> additionalHostileMobs = Arrays.asList(new String[]{
            "entity.minecraft.slime",
            "entity.minecraft.ghast"
    });

    public void validatePostLoad() {
        this.tryCompilePattern();
    }

    private void tryCompilePattern() {
        if (this.banHostileMobRegex.length() > 0) {
            try {
                this.pattern = Pattern.compile(this.banHostileMobRegex);
            } catch (Exception e) {
                // Reset field on invalid regex
                this.banHostileMobRegex = "";
                this.pattern = null;
            }
        } else {
            this.pattern = null;
        }

        this.previousBanHostileMobRegex = this.banHostileMobRegex;
    }

    @ConfigEntry.Gui.Excluded
    private transient Pattern pattern = null;
    public Pattern getPattern() {
        if (this.pattern == null || this.banHostileMobRegex != this.previousBanHostileMobRegex) {
            this.tryCompilePattern();
        }

        return this.pattern;
    }
}
