package com.empiricist.redcontrols.tileentity;


import com.bluepowermod.api.BPApi;
import com.bluepowermod.api.connect.ConnectionType;
import com.bluepowermod.api.connect.IConnectionCache;
import com.bluepowermod.api.misc.MinecraftColor;
import com.bluepowermod.api.wire.redstone.IBundledDevice;
import com.empiricist.redcontrols.utility.LogHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import crazypants.enderio.api.redstone.IRedstoneConnectable;
import crazypants.enderio.conduit.IConduit;
import crazypants.enderio.conduit.IConduitBundle;
import crazypants.enderio.conduit.redstone.IInsulatedRedstoneConduit;
import crazypants.enderio.conduit.redstone.IRedstoneConduit;
import crazypants.enderio.conduit.redstone.InsulatedRedstoneConduit;
import crazypants.enderio.conduit.redstone.RedstoneConduit;
import dan200.computercraft.api.ComputerCraftAPI;
import mods.immibis.redlogic.api.wiring.*;
import mods.immibis.redlogic.api.wiring.IBundledEmitter;
import mods.immibis.redlogic.api.wiring.IConnectable;
import mrtjp.projectred.api.*;
import net.minecraft.block.BlockSand;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.minefactoryreloaded.api.rednet.IRedNetOutputNode;

import java.util.Collection;

@Optional.InterfaceList({
        @Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IBundledUpdatable", modid = "RedLogic", striprefs = true),
        @Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IConnectable", modid = "RedLogic", striprefs = true),
        @Optional.Interface(iface = "mrtjp.projectred.api.IBundledTile", modid = "ProjRed|Core", striprefs = true),
        @Optional.Interface(iface = "com.bluepowermod.api.wire.redstone.IBundledDevice", modid = "bluepower", striprefs = true),
        @Optional.Interface(iface = "crazypants.enderio.api.redstone.IRedstoneConnectable", modid = "EnderIO", striprefs = true)
})
public class TEBundledReceiver extends TileEntity implements IBundledUpdatable, IConnectable, IBundledTile, IBundledDevice, IRedstoneConnectable {

    public byte[] signals;
    public byte[] blockSignals;
    public Object BPCache;
    public byte[] BPinputs;

