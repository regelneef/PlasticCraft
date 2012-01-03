package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class RenderPlasticBoat extends Render {
  protected ModelBase boat;
    
  public RenderPlasticBoat(){
    shadowSize = 0.5F;
    boat = new ModelBoat();
  }

  public void renderBoat(EntityPlasticBoat entityplasticboat, double d, double d1, double d2, float f1, float f2) {
    GL11.glPushMatrix();
    GL11.glTranslatef((float)d, (float)d1, (float)d2);
    GL11.glRotatef(180F - f1, 0.0F, 1.0F, 0.0F);
    
    float f3 = (float)entityplasticboat.getTimeSinceHit() - f2;
    float f4 = (float)entityplasticboat.getDamageTaken() - f2;
    if(f4 < 0.0F) f4 = 0.0F;
    if(f3 > 0.0F) GL11.glRotatef(((MathHelper.sin(f3) * f3 * f4) / 10F) * (float)entityplasticboat.getForwardDirection(), 1.0F, 0.0F, 0.0F);
    
    loadTexture("/terrain.png");
    float f5 = 0.75F;
    GL11.glScalef(f5, f5, f5);
    GL11.glScalef(1.0F / f5, 1.0F / f5, 1.0F / f5);
    loadTexture(PlasticCraftCore.modDir + "entityPlasticBoat.png");
    GL11.glScalef(-1F, -1F, 1.0F);
    boat.render(entityplasticboat, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
    GL11.glPopMatrix();
  }

  public void doRender(Entity entity, double d, double d1, double d2, float f1, float f2) {
    renderBoat((EntityPlasticBoat)entity, d, d1, d2, f1, f2);
  }
}