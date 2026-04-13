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
import com.astryxion.chaospersists.world.dimension.teleporter.TeleporterUtopia;
import java.util.Collections;
import java.util.List;

public class CommandVillageMania extends CommandBase {

    @Override
    public String getName() {
        return "villagemania";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.villagemania.usage";
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
        int dimId = ChaosPersists.getDimension(3);
        if (player.dimension == dimId) {
            player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "You are already in the Village Mania dimension."));
            return;
        }
        WorldServer world = DimensionManager.getWorld(dimId);
        if (world == null) {
            player.sendMessage(new TextComponentString(TextFormatting.RED + "Village Mania dimension is not available."));
            return;
        }
        double x = player.posX;
        double z = player.posZ;
        TeleporterUtopia teleporter = new TeleporterUtopia(world, x, z);
        player.getServer().getPlayerList().transferPlayerToDimension(player, dimId, teleporter);
        player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Teleported to Village Mania."));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        return Collections.emptyList();
    }
}
