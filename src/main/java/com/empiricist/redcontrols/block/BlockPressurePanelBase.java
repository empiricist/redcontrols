package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockPressurePanelBase extends BlockBasePressurePlate {

    protected String name;
    protected static float low = 0.0625F;
    protected static float high = 1.0F - low;
    protected static float HEIGHT_PRESSED = 0.03125F;
    protected static final AxisAlignedBB PANEL_EAST_AABB_PRESSED =  new AxisAlignedBB( 0.0F, low, low, HEIGHT_PRESSED, high, high );
    protected static final AxisAlignedBB PANEL_WEST_AABB_PRESSED =  new AxisAlignedBB( 1.0F, low, low, 1.0F - HEIGHT_PRESSED, high, high );
    protected static final AxisAlignedBB PANEL_NORTH_AABB_PRESSED = new AxisAlignedBB( low, low, 1.0F, high, high, 1.0F - HEIGHT_PRESSED);
    protected static final AxisAlignedBB PANEL_SOUTH_AABB_PRESSED = new AxisAlignedBB( low, low, 0.0F, high, high, HEIGHT_PRESSED);
    protected static final AxisAlignedBB PANEL_UP_AABB_PRESSED =    new AxisAlignedBB( low, 0.0F, low, high, HEIGHT_PRESSED, high);
    protected static final AxisAlignedBB PANEL_DOWN_AABB_PRESSED =  new AxisAlignedBB( low, 1.0F, low, high, 1.0F - HEIGHT_PRESSED, high);
    protected static float HEIGHT_UNPRESSED = 0.0625F;
    protected static final AxisAlignedBB PANEL_EAST_AABB_UNPRESSED =  new AxisAlignedBB( 0.0F, low, low, HEIGHT_UNPRESSED, high, high );
    protected static final AxisAlignedBB PANEL_WEST_AABB_UNPRESSED =  new AxisAlignedBB( 1.0F, low, low, 1.0F - HEIGHT_UNPRESSED, high, high );
    protected static final AxisAlignedBB PANEL_NORTH_AABB_UNPRESSED = new AxisAlignedBB( low, low, 1.0F, high, high, 1.0F - HEIGHT_UNPRESSED);
    protected static final AxisAlignedBB PANEL_SOUTH_AABB_UNPRESSED = new AxisAlignedBB( low, low, 0.0F, high, high, HEIGHT_UNPRESSED);
    protected static final AxisAlignedBB PANEL_UP_AABB_UNPRESSED =    new AxisAlignedBB( low, 0.0F, low, high, HEIGHT_UNPRESSED, high);
    protected static final AxisAlignedBB PANEL_DOWN_AABB_UNPRESSED =  new AxisAlignedBB( low, 1.0F, low, high, 1.0F - HEIGHT_UNPRESSED, high);

    public BlockPressurePanelBase(String name, Material material){
        super(material, material.getMaterialMapColor());
        this.setCreativeTab(CreativeTabRedControls.RED_CONTROLS_TAB);
        setHardness(0.5F);
        setResistance(2.5F);
        this.name = name;
        this.setUnlocalizedName(name);
        //this.setTickRandomly(true);   //handled by BlockBasePressurePlate
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        boolean flag = this.getRedstoneStrength(state) > 0;
        //float height = ( flag ? 0.03125F : 0.0625F);

        switch( getFacing(state) ){
            case DOWN: //on the bottom of a block
                return flag ? PANEL_DOWN_AABB_PRESSED : PANEL_DOWN_AABB_UNPRESSED;
            case UP: //on top of a block
                return flag ? PANEL_UP_AABB_PRESSED : PANEL_UP_AABB_UNPRESSED;
            case NORTH:
                return flag ? PANEL_NORTH_AABB_PRESSED : PANEL_NORTH_AABB_UNPRESSED;
            case SOUTH:
                return flag ? PANEL_SOUTH_AABB_PRESSED : PANEL_SOUTH_AABB_UNPRESSED;
            case WEST:
                return flag ? PANEL_WEST_AABB_PRESSED : PANEL_WEST_AABB_UNPRESSED;
            case EAST:
                return flag ? PANEL_EAST_AABB_PRESSED : PANEL_EAST_AABB_UNPRESSED;
            default:
                return FULL_BLOCK_AABB;//should never happen
        }
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return worldIn.isSideSolid(pos.offset(side.getOpposite()), side);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn){
        if (!this.canPlaceBlockOnSide(worldIn, pos, getFacing(state)))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    //to be overridden in actual pressure panel block classes
    public EnumFacing getFacing(IBlockState state){
        return EnumFacing.DOWN;
    }

    /**
     * Notify block and block in facing direction of changes
     */
    @Override
    protected void updateNeighbors(World worldIn, BlockPos pos) {
        worldIn.notifyNeighborsOfStateChange(pos, this);
        if(worldIn.getBlockState(pos).getBlock() == this){
            worldIn.notifyNeighborsOfStateChange( pos.offset( getFacing(worldIn.getBlockState(pos)).getOpposite() ), this);
        }else{
            for (EnumFacing enumfacing : EnumFacing.values()) {
                worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);//so breaking panel while it is pressed does not leave phantom powered redstone
            }
        }
    }

    @Override
    public int getStrongPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return side == getFacing(state) ? this.getRedstoneStrength(state) : 0;//panels can be on sides/bottom too
    }

    @Override
    protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
        //LogHelper.info("        Base panel compute redstone");
        return 0;
    }

    @Override
    protected int getRedstoneStrength(IBlockState state) {
        //LogHelper.info("        Base panel get redstone");
        return 0;
    }

    @Override
    protected IBlockState setRedstoneStrength(IBlockState state, int strength) {
        return null;
    }



    /**
     * Returns the cubic AABB inset by 1/8 on all sides
     */
//    @Override
//    protected AxisAlignedBB getSensitiveAABB(BlockPos pos)
//    {
//        float f = 0.125F;
//        return new AxisAlignedBB((double)((float)pos.getX() + 0.125F), (double)pos.getY(), (double)((float)pos.getZ() + 0.125F), (double)((float)(pos.getX() + 1) - 0.125F), (double)pos.getY() + 0.25D, (double)((float)(pos.getZ() + 1) - 0.125F));
//    }




    public String getName(){
        return name;
    }

    @Override
    public String getUnlocalizedName(){
        //easy storage format: blockName
        //convert to proper format: tile.[modID]:[blockName].name
        return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName( String unlocalizedName ){
        return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
    }
}
