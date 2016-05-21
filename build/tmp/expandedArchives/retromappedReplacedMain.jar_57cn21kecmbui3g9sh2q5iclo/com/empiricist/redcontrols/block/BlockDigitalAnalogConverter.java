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
        super(Material.field_151576_e);
        this.func_149711_c(3f);
        name = "dac";
        this.func_149663_c(name);
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public boolean func_149744_f()
    {
        return true;
    }

    @Override
    public int func_180656_a(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        TileEntity te = worldIn.func_175625_s(pos);
        if(te instanceof TileEntityDAC){
            TileEntityDAC teDAC = (TileEntityDAC) te;
            return ((TileEntityDAC) te).getRedstoneStrength();
        }
        return 0;
    }

    @Override
    public TileEntity func_149915_a(World world, int metadata) {
        return new TileEntityDAC();
    }

    @Override
    public boolean func_180639_a(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.field_72995_K) {
            worldIn.func_175656_a(pos, ModBlocks.adc.func_176223_P());
            worldIn.markAndNotifyBlock(pos, worldIn.func_175726_f(pos), ModBlocks.dac.func_176223_P(), ModBlocks.adc.func_176223_P(), 3);
            worldIn.func_180497_b(pos, ModBlocks.adc, 1, 1);
        }
        return true;
    }
}
