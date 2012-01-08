package net.minecraft.src.plasticcraft.core;

import net.minecraft.src.*;
import net.minecraft.src.forge.IOreHandler;
import net.minecraft.src.plasticcraft.PlasticCraftCore;

public class HandlerOre implements IOreHandler {
  public void registerOre(String oreClass, ItemStack item) {
    if (oreClass.equals("itemRubber")) {
      ModLoader.AddRecipe(new ItemStack(PlasticCraftCore.blockFloorMat, 1, 0), new Object[] { "RRR", "WWW", 
        'R', item, 'W', Block.planks });
      ModLoader.AddRecipe(new ItemStack(PlasticCraftCore.blockFloorMat, 4, 1), new Object[] { "RXR", "XSX", "RXR", 
        'R', item, 'X', Item.redstone, 'S', PlasticCraftCore.itemIntegratedCircuit });
      ModLoader.AddRecipe(new ItemStack(PlasticCraftCore.itemDuctTape, 4), new Object[] { "RRR", "GGG", 
        'R', item, 'G', PlasticCraftCore.itemPlasticGoo });
      ModLoader.AddRecipe(new ItemStack(PlasticCraftCore.armorFallBoots), new Object[] { "O O", " C ", "R R", 
        'R', item, 'O', Block.obsidian, 'C', PlasticCraftCore.itemIntegratedCircuit });
    } else if (oreClass.equals("woodRubber"))
    	PlasticCraftCore.addExtractorSmelting(item.itemID, new ItemStack(PlasticCraftCore.itemRubber), new ItemStack(PlasticCraftCore.itemWoodDust));
  }
}
