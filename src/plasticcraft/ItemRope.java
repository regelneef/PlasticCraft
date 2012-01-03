package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;
import net.minecraft.src.plasticcraft.core.Item_PC;

public class ItemRope extends Item_PC {
  public ItemRope(int i) {
    super(i);
    maxStackSize = 1;
  }
  
  public boolean isFull3D() {
    return true;
  }

  public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
    boolean ropeFall = true;
    int m = 2;
 
    if (world.getBlockId(i, j - 1, k) == 0 && world.getBlockId(i, j - 1, k) != PlasticCraftCore.blockRope.blockID) {
      if (world.getBlockId(i, j, k) == PlasticCraftCore.blockRope.blockID)
      	world.setBlockAndMetadataWithNotify(i, j - 1, k, PlasticCraftCore.blockRope.blockID, 1);
      else world.setBlockWithNotify(i, j - 1, k, PlasticCraftCore.blockRope.blockID);
      
      itemstack.stackSize--;
      
      while (ropeFall) {
        if (world.getBlockId(i, j - m, k) == 0) {
          world.setBlockAndMetadataWithNotify(i, j - m, k, PlasticCraftCore.blockRope.blockID, 1);
          m++;
        } else
          ropeFall = false;
      }
    }
    
    return false;
  }
}