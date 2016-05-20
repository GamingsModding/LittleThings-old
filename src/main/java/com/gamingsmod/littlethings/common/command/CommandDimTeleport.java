package com.gamingsmod.littlethings.common.command;

import com.gamingsmod.littlethings.common.lib.LibCommandLevel;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.lang3.math.NumberUtils;

public class CommandDimTeleport extends CommandBase
{

    @Override
    public String getCommandName()
    {
        return "tpdim";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return LibCommandLevel.MODERATOR;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return I18n.translateToLocal("command.littlethings.tpdim");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        /**
         * USAGES:
         * /tpdim 0 - Teleport Current Player to Overworld
         * /tpdim 0 100 64 100 - Teleport Current Player to Overworld at coords (100, 64, 100)
         * /tpdim RageGamingPE 0 - Teleport RageGamingPE to Overworld
         * /tpdim RageGamingPE 0 100 64 100 - Teleport RageGamingPE to Overworld at coords (100, 64, 100)
         * /tpdim RageGamingPE - Teleport Current Player to RageGamingPE
         * /tpdim RageGamingPE HCGamingMC - Teleport RageGamingPE to HCGamingMC
         */
        if (args.length > 5)
            throw new WrongUsageException(getCommandUsage(sender));

        if (NumberUtils.isNumber(args[0])) {
            // Current player to dim
            int dimension = Integer.valueOf(args[0]);
            if (!DimensionManager.isDimensionRegistered(dimension))
                throw new CommandException("command.littlethings.tpdim.noDim");

            EntityPlayer host = getCommandSenderAsPlayer(sender);
            if (host.dimension == dimension) {
                notifyOperators(sender, this, "command.littlethings.tpdim.success.dim", host.getDisplayNameString(), dimension, Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]));
                vanillaTeleport(host, Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]));
            } else {
                if (args.length == 4) {
                    notifyOperators(sender, this, "command.littlethings.tpdim.success.dim", host.getDisplayNameString(), dimension, Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]));
                    teleportPlayerToDim(host.worldObj, dimension, Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]), host);
                } else if (args.length == 1) {
                    WorldServer newServer = server.worldServerForDimension(dimension);
                    BlockPos spawn = newServer.getSpawnCoordinate();
                    if (spawn == null)
                        spawn = newServer.getSpawnPoint();

                    notifyOperators(sender, this, "command.littlethings.tpdim.success.dim", host.getDisplayNameString(), dimension, spawn.getX(), spawn.getY(), spawn.getZ());
                    teleportPlayerToDim(host.worldObj, dimension, spawn.getX(), spawn.getY(), spawn.getZ(), host);
                } else {
                    throw new WrongUsageException(getCommandUsage(sender));
                }
            }
        } else {
            /**
             * Current Player to Another Player
             * OR
             * Another Player to Other Dimension (+ Coords)
             */

            // Player to Player
            if (args.length == 1) {
                EntityPlayer host = getCommandSenderAsPlayer(sender);
                EntityPlayer recp = getPlayer(server, sender, args[0]);
                notifyOperators(sender, this, "command.littlethings.tpdim.success.player", host.getDisplayNameString(), recp.getDisplayNameString());
                playerToPlayer(server, sender, host, recp);
            } else if (args.length == 2) {
                if (NumberUtils.isNumber(args[1])) {
                    int dimension = Integer.valueOf(args[1]);
                    if (!DimensionManager.isDimensionRegistered(dimension))
                        throw new CommandException("command.littlethings.tpdim.noDim");
                    EntityPlayer host = getPlayer(server, sender, args[0]);
                    if (host.dimension == dimension) {
                        BlockPos spawn = host.worldObj.getSpawnPoint();
                        notifyOperators(sender, this, "command.littlethings.tpdim.success.dim", host.getDisplayNameString(), dimension, spawn.getX(), spawn.getY(), spawn.getZ());
                        vanillaTeleport(host, spawn.getX(), spawn.getY(), spawn.getZ());
                    } else {
                        WorldServer newServer = server.worldServerForDimension(dimension);
                        BlockPos spawn = newServer.getSpawnCoordinate();
                        if (spawn == null) spawn = newServer.getSpawnPoint();
                        notifyOperators(sender, this, "command.littlethings.tpdim.success.dim", host.getDisplayNameString(), dimension, spawn.getX(), spawn.getY(), spawn.getZ());
                        teleportPlayerToDim(host.worldObj, dimension, spawn.getX(), spawn.getY(), spawn.getZ(), host);
                    }
                } else {
                    playerToPlayer(server, sender, getPlayer(server, sender, args[0]), getPlayer(server, sender, args[1]));
                }
            } else if (args.length == 5) {
                EntityPlayer host = getPlayer(server, sender, args[0]);
                int dimension = Integer.valueOf(args[1]);
                if (!DimensionManager.isDimensionRegistered(dimension))
                    throw new CommandException("command.littlethings.tpdim.noDim");
                if (host.dimension == dimension) {
                    notifyOperators(sender, this, "command.littlethings.tpdim.success.dim", host.getDisplayNameString(), dimension, Double.valueOf(args[2]), Double.valueOf(args[3]), Double.valueOf(args[4]));
                    vanillaTeleport(host, Double.valueOf(args[2]), Double.valueOf(args[3]), Double.valueOf(args[4]));
                } else {
                    notifyOperators(sender, this, "command.littlethings.tpdim.success.dim", host.getDisplayNameString(), dimension, Double.valueOf(args[2]), Double.valueOf(args[3]), Double.valueOf(args[4]));
                    teleportPlayerToDim(host.worldObj, dimension, Double.valueOf(args[2]), Double.valueOf(args[3]), Double.valueOf(args[4]), host);
                }
            } else {
                throw new WrongUsageException(getCommandUsage(sender));

            }
        }
    }

    private void vanillaTeleport(EntityPlayer host, double x, double y, double z)
    {
        host.setPositionAndUpdate(x, y, z);
    }

    private void playerToPlayer(MinecraftServer server, ICommandSender sender, EntityPlayer host, EntityPlayer recp) throws CommandException
    {
        if (host.dimension == recp.dimension) {
            //Maybe teleport using code from CommandTeleport
            vanillaTeleport(host, recp.posX, recp.posY, recp.posZ);
        }

        teleportPlayerToDim(host.worldObj, recp.dimension, recp.posX, recp.posY, recp.posZ, host);
    }

    private Entity teleportPlayerToDim(World oldWorld, int newWorldID, double d, double e, double f, Entity entity)
    {
        MinecraftServer server = entity.getServer();
        WorldServer oldWorldServer = server.worldServerForDimension(entity.dimension);
        WorldServer newWorldServer = server.worldServerForDimension(newWorldID);
        if (entity instanceof EntityPlayer) {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            if (!player.worldObj.isRemote) {
                player.worldObj.theProfiler.startSection("portal");
                player.worldObj.theProfiler.startSection("changeDimension");
                PlayerList config = player.mcServer.getPlayerList();
                player.closeScreen();
                player.dimension = newWorldServer.provider.getDimension();
                player.playerNetServerHandler.sendPacket(new SPacketRespawn(player.dimension, player.worldObj.getDifficulty(), newWorldServer.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
                oldWorldServer.removeEntity(player);
                player.isDead = false;
                player.setLocationAndAngles(d, e, f, player.rotationYaw, player.rotationPitch);
                newWorldServer.spawnEntityInWorld(player);
                player.setWorld(newWorldServer);
                config.preparePlayer(player, oldWorldServer);
                player.playerNetServerHandler.setPlayerLocation(d, e, f, entity.rotationYaw, entity.rotationPitch);
                player.interactionManager.setWorld(newWorldServer);
                config.updateTimeAndWeatherForPlayer(player, newWorldServer);
                config.syncPlayerInventory(player);
                player.worldObj.theProfiler.endSection();
                oldWorldServer.resetUpdateEntityTick();
                newWorldServer.resetUpdateEntityTick();
                player.worldObj.theProfiler.endSection();
                for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                    player.playerNetServerHandler.sendPacket(new SPacketEntityEffect(player.getEntityId(), potionEffect));
                }
                player.playerNetServerHandler.sendPacket(new SPacketSetExperience(player.experience, player.experienceTotal, player.experienceLevel));
                FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, oldWorldServer.provider.getDimension(), player.dimension);
            }
            player.worldObj.theProfiler.endSection();
            return player;
        }
        return null;
    }
}
