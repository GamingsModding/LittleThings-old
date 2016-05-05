package com.gamingsmod.littlethings.common.item.tools;

import com.gamingsmod.littlethings.common.item.base.ModItemTool;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

import java.util.Set;

public class ModItemAxe extends ModItemTool
{
    protected static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.melon_block, Blocks.ladder, Blocks.wooden_button, Blocks.wooden_pressure_plate);

    public ModItemAxe(String name, ToolMaterial material)
    {
        super(material.getDamageVsEntity() + 5.0F, -3.0F, material, EFFECTIVE_ON);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
}
