package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPower extends Block {

    protected String name;

    public BlockPower(){
        super(Material.field_151594_q);
        name = "powerEmitter";
        this.func_149663_c(name);
    }

    @Override
    public void func_180650_b(World world, BlockPos pos, IBlockState state, Random random){
        //if(!world.isRemote){LogHelper.info("Updating");}
        world.func_175698_g(pos);
    }

    @Override
    public int func_149738_a(World world){
        return 40;
    }

    @Override
    public boolean func_149744_f()
    {
        return true;
    }

    @Override
    public int func_180656_a(IBlockAccess worldAccess, BlockPos pos, IBlockState state, EnumFacing side){
        return 15;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_180655_c(World world, BlockPos pos, IBlockState state, Random random){
        int x = pos.func_177958_n();
        int y = pos.func_177956_o();
        int z = pos.func_177952_p();
        for ( int l = 0; l < 6; ++l ){
            double d1 = (double)((float)x + random.nextFloat());
            double d2 = (double)((float)y + random.nextFloat());
            double d3 = (double)((float)z + random.nextFloat());
            world.func_175688_a(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int func_149645_b()
    {
        return -1;
    }

    public String getName(){
        return name;
    }

    @Override
    public AxisAlignedBB func_180640_a(World world, BlockPos pos, IBlockState state){
        return null;
    }

    @Override
    public boolean func_149662_c()
    {
        return false;
    }

    @Override
    public boolean func_176209_a(IBlockState state, boolean boat)
    {
        return false;
    }

    @Override
    public void func_180653_a(World world, BlockPos pos, IBlockState state, float chance, int fortune) {}
}
