package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;

public class ItemPlasticSpade extends ItemTool {
  private static Block blocksEffectiveAgainst[] = new Block[] {
    Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand, Block.mycelium
  };

  public ItemPlasticSpade(int i, EnumToolMaterial enumtoolmaterial) {
    super(i, 1, enumtoolmaterial, blocksEffectiveAgainst);
  }

  public boolean canHarvestBlock(Block block) {
    if (block == Block.snow)
      return true;
    
    return block == Block.blockSnow;
  }
}