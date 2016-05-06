package com.empiricist.redcontrols.tileentity;

import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityText extends TEBundledReceiver{
    private byte mode;
    private int color;

    public TileEntityText(){
        super();
        mode = 0;
        color = 0xFFFFFF;
    }

    public String getText(){
        if(mode == 0){  //unicode 16b
            return (char)getSignalShort() + ""; //I like shorts! They're comfy and easy to (char)!
        }else{  //unicode 12b
            return (char)(getSignalShort() & 0xFFF) + "";
        }
    }

    public int getColor(){
        if(mode == 0){
            return color; //white
        }else{
            return ItemDye.field_150922_c[(~getSignalShort()>>12)&15] | 0x0F0F0F;//color of last 4 bits, according to dyes, brightened a little
        }
    }

    public void setDyeColor(int dye){
        color = ItemDye.field_150922_c[(~dye)&15] | 0x0F0F0F;//color of last 4 bits, according to dyes, brightened a little
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
    public void func_145841_b(NBTTagCompound compound) {
        super.func_145841_b(compound);
        compound.func_74773_a("signals", signals);
        compound.func_74774_a("mode", mode);
        compound.func_74768_a("color", color);
    }

    @Override
    public void func_145839_a(NBTTagCompound compound) {
        super.func_145839_a(compound);
        signals = compound.func_74770_j("signals");
        mode = compound.func_74771_c("mode");
        color = compound.func_74762_e("color");
    }
}
