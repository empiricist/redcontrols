package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import com.empiricist.redcontrols.reference.Reference;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block{

    protected String name;

    public BlockBase(Material material) {
        //material determines sound, map color, tool?, flammability, etc
        super(material);
        func_149711_c(2);
        func_149752_b(30);
        this.func_149647_a(CreativeTabRedControls.RED_CONTROLS_TAB);
    }

    public BlockBase(){
        this(Material.field_151576_e);//we'll use rock as default
    }

    //unlocalized name here, localized name comes from lang file
    @Override
    public String func_149739_a(){
        //easy storage format: blockName
        //convert to proper format: tile.[modID]:[blockName].name
        return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.func_149739_a()));
    }

    protected String getUnwrappedUnlocalizedName( String unlocalizedName ){
        return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
    }

    @Override
    public int func_149645_b() {
        return 3;//-1 none, 1 liquid, 2 TESR, 3 model
    }

    public String getName(){
        return name;
    }
    /*
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister){
        //this assumes file name is same as name
        blockIcon = iconRegister.registerIcon( getUnwrappedUnlocalizedName( this.getUnlocalizedName() ) );
    }
    */

}