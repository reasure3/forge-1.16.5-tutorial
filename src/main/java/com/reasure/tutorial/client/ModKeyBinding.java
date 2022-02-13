package com.reasure.tutorial.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public final class ModKeyBinding {
    private ModKeyBinding() {}

    public static KeyBinding test;

    public static KeyBinding forward;
    public static KeyBinding backward;
    public static KeyBinding left;
    public static KeyBinding right;

    private static KeyBinding registerKey(String name, String category, int keycode) {
        final KeyBinding key = new KeyBinding("key." + name, keycode, "key.categories." + category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }

    public static void init() {
        test = registerKey("testing", "test", GLFW.GLFW_KEY_K);

        forward = registerKey("plane.forward", "plane", GLFW.GLFW_KEY_W);
        backward = registerKey("plane.backward", "plane", GLFW.GLFW_KEY_S);
        left = registerKey("plane.left", "plane", GLFW.GLFW_KEY_A);
        right = registerKey("plane.right", "plane", GLFW.GLFW_KEY_D);
    }
}
