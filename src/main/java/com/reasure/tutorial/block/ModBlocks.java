package com.reasure.tutorial.block;

import com.reasure.tutorial.TutorialMod;
import com.reasure.tutorial.block.custom.SpeedBoosterBlock;
import com.reasure.tutorial.item.ModItemGroup;
import com.reasure.tutorial.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TutorialMod.MOD_ID);

    public static final RegistryObject<Block> SILVER_ORE = register("silver_ore", () ->
            new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()
                    .strength(3.0F, 3.0F)));

    public static final RegistryObject<Block> SILVER_BLOCK = register("silver_block", () ->
            new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY).harvestTool(ToolType.PICKAXE)
                    .requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> SPEED_BOOSTER_BLOCK = register("speed_booster_block", () ->
            new SpeedBoosterBlock(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY)
                    .strength(3.0F, 6.0F)));

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }

    private static RegistryObject<Block> registerNoItem(String name, Supplier<Block> block) {
        return BLOCKS.register(name, block);
    }

    private static RegistryObject<Block> register(String name, Supplier<Block> block) {
        RegistryObject<Block> ret = registerNoItem(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(ModItemGroup.TAB_EXAMPLE)));
        return ret;
    }
}
