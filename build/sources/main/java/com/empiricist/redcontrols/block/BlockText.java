package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TileEntityButtons;
import com.empiricist.redcontrols.tileentity.TileEntityText;
import com.empiricist.redcontrols.utility.ChatHelper;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class BlockText extends BlockBundledReceiver {

    //public static int defaultMeta; //so it renders the right side as item
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyInteger VERTICAL = PropertyInteger.create("vertical", 0, 2);

    public BlockText() {
        super(Material.rock);
        name = "charPanel";
        this.setUnlocalizedName(name);
        //defaultMeta = 3;
        //setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(VERTICAL, 1));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityText();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing face, float clickX, float clickY, float clickZ){
        TileEntity te = world.getTileEntity( pos );
        if( te instanceof TileEntityText){
            TileEntityText tet = (TileEntityText)te;
            //ChatHelper.sendText(player, (world.isRemote? "Client ":"Server ") + tet.getSignalShort() + ", " + tet.debugOutput(tet.signals));
            if(!world.isRemote){
                if(player.getHeldItem() != null){
                    int[] ids = {OreDictionary.getOreID("dyeWhite"), OreDictionary.getOreID("dyeOrange"), OreDictionary.getOreID("dyeMagenta"), OreDictionary.getOreID("dyeLightBlue"),
                            OreDictionary.getOreID("dyeYellow"), OreDictionary.getOreID("dyeLime"), OreDictionary.getOreID("dyePink"), OreDictionary.getOreID("dyeGray"),
                            OreDictionary.getOreID("dyeLightGray"), OreDictionary.getOreID("dyeCyan"), OreDictionary.getOreID("dyePurple"), OreDictionary.getOreID("dyeBlue"),
                            OreDictionary.getOreID("dyeBrown"), OreDictionary.getOreID("dyeGreen"), OreDictionary.getOreID("dyeRed"), OreDictionary.getOreID("dyeBlack")};
                    for(int id : OreDictionary.getOreIDs(player.getHeldItem()) ){
                        for(int i = 0; i < ids.length; i++){
                            if(id == ids[i]){
                                tet.setDyeColor(i);
                                world.markBlockForUpdate( pos );
                                return true;
                            }
                        }
                    }
                }

                byte mode = tet.changeMode();
                if(mode==0){
                    ChatHelper.sendText(player, "Mode: Unicode Character (16 bits)");
                }else{
                    ChatHelper.sendText(player, "Mode: Unicode Character (12 bits), Color (4bits)");
                }
            }else{
                //ChatHelper.sendText(player, "String Width: " + Minecraft.getMinecraft().fontRenderer.getStringWidth(tet.getText()));
            }
        }
        world.markBlockForUpdate( pos );
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack){
        world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(world, pos, entity)).withProperty(VERTICAL, getVerticalFromEntity(world, pos, entity)), 2);
        if(entity instanceof EntityPlayer && !world.isRemote){
            ChatHelper.sendText((EntityPlayer)entity, "Mode: Unicode Character (16 bits)");
        }
        updateTE(world, pos);

//
//        boolean closeH = (MathHelper.abs((float) entity.posX - (float) x) < 2.0F && MathHelper.abs((float)entity.posZ - (float)z) < 2.0F);
//
//        double d0 = entity.posY + 1.82D - (double) entity.yOffset;
//
//        if (closeH && (d0 - (double) y > 2.0D)) {
//            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
//            updateTE(world, x, y, z);
//            return;
//        } else if (closeH && ((double) y - d0 > 0.0D)) {
//            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
//            updateTE(world, x, y, z);
//            return;
//        }
//
//
//        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
//        int m = (l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0))));
//        world.setBlockMetadataWithNotify(x, y, z, m, 2);
//
//        if(entity instanceof EntityPlayer && !world.isRemote){
//            ChatHelper.sendText((EntityPlayer)entity, "Mode: Unicode Character (16 bits)");
//        }
//        updateTE(world, pos);//note some returns above also
    }

    public static EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn) {
        return entityIn.getHorizontalFacing().getOpposite();
    }

    public static int getVerticalFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn) {
        if (MathHelper.abs((float)entityIn.posX - (float)clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - (float)clickedBlock.getZ()) < 2.0F) //up or down
        {
            double d0 = entityIn.posY + (double)entityIn.getEyeHeight();

            if (d0 - (double)clickedBlock.getY() > 2.0D)
            {
                return 2;
            }

            if ((double)clickedBlock.getY() - d0 > 0.0D)
            {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return (state.getValue(VERTICAL) << 2) + state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);//lower 2 bits handle horizontal direction
        int vertical = ((meta >> 2) & 3)%3;//upper 2 bits handle up/flat/down (mod 3 is for safety I guess)
        return getDefaultState().withProperty(FACING, facing).withProperty(VERTICAL, vertical);
    }

    @Override
    public BlockState createBlockState() {
        return new BlockState( this, new IProperty[]{VERTICAL, FACING} );
    }



//    @Override
//    public void getSubBlocks(Item item, CreativeTabs tab, List list){
//        list.add(new ItemStack(item, 1, defaultMeta));
//    }
//
//    @Override
//    public int damageDropped( int meta ){
//        return defaultMeta;
//    }

/*
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        //top and bottom have different texture
        return (side == meta ) ? this.blockIcon : Blocks.stone_slab.getIcon(0,0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(Reference.MOD_ID + ":panelText");
    }
    */
}
