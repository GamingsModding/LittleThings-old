package com.gamingsmod.littlethings.client;

import com.gamingsmod.littlethings.client.render.ItemRender;
import com.gamingsmod.littlethings.client.versioning.VersionChecker;
import com.gamingsmod.littlethings.common.CommonProxy;
import com.gamingsmod.littlethings.common.events.DeprecatedWarning;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.init.ModEntities;
import com.gamingsmod.littlethings.common.tileentity.base.TileEntityXpStore;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);

        DeprecatedWarning.modHasDeprecatedClasses = false;
        DeprecatedWarning.deprecatedAreas = new String[0];
        MinecraftForge.EVENT_BUS.register(new DeprecatedWarning());

        ModBlocks.registerBlockVariants();
        ItemRender.preInit();
        ModEntities.initRender();
        new VersionChecker().init();
    }

    public void init(FMLInitializationEvent e)
    {
        super.init(e);

//        BlockRender.registerBlockRenderer();
        ModBlocks.registerBlockRender();
        ItemRender.registerItemRender();
    }

    @Override
    public void updateXpBlock(BlockPos pos, int xp)
    {
        World world = Minecraft.getMinecraft().theWorld;
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityXpStore)
            ((TileEntityXpStore) te).setXp(xp);
    }
}
