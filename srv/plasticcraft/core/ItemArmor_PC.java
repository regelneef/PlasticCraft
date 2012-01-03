package net.minecraft.src.plasticcraft.core;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.plasticcraft.PlasticCraftCore;

public class ItemArmor_PC extends ItemArmor implements ITextureProvider {
	boolean isKevlar;
	
  public ItemArmor_PC(int id, EnumArmorMaterial material, int texture, int type, boolean kevlar) {
    super(id, material, texture, type);
    isKevlar = kevlar;
  }
	
  public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
  	if (isKevlar)
  	  itemstack.addEnchantment(Enchantment.projectileProtection, 2);
  }
  
  public String getTextureFile() {
    return PlasticCraftCore.itemSheet;
  }
}