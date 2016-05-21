package com.empiricist.redcontrols.init;

import com.empiricist.redcontrols.block.*;
import com.empiricist.redcontrols.handler.ConfigurationHandler;
import com.empiricist.redcontrols.item.ItemColorable;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.*;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemColored;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
    public static final BlockSwitches               switches =      new BlockSwitches();
    public static final BlockBundledEmitter         buttons =       new BlockButtons();
    public static final BlockBundledReceiver        indicators =    new BlockIndicators();
    public static final BlockSwitch                 toggleSwitch =  new BlockSwitch();
    public static final BlockPower                  power =         new BlockPower();
    public static final BlockBase                   analogPower =   new BlockAnalogPower();
    public static final BlockBundledReceiver        charPanel =     new BlockText();
    public static final BlockBreakerSwitch          breakerSwitch = new BlockBreakerSwitch();
    public static final BlockCoverButton            coverButton =   new BlockCoverButton(true);//too many states, will have to do these as two blocks
    public static final BlockCoverButton            uncoverButton = new BlockCoverButton(false);
    public static final BlockBigLever               bigLever =      new BlockBigLever();
    public static final BlockAnalogDigitalConverter adc =           new BlockAnalogDigitalConverter();//for technical reasons it is easier to make these separate blocks
    public static final BlockDigitalAnalogConverter dac =           new BlockDigitalAnalogConverter();
    public static final BlockPressurePanel          woodPanel =     new BlockPressurePanel("woodPressurePanel", Material.field_151575_d, BlockPressurePanel.Sensitivity.EVERYTHING);
    public static final BlockPressurePanel          stonePanel =    new BlockPressurePanel("stonePressurePanel", Material.field_151576_e, BlockPressurePanel.Sensitivity.MOBS);
    public static final BlockPressurePanel          playerPanel =   new BlockPressurePanel("playerPressurePanel", Material.field_151576_e, BlockPressurePanel.Sensitivity.PLAYERS);

    public static BlockPressurePanelWeighted[]  ironPanels = initializeWeighted("ironPressurePanel", Material.field_151573_f, 15);//ugh these need 16 states for their power already
    public static BlockPressurePanelWeighted[]  goldPanels = initializeWeighted("goldPressurePanel", Material.field_151573_f, 150);

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
            GameRegistry.registerBlock(analogPower, ItemColorable.class, analogPower.getName(), false);//so it has color in inventory too
        }

        if(ConfigurationHandler.enableChar) {
            GameRegistry.registerBlock(charPanel, charPanel.getName());
            GameRegistry.registerTileEntity(TileEntityText.class, charPanel.getName());
        }

        if(ConfigurationHandler.enableBreaker) {
            GameRegistry.registerBlock(breakerSwitch, breakerSwitch.getName());
        }

        if(ConfigurationHandler.enableCoverButton) {
            GameRegistry.registerBlock(coverButton, coverButton.getName());
            GameRegistry.registerBlock(uncoverButton, uncoverButton.getName());
        }

        if(ConfigurationHandler.enableBigLever) {
            GameRegistry.registerBlock(bigLever, bigLever.getName());
        }
        if(ConfigurationHandler.enableWoodPanel) {
            GameRegistry.registerBlock(woodPanel, woodPanel.getName());
        }
        if(ConfigurationHandler.enableStonePanel) {
            GameRegistry.registerBlock(stonePanel, stonePanel.getName());
        }
        if(ConfigurationHandler.enablePlayerPanel) {
            GameRegistry.registerBlock(playerPanel, playerPanel.getName());
        }
        for(int i = 0; i < ironPanels.length; i++){
            if(ConfigurationHandler.enableIronPanel) {
                GameRegistry.registerBlock(ironPanels[i], ironPanels[i].getName());
            }
            if(ConfigurationHandler.enableGoldPanel) {
                GameRegistry.registerBlock(goldPanels[i], goldPanels[i].getName());
            }
        }

        if(ConfigurationHandler.enableDACADC) {
            GameRegistry.registerBlock(adc, adc.getName());
            GameRegistry.registerTileEntity(TileEntityADC.class, adc.getName());
            GameRegistry.registerBlock(dac, dac.getName());
            GameRegistry.registerTileEntity(TileEntityDAC.class, dac.getName());
        }
    }

    public static BlockPressurePanelWeighted[] initializeWeighted(String name, Material material, int weight){
        BlockPressurePanelWeighted[] result = new BlockPressurePanelWeighted[ EnumFacing.values().length ];
        for( int i = 0; i < EnumFacing.values().length; i++ ){
            EnumFacing facing = EnumFacing.values()[i];
            result[i] = new BlockPressurePanelWeighted( name+"_"+facing.func_176610_l(), material, weight, facing );
        }
        return result;
    }
}
