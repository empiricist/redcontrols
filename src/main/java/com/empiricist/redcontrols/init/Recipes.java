package com.empiricist.redcontrols.init;

import com.empiricist.redcontrols.block.BlockButtons;
import com.empiricist.redcontrols.block.BlockIndicators;
import com.empiricist.redcontrols.block.BlockSwitches;
import com.empiricist.redcontrols.block.BlockText;
import com.empiricist.redcontrols.handler.ConfigurationHandler;
import com.empiricist.redcontrols.utility.LogHelper;
import com.empiricist.redcontrols.init.ModItems;
import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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
        GameRegistry.addRecipe(new ItemStack(ModBlocks.buttons, 1, 0), "sss", "bbb", "sss", 's', new ItemStack(Blocks.STONE_SLAB), 'b', new ItemStack(Blocks.STONE_BUTTON));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.buttons, 1, 0), "sss", "bbb", "sss", 's', new ItemStack(Blocks.STONE_SLAB), 'b', new ItemStack(Blocks.WOODEN_BUTTON));

        //switch panel
        GameRegistry.addRecipe(new ItemStack(ModBlocks.switches, 1, 0), "sss", "lll", "sss", 's', new ItemStack(Blocks.STONE_SLAB), 'l', new ItemStack(Blocks.LEVER));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.switches, 1, 0), "sss", "ttt", "sss", 's', new ItemStack(Blocks.STONE_SLAB), 't', new ItemStack(ModBlocks.toggleSwitch));

        //indicator panel
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.indicators, 1, 0), "sss", "ggg", "sss", 's', new ItemStack(Blocks.STONE_SLAB), 'g', "blockGlass"));

        //character panel
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.charPanel, 1, 0), "sss", "gng", "sss", 's', new ItemStack(Blocks.STONE_SLAB), 'g', "blockGlass", 'n', Items.SIGN));

        //toggle switch
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.toggleSwitch), new ItemStack(Blocks.LEVER), new ItemStack(Blocks.STONE_BUTTON));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.toggleSwitch), new ItemStack(Blocks.LEVER), new ItemStack(Blocks.WOODEN_BUTTON));

        //analog emitter
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.analogPower), "sss", "rtr", "sss", 's', new ItemStack(Blocks.STONE_SLAB,1,3), 't', new ItemStack(Blocks.REDSTONE_TORCH), 'r', "dustRedstone"));

        //power wand
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powerWand), new ItemStack(Blocks.REDSTONE_TORCH), "stickWood", "dustRedstone"));

        //breaker switch
        GameRegistry.addRecipe(new ItemStack(ModBlocks.breakerSwitch, 1, 0), "ll", 'l', new ItemStack(Blocks.LEVER));

        //cover button
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.coverButton), "blockGlass", new ItemStack(Blocks.STONE_BUTTON)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.coverButton), "blockGlass", new ItemStack(Blocks.WOODEN_BUTTON)));

        //big lever
        GameRegistry.addRecipe(new ItemStack(ModBlocks.bigLever, 1, 0), "s", "l", 's', Items.STICK, 'l', new ItemStack(Blocks.LEVER));

        //pressure panels
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.woodPanel), new ItemStack(Blocks.WOODEN_PRESSURE_PLATE));
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE), new ItemStack(ModBlocks.woodPanel));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.stonePanel), new ItemStack(Blocks.STONE_PRESSURE_PLATE));
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.STONE_PRESSURE_PLATE), new ItemStack(ModBlocks.stonePanel));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.playerPanel), "bb", 'b', new ItemStack(Blocks.STONEBRICK));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.ironPanels[0]), new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE));
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), new ItemStack(ModBlocks.ironPanels[0]));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.goldPanels[0]), new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE));
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), new ItemStack(ModBlocks.goldPanels[0]));

        //dac
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.dac), "sss", "rtr", "sss", 's', new ItemStack(Blocks.STONE_SLAB), 't', new ItemStack(Blocks.REDSTONE_TORCH), 'r', "dustRedstone"));

    }

    public static void postInit(){

    }

    public static boolean exists(String modid, String name) {
        if ( Item.REGISTRY.getObject(new ResourceLocation(modid + ":" + name)) != null) {
            LogHelper.info("Found item " + name + " for recipe");
            return true;
        }else if (Block.REGISTRY.getObject(new ResourceLocation(modid + ":" + name)) != null) {
            LogHelper.info("Found block " + name + " for recipe");
            return true;
        }else{
            LogHelper.warn("Did not find " + name + " for recipe!");
            return false;
        }
    }
}
