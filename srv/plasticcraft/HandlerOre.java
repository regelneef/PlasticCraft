package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;
import net.minecraft.src.forge.IOreHandler;

public class HandlerOre implements IOreHandler {
  public void registerOre(String oreClass, ItemStack item) {
    if (oreClass.equals("itemRubber")) {
      ModLoader.AddRecipe(new ItemStack(mod_PlasticCraft.blockTrampoline), new Object[] { "RRR", "WWW", 
        'R', item, 'W', Block.planks });
      ModLoader.AddRecipe(new ItemStack(mod_PlasticCraft.blockAccelerator, 4), new Object[] { "RXR", "XSX", "RXR", 
        'R', item, 'X', Item.redstone, 'S', mod_PlasticCraft.itemIntegratedCircuit });
      ModLoader.AddRecipe(new ItemStack(mod_PlasticCraft.itemDuctTape, 4), new Object[] { "RRR", "GGG", 
        'R', item, 'G', mod_PlasticCraft.itemPlasticGoo });
      ModLoader.AddRecipe(new ItemStack(mod_PlasticCraft.armorFallBoots), new Object[] { "O O", " C ", "R R", 
        'R', item, 'O', Block.obsidian, 'C', mod_PlasticCraft.itemIntegratedCircuit });
    } else if (oreClass.equals("woodRubber"))
    	mod_PlasticCraft.addExtractorSmelting(item.itemID, new ItemStack(mod_PlasticCraft.itemRubber), new ItemStack(mod_PlasticCraft.itemWoodDust));
  }
}
