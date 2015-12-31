package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
import com.empiricist.redcontrols.utility.ChatHelper;
import com.empiricist.redcontrols.utility.LogHelper;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
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
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;


public class BlockSwitches extends BlockBundledEmitter {

    public static int defaultMeta; //so it renders the right side as item

    public BlockSwitches(){
        super(Material.rock);
        this.setHardness(3f);
        this.setBlockName("switchPanel");
        defaultMeta = 3;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntitySwitches();
    }


//    //to tell tileentity it is activated
//    @Override
//    public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
//        TileEntity tile = world.getTileEntity(x, y, z);
//        if (tile != null && tile instanceof TileEntityWarpCore) {
//            TileEntityWarpCore warpCore = (TileEntityWarpCore)tile;
//            warpCore.signal = world.isBlockIndirectlyGettingPowered(x, y, z);
//        }
//    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float clickX, float clickY, float clickZ){
        world.markBlockForUpdate(x, y, z); // Makes the server call getDescriptionPacket for a full data sync
        if(world.getBlockMetadata(x,y,z) != face){ return false; }

        //if( !world.isRemote ){ ChatHelper.sendText(player, "side: " + face + " f0: " + clickX + " f1: " + clickY + " f2: " + clickZ );}//+ " faceX: " + faceX + " faceY: " + faceY + " button: " + button);}

        //TileEntity t = world.getTileEntity(x, y, z);
//        if (t != null && t instanceof TileEntityButtons) {
//            TileEntityButtons warpCore = (TileEntityButtons) t;
//
//            NBTTagCompound nbt = new NBTTagCompound();
//            warpCore.writeToNBT(nbt);
//            LogHelper.info((world.isRemote ? "Client " : "Server ") + " Data: " + nbt);
//        }
        if(!world.isRemote){


            double faceX = 0;
            double faceY = 0;
            switch (face) {
                case 0:
                    faceX = clickX;
                    faceY = clickZ;
                    break;
                case 1:
                    faceX = clickX;
                    faceY = clickZ;
                    break;
                case 2:
                    faceX = 1 - clickX;
                    faceY = 1 - clickY;
                    break;
                case 3:
                    faceX = clickX;
                    faceY = 1 - clickY;
                    break;
                case 4:
                    faceX = clickZ;
                    faceY = 1 - clickY;
                    break;
                case 5:
                    faceX = 1 - clickZ;
                    faceY = 1 - clickY;
            }

            double bezel = 2.0/16;
            int button = -1;
            if( (faceX > bezel) && (faceX < (1 - bezel)) && (faceY > bezel) && (faceY < (1 - bezel))){
                int bx = (int)((faceX - bezel)*16/3);
                int by = (int)((faceY - bezel)*16/3);
                //LogHelper.info("bx " + bx + " by " + by + " button " + button + " bezel " + bezel);
                button = bx + 4 * by;
            }

            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null && tile instanceof TileEntitySwitches) {
                TileEntitySwitches switchPanel = (TileEntitySwitches)tile;
                switchPanel.setSignal(button, !switchPanel.getSignal(button));
                //Minecraft.getMinecraft().getNetHandler().addToSendQueue(warpCore.getDescriptionPacket());
                //world.markBlockForUpdate(x, y, z);


                //player.openGui(RedControls.instance, 0, world, x, y, z);
            }

            world.notifyBlocksOfNeighborChange(x, y, z, this);
        }


        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
        boolean closeH = (MathHelper.abs((float) entity.posX - (float) x) < 2.0F && MathHelper.abs((float)entity.posZ - (float)z) < 2.0F);

        double d0 = entity.posY + 1.82D - (double) entity.yOffset;

        if (closeH && (d0 - (double) y > 2.0D)) {
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
            return;
        } else if (closeH && ((double) y - d0 > 0.0D)) {
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
            return;
        }


        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int m = (l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0))));
        world.setBlockMetadataWithNotify(x, y, z, m, 2);

    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        list.add(new ItemStack(item, 1, defaultMeta));
    }

    @Override
    public int damageDropped( int meta ){
        return defaultMeta;
    }



    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        //top and bottom have different texture
        return (side == meta ) ? this.blockIcon : Blocks.stone_slab.getIcon(0,0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        //super.registerBlockIcons(reg);
        //LogHelper.warn(Reference.MOD_ID + ":panel");
        this.blockIcon = reg.registerIcon(Reference.MOD_ID + ":panel");
    }

//    //public boolean canRenderInPass(int pass)
//    {
//        return true;
//    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    /*--
    @Override
    public void onNeighborBlockChange(World world, int xCoord, int yCoord, int zCoord, Block block) {
        super.onNeighborBlockChange(world, xCoord, yCoord, zCoord, block);
        world.setBlockToAir(xCoord, yCoord, zCoord);
    }
    --*/
}
