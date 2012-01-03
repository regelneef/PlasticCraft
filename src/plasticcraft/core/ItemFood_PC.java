package net.minecraft.src.plasticcraft.core;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.plasticcraft.PlasticCraftCore;

public class ItemFood_PC extends ItemFood implements ITextureProvider {
  public ItemFood_PC(int i, int j, float f, boolean flag) {
    super(i, j, f, flag);
  }

  public String getTextureFile() {
    return PlasticCraftCore.itemSheet;
  }
}