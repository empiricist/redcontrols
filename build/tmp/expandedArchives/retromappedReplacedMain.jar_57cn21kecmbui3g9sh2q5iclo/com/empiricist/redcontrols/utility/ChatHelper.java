package com.empiricist.redcontrols.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ChatHelper {
    public static void sendText(EntityPlayer player, String text){
        player.func_145747_a(new ChatComponentText(text));
    }
}
