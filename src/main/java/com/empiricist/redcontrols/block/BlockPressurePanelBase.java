package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockPressurePanelBase extends BlockBasePressurePlate {

    protected String name;

    public BlockPressurePanelBase(String name, Material material){
        super(material, material.getMaterialMapColor());
        this.setCreativeTab(CreativeTabRedControls.RED_CONTROLS_TAB);
        setHardness(0.5F);
        setResistance(2.5F);
        this.name = name;
        this.setUnlocalizedName(name);
        this.setTickRandomly(true);
    }

    public EnumFacing getFacing(IBlockState state){
        return EnumFacing.DOWN;
    }

    @Override
    protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    protected int getRedstoneStrength(IBlockState state) {
        return 0;
    }

    @Override
    protected IBlockState setRedstoneStrength(IBlockState state, int strength) {
        return null;
    }

    @Override
    protected void setBlockBoundsBasedOnState0(IBlockState state)
    {
        boolean flag = this.getRedstoneStrength(state) > 0;
        float low = 0.0625F;
        float high = 1.0F - low;
        float height = ( flag ? 0.03125F : 0.0625F);

        switch( getFacing(state) ){
            case DOWN: //on the bottom of a block
                this.setBlockBounds( low, 1.0F, low, high, 1.0F - height, high);
                break;
            case UP: //on top of a block
                this.setBlockBounds( low, 0.0F, low, high, height, high);
                break;
            case NORTH:
                this.setBlockBounds( low, low, 1.0F, high, high, 1.0F - height);
                break;
            case SOUTH:
                this.setBlockBounds( low, low, 0.0F, high, high, height);
                break;
            case WEST:
                this.setBlockBounds( 1.0F, low, low, 1.0F - height, high, high );
                break;
            case EAST:
                this.setBlockBounds( 0.0F, low, low, height, high, high );
                break;
        }
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return worldIn.isSideSolid(pos.offset(side.getOpposite()), side);
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (!this.canPlaceBlockOnSide(worldIn, pos, getFacing(state)))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    /**
     * Returns the cubic AABB inset by 1/8 on all sides
     */
    @Override
    protected AxisAlignedBB getSensitiveAABB(BlockPos pos)
    {
        float f = 0.125F;
        return new AxisAlignedBB((double)((float)pos.getX() + 0.125F), (double)pos.getY(), (double)((float)pos.getZ() + 0.125F), (double)((float)(pos.getX() + 1) - 0.125F), (double)pos.getY() + 0.25D, (double)((float)(pos.getZ() + 1) - 0.125F));
    }

    @Override
    /**
     * Notify block and block in facing direction of changes
     */
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
