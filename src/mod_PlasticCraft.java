package net.minecraft.src;

import java.io.File;
import java.io.PrintStream;
import java.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.src.forge.*;
import net.minecraft.src.plasticcraft.*;
import org.lwjgl.opengl.GL11;

public class mod_PlasticCraft extends BaseModMp {
  public String getVersion() { return "v2.4 (for 1.0.0)"; }
  public static String modDir = "/plasticcraft/images/";
  private static String getAppdata() { return Minecraft.getMinecraftDir().getPath(); }
  private static Props props = new Props(new File(getAppdata() + "/config/" + "mod_PlasticCraft.props").getPath());
  private static void console(String s) { System.out.println("[PlasticCraft] " + s); }
  private static int iOff = 256; // item id offset
  public static String itemSheet = modDir + "pc_items.png"; // item sprite sheet
  public static String blockSheet = modDir + "pc_terrain.png"; // block sprite sheet
  public static mod_PlasticCraft instance;
  
  // Handlers
  private net.minecraft.src.plasticcraft.HandlerOre oreHandler = new HandlerOre();
  private net.minecraft.src.plasticcraft.HandlerBucket bucketHandler = new HandlerBucket();
  
  // Enums
  private static EnumToolMaterial PLASTIC = EnumHelper.addToolMaterial("Plastic", 1, 400, 3F, 0, 5);
  private static EnumArmorMaterial KEVLAR = EnumHelper.addArmorMaterial("Kevlar", 18, new int[] {0, 8, 6, 0}, 12);
  private static EnumArmorMaterial UTILITY = EnumHelper.addArmorMaterial("Utility", 0, new int[] {0, 0, 0, 0}, 5);

