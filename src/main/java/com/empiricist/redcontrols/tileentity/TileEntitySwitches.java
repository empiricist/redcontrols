package com.empiricist.redcontrols.tileentity;


import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import mods.immibis.redlogic.api.wiring.IBundledEmitter;
import mods.immibis.redlogic.api.wiring.IBundledWire;
import mods.immibis.redlogic.api.wiring.IConnectable;
import mods.immibis.redlogic.api.wiring.IWire;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.*;
import java.util.List;

public class TileEntitySwitches extends TEBundledEmitter implements ITEBundledLights{

    private short signals;

    public TileEntitySwitches() {
        super();

        signals = 0;//new int[] { 0, 0, 0, 0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public short getSignals(){ return signals; }

    @Override
    public boolean[] getSignalsArray(){
        boolean[] result = new boolean[16];
        short bits = signals;
        for (int i = 0; i < result.length; i++){
            result[i] = ((bits & 1) != 0);
            bits = (short) (bits >> 1);
        }
        return result;
    }

    public boolean getSignal(int number){
        if (number < 0 || number > 15){ return false;}
        short value = (short)Math.pow(2, number);
        return (signals & value) != 0;
    }

    public void setSignals(short s){ signals = s; }

    public void setSignal(int number, boolean s){
        if (number < 0 || number > 15){ return; }
        short value = (short)Math.pow(2, number);
        signals = (short)(s ? (signals | value) : ( signals & ~value));
    }

    @Override
    public byte[] getBundledCableStrength(int blockFace, int toDirection) {
        boolean[] sig = getSignalsArray();
        byte[] result = new byte[16];
        for(int i = 0; i < sig.length; i++){
            result[i] = (byte)(sig[i] ? -1 : 0); //255 or 0 for unsigned
        }
        return result;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setShort("signals", signals);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        signals = compound.getShort("signals");
    }

}

