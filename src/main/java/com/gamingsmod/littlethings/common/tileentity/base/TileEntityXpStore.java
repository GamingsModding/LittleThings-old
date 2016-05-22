package com.gamingsmod.littlethings.common.tileentity.base;

import com.gamingsmod.littlethings.common.network.MessageHandler;
import com.gamingsmod.littlethings.common.network.message.MessageHeldXP;
import com.gamingsmod.littlethings.common.network.message.MessageXP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TileEntityXpStore extends TileEntity
{
    public float xp;
    public int xpTotal;
    public int xpLevel;

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setFloat("XP", xp);
        compound.setInteger("XPTotal", xpTotal);
        compound.setFloat("XPLevel", xpLevel);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        xp = compound.getFloat("XP");
        xpTotal = compound.getInteger("XPTotal");
        xpLevel = compound.getInteger("XPLevel");
    }

    public void addXpFromLevel(int level, int playerLevel)
    {
        for (int i = 0; i < level; i++)
            addXp(levelToRaw(playerLevel - i));
    }

    // Changed from {@link EntityPlayer#addExperience}
    public void addXp(int amount)
    {
        int i = Integer.MAX_VALUE - this.xpTotal;

        if (amount > i) amount = i;

        this.xp += (float) amount / (float) this.xpBarCap();

        for (this.xpTotal += amount; this.xp >= 1.0F; this.xp /= (float) this.xpBarCap()) {
            this.xp = (this.xp - 1.0F) * (float) this.xpBarCap();
            this.addLevel(1);
        }
    }

    //Changed from {@link EntityPlayer#addExperienceLevel}
    public void addLevel(int amount)
    {
        this.xpLevel += amount;

        if (this.xpLevel < 0) {
            this.xpLevel = 0;
            this.xp = 0.0F;
            this.xpTotal = 0;
        }
    }

    //Changed from {@link EntityPlayer#removeExperienceLevel}
    public void removeLevel(int amount)
    {
        this.xpLevel -= amount;

        if (this.xpLevel < 0) {
            this.xpLevel = 0;
            this.xp = 0.0F;
            this.xpTotal = 0;
        }
    }

    public IMessage updateStoredXP(EntityPlayer player, int lvl, int action)
    {
        //TAKE FROM PLAYER
        if (action == MessageXP.TAKE_EXP) {
            if (lvl >= player.experienceLevel) lvl = player.experienceLevel;
            addXpFromLevel(lvl, player.experienceLevel);
            player.removeExperienceLevel(lvl);
        } else if (action == MessageXP.GIVE_EXP) {
            //GIVE TO PLAYER
            if (lvl > this.xpLevel) lvl = this.xpLevel;
            removeLevel(lvl);
            player.addExperienceLevel(lvl);
        }
        this.markDirty();
        MessageHandler.INSTANCE.sendToAll(new MessageHeldXP(getPos(), xpLevel));
        return null;
    }

    private int levelToRaw(int lvl)
    {
        return lvl >= 30 ? 112 + (lvl - 30) * 9 : (lvl >= 15 ? 37 + (lvl - 15) * 5 : 7 + lvl * 2);
    }

    //Changed from {@link EntityPlayer#xpBarCap}
    public int xpBarCap()
    {
        return this.xpLevel >= 30 ? 112 + (this.xpLevel - 30) * 9 : (this.xpLevel >= 15 ? 37 + (this.xpLevel - 15) * 5 : 7 + this.xpLevel * 2);
    }
}
