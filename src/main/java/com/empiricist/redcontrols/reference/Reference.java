package com.empiricist.redcontrols.reference;

//a nice place to store mod information
public class Reference {
    public static final String MOD_ID = "redcontrols";
    public static final String MOD_NAME = "Red Controls";
    public static final String VERSION = "1.9.4-0.10";//here, mcmod.info, and build.gradle

    public static final String CLIENT_PROXY_CLASS = "com.empiricist.redcontrols.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.empiricist.redcontrols.proxy.ServerProxy";
    public static final String GUI_FACTORY_CLASS = "com.empiricist.redcontrols.client.gui.GuiFactory";

    //other mods' mod ids, for checking if they're loaded and for @Optional annotations
    public static final String ID_REDLOGIC = "RedLogic";
    public static final String ID_PROJECT_RED = "ProjRed|Core";
    public static final String ID_BLUEPOWER = "bluepower";
    public static final String ID_COMPUTERCRAFT = "ComputerCraft";
    public static final String ID_ENDER_IO = "EnderIO";
    public static final String ID_MFR = "MineFactoryReloaded";
    public static final String ID_CHARSET = "CharsetWires";

}
