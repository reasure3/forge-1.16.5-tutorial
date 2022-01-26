package com.reasure.tutorial.item;

import com.reasure.tutorial.TutorialMod;
import com.reasure.tutorial.item.custom.DowsingMachineItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MOD_ID);

    public static final RegistryObject<Item> EXAMPLE = ITEMS.register("example", () ->
            new Item(new Item.Properties().tab(ModItemGroup.TAB_EXAMPLE)));

    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () ->
            new Item(new Item.Properties().tab(ModItemGroup.TAB_EXAMPLE)));

    public static final RegistryObject<Item> DOWSING_MACHINE = ITEMS.register("dowsing_machine", () ->
            new DowsingMachineItem(new Item.Properties().tab(ModItemGroup.TAB_EXAMPLE).durability(32)));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
