package com.empiricist.redcontrols.init;


import com.empiricist.redcontrols.compat.CCRedstoneProvider;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.utility.LogHelper;
import com.empiricist.redcontrols.init.Recipes;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.redstone.IBundledRedstoneProvider;
import net.minecraft.block.Block;

public class Integration {

    public static void postInit(){
        LogHelper.info("Doing RedControls integration");
        if( Loader.isModLoaded(Reference.ID_BLUEPOWER)){ bluepower(); }
        if( Loader.isModLoaded(Reference.ID_COMPUTERCRAFT)){ computercraft(); }
        Recipes.postInit();//mod item recipes
    }

    @Optional.Method(modid = Reference.ID_BLUEPOWER)
    public static void bluepower(){
        if( Loader.isModLoaded(Reference.ID_BLUEPOWER)){
//            final BPRedstoneProvider provider = new BPRedstoneProvider();
//            if( provider instanceof IRedstoneApi){
//                BPApi.getInstance().getRedstoneApi().registerRedstoneProvider( (IRedstoneProvider) provider);
//                LogHelper.info("Registered bluepower redstone provider");
//            }
        }
    }

    @Optional.Method(modid = Reference.ID_COMPUTERCRAFT)
    public static void computercraft(){
        if( Loader.isModLoaded(Reference.ID_COMPUTERCRAFT) ){
            CCRedstoneProvider ccProvider = new CCRedstoneProvider();
            if(ccProvider instanceof IBundledRedstoneProvider){
                ComputerCraftAPI.registerBundledRedstoneProvider(new CCRedstoneProvider());
            }
        }
    }
}
