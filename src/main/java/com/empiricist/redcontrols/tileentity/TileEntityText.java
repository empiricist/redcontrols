package com.empiricist.redcontrols.tileentity;

import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityText extends TEBundledReceiver{
    private byte mode;

    public TileEntityText(){
        super();
        mode = 0;
    }

    public String getText(){
        if(mode == 0){
            return (char)getSignalShort() + ""; //unicode 16b
        }else{
            return (char)(getSignalShort() & 0xFFF) + ""; //unicode 12b
        }
    }

    public int getColor(){
        if(mode == 0){
            return 0xFFFFFF; //white
        }else{
            return ItemDye.field_150922_c[(~getSignalShort()>>12)&15];//color of last 4 bits, according to dyes
        }
    }

    public short getSignalShort(){
        int result = 0;
        for(int i = signals.length-1; i > 0; i--){
            result += (signals[i]==0?0:1);
            result <<= 1;
        }
        result += (signals[0]==0?0:1);
        return (short)result;
    }

    public byte changeMode(){
        mode++;
        mode %= 2;
        return mode;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByteArray("signals", signals);
        compound.setByte("mode", mode);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        signals = compound.getByteArray("signals");
        mode = compound.getByte("mode");
    }
}
