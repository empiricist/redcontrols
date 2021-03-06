package com.empiricist.redcontrols.handler;

import com.empiricist.redcontrols.reference.Reference;
import com.google.common.collect.Lists;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

//uses the new (~1.7.10) in-game config GUI
public class ConfigurationHandler {

    public static Configuration configuration;//we will want this in other methods too
    //public static boolean testValue = false;
    public static boolean enableSwitches = false;
    public static boolean enableButtons = false;
    public static boolean enableIndicators = false;
    public static boolean enableSwitch = false;
    public static boolean enableAnalog = false;
    public static boolean enableChar = false;
    public static boolean enableBreaker = false;
    public static boolean enableCoverButton = false;
    public static boolean enableBigLever = false;
    public static boolean enableWoodPanel = false;
    public static boolean enableStonePanel = false;
    public static boolean enablePlayerPanel = false;
    public static boolean enableIronPanel = false;
    public static boolean enableGoldPanel = false;
    public static boolean enableDACADC = false;
    public static boolean enableRXTX = false;


    public static boolean enablePowerWand = false;
    public static boolean enableDebugger = false;
    public static boolean enableCompass = false;

    public static void init(File configFile) {//initialize configuration
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }
/*
    public static void laterMethod(){//read in values from file
        try {//because file stuff
            //load config and values from config
            configuration.load();
            boolean testValue = configuration.get(Configuration.CATEGORY_GENERAL, "testValue", true, "Comment").getBoolean();

        }catch(Exception e){//should be more specific
            //log problem
            System.out.println("Exception reading from config file");

        }finally{
            if( configuration.hasChanged() ) {
                configuration.save();
            }
        }
    }
*/
    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
        if( event.getModID().equalsIgnoreCase(Reference.MOD_ID) ){//if it is this mod
            loadConfiguration();//resync for new value
        }
    }

    private static void loadConfiguration(){
        //testValue = configuration.getBoolean("testValue", Configuration.CATEGORY_GENERAL, false, "Comment");
        enableSwitches =    configuration.getBoolean("enableSwitches",      Configuration.CATEGORY_GENERAL, true, "Enable switch panel");
        enableButtons =     configuration.getBoolean("enableButtons",       Configuration.CATEGORY_GENERAL, true, "Enable button panel");
        enableIndicators =  configuration.getBoolean("enableIndicators",    Configuration.CATEGORY_GENERAL, true, "Enable indicator panel");
        enableSwitch =      configuration.getBoolean("enableSwitch",        Configuration.CATEGORY_GENERAL, true, "Enable toggle switch");
        enableAnalog =      configuration.getBoolean("enableAnalog",        Configuration.CATEGORY_GENERAL, true, "Enable analog emitter");
        enableChar =        configuration.getBoolean("enableChar",          Configuration.CATEGORY_GENERAL, true, "Enable character panel");
        enableBreaker =     configuration.getBoolean("enableBreaker",       Configuration.CATEGORY_GENERAL, true, "Enable breaker switch");
        enableCoverButton = configuration.getBoolean("enableCoverButton",   Configuration.CATEGORY_GENERAL, true, "Enable glass-covered button");
        enableBigLever =    configuration.getBoolean("enableBigLever",      Configuration.CATEGORY_GENERAL, true, "Enable bigger lever");
        enableWoodPanel =   configuration.getBoolean("enableWoodPanel",     Configuration.CATEGORY_GENERAL, true, "Enable wooden pressure panel");
        enableStonePanel =  configuration.getBoolean("enableStonePanel",    Configuration.CATEGORY_GENERAL, true, "Enable stone pressure panel");
        enablePlayerPanel = configuration.getBoolean("enablePlayerPanel",   Configuration.CATEGORY_GENERAL, true, "Enable player-only pressure panel");
        enableIronPanel =   configuration.getBoolean("enableIronPanel",     Configuration.CATEGORY_GENERAL, true, "Enable iron pressure panel");
        enableGoldPanel =   configuration.getBoolean("enableGoldPanel",     Configuration.CATEGORY_GENERAL, true, "Enable gold pressure panel");
        enableDACADC =      configuration.getBoolean("enableDACADC",        Configuration.CATEGORY_GENERAL, true, "Enable digital analog converter and analog digital converter");
        enableRXTX =        configuration.getBoolean("enableRXTX",          Configuration.CATEGORY_GENERAL, true, "Enable experimental serializer and deserializer");

        enablePowerWand =   configuration.getBoolean("enablePowerWand",     Configuration.CATEGORY_GENERAL, true, "Enable power wand");
        enableDebugger =    configuration.getBoolean("enableDebugger",      Configuration.CATEGORY_GENERAL, true, "Enable debugger");
        enableCompass =     configuration.getBoolean("enableCompass",       Configuration.CATEGORY_GENERAL, true, "Enable bearing compass");

        if( configuration.hasChanged() ) {
            configuration.save();
        }
    }
}
