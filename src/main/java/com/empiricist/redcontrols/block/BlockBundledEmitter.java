package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraftforge.fml.common.Optional;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

//@Optional.Interface(iface = "powercrystals.minefactoryreloaded.api.rednet.IRedNetOutputNode", modid = "MineFactoryReloaded", striprefs = true)
public class BlockBundledEmitter extends BlockContainerBase{//} implements IRedNetOutputNode{

    public BlockBundledEmitter(Material m){
        super(m);
    }

    /*
    //Minefactory Reloaded and EnderIO
    @Override
    public int[] getOutputValues(World world, int x, int y, int z, ForgeDirection side) {
        TileEntity te = world.getTileEntity(x,y,z);
        if( te instanceof TEBundledEmitter){
            TEBundledEmitter teb = (TEBundledEmitter)te;
            byte[] bytes = teb.getBundledCableStrength(0, 0);
            int[] result = new int[bytes.length];
            for( int i = 0; i < bytes.length; i++){
                result[i] = (int)bytes[i] & 255;
                //if(!world.isRemote){ LogHelper.info("byte " + i + " is " + bytes[i] + " int " + ((int)bytes[i] &255)); }
            }
            return result;
        }
        return new int[16];
    }

    @Override
    public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet) {
        TileEntity te = world.getTileEntity(x, y, z);
        if( te instanceof TEBundledEmitter ) {
            TEBundledEmitter teb = (TEBundledEmitter) te;
            byte[] bytes = teb.getBundledCableStrength(0, 0);
            //if(!world.isRemote){ LogHelper.info("byte " + subnet + " is " + bytes[subnet] + " int " + ((int)bytes[subnet] & 255)); }
            return (int)bytes[subnet] & 255;
        }
        return 0;
    }

    @Override
    @Optional.Method(modid="MineFactoryReloaded")
    public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side) {
        return RedNetConnectionType.CableAll;
    }
    */
}
