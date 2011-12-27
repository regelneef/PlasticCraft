package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;
import net.minecraft.src.forge.IBucketHandler;

public class HandlerBucket implements IBucketHandler {
  public ItemStack fillCustomBucket(World world, int i, int j, int k) {
  	if (world.getBlockId(i, j, k) == mod_PlasticCraft.blockTap.blockID)
      return new ItemStack(mod_PlasticCraft.itemIronBucketL);
  	
    return null;
  }
}
