package com.gamingsmod.littlethings.common.item.tools;

import com.gamingsmod.littlethings.common.init.ModItems;
import com.gamingsmod.littlethings.common.item.base.IModItem;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItemSword extends ItemSword implements IModItem
{
    public ModItemSword(String name, ToolMaterial material)
    {
        super(material);
        this.setUnlocalizedName(name);
    }

    @Override
    public Item setUnlocalizedName(String name)
    {
        super.setUnlocalizedName(name);
        if (getRegistryName() == null)
            setRegistryName(name);

        GameRegistry.register(this);

        ModItems.ITEMS.add(this);

        return this;
    }

    @SideOnly(Side.CLIENT)
    public void registerItemVariants(String modId)
    {

    }

    @SideOnly(Side.CLIENT)
    public void registerRender()
    {
        Minecraft.getMinecraft().getRenderItem()
                .getItemModelMesher().register(this, 0, new ModelResourceLocation(LibMisc.PREFIX_MOD + this.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()), "inventory"));
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", LibMisc.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return getUnlocalizedName();
    }


    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public void registerItemModel(int meta, String name)
    {
        Minecraft.getMinecraft().getRenderItem()
                .getItemModelMesher().register(this, meta, new ModelResourceLocation(LibMisc.PREFIX_MOD + name, "inventory"));
    }
}
