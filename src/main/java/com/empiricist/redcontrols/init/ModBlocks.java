package com.empiricist.redcontrols.init;

import com.empiricist.redcontrols.block.*;
import com.empiricist.redcontrols.handler.ConfigurationHandler;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.*;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemColored;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
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
    public static final BlockSerializer             serializer =    new BlockSerializer();
    public static final BlockDeserializer           deserializer =  new BlockDeserializer();
    public static final BlockPressurePanel          woodPanel =     new BlockPressurePanel("woodPressurePanel", Material.WOOD, BlockPressurePanel.Sensitivity.EVERYTHING);
    public static final BlockPressurePanel          stonePanel =    new BlockPressurePanel("stonePressurePanel", Material.ROCK, BlockPressurePanel.Sensitivity.MOBS);
    public static final BlockPressurePanel          playerPanel =   new BlockPressurePanel("playerPressurePanel", Material.ROCK, BlockPressurePanel.Sensitivity.PLAYERS);

    public static BlockPressurePanelWeighted[]  ironPanels = initializeWeighted("ironPressurePanel", Material.IRON, 150);//ugh these need 16 states for their power already
    public static BlockPressurePanelWeighted[]  goldPanels = initializeWeighted("goldPressurePanel", Material.IRON, 15);

    public static void init(){
        if(ConfigurationHandler.enableSwitches){
            switches.setRegistryName(Reference.MOD_ID, switches.getName());
            GameRegistry.register( switches );
            GameRegistry.registerTileEntity(TileEntitySwitches.class, switches.getName());
            GameRegistry.register( new ItemBlock(switches), new ResourceLocation(Reference.MOD_ID + ":" + switches.getName()));
        }

        if(ConfigurationHandler.enableButtons) {
            buttons.setRegistryName(Reference.MOD_ID, buttons.getName());
            GameRegistry.register( buttons );
            GameRegistry.registerTileEntity(TileEntityButtons.class, buttons.getName());
            GameRegistry.register( new ItemBlock(buttons), new ResourceLocation(Reference.MOD_ID + ":" + buttons.getName()));
        }

        if(ConfigurationHandler.enableIndicators) {
            indicators.setRegistryName(Reference.MOD_ID, indicators.getName());
            GameRegistry.register( indicators );
            GameRegistry.registerTileEntity(TileEntityIndicators.class, indicators.getName());
            GameRegistry.register( new ItemBlock(indicators), new ResourceLocation(Reference.MOD_ID + ":" + indicators.getName()));
        }

        if(ConfigurationHandler.enableSwitch) {
            toggleSwitch.setRegistryName(Reference.MOD_ID, toggleSwitch.getName());
            GameRegistry.register( toggleSwitch );
            GameRegistry.register( new ItemBlock(toggleSwitch), new ResourceLocation(Reference.MOD_ID + ":" + toggleSwitch.getName()));
        }

        if(ConfigurationHandler.enablePowerWand) { //produces power block
            power.setRegistryName(Reference.MOD_ID, power.getName());
            GameRegistry.register( power );
            //no item form
        }

        if(ConfigurationHandler.enableAnalog) {
            analogPower.setRegistryName(Reference.MOD_ID, analogPower.getName());
            GameRegistry.register( analogPower );
            GameRegistry.register( new ItemColored(analogPower, false), new ResourceLocation(Reference.MOD_ID + ":" + analogPower.getName()));//so it has color in inventory too
        }

        if(ConfigurationHandler.enableChar) {
            charPanel.setRegistryName(Reference.MOD_ID, charPanel.getName());
            GameRegistry.register( charPanel );
            GameRegistry.registerTileEntity(TileEntityText.class, charPanel.getName());
            GameRegistry.register( new ItemBlock(charPanel), new ResourceLocation(Reference.MOD_ID + ":" + charPanel.getName()));
        }

        if(ConfigurationHandler.enableBreaker) {
            breakerSwitch.setRegistryName(Reference.MOD_ID, breakerSwitch.getName());
            GameRegistry.register (breakerSwitch );
            GameRegistry.register( new ItemBlock(breakerSwitch), new ResourceLocation(Reference.MOD_ID + ":" + breakerSwitch.getName()));
        }

        if(ConfigurationHandler.enableCoverButton) {
            coverButton.setRegistryName(Reference.MOD_ID, coverButton.getName());
            GameRegistry.register( coverButton );
            uncoverButton.setRegistryName(Reference.MOD_ID, uncoverButton.getName());
            GameRegistry.register( uncoverButton );
            GameRegistry.register( new ItemBlock(coverButton), new ResourceLocation(Reference.MOD_ID + ":" + coverButton.getName()));
        }

        if(ConfigurationHandler.enableBigLever) {
            bigLever.setRegistryName(Reference.MOD_ID, bigLever.getName());
            GameRegistry.register( bigLever );
            GameRegistry.register( new ItemBlock(bigLever), new ResourceLocation(Reference.MOD_ID + ":" + bigLever.getName()));
        }
        if(ConfigurationHandler.enableWoodPanel) {
            woodPanel.setRegistryName(Reference.MOD_ID, woodPanel.getName());
            GameRegistry.register( woodPanel );
            GameRegistry.register( new ItemBlock(woodPanel), new ResourceLocation(Reference.MOD_ID + ":" + woodPanel.getName()));
        }
        if(ConfigurationHandler.enableStonePanel) {
            stonePanel.setRegistryName(Reference.MOD_ID, stonePanel.getName());
            GameRegistry.register( stonePanel );
            GameRegistry.register( new ItemBlock(stonePanel), new ResourceLocation(Reference.MOD_ID + ":" + stonePanel.getName()));
        }
        if(ConfigurationHandler.enablePlayerPanel) {
            playerPanel.setRegistryName(Reference.MOD_ID, playerPanel.getName());
            GameRegistry.register( playerPanel );
            GameRegistry.register( new ItemBlock(playerPanel), new ResourceLocation(Reference.MOD_ID + ":" + playerPanel.getName()));
        }
        if(ConfigurationHandler.enableIronPanel) {
            for(int i = 0; i < ironPanels.length; i++) {
                ironPanels[i].setRegistryName(Reference.MOD_ID, ironPanels[i].getName());
                GameRegistry.register(ironPanels[i]);
            }
            //LogHelper.info( "Trying to get block iron panel: " + Block.REGISTRY.getObject(new ResourceLocation(Reference.MOD_ID + ":" + ironPanels[i].getName())).getUnlocalizedName() );
            //LogHelper.info( "Trying to get block iron panel: " + Item.REGISTRY.getObject(new ResourceLocation(Reference.MOD_ID + ":" + ironPanels[0].getName())).getUnlocalizedName() );
            GameRegistry.register( new ItemBlock(ironPanels[0]), new ResourceLocation(Reference.MOD_ID + ":" + ironPanels[0].getName()));
        }
        if(ConfigurationHandler.enableGoldPanel) {
            for(int i = 0; i < goldPanels.length; i++) {
                goldPanels[i].setRegistryName(Reference.MOD_ID, goldPanels[i].getName());
                GameRegistry.register( goldPanels[i] );
            }
            GameRegistry.register( new ItemBlock(goldPanels[0]), new ResourceLocation(Reference.MOD_ID + ":" + goldPanels[0].getName()));
        }
        if(ConfigurationHandler.enableDACADC) {
            adc.setRegistryName(Reference.MOD_ID, adc.getName());
            GameRegistry.register( adc );
            GameRegistry.registerTileEntity(TileEntityADC.class, adc.getName());
            GameRegistry.register( new ItemBlock(adc), new ResourceLocation(Reference.MOD_ID + ":" + adc.getName()));

            dac.setRegistryName(Reference.MOD_ID, dac.getName());
            GameRegistry.register( dac );
            GameRegistry.registerTileEntity(TileEntityDAC.class, dac.getName());
            GameRegistry.register( new ItemBlock(dac), new ResourceLocation(Reference.MOD_ID + ":" + dac.getName()));
        }
        if(ConfigurationHandler.enableRXTX) {
            serializer.setRegistryName(Reference.MOD_ID, serializer.getName());
            GameRegistry.register( serializer );
            GameRegistry.registerTileEntity(TileEntitySerializer.class, serializer.getName());
            GameRegistry.register( new ItemBlock(serializer), new ResourceLocation(Reference.MOD_ID + ":" + serializer.getName()));

            deserializer.setRegistryName(Reference.MOD_ID, deserializer.getName());
            GameRegistry.register( deserializer );
            GameRegistry.registerTileEntity(TileEntityDeserializer.class, deserializer.getName());
            GameRegistry.register( new ItemBlock(deserializer), new ResourceLocation(Reference.MOD_ID + ":" + deserializer.getName()));
        }
    }

    public static BlockPressurePanelWeighted[] initializeWeighted(String name, Material material, int weight){
        BlockPressurePanelWeighted[] result = new BlockPressurePanelWeighted[ EnumFacing.values().length ];
        for( int i = 0; i < EnumFacing.values().length; i++ ){
            EnumFacing facing = EnumFacing.values()[i];
            result[i] = new BlockPressurePanelWeighted( name+"_"+facing.getName(), material, weight, facing );
        }
        return result;
    }
}
