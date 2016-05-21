package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.tileentity.TileEntityDAC;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDigitalAnalogConverter extends BlockBundledReceiver{

    public BlockDigitalAnalogConverter() {
        super(Material.rock);
        this.setHardness(3f);
        name = "dac";
        this.setUnlocalizedName(name);
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof TileEntityDAC){
            TileEntityDAC teDAC = (TileEntityDAC) te;
            return ((TileEntityDAC) te).getRedstoneStrength();
        }
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityDAC();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            worldIn.setBlockState(pos, ModBlocks.adc.getDefaultState());
            worldIn.markAndNotifyBlock(pos, worldIn.getChunkFromBlockCoords(pos), ModBlocks.dac.getDefaultState(), ModBlocks.adc.getDefaultState(), 3);
            worldIn.scheduleBlockUpdate(pos, ModBlocks.adc, 1, 1);
        }
        return true;
    }
}
