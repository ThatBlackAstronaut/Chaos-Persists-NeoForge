/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.client.registry.ClientRegistry
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  com.astryxion.chaospersists.KeyHandler
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 */
package com.astryxion.chaospersists.util;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class KeyHandler {
    private final Minecraft mc = Minecraft.getMinecraft();
    public static final String KEY_CATEGORY = "key.categories.chaospersists";
    public static final KeyBinding KEY_FLY_UP = new KeyBinding("chaospersists UP/FAST", 56, "key.categories.chaospersists");

    public KeyHandler() {
        ClientRegistry.registerKeyBinding((KeyBinding)KEY_FLY_UP);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
    }
}
