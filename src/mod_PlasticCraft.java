package net.minecraft.src;

import java.io.File;
import java.io.PrintStream;
import java.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.src.forge.*;
import net.minecraft.src.plasticcraft.*;
import net.minecraft.src.plasticcraft.core.*;
import net.minecraft.src.plasticcraft.PlasticCraftCore.EnumPlasticMachine;

import org.lwjgl.opengl.GL11;

public class mod_PlasticCraft extends BaseModMp {
  public String getVersion() { return PlasticCraftCore.version; }
  private static String getAppdata() { return Minecraft.getMinecraftDir().getPath(); }
  public static Props props = new Props(new File(getAppdata() + "/config/" + "mod_PlasticCraft.props").getPath());
  public static void console(String s) { System.out.println("[PlasticCraft] " + s); }
  public static mod_PlasticCraft instance;
  
  public mod_PlasticCraft() {
  	instance = this;
  }
  
  public void ModsLoaded() {
  	MinecraftForgeClient.preloadTexture(PlasticCraftCore.itemSheet);
    MinecraftForgeClient.preloadTexture(PlasticCraftCore.blockSheet);
  }
  
  public void load() {
  	PlasticCraftCore.init(new ClientProxy());
  	
    nameItems();
    console("Adding item names.");

    ModLoader.SetInGameHook(this, true, false);
    
    ModLoaderMp.RegisterGUI(this, PlasticCraftCore.guiMicrowaveId);
    ModLoaderMp.RegisterGUI(this, PlasticCraftCore.guiExtractorId);
    ModLoaderMp.RegisterGUI(this, PlasticCraftCore.guiDisassemblerId);
    
    ModLoaderMp.RegisterNetClientHandlerEntity(EntityC4Primed.class, PlasticCraftCore.entityC4NetId);
    ModLoaderMp.RegisterNetClientHandlerEntity(EntityPlasticBoat.class, PlasticCraftCore.entityPlasticBoatNetId);
  }
  
