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
        super(Material.rock);
        setCreativeTab(null);
        this.setHardness(3f);
        name = "adc";
        this.setUnlocalizedName(name);
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityADC();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote){
            worldIn.setBlockState(pos, ModBlocks.dac.getDefaultState());
            worldIn.markAndNotifyBlock(pos, worldIn.getChunkFromBlockCoords(pos), ModBlocks.adc.getDefaultState(), ModBlocks.dac.getDefaultState(), 3);
            worldIn.scheduleBlockUpdate(pos, ModBlocks.dac, 1, 1);
        }
        return true;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        worldIn.markAndNotifyBlock(pos, worldIn.getChunkFromBlockCoords(pos), ModBlocks.adc.getDefaultState(), ModBlocks.adc.getDefaultState(), 3);
    }
}
