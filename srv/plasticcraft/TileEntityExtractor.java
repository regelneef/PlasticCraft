package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;
import net.minecraft.src.forge.ISidedInventory;

public class TileEntityExtractor extends TileEntityPlastic implements IInventory, ISidedInventory {
  private ItemStack extractorItemStacks[];
  public int extractorBurnTime;
  public int currentItemBurnTime;
  public int extractorCookTime1;
  public int extractorCookTime2;
  public int cookTime;
  
  public TileEntityExtractor() {
    extractorItemStacks = new ItemStack[7];
    extractorBurnTime = 0;
    currentItemBurnTime = 0;
    extractorCookTime1 = 0;
    extractorCookTime2 = 0;
    cookTime = 200;
  }

  public int getSizeInventory() {
    return extractorItemStacks.length;
  }
  
  public int getStartInventorySide(int side) {
    if (side == 0) return 0;
    if (side == 1) return 1;
    return 3;
  }

  public int getSizeInventorySide(int side) {
    if (side == 0) return 1; // bottom side would only access slot 0 (fuel)
    if (side == 1) return 2; // top side would access slots 1-2 (inputs)
    return 4; // any other side can access the 4 outputs, slots 3-6
  }

  public ItemStack getStackInSlot(int i) {
    return extractorItemStacks[i];
  }

  public ItemStack decrStackSize(int i, int j) {
    if (extractorItemStacks[i] != null) {
      if (extractorItemStacks[i].stackSize <= j) {
        ItemStack itemstack = extractorItemStacks[i];
        extractorItemStacks[i] = null;
        return itemstack;
      }
      
      ItemStack itemstack1 = extractorItemStacks[i].splitStack(j);
      if (extractorItemStacks[i].stackSize == 0)
        extractorItemStacks[i] = null;
      
      return itemstack1;
    } else return null;
  }

  public void setInventorySlotContents(int i, ItemStack itemstack) {
    extractorItemStacks[i] = itemstack;
    
    if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
      itemstack.stackSize = getInventoryStackLimit();
  }

  public String getInvName() {
    return "Extracting Furnace";
  }

  public void readFromNBT(NBTTagCompound nbttagcompound) {
    super.readFromNBT(nbttagcompound);
    NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
    extractorItemStacks = new ItemStack[getSizeInventory()];
        
    for (int i=0; i<nbttaglist.tagCount(); i++) {
      NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
      byte byte0 = nbttagcompound1.getByte("Slot");
      
      if (byte0 >= 0 && byte0 < extractorItemStacks.length)
      	extractorItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
    }

    extractorBurnTime = nbttagcompound.getShort("BurnTime");
    extractorCookTime1 = nbttagcompound.getShort("CookTime1");
    extractorCookTime2 = nbttagcompound.getShort("CookTime2");
    currentItemBurnTime = fuel(extractorItemStacks[0]);
  }

  public void writeToNBT(NBTTagCompound nbttagcompound) {
    super.writeToNBT(nbttagcompound);
    nbttagcompound.setShort("BurnTime", (short)extractorBurnTime);
    nbttagcompound.setShort("CookTime1", (short)extractorCookTime1);
    nbttagcompound.setShort("CookTime2", (short)extractorCookTime2);
    NBTTagList nbttaglist = new NBTTagList();
        
    for (int i=0; i<extractorItemStacks.length; i++) {
      if (extractorItemStacks[i] != null) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setByte("Slot", (byte)i);
        extractorItemStacks[i].writeToNBT(nbttagcompound1);
        nbttaglist.setTag(nbttagcompound1);
      }
    }

