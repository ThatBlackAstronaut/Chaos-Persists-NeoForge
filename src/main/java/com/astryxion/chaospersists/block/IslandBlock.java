/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.IslandBlock
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockReed
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Locale;

/*
 * Exception performing whole class analysis ignored.
 */
public class IslandBlock
extends BlockReed {
    public IslandBlock() { this(0); }
    protected IslandBlock(int par1) {
        super();
        // Match 1.7.10: worldgen uses random ticks so islands grow sparsely. Player placement still uses onBlockAdded schedule.
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public net.minecraft.util.math.AxisAlignedBB getBoundingBox(net.minecraft.block.state.IBlockState state, net.minecraft.world.IBlockAccess source, net.minecraft.util.math.BlockPos pos) {
        float var3 = 0.375f;
        return new net.minecraft.util.math.AxisAlignedBB(0.5 - var3, 0.0, 0.5 - var3, 0.5 + var3, 1.0, 0.5 + var3);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (world.isRemote) {
            return;
        }
        world.scheduleUpdate(pos, this, 40);
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            this.runIslandSpawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), rand);
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote) {
            return;
        }
        this.runIslandSpawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), rand);
    }

    private void runIslandSpawn(World world, int par2, int par3, int par4, Random par5Random) {
        boolean isok;
        int n = 1 + par5Random.nextInt(3);
        int m = 64;
        if (ChaosPersists.IslandSizeFactor == 2) {
            m = 55;
        }
        if (ChaosPersists.IslandSizeFactor == 1) {
            m = 45;
        }
        for (int i = 0; i < n; ++i) {
            int height = 12 + par5Random.nextInt(m);
            isok = true;
            block1:
            for (int k = -10; k <= 10; ++k) {
                for (int j = -10; j <= 10; ++j) {
                    Block bid = world.getBlockState(new BlockPos(par2 + j, par3 + height, par4 + k)).getBlock();
                    if (bid == Blocks.AIR) {
                        continue;
                    }
                    isok = false;
                    continue block1;
                }
            }
            if (!isok) {
                continue;
            }
            if (par5Random.nextInt(25) == 1) {
                IslandBlock.spawnCreature(world, "Island", (double)par2, (double)(par3 + height), (double)par4);
                continue;
            }
            IslandBlock.spawnCreature(world, "IslandToo", (double)par2, (double)(par3 + height), (double)par4);
        }
        world.setBlockState(new BlockPos(par2, par3, par4), Blocks.AIR.getDefaultState(), 2);
        world.setBlockState(new BlockPos(par2, par3 + 1, par4), Blocks.AIR.getDefaultState(), 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (worldIn.rand.nextInt(20) != 1) {
            return;
        }
        for (int j1 = 0; j1 < 20; ++j1) {
            worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
                    (double)((float)pos.getX() + worldIn.rand.nextFloat()),
                    (double)pos.getY() + (double)worldIn.rand.nextFloat(),
                    (double)((float)pos.getZ() + worldIn.rand.nextFloat()),
                    0.0, 0.0, 0.0);
        }
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock((Block)ChaosPersists.MyIslandBlock);
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        ResourceLocation rl;
        if ("Island".equals(par1)) {
            rl = new ResourceLocation("chaospersists", "island");
        } else if ("IslandToo".equals(par1)) {
            rl = new ResourceLocation("chaospersists", "island_too");
        } else {
            rl = new ResourceLocation("chaospersists", par1.toLowerCase(Locale.ROOT));
        }
        var8 = EntityList.createEntityByIDFromName(rl, par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    /**
     * Same UX as {@link com.astryxion.chaospersists.item.ItemRandomDungeon}: Fortune, and use on stone/cobble/grass/dirt (y≥40) to place the island block above.
     */
    public static class ItemIslandBlock extends ItemBlock {

        public ItemIslandBlock(Block block) {
            super(block);
            this.setMaxStackSize(1);
            this.setCreativeTab(CreativeTabs.REDSTONE);
        }

        @Override
        public void onCreated(ItemStack stack, World world, EntityPlayer player) {
            stack.addEnchantment(Enchantments.FORTUNE, 2);
        }

        @Override
        public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
            int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            if (lvl <= 0) {
                stack.addEnchantment(Enchantments.FORTUNE, 2);
            }
        }

        @Override
        public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                          EnumFacing facing, float hitX, float hitY, float hitZ) {
            ItemStack stack = player.getHeldItem(hand);
            Block clicked = world.getBlockState(pos).getBlock();
            if (clicked != Blocks.STONE && clicked != Blocks.COBBLESTONE && clicked != Blocks.GRASS && clicked != Blocks.DIRT) {
                return EnumActionResult.FAIL;
            }
            // Low sky in mod dimensions (e.g. Islands / Chaos4 grass ~y=7): only enforce in Overworld.
            if (world.provider.getDimension() == 0 && pos.getY() < 40) {
                return EnumActionResult.FAIL;
            }
            if (!world.isRemote) {
                BlockPos up = pos.up();
                if (!world.isAirBlock(up) || !ChaosPersists.MyIslandBlock.canPlaceBlockAt(world, up)) {
                    return EnumActionResult.FAIL;
                }
                IBlockState state = ChaosPersists.MyIslandBlock.getDefaultState();
                world.setBlockState(up, state, 2);
            }
            if (!player.capabilities.isCreativeMode) {
                stack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
    }
}

