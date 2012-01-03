package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiUncrafter extends GuiContainer {
  private TileEntityUncrafter uncrafterInventory;
	
  public GuiUncrafter(InventoryPlayer inventoryplayer, TileEntityUncrafter tileentityuncrafter) {
    super(new ContainerUncrafter(inventoryplayer, tileentityuncrafter));
    uncrafterInventory = tileentityuncrafter;
  }

  public void drawGuiContainerForegroundLayer() {
    fontRenderer.drawString("Uncrafter", 60, 6, 0x404040);
    fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
  }

  protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    int texture = mc.renderEngine.getTexture(PlasticCraftCore.modDir + "guiUncrafter.png");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    mc.renderEngine.bindTexture(texture);
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
    int progress = uncrafterInventory.getUncraftProgressScaled(24);
    drawTexturedModalRect(guiLeft + 61, guiTop + 34, 176, 14, progress + 1, 16);
  }
}