package com.empiricist.redcontrols.init;

import com.empiricist.redcontrols.block.*;
import com.empiricist.redcontrols.handler.ConfigurationHandler;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TileEntityButtons;
import com.empiricist.redcontrols.tileentity.TileEntityIndicators;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
import com.empiricist.redcontrols.tileentity.TileEntityText;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

//this annotation tells forge to preserve this as reference w/o modification (unnecessary, good practice)
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
    public static final BlockSwitches           switches =      new BlockSwitches();
    public static final BlockBundledEmitter     buttons =       new BlockButtons();
    public static final BlockBundledReceiver    indicators =    new BlockIndicators();
    public static final BlockSwitch             toggleSwitch =  new BlockSwitch();
    public static final BlockPower              power =         new BlockPower();
    public static final BlockBase               analogPower =   new BlockAnalogPower();
    public static final BlockBundledReceiver    charPanel =     new BlockText();

    public static void init(){
        if(ConfigurationHandler.enableSwitches){
            GameRegistry.registerBlock( switches, switches.getName());
            GameRegistry.registerTileEntity(TileEntitySwitches.class, switches.getName());
        }

        if(ConfigurationHandler.enableButtons) {
            GameRegistry.registerBlock(buttons, buttons.getName());
            GameRegistry.registerTileEntity(TileEntityButtons.class, buttons.getName());
        }

        if(ConfigurationHandler.enableIndicators) {
            GameRegistry.registerBlock(indicators, indicators.getName());
            GameRegistry.registerTileEntity(TileEntityIndicators.class, indicators.getName());
        }

        if(ConfigurationHandler.enableSwitch) {
            GameRegistry.registerBlock(toggleSwitch, toggleSwitch.getName());
        }

        if(ConfigurationHandler.enablePowerWand) { //produces power block
            GameRegistry.registerBlock(power, power.getName());
        }

        if(ConfigurationHandler.enableAnalog) {
            GameRegistry.registerBlock(analogPower, analogPower.getName());
        }

        if(ConfigurationHandler.enableChar) {
            GameRegistry.registerBlock(charPanel, charPanel.getName());
            GameRegistry.registerTileEntity(TileEntityText.class, charPanel.getName());
        }

    }
}
