package com.empiricist.redcontrols.client;

import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.ITEBundledLights;
import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
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
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
        Tessellator tessellator = Tessellator.instance;
        //LogHelper.info(textureRL.toString());
        //Minecraft.getMinecraft().renderEngine.bindTexture(textureRS);
        //this.bindTexture(textureRL);

        if(te instanceof ITEBundledLights){
            ITEBundledLights teb = (ITEBundledLights) te;

            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
            GL11.glTranslated(x, y, z);


            boolean[] states = teb.getSignalsArray();
            for( int i = 0; i < states.length; i++){
                if( !states[i] ){
                    continue;//don't render this one
                }

                this.bindTexture(lightTextures[i]);
                double stopZFighting = 0.01;

                tessellator.startDrawingQuads();

                switch(te.getBlockMetadata()){
                    case 0:
                        tessellator.addVertexWithUV(0, -stopZFighting, 1, 0, 1);
                        tessellator.addVertexWithUV(0, -stopZFighting, 0, 0, 0);
                        tessellator.addVertexWithUV(1, -stopZFighting, 0, 1, 0);
                        tessellator.addVertexWithUV(1, -stopZFighting, 1, 1, 1);
                        break;
                    case 1:
                        tessellator.addVertexWithUV(0, 1+stopZFighting, 0, 0, 0);
                        tessellator.addVertexWithUV(0, 1+stopZFighting, 1, 0, 1);
                        tessellator.addVertexWithUV(1, 1+stopZFighting, 1, 1, 1);
                        tessellator.addVertexWithUV(1, 1+stopZFighting, 0, 1, 0);
                        break;
                    case 2:
                        tessellator.addVertexWithUV(0, 0, -stopZFighting, 1, 1);
                        tessellator.addVertexWithUV(0, 1, -stopZFighting, 1, 0);
                        tessellator.addVertexWithUV(1, 1, -stopZFighting, 0, 0);
                        tessellator.addVertexWithUV(1, 0, -stopZFighting, 0, 1);
                        break;
                    case 3:
                        tessellator.addVertexWithUV(1, 0, 1+stopZFighting, 1, 1);
                        tessellator.addVertexWithUV(1, 1, 1+stopZFighting, 1, 0);
                        tessellator.addVertexWithUV(0, 1, 1+stopZFighting, 0, 0);
                        tessellator.addVertexWithUV(0, 0, 1+stopZFighting, 0, 1);
                        break;
                    case 4:
                        tessellator.addVertexWithUV(-stopZFighting, 0, 1, 1, 1);
                        tessellator.addVertexWithUV(-stopZFighting, 1, 1, 1, 0);
                        tessellator.addVertexWithUV(-stopZFighting, 1, 0, 0, 0);
                        tessellator.addVertexWithUV(-stopZFighting, 0, 0, 0, 1);
                        break;
                    case 5:
                        tessellator.addVertexWithUV(1+stopZFighting, 0, 0, 1, 1);
                        tessellator.addVertexWithUV(1+stopZFighting, 1, 0, 1, 0);
                        tessellator.addVertexWithUV(1+stopZFighting, 1, 1, 0, 0);
                        tessellator.addVertexWithUV(1+stopZFighting, 0, 1, 0, 1);
                        break;
                }
                tessellator.draw();

            }


            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }
}
