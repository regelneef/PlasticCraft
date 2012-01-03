package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiExtractor extends GuiContainer {
  private TileEntityExtractor extractorInventory;
	
  public GuiExtractor(InventoryPlayer inventoryplayer, TileEntityExtractor tileentityextract) {
    super(new ContainerExtractor(inventoryplayer, tileentityextract));
    extractorInventory = tileentityextract;
  }

  public void drawGuiContainerForegroundLayer() {
    fontRenderer.drawString("Extractor", 60, 6, 0x404040);
    fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
  }

  protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    int texture = mc.renderEngine.getTexture(PlasticCraftCore.modDir + "guiExtractor.png");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    mc.renderEngine.bindTexture(texture);
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
    if (extractorInventory.isBurning()) {
      int burn = extractorInventory.getBurnTimeRemainingScaled(12);
      drawTexturedModalRect(guiLeft + 8, (guiTop + 28 + 12) - burn, 176, 12 - burn, 14, burn + 2);
    }
        
    int cook1 = extractorInventory.getCookProgressScaledOne(24);
    drawTexturedModalRect(guiLeft + 53, guiTop + 34, 176, 14, cook1 + 1, 16);
    
    int cook2 = extractorInventory.getCookProgressScaledTwo(24);
    drawTexturedModalRect(guiLeft + 125, guiTop + 34, 176, 14, cook2 + 1, 16);
  }
}