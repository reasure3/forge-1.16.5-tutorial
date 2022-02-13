package com.reasure.tutorial.event.client;

import com.reasure.tutorial.TutorialMod;
import com.reasure.tutorial.client.ModKeyBinding;
import com.reasure.tutorial.entity.custom.PaperPlaneEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputEventHandler {
    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            onInput(mc, event.getKey(), event.getAction());
        }
    }

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            onInput(mc, event.getButton(), event.getAction());
        }
    }

    private static void onInput(Minecraft mc, int key, int action) {
        if (mc.player != null && mc.player.isPassenger()) {
            Entity vehicle = mc.player.getVehicle();
            if (vehicle instanceof PaperPlaneEntity && vehicle.getControllingPassenger() == mc.player) {
                boolean notScreen = mc.screen == null;
                boolean isForward = ModKeyBinding.forward.isDown() && notScreen;
                boolean isBackward = ModKeyBinding.backward.isDown() && notScreen;
                boolean isLeft = ModKeyBinding.left.isDown() && notScreen;
                boolean isRight = ModKeyBinding.right.isDown() && notScreen;
                ((PaperPlaneEntity) vehicle).updateInputs(isForward, isBackward, isLeft, isRight);
            }
        }
    }
}
