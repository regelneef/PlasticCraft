package net.minecraft.src.plasticcraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.src.*;

public class RecipesUncraft {
  private static final RecipesUncraft uncraftingBase = new RecipesUncraft();
  private Map uncraftingList;
  private Map metaUncraftingList;
  
  /*
  * TODO: Get Fishtaco to make this better.
  */
  
  private RecipesUncraft() {
    uncraftingList = new HashMap();
    metaUncraftingList = new HashMap();
    
    addUncrafting(PlasticCraftCore.itemMould.shiftedIndex, 
      null, null, null,
      new ItemStack(Block.cobblestone), null, new ItemStack(Block.cobblestone),
      null, new ItemStack(Block.cobblestone), null);
  }

  public static RecipesUncraft uncrafting() {
    return uncraftingBase;
  }

  public final void addUncrafting(int i, ItemStack... itemstacks) {
    for (int j=0; j<9; j++)
      uncraftingList.put(Arrays.asList(i, j + 1), itemstacks[j]);
  }
  
  public final void addUncrafting(int i, int meta, ItemStack... itemstacks) {
    for (int j=0; j<9; j++)
      metaUncraftingList.put(Arrays.asList(i, meta, j + 1), itemstacks[j]);
  }
  
  public ItemStack getUncraftingResult(ItemStack src, int slot) {
    ItemStack tr;
  	
    if (src == null) return null;
    
    tr = (ItemStack)metaUncraftingList.get(Arrays.asList(src.itemID, src.getItemDamage(), slot));
    if (tr != null) return tr;
    return (ItemStack)uncraftingList.get(Arrays.asList(src.itemID, slot));
  }
}