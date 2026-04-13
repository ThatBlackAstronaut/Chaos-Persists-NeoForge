/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
 *  com.astryxion.chaospersists.KeyHandler
 *  com.astryxion.chaospersists.RiderControl
 *  com.astryxion.chaospersists.RiderControlMessage
 *  net.minecraft.client.settings.KeyBinding
 */
package com.astryxion.chaospersists.network;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import com.astryxion.chaospersists.util.KeyHandler;
import com.astryxion.chaospersists.network.RiderControlMessage;
import net.minecraft.client.settings.KeyBinding;

public class RiderControl {
    private final RiderControlMessage rcm = new RiderControlMessage();
    private final SimpleNetworkWrapper network;
    private int keystate = 0;

    public RiderControl(SimpleNetworkWrapper network) {
        this.network = network;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent evt) {
        int newkeystate = 0;
        if (KeyHandler.KEY_FLY_UP.isKeyDown()) {
            newkeystate = 1;
        }
        if (this.keystate != newkeystate) {
            this.rcm.keystate = newkeystate;
            this.network.sendToServer((IMessage)this.rcm);
            this.keystate = newkeystate;
        }
    }
}

