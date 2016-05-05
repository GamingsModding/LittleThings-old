package com.gamingsmod.littlethings.common.block.base;

import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.item.base.ItemBlockMeta;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlock extends Block
{
    public ModBlock(Material material)
    {
        super(material);
    }

    public ModBlock(String name)
    {
        this(name, Material.rock);
    }

    public ModBlock(String name, Material material)
    {
        super(material);
        this.setUnlocalizedName(name);
    }

    public ModBlock(String name, Material material, MapColor color)
    {
        super(material, color);
        this.setUnlocalizedName(name);
    }

    @Override
    public Block setUnlocalizedName(String name)
    {
        super.setUnlocalizedName(name);
        if (getRegistryName() == null)
            setRegistryName(name);

        GameRegistry.register(this);
        if (this instanceof IMetaBlockName)
            GameRegistry.register(new ItemBlockMeta(this).setRegistryName(this.getRegistryName()));
        else
            GameRegistry.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));

        ModBlocks.BLOCKS.add(this);

        return this;
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", LibMisc.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockVariants(String modId)
    {

    }

    @SideOnly(Side.CLIENT)
    public void registerRender()
    {
        Minecraft.getMinecraft().getRenderItem()
                .getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(LibMisc.PREFIX_MOD + this.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()), "inventory"));
    }

    protected void registerItemModel(int meta, String file)
    {
        Minecraft.getMinecraft().getRenderItem()
                .getItemModelMesher().register(Item.getItemFromBlock(this), meta, new ModelResourceLocation(LibMisc.PREFIX_MOD + file, "inventory"));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