  static { prepareProps(); console("Reading /config/PlasticCraft.props"); }
  // Blocks
  public static final Block blockPlastic = new BlockPlastic(props.getInt("blockPlastic"));
  public static final Block blockPlasticGoo = new BlockGooPlastic(props.getInt("blockPlasticGoo"));
  public static final Block blockC4 = new BlockC4(props.getInt("blockC4"));
  public static final Block blockPlexiglass = new BlockPlexiglass(props.getInt("blockPlexiglass"));
  public static final Block blockPlexidoor = new BlockPlasticDoor(props.getInt("blockPlexiglassDoor"));
  public static final Block blockPlasticMachine = new BlockPlasticMachine(props.getInt("blockPlasticMachine"));
  public static final Block blockTap = new BlockTap(props.getInt("blockLatexTap"));
  public static final Block blockTrampoline = new BlockTrampoline(props.getInt("blockTrampoline"));
  public static final Block blockAccelerator = new BlockAccelerator(props.getInt("blockAccelerator"));
  public static final Block blockRope = new BlockRope(props.getInt("blockRope"));
  // Items
  public static final Item itemPlastic = new Item_PC(props.getInt("itemPlasticBall") - iOff).setIconIndex(0).setItemName("pPlasticBall");
  public static final Item itemPlasticClear = new Item_PC(props.getInt("itemClearBall") - iOff).setIconIndex(1).setItemName("pClearBall");
  public static final Item itemPlasticStick = new Item_PC(props.getInt("itemPlasticStick") - iOff).setIconIndex(2).setItemName("pPlasticStick");
  public static final Item itemMould = new Item_PC(props.getInt("itemMould") - iOff).setIconIndex(3).setItemName("pMould");
  public static final Item itemMouldFull = new Item_PC(props.getInt("itemFilledMould") - iOff).setIconIndex(4).setItemName("pFilledMould");
  public static final Item itemWoodDust = new Item_PC(props.getInt("itemWoodFlour") - iOff).setIconIndex(5).setItemName("pWoodFlour");
  public static final Item itemSynthString = new Item_PC(props.getInt("itemPlasticString") - iOff).setIconIndex(6).setItemName("pPlasticString");
  public static final Item itemIntegratedCircuit = new Item_PC(props.getInt("itemSiliconChip") - iOff).setIconIndex(7).setItemName("pSiliconChip");
  public static final Item itemBowlGelatin = new Item_PC(props.getInt("itemGelatinBowl") - iOff).setIconIndex(8).setItemName("pGelatinBowl");
  public static final Item itemGelatin = new Item_PC(props.getInt("itemGelatin") - iOff).setIconIndex(9).setItemName("pGelatin");
  public static final Item itemPlasticGoo = new Item_PC(props.getInt("itemGooPlastic") - iOff).setIconIndex(10).setItemName("pGooPlastic");
  public static final Item itemRubber = new Item_PC(props.getInt("itemRubberBall") - iOff).setIconIndex(11).setItemName("pRubberBall");
  public static final Item itemDuctTape = new Item_PC(props.getInt("itemTape") - iOff).setIconIndex(12).setItemName("pTape");
  public static final Item itemPlexidoor = new ItemPlasticDoor(props.getInt("itemPlexiglassDoor") - iOff).setIconIndex(13).setItemName("pItemPlasticDoor");
  public static final Item itemBattery = new Item_PC(props.getInt("itemBattery") - iOff).setIconIndex(14).setItemName("pBattery");
  public static final Item itemSilicon = new Item_PC(props.getInt("itemSilicon") - iOff).setIconIndex(15).setItemName("pSilicon");
  public static final Item itemPlasticBoat = new ItemPlasticBoat(props.getInt("itemPlasticBoat") - iOff).setIconIndex(16).setItemName("pBoat");
  public static final Item itemC4Remote = new ItemC4Remote(props.getInt("itemC4Remote") - iOff).setIconIndex(17).setItemName("pC4Remote");
  public static final Item itemSynthCloth = new Item_PC(props.getInt("itemSynthCloth") - iOff).setIconIndex(35).setItemName("pPlasticCloth");
  public static final Item itemRope = new ItemRope(props.getInt("itemRope") - iOff).setIconIndex(36).setItemName("pRope");
  // Foods and Bucket/Bottles
  public static final Item itemPlasticBucket = new ItemPlasticBucket(props.getInt("itemPlasticBucket") - iOff, 0).setIconIndex(18).setItemName("pPlasticBucket");
  public static final Item itemPlasticBucketW = new ItemPlasticBucket(props.getInt("itemPlasticWaterBucket") - iOff, Block.waterMoving.blockID).setIconIndex(19).setItemName("pPlasticWaterBucket").setContainerItem(itemPlasticBucket);
  public static final Item itemPlasticBucketM = new ItemPlasticBucket(props.getInt("itemPlasticMilkBucket") - iOff, -1).setIconIndex(20).setItemName("pPlasticMilkBucket").setContainerItem(itemPlasticBucket);
  public static final Item itemPlasticBucketL = new Item_PC(props.getInt("itemPlasticLatexBucket") - iOff).setIconIndex(21).setItemName("pPlasticLatexBucket");
  public static final Item itemPlasticBottle = new ItemPlasticBucket(props.getInt("itemEmptyBottle") - iOff, 0).setIconIndex(22).setItemName("pEmptyBottle");
  public static final Item itemPlasticBottleW = new ItemConsumable(props.getInt("itemWaterBottle") - iOff, 3, 0.8F, false).setIconIndex(23).setItemName("pWaterBottle");
  public static final Item itemPlasticBottleM = new ItemConsumable(props.getInt("itemMilkBottle") - iOff, 6, 0.6F, true).setIconIndex(24).setItemName("pMilkBottle");
  public static final Item itemIronBucketL = new Item_PC(props.getInt("itemIronLatexBucket") - iOff).setIconIndex(37).setItemName("pIronLatexBucket");
  public static final Item itemNeedle = new Item_PC(props.getInt("itemNeedle") - iOff).setIconIndex(25).setItemName("pNeedle");
  public static final Item itemNeedleHealth = new ItemConsumable(props.getInt("itemRedNeedle") - iOff, 0, 0, true).setIconIndex(26).setItemName("pHealthNeedle");
  public static final Item itemJello = new ItemFood_PC(props.getInt("itemJello") - iOff, 6, 0.7F, false).setIconIndex(34).setItemName("pJello");
  // Tools and Armor
  public static final Item armorNightGoggles = new ItemArmor_PC(props.getInt("armorNightVisionGoggles") - iOff, UTILITY, ModLoader.AddArmor("NVGoggles"), 0, false).setIconIndex(27).setItemName("pNVGoggles");
  public static final Item armorKevlarVest = new ItemArmor_PC(props.getInt("armorKevlarVest") - iOff, KEVLAR, ModLoader.AddArmor("Kevlar"), 1, true).setIconIndex(28).setItemName("pKevlarVest");
  public static final Item armorKevlarLegs = new ItemArmor_PC(props.getInt("armorKevlarLegs") - iOff, KEVLAR, ModLoader.AddArmor("Kevlar"), 2, true).setIconIndex(29).setItemName("pKevlarLegs");
  public static final Item armorFallBoots = new ItemArmor_PC(props.getInt("armorFallDampeningBoots") - iOff, UTILITY, ModLoader.AddArmor("FallDampener"), 3, false).setIconIndex(30).setItemName("pFallDampener");
  public static final Item toolPlasticShovel = new ItemPlasticSpade(props.getInt("toolPlasticShovel") - iOff, PLASTIC).setIconIndex(31).setItemName("pShovel");
  public static final Item toolPlasticPickaxe = new ItemPlasticPickaxe(props.getInt("toolPlasticPickaxe") - iOff, PLASTIC).setIconIndex(32).setItemName("pPickaxe");
  public static final Item toolPlasticAxe = new ItemPlasticAxe(props.getInt("toolPlasticAxe") - iOff, PLASTIC).setIconIndex(33).setItemName("pAxe");
  
  // Entity / GUI ids
  public static int entityC4Id = props.getInt("entityC4ID");
  public static int entityC4NetId = props.getInt("entityC4NetID");
  public static int entityPlasticBoatId = props.getInt("entityPlasticBoatID");
  public static int entityPlasticBoatNetId = props.getInt("entityPlasticBoatNetID");
  public static int guiMicrowaveId = props.getInt("guiMicrowaveID");
  public static int guiExtractorId = props.getInt("guiExtractorID");
  
