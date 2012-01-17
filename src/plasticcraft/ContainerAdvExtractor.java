package net.minecraft.src.plasticcraft;

import java.util.List;
import net.minecraft.src.*;

public class ContainerAdvExtractor extends Container {
  private TileEntityAdvExtractor extract;
  private int lastCookTime1;
  private int lastCookTime2;
  private int lastBurnTime;
  private int lastItemBurnTime;
  
  public ContainerAdvExtractor(InventoryPlayer inventoryplayer, TileEntityAdvExtractor tileentityextract) {
    lastCookTime1 = 0;
    lastCookTime2 = 0;
    lastBurnTime = 0;
    lastItemBurnTime = 0;
    extract = tileentityextract;
        
    addSlot(new Slot(tileentityextract, 0, 8, 45)); // fuel
    
    addSlot(new Slot(tileentityextract, 1, 34, 35)); // itemOne
    addSlot(new SlotFurnace(inventoryplayer.player, tileentityextract, 3, 80, 25)); // smeltOne
    addSlot(new SlotFurnace(inventoryplayer.player, tileentityextract, 4, 80, 44)); // extractOne
    
    addSlot(new Slot(tileentityextract, 2, 106, 35)); // itemTwo
    addSlot(new SlotFurnace(inventoryplayer.player, tileentityextract, 5, 152, 25)); // smeltTwo
    addSlot(new SlotFurnace(inventoryplayer.player, tileentityextract, 6, 152, 44)); // extractTwo
    
    // player inventory stuff
    for (int i=0; i<3; i++)
      for (int k = 0; k < 9; k++)
        addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));

    for (int j = 0; j < 9; j++)
      addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 142));
  }

  public void updateCraftingResults() {
    super.updateCraftingResults();
        
    for (int i=0; i<crafters.size(); i++) {
      ICrafting icrafting = (ICrafting)crafters.get(i);
            
      if (lastCookTime1 != extract.extractorCookTime1)
        icrafting.updateCraftingInventoryInfo(this, 0, extract.extractorCookTime1);
      if (lastCookTime2 != extract.extractorCookTime2)
        icrafting.updateCraftingInventoryInfo(this, 1, extract.extractorCookTime2);
      if (lastBurnTime != extract.extractorBurnTime)
        icrafting.updateCraftingInventoryInfo(this, 2, extract.extractorBurnTime);
      if (lastItemBurnTime != extract.currentItemBurnTime);
        icrafting.updateCraftingInventoryInfo(this, 3, extract.currentItemBurnTime);
    }

    lastCookTime1 = extract.extractorCookTime1;
    lastCookTime2 = extract.extractorCookTime2;
    lastBurnTime = extract.extractorBurnTime;
    lastItemBurnTime = extract.currentItemBurnTime;
  }

  public void updateProgressBar(int i, int j) {
    if (i == 0)
      extract.extractorCookTime1 = j;
    if (i == 1)
      extract.extractorCookTime2 = j;
    if (i == 2)
      extract.extractorBurnTime = j;
    if (i == 3)
      extract.currentItemBurnTime = j;
  }

  public boolean canInteractWith(EntityPlayer entityplayer) {
    return extract.isUseableByPlayer(entityplayer);
  }
    
  public ItemStack transferStackInSlot(int i) {
    ItemStack itemstack = null;
    Slot slot = (Slot)inventorySlots.get(i);
        
    if (slot != null && slot.getHasStack()) {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();
      
      if (i == 2) {
        if (!mergeItemStack(itemstack1, 3, 39, true))
          return null;
      } else if (i >= 3 && i < 30) {
        if (!mergeItemStack(itemstack1, 30, 39, false))
          return null;
      } else if (i >= 30 && i < 39) {
        if (!mergeItemStack(itemstack1, 3, 30, false))
          return null;
      } else if (!mergeItemStack(itemstack1, 3, 39, false))
        return null;
            
      if (itemstack1.stackSize == 0)
        slot.putStack(null);
      else slot.onSlotChanged();
      
      if (itemstack1.stackSize != itemstack.stackSize)
        slot.onPickupFromSlot(itemstack1);
      else return null;
    }
    
    return itemstack;
  }
}