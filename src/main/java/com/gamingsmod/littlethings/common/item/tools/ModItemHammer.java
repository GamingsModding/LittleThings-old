package com.gamingsmod.littlethings.common.item.tools;

import com.gamingsmod.littlethings.common.helper.Vector3;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Arrays;

/**
 * Inspired By Tinkers Construct
 */
public class ModItemHammer extends ModItemPickaxe
{
    private static final Material[] effective = new Material[]{
            Material.iron,
            Material.anvil,
            Material.rock,
            Material.ice,
            Material.glass,
            Material.packedIce,
            Material.piston
    };

    public ModItemHammer(String name, ToolMaterial material)
    {
        super(name, material);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos, EntityLivingBase entityLiving)
    {
        EnumFacing facing = getFacing(entityLiving);

        if (isEffective(blockIn)) {
            if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
                mineOrOtherwise(worldIn, pos.north(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.south(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.east(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.west(), stack, entityLiving);

                mineOrOtherwise(worldIn, pos.east().north(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.east().south(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.west().north(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.west().south(), stack, entityLiving);
            } else {
                mineOrOtherwise(worldIn, pos.up(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.down(), stack, entityLiving);
                if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
                    mineOrOtherwise(worldIn, pos.east(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.east().up(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.east().down(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.west(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.west().up(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.west().down(), stack, entityLiving);
                } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                    mineOrOtherwise(worldIn, pos.north(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.north().up(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.north().down(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.south(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.south().up(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.south().down(), stack, entityLiving);
                }
            }
        }

        return super.onBlockDestroyed(stack, worldIn, blockIn, pos, entityLiving);
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        Block block = blockIn.getBlock();

        if (block == Blocks.obsidian) {
            return this.toolMaterial.getHarvestLevel() == 3;
        } else if (block != Blocks.diamond_block && block != Blocks.diamond_ore) {
            if (block != Blocks.emerald_ore && block != Blocks.emerald_block) {
                if (block != Blocks.gold_block && block != Blocks.gold_ore) {
                    if (block != Blocks.iron_block && block != Blocks.iron_ore) {
                        if (block != Blocks.lapis_block && block != Blocks.lapis_ore) {
                            if (block != Blocks.redstone_ore && block != Blocks.lit_redstone_ore) {
                                Material material = blockIn.getMaterial();
                                return material == Material.rock || (material == Material.iron || material == Material.anvil);
                            } else {
                                return this.toolMaterial.getHarvestLevel() >= 2;
                            }
                        } else {
                            return this.toolMaterial.getHarvestLevel() >= 1;
                        }
                    } else {
                        return this.toolMaterial.getHarvestLevel() >= 1;
                    }
                } else {
                    return this.toolMaterial.getHarvestLevel() >= 2;
                }
            } else {
                return this.toolMaterial.getHarvestLevel() >= 2;
            }
        } else {
            return this.toolMaterial.getHarvestLevel() >= 2;
        }
    }

    protected void mineOrOtherwise(World world, BlockPos pos, ItemStack tool, EntityLivingBase player)
    {
        IBlockState state = world.getBlockState(pos);

        if (isEffective(state)) {
            state.getBlock().harvestBlock(world, (EntityPlayer) player, pos, state, world.getTileEntity(pos), tool);
            world.destroyBlock(pos, false);
        }
    }

    private boolean isEffective(IBlockState state)
    {
        return Arrays.asList(effective).contains(state.getMaterial()) || EFFECTIVE_ON.contains(state.getBlock());
    }

    protected EnumFacing getFacing(Entity player)
    {
        Vector3 origin = Vector3.fromEntity(player);
        if (player instanceof EntityPlayer)
            origin.add(0, player.getEyeHeight(), 0);

        Vector3 look = new Vector3(player.getLookVec());

        Vector3 end = origin.copy().add(look.copy().normalize().multiply(10));
        RayTraceResult pos = player.worldObj.rayTraceBlocks(origin.toVec3D(), end.toVec3D());
        return pos.sideHit;
    }
}
