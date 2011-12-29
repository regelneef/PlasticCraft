package net.minecraft.src.plasticcraft;

import java.util.List;
import net.minecraft.src.*;

public class ItemC4Remote extends Item {
  public static BlockC4 c4 = (BlockC4)mod_PlasticCraft.blockC4;
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
      
      for (int j=0; j<list.size(); j++) {
        Entity entity = (Entity)list.get(j);
        if (!(entity instanceof EntityC4Primed)) continue;
      
        EntityC4Primed entityc4primed = (EntityC4Primed)entity;
        entityc4primed.setEntityDead();
        int k = entityc4primed.connectedCount;
        int l = mod_PlasticCraft.blockC4.tickRate();
        int i1;
      
        for (; k > 0; k -= i1) {
          i1 = k >= l ? l : k;
          entityplayer.dropItem(mod_PlasticCraft.blockC4.blockID, i1);
        }
      }
    } else if (itemstack.getItemDamage() == 1) { // detonator, handled by client
      return itemstack;
    }
    
    return itemstack;
  }

  public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
    if (itemstack.getItemDamage() == 0) {
      if (world.getBlockId(i, j, k) == mod_PlasticCraft.blockC4.blockID) {
        entityplayer.dropItem(mod_PlasticCraft.blockC4.blockID, 1);
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
  
  public String getItemNameIS(ItemStack itemstack) {
    if (itemstack.getItemDamage() == 1) return "plastic.detonator";
    return "plastic.defuser";
  }
}