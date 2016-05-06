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
    public void func_145841_b(NBTTagCompound compound) {
        super.func_145841_b(compound);
        compound.func_74773_a("signals", signals);
    }

    @Override
    public void func_145839_a(NBTTagCompound compound) {
        super.func_145839_a(compound);
        signals = compound.func_74770_j("signals");
    }
}