    nbttagcompound.setTag("Items", nbttaglist);
  }

  public int getInventoryStackLimit() {
    return 64;
  }

  public int getCookProgressScaledOne(int i) {
    return (extractorCookTime1 * i) / cookTime;
  }
  
  public int getCookProgressScaledTwo(int i) {
    return (extractorCookTime2 * i) / cookTime;
  }

  public int getBurnTimeRemainingScaled(int i) {
    if (currentItemBurnTime == 0)
      currentItemBurnTime = cookTime;
    
    return (extractorBurnTime * i) / currentItemBurnTime;
  }

  public boolean isBurning() {
    return extractorBurnTime > 0;
  }

  public void updateEntity() {
    boolean flag = extractorBurnTime > 0;
    boolean flag1 = false;
    if (extractorBurnTime > 0)
      extractorBurnTime--;
    
    if (!worldObj.singleplayerWorld) {
      if (extractorBurnTime == 0 && (canSmeltOne() || canSmeltTwo())) {
        currentItemBurnTime = extractorBurnTime = fuel(extractorItemStacks[0]);
        if (extractorBurnTime > 0) {
          flag1 = true;
          if (extractorItemStacks[0] != null) {
            if (extractorItemStacks[0].getItem().hasContainerItem())
              extractorItemStacks[0] = new ItemStack(extractorItemStacks[0].getItem().getContainerItem());
            else
              extractorItemStacks[0].stackSize--;
            
            if (extractorItemStacks[0].stackSize == 0)
              extractorItemStacks[0] = null;
          }
        }
      }
      
      if (isBurning() && canSmeltOne()) {
        extractorCookTime1++;
        if (extractorCookTime1 == cookTime) {
          extractorCookTime1 = 0;
          smeltItemOne();
          flag1 = true;
        }
      } else
        extractorCookTime1 = 0;
      
      if (isBurning() && canSmeltTwo()) {
        extractorCookTime2++;
        if (extractorCookTime2 == cookTime) {
          extractorCookTime2 = 0;
          smeltItemTwo();
          flag1 = true;
        }
      } else
        extractorCookTime2 = 0;
            
      if (flag != (extractorBurnTime > 0)) {
      	flag1 = true;
      	BlockPlasticMachine.updateBlockState(extractorBurnTime > 0, worldObj, xCoord, yCoord, zCoord);
      }
    }
    
    if (flag1) onInventoryChanged();
  }

  private boolean canSmeltOne() {
    if (extractorItemStacks[1] == null) return false;
    ItemStack itemstack = RecipesExtractor.smelting().getSmeltingResult(extractorItemStacks[1]);
    ItemStack itemstack1 = RecipesExtractor.smelting().getExtractionResult(extractorItemStacks[1]);
        
    if (itemstack == null) return false;
    if (extractorItemStacks[3] == null && extractorItemStacks[4] == null) return true;
    if (!extractorItemStacks[3].isItemEqual(itemstack) && !extractorItemStacks[4].isItemEqual(itemstack1)) return false;
    if (extractorItemStacks[3].stackSize < getInventoryStackLimit() && extractorItemStacks[3].stackSize < extractorItemStacks[3].getMaxStackSize() 
      && extractorItemStacks[4].stackSize < getInventoryStackLimit() && extractorItemStacks[4].stackSize < extractorItemStacks[4].getMaxStackSize())
      return true;
    else
      return extractorItemStacks[3].stackSize < itemstack.getMaxStackSize() && extractorItemStacks[4].stackSize < itemstack.getMaxStackSize();
  }
  
  private boolean canSmeltTwo() {
    if (extractorItemStacks[2] == null) return false;
    ItemStack itemstack = RecipesExtractor.smelting().getSmeltingResult(extractorItemStacks[2]);
    ItemStack itemstack1 = RecipesExtractor.smelting().getExtractionResult(extractorItemStacks[2]);
        
    if (itemstack == null) return false;
    if (extractorItemStacks[5] == null && extractorItemStacks[6] == null) return true;
    if (!extractorItemStacks[5].isItemEqual(itemstack) && !extractorItemStacks[6].isItemEqual(itemstack1)) return false;
    if (extractorItemStacks[5].stackSize < getInventoryStackLimit() && extractorItemStacks[5].stackSize < extractorItemStacks[5].getMaxStackSize() 
      && extractorItemStacks[6].stackSize < getInventoryStackLimit() && extractorItemStacks[6].stackSize < extractorItemStacks[6].getMaxStackSize())
      return true;
    else
      return extractorItemStacks[5].stackSize < itemstack.getMaxStackSize() && extractorItemStacks[6].stackSize < itemstack.getMaxStackSize();
  }

  public void smeltItemOne() {
  	if (!canSmeltOne()) return;
  	
    ItemStack itemstack = RecipesExtractor.smelting().getSmeltingResult(extractorItemStacks[1]);
    ItemStack itemstack1 = RecipesExtractor.smelting().getExtractionResult(extractorItemStacks[1]);
        
    if (extractorItemStacks[3] == null)
      extractorItemStacks[3] = itemstack.copy();
    else if (extractorItemStacks[3].itemID == itemstack.itemID)
      extractorItemStacks[3].stackSize += itemstack.stackSize;
    
    if (itemstack1 != null) {
      if (extractorItemStacks[4] == null)
        extractorItemStacks[4] = itemstack1.copy();
      else if (extractorItemStacks[4].itemID == itemstack1.itemID)
        extractorItemStacks[4].stackSize += itemstack1.stackSize;
    }
    
    if (extractorItemStacks[1].getItem().hasContainerItem())
      extractorItemStacks[1] = new ItemStack(extractorItemStacks[1].getItem().getContainerItem());
    else
    	extractorItemStacks[1].stackSize--;
        
    if (extractorItemStacks[1].stackSize <= 0)
      extractorItemStacks[1] = null;
  }
  
  public void smeltItemTwo() {
    ItemStack itemstack = RecipesExtractor.smelting().getSmeltingResult(extractorItemStacks[2]);
    ItemStack itemstack1 = RecipesExtractor.smelting().getExtractionResult(extractorItemStacks[2]);
        
    if (extractorItemStacks[5] == null)
      extractorItemStacks[5] = itemstack.copy();
    else if (extractorItemStacks[5].itemID == itemstack.itemID)
      extractorItemStacks[5].stackSize += itemstack.stackSize;
    
    if (itemstack1 != null) {
      if (extractorItemStacks[6] == null)
        extractorItemStacks[6] = itemstack1.copy();
      else if(extractorItemStacks[6].itemID == itemstack1.itemID)
        extractorItemStacks[6].stackSize += itemstack1.stackSize;
    }
    
    if (extractorItemStacks[2].getItem().hasContainerItem())
      extractorItemStacks[2] = new ItemStack(extractorItemStacks[2].getItem().getContainerItem());
    else
    	extractorItemStacks[2].stackSize--;
        
    if (extractorItemStacks[2].stackSize <= 0)
      extractorItemStacks[2] = null;
  }

  private int fuel(ItemStack itemstack) {
    if (itemstack == null) return 0;
    
    int i = itemstack.getItem().shiftedIndex;
    if (i < 256 && Block.blocksList[i].blockMaterial == Material.wood) return 250;
    if (i == Item.stick.shiftedIndex) return 80;
    if (i == Block.sapling.blockID) return 100;
    if (i == Block.netherrack.blockID) return 120;
    if (i == Item.coal.shiftedIndex) return 1600;
    return i != PlasticCraftCore.itemBattery.shiftedIndex ? 0 : 25000;
  }

  public boolean isUseableByPlayer(EntityPlayer entityplayer) {
    if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
      return false;
    
    return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
  }
    
  public void openChest() {}
  public void closeChest() {}
}