  // Booleans
  public static int c4Power = props.getInt("c4Power");
  public static int c4Fuse = props.getInt("c4Fuse") * 20;
  public static boolean c4Enhanced = props.getBoolean("c4Enhanced");
  private static String nightvisionStyle = props.getString("nightvisionStyle");
  public static boolean isWearingFallBoots;
  static { props.save(); }
  
  // Repair Lists
  private static ArrayList class1 = new ArrayList(Arrays.asList(new Item[] {
    Item.swordWood, Item.shovelWood, Item.pickaxeWood, Item.axeWood, Item.hoeWood, Item.swordGold, Item.shovelGold, Item.pickaxeGold, Item.axeGold,
    Item.hoeGold, Item.helmetLeather, Item.plateLeather, Item.legsLeather, Item.bootsLeather, Item.helmetGold, Item.plateGold, Item.legsGold, Item.bootsGold
  }));
  private static ArrayList class2 = new ArrayList(Arrays.asList(new Item[] {
    Item.swordStone, Item.shovelStone, Item.pickaxeStone, Item.axeStone, Item.hoeStone, Item.helmetChain, Item.plateChain, Item.legsChain, Item.bootsChain, toolPlasticShovel, toolPlasticPickaxe, toolPlasticAxe
  }));
  private static ArrayList class3 = new ArrayList(Arrays.asList(new Item[] {
    Item.flintAndSteel, Item.shovelSteel, Item.pickaxeSteel, Item.axeSteel, Item.swordSteel, Item.hoeSteel, Item.helmetSteel, Item.plateSteel, Item.legsSteel, Item.bootsSteel, armorKevlarVest, armorKevlarLegs
  }));
  private static ArrayList class4 = new ArrayList(Arrays.asList(new Item[] {
    Item.swordDiamond, Item.shovelDiamond, Item.pickaxeDiamond, Item.axeDiamond, Item.hoeDiamond, Item.helmetDiamond, Item.plateDiamond, Item.legsDiamond, Item.bootsDiamond
  }));
  
  public mod_PlasticCraft() {
  	instance = this;
  }
  
  public void ModsLoaded() {
  	MinecraftForgeClient.preloadTexture(itemSheet);
    MinecraftForgeClient.preloadTexture(blockSheet);
  }
  
  public void load() {
    MinecraftForge.versionDetect("PlasticCraft", 1, 2, 3);
    
    registerItems();
    console("Registering items.");
    addRecipes();
    console("Registering recipes.");
    
    MinecraftForge.registerOreHandler(oreHandler);
    MinecraftForge.registerCustomBucketHandler(bucketHandler);
    
    MinecraftForge.registerOre("itemRubber", new ItemStack(itemRubber, 1));
    
    MinecraftForge.setToolClass(toolPlasticShovel, "shovel", 1);
    MinecraftForge.setToolClass(toolPlasticPickaxe, "pickaxe", 1);
    MinecraftForge.setToolClass(toolPlasticAxe, "axe", 1);
    MinecraftForge.setBlockHarvestLevel(blockPlasticGoo, "shovel", 0);
    
    MinecraftForge.addDungeonLoot(new ItemStack(itemSynthString), 1, 1, 5);
    MinecraftForge.addDungeonLoot(new ItemStack(itemRubber), 0.8F, 1, 2);
    MinecraftForge.addDungeonLoot(new ItemStack(itemNeedleHealth), 0.5F);
    MinecraftForge.addDungeonLoot(new ItemStack(armorNightGoggles), 0.15F);

    ModLoader.SetInGameHook(this, true, false);
    
    ModLoader.RegisterTileEntity(TileEntityMicrowave.class, "Microwave");
    ModLoaderMp.RegisterGUI(this, guiMicrowaveId); // microwave
    ModLoader.RegisterTileEntity(TileEntityExtract.class, "Extracting Furnace");
    ModLoaderMp.RegisterGUI(this, guiExtractorId); // extractor
    
    ModLoader.RegisterEntityID(EntityC4Primed.class, "C4", entityC4Id);
    ModLoaderMp.RegisterNetClientHandlerEntity(EntityC4Primed.class, entityC4NetId);
    ModLoader.RegisterEntityID(EntityPlasticBoat.class, "Plastic Boat", entityPlasticBoatId);
    ModLoaderMp.RegisterNetClientHandlerEntity(EntityPlasticBoat.class, entityPlasticBoatNetId);
    
    ModLoader.RemoveSpawn("Cow", EnumCreatureType.creature);
    ModLoader.RegisterEntityID(EntityPlasticCow.class, "Cow", 92);
    ModLoader.AddSpawn(EntityPlasticCow.class, 8, 4, 4, EnumCreatureType.creature);
  }
  
