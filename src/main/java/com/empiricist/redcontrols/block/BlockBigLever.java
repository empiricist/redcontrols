package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.reference.Reference;
import net.minecraft.block.BlockLever;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockBigLever extends BlockLever {

    protected String name;
    protected static float f = 0.1875F;// 3/16
    protected static float g = 0.25F;

    protected static final AxisAlignedBB BIGLEVER_EAST_AABB =  new AxisAlignedBB(0.0F, 0.2F, f, f*4.0F, 0.8F, 1.0F - f);
    protected static final AxisAlignedBB BIGLEVER_WEST_AABB =  new AxisAlignedBB(1.0F - f*4.0F, 0.2F, f, 1.0F, 0.8F, 1.0F - f);
    protected static final AxisAlignedBB BIGLEVER_NORTH_AABB = new AxisAlignedBB(f, 0.2F, 1.0F - f*4.0F, 1.0F - f, 0.8F, 1.0F);
    protected static final AxisAlignedBB BIGLEVER_SOUTH_AABB = new AxisAlignedBB(f, 0.2F, 0.0F, 1.0F - f, 0.8F, f*4.0F);

    protected static final AxisAlignedBB BIGLEVER_UP_AABB =    new AxisAlignedBB(g, 0.0F, 0.5F - g, 1.0F - g, 0.8F, 0.5F + g);
    protected static final AxisAlignedBB BIGLEVER_DOWN_AABB =  new AxisAlignedBB(g, 0.2F, 0.5F - g, 1.0F - g, 1.0F, 0.5F + g);

    public BlockBigLever(){
        super();
        setCreativeTab(CreativeTabRedControls.RED_CONTROLS_TAB);
        name = "bigLever";
        this.setUnlocalizedName(name);
        setHardness(0.5F);//same as lever (lever's is set in Block.java, not in constructor)
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

        switch ((BlockLever.EnumOrientation)state.getValue(FACING))
        {
            case EAST:
                return BIGLEVER_EAST_AABB;
            case WEST:
                return BIGLEVER_WEST_AABB;
            case SOUTH:
                return BIGLEVER_SOUTH_AABB;
            case NORTH:
                return BIGLEVER_NORTH_AABB;
            case UP_Z:
            case UP_X:
                f = 0.25F;
                return BIGLEVER_UP_AABB;
            case DOWN_X:
            case DOWN_Z:
                return BIGLEVER_DOWN_AABB;
            default:
                return FULL_BLOCK_AABB;
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        else
        {
            state = state.cycleProperty(POWERED);
            worldIn.setBlockState(pos, state, 3);
            //worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "random.click", 0.3F, ((Boolean)state.getValue(POWERED)).booleanValue() ? 0.2F : 0.1F);//lower pitch
            worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, ((Boolean)state.getValue(POWERED)).booleanValue() ? 0.2F : 0.1F);
            worldIn.notifyNeighborsOfStateChange(pos, this);
            EnumFacing enumfacing = ((BlockLever.EnumOrientation)state.getValue(FACING)).getFacing();
            worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this);
            return true;
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
