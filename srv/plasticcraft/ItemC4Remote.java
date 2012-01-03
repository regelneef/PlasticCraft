package net.minecraft.src.plasticcraft;

import java.util.List;
import net.minecraft.src.*;
import net.minecraft.src.plasticcraft.core.Item_PC;

public class ItemC4Remote extends Item_PC {
  public static BlockC4 c4 = (BlockC4)PlasticCraftCore.blockC4;
  public static BlockTNT tnt = (BlockTNT)Block.tnt;

  public ItemC4Remote(int i) {
    super(i);
    maxStackSize = 1;
    setMaxDamage(0);
    setHasSubtypes(true);
  }

  public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
    if (itemstack.getItemDamage() == 0) { // defuser
      List list = world.loadedEntityList;
      int i = 0;
    	
      for (int j=0; j<list.size(); j++) {
        Entity entity = (Entity)list.get(j);
        if (!(entity instanceof EntityC4Primed)) continue;
      
        EntityC4Primed entityc4primed = (EntityC4Primed)entity;
        entityc4primed.setEntityDead();
        int k = entityc4primed.connectedCount;
        int l = PlasticCraftCore.blockC4.tickRate();
        int i1;
      
        for (; k > 0; k -= i1) {
          i1 = k >= l ? l : k;
          entityplayer.dropItem(PlasticCraftCore.blockC4.blockID, i1);
        }
      }
    } else if (itemstack.getItemDamage() == 1) { // detonator
      MovingObjectPosition mop = rayTrace(world, 150.0D, 1.0F, entityplayer);
    	
      if (PlasticCraftCore.proxy.isServer(world))
        return itemstack;
      
      if (mop != null && mop.typeOfHit == EnumMovingObjectType.TILE) {
        int id = world.getBlockId(mop.blockX, mop.blockY, mop.blockZ);
        
        if (PlasticCraftCore.proxy.isSingleplayer(world)) {
          if (id == PlasticCraftCore.blockC4.blockID) {
            c4.onBlockDestroyedByPlayer(world, mop.blockX, mop.blockY, mop.blockZ, 1);
            world.setBlockWithNotify(mop.blockX, mop.blockY, mop.blockZ, 0);
          } else if (id == Block.tnt.blockID) {
        	  tnt.onBlockDestroyedByPlayer(world, mop.blockX, mop.blockY, mop.blockZ, 1);
            world.setBlockWithNotify(mop.blockX, mop.blockY, mop.blockZ, 0);
          }
        } else if (PlasticCraftCore.proxy.isClient(world)) {
        	Packet230ModLoader packet = new Packet230ModLoader();
          packet.modId = PlasticCraftCore.proxy.getModId();
          packet.dataFloat = new float[3];
          packet.dataFloat[0] = mop.blockX;
          packet.dataFloat[1] = mop.blockY;
          packet.dataFloat[2] = mop.blockZ;
          PlasticCraftCore.proxy.sendPacketToServer(packet);
        }
      }
    }
    
    return itemstack;
  }

  public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
    if (itemstack.getItemDamage() == 0) {
      if (world.getBlockId(i, j, k) == PlasticCraftCore.blockC4.blockID) {
        entityplayer.dropItem(PlasticCraftCore.blockC4.blockID, 1);
        world.setBlockAndMetadataWithNotify(i, j, k, 0, 0);
        itemstack.damageItem(1, entityplayer);
        return true;
      } else if (world.getBlockId(i, j, k) == Block.tnt.blockID) {
        entityplayer.dropItem(Block.tnt.blockID, 1);
        world.setBlockAndMetadataWithNotify(i, j, k, 0, 0);
        itemstack.damageItem(1, entityplayer);
        return true;
      }
    }
    
    return false;
  }
  
  private MovingObjectPosition rayTrace(World world, double d, float f, EntityLiving entity) {
    Vec3D vec3d = Vec3D.createVector(entity.posX, entity.posY, entity.posZ);
    Vec3D vec3d1 = entity.getLook(f);
    Vec3D vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
    return world.rayTraceBlocks(vec3d, vec3d2);
  }
  
  public String getItemNameIS(ItemStack itemstack) {
    if (itemstack.getItemDamage() == 1) return "plastic.detonator";
    return "plastic.defuser";
  }
}