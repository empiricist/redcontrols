package com.empiricist.redcontrols.item;


import com.empiricist.redcontrols.utility.ChatHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import java.util.List;

public class ItemBearingCompass extends ItemBase {

    public ItemBearingCompass() {
        super();
        name = "bearingCompass";
        this.setUnlocalizedName(name);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
        if( !world.isRemote ){
            //alt 167 (º), or \u0167

            ChatHelper.sendText(player, "----- Direction -----");
            double yaw = player.rotationYaw;
            double pitch = player.rotationPitch;
            //why am I doing printf stuff when the internet is down :/
            ChatHelper.sendText(player, String.format("%-8s %5.1f\u00B0  %-12s %5.1f\u00B0", "Yaw:", yaw, "Bearing:", (180+yaw)%360));
            ChatHelper.sendText(player, String.format("%-8s %5.1f\u00B0  %-12s %5.1f\u00B0", "Pitch:", pitch, "Inclination:", (-pitch)));

            String dir = "", axis = "";
            if(pitch < -50 ){
                dir = "Up";
                axis = "+Y";
            }else if (pitch > 50){
                dir = "Down";
                axis = "-Y";
            }else{
                int direction = MathHelper.floor_double((double) ((yaw * 4F) / 360F) + 0.5D) & 3;
                dir = EnumFacing.HORIZONTALS[direction].getName();
                switch( direction ) {
                    case 0:
                        axis = "+Z";
                        break;
                    case 1:
                        axis = "-X";
                        break;
                    case 2:
                        axis = "-Z";
                        break;
                    case 3:
                        axis = "+X";
                        break;
                }
            }
            ChatHelper.sendText(player, String.format("%-8s %-5s  %-10s %-5s", "Facing:", dir, "Axis:", axis));
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        list.add("Finds Directions");
        super.addInformation(stack, player, list, bool);
    }
}
