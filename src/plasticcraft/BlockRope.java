package net.minecraft.src.plasticcraft;

import java.util.Random;
import net.minecraft.src.*;
import net.minecraft.client.Minecraft;

public class BlockRope extends Block_PC {
  public BlockRope(int i) {
    super(i, Material.circuits);
    setHardness(0.3F);
    blockIndexInTexture = 19;
  }
  
  public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
    return AxisAlignedBB.getBoundingBoxFromPool(((float)i + 0.5F) - 0.0625F, (float)j, ((float)k + 0.5F) - 0.0625F, (float)i + 0.5F + 0.0625F, (float)j + 1.0F, (float)k + 0.5F + 0.0625F);
  }

  public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
    setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
  }
  
  public boolean isLadder() {
    return true;
  }
  
  public int getBlockTextureFromSideAndMetadata(int side, int meta) {
    if (meta == 0) return blockIndexInTexture - 1;
    
    return blockIndexInTexture;
  }

  public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
    if (world.isAirBlock(i, j - 1, k))
      world.setBlockAndMetadataWithNotify(i, j - 1, k, blockID, 1);
  	
    if (world.isAirBlock(i, j + 1, k)) {
      if (world.getBlockMetadata(i, j, k) == 0) {
        float f = 0.7F;
        double d = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        ItemStack itemstack = new ItemStack(mod_PlasticCraft.itemRope, 1);
        EntityItem entityitem = new EntityItem(world, (double)i + d, (double)j + d1, (double)k + d2, itemstack);
        entityitem.delayBeforeCanPickup = 10;
        world.entityJoinedWorld(entityitem);
      }
          
      boolean removeBelow = true;
      int i1 = 1;
      world.setBlockWithNotify(i, j, k, 0);
          
      while (removeBelow) {
        if (world.getBlockId(i, j - i1, k) == blockID)
          world.setBlockWithNotify(i, j - i1, k, 0);
        else if (world.getBlockId(i, j - i1, k) != blockID)
          removeBelow = false;
        i1++;
      }
    }
    
    super.canPlaceBlockOnSide(world, i, j, k, l);
  }

  public void onBlockRemoval(World world, int i, int j, int k) {
    boolean flag = true;
    int l = 1;
          
    for (int m=1; flag; m++) {
      if (world.getBlockId(i, j - l, k) == blockID)
       world.setBlockWithNotify(i, j - l, k, 0);
      else if (world.getBlockId(i, j - l, k) != blockID)
        flag = false;
              
      l++;
      if (world.getBlockId(i, j + m, k) == blockID)
        world.setBlockWithNotify(i, j + m, k, 0);
      else if (world.getBlockId(i, j + m, k) != blockID)
        flag = false;
    }
  }

  public boolean isOpaqueCube() {
    return false;
  }

  public boolean renderAsNormalBlock() {
    return false;
  }
  
  public int idDropped(int i, Random random, int meta) {
    if (meta == 0) return mod_PlasticCraft.itemRope.shiftedIndex;
    return 0;
  }
}