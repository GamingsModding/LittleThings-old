package com.gamingsmod.littlethings.client.render;

import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.init.ModItems;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemRender
{
    public static void preInit()
    {
        for (String name : ModItems.CrossBolt.getVariants()) {
            ModelBakery.registerItemVariants(ModItems.CrossBolt, new ResourceLocation(LibMisc.PREFIX_MOD + "crossbolt_" + name));
        }
    }

    public static void registerItemRender()
    {
        reg(ModItems.IronNugget);
        reg(ModItems.StoneStick);
        if (ConfigurationHandler.enableHammers) {
            reg(ModItems.WoodHammer);
            reg(ModItems.StoneHammer);
            reg(ModItems.IronHammer);
            reg(ModItems.GoldHammer);
            reg(ModItems.DiamondHammer);
        }

        if (ConfigurationHandler.enableExcavators) {
            reg(ModItems.WoodExcavator);
            reg(ModItems.StoneExcavator);
            reg(ModItems.IronExcavator);
            reg(ModItems.GoldExcavator);
            reg(ModItems.DiamondExcavator);
        }

        if (ConfigurationHandler.enableCrossbow) {
            reg(ModItems.CrossBow);
            int i = 0;
            for (String name : ModItems.CrossBolt.getVariants()) {
                reg(ModItems.CrossBolt, i++, "crossbolt_" + name);
            }
        }

        if (ConfigurationHandler.enableObsidianTools) {
            reg(ModItems.ObsidianSword);
            reg(ModItems.ObsidianPickaxe);
            reg(ModItems.ObsidianAxe);
            reg(ModItems.ObsidianSpade);
            reg(ModItems.ObsidianHoe);
            if (ConfigurationHandler.enableHammers)
                reg(ModItems.ObsidianHammer);
            if (ConfigurationHandler.enableExcavators)
                reg(ModItems.ObsidianExcavator);
        }

        if (ConfigurationHandler.enableEmeraldTools) {
            reg(ModItems.EmeraldSword);
            reg(ModItems.EmeraldPickaxe);
            reg(ModItems.EmeraldAxe);
            reg(ModItems.EmeraldSpade);
            reg(ModItems.EmeraldHoe);
            if (ConfigurationHandler.enableHammers)
                reg(ModItems.EmeraldHammer);
            if (ConfigurationHandler.enableExcavators)
                reg(ModItems.EmeraldExcavator);
        }

        if (ConfigurationHandler.enableBejeweledApple) {
            reg(ModItems.BejeweledApple);
        }

        if (ConfigurationHandler.enableExtraFood) {
            reg(ModItems.FriedEgg);
            reg(ModItems.SeedsPack);
        }
    }

    private static void reg(Item item)
    {
        String modId = LibMisc.PREFIX_MOD;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(item, 0, new ModelResourceLocation(modId + item.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()), "inventory"));
    }

    private static void reg(Item item, int meta, String file)
    {
        String modId = LibMisc.PREFIX_MOD;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(item, meta, new ModelResourceLocation(modId + file, "inventory"));
    }

    private static void reg(Item item, ItemMeshDefinition definition)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(item, definition);
    }
}
