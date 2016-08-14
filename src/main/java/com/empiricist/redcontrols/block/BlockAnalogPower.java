package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ColorizerGrass;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockAnalogPower extends BlockBase implements IBlockColor, IItemColor {

    public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);

    public BlockAnalogPower(){
        super(Material.CIRCUITS);
        name = "analogPower";
        this.setUnlocalizedName(name);
        //setDefaultState(blockState.getBaseState().withProperty(POWER, 0));
    }

    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

    @Override
    public int getWeakPower(IBlockState state, IBlockAccess worldAccess, BlockPos pos, EnumFacing side){
        return state.getValue(POWER);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        //LogHelper.info("Side is " + (world.isRemote?"client":"server") + ", hand is " + hand.name());
        if(hand == EnumHand.MAIN_HAND){
            int meta = state.getValue(POWER);
            if(player.isSneaking()){
                meta--;
            }else{
                meta++;
            }
            world.setBlockState(pos, blockState.getBaseState().withProperty(POWER, meta&15), 3);
            return true;
        }
        return false;
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public int colorMultiplier(IBlockAccess worldAccess, BlockPos pos, int pass){ //in world
//
//    }
//    @Override
//    @SideOnly(Side.CLIENT)
//    public int getRenderColor(IBlockState state){ //in inventory, but only if registered with an ItemColored using a silly workaround for a type issue with forge's reflection stuff
//        return 0xFF2000;
//    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) { //for block form
        return 0x080202 * (state.getValue(POWER)+1) + 0x7F0000;
    }
    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {//for item form
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
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer( this, POWER );
    }
}
