package net.minecraft.src.plasticcraft;

import java.util.Random;
import net.minecraft.src.*;

public class BlockPlexiglass extends BlockBreakable {
  public BlockPlexiglass(int i) {
    super(i, 2, Material.glass, false);
    setHardness(1.0F);
    setResistance(1500F);
    setLightOpacity(1);
    setStepSound(soundGlassFootstep);
    setTickOnLoad(true);
  }
  
  public int getLightValue(IBlockAccess iblockaccess, int i, int j, int k) {
  	int meta = iblockaccess.getBlockMetadata(i, j, k);
  	if (meta == 1) return 15;
  	return 0;
  }
  
  public int getBlockTextureFromSideAndMetadata(int side, int meta) {
  	if (meta == 1) return 17;
  	return 2;
  }
  
  public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    return super.shouldSideBeRendered(iblockaccess, i, j, k, 1 - l);
  }

  public int quantityDropped(Random random) {
    return 1;
  }
  
  protected int damageDropped(int i) {
    return i;
  }
}