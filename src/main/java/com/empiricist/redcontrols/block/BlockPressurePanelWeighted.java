package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockPressurePanelWeighted extends BlockPressurePanelBase{

    public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
    private final int weight;
    private EnumFacing facing;

    public BlockPressurePanelWeighted(String name, Material materialIn, int weight, EnumFacing facing){
        super(name, materialIn);
        this.setCreativeTab(facing==EnumFacing.DOWN ? CreativeTabRedControls.RED_CONTROLS_TAB : null);//only register one of them
        this.weight = weight;
        this.facing = facing;
    }

    @Override
    public EnumFacing getFacing(IBlockState state){
        return facing;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        BlockPressurePanelWeighted[] panels = (name.contains("iron") ? ModBlocks.ironPanels : ModBlocks.goldPanels);
        return panels[facing.getIndex()].getDefaultState().withProperty(POWER, 0);//place it on the side of the block they click on
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {//return the default one with the name and inventory model
        if( world.getBlockState(pos).getBlock().getUnlocalizedName().contains("iron") ){
            return new ItemStack(ModBlocks.ironPanels[0]);//its an iron panel
        }//this is a bit silly, why don't I just make these separate classes?
        return new ItemStack(ModBlocks.goldPanels[0]);//its a gold panel
    }

    @Override
    protected int computeRedstoneStrength(World worldIn, BlockPos pos)
    {
        int i = Math.min(worldIn.getEntitiesWithinAABB(Entity.class, this.getSensitiveAABB(pos)).size(), this.weight);

        if (i > 0)
        {
            float f = (float)Math.min(this.weight, i) / (float)this.weight;
            return MathHelper.ceiling_float_int(f * 15.0F);
        }
        else
        {
            return 0;
        }
    }
    @Override
    protected int getRedstoneStrength(IBlockState state) {
        return state.getValue(POWER);
    }
    @Override
    protected IBlockState setRedstoneStrength(IBlockState state, int strength){
        return state.withProperty(POWER, strength);
    }

    /**
     * How many world ticks before ticking
     */
    @Override
    public int tickRate(World worldIn)
    {
        return 10;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POWER, meta & 15);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(POWER);
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {POWER});
    }
}
