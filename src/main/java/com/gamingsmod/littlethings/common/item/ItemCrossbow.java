package com.gamingsmod.littlethings.common.item;

import com.gamingsmod.littlethings.common.entity.EntityCrossBolt;
import com.gamingsmod.littlethings.common.helper.NBTHelper;
import com.gamingsmod.littlethings.common.item.base.ModItem;
import com.gamingsmod.littlethings.common.lib.LibItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemCrossbow extends ModItem
{
    private static final String COOLDOWN_KEY = "cooldown";
    private static final int COOLDOWN_MAX = 20;

    public ItemCrossbow()
    {
        super(LibItems.CROSSBOW);
        this.setMaxDamage(350);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        ItemStack ammo = getAmmo(playerIn);
        int cooldown = NBTHelper.getInt(itemStackIn, COOLDOWN_KEY);

        if ((playerIn.isCreative() || ammo != null) && cooldown >= COOLDOWN_MAX) {
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.entity_arrow_shoot, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) * 0.5F);
            if (!worldIn.isRemote) {
                EntityCrossBolt entityCrossBolt = new EntityCrossBolt(worldIn, playerIn);
                entityCrossBolt.func_184547_a(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3.0F, 1.0F);
                worldIn.spawnEntityInWorld(entityCrossBolt);

                if (ammo != null && !playerIn.isCreative()) ammo.stackSize--;

                itemStackIn.damageItem(1, playerIn);
                NBTHelper.setInteger(itemStackIn, COOLDOWN_KEY, 0);
            }
        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (isSelected) {
            int cooldown = NBTHelper.getInt(stack, COOLDOWN_KEY);
            if (cooldown < COOLDOWN_MAX) {
                NBTHelper.setInteger(stack, COOLDOWN_KEY, ++cooldown);
            }
        }
    }

    private ItemStack getAmmo(EntityPlayer player)
    {
        if (this.isValidAmmo(player.getHeldItem(EnumHand.OFF_HAND))) {
            return player.getHeldItem(EnumHand.OFF_HAND);
        } else if (this.isValidAmmo(player.getHeldItem(EnumHand.MAIN_HAND))) {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isValidAmmo(itemstack)) {
                    return itemstack;
                }
            }

            return null;
        }
    }

    protected boolean isValidAmmo(ItemStack stack)
    {
        return stack != null && stack.getItem() instanceof ItemCrossBolt;
    }
}
