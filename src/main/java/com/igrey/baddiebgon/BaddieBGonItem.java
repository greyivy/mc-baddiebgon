package com.igrey.baddiebgon;

import net.minecraft.item.Item;

public abstract class BaddieBGonItem extends Item {
    public BaddieBGonItem(Settings settings) {
        super(settings);
    }

    public abstract int getEffectiveDistance();
}
