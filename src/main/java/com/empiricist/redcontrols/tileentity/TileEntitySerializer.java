package com.empiricist.redcontrols.tileentity;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntitySerializer extends TEBundledReceiver implements ITickable {

    private boolean lastOutput;

    public TileEntitySerializer(){
        super();
        lastOutput = false;
    }

    @Override
    public void onBundledInputChanged() {
        //LogHelper.info(" Serializer thing happened");
        super.onBundledInputChanged();
//        if( super.doesThisChangeBundledInput() ) {
//            LogHelper.info("  Serializer scheduling update for itself");
//            worldObj.scheduleUpdate(pos, ModBlocks.dac, 1);//schedule a tick, we will notify neighbors then
//            //worldObj.notifyNeighborsOfStateChange(pos, ModBlocks.dac);
//        }
    }

    //called by block
    public int getRedstoneStrength() {
        int time = (int) ((worldObj.getTotalWorldTime() >> 2) % 20);
        if( time == 1 || (time!=0 && time < 18 && signals[time-2] == 0) ){             //each 20 ticks: 1, 0, 16 data bits, 1, 1
            return 0;
        }else{
            return 15;
        }
    }

    @Override
    public void update() {
        if(worldObj.isRemote){ return; }

        long time = worldObj.getTotalWorldTime();//unchanged by time command, unlike getWorldTime()
        if( time % 4 == 0 ){
            boolean output = getRedstoneStrength() != 0;
            //LogHelper.info("serializer ticking index " + ((time>>2)%20) + ", last " + lastOutput + ", sends signal " + output);
            if ( output != lastOutput ){
                //LogHelper.info("  serial change, notify neighbors");
                worldObj.notifyNeighborsOfStateChange(pos, ModBlocks.serializer);//update if output changes
                //worldObj.markAndNotifyBlock(pos, worldObj.getChunkFromBlockCoords(pos), ModBlocks.serializer.getDefaultState(), ModBlocks.serializer.getDefaultState(), 3);
            }
            lastOutput = output;
        }

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        tag.setBoolean("lastOutput", lastOutput);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        lastOutput = compound.getBoolean("lastOutput");
    }
}
