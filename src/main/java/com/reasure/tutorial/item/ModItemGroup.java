package com.reasure.tutorial.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModItemGroup {
    public static final ItemGroup TAB_EXAMPLE = new ItemGroup("tab_example") {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.EXAMPLE.get());
        }
    };
}
