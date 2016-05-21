package com.empiricist.redcontrols.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ColorizerGrass;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAnalogPower extends BlockBase{

    public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);

    public BlockAnalogPower(){
        super(Material.circuits);
        name = "analogPower";
        this.setUnlocalizedName(name);
        //setDefaultState(blockState.getBaseState().withProperty(POWER, 0));
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public int getWeakPower(IBlockAccess worldAccess, BlockPos pos, IBlockState state, EnumFacing side){
        return state.getValue(POWER);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
        int meta = state.getValue(POWER);
        if(player.isSneaking()){
            meta--;
        }else{
            meta++;
        }
        world.setBlockState(pos, blockState.getBaseState().withProperty(POWER, meta&15), 3);
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldAccess, BlockPos pos, int pass){ //in world
        return 0x080202 * (worldAccess.getBlockState(pos).getValue(POWER)+1) + 0x7F0000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(IBlockState state){ //in inventory, but only if registered with an ItemColored using a silly workaround for a type issue with forge's reflection stuff
        return 0xFF2000;
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public int getBlockColor() {//not necessary I guess
//        return 0xFF2000;
//    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(POWER);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return blockState.getBaseState().withProperty(POWER, meta);
    }

    @Override
    public BlockState createBlockState() {
        return new BlockState( this, POWER );
    }

}