  public static void registerItems() {
    ModLoader.RegisterBlock(blockPlastic, ItemBlockPlastic.class);
    ModLoader.RegisterBlock(blockPlasticGoo);
    ModLoader.RegisterBlock(blockC4);
    ModLoader.RegisterBlock(blockPlexiglass, ItemBlockPlexiglass.class);
    ModLoader.RegisterBlock(blockPlexidoor);
    ModLoader.RegisterBlock(blockPlasticMachine, ItemBlockPlasticMachine.class);
    ModLoader.RegisterBlock(blockTap);
    ModLoader.RegisterBlock(blockTrampoline);
    ModLoader.RegisterBlock(blockAccelerator);
    ModLoader.RegisterBlock(blockRope);
    
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 0), "Plain Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 1), "Orange Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 2), "Magenta Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 3), "Light Blue Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 4), "Yellow Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 5), "Lime Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 6), "Pink Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 7), "Grey Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 8), "Teal Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 9), "Cyan Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 10), "Purple Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 11), "Blue Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 12), "Brown Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 13), "Green Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 14), "Red Plastic");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlastic.blockID], 1, 15), "Black Plastic");
    ModLoader.AddName(blockPlasticGoo, "Gooey Plastic Block");
    ModLoader.AddName(blockC4, "C4 Explosive");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlexiglass.blockID], 1, 0), "Plexiglass");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlexiglass.blockID], 1, 1), "Glowing Plexiglass");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlasticMachine.blockID], 1, 0), "Microwave Oven");
    ModLoader.AddName(new ItemStack(Item.itemsList[blockPlasticMachine.blockID], 1, 8), "Extracting Furnace");
    ModLoader.AddName(blockTap, "Latex Extractor");
    ModLoader.AddName(blockTrampoline, "Trampoline");
    ModLoader.AddName(blockAccelerator, "Accelerator");
    
    ModLoader.AddName(itemPlastic, "Plastic Ball");
    ModLoader.AddName(itemPlasticClear, "Clear Plastic Ball");
    ModLoader.AddName(itemPlasticStick, "Plastic Stick");
    ModLoader.AddName(itemWoodDust, "Wood Flour");
    ModLoader.AddName(itemMould, "Mould");
    ModLoader.AddName(itemMouldFull, "Filled Mould");
    ModLoader.AddName(itemSynthString, "Sythetic String");
    ModLoader.AddName(itemIntegratedCircuit, "Integrated Circuit");
    ModLoader.AddName(itemBowlGelatin, "Bowl of Gelatin");
    ModLoader.AddName(itemGelatin, "Gelatin Powder");
    ModLoader.AddName(itemPlasticGoo, "Plastic Goo");
    ModLoader.AddName(itemRubber, "Rubber Ball");
    ModLoader.AddName(itemDuctTape, "Duct Tape");
    ModLoader.AddName(itemPlexidoor, "Plexiglass Door");
    ModLoader.AddName(itemBattery, "Battery");
    ModLoader.AddName(itemSilicon, "Rough Silicon");
    ModLoader.AddName(itemPlasticBoat, "Plastic Boat");
    ModLoader.AddName(new ItemStack(Item.itemsList[itemC4Remote.shiftedIndex], 1, 0), "Handheld C4 Defuser");
    ModLoader.AddName(new ItemStack(Item.itemsList[itemC4Remote.shiftedIndex], 1, 1), "Handheld C4 Detonator");
    ModLoader.AddName(itemSynthCloth, "Synthetic Fiber");
    ModLoader.AddName(itemRope, "Synthetic Rope");
    ModLoader.AddName(itemPlasticBucket, "Plastic Bucket");
    ModLoader.AddName(itemPlasticBucketW, "Plastic Water Bucket");
    ModLoader.AddName(itemPlasticBucketM, "Plastic Milk Bucket");
    ModLoader.AddName(itemPlasticBucketL, "Plastic Latex Bucket");
    ModLoader.AddName(itemPlasticBottle, "Plastic Bottle");
    ModLoader.AddName(itemPlasticBottleW, "Water Bottle");
    ModLoader.AddName(itemPlasticBottleM, "Milk Bottle");
    ModLoader.AddName(itemIronBucketL, "Iron Latex Bucket");
    ModLoader.AddName(itemNeedle, "Needle");
    ModLoader.AddName(itemNeedleHealth, "Health Needle");
    ModLoader.AddName(itemJello, "Jello");
    ModLoader.AddName(armorNightGoggles, "Night-Vision Goggles");
    ModLoader.AddName(armorKevlarVest, "Kevlar Vest");
    ModLoader.AddName(armorKevlarLegs, "Kevlar Pants");
    ModLoader.AddName(armorFallBoots, "Shock-Absorbing Boots");
    ModLoader.AddName(toolPlasticShovel, "Plastic Shovel");
    ModLoader.AddName(toolPlasticPickaxe, "Plastic Pickaxe");
    ModLoader.AddName(toolPlasticAxe, "Plastic Axe");
  }

  public static void addRecipes() {
  	// Blocks
    ModLoader.AddRecipe(new ItemStack(blockPlastic, 1, 0), new Object[] { "PP", "PP", 
      'P', itemPlastic });
    ModLoader.AddRecipe(new ItemStack(blockPlasticGoo), new Object[] { "PP", "PP", 
      'P', itemPlasticGoo });
    ModLoader.AddRecipe(new ItemStack(blockC4), new Object[] { "GPG", "PCP", "GPG", 
      'P', blockPlastic, 'G', Item.gunpowder, 'C', itemIntegratedCircuit });
    ModLoader.AddRecipe(new ItemStack(blockPlexiglass, 1, 0), new Object[] { "PP", "PP", 
      'P', itemPlasticClear });
    ModLoader.AddRecipe(new ItemStack(blockPlexiglass, 1, 1), new Object[] { " L ", "LPL", " L ", 
      'P', new ItemStack(blockPlexiglass, 1, 0), 'L', Item.lightStoneDust });
    ModLoader.AddRecipe(new ItemStack(blockTap), new Object[] { "P", "P", "P",
      'P', itemPlastic, });
    ModLoader.AddRecipe(new ItemStack(blockPlasticMachine, 1, 0), new Object[] { "IPI", "GSG", "IPI", // Microwave
      'P', blockPlastic, 'I', Item.ingotIron, 'G', Block.glass, 'S', itemIntegratedCircuit });
    ModLoader.AddRecipe(new ItemStack(blockPlasticMachine, 1, 8), new Object[] { "PXP", "PFP", "PEP", // Extractor
      'P', blockPlastic, 'F', Block.stoneOvenIdle, 'E', blockTap, 'X', itemIntegratedCircuit });
    
    // Items
    ModLoader.AddRecipe(new ItemStack(itemPlastic, 4), new Object[] { "X", 
      'X', blockPlastic });
    ModLoader.AddRecipe(new ItemStack(itemPlasticClear, 4), new Object[] { "X", 
      'X', new ItemStack(blockPlexiglass, 1, 0) });
    ModLoader.AddRecipe(new ItemStack(itemPlasticGoo, 4), new Object[] { "X", 
      'X', blockPlasticGoo });
    ModLoader.AddRecipe(new ItemStack(itemPlasticClear), new Object[] { "P", 
      'P', itemPlastic });
    ModLoader.AddRecipe(new ItemStack(itemPlasticStick, 4), new Object[] { "P", "P", 
      'P', itemPlastic });
    ModLoader.AddRecipe(new ItemStack(itemSynthString, 4), new Object[] { "PP", 
      'P', itemPlasticClear });
    ModLoader.AddRecipe(new ItemStack(itemSynthCloth), new Object[] { "SS", "SS", 
      'S', itemSynthString });
    ModLoader.AddRecipe(new ItemStack(itemSynthString, 4), new Object[] { "X", 
      'X', itemSynthCloth });
    ModLoader.AddRecipe(new ItemStack(itemMould), new Object[] { "S S", " S ", 
      'S', Block.cobblestone });
    ModLoader.AddShapelessRecipe(new ItemStack(itemMouldFull), new Object[] { 
      new ItemStack(itemWoodDust), new ItemStack(Item.egg), new ItemStack(itemMould) });
    ModLoader.AddShapelessRecipe(new ItemStack(itemMouldFull), new Object[] {
      new ItemStack(itemWoodDust), new ItemStack(itemGelatin), new ItemStack(itemMould) });
    ModLoader.AddShapelessRecipe(new ItemStack(itemMouldFull), new Object[] { 
      new ItemStack(itemWoodDust), new ItemStack(Item.dyePowder, 1, 15), new ItemStack(itemMould) });
    ModLoader.AddRecipe(new ItemStack(itemWoodDust, 4), new Object[] { "W", 
      'W', Block.planks });
    ModLoader.AddShapelessRecipe(new ItemStack(itemBowlGelatin), new Object[] { 
      new ItemStack(Item.beefRaw), new ItemStack(Item.bowlEmpty) });
    ModLoader.AddShapelessRecipe(new ItemStack(itemBowlGelatin), new Object[] { 
      new ItemStack(Item.chickenRaw), new ItemStack(Item.bowlEmpty) });
    ModLoader.AddShapelessRecipe(new ItemStack(itemBowlGelatin), new Object[] { 
      new ItemStack(Item.porkRaw), new ItemStack(Item.bowlEmpty) });
    ModLoader.AddShapelessRecipe(new ItemStack(itemBowlGelatin), new Object[] { 
      new ItemStack(Item.leather), new ItemStack(Item.bowlEmpty) });
    ModLoader.AddRecipe(new ItemStack(itemPlexidoor), new Object[] { "PP", "PP", "PP", 
      'P', new ItemStack(blockPlexiglass, 1, 0) });
    ModLoader.AddRecipe(new ItemStack(itemBattery), new Object[] { "III", "RBR", "LLL", 
      'I', Item.ingotIron, 'R', Item.redstone, 'L', Item.lightStoneDust, 'B', Item.bucketLava });
    ModLoader.AddRecipe(new ItemStack(itemSilicon, 3), new Object[] { " SS", "S S", "SS ", 
      'S', Block.sand });
    ModLoader.AddRecipe(new ItemStack(itemIntegratedCircuit, 2), new Object[] { "SFS", "IRI", "PPP", 
      'F', itemSynthCloth, 'I', Item.ingotIron, 'R', Item.redstoneRepeater, 'P', blockPlastic, 'S', itemSilicon });
    ModLoader.AddRecipe(new ItemStack(itemPlasticBoat), new Object[] { "P P", "PGP", 
      'P', blockPlastic, 'G', new ItemStack(blockPlexiglass, 1, 0) });
    ModLoader.AddRecipe(new ItemStack(itemC4Remote, 1, 1), new Object[] { "PBP", "RCG", "IS ", 
      'P', itemPlastic, 'R', Item.redstone, 'I', Item.ingotIron, 'C', itemIntegratedCircuit, 'G', Block.glass, 'B', Block.button, 'S', itemBattery });
    ModLoader.AddRecipe(new ItemStack(itemC4Remote, 1, 0), new Object[] { "R",
      'R', new ItemStack(itemC4Remote, 1, 1) });
    ModLoader.AddRecipe(new ItemStack(itemC4Remote, 1, 1), new Object[] { "R",
      'R', new ItemStack(itemC4Remote, 1, 0) });
    ModLoader.AddRecipe(new ItemStack(itemRope), new Object[] { "/I", "SS", "SS", 
      'S', itemSynthCloth, '/', itemSynthString, 'I', Item.ingotIron });
    ModLoader.AddRecipe(new ItemStack(itemPlasticBucket), new Object[] { "P P", " P ", 
      'P', itemPlastic });
    ModLoader.AddRecipe(new ItemStack(itemPlasticBottle), new Object[] { "P  ", " P ", "  P", 
      'P', itemPlasticClear });
    ModLoader.AddShapelessRecipe(new ItemStack(itemPlasticBottleM), new Object[] { 
      new ItemStack(itemPlasticBucketM), new ItemStack(itemPlasticBottle) });
    ModLoader.AddShapelessRecipe(new ItemStack(itemPlasticBottleM), new Object[] {
      new ItemStack(Item.bucketMilk), new ItemStack(itemPlasticBottle) });
    ModLoader.AddShapelessRecipe(new ItemStack(itemPlasticBottleW), new Object[] { 
      new ItemStack(itemPlasticBucketW), new ItemStack(itemPlasticBottle) });
    ModLoader.AddShapelessRecipe(new ItemStack(itemPlasticBottleW), new Object[] { 
      new ItemStack(Item.bucketMilk), new ItemStack(itemPlasticBottle) });
    ModLoader.AddRecipe(new ItemStack(itemNeedle, 2), new Object[] { "P P", "G G", " I ", 
      'P', itemPlasticClear, 'G', Block.glass, 'I', Item.ingotIron });
    ModLoader.AddRecipe(new ItemStack(itemNeedleHealth), new Object[] { " A ", "RFR", " N ", 
      'N', itemNeedle, 'R', Item.redstone, 'F', Block.plantRed, 'A', Item.speckledMelon });
    ModLoader.AddShapelessRecipe(new ItemStack(itemJello), new Object[] { 
      new ItemStack(itemPlasticBucketW), new ItemStack(Item.sugar), new ItemStack(itemGelatin) });
    ModLoader.AddShapelessRecipe(new ItemStack(itemJello), new Object[] { 
      new ItemStack(Item.bucketWater), new ItemStack(Item.sugar), new ItemStack(itemGelatin) });
    
    // Tools and Armor
    ModLoader.AddRecipe(new ItemStack(armorNightGoggles), new Object[] { "SSS", "S S", "PCP", 
      'S', itemSynthString, 'P', Item.diamond, 'C', itemIntegratedCircuit });
    ModLoader.AddRecipe(new ItemStack(armorKevlarVest), new Object[] { "SSS", "/I/", "SSS", 
      'S', itemSynthCloth, '/', itemSynthString, 'I', Item.plateLeather });
    ModLoader.AddRecipe(new ItemStack(armorKevlarLegs), new Object[] { "SSS", "/I/", "SSS", 
      'S', itemSynthCloth, '/', itemSynthString, 'I', Item.legsLeather });
    ModLoader.AddRecipe(new ItemStack(toolPlasticShovel), new Object[] { " P ", " / ", " / ", 
      'P', itemPlastic, '/', itemPlasticStick });
    ModLoader.AddRecipe(new ItemStack(toolPlasticPickaxe), new Object[] { "PPP", " / ", " / ", 
    	'P', itemPlastic, '/', itemPlasticStick });
    ModLoader.AddRecipe(new ItemStack(toolPlasticAxe), new Object[] { "PP ", "P/ ", " / ", 
    	'P', itemPlastic, '/', itemPlasticStick });
    
    // Other
    ModLoader.AddRecipe(new ItemStack(Item.bow), new Object[] { "S/ ", "S /", "S/ ", 
      '/', Item.stick, 'S', itemSynthString });
    ModLoader.AddRecipe(new ItemStack(Item.fishingRod), new Object[] { "  /", " /S", "/ S", 
      '/', Item.stick, 'S', itemSynthString });
    ModLoader.AddRecipe(new ItemStack(Item.feather, 4), new Object[] { "S", "/", 
      '/', Item.stick, 'S', itemSynthCloth });
    ModLoader.AddRecipe(new ItemStack(Item.redstone, 5), new Object[] { "SIS", " S ", "SIS", 
      'S', itemSilicon, 'I', Item.ingotIron });
    ModLoader.AddRecipe(new ItemStack(Item.redstone, 15), new Object[] { "SIS", " S ", "SIS", 
      'S', itemSilicon, 'I', Item.ingotGold });
    ModLoader.AddRecipe(new ItemStack(Item.cake), new Object[] { "MMM", "SES", "WWW", 
      'M', itemPlasticBucketM, 'S', Item.sugar, 'E', Item.egg, 'W', Item.wheat });
    ModLoader.AddShapelessRecipe(new ItemStack(Block.pistonStickyBase), new Object[] { 
      new ItemStack(Block.pistonBase), new ItemStack(itemPlasticGoo) });
    
    // Misc
    BlockPlastic.recipes();
    
    // Furnace(s)  
    ModLoader.AddSmelting(itemMouldFull.shiftedIndex, new ItemStack(itemPlastic, 3));
    ModLoader.AddSmelting(itemPlastic.shiftedIndex, new ItemStack(itemPlasticGoo));
    ModLoader.AddSmelting(itemBowlGelatin.shiftedIndex, new ItemStack(itemGelatin, 4));
    ModLoader.AddSmelting(itemPlasticBucketL.shiftedIndex, new ItemStack(itemRubber, 2));
    ModLoader.AddSmelting(itemIronBucketL.shiftedIndex, new ItemStack(itemRubber, 2));
    addExtractorSmelting(itemMouldFull.shiftedIndex, new ItemStack(itemPlastic, 3), new ItemStack(itemMould));
    addExtractorSmelting(itemPlastic.shiftedIndex, new ItemStack(itemPlasticGoo));
    addExtractorSmelting(itemBowlGelatin.shiftedIndex, new ItemStack(itemGelatin, 4), new ItemStack(Item.bowlEmpty));
    addExtractorSmelting(itemPlasticBucketL.shiftedIndex, new ItemStack(itemRubber, 2), new ItemStack(itemPlasticBucket));
    addExtractorSmelting(itemIronBucketL.shiftedIndex, new ItemStack(itemRubber, 2), new ItemStack(Item.bucketEmpty));
    
    // Repair
    for (int i=0; i<class1.size(); i++) {
      Item item = (Item)class1.get(i);
      ModLoader.AddShapelessRecipe(new ItemStack(item), new Object[] { 
      	new ItemStack(itemDuctTape), new ItemStack(item, 1, -1) });
    }
    for (int i=0; i<class2.size(); i++) {
      Item item1 = (Item)class2.get(i);
      ModLoader.AddShapelessRecipe(new ItemStack(item1), new Object[] { 
      	new ItemStack(itemDuctTape), new ItemStack(itemDuctTape), new ItemStack(item1, 1, -1) });
    }  
    for (int i=0; i<class3.size(); i++) {
      Item item2 = (Item)class3.get(i);
      ModLoader.AddRecipe(new ItemStack(item2), new Object[] {
        " D ", "DXD", " D ", 'D', itemDuctTape, 'X', new ItemStack(item2, 1, -1) });
    }
    for (int i=0; i<class4.size(); i++) {
      Item item3 = (Item)class4.get(i);
      ModLoader.AddRecipe(new ItemStack(item3), new Object[] {
        "DDD", "DXD", "DDD", 'D', itemDuctTape, 'X', new ItemStack(item3, 1, -1) });
    }     
  }
  
  /** Method for adding duct-tape compatible items. **/
  public static void addRepairs(int repairClass, Item item) {
    if (repairClass == 1) class1.add(item);
    if (repairClass == 2) class2.add(item);
    if (repairClass == 3) class3.add(item);
    if (repairClass == 4) class4.add(item);
  }
  
  /** Method for adding items to the extractor recipes. **/
  public static void addExtractorSmelting(int id, ItemStack... itemstack) {
    ExtractRecipes.smelting().addSmelting(id, itemstack[0]);
    try {
      ExtractRecipes.smelting().addExtraction(id, itemstack[1]);
    } catch (Exception e) {}
  }
  
  /** Metadata-aware method for adding items to the extractor recipes. **/
  public static void addExtractorSmelting(int id, int meta, ItemStack... itemstack) {
    ExtractRecipes.smelting().addSmelting(id, meta, itemstack[0]);
    try {
      ExtractRecipes.smelting().addExtraction(id, meta, itemstack[1]);
    } catch (Exception e) {}
  }
  
  public void AddRenderer(Map map) {
    map.put(EntityC4Primed.class, new RenderC4Primed());
    map.put(EntityPlasticBoat.class, new RenderPlasticBoat());
  }
  
  public void RegisterAnimation(Minecraft minecraft) {
    ModLoader.addAnimation(new TextureFrameAnimFX(BlockPlasticMachine.microwaveAnim, modDir + "blockMicrowaveAnim.png"));
  }
  
  public GuiScreen HandleGUI(int invType) {
  	InventoryPlayer inventory = ModLoader.getMinecraftInstance().thePlayer.inventory;
  	TileEntityMicrowave microwave = new TileEntityMicrowave();
  	TileEntityExtract extractor = new TileEntityExtract();
  	
  	if (invType == 250)
  		return new GuiMicrowave(inventory, microwave);
  	else if (invType == 251)
  		return new GuiExtract(inventory, extractor);
  	
  	return null;
  }

  public boolean DispenseEntity(World world, double d, double d1, double d2, int i, int j, ItemStack itemstack) {
    int k = itemstack.itemID;
    Random random = world.rand;
    Entity block = null;
        
    if (k == blockC4.blockID)
    	block = new EntityC4Primed(world, d, d1, d2);
    if (k == Block.tnt.blockID)
    	block = new EntityTNTPrimed(world, d, d1, d2);
    if (k == blockPlasticGoo.blockID)
    	block = new EntityFallingSand(world, d, d1, d2, blockPlasticGoo.blockID);
    if (k == Block.sand.blockID)
    	block = new EntityFallingSand(world, d, d1, d2, Block.sand.blockID);
    if (k == Block.gravel.blockID)
    	block = new EntityFallingSand(world, d, d1, d2, Block.gravel.blockID);
    if (block != null) {
      block.posZ = (double)i * (random.nextDouble() * 3D + 2D) + (double)(random.nextFloat() - 0.5F);
      block.motionX = 1.0D;
      block.motionY = (double)j * (random.nextDouble() * 3D + 2D) + (double)(random.nextFloat() - 0.5F);
      world.spawnEntityInWorld((Entity)block);
      world.playSoundEffect(d, d1, d2, "random.bow", 1.0F, 1.2F);
      return true;
    } else
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
    
    if (itemstack != null && itemstack.itemID == armorFallBoots.shiftedIndex) {
      minecraft.thePlayer.fallDistance = -1F;
      isWearingFallBoots = true;
    } else
      isWearingFallBoots = false;
  }

  private void renderNightvisionOverlay(Minecraft minecraft) {
    ItemStack itemstack = minecraft.thePlayer.inventory.armorItemInSlot(3);
        
    if (!minecraft.gameSettings.hideGUI && itemstack != null && itemstack.itemID == armorNightGoggles.shiftedIndex)
      renderTextureOverlay(minecraft, "%blur%" + modDir + "guiNightVision" + nightvisionStyle + ".png", 1.0F);
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

  private static void prepareProps() {
    console("Preparing /config/PlasticCraft.props");
    
    props.getInt("blockPlastic", 125);
    props.getInt("blockPlasticGoo", 126);
    props.getInt("blockC4", 127);
    props.getInt("blockPlexiglass", 128);
    props.getInt("blockPlexiglassDoor", 129);
    props.getInt("blockPlasticMachine", 130);
    props.getInt("blockLatexTap", 131);
    props.getInt("blockTrampoline", 132);
    props.getInt("blockAccelerator", 133);
    props.getInt("blockRope", 135);
    
    props.getInt("itemPlasticBall", 1001);
    props.getInt("itemClearBall", 1002);
    props.getInt("itemPlasticStick", 1003);
    props.getInt("itemMould", 1004);
    props.getInt("itemFilledMould", 1005);
    props.getInt("itemWoodFlour", 1006);
    props.getInt("itemPlasticString", 1007);
    props.getInt("itemSiliconChip", 1008);
    props.getInt("itemGelatinBowl", 1009);
    props.getInt("itemGelatin", 1010);
    props.getInt("itemGooPlastic", 1011);
    props.getInt("itemRubberBall", 1012);
    props.getInt("itemTape", 1013);
    props.getInt("itemPlexiglassDoor", 1014);
    props.getInt("itemBattery", 1015);
    props.getInt("itemSilicon", 1016);
    props.getInt("itemPlasticBoat", 1017);
    props.getInt("itemC4Remote", 1018);
    props.getInt("itemPlasticBucket", 1019);
    props.getInt("itemPlasticWaterBucket", 1020);
    props.getInt("itemPlasticMilkBucket", 1021);
    props.getInt("itemPlasticLatexBucket", 1022);
    props.getInt("itemEmptyBottle", 1023);
    props.getInt("itemWaterBottle", 1024);
    props.getInt("itemMilkBottle", 1025);
    props.getInt("itemNeedle", 1026);
    props.getInt("itemRedNeedle", 1027);
    props.getInt("itemJello", 1028);
    props.getInt("itemSynthCloth", 1029);
    props.getInt("itemRope", 1030);
    props.getInt("itemIronLatexBucket", 1031);
    
    props.getInt("armorNightVisionGoggles", 1040);
    props.getInt("armorKevlarVest", 1041);
    props.getInt("armorKevlarLegs", 1042);
    props.getInt("armorFallDampeningBoots", 1043);
    props.getInt("toolPlasticShovel", 1050);
    props.getInt("toolPlasticPickaxe", 1051);
    props.getInt("toolPlasticAxe", 1052);
    
    props.getInt("entityC4ID", 250);
    props.getInt("entityC4NetID", 250);
    props.getInt("entityPlasticBoatID", 251);
    props.getInt("entityPlasticBoatNetID", 251);
    props.getInt("guiMicrowaveID", 250);
    props.getInt("guiExtractorID", 251);
    
    props.getInt("c4Power", 10);
    props.getInt("c4Fuse", 6);
    props.getBoolean("c4Enhanced", true);
    props.getString("nightvisionStyle", "SDK");
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