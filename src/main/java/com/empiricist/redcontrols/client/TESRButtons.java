package com.empiricist.redcontrols.client;

import com.empiricist.redcontrols.block.BlockSwitches;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.ITEBundledLights;
import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TESRButtons extends TileEntitySpecialRenderer{
    private ResourceLocation[] lightTextures;

    public TESRButtons(){
        lightTextures = new ResourceLocation[16];
        for (int i=0; i<lightTextures.length; i++){
            lightTextures[i] = new ResourceLocation(Reference.MOD_ID + ":textures/blocks/light" + i + ".png");
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        Tessellator tessellator = Tessellator.getInstance();
        net.minecraft.client.renderer.VertexBuffer worldrenderer = tessellator.getBuffer();
        //LogHelper.info(textureRL.toString());

        if(te instanceof ITEBundledLights){
            ITEBundledLights teb = (ITEBundledLights) te;

            boolean[] states = teb.getSignalsArray();
            IBlockState state = te.getWorld().getBlockState(te.getPos());

            for( int i = 0; i < states.length; i++){
                if( !states[i] ){
                    continue;//don't render this one
                }

                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
                GL11.glTranslated(x, y, z);

                this.bindTexture(lightTextures[i]);
                final double stopZFighting = 0.01;

                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX); //no idea why mode is 7, just copying beacon

                switch( state.getValue(BlockSwitches.VERTICAL) ){ //does this work?
                    case 0: //bottom
                        GL11.glScaled(1, -1, -1); //flip
                        GL11.glTranslated(0, 0, -1);
                        switch(  state.getValue(BlockSwitches.FACING ).getHorizontalIndex() ){
                            case 0: //south
                                worldrenderer.pos(1, stopZFighting, 0).tex(1, 0).endVertex();
                                worldrenderer.pos(0, stopZFighting, 0).tex(0, 0).endVertex();
                                worldrenderer.pos(0, stopZFighting, 1).tex(0, 1).endVertex();
                                worldrenderer.pos(1, stopZFighting, 1).tex(1, 1).endVertex();
                                break;
                            case 1: //west
                                worldrenderer.pos(1, stopZFighting, 0).tex(1, 0).endVertex();
                                worldrenderer.pos(0, stopZFighting, 0).tex(0, 0).endVertex();
                                worldrenderer.pos(0, stopZFighting, 1).tex(0, 1).endVertex();
                                worldrenderer.pos(1, stopZFighting, 1).tex(1, 1).endVertex();
                                GL11.glRotated(90, 0, 1, 0);
                                GL11.glTranslated(-1, 0, 0);
                                break;
                            case 2: //north
                                worldrenderer.pos(1, stopZFighting, 0).tex(1, 0).endVertex();
                                worldrenderer.pos(0, stopZFighting, 0).tex(0, 0).endVertex();
                                worldrenderer.pos(0, stopZFighting, 1).tex(0, 1).endVertex();
                                worldrenderer.pos(1, stopZFighting, 1).tex(1, 1).endVertex();
                                GL11.glRotated(180, 0, 1, 0);
                                GL11.glTranslated(-1, 0, -1);
                                break;
                            case 3: //east
                                worldrenderer.pos(1, stopZFighting, 0).tex(1, 0).endVertex();
                                worldrenderer.pos(0, stopZFighting, 0).tex(0, 0).endVertex();
                                worldrenderer.pos(0, stopZFighting, 1).tex(0, 1).endVertex();
                                worldrenderer.pos(1, stopZFighting, 1).tex(1, 1).endVertex();
                                GL11.glRotated(270, 0, 1, 0);
                                GL11.glTranslated(0, 0, -1);
                                break;
                        }
                        break;
                    case 1: //horizontal
                        switch(  state.getValue(BlockSwitches.FACING ).getHorizontalIndex() ){
                            case 0: //south
                                worldrenderer.pos(1, 0, 1+stopZFighting).tex(1, 1).endVertex();
                                worldrenderer.pos(1, 1, 1+stopZFighting).tex(1, 0).endVertex();
                                worldrenderer.pos(0, 1, 1+stopZFighting).tex(0, 0).endVertex();
                                worldrenderer.pos(0, 0, 1+stopZFighting).tex(0, 1).endVertex();
                                break;
                            case 1: //west
                                worldrenderer.pos(-stopZFighting, 0, 1).tex(1, 1).endVertex();
                                worldrenderer.pos(-stopZFighting, 1, 1).tex(1, 0).endVertex();
                                worldrenderer.pos(-stopZFighting, 1, 0).tex(0, 0).endVertex();
                                worldrenderer.pos(-stopZFighting, 0, 0).tex(0, 1).endVertex();
                                break;
                            case 2: //north
                                worldrenderer.pos(0, 0, -stopZFighting).tex(1, 1).endVertex();
                                worldrenderer.pos(0, 1, -stopZFighting).tex(1, 0).endVertex();
                                worldrenderer.pos(1, 1, -stopZFighting).tex(0, 0).endVertex();
                                worldrenderer.pos(1, 0, -stopZFighting).tex(0, 1).endVertex();
                                break;
                            case 3: //east
                                worldrenderer.pos(1+stopZFighting, 0, 0).tex(1, 1).endVertex();
                                worldrenderer.pos(1+stopZFighting, 1, 0).tex(1, 0).endVertex();
                                worldrenderer.pos(1+stopZFighting, 1, 1).tex(0, 0).endVertex();
                                worldrenderer.pos(1+stopZFighting, 0, 1).tex(0, 1).endVertex();
                                break;
                        }
                        break;
                    case 2: //top
                        switch(  state.getValue(BlockSwitches.FACING ).getHorizontalIndex() ){
                            case 0: //south
                                worldrenderer.pos(1, 1+stopZFighting, 1).tex(1, 1).endVertex();
                                worldrenderer.pos(1, 1+stopZFighting, 0).tex(1, 0).endVertex();
                                worldrenderer.pos(0, 1+stopZFighting, 0).tex(0, 0).endVertex();
                                worldrenderer.pos(0, 1+stopZFighting, 1).tex(0, 1).endVertex();
                                break;
                            case 1: //west
                                worldrenderer.pos(1, 1+stopZFighting, 0).tex(1, 0).endVertex();
                                worldrenderer.pos(0, 1+stopZFighting, 0).tex(0, 0).endVertex();
                                worldrenderer.pos(0, 1+stopZFighting, 1).tex(0, 1).endVertex();
                                worldrenderer.pos(1, 1+stopZFighting, 1).tex(1, 1).endVertex();
                                GL11.glRotated(270, 0, 1, 0);
                                GL11.glTranslated(0, 0, -1);
                                break;
                            case 2: //north
                                worldrenderer.pos(0, 1+stopZFighting, 0).tex(0, 0).endVertex();
                                worldrenderer.pos(0, 1+stopZFighting, 1).tex(0, 1).endVertex();
                                worldrenderer.pos(1, 1+stopZFighting, 1).tex(1, 1).endVertex();
                                worldrenderer.pos(1, 1+stopZFighting, 0).tex(1, 0).endVertex();
                                GL11.glRotated(180, 0, 1, 0);
                                GL11.glTranslated(-1, 0, -1);
                                break;
                            case 3: //east
                                worldrenderer.pos(0, 1+stopZFighting, 1).tex(0, 1).endVertex();
                                worldrenderer.pos(1, 1+stopZFighting, 1).tex(1, 1).endVertex();
                                worldrenderer.pos(1, 1+stopZFighting, 0).tex(1, 0).endVertex();
                                worldrenderer.pos(0, 1+stopZFighting, 0).tex(0, 0).endVertex();
                                GL11.glRotated(90, 0, 1, 0);
                                GL11.glTranslated(-1, 0, 0);
                                break;
                        }
                        break;
                }

                tessellator.draw();

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();

            }
        }
    }
}
