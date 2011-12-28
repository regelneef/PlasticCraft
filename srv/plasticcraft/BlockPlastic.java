package net.minecraft.src.plasticcraft;

import java.util.Random;
import net.minecraft.src.*;

public class BlockPlastic extends Block {
  public BlockPlastic(int i) {
    super(i, Material.sponge);
    setHardness(1.5F);
    setResistance(1500F);
    setStepSound(soundWoodFootstep);
  }

  public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
    if (l == Block.fire.blockID || l == Block.lavaMoving.blockID || l == Block.lavaStill.blockID)
      world.setBlockWithNotify(i, j, k, mod_PlasticCraft.blockPlasticGoo.blockID);
  }

  public int quantityDropped(Random random) {
    return 1;
  }

  protected int damageDropped(int i) {
    return i;
  }

  public static void recipes() {
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 0), new Object[] {
      new ItemStack(Item.dyePowder, 1, 15), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 1), new Object[] {
      new ItemStack(Item.dyePowder, 1, 14), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 2), new Object[] {
      new ItemStack(Item.dyePowder, 1, 13), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 3), new Object[] {
      new ItemStack(Item.dyePowder, 1, 12), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 4), new Object[] {
      new ItemStack(Item.dyePowder, 1, 11), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 5), new Object[] {
      new ItemStack(Item.dyePowder, 1, 10), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 6), new Object[] {
      new ItemStack(Item.dyePowder, 1, 9), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 7), new Object[] {
      new ItemStack(Item.dyePowder, 1, 8), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 8), new Object[] {
      new ItemStack(Item.dyePowder, 1, 4), new ItemStack(Item.dyePowder, 1, 6), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 9), new Object[] {
      new ItemStack(Item.dyePowder, 1, 6), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 10), new Object[] {
      new ItemStack(Item.dyePowder, 1, 5), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 11), new Object[] {
      new ItemStack(Item.dyePowder, 1, 4), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 12), new Object[] {
      new ItemStack(Item.dyePowder, 1, 3), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 13), new Object[] {
      new ItemStack(Item.dyePowder, 1, 2), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 14), new Object[] {
      new ItemStack(Item.dyePowder, 1, 1), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
    ModLoader.AddShapelessRecipe(new ItemStack(mod_PlasticCraft.blockPlastic, 1, 15), new Object[] {
      new ItemStack(Item.dyePowder, 1, 0), new ItemStack(Item.itemsList[mod_PlasticCraft.blockPlastic.blockID], 1, 0)
    });
  }
}