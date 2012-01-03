package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.plasticcraft.core.ItemFood_PC;

public class ItemConsumable extends ItemFood_PC {
  public ItemConsumable(int i, int j, float k, boolean flag) {
    super(i, j, k, flag);
    maxStackSize = 1;
  }
  
  public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
    entityplayer.getFoodStats().eatFood(this);
    world.playSoundAtEntity(entityplayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
    
    if (itemstack != null && itemstack.itemID == PlasticCraftCore.itemPlasticBottleW.shiftedIndex)
      return new ItemStack(PlasticCraftCore.itemPlasticBottle);
    if (itemstack != null && itemstack.itemID == PlasticCraftCore.itemPlasticBottleM.shiftedIndex)
      return new ItemStack(PlasticCraftCore.itemPlasticBottle);
    
    return itemstack;
  }
  
  public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
    if (itemstack != null && itemstack.itemID == PlasticCraftCore.itemNeedleHealth.shiftedIndex && player.getEntityHealth() < player.getMaxHealth()) {
      player.heal(15);
      return new ItemStack(PlasticCraftCore.itemNeedle);
    }
    
    return super.onItemRightClick(itemstack, world, player);
  }
}