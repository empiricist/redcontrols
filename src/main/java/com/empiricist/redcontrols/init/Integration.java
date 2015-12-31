package com.empiricist.redcontrols.init;



import com.bluepowermod.api.BPApi;
import com.bluepowermod.api.wire.redstone.IRedstoneApi;
import com.bluepowermod.api.wire.redstone.IRedstoneProvider;
import com.empiricist.redcontrols.compat.BPRedstoneProvider;
import com.empiricist.redcontrols.utility.LogHelper;
import com.empiricist.redcontrols.init.Recipes;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import net.minecraft.block.Block;

public class Integration {

    public static void postInit(){
        LogHelper.info("Doing RedControls integration");
        if( Loader.isModLoaded("bluepower")){ bluepower(); }
        //computercraft();
        Recipes.postInit();//mod item recipes
    }

    @Optional.Method(modid = "bluepower")
    public static void bluepower(){
        if( Loader.isModLoaded("bluepower")){
            final BPRedstoneProvider provider = new BPRedstoneProvider();
            if( provider instanceof IRedstoneApi){
                BPApi.getInstance().getRedstoneApi().registerRedstoneProvider( (IRedstoneProvider) provider);
                LogHelper.info("Registered bluepower redstone provider");
            }
        }
    }


    public static void computercraft(){
//        if( Loader.isModLoaded("ComputerCraft") ){
//            Block core = ModBlocks.warpcore;
//            if( core instanceof IPeripheralProvider ){
//                LogHelper.info("Registering Warp Core as peripheral provider");
//                ComputerCraftAPI.registerPeripheralProvider( (IPeripheralProvider) core);
//            }else{
//                LogHelper.info("Warp Core is not a peripheral provider");
//            }
//        }else{
//            LogHelper.info("ComputerCraft is not loaded, skipping ComputerCraft integration");
//        }
    }
}
