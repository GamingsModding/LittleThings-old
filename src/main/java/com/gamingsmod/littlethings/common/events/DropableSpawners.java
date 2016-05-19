package com.gamingsmod.littlethings.common.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class DropableSpawners
{
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent e)
    {
        IBlockState state = e.getState();
        World world = e.getWorld();
        BlockPos pos = e.getPos();
        EntityPlayer player = e.getPlayer();
        ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
        if (state.getBlock() == Blocks.mob_spawner) {
            Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(heldItem);
            if (enchants.containsKey(Enchantments.silkTouch)) {
                ItemStack stack = new ItemStack(Blocks.mob_spawner, 1, 0);
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                world.getTileEntity(pos).writeToNBT(nbttagcompound);
                stack.setTagInfo("BlockEntityTag", nbttagcompound);

                e.setExpToDrop(0);
                e.getWorld().spawnEntityInWorld(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
            }
        }
    }
}
