package com.empiricist.redcontrols.item;


import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.creativetab.CreativeTabRedControls;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

//if you do this you can make making new items easier by leaving common stuff here
public class ItemBase extends Item {

    protected String name;

    public ItemBase(){
        super();
        this.func_77637_a(CreativeTabRedControls.RED_CONTROLS_TAB);
    }

    //unlocalized name here, localized name comes from lang file
    @Override
    public String func_77658_a(){
        //easy storage format: itemName
        //convert to proper format: item.[modID]:[itemName].name
        return String.format("item.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.func_77658_a()));
    }

    @Override
    public String func_77667_c(ItemStack itemStack){
        //same format
        return String.format("item.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.func_77658_a()));
    }

    protected String getUnwrappedUnlocalizedName( String unlocalizedName ){
        return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
    }

    public String getName(){ return name; }

    /*
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister){
        //this assumes file name is same as name
        itemIcon = iconRegister.registerIcon( getUnwrappedUnlocalizedName( this.getUnlocalizedName() ) );
    }
    */
}
