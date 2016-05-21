package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.reference.Reference;
import net.minecraft.block.BlockLever;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBreakerSwitch extends BlockLever{

    protected String name;

    public BlockBreakerSwitch(){
        super();
        setCreativeTab(CreativeTabRedControls.RED_CONTROLS_TAB);
        name = "breakerSwitch";
        this.setUnlocalizedName(name);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        float f = 0.1875F;// 3/16

        switch ((BlockLever.EnumOrientation)worldIn.getBlockState(pos).getValue(FACING))
        {
            case EAST:
                this.setBlockBounds(0.0F, 0.2F, f, f * 2.0F, 0.8F, 1.0F - f);
                break;
            case WEST:
                this.setBlockBounds(1.0F - f * 2.0F, 0.2F, f, 1.0F, 0.8F, 1.0F - f);
                break;
            case SOUTH:
                this.setBlockBounds(f, 0.2F, 0.0F, 1.0F - f, 0.8F, f * 2.0F);
                break;
            case NORTH:
                this.setBlockBounds(f, 0.2F, 1.0F - f * 2.0F, 1.0F - f, 0.8F, 1.0F);
                break;
            case UP_Z:
            case UP_X:
                f = 0.25F;
                this.setBlockBounds(f, 0.0F, 0.5F - f, 1.0F - f, 0.6F, 0.5F + f);
                break;
            case DOWN_X:
            case DOWN_Z:
                f = 0.25F;
                this.setBlockBounds(f, 0.4F, 0.5F - f, 1.0F - f, 1.0F, 0.5F + f);
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
