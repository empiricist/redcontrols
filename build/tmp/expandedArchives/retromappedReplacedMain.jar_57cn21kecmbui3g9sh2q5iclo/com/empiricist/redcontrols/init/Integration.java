package com.empiricist.redcontrols.init;


import com.empiricist.redcontrols.compat.CCRedstoneProvider;
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
        if( Loader.isModLoaded("bluepower")){ bluepower(); }
        if( Loader.isModLoaded("ComputerCraft")){ computercraft(); }
        Recipes.postInit();//mod item recipes
    }

    @Optional.Method(modid = "bluepower")
    public static void bluepower(){
        if( Loader.isModLoaded("bluepower")){
//            final BPRedstoneProvider provider = new BPRedstoneProvider();
//            if( provider instanceof IRedstoneApi){
//                BPApi.getInstance().getRedstoneApi().registerRedstoneProvider( (IRedstoneProvider) provider);
//                LogHelper.info("Registered bluepower redstone provider");
//            }
        }
    }

    @Optional.Method(modid = "ComputerCraft")
    public static void computercraft(){
        if( Loader.isModLoaded("ComputerCraft") ){
            CCRedstoneProvider ccProvider = new CCRedstoneProvider();
            if(ccProvider instanceof IBundledRedstoneProvider){
                ComputerCraftAPI.registerBundledRedstoneProvider(new CCRedstoneProvider());
            }
        }
    }
}
