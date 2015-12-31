package com.empiricist.redcontrols.item;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.utility.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class ItemPowerWand extends ItemBase{

    public ItemPowerWand(){
        super();
        this.setUnlocalizedName("powerWand");
        setFull3D();//hold like tool
        //this.setCreativeTab(CreativeTabTestProject.TEST_PROJECT_TAB);
    }

    //give data of block right clicked on
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        int destX = x + dir.offsetX;
        int destY = y + dir.offsetY;
        int destZ = z + dir.offsetZ;
        //if(!world.isRemote){LogHelper.info("Clicked  " + x + ", " + y + ", " + z);}
        //if(!world.isRemote){LogHelper.info("Checking " + destX + ", " + destY + ", " + destZ);}
        if(world.getBlock(destX, destY, destZ).isReplaceable(world, destX, destY, destZ)){
            world.setBlock(destX, destY, destZ, ModBlocks.power);
            //if(!world.isRemote){LogHelper.info("Set Block");}
            world.scheduleBlockUpdate(destX, destY, destZ, ModBlocks.power, 40);
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
