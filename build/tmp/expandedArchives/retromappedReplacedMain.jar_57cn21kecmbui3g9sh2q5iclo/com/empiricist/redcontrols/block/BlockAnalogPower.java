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

    public static final PropertyInteger POWER = PropertyInteger.func_177719_a("power", 0, 15);

    public BlockAnalogPower(){
        super(Material.field_151594_q);
        name = "analogPower";
        this.func_149663_c(name);
        //setDefaultState(blockState.getBaseState().withProperty(POWER, 0));
    }

    @Override
    public boolean func_149744_f()
    {
        return true;
    }

    @Override
    public int func_180656_a(IBlockAccess worldAccess, BlockPos pos, IBlockState state, EnumFacing side){
        return state.func_177229_b(POWER);
    }

    @Override
    public boolean func_180639_a(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
        int meta = state.func_177229_b(POWER);
        if(player.func_70093_af()){
            meta--;
        }else{
            meta++;
        }
        world.func_180501_a(pos, field_176227_L.func_177621_b().func_177226_a(POWER, meta&15), 3);
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int func_180662_a(IBlockAccess worldAccess, BlockPos pos, int pass){ //in world
        return 0x080202 * (worldAccess.func_180495_p(pos).func_177229_b(POWER)+1) + 0x7F0000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int func_180644_h(IBlockState state){ //in inventory, but only if registered with an ItemColored using a silly workaround for a type issue with forge's reflection stuff
        return 0xFF2000;
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public int getBlockColor() {//not necessary I guess
//        return 0xFF2000;
//    }

    @Override
    public int func_176201_c(IBlockState state){
        return state.func_177229_b(POWER);
    }

    @Override
    public IBlockState func_176203_a(int meta){
        return field_176227_L.func_177621_b().func_177226_a(POWER, meta);
    }

    @Override
    public BlockState func_180661_e() {
        return new BlockState( this, POWER );
    }

}
