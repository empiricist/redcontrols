package com.empiricist.redcontrols.tileentity;

import com.empiricist.redcontrols.utility.WorldHelper;
import net.minecraft.util.EnumFacing;

public class TileEntityADC extends TEBundledEmitter{

    private int lastPower = 0;

    @Override
    public byte[] getBundledCableStrength(int blockFace, int toDirection) {
        int strength = worldObj.isBlockIndirectlyGettingPowered(pos);//WorldHelper.maxRedstonePower(worldObj, pos);//check all directions, pick max

        byte[] result = {0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
        for(int i = 0; i < 4; i++){
            result[i] = (byte)(((strength & (1<<i)) != 0) ? -1 : 0);
        }
        return result;
    }

    public int getLastPower(){
        return lastPower;
    }
    public void setLastPower( int power ){
        lastPower = power;
    }
}
