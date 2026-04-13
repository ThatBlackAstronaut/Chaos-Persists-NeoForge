package com.astryxion.chaospersists.mixin;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes ForgeGradle #748: dev classpath can pollute Side with BUKKIT,
 * causing NPE in NetworkRegistry.newChannel. Redirect Side.values() to
 * only CLIENT and SERVER when in deobf/dev environment.
 */
@Mixin(net.minecraftforge.fml.common.network.NetworkRegistry.class)
public class MixinNetworkRegistry {

    @Redirect(
        method = "newChannel(Ljava/lang/String;[Lio/netty/channel/ChannelHandler;)Ljava/util/EnumMap;",
        at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/relauncher/Side;values()[Lnet/minecraftforge/fml/relauncher/Side;"),
        remap = false
    )
    private Side[] redirectNewChannel1() {
        Object isDeobf = Launch.blackboard.get("fml.deobfuscatedEnvironment");
        if (isDeobf instanceof Boolean && (Boolean) isDeobf) {
            return new Side[]{Side.CLIENT, Side.SERVER};
        }
        return Side.values();
    }

    @Redirect(
        method = "newChannel(Lnet/minecraftforge/fml/common/ModContainer;Ljava/lang/String;[Lio/netty/channel/ChannelHandler;)Ljava/util/EnumMap;",
        at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/relauncher/Side;values()[Lnet/minecraftforge/fml/relauncher/Side;"),
        remap = false
    )
    private Side[] redirectNewChannel2() {
        Object isDeobf = Launch.blackboard.get("fml.deobfuscatedEnvironment");
        if (isDeobf instanceof Boolean && (Boolean) isDeobf) {
            return new Side[]{Side.CLIENT, Side.SERVER};
        }
        return Side.values();
    }
}
