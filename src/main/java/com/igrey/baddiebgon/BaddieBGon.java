package com.igrey.baddiebgon;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BaddieBGon implements ModInitializer {
    public static final String MOD_ID = "baddiebgon";

    public static final BaddieBGonItem ITEM_SPRAY = new BaddieBGonItemSpray();
    public static final List<BaddieBGonItem> ITEMS = List.of(ITEM_SPRAY);

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static BaddieBGonConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(BaddieBGonConfig.class, JanksonConfigSerializer::new);

        Registry.register(Registry.ITEM, new Identifier("baddiebgon", "spray"), ITEM_SPRAY);
        config = AutoConfig.getConfigHolder(BaddieBGonConfig.class).getConfig();
    }
}
