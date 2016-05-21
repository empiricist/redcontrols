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
        super(material, material.func_151565_r());
        this.func_149647_a(CreativeTabRedControls.RED_CONTROLS_TAB);
        func_149711_c(0.5F);
        func_149752_b(2.5F);
        this.name = name;
        this.func_149663_c(name);
        this.func_149675_a(true);
    }

    public EnumFacing getFacing(IBlockState state){
        return EnumFacing.DOWN;
    }

    @Override
    protected int func_180669_e(World worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    protected int func_176576_e(IBlockState state) {
        return 0;
    }

    @Override
    protected IBlockState func_176575_a(IBlockState state, int strength) {
        return null;
    }

    @Override
    protected void func_180668_d(IBlockState state)
    {
        boolean flag = this.func_176576_e(state) > 0;
        float low = 0.0625F;
        float high = 1.0F - low;
        float height = ( flag ? 0.03125F : 0.0625F);

        switch( getFacing(state) ){
            case DOWN: //on the bottom of a block
                this.func_149676_a( low, 1.0F, low, high, 1.0F - height, high);
                break;
            case UP: //on top of a block
                this.func_149676_a( low, 0.0F, low, high, height, high);
                break;
            case NORTH:
                this.func_149676_a( low, low, 1.0F, high, high, 1.0F - height);
                break;
            case SOUTH:
                this.func_149676_a( low, low, 0.0F, high, high, height);
                break;
            case WEST:
                this.func_149676_a( 1.0F, low, low, 1.0F - height, high, high );
                break;
            case EAST:
                this.func_149676_a( 0.0F, low, low, height, high, high );
                break;
        }
    }

    @Override
    public boolean func_176198_a(World worldIn, BlockPos pos, EnumFacing side) {
        return worldIn.isSideSolid(pos.func_177972_a(side.func_176734_d()), side);
    }

    @Override
    public void func_176204_a(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (!this.func_176198_a(worldIn, pos, getFacing(state)))
        {
            this.func_176226_b(worldIn, pos, state, 0);
            worldIn.func_175698_g(pos);
        }
    }

    /**
     * Returns the cubic AABB inset by 1/8 on all sides
     */
    @Override
    protected AxisAlignedBB func_180667_a(BlockPos pos)
    {
        float f = 0.125F;
        return new AxisAlignedBB((double)((float)pos.func_177958_n() + 0.125F), (double)pos.func_177956_o(), (double)((float)pos.func_177952_p() + 0.125F), (double)((float)(pos.func_177958_n() + 1) - 0.125F), (double)pos.func_177956_o() + 0.25D, (double)((float)(pos.func_177952_p() + 1) - 0.125F));
    }

    @Override
    /**
     * Notify block and block in facing direction of changes
     */
    protected void func_176578_d(World worldIn, BlockPos pos) {
        worldIn.func_175685_c(pos, this);
        if(worldIn.func_180495_p(pos).func_177230_c() == this){
            worldIn.func_175685_c( pos.func_177972_a( getFacing(worldIn.func_180495_p(pos)).func_176734_d() ), this);
        }else{
            for (EnumFacing enumfacing : EnumFacing.values()) {
                worldIn.func_175685_c(pos.func_177972_a(enumfacing), this);//so breaking panel while it is pressed does not leave phantom powered redstone
            }
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
