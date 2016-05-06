package com.empiricist.redcontrols.creativetab;

import com.empiricist.redcontrols.block.BlockSwitches;
import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.reference.Reference;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

//custom creative tab
public class CreativeTabRedControls {
    //I guess you don't need to register tabs anywhere? b/c registered items added to it?
    public static final CreativeTabs RED_CONTROLS_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase()) {
        @Override
        public Item getTabIconItem() {
            return new ItemStack(ModBlocks.buttons).getItem();//this tab's icon item
        }

//        @Override
//        @SideOnly(Side.CLIENT)
//        public int getIconItemDamage()
//        {
//            return BlockSwitches.defaultMeta;
//        } //meta of icon item
    };
}
