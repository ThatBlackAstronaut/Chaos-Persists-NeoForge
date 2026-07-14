package com.astryxion.chaospersists.network;

import com.astryxion.chaospersists.entity.SpiderRobot;
import com.astryxion.chaospersists.util.KeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class RiderControl {
    private final RiderControlMessage rcm = new RiderControlMessage();
    private final SimpleChannel network;
    private int keystate = 0;

    public RiderControl(SimpleChannel network) {
        this.network = network;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent evt) {
        if (evt.phase != TickEvent.Phase.END) {
            return;
        }
        syncSpiderRider();
        int newkeystate = 0;
        if (KeyHandler.KEY_FLY_UP.isDown()) {
            newkeystate = 1;
        }
        if (this.keystate != newkeystate) {
            this.rcm.keystate = newkeystate;
            this.network.sendToServer(this.rcm);
            this.keystate = newkeystate;
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent evt) {
        if (evt.phase != TickEvent.Phase.END) {
            return;
        }
        syncSpiderRider();
    }

    private static void syncSpiderRider() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || !mc.player.isPassenger()) {
            return;
        }
        Entity vehicle = mc.player.getVehicle();
        if (vehicle instanceof SpiderRobot spider) {
            spider.syncLocalPassengerAfterTick();
        }
    }
}
