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
        super(Material.circuits);
        name = "powerEmitter";
        this.setUnlocalizedName(name);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random){
        //if(!world.isRemote){LogHelper.info("Updating");}
        world.setBlockToAir(pos);
    }

    @Override
    public int tickRate(World world){
        return 40;
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public int getWeakPower(IBlockAccess worldAccess, BlockPos pos, IBlockState state, EnumFacing side){
        return 15;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random random){
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        for ( int l = 0; l < 6; ++l ){
            double d1 = (double)((float)x + random.nextFloat());
            double d2 = (double)((float)y + random.nextFloat());
            double d3 = (double)((float)z + random.nextFloat());
            world.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    public String getName(){
        return name;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state){
        return null;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean boat)
    {
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {}
}
