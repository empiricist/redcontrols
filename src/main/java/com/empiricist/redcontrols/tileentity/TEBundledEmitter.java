package com.empiricist.redcontrols.tileentity;

import com.bluepowermod.api.BPApi;
import com.bluepowermod.api.connect.ConnectionType;
import com.bluepowermod.api.connect.IConnectionCache;
import com.bluepowermod.api.misc.MinecraftColor;
import com.bluepowermod.api.wire.redstone.IBundledDevice;
import com.empiricist.redcontrols.utility.LogHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
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
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({
        @Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IBundledEmitter", modid = "RedLogic", striprefs = true),
        @Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IConnectable", modid = "RedLogic", striprefs = true),
        @Optional.Interface(iface = "mrtjp.projectred.api.IBundledTile", modid = "ProjRed|Core", striprefs = true),
        @Optional.Interface(iface = "com.bluepowermod.api.wire.redstone.IBundledDevice", modid = "bluepower", striprefs = true)
})
public class TEBundledEmitter extends TileEntity implements IBundledEmitter, IConnectable, IBundledTile, IBundledDevice {
    public byte[] BPinput;
    public Object BPCache;

    public TEBundledEmitter(){
        BPinput = new byte[16];
        BPCache = Loader.isModLoaded("bluepower") ? initCache() : null;
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
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
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

    //MFR methods are in block class
}
