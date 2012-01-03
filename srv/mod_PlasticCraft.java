package net.minecraft.src;

import java.io.File;
import java.io.PrintStream;
import java.util.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.forge.*;
import net.minecraft.src.plasticcraft.*;

public class mod_PlasticCraft extends BaseModMp {
  public String Version() { return PlasticCraftCore.version; }
  public static Props props = new Props(new File("config/" + "mod_PlasticCraft.props").getPath());
  private static void console(String s) { System.out.println("[PlasticCraft] " + s); }
  public static mod_PlasticCraft instance;
  
  public mod_PlasticCraft() {
  	instance = this;
  }
  
  public void ModsLoaded() {
  	PlasticCraftCore.init(new ServerProxy());

    ModLoader.SetInGameHook(this, true, false);
    
    ModLoaderMp.RegisterEntityTrackerEntry(EntityC4Primed.class, PlasticCraftCore.entityC4NetId);
    ModLoaderMp.RegisterEntityTracker(EntityC4Primed.class, 160, 5);
    ModLoaderMp.RegisterEntityTrackerEntry(EntityPlasticBoat.class, PlasticCraftCore.entityPlasticBoatNetId);
    ModLoaderMp.RegisterEntityTracker(EntityPlasticBoat.class, 160, 5);
  }
  
  public void HandlePacket(Packet230ModLoader packet, EntityPlayerMP entityplayermp) {
    int x = (int)packet.dataFloat[0];
    int y = (int)packet.dataFloat[1];
    int z = (int)packet.dataFloat[2];
    
    BlockC4 c4 = (BlockC4)PlasticCraftCore.blockC4;
    BlockTNT tnt = (BlockTNT)Block.tnt;

    int id = entityplayermp.worldObj.getBlockId(x, y, z);
    if (id == PlasticCraftCore.blockC4.blockID) {
      c4.onBlockDestroyedByPlayer(entityplayermp.worldObj, x, y, z, 1);
      entityplayermp.worldObj.setBlockWithNotify(x, y, z, 0);
    } else if (id == Block.tnt.blockID) {
      tnt.onBlockDestroyedByPlayer(entityplayermp.worldObj, x, y, z, 1);
      entityplayermp.worldObj.setBlockWithNotify(x, y, z, 0);
    }
  }

  public boolean DispenseEntity(World world, double d, double d1, double d2, int i, int j, ItemStack itemstack) {
    PlasticCraftCore.DispenseEntity(world, d, d1, d2, i, j, itemstack);
    
    return false;
  }
  
  public void OnTickInGame(MinecraftServer game) {
    enableShockAbsorbing(game);
    Stun.tick();
  }

  private void enableShockAbsorbing(MinecraftServer game) {   
    for (EntityPlayerMP player : (EntityPlayerMP[])(EntityPlayerMP[])game.configManager.playerEntities.toArray(new EntityPlayerMP[game.configManager.playerEntities.size()])) {
      ItemStack itemstack = player.inventory.armorInventory[0];
      if (itemstack != null && itemstack.itemID == PlasticCraftCore.armorFallBoots.shiftedIndex) {
        player.fallDistance = -1F;
        PlasticCraftCore.isWearingFallBoots = true;
      } else
      	PlasticCraftCore.isWearingFallBoots = false;
    }
  }

  public static void prepareProps() {
    props.getBoolean("c4Disabled", false);
  }
  
  public static boolean getIsJumping(EntityLiving entityliving) {
  	return entityliving.isJumping;
  }
    
  public static class Stun {
  	private static ArrayList shockedMobs = new ArrayList();
    private static ArrayList shockedMobsTime = new ArrayList();
    private static ArrayList shockedMobsCantTime = new ArrayList();
    private static ArrayList shockedMobsSpeed = new ArrayList();
    private static long lastTime = System.currentTimeMillis();
    private static double delta = 0.0D;
    
    static void tick() {
      delta = (double)(System.currentTimeMillis() - lastTime) / 1000D;
      lastTime = System.currentTimeMillis();
            
      for (int i=0; i<shockedMobs.size(); i++) {
        EntityLiving entityliving = (EntityLiving)shockedMobs.get(i);
        double d = ((Double)shockedMobsTime.get(i)).doubleValue();
        double d1 = ((Double)shockedMobsCantTime.get(i)).doubleValue();
        d -= delta;
                
        if (d <= -d1) {
          shockedMobs.remove(i);
          shockedMobsTime.remove(i);
          shockedMobsCantTime.remove(i);
          shockedMobsSpeed.remove(i);
          i--;
          continue;
        }
                
        if (d > 0.0D) {
          entityliving.motionX = 0.0D;
          entityliving.newPosY = 0.0D;
          entityliving.motionZ = 0.0D;
        } else {
          float f = ((Float)shockedMobsSpeed.get(i)).floatValue();
          entityliving.moveSpeed = f;
        }
         
        shockedMobsTime.set(i, Double.valueOf(d));
      }
    }

    public static void shockMob(EntityLiving entityliving, double d, double d1) {
      if (!shockedMobs.contains(entityliving)) {
        shockedMobs.add(entityliving);
        shockedMobsTime.add(Double.valueOf(d));
        shockedMobsCantTime.add(Double.valueOf(d1));
        shockedMobsSpeed.add(Float.valueOf(entityliving.moveSpeed));
        entityliving.moveSpeed = 0.0F;
      }
    }
  }
}