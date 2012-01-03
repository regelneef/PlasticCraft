package net.minecraft.src.plasticcraft;

import net.minecraft.src.*;

public class TileEntityPlastic extends TileEntity {
  private short direction = 0;
  private boolean active = false;
  
  public void readFromNBT(NBTTagCompound nbttagcompound) {
    super.readFromNBT(nbttagcompound);
    direction = nbttagcompound.getShort("Direction");
    active = nbttagcompound.getBoolean("Active");
  }

  public void writeToNBT(NBTTagCompound nbttagcompound) {
    super.writeToNBT(nbttagcompound);
    nbttagcompound.setShort("Direction", direction);
    nbttagcompound.setBoolean("Active", active);
  }
	
  public void setDirection(short direction) {
    this.direction = direction;
  }
  
  public short getDirection() {
    return this.direction;
  }
  
  public short getOppositeDirection() {
    if (this.direction == 2) return 3;
    if (this.direction == 3) return 2;
    if (this.direction == 5) return 4;
    if (this.direction == 4) return 5;
    return 2;
  }
  
  public void setActive(boolean active) {
    this.active = active;
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