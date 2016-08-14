package com.empiricist.redcontrols.tileentity;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.utility.LogHelper;
import com.empiricist.redcontrols.utility.WorldHelper;

public class TileEntityDAC extends TEBundledReceiver{

    @Override
    public void onBundledInputChanged() {
        //LogHelper.info("DAC thing happened");
        super.onBundledInputChanged();
        worldObj.scheduleUpdate(pos, ModBlocks.dac, 1);//schedule a tick, we will notify neighbors then
        //worldObj.notifyNeighborsOfStateChange(pos, ModBlocks.dac);
    }

    public int getRedstoneStrength(){
        int sum = 0;
        for(int i = 0; i < 4; i++){
            if(signals[i] != 0){
                sum += (1<<i);
            }
        }
        return sum;
    }

}
