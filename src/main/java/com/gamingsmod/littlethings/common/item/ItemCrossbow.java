package com.gamingsmod.littlethings.common.item;

import com.gamingsmod.littlethings.common.entity.EntityCrossBolt;
import com.gamingsmod.littlethings.common.entity.EntityCrossBoltExplosive;
import com.gamingsmod.littlethings.common.entity.EntityCrossBoltPotion;
import com.gamingsmod.littlethings.common.init.ModItems;
import com.gamingsmod.littlethings.common.item.base.ModItem;
import com.gamingsmod.littlethings.common.lib.LibItems;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemCrossbow extends ModItem
{
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

        if ((playerIn.isCreative() || ammo != null)) {
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.entity_arrow_shoot, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) * 0.5F);
            if (!worldIn.isRemote) {
                EntityCrossBolt entityCrossBolt = null;
                if (ammo != null) {
                    if (ammo.getMetadata() == 0)
                        entityCrossBolt = new EntityCrossBolt(worldIn, playerIn);
                    else if (ammo.getMetadata() == 1)
                        entityCrossBolt = new EntityCrossBoltExplosive(worldIn, playerIn);
                    else if (ammo.getMetadata() == 2)
                        entityCrossBolt = (new EntityCrossBoltPotion(worldIn, playerIn)).setPotionEffect(MobEffects.glowing);

                }

                if (entityCrossBolt != null) {
                    entityCrossBolt.func_184547_a(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3.0F, 1.0F);
                    worldIn.spawnEntityInWorld(entityCrossBolt);

                    if (!playerIn.isCreative()) ammo.stackSize--;

                    if (ammo.stackSize <= 0)
                        playerIn.inventory.deleteStack(ammo);

                    playerIn.getCooldownTracker().setCooldown(this, 20);
                    itemStackIn.damageItem(1, playerIn);
                }
            }
        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    public static void registerModels()
    {
        final String[] ICONS = new String[]{"crossbow1", "crossbow2", "crossbow3"};
        for (String icon : ICONS)
            ModelBakery.registerItemVariants(ModItems.CrossBow, new ResourceLocation(LibMisc.PREFIX_MOD + icon));
    }

    public static ItemMeshDefinition registerMesh()
    {
        return stack -> {
            float cooldown = Minecraft.getMinecraft().thePlayer.getCooldownTracker().getCooldown(stack.getItem(), 0F);
            if (cooldown > 0.75)
                return new ModelResourceLocation(LibMisc.PREFIX_MOD + stack.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()) + "1", "inventory");
            else if (cooldown > 0.25)
                return new ModelResourceLocation(LibMisc.PREFIX_MOD + stack.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()) + "2", "inventory");
            return new ModelResourceLocation(LibMisc.PREFIX_MOD + stack.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()) + "3", "inventory");
        };
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
