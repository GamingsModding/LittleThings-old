package com.gamingsmod.littlethings.common.item;

import com.gamingsmod.littlethings.common.item.base.ModItem;
import com.gamingsmod.littlethings.common.lib.LibItems;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemCrossbow extends ModItem
{
    public ItemCrossbow()
    {
        super(LibItems.CROSSBOW);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    public static ItemMeshDefinition getMesh()
    {
        return new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(LibMisc.PREFIX_MOD + stack.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()), "inventory");
            }
        };
    }
}
