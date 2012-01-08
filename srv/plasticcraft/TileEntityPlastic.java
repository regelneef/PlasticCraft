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
  
  public void setActiveByState(int state) {
    this.active = (state == 1);
  }
  
  public boolean getActive() {
    return this.active;
  }
  
  public int getActiveByState() {
    if (!this.active) return 0;
    return 1;
  }
  
  public Packet getDescriptionPacket() {
    int[] data = new int[2];
    data[0] = getDirection();
    data[1] = getActiveByState();
    Packet p = PlasticCraftCore.proxy.getTileEntityPacket(this, data, null, null);
    return p;
  }
}