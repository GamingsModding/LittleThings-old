package com.gamingsmod.littlethings.common.events;

import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.helper.NBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Random;

public class FriendsSkulls
{
    public static String[] playerNames = new String[]{"GingerMikie", "LilMissSpl3nd0r", "The_EliteAngel", "CamGaming69", "HCGamingMC"};
    public static String[] otherNames = new String[]{"RageGamingPE", "GullWolf"};

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onSkullHover(ItemTooltipEvent e)
    {
        if (e.getItemStack().getItem() instanceof ItemSkull && e.getItemStack().getMetadata() == 3) {
            ItemStack skull = e.getItemStack();
            String playerName = "";
            if (skull.getTagCompound() != null && skull.getTagCompound().hasKey("SkullOwner", 8)) {
                playerName = skull.getTagCompound().getString("SkullOwner");
            }

            if (skull.getTagCompound() != null && skull.getTagCompound().hasKey("SkullOwner", 10)) {
                NBTTagCompound nbttagcompound = skull.getTagCompound().getCompoundTag("SkullOwner");

                if (nbttagcompound.hasKey("Name", 8)) {
                    playerName = nbttagcompound.getString("Name");
                }
            }

            if (ConfigurationHandler.enableSkullOwner) {
                //TODO When waila updates - Add to tooltip
                e.getToolTip().remove(0);
                e.getToolTip().add(0, I18n.translateToLocal("tooltip.littlethings.playerSkull"));
                e.getToolTip().add(1, I18n.translateToLocalFormatted("tooltip.littlethings.skullOwner", playerName));
            }

            if (Arrays.asList(playerNames).contains(playerName)) {
                if (ConfigurationHandler.enableColorfulText || !I18n.canTranslate("tooltip.littlethings.skull." + playerName + ".clean"))
                    e.getToolTip().add(2, TextFormatting.ITALIC + I18n.translateToLocalFormatted("tooltip.littlethings.skull." + playerName));
                else
                    e.getToolTip().add(2, TextFormatting.ITALIC + I18n.translateToLocalFormatted("tooltip.littlethings.skull." + playerName + ".clean"));
            }
        }
    }

    @SubscribeEvent
    public void onMobKillDropSkull(LivingDropsEvent e)
    {
        if (!e.getSource().isExplosion() && !e.getSource().isFireDamage()) {
            Entity justDied = e.getEntity();
            justDied.getArmorInventoryList().forEach((itemStack -> {
                if (itemStack != null && itemStack.getItem() instanceof ItemSkull && itemStack.getMetadata() == 3) {
                    String playerName = "";
                    if (itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("SkullOwner", 8)) {
                        playerName = itemStack.getTagCompound().getString("SkullOwner");
                    }

                    if (itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("SkullOwner", 10)) {
                        NBTTagCompound nbttagcompound = itemStack.getTagCompound().getCompoundTag("SkullOwner");

                        if (nbttagcompound.hasKey("Name", 8)) {
                            playerName = nbttagcompound.getString("Name");
                        }
                    }

                    if (Arrays.asList(playerNames).contains(playerName) || Arrays.asList(otherNames).contains(playerName)) {
                        EntityItem ei = new EntityItem(justDied.worldObj, justDied.posX, justDied.posY, justDied.posZ, itemStack);
                        if (e.getDrops().contains(ei))
                            e.getDrops().remove(ei);

                        Random rng = new Random();

                        if (rng.nextDouble() < 0.1) e.getDrops().add(ei);
                    }
                }
            }));
        }
    }

    @SubscribeEvent
    public void onMobSpawn(LivingSpawnEvent e)
    {
        Random rng = new Random();
        if (rng.nextDouble() < ConfigurationHandler.percentOfSkullSpawn && e.getEntity() instanceof EntityZombie) {
            ItemStack skull = new ItemStack(Items.skull, 1, 3);
            int id = rng.nextInt(playerNames.length + otherNames.length);
            if (id + 1 > playerNames.length)
                NBTHelper.setString(skull, "SkullOwner", otherNames[id - playerNames.length]);
            else
                NBTHelper.setString(skull, "SkullOwner", playerNames[rng.nextInt(playerNames.length)]);

            e.getEntity().replaceItemInInventory(100 + EntityEquipmentSlot.HEAD.getIndex(), skull);
        }
    }
}
