package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.tileentity.TileEntityADC;
import com.empiricist.redcontrols.tileentity.TileEntityDeserializer;
import com.empiricist.redcontrols.utility.LogHelper;
import com.empiricist.redcontrols.utility.WorldHelper;
import net.minecraft.block.Block;
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
import java.util.Random;

public class BlockDeserializer extends BlockBundledEmitter{

    public BlockDeserializer() {
        super(Material.ROCK);
        setCreativeTab(null);
        this.setHardness(3f);
        name = "deserializer";
        this.setUnlocalizedName(name);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return side != null;//yes, except not up side of block
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityDeserializer();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote && hand == EnumHand.MAIN_HAND){
            worldIn.setBlockState(pos, ModBlocks.serializer.getDefaultState());
            worldIn.markAndNotifyBlock(pos, worldIn.getChunkFromBlockCoords(pos), ModBlocks.deserializer.getDefaultState(), ModBlocks.serializer.getDefaultState(), 3);
            worldIn.scheduleBlockUpdate(pos, ModBlocks.serializer, 1, 1);
        }
        return true;
    }

//    @Override
//    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
//        super.updateTick(worldIn, pos, state, rand);
//        if (!worldIn.isRemote) {
//            LogHelper.info("Ticking Deserializer block, will notify neighbors");
//            worldIn.markAndNotifyBlock(pos, worldIn.getChunkFromBlockCoords(pos), state, state, 3);//send to clients and neighboring blocks
//        }
//    }

//    @Override
//    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {//possible redstone update (blockIn is block that changed)
//        LogHelper.info("Deserializer's neighbor changed, state is " + state + ", pos is " + pos + ", block is " + blockIn);
//        TileEntity te = worldIn.getTileEntity(pos);
//        if( te != null && te instanceof TileEntityDeserializer){
//            int power = WorldHelper.maxRedstonePower(worldIn, pos);
//            LogHelper.info("  Found power " + power);
//            TileEntityDeserializer teRx = (TileEntityDeserializer) te;
//            LogHelper.info("  Last power  " + teRx.getLastPower());
//            if(power != teRx.getLastPower()){//send update if redstone changed
//                LogHelper.info("    scheduling update to notify clients and adjacent blocks");
//                teRx.setLastPower(power);
//                worldIn.scheduleUpdate(pos, this, 1);
//            }
//        }
//    }
}
