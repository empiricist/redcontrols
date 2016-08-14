package com.empiricist.redcontrols.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class ChatHelper {
    public static void sendText(EntityPlayer player, String text){
        player.addChatMessage(new TextComponentString(text));
    }
}
