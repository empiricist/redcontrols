package com.empiricist.redcontrols.item;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import java.util.List;

public class ItemPowerWand extends ItemBase{

    public ItemPowerWand(){
        super();
        name = "powerWand";
        this.func_77655_b(name);
        func_77664_n();//hold like tool
        //this.setCreativeTab(CreativeTabTestProject.TEST_PROJECT_TAB);
    }

    //give data of block right clicked on
    @Override
    public boolean func_180614_a(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        BlockPos powerPos = pos.func_177972_a(side);
        //if(!world.isRemote){LogHelper.info("Clicked  " + x + ", " + y + ", " + z);}
        //if(!world.isRemote){LogHelper.info("Checking " + destX + ", " + destY + ", " + destZ);}
        if(world.func_180495_p(powerPos).func_177230_c().func_176200_f(world, powerPos)){
            world.func_175656_a(powerPos, ModBlocks.power.func_176223_P());
            //if(!world.isRemote){LogHelper.info("Set Block");}
            world.func_180497_b(powerPos, ModBlocks.power, 40, 1);
            return true;
        }
        //if(!world.isRemote){LogHelper.info("Couldn't Set Block");}
        return false;
    }

    @Override
    public void func_77624_a(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        list.add("Temporarily powers things");
        super.func_77624_a(stack, player, list, bool);
    }

    //    @SideOnly(Side.CLIENT)
//    public boolean shouldRotateAroundWhenRendering(){
//        return true;
//    }
}
