package com.empiricist.redcontrols.utility;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldHelper {
    public static void scheduleTickNeighbors(World world, BlockPos center){
        BlockPos pos;
        for(EnumFacing dir : EnumFacing.VALUES){
            pos = center.offset(dir);
            world.scheduleBlockUpdate(pos, world.getBlockState(pos).getBlock(), 1, 0);//never mind this is just a normal tick, like for buttons resetting
        }
    }

//    public static int maxRedstonePower(World world, BlockPos pos){
//        int power = 0;
//        for(EnumFacing facing : EnumFacing.values()){
//            power = Math.max(power, world.getRedstonePower(pos.offset(facing), facing.getOpposite()) );//never mind just use world.isIndirectlyGettingPowered()
//        }
//        return power;
//    }
}
