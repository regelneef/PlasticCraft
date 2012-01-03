package net.minecraft.src.plasticcraft.core;

import net.minecraft.src.*;
import net.minecraft.src.forge.IBucketHandler;
import net.minecraft.src.plasticcraft.PlasticCraftCore;

public class HandlerBucket implements IBucketHandler {
  public ItemStack fillCustomBucket(World world, int i, int j, int k) {
  	if (world.getBlockId(i, j, k) == PlasticCraftCore.blockTap.blockID)
      return new ItemStack(PlasticCraftCore.itemIronBucketL);
  	
    return null;
  }
}
