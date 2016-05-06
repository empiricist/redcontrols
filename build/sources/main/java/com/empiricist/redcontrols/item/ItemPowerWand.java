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
        this.setUnlocalizedName(name);
        setFull3D();//hold like tool
        //this.setCreativeTab(CreativeTabTestProject.TEST_PROJECT_TAB);
    }

    //give data of block right clicked on
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        BlockPos powerPos = pos.offset(side);
        //if(!world.isRemote){LogHelper.info("Clicked  " + x + ", " + y + ", " + z);}
        //if(!world.isRemote){LogHelper.info("Checking " + destX + ", " + destY + ", " + destZ);}
        if(world.getBlockState(powerPos).getBlock().isReplaceable(world, powerPos)){
            world.setBlockState(powerPos, ModBlocks.power.getDefaultState());
            //if(!world.isRemote){LogHelper.info("Set Block");}
            world.scheduleBlockUpdate(powerPos, ModBlocks.power, 40, 1);
            return true;
        }
        //if(!world.isRemote){LogHelper.info("Couldn't Set Block");}
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        list.add("Temporarily powers things");
        super.addInformation(stack, player, list, bool);
    }

    //    @SideOnly(Side.CLIENT)
//    public boolean shouldRotateAroundWhenRendering(){
//        return true;
//    }
}
