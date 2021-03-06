package net.minecraft.src.plasticcraft.core;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.plasticcraft.PlasticCraftCore;

public class Block_PC extends Block implements ITextureProvider {
  public Block_PC(int i, Material mat) {
  	super(i, mat);
  }
  
  public Block_PC(int i, int j, Material mat) {
  	super(i, j, mat);
  }

  public String getTextureFile() {
    return PlasticCraftCore.blockSheet;
  }
}