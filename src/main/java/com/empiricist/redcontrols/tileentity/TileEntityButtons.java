package com.empiricist.redcontrols.tileentity;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

public class TileEntityButtons extends TEBundledEmitter implements ITEBundledLights, ITickable{
    public int[] signals;

    public TileEntityButtons() {
        super();
        signals = new int[] { 0, 0, 0, 0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    @Override
    public void update() {
        if(!worldObj.isRemote) {
            for (int i = 0; i < signals.length; i++) {
                if (signals[i] == 1) {//button runs out
                    worldObj.scheduleUpdate(pos, worldObj.getBlockState(pos).getBlock(), 1);//this will update EIO too (in updateTick()) when you run out
//                    for( ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
//                        worldObj.scheduleBlockUpdate(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ, ModBlocks.buttons, 10);
//                        LogHelper.info("Schedules update for x:" + (xCoord + dir.offsetX) + " y:" + (yCoord + dir.offsetY) + " z:" + (zCoord + dir.offsetZ));
//                        //worldObj.notifyBlockOfNeighborChange(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, this.blockType);
//                    }
                    //worldObj.notifyBlockChange(xCoord, yCoord, zCoord, this.blockType);
                    //if(!worldObj.isRemote){ LogHelper.info("notify block update"); }
                    //worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, ModBlocks.buttons, 2);
                    //worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, ModBlocks.buttons);
                    IBlockState state = worldObj.getBlockState(pos);
                    worldObj.markAndNotifyBlock( pos, worldObj.getChunkFromBlockCoords( pos ), state, state, 3);
                    worldObj.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.3F, 0.7F);//play pop sound
                    //ModBlocks.buttons.onBlockActivated(worldObj, xCoord, yCoord, zCoord, null, worldObj.getBlockMetadata(xCoord,yCoord,zCoord), 0, 0, 0);
                }
                if (signals[i] >= 1) {//if time left for signal, decrement counter
                    //if(!worldObj.isRemote){ LogHelper.info("Signal is " + signals[i]); }
                    signals[i]--;
                }
            }
        }
    }

    public byte[] getBundledCableStrength(int blockFace, int toDirection) {
        byte[] result = new byte[16];
        for(int i = 0; i < signals.length; i++){
            result[i] = (byte)(signals[i] > 1 ? -1 : 0); //255 or 0 for unsigned
        }
        //if(!worldObj.isRemote){LogHelper.info("emitting signal to thing at face " + blockFace + " in the block in direction " + toDirection);}
        return result;
    }

    @Override
    public boolean[] getSignalsArray(){
        boolean[] result = new boolean[16];
        for(int i = 0; i < signals.length; i++){
            result[i] = signals[i] > 0;
        }
        return result;
    }

    public void setSignal(int number, int duration){
        if (number < 0 || number >= signals.length){ return; }
        //if(!worldObj.isRemote){ LogHelper.info("Set signal to " + duration); }
        signals[number] = duration;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setIntArray("signals", signals);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        signals = compound.getIntArray("signals");
    }
}
