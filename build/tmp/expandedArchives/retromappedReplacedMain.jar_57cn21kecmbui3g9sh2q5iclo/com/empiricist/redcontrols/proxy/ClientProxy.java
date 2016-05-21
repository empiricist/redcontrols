package com.empiricist.redcontrols.proxy;

import com.empiricist.redcontrols.client.TESRButtons;
import com.empiricist.redcontrols.client.TESRText;
import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.init.ModItems;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TileEntityButtons;
import com.empiricist.redcontrols.tileentity.TileEntityIndicators;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
import com.empiricist.redcontrols.tileentity.TileEntityText;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;


public class ClientProxy extends CommonProxy{
    @Override
    public void registerKeyBindings(){
        //currently keys are disabled in main class preinit method
//        ClientRegistry.registerKeyBinding(Keybindings.charge);
//        ClientRegistry.registerKeyBinding(Keybindings.release);
    }

    @Override
    public void registerTESR(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySwitches.class,      new TESRButtons());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityButtons.class,       new TESRButtons());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIndicators.class,    new TESRButtons());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityText.class,          new TESRText());
    }

    @Override
    public void registerModels(){
        //LogHelper.info("Registering item models");
        ItemModelMesher mesher = Minecraft.func_71410_x().func_175599_af().func_175037_a();
        //Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(YourItem, metadata, new ModelResourceLocation("modid:items_registered_name", "inventory"));

        //blocks
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.switches),     0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.switches.getName(),      "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.buttons),      0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.buttons.getName(),       "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.indicators),   0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.indicators.getName(),    "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.toggleSwitch), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.toggleSwitch.getName(),  "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.analogPower),  0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.analogPower.getName(),   "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.charPanel),    0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.charPanel.getName(),     "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.breakerSwitch),0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.breakerSwitch.getName(), "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.coverButton),  0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.coverButton.getName(),   "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.uncoverButton),0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.uncoverButton.getName(), "inventory"));//just in case someone gets one
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.bigLever),     0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.bigLever.getName(),      "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.woodPanel),    0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.woodPanel.getName(),     "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.stonePanel),   0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.stonePanel.getName(),    "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.playerPanel),  0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.playerPanel.getName(),   "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.dac),          0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.dac.getName(),           "inventory"));
        mesher.func_178086_a(Item.func_150898_a(ModBlocks.adc),          0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.adc.getName(),           "inventory"));
        for(int i = 0; i < ModBlocks.ironPanels.length; i++){
            mesher.func_178086_a(Item.func_150898_a(ModBlocks.ironPanels[i]),0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.ironPanels[i].getName(), "inventory"));
            mesher.func_178086_a(Item.func_150898_a(ModBlocks.goldPanels[i]),0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.goldPanels[i].getName(), "inventory"));
        }

        //items
        mesher.func_178086_a(ModItems.itemBearingCompass,   0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModItems.itemBearingCompass.getName(), "inventory"));
        mesher.func_178086_a(ModItems.debugger,             0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModItems.debugger.getName(),           "inventory"));
        mesher.func_178086_a(ModItems.powerWand,            0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModItems.powerWand.getName(),          "inventory"));
    }
}
