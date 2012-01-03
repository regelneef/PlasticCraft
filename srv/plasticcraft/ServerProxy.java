package net.minecraft.src.plasticcraft;

import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_PlasticCraft;
import net.minecraft.src.plasticcraft.core.IProxy;

public class ServerProxy implements IProxy {
  public boolean isClient(World world) {
    return world.singleplayerWorld;
  }
  
  public boolean isServer(World world) {
    return true;
  }

  public boolean isSingleplayer(World world) {
    return !world.singleplayerWorld;
  }
  
  public Packet getTileEntityPacket(TileEntity te, int[] dataInt, float[] dataFloat, String[] dataString) {
    return ModLoaderMp.GetTileEntityPacket(mod_PlasticCraft.instance, te.xCoord, te.yCoord, te.zCoord, 0, dataInt, dataFloat, dataString);
  }
  
  public void sendPacketToServer(Packet230ModLoader packet) {}
  
  public void sendPacketToAll(Packet230ModLoader packet) {
    ModLoaderMp.SendPacketToAll(mod_PlasticCraft.instance, packet);
  }

  public int getModId() {
    if (mod_PlasticCraft.instance == null)
		  return 0;
    
    return mod_PlasticCraft.instance.getId();
  }
}