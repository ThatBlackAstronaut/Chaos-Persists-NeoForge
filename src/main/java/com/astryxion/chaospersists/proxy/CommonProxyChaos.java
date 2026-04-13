/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.network.NetworkRegistry
 *  net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
 *  net.minecraftforge.fml.relauncher.Side
 *  com.astryxion.chaospersists.CommonProxyChaos
 *  com.astryxion.chaospersists.RiderControlMessage
 *  com.astryxion.chaospersists.RiderControlMessageHandler
 */
package com.astryxion.chaospersists.proxy;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import com.astryxion.chaospersists.network.RiderControlMessage;
import com.astryxion.chaospersists.network.RiderControlMessageHandler;

public class CommonProxyChaos {
    private SimpleNetworkWrapper network;

    public SimpleNetworkWrapper getNetwork() {
        return this.network;
    }

    public void registerRenderThings() {
    }

    public void registerBlockModels() {
    }

    public void registerBlockColors() {
    }

    public void registerLeafColors() {
    }

    public void registerItemColors() {
    }

    public void registerSoundThings() {
    }

    public void registerKeyboardInput() {
    }

    public void registerNetworkStuff() {
        this.network = NetworkRegistry.INSTANCE.newSimpleChannel("chaospersists");
        this.network.registerMessage(RiderControlMessageHandler.class, RiderControlMessage.class, 0, Side.SERVER);
    }

    public int setArmorPrefix(String string) {
        return 0;
    }
}

