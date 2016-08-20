package com.empiricist.redcontrols.tileentity;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;;

public class TileEntityDeserializer extends TEBundledEmitter implements ITickable {

    private byte[] output;          //bundled output cache
    private byte counter;           //which bit is next
    private boolean lastTickInput;  //for detecting changes

    public TileEntityDeserializer(){
        super();
        output = new byte[] {0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
        counter = 16;
        lastTickInput = false;
    }

    @Override
    public byte[] getBundledCableStrength(int blockFace, int toDirection) {
        return output;
    }

    @Override
    public void update() {
        if(worldObj.isRemote){ return; }

        long time = worldObj.getTotalWorldTime();//unchanged by time command, unlike getWorldTime()
        if( time % 4 == 1 ) {  //do things every 4 ticks so its slower

            byte rs = (byte) worldObj.isBlockIndirectlyGettingPowered(pos);
            boolean thisTickInput = rs != 0;
            //LogHelper.info("  deserializer ticking index " + counter + ", receives redstone " + rs );

            if( counter < output.length ){ //if this is a data bit rather than start/stop etc
                if (output[counter] != rs) {
                    //LogHelper.info("  deserial change, notify neighbors");
                    output[counter] = rs;
                    worldObj.notifyNeighborsOfStateChange(pos, ModBlocks.deserializer);//if input changed (and thus output must change) changed, notify neighbors
                }
                counter++;
            }else if( lastTickInput &&  !thisTickInput ){    //detect falling edge at beginning of start bit
                counter = 0;//next is data bit 0
            }
            lastTickInput = thisTickInput;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        tag.setByteArray("output", output);
        tag.setByte("counter", counter);
        tag.setBoolean("lastTickInput", lastTickInput);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        output = compound.getByteArray("output");
        if(output.length != 16){
            output = new byte[] {0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};//this only caused problems in worlds preceding this addition but I'll leave the fix in
        }
        counter = compound.getByte("counter");
        lastTickInput = compound.getBoolean("lastTickInput");
    }
}
