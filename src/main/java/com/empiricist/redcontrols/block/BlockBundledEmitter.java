package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.utility.LogHelper;
import com.enderio.core.common.util.DyeColor;
import com.google.common.collect.Multimap;
import crazypants.enderio.conduit.AbstractConduitNetwork;
import crazypants.enderio.conduit.IConduitBundle;
import crazypants.enderio.conduit.redstone.IRedstoneConduit;
import crazypants.enderio.conduit.redstone.RedstoneConduitNetwork;
import crazypants.enderio.conduit.redstone.Signal;
import crazypants.enderio.conduit.redstone.SignalSource;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;

//@Optional.Interface(iface = "powercrystals.minefactoryreloaded.api.rednet.IRedNetOutputNode", modid = "MineFactoryReloaded", striprefs = true)
public class BlockBundledEmitter extends BlockContainerBase{//} implements IRedNetOutputNode{

    public BlockBundledEmitter(Material m){
        super(m);
    }

    //EIO (crazypants please add a proper API)
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing face, float clickX, float clickY, float clickZ) {
        world.scheduleUpdate(pos, state.getBlock(), 1);
        return false;
    }
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        TileEntity thisTE = worldIn.getTileEntity(pos);
        if(!(thisTE instanceof TEBundledEmitter)){
            return;
        }
        TEBundledEmitter teb = (TEBundledEmitter)thisTE;
        for(EnumFacing dir : EnumFacing.VALUES){
            TileEntity adjacent = worldIn.getTileEntity(pos.offset(dir));
            if(Loader.isModLoaded(Reference.ID_ENDER_IO) && adjacent instanceof IConduitBundle){
                IRedstoneConduit rc = ((IConduitBundle) adjacent).getConduit(IRedstoneConduit.class);
                if(rc!=null){
                    //LogHelper.info("Found redstone conduit, trying to add signals to inputs");
                    int[] eioSignals = teb.getEIOSignals();
                    //LogHelper.info("  telling EIO  " + Arrays.toString( eioSignals ));
                    //Set<Signal> itsInputs = rc.getNetworkInputs(dir.getOpposite());                 //cannot just add new signals to signal set
                    AbstractConduitNetwork net = rc.getNetwork();
                    if(net instanceof RedstoneConduitNetwork){
                        Multimap<SignalSource, Signal> netInputs = ((RedstoneConduitNetwork) net).getSignals(); //can add new signals to network signals
                        netInputs.removeAll( new SignalSource(pos, dir.getOpposite()));                         //but also need to remove old ones
                        for(int i = 0; i < eioSignals.length; i++){
                            if(eioSignals[i] > 0){
                                Signal sig = new Signal(pos, dir.getOpposite(), eioSignals[i], DyeColor.fromIndex(15-i));//origin pos, dir from conduit to origin
                                //LogHelper.info("    Adding " + sig);
                                netInputs.put( new SignalSource(sig), sig );
                            }
                        }
                        ((RedstoneConduitNetwork) net).updateInputsFromConduit(rc);
                    }

                    //rc.onInputsChanged(dir.getOpposite(), switchPanel.getEIOSignals() );    //does not seem to work, EIO receives nothing (also doesn't work with just dir)
                    //LogHelper.info("  EIO received " + rc.getNetworkInputs(dir.getOpposite()) );
                }
            }
        }
    }
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        //seems like the easiest way to make sure EIO wires get updated on break
        TileEntity thisTE = world.getTileEntity(pos);
        if(!(thisTE instanceof TEBundledEmitter)){
            return false;
        }
        TEBundledEmitter teb = (TEBundledEmitter)thisTE;
        for(EnumFacing dir : EnumFacing.VALUES){
            TileEntity adjacent = world.getTileEntity(pos.offset(dir));
            if(Loader.isModLoaded(Reference.ID_ENDER_IO) && adjacent instanceof IConduitBundle){
                IRedstoneConduit rc = ((IConduitBundle) adjacent).getConduit(IRedstoneConduit.class);
                if(rc!=null){
                    AbstractConduitNetwork net = rc.getNetwork();
                    if(net instanceof RedstoneConduitNetwork){
                        Multimap<SignalSource, Signal> netInputs = ((RedstoneConduitNetwork) net).getSignals(); //can add new signals to network signals
                        netInputs.removeAll( new SignalSource(pos, dir.getOpposite()));                         //but also need to remove old ones
                        ((RedstoneConduitNetwork) net).updateInputsFromConduit(rc);
                    }
                }
            }
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    /*
    //Minefactory Reloaded and EnderIO
    @Override
    public int[] getOutputValues(World world, int x, int y, int z, ForgeDirection side) {
        TileEntity te = world.getTileEntity(x,y,z);
        if( te instanceof TEBundledEmitter){
            TEBundledEmitter teb = (TEBundledEmitter)te;
            byte[] bytes = teb.getBundledCableStrength(0, 0);
            int[] result = new int[bytes.length];
            for( int i = 0; i < bytes.length; i++){
                result[i] = (int)bytes[i] & 255;
                //if(!world.isRemote){ LogHelper.info("byte " + i + " is " + bytes[i] + " int " + ((int)bytes[i] &255)); }
            }
            return result;
        }
        return new int[16];
    }

    @Override
    public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet) {
        TileEntity te = world.getTileEntity(x, y, z);
        if( te instanceof TEBundledEmitter ) {
            TEBundledEmitter teb = (TEBundledEmitter) te;
            byte[] bytes = teb.getBundledCableStrength(0, 0);
            //if(!world.isRemote){ LogHelper.info("byte " + subnet + " is " + bytes[subnet] + " int " + ((int)bytes[subnet] & 255)); }
            return (int)bytes[subnet] & 255;
        }
        return 0;
    }

    @Override
    @Optional.Method(modid="MineFactoryReloaded")
    public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side) {
        return RedNetConnectionType.CableAll;
    }
    */
}
