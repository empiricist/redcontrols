package com.empiricist.redcontrols.init;

import com.empiricist.redcontrols.handler.ConfigurationHandler;
import com.empiricist.redcontrols.item.*;
import com.empiricist.redcontrols.reference.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;

//this annotation tells forge to preserve this as reference w/o modification (unnecessary, good practice)
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
    public static final ItemBase debugger =             new ItemDebugger();
    public static final ItemBase itemBearingCompass =   new ItemBearingCompass();
    public static final ItemBase powerWand =            new ItemPowerWand();

    //register items from mod
    public static void init(){
        if(ConfigurationHandler.enableDebugger) {
            debugger.setRegistryName(Reference.MOD_ID, debugger.getName());
            GameRegistry.register( debugger );
        }

        if(ConfigurationHandler.enableCompass) {
            itemBearingCompass.setRegistryName(Reference.MOD_ID, itemBearingCompass.getName());
            GameRegistry.register( itemBearingCompass );
        }

        if(ConfigurationHandler.enablePowerWand) {
            powerWand.setRegistryName(Reference.MOD_ID, powerWand.getName());
            GameRegistry.register( powerWand );
        }
    }
}
