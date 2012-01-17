package net.minecraft.src.plasticcraft;

import java.util.Random;
import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.plasticcraft.PlasticCraftCore.EnumPlasticMachine;

public class BlockPlasticMachine extends BlockContainer implements ITextureProvider {
  public static int[][] textures = new int[16][6];
  public static int microwaveFront = 8;
  public static int microwaveAnim = 255;
  public static int extractorFront = 9;
  public static int extractorTop = 10;
  public static int disassemblerFront = 11;
  
  public BlockPlasticMachine(int i) {
    super(i, Material.sponge);
    setHardness(2.0F);
    setResistance(1500F);
    setStepSound(soundMetalFootstep);
    setBlockName("pPlasticMachine");
    setRequiresSelfNotify();
  }
  
  public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int side) {
    int meta = iblockaccess.getBlockMetadata(i, j, k);
    int direction = getDirection(iblockaccess, i, j, k, false);
    boolean active = isActive(iblockaccess, i, j, k);
    
    if (meta == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Microwave)) {
      if (side != direction) return 0; // all else
      if (active) return microwaveAnim; // front, when active
    	
      return microwaveFront; // front
    } else if (meta == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Extractor) || meta == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.AdvExtractor) ) {
      if (side == 1) return extractorTop; // top
      if (side != direction) return 0; // all else
    	
      return extractorFront; // front
    } else if (meta == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Disassembler)) {
      if (side == 1) return extractorTop; // top
      if (side != direction) return 0; // all else
    	
      return disassemblerFront; // front
    }
  	
    return 0;
  }

  public int getBlockTextureFromSideAndMetadata(int side, int meta) {
    return textures[meta][side];
  }
  
  public int getLightValue(IBlockAccess iblockaccess, int i, int j, int k) {
    if (isActive(iblockaccess, i, j, k)) return 13;
    return 0;
  }

  public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
    int meta = world.getBlockMetadata(i, j, k);
    if (world.singleplayerWorld)
      return true;
    else {
      if (meta == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Microwave)) {
        TileEntityMicrowave tileentity = (TileEntityMicrowave)world.getBlockTileEntity(i, j, k);
        ContainerMicrowave container = new ContainerMicrowave(entityplayer.inventory, tileentity);
        ModLoader.OpenGUI(entityplayer, PlasticCraftCore.guiMicrowaveId, entityplayer.inventory, container);
        return true;
      } else if (meta == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Extractor)) {
      	TileEntityExtractor tileentity = (TileEntityExtractor)world.getBlockTileEntity(i, j, k);
        ContainerExtractor container = new ContainerExtractor(entityplayer.inventory, tileentity);
        ModLoader.OpenGUI(entityplayer, PlasticCraftCore.guiExtractorId, entityplayer.inventory, container);
        return true;
      } else if (meta == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.AdvExtractor)) {
      	TileEntityAdvExtractor tileentity = (TileEntityAdvExtractor)world.getBlockTileEntity(i, j, k);
        ContainerAdvExtractor container = new ContainerAdvExtractor(entityplayer.inventory, tileentity);
        ModLoader.OpenGUI(entityplayer, PlasticCraftCore.guiAdvExtractorId, entityplayer.inventory, container);
        return true;
      } else if (meta == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Disassembler)) {
      	TileEntityDisassembler tileentity = (TileEntityDisassembler)world.getBlockTileEntity(i, j, k);
        ContainerDisassembler container = new ContainerDisassembler(entityplayer.inventory, tileentity);
        ModLoader.OpenGUI(entityplayer, PlasticCraftCore.guiDisassemblerId, entityplayer.inventory, container);
        return true;
      } else 
        return true;
    }
  }

  public static void updateBlockState(boolean flag, World world, int i, int j, int k) {
    TileEntityPlastic te = (TileEntityPlastic)world.getBlockTileEntity(i, j, k);

    te.setActive(flag);
    
    if (te != null) {
      te.validate();
      world.setBlockTileEntity(i, j, k, te);
    }
  }
  
  public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
    TileEntityPlastic te = (TileEntityPlastic)world.getBlockTileEntity(i, j, k);

    int l = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;

    switch (l) {
    case 0:
      te.setDirection((short)2); // -Z
      break;
    case 1:
      te.setDirection((short)5); // +X
      break;
    case 2:
      te.setDirection((short)3); // +Z
      break;
    case 3:
      te.setDirection((short)4); // -X
    }
  }
  
  public TileEntity getBlockEntity() {
    return null;
  }

  public TileEntity getBlockEntity(int m) {
    if (m == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Microwave)) return new TileEntityMicrowave();
    if (m == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Extractor)) return new TileEntityExtractor();
    if (m == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.AdvExtractor)) return new TileEntityAdvExtractor();
    if (m == PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Disassembler)) return new TileEntityDisassembler();
    return getBlockEntity();
  }
  
  public int quantityDropped(Random random) {
    return 1;
  }
  
  protected int damageDropped(int i) {
    return i;
  }
  
  private static boolean isActive(IBlockAccess iblockaccess, int i, int j, int k) {
    TileEntity te = iblockaccess.getBlockTileEntity(i, j, k);
  	
    if (te instanceof TileEntityPlastic)
      return ((TileEntityPlastic)te).getActive();
  	
    return false;
  }
  
  private static short getDirection(IBlockAccess iblockaccess, int i, int j, int k, boolean opp) {
    TileEntity te = iblockaccess.getBlockTileEntity(i, j, k);
  	
    if (te instanceof TileEntityPlastic) {
      if (!opp) return ((TileEntityPlastic)te).getDirection();
      else return ((TileEntityPlastic)te).getOppositeDirection();
    }
  	
    return 3;
  }
  
  public void onBlockRemoval(World world, int i, int j, int k) {
    TileEntity te = world.getBlockTileEntity(i, j, k);
  	
    if (te == null || !(te instanceof IInventory))
			return;
  	
    IInventory inventory = ((IInventory)te);
  	
    label0:
    for (int l=0; l < inventory.getSizeInventory(); l++) {
      ItemStack itemstack = inventory.getStackInSlot(l);
      if(itemstack == null)
        continue;
        
      float f = world.rand.nextFloat() * 0.8F + 0.1F;
      float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
      float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
        
      do {
        if (itemstack.stackSize <= 0)
          continue label0;
                    
        int i1 = world.rand.nextInt(21) + 10;
        if (i1 > itemstack.stackSize)
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
    
    super.onBlockRemoval(world, i, j, k);
  }
  
  public String getTextureFile() {
    return PlasticCraftCore.blockSheet;
  }
  
  public static void setupTextures() {
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Microwave)][0] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Microwave)][1] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Microwave)][2] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Microwave)][3] = microwaveFront;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Microwave)][4] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Microwave)][5] = 0;
  	
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Extractor)][0] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Extractor)][1] = extractorTop;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Extractor)][2] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Extractor)][3] = extractorFront;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Extractor)][4] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Extractor)][5] = 0;
    
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.AdvExtractor)][0] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.AdvExtractor)][1] = extractorTop;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.AdvExtractor)][2] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.AdvExtractor)][3] = extractorFront;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.AdvExtractor)][4] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.AdvExtractor)][5] = 0;
  	
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Disassembler)][0] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Disassembler)][1] = extractorTop;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Disassembler)][2] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Disassembler)][3] = disassemblerFront;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Disassembler)][4] = 0;
    BlockPlasticMachine.textures[PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Disassembler)][5] = 0;
  }
}