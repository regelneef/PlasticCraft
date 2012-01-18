package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;
import net.minecraft.src.plasticcraft.PlasticCraftCore.EnumPlasticMachine;

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

  public String getItemNameIS(ItemStack itemstack) {
    int meta = itemstack.getItemDamage();
    if (meta == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Microwave)) return "plastic.microwave";
    if (meta == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Extractor)) return "plastic.extractor";
    if (meta == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Disassembler)) return "plastic.disassembler";
    return "plastic.microwave";
  }
}