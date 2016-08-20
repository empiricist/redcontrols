package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.reference.Reference;
import net.minecraft.block.BlockLever;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBreakerSwitch extends BlockLever{

    protected String name;
    protected static float f = 0.1875F;// 3/16
    protected static float g = 0.25F;

    protected static final AxisAlignedBB BREAKER_EAST_AABB =  new AxisAlignedBB(0.0F, 0.2F, f, f * 2.0F, 0.8F, 1.0F - f);
    protected static final AxisAlignedBB BREAKER_WEST_AABB =  new AxisAlignedBB(1.0F - f * 2.0F, 0.2F, f, 1.0F, 0.8F, 1.0F - f);
    protected static final AxisAlignedBB BREAKER_NORTH_AABB = new AxisAlignedBB(f, 0.2F, 1.0F - f * 2.0F, 1.0F - f, 0.8F, 1.0F);
    protected static final AxisAlignedBB BREAKER_SOUTH_AABB = new AxisAlignedBB(f, 0.2F, 0.0F, 1.0F - f, 0.8F, f * 2.0F);

    protected static final AxisAlignedBB BREAKER_UP_AABB =    new AxisAlignedBB(g, 0.0F, 0.5F - g, 1.0F - g, 0.6F, 0.5F + g);
    protected static final AxisAlignedBB BREAKER_DOWN_AABB =  new AxisAlignedBB(g, 0.4F, 0.5F - g, 1.0F - g, 1.0F, 0.5F + g);


    public BlockBreakerSwitch(){
        super();
        setCreativeTab(CreativeTabRedControls.RED_CONTROLS_TAB);
        name = "breakerSwitch";
        this.setUnlocalizedName(name);
        setHardness(0.5F);//same as lever (lever's is set in Block.java, not in constructor)
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

        switch ((BlockLever.EnumOrientation)state.getValue(FACING))
        {
            case EAST:
                return BREAKER_EAST_AABB;
            case WEST:
                return BREAKER_WEST_AABB;
            case SOUTH:
                return BREAKER_SOUTH_AABB;
            case NORTH:
                return BREAKER_NORTH_AABB;
            case UP_Z:
            case UP_X:
                return BREAKER_UP_AABB;
            case DOWN_X:
            case DOWN_Z:
                return BREAKER_DOWN_AABB;
            default:
                return FULL_BLOCK_AABB;//should never happen
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
