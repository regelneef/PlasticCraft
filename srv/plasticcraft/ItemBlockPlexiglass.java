package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;

public class ItemBlockPlexiglass extends ItemBlock {
  public ItemBlockPlexiglass(int i) {
    super(i);
    setMaxDamage(0);
    setHasSubtypes(true);
    setItemName("pPlexiglass");
  }

  public int getMetadata(int i) {
    return i;
  }
  
  public String func_35407_a(ItemStack itemstack) {
    if (itemstack.getItemDamage() == 1) return "plastic.glowplexiglass";
  	return "plastic.plexiglass";
  }
}