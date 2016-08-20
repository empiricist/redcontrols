package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.tileentity.TileEntityADC;
import com.empiricist.redcontrols.tileentity.TileEntityDAC;
import com.empiricist.redcontrols.utility.LogHelper;
import com.empiricist.redcontrols.utility.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockAnalogDigitalConverter extends BlockBundledEmitter{

    public BlockAnalogDigitalConverter() {
        super(Material.ROCK);
        setCreativeTab(null);
        this.setHardness(3f);
        name = "adc";
        this.setUnlocalizedName(name);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return side != null;//yes, except not up side of block
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityADC();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote && hand == EnumHand.MAIN_HAND){
            worldIn.setBlockState(pos, ModBlocks.dac.getDefaultState());
            worldIn.markAndNotifyBlock(pos, worldIn.getChunkFromBlockCoords(pos), ModBlocks.adc.getDefaultState(), ModBlocks.dac.getDefaultState(), 3);
            worldIn.scheduleBlockUpdate(pos, ModBlocks.dac, 1, 1);
        }
        return true;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (!worldIn.isRemote) {
            //LogHelper.info("Ticking ADC block, will notify neighbors");
            worldIn.markAndNotifyBlock(pos, worldIn.getChunkFromBlockCoords(pos), state, state, 3);//send to clients and neighboring blocks
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {//possible redstone update (blockIn is block that changed)
        //LogHelper.info("ADC's neighbor changed, state is " + state + ", pos is " + pos + ", block is " + blockIn);
        TileEntity te = worldIn.getTileEntity(pos);
        if( te != null && te instanceof TileEntityADC){
            int power = worldIn.isBlockIndirectlyGettingPowered(pos);//WorldHelper.maxRedstonePower(worldIn, pos);
            //LogHelper.info("  Found power " + power);
            TileEntityADC teadc = (TileEntityADC) te;
            //LogHelper.info("  Last power  " + teadc.getLastPower());
            if(power != teadc.getLastPower()){//send update if redstone changed
                //LogHelper.info("    scheduling update to notify clients and adjacent blocks");
                teadc.setLastPower(power);
                worldIn.scheduleUpdate(pos, this, 1);
            }
        }

        //WorldHelper.scheduleTickNeighbors(worldIn, pos);
//        Chunk chunk = worldIn.getChunkFromBlockCoords(pos);
//        if ( chunk == null || chunk.isPopulated()) {
//            LogHelper.info("    notifying clients");
//            worldIn.notifyBlockUpdate(pos, state, state, 3);
//        }
//
//        if ( !worldIn.isRemote ) {
//            LogHelper.info("    Scheduling block update");
//            worldIn.scheduleBlockUpdate(pos, state.getBlock(), 1, 2);
//        }
    }
}