  public void nameItems() {
    // Plastic Blocks
    for (int i=0; i<16; i++)
      ModLoader.AddName(new ItemStack(PlasticCraftCore.blockPlastic, 1, i), BlockPlastic.plasticNames[i] + " Plastic");
    // Plexiglass Blocks
    ModLoader.AddName(new ItemStack(PlasticCraftCore.blockPlexiglass, 1, 0), "Plexiglass");
    ModLoader.AddName(new ItemStack(PlasticCraftCore.blockPlexiglass, 1, 1), "Glowing Plexiglass");
    // Machines
    ModLoader.AddName(new ItemStack(PlasticCraftCore.blockPlasticMachine, 1, PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Microwave)), "Microwave Oven");
    ModLoader.AddName(new ItemStack(PlasticCraftCore.blockPlasticMachine, 1, PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Extractor)), "Extracting Furnace");
    ModLoader.AddName(new ItemStack(PlasticCraftCore.blockPlasticMachine, 1, PlasticCraftCore.machineMetadataMappings.get(EnumPlasticMachine.Disassembler)), "Disassembler");
    // Other blocks w/o metadata
    ModLoader.AddName(PlasticCraftCore.blockPlasticGoo, "Gooey Plastic");
    ModLoader.AddName(PlasticCraftCore.blockC4, "C4 Explosive");
    ModLoader.AddName(PlasticCraftCore.blockTap, "Latex Extractor");
    ModLoader.AddName(PlasticCraftCore.blockTrampoline, "Trampoline");
    ModLoader.AddName(PlasticCraftCore.blockAccelerator, "Accelerator");
    // Items
    ModLoader.AddName(PlasticCraftCore.itemPlastic, "Plastic Ball");
    ModLoader.AddName(PlasticCraftCore.itemPlasticClear, "Clear Plastic Ball");
    ModLoader.AddName(PlasticCraftCore.itemPlasticStick, "Plastic Stick");
    ModLoader.AddName(PlasticCraftCore.itemWoodDust, "Wood Flour");
    ModLoader.AddName(PlasticCraftCore.itemMould, "Mould");
    ModLoader.AddName(PlasticCraftCore.itemMouldFull, "Filled Mould");
    ModLoader.AddName(PlasticCraftCore.itemSynthString, "Sythetic String");
    ModLoader.AddName(PlasticCraftCore.itemIntegratedCircuit, "Integrated Circuit");
    ModLoader.AddName(PlasticCraftCore.itemBowlGelatin, "Bowl of Gelatin");
    ModLoader.AddName(PlasticCraftCore.itemGelatin, "Gelatin Powder");
    ModLoader.AddName(PlasticCraftCore.itemPlasticGoo, "Plastic Goo");
    ModLoader.AddName(PlasticCraftCore.itemRubber, "Rubber Ball");
    ModLoader.AddName(PlasticCraftCore.itemDuctTape, "Duct Tape");
    ModLoader.AddName(PlasticCraftCore.itemPlexidoor, "Plexiglass Door");
    ModLoader.AddName(PlasticCraftCore.itemBattery, "Battery");
    ModLoader.AddName(PlasticCraftCore.itemSilicon, "Rough Silicon");
    ModLoader.AddName(PlasticCraftCore.itemPlasticBoat, "Plastic Boat");
    ModLoader.AddName(new ItemStack(PlasticCraftCore.itemC4Remote, 1, 0), "Handheld C4 Defuser");
    ModLoader.AddName(new ItemStack(PlasticCraftCore.itemC4Remote, 1, 1), "Handheld C4 Detonator");
    ModLoader.AddName(PlasticCraftCore.itemSynthCloth, "Synthetic Fiber");
    ModLoader.AddName(PlasticCraftCore.itemRope, "Synthetic Rope");
    ModLoader.AddName(PlasticCraftCore.itemPlasticBucket, "Plastic Bucket");
    ModLoader.AddName(PlasticCraftCore.itemPlasticBucketW, "Plastic Water Bucket");
    ModLoader.AddName(PlasticCraftCore.itemPlasticBucketM, "Plastic Milk Bucket");
    ModLoader.AddName(PlasticCraftCore.itemPlasticBucketL, "Plastic Latex Bucket");
    ModLoader.AddName(PlasticCraftCore.itemPlasticBottle, "Plastic Bottle");
    ModLoader.AddName(PlasticCraftCore.itemPlasticBottleW, "Water Bottle");
    ModLoader.AddName(PlasticCraftCore.itemPlasticBottleM, "Milk Bottle");
    ModLoader.AddName(PlasticCraftCore.itemIronBucketL, "Iron Latex Bucket");
    ModLoader.AddName(PlasticCraftCore.itemNeedle, "Needle");
    ModLoader.AddName(PlasticCraftCore.itemNeedleHealth, "Health Needle");
    ModLoader.AddName(PlasticCraftCore.itemJello, "Jello");
    ModLoader.AddName(PlasticCraftCore.armorNightGoggles, "Night-Vision Goggles");
    ModLoader.AddName(PlasticCraftCore.armorKevlarVest, "Kevlar Vest");
    ModLoader.AddName(PlasticCraftCore.armorKevlarLegs, "Kevlar Pants");
    ModLoader.AddName(PlasticCraftCore.armorFallBoots, "Shock-Absorbing Boots");
    ModLoader.AddName(PlasticCraftCore.toolPlasticShovel, "Plastic Shovel");
    ModLoader.AddName(PlasticCraftCore.toolPlasticPickaxe, "Plastic Pickaxe");
    ModLoader.AddName(PlasticCraftCore.toolPlasticAxe, "Plastic Axe");
  }
  
  public void AddRenderer(Map map) {
    map.put(EntityC4Primed.class, new RenderC4Primed());
    map.put(EntityPlasticBoat.class, new RenderPlasticBoat());
  }
  
  public void RegisterAnimation(Minecraft minecraft) {
    ModLoader.addAnimation(new TextureFrameAnimFX(BlockPlasticMachine.microwaveAnim, PlasticCraftCore.modDir + "blockMicrowaveAnim.png"));
  }
  
  public GuiScreen HandleGUI(int invType) {
  	InventoryPlayer inventory = ModLoader.getMinecraftInstance().thePlayer.inventory;
  	TileEntityMicrowave microwave = new TileEntityMicrowave();
  	TileEntityExtractor extractor = new TileEntityExtractor();
  	TileEntityDisassembler uncrafter = new TileEntityDisassembler();
  	
  	if (invType == PlasticCraftCore.guiMicrowaveId)
  		return new GuiMicrowave(inventory, microwave);
  	else if (invType == PlasticCraftCore.guiExtractorId)
  		return new GuiExtractor(inventory, extractor);
  	else if (invType == PlasticCraftCore.guiDisassemblerId)
  		return new GuiDisassembler(inventory, uncrafter);
  	
  	return null;
  }
  
  public void HandleTileEntityPacket(int i, int j, int k, int l, int ai[], float af[], String as[]) {
    World world = ModLoader.getMinecraftInstance().theWorld;
    TileEntity te = world.getBlockTileEntity(i, j, k);
    if (te != null && te instanceof TileEntityPlastic) {
      ((TileEntityPlastic)te).setDirection((short)ai[0]); // direction
      ((TileEntityPlastic)te).setActiveByState(ai[1]); // active state
	  }
  }
  
  public boolean DispenseEntity(World world, double d, double d1, double d2, int i, int j, ItemStack itemstack) {
    PlasticCraftCore.DispenseEntity(world, d, d1, d2, i, j, itemstack);
    
    return false;
  }
  
  public boolean OnTickInGame(float f, Minecraft minecraft) {
    if (minecraft.currentScreen == null)
      renderNightvisionOverlay(minecraft);

    enableShockAbsorbing(minecraft);
    Stun.tick();
    return true;
  }

  private void enableShockAbsorbing(Minecraft minecraft) {
    ItemStack itemstack = minecraft.thePlayer.inventory.armorItemInSlot(0);
    
    if (itemstack != null && itemstack.itemID == PlasticCraftCore.armorFallBoots.shiftedIndex) {
      minecraft.thePlayer.fallDistance = -1F;
      PlasticCraftCore.isWearingFallBoots = true;
    } else
    	PlasticCraftCore.isWearingFallBoots = false;
  }

  private void renderNightvisionOverlay(Minecraft minecraft) {
    ItemStack itemstack = minecraft.thePlayer.inventory.armorItemInSlot(3);
        
    if (!minecraft.gameSettings.hideGUI && itemstack != null && itemstack.itemID == PlasticCraftCore.armorNightGoggles.shiftedIndex)
      renderTextureOverlay(minecraft, "%blur%" + PlasticCraftCore.modDir + "guiNightVision" + PlasticCraftCore.nightvisionStyle + ".png", 1.0F);
  }

  private void renderTextureOverlay(Minecraft minecraft, String s, float f) {
    ScaledResolution scaledresolution = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
    int i = scaledresolution.getScaledWidth();
    int j = scaledresolution.getScaledHeight();
    GL11.glEnable(3042 /*GL_BLEND*/);
    GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
    GL11.glDepthMask(false);
    GL11.glBlendFunc(770, 771);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
    GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
    GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, minecraft.renderEngine.getTexture(s));
    Tessellator tessellator = Tessellator.instance;
    tessellator.startDrawingQuads();
    tessellator.addVertexWithUV(0.0D, j, -90D, 0.0D, 1.0D);
    tessellator.addVertexWithUV(i, j, -90D, 1.0D, 1.0D);
    tessellator.addVertexWithUV(i, 0.0D, -90D, 1.0D, 0.0D);
    tessellator.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
    tessellator.draw();
    GL11.glDepthMask(true);
    GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
    GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
  }
  
  public static boolean getIsJumping(EntityLiving entityliving) {
  	return entityliving.isJumping;
  }
  
  public static void prepareProps() {
  	props.getString("nightvisionStyle", "SDK");
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