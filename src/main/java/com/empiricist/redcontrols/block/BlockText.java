package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TileEntityButtons;
import com.empiricist.redcontrols.tileentity.TileEntityText;
import com.empiricist.redcontrols.utility.ChatHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.List;

public class BlockText extends BlockBundledReceiver {

    public static int defaultMeta; //so it renders the right side as item

    public BlockText() {
        super(Material.rock);
        this.setBlockName("textPanel");
        defaultMeta = 3;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityText();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        //top and bottom have different texture
        return (side == meta ) ? this.blockIcon : Blocks.stone_slab.getIcon(0,0);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float clickX, float clickY, float clickZ){
        TileEntity te = world.getTileEntity(x,y,z);
        if( te instanceof TileEntityText){
            TileEntityText tet = (TileEntityText)te;
            //ChatHelper.sendText(player, (world.isRemote? "Client ":"Server ") + tet.getSignalShort() + ", " + tet.debugOutput(tet.signals));
            if(!world.isRemote){
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
        world.markBlockForUpdate(x,y,z);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
        boolean closeH = (MathHelper.abs((float) entity.posX - (float) x) < 2.0F && MathHelper.abs((float)entity.posZ - (float)z) < 2.0F);

        double d0 = entity.posY + 1.82D - (double) entity.yOffset;

        if (closeH && (d0 - (double) y > 2.0D)) {
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
            updateTE(world, x, y, z);
            return;
        } else if (closeH && ((double) y - d0 > 0.0D)) {
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
            updateTE(world, x, y, z);
            return;
        }


        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int m = (l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0))));
        world.setBlockMetadataWithNotify(x, y, z, m, 2);

        if(entity instanceof EntityPlayer && !world.isRemote){
            ChatHelper.sendText((EntityPlayer)entity, "Mode: Unicode Character (16 bits)");
        }
        updateTE(world, x, y, z);//note some returns above also
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(Reference.MOD_ID + ":panelText");
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        list.add(new ItemStack(item, 1, defaultMeta));
    }

    @Override
    public int damageDropped( int meta ){
        return defaultMeta;
    }
}
