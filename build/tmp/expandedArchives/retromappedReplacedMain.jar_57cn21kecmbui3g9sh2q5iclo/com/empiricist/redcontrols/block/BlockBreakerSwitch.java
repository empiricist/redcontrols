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
        func_149647_a(CreativeTabRedControls.RED_CONTROLS_TAB);
        name = "breakerSwitch";
        this.func_149663_c(name);
    }

    public void func_180654_a(IBlockAccess worldIn, BlockPos pos)
    {
        float f = 0.1875F;// 3/16

        switch ((BlockLever.EnumOrientation)worldIn.func_180495_p(pos).func_177229_b(field_176360_a))
        {
            case EAST:
                this.func_149676_a(0.0F, 0.2F, f, f * 2.0F, 0.8F, 1.0F - f);
                break;
            case WEST:
                this.func_149676_a(1.0F - f * 2.0F, 0.2F, f, 1.0F, 0.8F, 1.0F - f);
                break;
            case SOUTH:
                this.func_149676_a(f, 0.2F, 0.0F, 1.0F - f, 0.8F, f * 2.0F);
                break;
            case NORTH:
                this.func_149676_a(f, 0.2F, 1.0F - f * 2.0F, 1.0F - f, 0.8F, 1.0F);
                break;
            case UP_Z:
            case UP_X:
                f = 0.25F;
                this.func_149676_a(f, 0.0F, 0.5F - f, 1.0F - f, 0.6F, 0.5F + f);
                break;
            case DOWN_X:
            case DOWN_Z:
                f = 0.25F;
                this.func_149676_a(f, 0.4F, 0.5F - f, 1.0F - f, 1.0F, 0.5F + f);
        }
    }

    public String getName(){
        return name;
    }

    @Override
    public String func_149739_a(){
        //easy storage format: blockName
        //convert to proper format: tile.[modID]:[blockName].name
        return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.func_149739_a()));
    }

    protected String getUnwrappedUnlocalizedName( String unlocalizedName ){
        return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
    }

}
