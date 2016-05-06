package com.empiricist.redcontrols.item;

import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.tileentity.TEBundledReceiver;
import com.empiricist.redcontrols.utility.ChatHelper;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import java.util.Arrays;

public class ItemDebugger extends ItemBase{

    public ItemDebugger(){
        super();
        name = "debugger";
        this.setUnlocalizedName(name);
        //this.setCreativeTab(CreativeTabTestProject.TEST_PROJECT_TAB);
    }

    //give data of block right clicked on
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        if( !world.isRemote ){
            ChatHelper.sendText(player, "-----Block-----");
            IBlockState blockstate = world.getBlockState(pos);
            Block block = blockstate.getBlock();
            ChatHelper.sendText(player, "X:" + pos.getX() + ", Y:" + pos.getY() + ", Z:" + pos.getZ() + "; Name: " + block.getRegistryName() + ", ID: " + block.getUnlocalizedName() + ", Meta: " + blockstate.toString());
            ChatHelper.sendText(player, "Hardness: " + block.getBlockHardness(world, pos) + ", Resistance: " + block.getExplosionResistance(player)*5.0f + ", Mining Level: " + block.getHarvestLevel(blockstate));
            TileEntity te = world.getTileEntity(pos);
            if( te != null ){
                NBTTagCompound tag = new NBTTagCompound();
                te.writeToNBT(tag);
                ChatHelper.sendText(player, "NBTData:" + tag.toString());

                if(Loader.isModLoaded("CharsetWires") ) {
                    ChatHelper.sendText(player, "-----Block-----");
                    for (EnumFacing face : EnumFacing.values()) {
                        ChatHelper.sendText(player, "Side: " + face.name() + ", receiver?: " + te.hasCapability(TEBundledReceiver.BUNDLED_RECEIVER, face));
                        ChatHelper.sendText(player, "    emitter? " + (te.hasCapability(TEBundledEmitter.BUNDLED_EMITTER, face)? debugOutput(te.getCapability(TEBundledEmitter.BUNDLED_EMITTER, face).getBundledSignal() ) : "false"));
                    }
                }
            }

            /*
            if(Loader.isModLoaded("bluepower") ) {
                for ( EnumFacing face : EnumFacing.values() ) {
                    for ( EnumFacing sideDir : EnumFacing.values() ) {
                        IBundledDevice dev = BPApi.getInstance().getRedstoneApi().getBundledDevice(world, getBundledDevice(world, pos.getX(), pos.getY(), pos.getZ(), face, sideDir));
                        if (dev != null) {
                            ChatHelper.sendText(player, "BP Bundled Device found at face " + face.name() + " and side " + sideDir.name());
                            for ( EnumFacing outSide : EnumFacing.values() ){
                                ChatHelper.sendText(player, "Output " + outSide.name() + "  " + Arrays.toString(dev.getBundledOutput(outSide)));
                            }
                        }
                    }
                }
                for ( EnumFacing sideDir : EnumFacing.values() ) {
                    IBundledDevice dev = BPApi.getInstance().getRedstoneApi().getBundledDevice(world,  pos.getX(), pos.getY(), pos.getZ(), ForgeDirection.UNKNOWN, sideDir);
                    if (dev != null) {
                        ChatHelper.sendText(player, "BP Bundled Device found at face UNKNOWN and side " + sideDir.name());
                        ChatHelper.sendText(player, "Output " + Arrays.toString(dev.getBundledOutput(sideDir.getOpposite())));
                    }
                }
            }
            */
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

    public String debugOutput(byte[] bytes){
        String result = "[";
        for(int i = 0; i < bytes.length-1; i++){
            result += bytes[i] + ",";
        }
        result += bytes[bytes.length-1] + "]";
        return result;
    }
}
