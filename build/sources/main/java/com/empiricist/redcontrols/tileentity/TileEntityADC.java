package com.empiricist.redcontrols.tileentity;

import net.minecraft.util.EnumFacing;

public class TileEntityADC extends TEBundledEmitter{

    @Override
    public byte[] getBundledCableStrength(int blockFace, int toDirection) {
        int strength = 0;
        for(EnumFacing facing : EnumFacing.values()){
            strength = Math.max(strength, worldObj.getRedstonePower(pos.offset(facing), facing.getOpposite()) );
        }

        byte[] result = {0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
        for(int i = 0; i < 4; i++){
            result[i] = (byte)(((strength & (1<<i)) != 0) ? -1 : 0);
        }
        return result;
    }
}
