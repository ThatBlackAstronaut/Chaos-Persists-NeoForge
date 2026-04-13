/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 *  net.minecraftforge.fml.relauncher.Side
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.RiderControlMessage
 *  com.astryxion.chaospersists.RiderControlMessageHandler
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelHandler$Sharable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package com.astryxion.chaospersists.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.network.RiderControlMessage;
import io.netty.channel.ChannelHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ChannelHandler.Sharable
public class RiderControlMessageHandler
implements IMessageHandler<RiderControlMessage, IMessage> {
    private static final Logger L = LogManager.getLogger();

    public IMessage onMessage(RiderControlMessage message, MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            return null;
        }
        ChaosPersists.flyup_keystate = message.keystate;
        return null;
    }
}

