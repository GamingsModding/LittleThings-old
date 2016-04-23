package com.gamingsmod.littlethings.common.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class EmeraldsEverywhere implements IWorldGenerator
{
    int clusterCount;
    public static WorldGenMinable generator;

    public EmeraldsEverywhere(int clusterCount)
    {
        this.clusterCount = clusterCount;

        generator = new WorldGenMinable(Blocks.emerald_ore.getDefaultState(), 4);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        for (int i = 0; i < clusterCount; i++){
            int x = chunkX * 16 + random.nextInt(16);
            int y = random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);

            generator.generate(world, random, new BlockPos(x, y, z));
        }
    }
}
