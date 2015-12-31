package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.utility.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPower extends Block {

    public BlockPower(){
        super(Material.circuits);
        setBlockName("powerEmitter");
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random){
        //if(!world.isRemote){LogHelper.info("Updating");}
        world.setBlockToAir(x, y, z);
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
    public int isProvidingWeakPower(IBlockAccess worldAccess, int x, int y, int z, int side){
        return 15;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random){
        for (int l = 0; l < 6; ++l)
        {
            double d1 = (double)((float)x + random.nextFloat());
            double d2 = (double)((float)y + random.nextFloat());
            double d3 = (double)((float)z + random.nextFloat());
            world.spawnParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z){
        return null;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean canCollideCheck(int meta, boolean boat)
    {
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {}
}
