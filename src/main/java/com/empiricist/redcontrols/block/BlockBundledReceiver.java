package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.tileentity.TEBundledReceiver;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

//@Optional.Interface(iface = "powercrystals.minefactoryreloaded.api.rednet.IRedNetInputNode", modid = "MineFactoryReloaded", striprefs = true)
public class BlockBundledReceiver extends BlockContainerBase{//} implements IRedNetInputNode {

    public BlockBundledReceiver(Material m){
        super(m);
    }
/*
    @Override
    public void onInputsChanged(World world, int x, int y, int z, ForgeDirection side, int[] inputValues) {
        TileEntity te = world.getTileEntity(x,y,z);
        if(te instanceof TEBundledReceiver){
            TEBundledReceiver teb = (TEBundledReceiver)te;
            byte[] inputBytes = new byte[16];
            int val;
            for(int i = 0; i < inputValues.length; i++){
                val = inputValues[i];
                inputBytes[i] = (byte)(val > 127 ? val - 256 : val);
            }
            teb.setBlockSignals(inputBytes);
            teb.onBundledInputChanged();
        }
    }
*/
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        LogHelper.info("Receiver block " + state.getBlock().getUnlocalizedName() + " does update tick");
        //updateTE(world, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block){
        super.neighborChanged(state, world, pos, block);
        LogHelper.info("Receiver block " + state.getBlock().getUnlocalizedName() + " does block change update caused by " + block.getUnlocalizedName());
        updateTE(world, pos);
    }

    public void updateTE(World world, BlockPos pos){
        //LogHelper.info("Receiver block does updateTE");
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof TEBundledReceiver){
            //LogHelper.info("  TEBundledReceiver found");
            TEBundledReceiver teb = (TEBundledReceiver)te;
            teb.onBundledInputChanged();
        }
    }
/*
    //only called if single connection mode
    @Override
    public void onInputChanged(World world, int x, int y, int z, ForgeDirection side, int inputValue) {}

    @Override
    @Optional.Method(modid="MineFactoryReloaded")
    public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side) {
        return RedNetConnectionType.CableAll;
    }
    */
}
