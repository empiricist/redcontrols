package com.empiricist.redcontrols.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityIndicators extends TEBundledReceiver implements ITEBundledLights{

    public TileEntityIndicators() {
        super();
    }

    @Override
    public boolean[] getSignalsArray() {
        boolean[] result = new boolean[16];
        for(int i = 0; i < signals.length; i++){
            result[i] = signals[i] != 0;
        }
        return result;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByteArray("signals", signals);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        signals = compound.getByteArray("signals");
    }
}
