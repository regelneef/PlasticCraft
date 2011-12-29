package net.minecraft.src.plasticcraft;

import java.util.Random;
import net.minecraft.src.*;

public class BlockPlasticMachine extends BlockContainer {
  public static int plasticIndex = 0;
  private static int microwaveFront = 8;
  private static int extractorFront = 9;
  private static int extractorTop = 10;
  public static int microwaveAnim = 255;
  private static boolean keepInventory = false;
  
  public BlockPlasticMachine(int i) {
    super(i, Material.sponge);
    setHardness(2.0F);
    setResistance(1500F);
    setStepSound(soundPowderFootstep);
    blockIndexInTexture = plasticIndex;
    setRequiresSelfNotify();
    setTickOnLoad(true);
  }

  public void onBlockAdded(World world, int i, int j, int k) {
    super.onBlockAdded(world, i, j, k);
    setDefaultDirection(world, i, j, k);
  }

  private void setDefaultDirection(World world, int i, int j, int k) {
    if (world.singleplayerWorld)
      return;
    
    int l = world.getBlockId(i, j, k - 1);
    int i1 = world.getBlockId(i, j, k + 1);
    int j1 = world.getBlockId(i - 1, j, k);
    int k1 = world.getBlockId(i + 1, j, k);
    int m = world.getBlockMetadata(i, j, k) & 8;
    byte byte0 = 3;
          
    if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
      byte0 = 1;
    if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
      byte0 = 0;
    if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
      byte0 = 3;
    if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
      byte0 = 2;
    
    byte0 |= m;
      
    world.setBlockMetadataWithNotify(i, j, k, byte0);
  }
  
  public int getBlockTextureFromSideAndMetadata(int side, int meta) {
    int type = meta & 8;
    if (side == 3 && type == 0) return microwaveFront;
    if (side == 3 && type == 8) return extractorFront;
    if (side == 1 && type == 8) return extractorTop;
    
    return plasticIndex;
  }
  
  public int getLightValue(IBlockAccess iblockaccess, int i, int j, int k) {
    int meta = iblockaccess.getBlockMetadata(i, j, k);
    if ((meta & 4) == 4) return 13;
    return 0;
  }
  
  public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
    int type = world.getBlockMetadata(i, j, k) & 8;
    if (world.singleplayerWorld)
      return true;
    else {
      if (type == 0) {
    	  TileEntityMicrowave tileentity = (TileEntityMicrowave)world.getBlockTileEntity(i, j, k);
    	  ContainerMicrowave container = new ContainerMicrowave(entityplayer.inventory, tileentity);
        ModLoader.OpenGUI(entityplayer, 300, entityplayer.inventory, container);
        return true;
      } else {
      	TileEntityExtract tileentity = (TileEntityExtract)world.getBlockTileEntity(i, j, k);
        ContainerExtract container = new ContainerExtract(entityplayer.inventory, tileentity);
        ModLoader.OpenGUI(entityplayer, 301, entityplayer.inventory, container);
        return true;
      }
    }
  }

  public static void updateBlockState(boolean flag, World world, int i, int j, int k) {
    int m = world.getBlockMetadata(i, j, k);
    TileEntity tileentity = world.getBlockTileEntity(i, j, k);
    keepInventory = true;

    if (flag)
      m |= 4;
    else
      m &= 11;
        
    keepInventory = false;
        
    world.setBlockMetadataWithNotify(i, j, k, m);
        
    if (tileentity != null) {
      tileentity.validate();
      world.setBlockTileEntity(i, j, k, tileentity);
    }
  }
  
  public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
  	int m = world.getBlockMetadata(i, j, k) & 8;
    int l = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
    if (l == 0) world.setBlockMetadataWithNotify(i, j, k, 0 + m);
    if (l == 1) world.setBlockMetadataWithNotify(i, j, k, 3 + m);
    if (l == 2) world.setBlockMetadataWithNotify(i, j, k, 1 + m);
    if (l == 3) world.setBlockMetadataWithNotify(i, j, k, 2 + m);
  }
  
  public TileEntity getBlockEntity() {
    return null;
  }

  public TileEntity getBlockEntity(int m) {
    if ((m & 8) == 8)
      return new TileEntityExtract();
  	
    return new TileEntityMicrowave();
  }

  public void onBlockRemoval(World world, int i, int j, int k) {
  	int type = world.getBlockMetadata(i, j, k) & 8;
  	
    if (type == 0) {
      if (!keepInventory) {
        TileEntityMicrowave tileentity = (TileEntityMicrowave)world.getBlockTileEntity(i, j, k);
    	
        label0:
        for (int l=0; l < tileentity.getSizeInventory(); l++) {
          ItemStack itemstack = tileentity.getStackInSlot(l);
          if(itemstack == null)
            continue;
        
          float f = world.rand.nextFloat() * 0.8F + 0.1F;
          float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
          float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
        
          do {
            if (itemstack.stackSize <= 0)
              continue label0;
                    
            int i1 = world.rand.nextInt(21) + 10;
            if(i1 > itemstack.stackSize)
              i1 = itemstack.stackSize;
                    
            itemstack.stackSize -= i1;
            EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
            float f3 = 0.05F;
            entityitem.motionX = (float)world.rand.nextGaussian() * f3;
            entityitem.motionY = (float)world.rand.nextGaussian() * f3 + 0.2F;
            entityitem.motionZ = (float)world.rand.nextGaussian() * f3;
            world.spawnEntityInWorld(entityitem);
          } while (true);
        }
      }
    } else {
      if (!keepInventory) {
        TileEntityExtract tileentity = (TileEntityExtract)world.getBlockTileEntity(i, j, k);
    	
        label0:
        for (int l=0; l < tileentity.getSizeInventory(); l++) {
          ItemStack itemstack = tileentity.getStackInSlot(l);
          if(itemstack == null)
            continue;
        
          float f = world.rand.nextFloat() * 0.8F + 0.1F;
          float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
          float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
        
          do {
            if (itemstack.stackSize <= 0)
              continue label0;
                    
            int i1 = world.rand.nextInt(21) + 10;
            if(i1 > itemstack.stackSize)
              i1 = itemstack.stackSize;
                    
            itemstack.stackSize -= i1;
            EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
            float f3 = 0.05F;
            entityitem.motionX = (float)world.rand.nextGaussian() * f3;
            entityitem.motionY = (float)world.rand.nextGaussian() * f3 + 0.2F;
            entityitem.motionZ = (float)world.rand.nextGaussian() * f3;
            world.spawnEntityInWorld(entityitem);
          } while (true);
        }
      }
    }
    
    super.onBlockRemoval(world, i, j, k);
  }
  
  public int quantityDropped(Random random) {
    return 1;
  }
  
  protected int damageDropped(int i) {
    return i & 8;
  }
}