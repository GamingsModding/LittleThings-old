package com.gamingsmod.littlethings.client.render;

import com.gamingsmod.littlethings.common.block.VanillaCraftingTables;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class BlockRender
{
    public static void preInit()
    {
        String modId = LibMisc.PREFIX_MOD;
        ModelBakery.registerItemVariants(
                Item.getItemFromBlock(ModBlocks.VanillaCraftingTables),
                new ResourceLocation(modId + "vanillaCraftingTable_acacia"),
                new ResourceLocation(modId + "vanillaCraftingTable_birch"),
                new ResourceLocation(modId + "vanillaCraftingTable_darkoak"),
                new ResourceLocation(modId + "vanillaCraftingTable_jungle"),
                new ResourceLocation(modId + "vanillaCraftingTable_spruce")
        );
    }

    public static void registerBlockRenderer()
    {
        for (VanillaCraftingTables.Variant variant:
             VanillaCraftingTables.Variant.values()) {
            reg(ModBlocks.VanillaCraftingTables, variant.getId(), "vanillaCraftingTable_" + variant.getName());
        }
    }

    private static void reg(Block block)
    {
        String modId = LibMisc.PREFIX_MOD;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modId + block.getUnlocalizedName().substring(5), "inventory"));
    }

    private static void reg(Block block, int meta, String file) {
        String modId = LibMisc.PREFIX_MOD;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(modId + file, "inventory"));
    }
}