    public TEBundledReceiver(){
        signals = new byte[]{0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
        blockSignals = signals;
        BPCache = Loader.isModLoaded("bluepower") ? initCache() : null;
        BPinputs = new byte[]{0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
    }

    //this one
    public void onBundledInputChanged() {
        byte[] in = new byte[16];

        //if(!worldObj.isRemote){LogHelper.info("Update for TE at x " + xCoord + " y " + yCoord + " z " + zCoord);}

        for( ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS ){
            //LogHelper.info("---- Checking for TE at " + dir.name() + " ----");
            TileEntity te = worldObj.getTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ);
            if(te == null ){continue;}
            //if(!worldObj.isRemote){LogHelper.info(" tile " + te);}
            //LogHelper.info("Checking signals against redlogic signals");
            if(te instanceof TEBundledEmitter){
                for( int i = -1; i < 6; i++ ){
                    in = maxSignal(in, ((TEBundledEmitter) te).getBundledCableStrength(i, dir.getOpposite().ordinal()));
                }
            }
            if(Loader.isModLoaded("RedLogic") && te instanceof IBundledEmitter){
                //if(!worldObj.isRemote){LogHelper.info("found redlogic " + dir);}
                for( int i = -1; i < 6; i++ ){
                    //if(!worldObj.isRemote){LogHelper.info(" trying direction " + i);}
                    in = maxSignal(in, ((IBundledEmitter) te).getBundledCableStrength(i, dir.getOpposite().ordinal()));
            }
                //in = maxSignal(in, ((IBundledEmitter) te).getBundledCableStrength(dir.ordinal(), dir.getOpposite().ordinal()));
                //if(!worldObj.isRemote){LogHelper.info(" " + debugOutput(in));}
            }
            if(Loader.isModLoaded("ProjRed|Core") ){//&& te instanceof mrtjp.projectred.api.IBundledEmitter){
                //if(!worldObj.isRemote){LogHelper.info("maybe found project red? " + dir);}
                //LogHelper.info("Checking signals against project red signals");
                in = maxSignal(in, ProjectRedAPI.transmissionAPI.getBundledInput(worldObj, xCoord, yCoord, zCoord, dir.ordinal()));
                //if(!worldObj.isRemote){LogHelper.info(" " + debugOutput(in));}
            }

            if(Loader.isModLoaded("bluepower") ){//&& te instanceof IBundledDevice){
                IConnectionCache<? extends IBundledDevice> connections = ((IConnectionCache<? extends IBundledDevice>) BPCache);

                connections.recalculateConnections();
                int connected = 0;
                for (ForgeDirection s : ForgeDirection.VALID_DIRECTIONS) {
                    connected += (connections.getConnectionOnSide(s) != null) ? 1 : 0;
                }

                if (connected == 0) {
                    //LogHelper.info("There are no blue power connections");
                    BPinputs = new byte[]{0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
                }
                //LogHelper.info("Checking signals against blue power signals");
                in = maxSignal(in, BPinputs);

/*
                for( int i = -1; i < 7; i++ ) {
                //the face the device is on within block xyz (eg, a cable on the ground is on side BOTTOM), and some kind of side
                    IBundledDevice dev = BPApi.getInstance().getRedstoneApi().getBundledDevice(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, ForgeDirection.getOrientation(i), dir.getOpposite());
                    if (dev != null) {
//                        if (!worldObj.isRemote) {
//                            LogHelper.info("found bluepower to " + dir + " on? " + i + " " + dev.getClass().getSimpleName());
//                        }
                        LogHelper.info("Actual blue power signals found were " + debugOutput(dev.getBundledOutput(dir.getOpposite())));
                        in = maxSignal(in, dev.getBundledOutput(dir.getOpposite()));
                        //in = maxSignal(in, dev.getBundledOutput(dir.getOpposite()));
//                        if (!worldObj.isRemote) {
//                            LogHelper.info(" " + debugOutput(in));
//                        }
                    }
                }
*/

            }

            //CC
            if(Loader.isModLoaded("ComputerCraft")){
                int ccSignal = ComputerCraftAPI.getBundledRedstoneOutput(worldObj, xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ, dir.getOpposite().ordinal());
                if(ccSignal != -1){ //there is a signal
                    //convert to unsigned byte array
                    byte[] ccSignals = new byte[16];
                    for (int i = 0; i < ccSignals.length; i++){
                        ccSignals[i] = (byte)((ccSignal & 1) != 0 ? -128 : 0);
                        ccSignal = (short) (ccSignal >> 1);
                    }
                    in = maxSignal(in, ccSignals);
                }
            }
            //MFR
            in = maxSignal(in, blockSignals);
            //EIO (does this require MFR to be loaded?)
//            if(Loader.isModLoaded("MineFactoryReloaded") && te instanceof IRedNetOutputNode){
//                if(!worldObj.isRemote){LogHelper.info("found MFR? " + dir);}
//                in = maxSignal(in, intsToBytes(((IRedNetOutputNode) te).getOutputValues(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir.getOpposite())));
//                if(!worldObj.isRemote){LogHelper.info(" " + debugOutput(in));}
//            }
//            if(!worldObj.isRemote){LogHelper.info(" tile " + te + " is ICB? " + (te instanceof IConduitBundle));}
//            if(!worldObj.isRemote && (te instanceof IConduitBundle)){
//                IConduitBundle icb= (IConduitBundle)te;
////                LogHelper.info(" tile " + te.getClass().getSimpleName() + " has IRC?    " + icb.containsConnection(IRedstoneConduit.class, dir.getOpposite()));
////                LogHelper.info(" tile " + te.getClass().getSimpleName() + " has IInsRC? " + icb.containsConnection(IInsulatedRedstoneConduit.class, dir.getOpposite()));
////                LogHelper.info(" tile " + te.getClass().getSimpleName() + " has RC?     " + icb.containsConnection(RedstoneConduit.class, dir.getOpposite()));
////                LogHelper.info(" tile " + te.getClass().getSimpleName() + " has InsRC?  " + icb.containsConnection(InsulatedRedstoneConduit.class, dir.getOpposite()));
////                LogHelper.info(" tile " + te.getClass().getSimpleName() + " has IRC?    " + icb.containsConnection(IRedstoneConduit.class, dir));
////                LogHelper.info(" tile " + te.getClass().getSimpleName() + " has IInsRC? " + icb.containsConnection(IInsulatedRedstoneConduit.class, dir));
////                LogHelper.info(" tile " + te.getClass().getSimpleName() + " has RC?     " + icb.containsConnection(RedstoneConduit.class, dir));
////                LogHelper.info(" tile " + te.getClass().getSimpleName() + " has InsRC?  " + icb.containsConnection(InsulatedRedstoneConduit.class, dir));
//                Collection conduits = icb.getConduits();
//                for(Object o : conduits){
//                    LogHelper.info(" " + o + "\n");
//                }
//            }
            //if(!worldObj.isRemote){LogHelper.info(" tile " + te + " is ICB? " + (te instanceof IConduitBundle));}
            if(Loader.isModLoaded("EnderIO") && te instanceof IConduitBundle && ((IConduitBundle)te).hasType(IRedstoneConduit.class)){
                //if(!worldObj.isRemote){LogHelper.info(" tile " + te + " is ICB? " + (te instanceof IConduitBundle));}
                IRedstoneConduit ter = ((IConduitBundle)te).getConduit(IRedstoneConduit.class);
                //if(!worldObj.isRemote){LogHelper.info("found EIO " + dir);}
                in = maxSignal(in, intsToBytes(ter.getOutputValues(worldObj,xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ, dir.getOpposite())));
                //if(!worldObj.isRemote){LogHelper.info(" " + debugOutput(in));}
            }
        }

        signals = in;
        //if(!worldObj.isRemote){LogHelper.info("final signals " + debugOutput(signals));}
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public byte[] maxSignal(byte[] a, byte[] b){ //returns element-wise maximum of a and b byte arrays, interpreted as unsigned, either of which may be null
        byte[] defVal = new byte[]{0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
        if(a==null){
            //if(!worldObj.isRemote){LogHelper.info(" a was null, replacing with zeros");}
            if(b==null){
                return defVal;
            }else{
                return b;
            }
        }//else{if(!worldObj.isRemote){LogHelper.info(" a was not null, it was " + debugOutput(a));}}
        if(b==null){
            //if(!worldObj.isRemote){LogHelper.info(" b was null, replacing with zeros");}
            return a;
        }//else{if(!worldObj.isRemote){LogHelper.info(" b was not null, it was " + debugOutput(b));}}

        int num = Math.min(a.length, b.length);
        byte[] result = new byte[num];
        for(int i = 0; i < num; i++){
            result[i] = (byte)(Math.max((a[i]>=0)?a[i]:a[i]+256, (b[i]>=0)?b[i]:b[i]+256));//what
        }
        return result;
    }

    public void setBlockSignals(byte[] newBlockSignals){ //so block can update data of tile entity for next check
        if(newBlockSignals.length == 16){
            blockSignals = newBlockSignals;
        }
    }

    private byte[] intsToBytes(int[] ints){
        byte[] bytes = new byte[ints.length];
        int val;
        for(int i = 0; i < ints.length; i++){
            val = ints[i];
            bytes[i] = (byte)(val > 127 ? val - 256 : val);
        }
        return bytes;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeToNBT(syncData);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
    }



    //redlogic
    @Override
    @Optional.Method(modid="RedLogic")
    public boolean connects(IWire wire, int blockFace, int fromDirection) {
        return wire instanceof IBundledWire;
    }

    @Override
    @Optional.Method(modid="RedLogic")
    public boolean connectsAroundCorner(IWire wire, int blockFace, int fromDirection) {
        return false;
    }



    //project red
    @Override
    @Optional.Method(modid="ProjRed|Core")
    public boolean canConnectBundled(int side) {
        return true;
    }

    @Override
    @Optional.Method(modid="ProjRed|Core")
    public byte[] getBundledSignal(int dir) {
        return null;//does not emit
    }


    //bluepower
    @Override
    @Optional.Method(modid="bluepower")
    public boolean canConnect(ForgeDirection side, IBundledDevice dev, ConnectionType type) {
        //LogHelper.info("Receiver returns " + (type == ConnectionType.STRAIGHT && side != ForgeDirection.UNKNOWN) + " for canConnect on " + side.name());
        return type == ConnectionType.STRAIGHT;// && side != ForgeDirection.UNKNOWN;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public IConnectionCache<? extends IBundledDevice> getBundledConnectionCache() {
        //LogHelper.info("Receiver returns its connection cache");
        return (IConnectionCache<? extends IBundledDevice>) BPCache;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public byte[] getBundledOutput(ForgeDirection side) {
        //LogHelper.info("Receiver returns all zeros for its output on " + side.name());
        return new byte[16];
    }

    @Override
    @Optional.Method(modid="bluepower")
    public void setBundledPower(ForgeDirection side, byte[] power) {
        BPinputs = power;
        //LogHelper.info("Receiver sets its input on side " + side.name() + " to " + debugOutput(power));
    }

    @Override
    @Optional.Method(modid="bluepower")
    public byte[] getBundledPower(ForgeDirection side) {
        //LogHelper.info("Receiver returns its input on side " + side.name() + " as " + debugOutput(BPinputs));
        return BPinputs;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public void onBundledUpdate() {
        //LogHelper.info("Receiver updates");
        onBundledInputChanged();
    }

    @Override
    @Optional.Method(modid="bluepower")
    public MinecraftColor getBundledColor(ForgeDirection side) {
        //LogHelper.info("Receiver returns 'any' for its color");
        return MinecraftColor.ANY;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public boolean isNormalFace(ForgeDirection side) {
        //LogHelper.info("Receiver returns true for normal face check");
        return true;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public World getWorld() {
        return worldObj;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public int getX() {
        return xCoord;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public int getY() {
        return yCoord;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public int getZ() {
        return zCoord;
    }

    @Optional.Method(modid="bluepower")
    public IConnectionCache<? extends  IBundledDevice> initCache() {
        return BPApi.getInstance().getRedstoneApi().createBundledConnectionCache(this);
    }


    public String debugOutput(byte[] bytes){
        String result = "[";
        for(int i = 0; i < bytes.length-1; i++){
            result += bytes[i] + ",";
        }
        result += bytes[bytes.length-1] + "]";
        return result;
    }

    @Override
        public boolean shouldRedstoneConduitConnect(World world, int x, int y, int z, ForgeDirection from) { //for EIO
        return true;
    }
}
