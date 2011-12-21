package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;

public class ItemBlockMicrowave extends ItemBlock {
  public ItemBlockMicrowave(int i) {
    super(i);
    setMaxDamage(0);
    setHasSubtypes(true);
    setItemName("pMicrowave");
  }

  public int getPlacedBlockMetadata(int i) {
    return i;
  }

  public String getItemNameIS(ItemStack itemstack) {
    if (itemstack.getItemDamage() == 8) return "machine.extract";
    return "machine.microwave";
  }
}