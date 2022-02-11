package com.igrey.baddiebgon;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import java.util.List;

import static com.igrey.baddiebgon.BaddieBGon.config;

public class BaddieBGonItemSpray extends BaddieBGonItem {
    public BaddieBGonItemSpray() {
        super(new FabricItemSettings()
                .group(ItemGroup.TOOLS)
                .maxCount(1)
                .fireproof()
                .rarity(Rarity.EPIC));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add( new TranslatableText("item.baddiebgon.spray.tooltip") );
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public int getEffectiveDistance() {
        return config.effectiveDistanceSpray;
    }
}
