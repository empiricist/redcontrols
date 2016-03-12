package com.empiricist.redcontrols.compat;

import com.bluepowermod.api.wire.redstone.IBundledDevice;
import com.bluepowermod.api.wire.redstone.IRedstoneDevice;
import com.bluepowermod.api.wire.redstone.IRedstoneProvider;
import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.tileentity.TEBundledReceiver;
import com.empiricist.redcontrols.utility.LogHelper;
import cpw.mods.fml.common.Optional;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

//so bluepower knows our stuff can connect
@Optional.Interface(iface = "com.bluepowermod.api.wire.redstone.IRedstoneProvider", modid = "bluepower", striprefs = true)
public class BPRedstoneProvider implements IRedstoneProvider {

    public BPRedstoneProvider(){}

    @Override
    @Optional.Method(modid="bluepower")
    public IRedstoneDevice getRedstoneDeviceAt(World world, int x, int y, int z, ForgeDirection side, ForgeDirection face) {
        //LogHelper.info("getting redstone device (null)");
        return null;
    }

    @Override
    @Optional.Method(modid="bluepower")
    public IBundledDevice getBundledDeviceAt(World world, int x, int y, int z, ForgeDirection side, ForgeDirection face) {
        if (face != ForgeDirection.UNKNOWN || side == ForgeDirection.UNKNOWN) {
            //return null;
        }
        LogHelper.info("getting bundled device");

        TileEntity te = world.getTileEntity(x,y,z);
        if( te instanceof TEBundledEmitter){ //null just returns false
            TEBundledEmitter teb = (TEBundledEmitter)te;
            return (IBundledDevice)teb;
        }
        if( te instanceof TEBundledReceiver){
            TEBundledReceiver teb = (TEBundledReceiver)te;
            return (IBundledDevice)teb;
        }
        return null;
    }
}

