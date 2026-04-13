package com.astryxion.chaospersists.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.world.dimension.teleporter.TeleporterMining;
import java.util.Collections;
import java.util.List;

public class CommandMining extends CommandBase {

    @Override
    public String getName() {
        return "mining";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.mining.usage";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender.getCommandSenderEntity() instanceof EntityPlayerMP)) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "This command can only be used by a player."));
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) sender.getCommandSenderEntity();
        int dimId = ChaosPersists.getDimension(2);
        if (player.dimension == dimId) {
            player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "You are already in the Mining dimension."));
            return;
        }
        WorldServer world = DimensionManager.getWorld(dimId);
        if (world == null) {
            player.sendMessage(new TextComponentString(TextFormatting.RED + "Mining dimension is not available."));
            return;
        }
        double x = player.posX;
        double z = player.posZ;
        TeleporterMining teleporter = new TeleporterMining(world, x, z);
        player.getServer().getPlayerList().transferPlayerToDimension(player, dimId, teleporter);
        player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Teleported to Mining."));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        return Collections.emptyList();
    }
}
