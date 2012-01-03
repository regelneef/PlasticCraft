package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;

public class EntityPlasticCow extends EntityCow {
  public EntityPlasticCow(World world) {
    super(world);
  }

  public boolean interact(EntityPlayer entityplayer) {
    ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        
    if (itemstack != null) {
      if (itemstack.itemID == Item.bucketEmpty.shiftedIndex) {
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.bucketMilk));
        return true;
      }
      if(itemstack.itemID == PlasticCraftCore.itemPlasticBucket.shiftedIndex) {
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(PlasticCraftCore.itemPlasticBucketM));
        return true;
      }
    }
    
    return super.interact(entityplayer);
  }
}