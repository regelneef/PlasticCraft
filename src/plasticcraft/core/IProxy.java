package net.minecraft.src.plasticcraft.core;

import java.util.List;
import java.util.Random;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public interface IProxy {
	public boolean isClient(World world);
	public boolean isServer(World world);
	public boolean isSingleplayer(World world);
	public Packet getTileEntityPacket(TileEntity te, int[] dataInt, float[] dataFloat, String[] dataString);
	public void sendPacketToServer(Packet230ModLoader p);
	public void sendPacketToAll(Packet230ModLoader p);
	public int getModId();
}