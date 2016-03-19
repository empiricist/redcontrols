package com.empiricist.redcontrols.init;

import com.empiricist.redcontrols.block.*;
import com.empiricist.redcontrols.handler.ConfigurationHandler;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TileEntityButtons;
import com.empiricist.redcontrols.tileentity.TileEntityIndicators;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
import com.empiricist.redcontrols.tileentity.TileEntityText;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

//this annotation tells forge to preserve this as reference w/o modification (unnecessary, good practice)
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
    public static final BlockBundledEmitter switches = new BlockSwitches();
    public static final BlockBundledEmitter buttons = new BlockButtons();
    public static final BlockBundledReceiver indicators = new BlockIndicators();
    public static final Block toggleSwitch = new BlockSwitch();
    public static final Block power = new BlockPower();
    public static final BlockBase analogPower = new BlockAnalogPower();
    public static final BlockBundledReceiver text = new BlockText();

    public static void init(){
        if(ConfigurationHandler.enableSwitches){
            GameRegistry.registerBlock( switches, "switchPanel");
            GameRegistry.registerTileEntity(TileEntitySwitches.class, "switchPanel");
        }

        if(ConfigurationHandler.enableButtons) {
            GameRegistry.registerBlock(buttons, "buttonPanel");
            GameRegistry.registerTileEntity(TileEntityButtons.class, "buttonPanel");
        }

        if(ConfigurationHandler.enableIndicators) {
            GameRegistry.registerBlock(indicators, "indicatorPanel");
            GameRegistry.registerTileEntity(TileEntityIndicators.class, "indicatorPanel");
        }

        if(ConfigurationHandler.enableSwitch) {
            GameRegistry.registerBlock(toggleSwitch, "toggleSwitch");
        }

        if(ConfigurationHandler.enablePowerWand) {
            GameRegistry.registerBlock(power, "power");
        }

        if(ConfigurationHandler.enableAnalog) {
            GameRegistry.registerBlock(analogPower, "analogPower");
        }

        if(ConfigurationHandler.enableChar) {
            GameRegistry.registerBlock(text, "textPanel");
            GameRegistry.registerTileEntity(TileEntityText.class, "textPanel");
        }

    }
}
