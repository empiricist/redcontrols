package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.tileentity.TileEntitySerializer;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockSerializer extends BlockBundledReceiver{

    public BlockSerializer() {
        super(Material.ROCK);
        this.setHardness(3f);
        name = "serializer";
        this.setUnlocalizedName(name);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return side != null;//yes, except not up side of block
    }

    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side){
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof TileEntitySerializer){
            TileEntitySerializer teTx = (TileEntitySerializer) te;
            return teTx.getRedstoneStrength();
        }
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntitySerializer();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
            worldIn.setBlockState(pos, ModBlocks.deserializer.getDefaultState());
            worldIn.markAndNotifyBlock(pos, worldIn.getChunkFromBlockCoords(pos), ModBlocks.serializer.getDefaultState(), ModBlocks.deserializer.getDefaultState(), 3);//tell clients and adjacent blocks
            worldIn.scheduleBlockUpdate(pos, ModBlocks.deserializer, 1, 1);
        }
        return true;
    }

//    @Override
//    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
//        super.updateTick(worldIn, pos, state, rand);
//        if (!worldIn.isRemote) {
//            LogHelper.info("  Ticking serializer block, will notify neighbors");
//            worldIn.markAndNotifyBlock(pos, worldIn.getChunkFromBlockCoords(pos), state, state, 3);//send to clients and neighboring blocks
//        }
//    }
}
