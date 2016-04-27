package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.LittleThings;
import com.gamingsmod.littlethings.common.block.base.ModBlockInventory;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import com.gamingsmod.littlethings.common.lib.LibGuiId;
import com.gamingsmod.littlethings.common.tileentity.TileEntityUnenchantingTable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockUnenchantingTable extends ModBlockInventory
{
    protected static final AxisAlignedBB boundingbox = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    public BlockUnenchantingTable()
    {
        super(Material.rock);
        this.setUnlocalizedName(LibBlocks.UNENCHANTING_TABLE);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(5.0F);
        this.setResistance(2000.0F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return boundingbox;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
            playerIn.openGui(LittleThings.instance, LibGuiId.UNENCHANTING_TABLE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityUnenchantingTable();
    }

    public static void runUnenchant(EntityPlayerMP playerEntity, int xp, ItemStack stack, BlockPos pos)
    {
        if (playerEntity.experienceLevel < xp && !playerEntity.isCreative())
            return;

        if (stack != null && stack.getEnchantmentTagList() != null) {
            World world = playerEntity.worldObj;
            IInventory te  = (IInventory) world.getTileEntity(pos);
            if (te instanceof TileEntityUnenchantingTable) {
                ItemStack books = te.getStackInSlot(1);
                if ((books != null && books.getItem() instanceof ItemBook) && te.getStackInSlot(2) == null) {
                    NBTTagList list = stack.getEnchantmentTagList();
                    NBTTagCompound enchTag = list.getCompoundTagAt(0);
                    ItemStack book = new ItemStack(Items.enchanted_book);

                    NBTTagCompound baseTag = new NBTTagCompound();
                    NBTTagList enchList = new NBTTagList();
                    enchList.appendTag(enchTag);
                    baseTag.setTag("StoredEnchantments", enchList);
                    book.setTagCompound(baseTag);

                    te.setInventorySlotContents(2, book);
                    --books.stackSize;

                    list.removeTag(0);
                    if (list.tagCount() == 0) {
                        stack.getTagCompound().removeTag("ench");
                        if (stack.getTagCompound().hasNoTags()) {
                            stack.setTagCompound(null);
                        }
                    }

                    if (stack.isItemStackDamageable()) {
                        int damage = world.rand.nextInt(1 + (stack.getMaxDamage() / 4));
                        int m = stack.getMaxDamage();
                        damage = Math.min(m, damage + 1 + (m / 10)) + (m == 1 ? 1 : 0);
                        if (stack.attemptDamageItem(damage, world.rand)) {
                            te.setInventorySlotContents(0, null);
                            stack = null;
                        }
                    }
                    if (stack != null)
                        te.setInventorySlotContents(0, stack);

                    if (!playerEntity.isCreative()) {
                        playerEntity.removeExperienceLevel(xp);
                    }
                }
            }
        }
    }
}
