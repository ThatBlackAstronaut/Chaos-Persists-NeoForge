/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.AntRobot
 *  com.astryxion.chaospersists.ItemSpiderRobotKit
 *  com.astryxion.chaospersists.MobStats
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.entity.AntRobot;
import com.astryxion.chaospersists.util.MobStats;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*
 * Exception performing whole class analysis ignored.
 */
public class ItemSpiderRobotKit
extends Item {
    public ItemSpiderRobotKit(int i) {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.TOOLS);
        if (i == ChaosPersists.BaseItemID + 471) {
            this.setMaxDamage(ChaosPersists.SpiderRobot_stats.health);
        } else {
            this.setMaxDamage(ChaosPersists.AntRobot_stats.health);
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer par2EntityPlayer, World par3World, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack par1ItemStack = par2EntityPlayer.getHeldItem(hand);
        int par4 = pos.getX();
        int par5 = pos.getY();
        int par6 = pos.getZ();
        Entity ent;
        if (par3World.isRemote) {
            return EnumActionResult.SUCCESS;
        }
        String name = "robot_spider";
        if (par1ItemStack.getItem() == ChaosPersists.AntRobotKit) {
            name = "robot_red_ant";
        }
        if ((ent = ItemSpiderRobotKit.spawnCreature((World)par3World, (int)0, (String)name, (double)((double)par4 + 0.5), (double)((double)par5 + 1.01), (double)((double)par6 + 0.5))) != null) {
            EntityLiving e = (EntityLiving)ent;
            e.setHealth((float)(this.getMaxDamage() - this.getDamage(par1ItemStack)));
            if (ent instanceof EntityLiving && par1ItemStack.hasDisplayName()) {
                ((EntityLiving)ent).setCustomNameTag(par1ItemStack.getDisplayName());
            }
            par3World.playSound(par2EntityPlayer.posX, par2EntityPlayer.posY, par2EntityPlayer.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.PLAYERS, 1.0f, par3World.rand.nextFloat() * 0.2f + 0.9f, false);
            if (ent instanceof AntRobot) {
                AntRobot a = (AntRobot)ent;
                a.setOwned();
            }
        }
        if (!par2EntityPlayer.capabilities.isCreativeMode) {
            par1ItemStack.shrink(1);
        }
        return EnumActionResult.SUCCESS;
    }

    public static Entity spawnCreature(World par0World, int par1, String name, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = name == null ? EntityList.createEntityByID((int)par1, (World)par0World) : EntityList.createEntityByIDFromName(new net.minecraft.util.ResourceLocation("chaospersists", (String)name), par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }}

