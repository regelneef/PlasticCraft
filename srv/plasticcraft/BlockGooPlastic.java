package net.minecraft.src.plasticcraft;

import java.util.Random;
import net.minecraft.src.*;

public class BlockGooPlastic extends Block {
  public BlockGooPlastic(int i) {
    super(i, Material.sand);
    setHardness(0.5F);
    setResistance(500F);
    setStepSound(soundClothFootstep);
    setTickOnLoad(true);
  }

  public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
    float f = 0.0625F;
    return AxisAlignedBB.getBoundingBoxFromPool((float)i + f, j, (float)k + f, (float)(i + 1) - f, (float)(j + 1) - f, (float)(k + 1) - f);
  }

  public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
    if (l == Block.waterMoving.blockID || l == Block.waterStill.blockID) {
      int i1 = colors[world.rand.nextInt(colors.length)];
      world.setBlockAndMetadataWithNotify(i, j, k, mod_PlasticCraft.blockPlastic.blockID, i1);
    }
  }

  public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
    freeze(entity);
  }

  public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
    entity.setInWeb();
    freeze(entity);
  }

  public void freeze(Entity entity) {
    entity.attackEntityFrom(DamageSource.cactus, 1);
    if (entity instanceof EntityLiving) {
      EntityLiving entityliving = (EntityLiving)entity;
      mod_PlasticCraft.Stun.shockMob(entityliving, 6D, 4D);
    }
  }

  public int tickRate() {
    return 30;
  }

  public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
    return super.blockActivated(world, i, j, k, entityplayer);
  }

  public int quantityDropped(Random random) {
    return 1;
  }

  public static int colors[] = { 0, 1, 4, 7, 14, 15 };
}