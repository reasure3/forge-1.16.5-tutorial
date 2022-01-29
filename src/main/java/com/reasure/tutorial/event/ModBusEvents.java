package com.reasure.tutorial.event;

import com.reasure.tutorial.TutorialMod;
import com.reasure.tutorial.entity.ModEntityTypes;
import com.reasure.tutorial.entity.custom.BuffZombieEntity;
import com.reasure.tutorial.entity.custom.HogEntity;
import com.reasure.tutorial.entity.custom.PigeonEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(final EntityAttributeCreationEvent event) {
        TutorialMod.getLOGGER().info("loading mod entity");
        event.put(ModEntityTypes.BUFF_ZOMBIE.get(), BuffZombieEntity.createAttributes().build());
        event.put(ModEntityTypes.PIGEON.get(), PigeonEntity.createAttributes().build());
        event.put(ModEntityTypes.HOG.get(), HogEntity.createAttributes().build());
    }
}
