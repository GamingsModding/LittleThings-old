package com.gamingsmod.littlethings.client.render;

import com.gamingsmod.littlethings.client.model.TEAnimalChestRenderer;
import com.gamingsmod.littlethings.common.block.BlockVanillaCraftingTables;
import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import com.gamingsmod.littlethings.common.tileentity.TileEntityAnimalChest;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class BlockRender
{
    public static void preInit()
    {
        String modId = LibMisc.PREFIX_MOD;
        if (ConfigurationHandler.enableExtraCraftingTables) {
            ModelBakery.registerItemVariants(
                    Item.getItemFromBlock(ModBlocks.VanillaCraftingTables),
                    new ResourceLocation(modId + "vanillaCraftingTable_acacia"),
                    new ResourceLocation(modId + "vanillaCraftingTable_birch"),
                    new ResourceLocation(modId + "vanillaCraftingTable_darkoak"),
                    new ResourceLocation(modId + "vanillaCraftingTable_jungle"),
                    new ResourceLocation(modId + "vanillaCraftingTable_spruce")
            );
        }

        if (ConfigurationHandler.enableClearGlass) {
            ResourceLocation[] rl = new ResourceLocation[16];
            int i = 0;
            for (EnumDyeColor color : EnumDyeColor.values()) {
                rl[i] = new ResourceLocation(modId + "stainedClearGlass_" + color.getName());
                i++;
            }

            ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.StainedClearGlass), rl);
        }
    }

    public static void registerBlockRenderer()
    {
        if (ConfigurationHandler.enableExtraCraftingTables) {
            for (BlockVanillaCraftingTables.Variant variant : BlockVanillaCraftingTables.Variant.values())
                reg(ModBlocks.VanillaCraftingTables, variant.getId(), "vanillaCraftingTable_" + variant.getName());
        }

        if (ConfigurationHandler.enableItemElevator)
            reg(ModBlocks.ItemEleveator);

        if (ConfigurationHandler.enableUpgradedFurnaces)
            for (Block block : ModBlocks.UpgradedFurnaces)
                reg(block);

        if (ConfigurationHandler.enableAnimalChests) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnimalChest.class, new TEAnimalChestRenderer());

            for (Block block : ModBlocks.AnimalChests)
                reg(block);
        }

        if (ConfigurationHandler.enableClearGlass) {
            reg(ModBlocks.ClearGlass);
            for (EnumDyeColor color : EnumDyeColor.values())
                reg(ModBlocks.StainedClearGlass, color.getMetadata(), "stainedClearGlass_" + color.getName());
        }

        if (ConfigurationHandler.enableUnenchantingTable)
            reg(ModBlocks.UnenchantingTable);
    }

    private static void reg(Block block)
    {
        String modId = LibMisc.PREFIX_MOD;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modId + block.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()), "inventory"));
    }

    private static void reg(Block block, int meta, String file) {
        String modId = LibMisc.PREFIX_MOD;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(modId + file, "inventory"));
    }
}
