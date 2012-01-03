package net.minecraft.src.plasticcraft;

import java.util.List;
import net.minecraft.src.*;

public class ContainerUncrafter extends Container {
  private TileEntityUncrafter uncrafter;
  private int lastCraftTime;
  
  public ContainerUncrafter(InventoryPlayer inventoryplayer, TileEntityUncrafter tileentityuncrafter) {
    lastCraftTime = 0;
    uncrafter = tileentityuncrafter;
        
    addSlot(new Slot(tileentityuncrafter, 0, 34, 35)); // item
    
    int id1 = 1;
    for (int j=92; j<=128; j+=18) { // row 1
      addSlot(new SlotFurnace(inventoryplayer.player, tileentityuncrafter, id1, j, 17));
      id1++;
    }
    
    int id2 = 4;
    for (int j=92; j<=128; j+=18) { // row 2
      addSlot(new SlotFurnace(inventoryplayer.player, tileentityuncrafter, id2, j, 35));
      id2++;
    }
    
    int id3 = 7;
    for (int j=92; j<=128; j+=18) { // row 3
      addSlot(new SlotFurnace(inventoryplayer.player, tileentityuncrafter, id3, j, 53));
      id3++;
    }
    
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
            
      if (lastCraftTime != uncrafter.uncrafterCraftTime)
        icrafting.updateCraftingInventoryInfo(this, 0, uncrafter.uncrafterCraftTime);
    }

    lastCraftTime = uncrafter.uncrafterCraftTime;
  }

  public void updateProgressBar(int i, int j) {
    if (i == 0)
      uncrafter.uncrafterCraftTime = j;
  }

  public boolean canInteractWith(EntityPlayer entityplayer) {
    return uncrafter.isUseableByPlayer(entityplayer);
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