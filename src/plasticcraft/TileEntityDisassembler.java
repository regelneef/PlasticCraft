package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;
import net.minecraft.src.forge.ISidedInventory;

public class TileEntityDisassembler extends TileEntityPlastic implements IInventory, ISidedInventory {
  private ItemStack disassemblerItemStacks[];
  public int disassemblerCraftTime;
  public int craftTime;
  
  public TileEntityDisassembler() {
    disassemblerItemStacks = new ItemStack[10];
    disassemblerCraftTime = 0;
    craftTime = 60;
  }

  public int getSizeInventory() {
    return disassemblerItemStacks.length;
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
    return disassemblerItemStacks[i];
  }

  public ItemStack decrStackSize(int i, int j) {
    if (disassemblerItemStacks[i] != null) {
      if (disassemblerItemStacks[i].stackSize <= j) {
        ItemStack itemstack = disassemblerItemStacks[i];
        disassemblerItemStacks[i] = null;
        return itemstack;
      }
      
      ItemStack itemstack1 = disassemblerItemStacks[i].splitStack(j);
      if (disassemblerItemStacks[i].stackSize == 0)
        disassemblerItemStacks[i] = null;
      
      return itemstack1;
    } else return null;
  }

  public void setInventorySlotContents(int i, ItemStack itemstack) {
    disassemblerItemStacks[i] = itemstack;
    
    if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
      itemstack.stackSize = getInventoryStackLimit();
  }

  public String getInvName() {
    return "disassembler";
  }

  public void readFromNBT(NBTTagCompound nbttagcompound) {
    super.readFromNBT(nbttagcompound);
    NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
    disassemblerItemStacks = new ItemStack[getSizeInventory()];
        
    for (int i=0; i<nbttaglist.tagCount(); i++) {
      NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
      byte byte0 = nbttagcompound1.getByte("Slot");
      
      if (byte0 >= 0 && byte0 < disassemblerItemStacks.length)
        disassemblerItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
    }

    disassemblerCraftTime = nbttagcompound.getShort("CraftingTime");
  }

  public void writeToNBT(NBTTagCompound nbttagcompound) {
    super.writeToNBT(nbttagcompound);
    nbttagcompound.setShort("CraftingTime", (short)disassemblerCraftTime);
    NBTTagList nbttaglist = new NBTTagList();
        
    for (int i=0; i<disassemblerItemStacks.length; i++) {
      if (disassemblerItemStacks[i] != null) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setByte("Slot", (byte)i);
        disassemblerItemStacks[i].writeToNBT(nbttagcompound1);
        nbttaglist.setTag(nbttagcompound1);
      }
    }

    nbttagcompound.setTag("Items", nbttaglist);
  }

  public int getInventoryStackLimit() {
    return 64;
  }

  public int getUncraftProgressScaled(int i) {
    return (disassemblerCraftTime * i) / craftTime;
  }

  public void updateEntity() {
    boolean flag = false;
    
    if (!worldObj.multiplayerWorld) {
      if (canUncraft()) {
        disassemblerCraftTime++;
        if (disassemblerCraftTime == craftTime) {
          disassemblerCraftTime = 0;
          uncraftItem();
          flag = true;
        }
      } else
        disassemblerCraftTime = 0;
    }
    
    if (flag) onInventoryChanged();
  }

  private boolean canUncraft() {
    if (disassemblerItemStacks[0] == null) return false;
    
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
    if (disassemblerItemStacks[1] == null && disassemblerItemStacks[2] == null && disassemblerItemStacks[3] == null
      && disassemblerItemStacks[4] == null && disassemblerItemStacks[5] == null && disassemblerItemStacks[6] == null
      && disassemblerItemStacks[7] == null && disassemblerItemStacks[8] == null && disassemblerItemStacks[9] == null) return true;
    
    return true;
  }

  public void uncraftItem() {
    if (!canUncraft()) return;
    
    for (int i=1; i<10; i++) {
      if (getResultBySlot(i) != null) {
        if (disassemblerItemStacks[i] == null)
          disassemblerItemStacks[i] = getResultBySlot(i).copy();
        else if (disassemblerItemStacks[i].itemID == getResultBySlot(i).itemID)
          disassemblerItemStacks[i].stackSize += getResultBySlot(i).stackSize;
      }
    }
    
    if (disassemblerItemStacks[0].getItem().hasContainerItem())
      disassemblerItemStacks[0] = new ItemStack(disassemblerItemStacks[0].getItem().getContainerItem());
    else disassemblerItemStacks[0].stackSize--;
        
    if (disassemblerItemStacks[0].stackSize <= 0)
      disassemblerItemStacks[0] = null;
  }
  
  private ItemStack getResultBySlot(int slot) {
    return RecipesDisassembler.uncrafting().getUncraftingResult(disassemblerItemStacks[0], slot);
  }

  public boolean isUseableByPlayer(EntityPlayer entityplayer) {
    if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
      return false;
      
    return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
  }
    
  public void openChest() {}
  public void closeChest() {}
}