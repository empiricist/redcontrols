package com.empiricist.redcontrols.init;

import com.empiricist.redcontrols.block.BlockButtons;
import com.empiricist.redcontrols.block.BlockIndicators;
import com.empiricist.redcontrols.block.BlockSwitches;
import com.empiricist.redcontrols.block.BlockText;
import com.empiricist.redcontrols.handler.ConfigurationHandler;
import com.empiricist.redcontrols.utility.LogHelper;
import com.empiricist.redcontrols.init.ModItems;
import com.google.common.collect.Multimap;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public class Recipes {

    //register recipes
    public static void init(){
        //to add vanilla shaped crafting recipe (can also use addShapedRecipe?)

        //button panel
        GameRegistry.addRecipe(new ItemStack(ModBlocks.buttons, 1, 0), "sss", "bbb", "sss", 's', new ItemStack(Blocks.field_150333_U), 'b', new ItemStack(Blocks.field_150430_aB));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.buttons, 1, 0), "sss", "bbb", "sss", 's', new ItemStack(Blocks.field_150333_U), 'b', new ItemStack(Blocks.field_150471_bO));

        //switch panel
        GameRegistry.addRecipe(new ItemStack(ModBlocks.switches, 1, 0), "sss", "lll", "sss", 's', new ItemStack(Blocks.field_150333_U), 'l', new ItemStack(Blocks.field_150442_at));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.switches, 1, 0), "sss", "ttt", "sss", 's', new ItemStack(Blocks.field_150333_U), 't', new ItemStack(ModBlocks.toggleSwitch));

        //indicator panel
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.indicators, 1, 0), "sss", "ggg", "sss", 's', new ItemStack(Blocks.field_150333_U), 'g', "blockGlass"));

        //character panel
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.charPanel, 1, 0), "sss", "gng", "sss", 's', new ItemStack(Blocks.field_150333_U), 'g', "blockGlass", 'n', Items.field_151155_ap));

        //toggle switch
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.toggleSwitch), new ItemStack(Blocks.field_150442_at), new ItemStack(Blocks.field_150430_aB));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.toggleSwitch), new ItemStack(Blocks.field_150442_at), new ItemStack(Blocks.field_150471_bO));

        //analog emitter
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.analogPower), "sss", "rtr", "sss", 's', new ItemStack(Blocks.field_150333_U,1,3), 't', new ItemStack(Blocks.field_150429_aA), 'r', "dustRedstone"));

        //power wand
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powerWand), new ItemStack(Blocks.field_150429_aA), "stickWood", "dustRedstone"));
    }

    public static void postInit(){

    }

    public static boolean exists(String modid, String name) {
        if ( GameRegistry.findItem(modid, name) != null) {
            LogHelper.info("Found item " + name + " for recipe");
            return true;
        }else if (GameRegistry.findBlock(modid, name) != null){
            LogHelper.info("Found block " + name + " for recipe");
            return true;
        }else{
            LogHelper.warn("Did not find " + name + " for recipe!");
            return false;
        }
    }
}
