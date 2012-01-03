package net.minecraft.src.plasticcraft.core;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.plasticcraft.PlasticCraftCore;

public class Item_PC extends Item implements ITextureProvider {
  public Item_PC(int i) {
    super(i);
  }

  public String getTextureFile() {
    return PlasticCraftCore.itemSheet;
  }
}