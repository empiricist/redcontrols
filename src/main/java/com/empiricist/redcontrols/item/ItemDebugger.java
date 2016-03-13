package com.empiricist.redcontrols.item;


import com.bluepowermod.api.BPApi;
import com.bluepowermod.api.wire.redstone.IBundledDevice;
import com.empiricist.redcontrols.utility.ChatHelper;
import com.empiricist.redcontrols.utility.LogHelper;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;

public class ItemDebugger extends ItemBase{

    public ItemDebugger(){
        super();
        this.setUnlocalizedName("debugger");
        //this.setCreativeTab(CreativeTabTestProject.TEST_PROJECT_TAB);
    }

    //give data of block right clicked on
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
        if( !world.isRemote ){
            ChatHelper.sendText(player, "-----Block-----");
            Block block = world.getBlock(x, y, z);
            ChatHelper.sendText(player, "X:" + x + ", Y:" + y + ", Z:" + z + "; Name: " + block.getLocalizedName() + ", ID: " + block.getUnlocalizedName() + ", Meta: " + world.getBlockMetadata(x, y, z));
            ChatHelper.sendText(player, "Hardness: " + block.getBlockHardness(world,x,y,z) + ", Resistance: " + block.getExplosionResistance(player)*5.0f + ", Mining Level: " + block.getHarvestLevel(world.getBlockMetadata(x,y,z)));
            TileEntity te = world.getTileEntity(x,y,z);
            if( te != null ){
                NBTTagCompound tag = new NBTTagCompound();
                te.writeToNBT(tag);
                ChatHelper.sendText(player, "NBTData:" + tag.toString());
            }
            if(Loader.isModLoaded("bluepower") ) {
                for (ForgeDirection face : ForgeDirection.VALID_DIRECTIONS) {
                    for (ForgeDirection sideDir : ForgeDirection.VALID_DIRECTIONS) {
                        IBundledDevice dev = BPApi.getInstance().getRedstoneApi().getBundledDevice(world, x, y, z, face, sideDir);
                        if (dev != null) {
                            ChatHelper.sendText(player, "BP Bundled Device found at face " + face.name() + " and side " + sideDir.name());
                            for (ForgeDirection outSide : ForgeDirection.VALID_DIRECTIONS) {
                                ChatHelper.sendText(player, "Output " + outSide.name() + "  " + Arrays.toString(dev.getBundledOutput(outSide)));
                            }
                        }
                    }
                }
                for (ForgeDirection sideDir : ForgeDirection.VALID_DIRECTIONS) {
                    IBundledDevice dev = BPApi.getInstance().getRedstoneApi().getBundledDevice(world, x, y, z, ForgeDirection.UNKNOWN, sideDir);
                    if (dev != null) {
                        ChatHelper.sendText(player, "BP Bundled Device found at face UNKNOWN and side " + sideDir.name());
                        ChatHelper.sendText(player, "Output " + Arrays.toString(dev.getBundledOutput(sideDir.getOpposite())));
                    }
                }
            }
        }
        return true;
    }

    //If right clicked on no block, give data of item in slot 1
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if( !world.isRemote ){
            ItemStack slot1 = player.inventory.getStackInSlot(0);
            if (slot1 != null){
                ChatHelper.sendText(player, "-----Item-----");
                ChatHelper.sendText(player, "Item in slot 1 has Display Name: " + slot1.getDisplayName() + ", Unlocalized Name: " + slot1.getItem().getUnlocalizedName());
                if( slot1.hasTagCompound() ){
                    ChatHelper.sendText(player, "NBT Data is :"  + slot1.getTagCompound().toString());
                }
            }
        }
        return stack;
    }

    //give data of entity left clicked on
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity){
        if( !entity.worldObj.isRemote ){
            NBTTagCompound tag = new NBTTagCompound();
            entity.writeToNBT(tag);
            ChatHelper.sendText(player, "-----Entity-----");
            ChatHelper.sendText(player, "Name: " + entity.getClass()  + ", ID: " + entity.getEntityId() + ", Data: " + entity.toString() );
            if(player.isSneaking()){
                ChatHelper.sendText(player, "NBT: " + tag ); //because this can be very long
            }
        }
        return true;//do no damage
    }
}
