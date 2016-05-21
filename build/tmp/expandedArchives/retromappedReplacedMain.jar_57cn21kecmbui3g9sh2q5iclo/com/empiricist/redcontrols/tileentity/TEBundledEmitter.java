package com.empiricist.redcontrols.tileentity;

import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import mods.immibis.redlogic.api.wiring.IBundledEmitter;
import mods.immibis.redlogic.api.wiring.IBundledWire;
import mods.immibis.redlogic.api.wiring.IConnectable;
import mods.immibis.redlogic.api.wiring.IWire;
import mrtjp.projectred.api.IBundledTile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


@Optional.InterfaceList({
        @Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IBundledEmitter", modid = "RedLogic", striprefs = true),
        @Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IConnectable", modid = "RedLogic", striprefs = true),
        @Optional.Interface(iface = "mrtjp.projectred.api.IBundledTile", modid = "ProjRed|Core", striprefs = true),
        @Optional.Interface(iface = "pl.asie.charset.api.wires.IBundledEmitter", modid = "CharsetWires", striprefs = true)
})
//If bluepower ever updates start by uncommenting cache, methods, IBundledDevice, readding @Optional.Interface(iface = "com.bluepowermod.api.wire.redstone.IBundledDevice", modid = "bluepower", striprefs = true)
public class TEBundledEmitter extends TileEntity implements IBundledEmitter, IConnectable, IBundledTile, pl.asie.charset.api.wires.IBundledEmitter{//}, IBundledDevice {
    public byte[] BPinput;
    public Object BPCache;

    @CapabilityInject(pl.asie.charset.api.wires.IBundledEmitter.class)
    public static Capability<pl.asie.charset.api.wires.IBundledEmitter> BUNDLED_EMITTER;//the annotation tells forge to make this the capability for IBundledEmitter, if there is one

    public TEBundledEmitter(){
        super();
        BPinput = new byte[16];
        //BPCache = Loader.isModLoaded("bluepower") ? initCache() : null;
    }

    //implement this, and extend BlockBundledEmitter on block, and compatibility is handled
    @Override
    public byte[] getBundledCableStrength(int blockFace, int toDirection) {
        return new byte[16];
    }

    public boolean[] getSignalsArray(){
        return new boolean[16];
    }


    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeToNBT(syncData);
        return new S35PacketUpdateTileEntity(this.pos, 1, syncData);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }



    //Redlogic
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



    //Project Red
    @Override
    @Optional.Method(modid="ProjRed|Core")
    public boolean canConnectBundled(int side) {
        return true;
    }

    @Override
    @Optional.Method(modid="ProjRed|Core")
    public byte[] getBundledSignal(int dir) {
        return getBundledCableStrength(0,dir);
    }



    //CharSet
    @Override
    public byte[] getBundledSignal() {
        byte[] signals = getBundledCableStrength(0, 0);
        byte[] result = new byte[signals.length];
        for( int i = 0; i < signals.length; i++ ){
            result[i] = (byte)(signals[i] == -1 ? 15 : 0);//charset wires, like vanilla redstone, carry a strength in [0,15], while redcontrols natively uses values 0 or 255 (-1 signed)
        }
        return result;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability != null && capability == BUNDLED_EMITTER) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability != null && capability == BUNDLED_EMITTER) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }



    public String debugOutput(byte[] bytes){
        String result = "[";
        for(int i = 0; i < bytes.length-1; i++){
            result += bytes[i] + ",";
        }
        result += bytes[bytes.length-1] + "]";
        return result;
    }
/*
    //BluePower
    @Override
    @Optional.Method(modid="bluepower")
    public boolean canConnect(ForgeDirection side, IBundledDevice dev, ConnectionType type) {
        //return true;//(type == ConnectionType.STRAIGHT);
        return type == ConnectionType.STRAIGHT && side != ForgeDirection.UNKNOWN;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public IConnectionCache<? extends IBundledDevice> getBundledConnectionCache() {
        //if(!worldObj.isRemote){ LogHelper.info("get cache"); }
        //IConnectionCache c = BPApi.getInstance().getRedstoneApi().createBundledConnectionCache(this);
        //if(!worldObj.isRemote){ LogHelper.info("got cache"); }
        return (IConnectionCache<? extends IBundledDevice>) BPCache;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public byte[] getBundledOutput(ForgeDirection side) {
        //LogHelper.info("Emitter returns " + debugOutput(getBundledCableStrength(0, side.ordinal())) + " for its output");
        return getBundledCableStrength(0, side.ordinal());
    }

    @Override
    @Optional.Method(modid="bluepower")
    public void setBundledPower(ForgeDirection side, byte[] power) {
        BPinput = power.clone();
    }

    @Override
    @Optional.Method(modid="bluepower")
    public byte[] getBundledPower(ForgeDirection side) {
        return BPinput.clone();
    }

    @Override
    @Optional.Method(modid="bluepower")
    public void onBundledUpdate() {}

    @Override
    @Optional.Method(modid="bluepower")
    public MinecraftColor getBundledColor(ForgeDirection side) {
        return MinecraftColor.ANY;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public boolean isNormalFace(ForgeDirection side) {
        return true;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public World getWorld() {
        return worldObj;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public int getX() { return pos.getX(); }

    @Override
    @Optional.Method(modid="bluepower")
    public int getY() {
        return pos.getY();
    }

    @Override
    @Optional.Method(modid="bluepower")
    public int getZ() {
        return pos.getZ();
    }

    @Optional.Method(modid="bluepower")
    public IConnectionCache<? extends  IBundledDevice> initCache() {
        return BPApi.getInstance().getRedstoneApi().createBundledConnectionCache(this);
    }
    */



    //MFR methods are in block class
}