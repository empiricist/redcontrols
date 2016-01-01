package com.empiricist.redcontrols.compat;


import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.redstone.IBundledRedstoneProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@Optional.Interface(iface = "dan200.computercraft.api.redstone.IBundledRedstoneProvider", modid = "ComputerCraft", striprefs = true)
public class CCRedstoneProvider implements IBundledRedstoneProvider {
    @Override
    public int getBundledRedstoneOutput(World world, int x, int y, int z, int side) {
        TileEntity te = world.getTileEntity(x,y,z);
        if(te != null && te instanceof TEBundledEmitter){
            boolean[] signals = ((TEBundledEmitter)te).getSignalsArray();

            int result = 0, add = 1;
            for(int i = 0; i < signals.length; i++){
                if( signals[i]  ){
                    result += add;
                }
                add *= 2;
            }
            return result;
        }
        return -1;
    }
}
