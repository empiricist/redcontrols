package com.empiricist.redcontrols.client.gui;

import com.empiricist.redcontrols.RedControls;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public GuiHandler(){
        //need to register so we can use
        NetworkRegistry.INSTANCE.registerGuiHandler(RedControls.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//        switch(ID){
//            case 0:
//                TileEntity te = world.getTileEntity(x, y, z);
//                if(te!=null && te instanceof TileEntityWarpCore){
//                    return new ContainerWarpCore(player.inventory, (TileEntityWarpCore)te);
//                }
//                break;
//        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//        switch(ID){
//            case 0://machine
//                TileEntity te = world.getTileEntity(x, y, z);
//                if(te!=null && te instanceof TileEntityWarpCore){
//                    return new GuiWarpCore(player.inventory, (TileEntityWarpCore)te);
//                }
//                break;
//        }
        return null;
    }
}
