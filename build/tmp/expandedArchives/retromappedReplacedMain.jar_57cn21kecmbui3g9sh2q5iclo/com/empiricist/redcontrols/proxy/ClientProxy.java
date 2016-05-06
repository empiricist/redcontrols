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
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySwitches.class, new TESRButtons());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityButtons.class, new TESRButtons());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIndicators.class, new TESRButtons());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityText.class, new TESRText());
    }

    @Override
    public void registerModels(){
        //LogHelper.info("Registering item models");
        RenderItem renderItem = Minecraft.func_71410_x().func_175599_af();
        //Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(YourItem, metadata, new ModelResourceLocation("modid:items_registered_name", "inventory"));

        //blocks
        renderItem.func_175037_a().func_178086_a(Item.func_150898_a(ModBlocks.switches),     0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.switches.getName(),      "inventory"));
        renderItem.func_175037_a().func_178086_a(Item.func_150898_a(ModBlocks.buttons),      0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.buttons.getName(),       "inventory"));
        renderItem.func_175037_a().func_178086_a(Item.func_150898_a(ModBlocks.indicators),   0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.indicators.getName(),    "inventory"));
        renderItem.func_175037_a().func_178086_a(Item.func_150898_a(ModBlocks.toggleSwitch), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.toggleSwitch.getName(),  "inventory"));
        renderItem.func_175037_a().func_178086_a(Item.func_150898_a(ModBlocks.analogPower),  0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.analogPower.getName(),   "inventory"));
        renderItem.func_175037_a().func_178086_a(Item.func_150898_a(ModBlocks.charPanel),    0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModBlocks.charPanel.getName(),     "inventory"));

        //items
        renderItem.func_175037_a().func_178086_a(ModItems.itemBearingCompass,   0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModItems.itemBearingCompass.getName(), "inventory"));
        renderItem.func_175037_a().func_178086_a(ModItems.debugger,             0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModItems.debugger.getName(),           "inventory"));
        renderItem.func_175037_a().func_178086_a(ModItems.powerWand,            0, new ModelResourceLocation(Reference.MOD_ID + ":" + ModItems.powerWand.getName(),          "inventory"));
    }
}
