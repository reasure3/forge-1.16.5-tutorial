package com.reasure.tutorial.entity;

import com.reasure.tutorial.TutorialMod;
import com.reasure.tutorial.entity.custom.BuffZombieEntity;
import com.reasure.tutorial.entity.custom.HogEntity;
import com.reasure.tutorial.entity.custom.PigeonEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, TutorialMod.MOD_ID);

    public static final RegistryObject<EntityType<HogEntity>> HOG = ENTITY_TYPES.register("hog",
            () -> EntityType.Builder.of(HogEntity::new, EntityClassification.CREATURE)
                    .sized(0.9f, 0.9f) // 히트박스 크기 (단위: 블럭)
                    .build(new ResourceLocation(TutorialMod.MOD_ID, "hog").toString()));

    public static final RegistryObject<EntityType<BuffZombieEntity>> BUFF_ZOMBIE = ENTITY_TYPES.register("buff_zombie",
            () -> EntityType.Builder.of(BuffZombieEntity::new, EntityClassification.MONSTER)
                    .sized(1.0f, 3.0f) // 히트박스 크기 (단위: 블럭)
                    .build(new ResourceLocation(TutorialMod.MOD_ID, "buff_zombie").toString()));

    public static final RegistryObject<EntityType<PigeonEntity>> PIGEON = ENTITY_TYPES.register("pigeon",
            () -> EntityType.Builder.of(PigeonEntity::new, EntityClassification.CREATURE)
                    .sized(0.4f, 0.3f) // 히트박스 크기 (단위: 블럭)
                    .build(new ResourceLocation(TutorialMod.MOD_ID, "pigeon").toString()));

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
