package net.minecraft.src.plasticcraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.src.*;

public class RecipesDisassembler {
  private static final RecipesDisassembler uncraftingBase = new RecipesDisassembler();
  private Map uncraftingList;
  private Map metaUncraftingList;
  
  private RecipesDisassembler() {
    uncraftingList = new HashMap();
    metaUncraftingList = new HashMap();
    
    addUncrafting(PlasticCraftCore.itemMould.shiftedIndex, 
      null, null, null,
      new ItemStack(Block.cobblestone), null, new ItemStack(Block.cobblestone),
      null, new ItemStack(Block.cobblestone), null);
  }

  public static RecipesDisassembler uncrafting() {
    return uncraftingBase;
  }

  public final void addUncrafting(int id, ItemStack... itemstacks) {
  	for (int j=0; j<9; j++)
      uncraftingList.put(Arrays.asList(id, j + 1), itemstacks[j]);
  }
  
  public final void addUncrafting(int id, int meta, ItemStack... itemstacks) {
    for (int j=0; j<9; j++)
      metaUncraftingList.put(Arrays.asList(id, meta, j + 1), itemstacks[j]);
  }
  
  public ItemStack getUncraftingResult(ItemStack src, int slot) {
    ItemStack tr;
  	
    if (src == null) return null;
    
    tr = (ItemStack)metaUncraftingList.get(Arrays.asList(src.itemID, src.getItemDamage(), slot));
    if (tr != null) return tr;
    return (ItemStack)uncraftingList.get(Arrays.asList(src.itemID, slot));
  }
  
  // When I figure out how to use this, I will.. lol
//  public ArrayList<IRecipe> getMatchingRecipes(ItemStack stack) {
//  	ArrayList<IRecipe> matching = new ArrayList<IRecipe>();
//  	if (stack == null) return matching;
//  	for (IRecipe tmp : recipes) {
//  		if (stack.itemID == tmp.getRecipeOutput().itemID && (tmp.getRecipeOutput().getItemDamage() == stack.getItemDamage() || tmp.getRecipeOutput().getItemDamage() < 0)) {
//  			matching.add(tmp);
//  			continue;
//  		}
//  	}
//  	
//  	return matching;
//  }
}