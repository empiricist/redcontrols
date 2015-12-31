package com.empiricist.redcontrols.init;

import com.empiricist.redcontrols.block.*;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TileEntityButtons;
import com.empiricist.redcontrols.tileentity.TileEntityIndicators;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
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

    public static void init(){
        GameRegistry.registerBlock( switches, "switchPanel");
        GameRegistry.registerTileEntity(TileEntitySwitches.class, "switchPanel");

        GameRegistry.registerBlock( buttons, "buttonPanel");
        GameRegistry.registerTileEntity(TileEntityButtons.class, "buttonPanel");

        GameRegistry.registerBlock( indicators, "indicatorPanel");
        GameRegistry.registerTileEntity(TileEntityIndicators.class, "indicatorPanel");

        GameRegistry.registerBlock( toggleSwitch, "toggleSwitch");

        GameRegistry.registerBlock( power, "power");

        GameRegistry.registerBlock( analogPower, "analogPower");
    }
}
