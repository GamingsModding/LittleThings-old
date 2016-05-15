package com.gamingsmod.littlethings.common.events;

import com.gamingsmod.littlethings.common.helper.Vector3;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GetExpBottles
{
    @SubscribeEvent
    public void onHover(ItemTooltipEvent e)
    {
        if (e.getItemStack().getItem() instanceof ItemExpBottle)
            e.getToolTip().add("Sneak + Right Click empty bottles to craft");
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem e)
    {
        if (e.getItemStack() != null && e.getItemStack().getItem() instanceof ItemGlassBottle && e.getEntityPlayer().isSneaking() && (e.getEntityPlayer().experienceLevel >= 1 || e.getEntityPlayer().isCreative())) {
            EntityPlayer player = e.getEntityPlayer();

            Vector3 origin = Vector3.fromEntity(player).add(0, player.getEyeHeight(), 0);
            Vector3 look = new Vector3(player.getLookVec());
            Vector3 end = origin.copy().add(look.copy().normalize().multiply(5));

            RayTraceResult rtr = player.worldObj.rayTraceBlocks(origin.toVec3D(), end.toVec3D(), true);
            IBlockState hitBlockState = null;
            if (rtr != null) hitBlockState = e.getWorld().getBlockState(rtr.getBlockPos());

            if (hitBlockState == null || hitBlockState.getBlock() != Blocks.water) {
                e.getItemStack().stackSize--;
                if (e.getItemStack().stackSize <= 0)
                    e.getEntityPlayer().inventory.deleteStack(e.getItemStack());
                e.getEntityPlayer().inventory.addItemStackToInventory(new ItemStack(Items.experience_bottle));
                e.getEntityPlayer().removeExperienceLevel(1);
            }
        }
    }
}
