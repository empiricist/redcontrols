package com.empiricist.redcontrols.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemColored;

//this is dumb
// why do I need this
// I could just recolor the texture of the item version
public class ItemColorable extends ItemColored {
    public ItemColorable(Block block, Boolean hasSubtypes){
        super(block, hasSubtypes);
    }
}
