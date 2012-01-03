package net.minecraft.src.plasticcraft;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.src.*;

public class RecipesExtractor {
  private static final RecipesExtractor smeltingBase = new RecipesExtractor();
  private Map smeltingList;
  private Map extractionList;
  private Map metaSmeltingList;
  private Map metaExtractionList;
  
  private RecipesExtractor() {
    smeltingList = new HashMap();
    extractionList = new HashMap();
    metaSmeltingList = new HashMap();
    metaExtractionList = new HashMap();
    
    addSmelting(Block.oreCoal.blockID, new ItemStack(Item.coal));
    addSmelting(Block.oreIron.blockID, new ItemStack(Item.ingotIron));
    addSmelting(Block.oreGold.blockID, new ItemStack(Item.ingotGold));
    addSmelting(Block.oreDiamond.blockID, new ItemStack(Item.diamond));
    addSmelting(Block.oreRedstone.blockID, new ItemStack(Item.redstone, 3));
    addSmelting(Block.oreLapis.blockID, new ItemStack(Item.dyePowder, 2, 4));
    addSmelting(Block.sand.blockID, new ItemStack(Block.glass));
    addSmelting(Item.porkRaw.shiftedIndex, new ItemStack(Item.porkCooked));
    addSmelting(Item.beefRaw.shiftedIndex, new ItemStack(Item.beefCooked));
    addSmelting(Item.chickenRaw.shiftedIndex, new ItemStack(Item.chickenCooked));
    addSmelting(Item.fishRaw.shiftedIndex, new ItemStack(Item.fishCooked));
    addSmelting(Block.cobblestone.blockID, new ItemStack(Block.stone));
    addSmelting(Item.clay.shiftedIndex, new ItemStack(Item.brick));
    addSmelting(Block.cactus.blockID, new ItemStack(Item.dyePowder, 1, 2));
    addSmelting(Block.wood.blockID, new ItemStack(Item.coal, 1, 1));
    addSmelting(Block.netherBrick.blockID, new ItemStack(Block.brick));
    addSmelting(Block.netherFence.blockID, new ItemStack(Block.fence));
    addSmelting(Block.stairsNetherBrick.blockID, new ItemStack(Block.stairsStoneBrickSmooth));
    
    addExtraction(Block.oreCoal.blockID, new ItemStack(Block.stone));
    addExtraction(Block.oreIron.blockID, new ItemStack(Block.stone));
    addExtraction(Block.oreGold.blockID, new ItemStack(Block.stone));
    addExtraction(Block.oreDiamond.blockID, new ItemStack(Block.stone));
    addExtraction(Block.oreRedstone.blockID, new ItemStack(Block.stone));
    addExtraction(Block.oreLapis.blockID, new ItemStack(Block.stone));
    addExtraction(Item.porkRaw.shiftedIndex, new ItemStack(PlasticCraftCore.itemGelatin, 2));
    addExtraction(Item.beefRaw.shiftedIndex, new ItemStack(PlasticCraftCore.itemGelatin));
    addExtraction(Item.chickenRaw.shiftedIndex, new ItemStack(PlasticCraftCore.itemGelatin));
    addExtraction(Item.fishRaw.shiftedIndex, new ItemStack(Item.dyePowder, 1, 15));
    addExtraction(Item.clay.shiftedIndex, new ItemStack(Block.sand));
    addExtraction(Block.cactus.blockID, new ItemStack(Item.sugar, 2));
    addExtraction(Block.wood.blockID, new ItemStack(PlasticCraftCore.itemWoodDust, 3));
    addExtraction(Block.netherBrick.blockID, new ItemStack(Block.netherrack, 3));
    addExtraction(Block.netherFence.blockID, new ItemStack(Block.netherrack));
    addExtraction(Block.stairsNetherBrick.blockID, new ItemStack(Block.netherrack, 2));
    
    for (int i = 0; i < 15; i++) { // extracting dyes from wool
      addSmelting(Block.cloth.blockID, BlockCloth.getDyeFromBlock(i), new ItemStack(Block.cloth, 1, 0));
      addExtraction(Block.cloth.blockID, BlockCloth.getDyeFromBlock(i), new ItemStack(Item.dyePowder, 1, i));
    }
  }

  public static RecipesExtractor smelting() {
    return smeltingBase;
  }

  public final void addSmelting(int i, ItemStack itemstack) {
    smeltingList.put(Integer.valueOf(i), itemstack);
  }
  
  public void addSmelting(int i, int meta, ItemStack itemstack) {
    metaSmeltingList.put(Arrays.asList(i, meta), itemstack);
  }

  public final void addExtraction(int i, ItemStack itemstack) {
    extractionList.put(Integer.valueOf(i), itemstack);
  }
  
  public void addExtraction(int i, int meta, ItemStack itemstack) {
    metaExtractionList.put(Arrays.asList(i, meta), itemstack);
  }
  
  public ItemStack getSmeltingResult(ItemStack src) {
    ItemStack tr;

    if (src == null) return null;
    
    tr = (ItemStack)metaSmeltingList.get(Arrays.asList(src.itemID, src.getItemDamage()));
    if (tr != null) return tr;
    return (ItemStack)smeltingList.get(Integer.valueOf(src.itemID));
  }
  
  public ItemStack getExtractionResult(ItemStack src) {
    ItemStack tr;

    if (src == null) return null;
    tr = (ItemStack)metaExtractionList.get(Arrays.asList(src.itemID, src.getItemDamage()));
    if (tr != null) return tr;
    return (ItemStack)extractionList.get(Integer.valueOf(src.itemID));
  }

  public Map getSmeltingList() {
    return smeltingList;
  }

  public Map getExtractionList() {
    return extractionList;
  }
}