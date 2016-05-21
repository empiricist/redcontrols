package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.tileentity.TileEntityADC;
import com.empiricist.redcontrols.tileentity.TileEntityDAC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAnalogDigitalConverter extends BlockBundledEmitter{

    public BlockAnalogDigitalConverter() {
        super(Material.field_151576_e);
        func_149647_a(null);
        this.func_149711_c(3f);
        name = "adc";
        this.func_149663_c(name);
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public TileEntity func_149915_a(World world, int metadata) {
        return new TileEntityADC();
    }

    @Override
    public boolean func_180639_a(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.field_72995_K){
            worldIn.func_175656_a(pos, ModBlocks.dac.func_176223_P());
            worldIn.markAndNotifyBlock(pos, worldIn.func_175726_f(pos), ModBlocks.adc.func_176223_P(), ModBlocks.dac.func_176223_P(), 3);
            worldIn.func_180497_b(pos, ModBlocks.dac, 1, 1);
        }
        return true;
    }

    @Override
    public void func_176204_a(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        worldIn.markAndNotifyBlock(pos, worldIn.func_175726_f(pos), ModBlocks.adc.func_176223_P(), ModBlocks.adc.func_176223_P(), 3);
    }
}
