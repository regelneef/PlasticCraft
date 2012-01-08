package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;

public class ItemBlockFloorMat extends ItemBlock {
  public ItemBlockFloorMat(int i) {
    super(i);
    setMaxDamage(0);
    setHasSubtypes(true);
    setItemName("pFloorMatBlock");
  }

  public int getPlacedBlockMetadata(int i) {
    return i;
  }

  public String getItemNameIS(ItemStack itemstack) {
    if (itemstack.getItemDamage() == 1) return "plastic.accelerator";
    return "plastic.trampoline";
  }
}