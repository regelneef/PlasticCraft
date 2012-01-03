package net.minecraft.src.plasticcraft;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_PlasticCraft;
import net.minecraft.src.plasticcraft.core.IProxy;

public class ClientProxy implements IProxy {
  public boolean isClient(World world) {
    return world.multiplayerWorld;
  }
  
  public boolean isServer(World world) {
    return false;
  }

  public boolean isSingleplayer(World world) {
    return !world.multiplayerWorld;
  }
  
  public Packet getTileEntityPacket(TileEntity te, int[] dataInt, float[] dataFloat, String[] dataString) {
    return null;
  }
  
  public void sendPacketToServer(Packet230ModLoader packet) {
    ModLoaderMp.SendPacket(mod_PlasticCraft.instance, packet);
  }
  
  public void sendPacketToAll(Packet230ModLoader packet) {}

  public int getModId() {
    if (mod_PlasticCraft.instance == null)
		  return 0;
    
    return mod_PlasticCraft.instance.getId();
  }
}