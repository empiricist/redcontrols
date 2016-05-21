package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockPressurePanel extends BlockPressurePanelBase{ //like a pressure plate but it can go on walls and stuff

    public static final PropertyDirection FACING = PropertyDirection.func_177714_a("facing");
    public static final PropertyBool POWERED = PropertyBool.func_177716_a("powered");
    private final BlockPressurePanel.Sensitivity sensitivity;

    public BlockPressurePanel(String name, Material materialIn, BlockPressurePanel.Sensitivity sensitivityIn){
        super(name, materialIn);
        this.sensitivity = sensitivityIn;
    }

    @Override
    public EnumFacing getFacing(IBlockState state){
        return state.func_177229_b(FACING);
    }

    @Override
    protected int func_176576_e(IBlockState state)
    {
        return state.func_177229_b(POWERED) ? 15 : 0;
    }
    @Override
    protected IBlockState func_176575_a(IBlockState state, int strength)
    {
        return state.func_177226_a(POWERED, (strength > 0));
    }
    @Override
    protected int func_180669_e(World worldIn, BlockPos pos)
    {
        AxisAlignedBB axisalignedbb = this.func_180667_a(pos);
        List<? extends Entity > list;

        switch (this.sensitivity)
        {
            case EVERYTHING:
                list = worldIn.func_72839_b((Entity)null, axisalignedbb);
                break;
            case MOBS:
                list = worldIn.<Entity>func_72872_a(EntityLivingBase.class, axisalignedbb);
                break;
            case PLAYERS:
                list = worldIn.<Entity>func_72872_a(EntityPlayer.class, axisalignedbb);
                break;
            default:
                return 0;
        }

        if (!list.isEmpty())
        {
            for (Entity entity : list)
            {
                if (!entity.func_145773_az())
                {
                    return 15;
                }
            }
        }

        return 0;
    }

    @Override
    public IBlockState func_180642_a(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        //LogHelper.info("Facing is " + facing.name());
        return this.func_176223_P().func_177226_a(POWERED, false).func_177226_a(FACING, facing);//place it on the side of the block they click on
    }

    @Override
    public int func_176211_b(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        return side == state.func_177229_b(FACING) ? this.func_176576_e(state) : 0;
    }

    @Override
    public IBlockState func_176203_a(int meta) {
        return this.func_176223_P().func_177226_a(POWERED, (meta&1) == 1).func_177226_a(FACING, EnumFacing.values()[ (meta>>1)%6 ]);
    }

    @Override
    public int func_176201_c(IBlockState state) {
        return (state.func_177229_b(POWERED) ? 1 : 0) + (state.func_177229_b(FACING).func_176745_a() << 1);
    }

    @Override
    protected BlockState func_180661_e()
    {
        return new BlockState(this, new IProperty[] {POWERED, FACING});
    }

    public static enum Sensitivity
    {
        EVERYTHING,
        MOBS,
        PLAYERS;
    }
}
