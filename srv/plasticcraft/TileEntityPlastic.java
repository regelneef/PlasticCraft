package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;

public class TileEntityPlastic extends TileEntity {
  private short direction = 0;
  private boolean active = false;
  public short prevDirection = 0;
  public boolean prevActive = false;
  
  public void readFromNBT(NBTTagCompound nbttagcompound) {
    super.readFromNBT(nbttagcompound);
    prevDirection = (direction = nbttagcompound.getShort("Direction"));
    active = nbttagcompound.getBoolean("Active");
  }

  public void writeToNBT(NBTTagCompound nbttagcompound) {
    super.writeToNBT(nbttagcompound);
    nbttagcompound.setShort("Direction", direction);
    nbttagcompound.setBoolean("Active", active);
  }
	
  public void setDirection(short direction) {
    this.direction = direction;
    this.prevDirection = direction;
  }
  
  public short getDirection() {
    return this.direction;
  }
  
  public void setActive(boolean active) {
    this.active = active;
    this.prevActive = active;
  }
  
  public boolean getActive() {
    return this.active;
  }
  
  public Packet getDescriptionPacket() {
  	int isActive;
    if (getActive()) isActive = 1;
    else isActive = 0;
    
    int[] data = new int[2];
    data[0] = getDirection();
    data[1] = isActive;
    Packet p = PlasticCraftCore.proxy.getTileEntityPacket(this, data, null, null);
    return p;
  }
}