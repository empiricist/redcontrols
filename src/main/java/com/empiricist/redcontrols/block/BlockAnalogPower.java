package com.empiricist.redcontrols.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAnalogPower extends BlockBase{

    public BlockAnalogPower(){
        super(Material.circuits);
        this.setBlockName("analogPower");
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess worldAccess, int x, int y, int z, int side){
        return worldAccess.getBlockMetadata(x,y,z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        int meta = world.getBlockMetadata(x,y,z);
        if(player.isSneaking()){
            meta--;
        }else{
            meta++;
        }
        world.setBlockMetadataWithNotify(x,y,z,meta&15,3);
        return false;
    }

    @Override
    public int getRenderType(){
        return 0;
    }//0,1,17

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldAccess, int x, int y, int z){ //in world
        return 0x080202 * (worldAccess.getBlockMetadata(x,y,z)+1) + 0x7F0000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta){ //in inventory
        return 0xFF2000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        //return Items.cookie.getIconFromDamage(0);
        //return Blocks.stone_slab.getIcon(0,0);
        //return Blocks.stained_glass.getIcon(side,meta);
        //return Blocks.redstone_block.getIcon(0,0);
        return Blocks.furnace.getIcon(0,0);
    }

}
