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
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockPressurePanel extends BlockPressurePanelBase{ //like a pressure plate but it can go on walls and stuff

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    private final BlockPressurePanel.Sensitivity sensitivity;

    public BlockPressurePanel(String name, Material materialIn, BlockPressurePanel.Sensitivity sensitivityIn){
        super(name, materialIn);
        this.sensitivity = sensitivityIn;
    }

    @Override
    public EnumFacing getFacing(IBlockState state){
        return state.getValue(FACING);
    }

    @Override
    protected int getRedstoneStrength(IBlockState state)
    {
        return state.getValue(POWERED) ? 15 : 0;
    }
    @Override
    protected IBlockState setRedstoneStrength(IBlockState state, int strength)
    {
        return state.withProperty(POWERED, (strength > 0));
    }

    @Override
    protected void playClickOnSound(World worldIn, BlockPos pos) {
        switch (this.sensitivity)
        {
            case EVERYTHING:
                worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.8F);//wooden panel
                break;
            case MOBS:
                worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);//stone panel
                break;
            case PLAYERS:
                worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);//stone brick panel
                break;
        }
    }

    @Override
    protected void playClickOffSound(World worldIn, BlockPos pos) {
        switch (this.sensitivity)
        {
            case EVERYTHING:
                worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.8F);//wooden panel
                break;
            case MOBS:
                worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.6F);//stone panel
                break;
            case PLAYERS:
                worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.6F);//stone brick panel
                break;
        }
    }

    @Override
    protected int computeRedstoneStrength(World worldIn, BlockPos pos)
    {
        AxisAlignedBB axisalignedbb = PRESSURE_AABB.offset(pos);//this.getSensitiveAABB(pos);
        List<? extends Entity > list;

        switch (this.sensitivity)
        {
            case EVERYTHING:
                list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);
                break;
            case MOBS:
                list = worldIn.<Entity>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
                break;
            case PLAYERS:
                list = worldIn.<Entity>getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
                break;
            default:
                return 0;
        }

        if (!list.isEmpty())
        {
            for (Entity entity : list)
            {
                if (!entity.doesEntityNotTriggerPressurePlate())
                {
                    return 15;
                }
            }
        }

        return 0;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        //LogHelper.info("Facing is " + facing.name());
        return this.getDefaultState().withProperty(POWERED, false).withProperty(FACING, facing);//place it on the side of the block they click on
    }



    @Override
    public int getStrongPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return side == state.getValue(FACING) ? this.getRedstoneStrength(state) : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POWERED, (meta&1) == 1).withProperty(FACING, EnumFacing.values()[ (meta>>1)%6 ]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(POWERED) ? 1 : 0) + (state.getValue(FACING).getIndex() << 1);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {POWERED, FACING});
    }

    public static enum Sensitivity
    {
        EVERYTHING,
        MOBS,
        PLAYERS;
    }
}
