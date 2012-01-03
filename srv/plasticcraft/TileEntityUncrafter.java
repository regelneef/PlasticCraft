package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;
import net.minecraft.src.forge.ISidedInventory;

public class TileEntityUncrafter extends TileEntityPlastic implements IInventory, ISidedInventory {
  private ItemStack uncrafterItemStacks[];
  public int uncrafterCraftTime;
  public int craftTime;
  
  public TileEntityUncrafter() {
    uncrafterItemStacks = new ItemStack[10];
    uncrafterCraftTime = 0;
    craftTime = 60;
  }

  public int getSizeInventory() {
    return uncrafterItemStacks.length;
  }
  
  public int getStartInventorySide(int side) {
    if (side == 1) return 0;
    return 1;
  }

  public int getSizeInventorySide(int side) {
    if (side == 1) return 1;
    return 9;
  }

  public ItemStack getStackInSlot(int i) {
    return uncrafterItemStacks[i];
  }

  public ItemStack decrStackSize(int i, int j) {
    if (uncrafterItemStacks[i] != null) {
      if (uncrafterItemStacks[i].stackSize <= j) {
        ItemStack itemstack = uncrafterItemStacks[i];
        uncrafterItemStacks[i] = null;
        return itemstack;
      }
      
      ItemStack itemstack1 = uncrafterItemStacks[i].splitStack(j);
      if (uncrafterItemStacks[i].stackSize == 0)
        uncrafterItemStacks[i] = null;
      
      return itemstack1;
    } else return null;
  }

  public void setInventorySlotContents(int i, ItemStack itemstack) {
    uncrafterItemStacks[i] = itemstack;
    
    if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
      itemstack.stackSize = getInventoryStackLimit();
  }

  public String getInvName() {
    return "Uncrafter";
  }

  public void readFromNBT(NBTTagCompound nbttagcompound) {
    super.readFromNBT(nbttagcompound);
    NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
    uncrafterItemStacks = new ItemStack[getSizeInventory()];
        
    for (int i=0; i<nbttaglist.tagCount(); i++) {
      NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
      byte byte0 = nbttagcompound1.getByte("Slot");
      
      if (byte0 >= 0 && byte0 < uncrafterItemStacks.length)
        uncrafterItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
    }

    uncrafterCraftTime = nbttagcompound.getShort("CraftingTime");
  }

  public void writeToNBT(NBTTagCompound nbttagcompound) {
    super.writeToNBT(nbttagcompound);
    nbttagcompound.setShort("CraftingTime", (short)uncrafterCraftTime);
    NBTTagList nbttaglist = new NBTTagList();
        
    for (int i=0; i<uncrafterItemStacks.length; i++) {
      if (uncrafterItemStacks[i] != null) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setByte("Slot", (byte)i);
        uncrafterItemStacks[i].writeToNBT(nbttagcompound1);
        nbttaglist.setTag(nbttagcompound1);
      }
    }

    nbttagcompound.setTag("Items", nbttaglist);
  }

  public int getInventoryStackLimit() {
    return 64;
  }

  public int getUncraftProgressScaled(int i) {
    return (uncrafterCraftTime * i) / craftTime;
  }

  public void updateEntity() {
    boolean flag = false;
    
    if (!worldObj.singleplayerWorld) {
      if (canUncraft()) {
        uncrafterCraftTime++;
        if (uncrafterCraftTime == craftTime) {
          uncrafterCraftTime = 0;
          uncraftItem();
          flag = true;
        }
      } else
        uncrafterCraftTime = 0;
    }
    
    if (flag) onInventoryChanged();
  }

  private boolean canUncraft() {
    if (uncrafterItemStacks[0] == null) return false;
    
    ItemStack slot1 = getResultBySlot(1);
    ItemStack slot2 = getResultBySlot(2);
    ItemStack slot3 = getResultBySlot(3);
    ItemStack slot4 = getResultBySlot(4);
    ItemStack slot5 = getResultBySlot(5);
    ItemStack slot6 = getResultBySlot(6);
    ItemStack slot7 = getResultBySlot(7);
    ItemStack slot8 = getResultBySlot(8);
    ItemStack slot9 = getResultBySlot(9);
        
    if (slot1 == null && slot2 == null && slot3 == null 
      && slot4 == null && slot5 == null && slot6 == null 
      && slot7 == null && slot8 == null && slot9 == null) return false;
    if (uncrafterItemStacks[1] == null && uncrafterItemStacks[2] == null && uncrafterItemStacks[3] == null
      && uncrafterItemStacks[4] == null && uncrafterItemStacks[5] == null && uncrafterItemStacks[6] == null
      && uncrafterItemStacks[7] == null && uncrafterItemStacks[8] == null && uncrafterItemStacks[9] == null) return true;
    
    return true;
  }

  public void uncraftItem() {
    if (!canUncraft()) return;
    
    for (int i=1; i<10; i++) {
      if (getResultBySlot(i) != null) {
        if (uncrafterItemStacks[i] == null)
          uncrafterItemStacks[i] = getResultBySlot(i).copy();
        else if (uncrafterItemStacks[i].itemID == getResultBySlot(i).itemID)
          uncrafterItemStacks[i].stackSize += getResultBySlot(i).stackSize;
      }
    }
    
    if (uncrafterItemStacks[0].getItem().hasContainerItem())
      uncrafterItemStacks[0] = new ItemStack(uncrafterItemStacks[0].getItem().getContainerItem());
    else uncrafterItemStacks[0].stackSize--;
        
    if (uncrafterItemStacks[0].stackSize <= 0)
      uncrafterItemStacks[0] = null;
  }
  
  private ItemStack getResultBySlot(int slot) {
    return RecipesUncraft.uncrafting().getUncraftingResult(uncrafterItemStacks[0], slot);
  }

  public boolean isUseableByPlayer(EntityPlayer entityplayer) {
    if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
      return false;
      
    return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
  }
    
  public void openChest() {}
  public void closeChest() {}
}