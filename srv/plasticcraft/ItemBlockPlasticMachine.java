package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;

public class ItemBlockPlasticMachine extends ItemBlock {
  public ItemBlockPlasticMachine(int i) {
    super(i);
    setMaxDamage(0);
    setHasSubtypes(true);
    setItemName("pPlasticMachine");
  }

  public int getMetadata(int i) {
    return i;
  }

  public String func_35407_a(ItemStack itemstack) {
    if (itemstack.getItemDamage() == 8) return "plastic.extractor";
    return "plastic.microwave";
  }
}