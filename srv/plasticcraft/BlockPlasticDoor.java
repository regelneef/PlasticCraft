package net.minecraft.src.plasticcraft;

import java.util.Random;
import net.minecraft.src.*;

public class BlockPlasticDoor extends BlockDoor {
  public BlockPlasticDoor(int i) {
    super(i, Material.glass);
    setHardness(1.0F);
    setResistance(1500F);
    setStepSound(soundGlassFootstep);
  }

  public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    return super.shouldSideBeRendered(iblockaccess, i, j, k, 1 - l);
  }

  public int getBlockTextureFromSideAndMetadata(int i, int data) {
  	if ((data & 0x8) == 8) return 3;
    return 4;
  }

  public int idDropped(int data, Random rand, int j) {
  	if ((data & 0x8) != 0) return 0;
    return mod_PlasticCraft.itemPlexidoor.shiftedIndex;
  }
}