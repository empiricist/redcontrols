package com.empiricist.redcontrols.proxy;

import com.empiricist.redcontrols.client.TESRButtons;
import com.empiricist.redcontrols.tileentity.TileEntityButtons;
import com.empiricist.redcontrols.tileentity.TileEntityIndicators;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
import cpw.mods.fml.client.registry.ClientRegistry;


public class ClientProxy extends CommonProxy{
    @Override
    public void registerKeyBindings(){
        //currently keys are disabled in main class preinit method
//        ClientRegistry.registerKeyBinding(Keybindings.charge);
//        ClientRegistry.registerKeyBinding(Keybindings.release);
    }

    @Override
    public void registerTESR(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySwitches.class, new TESRButtons());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityButtons.class, new TESRButtons());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIndicators.class, new TESRButtons());
    }
}
