package com.empiricist.redcontrols.compat;


import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import dan200.computercraft.api.redstone.IBundledRedstoneProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@Optional.Interface(iface = "dan200.computercraft.api.redstone.IBundledRedstoneProvider", modid = "ComputerCraft", striprefs = true)
public class CCRedstoneProvider implements IBundledRedstoneProvider {

    @Override
    public int getBundledRedstoneOutput(World world, BlockPos pos, EnumFacing side) {
        TileEntity te = world.func_175625_s( pos );
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
