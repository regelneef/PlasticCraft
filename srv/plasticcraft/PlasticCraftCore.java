package net.minecraft.src.plasticcraft;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.src.*;
import net.minecraft.src.forge.*;
import net.minecraft.src.plasticcraft.core.*;

public class PlasticCraftCore {
  public static String version = "v3.0 (for 1.1.0)";
  public static String modDir = "/plasticcraft/images/";
  public static String itemSheet = modDir + "pc_items.png"; // item sprite sheet
  public static String blockSheet = modDir + "pc_terrain.png"; // block sprite sheet
  private static Props props = mod_PlasticCraft.props;
  private static int iOff = 256; // item id offset
  private static void console(String s) { System.out.println("[PlasticCraft] " + s); }
  public static IProxy proxy;
  
  // Handlers
  private static net.minecraft.src.plasticcraft.core.HandlerOre oreHandler = new HandlerOre();
  private static net.minecraft.src.plasticcraft.core.HandlerBucket bucketHandler = new HandlerBucket();
 
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
  public static final Block blockFloorMat = new BlockFloorMat(props.getInt("blockFloorMat"));
  public static final Block blockTap = new BlockTap(props.getInt("blockLatexTap"));
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
  public static int guiAdvExtractorId = props.getInt("guiAdvExtractorID");
  public static int guiDisassemblerId = props.getInt("guiUncrafterID");
  
  // Booleans
  public static int c4Power = props.getInt("c4Power");
  public static int c4Fuse = props.getInt("c4Fuse") * 20;
  public static boolean c4Enhanced = props.getBoolean("c4Enhanced");
  public static boolean c4Disabled = props.getBoolean("c4Disabled"); // serverside only, for disabling C4 explosions
  public static String nightvisionStyle = props.getString("nightvisionStyle"); // clientside only, for selecting night vision style
  public static boolean isWearingFallBoots;
  static { props.save(); }
  
  public static Map<EnumPlasticMachine, Integer> machineMetadataMappings;
  
  //Repair Lists
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

  public static void init(IProxy proxyParam) {
    MinecraftForge.versionDetect("PlasticCraft", 1, 3, 0);
  
    proxy = proxyParam;
    proxy.getModId();
  
    registerItems();
    console("Registering items.");
    registerRecipes();
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
    
    ModLoader.RegisterTileEntity(TileEntityMicrowave.class, "Microwave");
    ModLoader.RegisterTileEntity(TileEntityExtractor.class, "Extracting Furnace");
    ModLoader.RegisterTileEntity(TileEntityAdvExtractor.class, "Advanced Extractor");
    ModLoader.RegisterTileEntity(TileEntityDisassembler.class, "Uncrafter");
    
    ModLoader.RegisterEntityID(EntityC4Primed.class, "C4", entityC4Id);
    ModLoader.RegisterEntityID(EntityPlasticBoat.class, "Plastic Boat", entityPlasticBoatId);
    
    ModLoader.RemoveSpawn("Cow", EnumCreatureType.creature);
    ModLoader.RegisterEntityID(EntityPlasticCow.class, "Cow", 92);
    ModLoader.AddSpawn(EntityPlasticCow.class, 8, 4, 4, EnumCreatureType.creature);
  }
  
  private static void registerItems() {
    machineMetadataMappings = new HashMap<EnumPlasticMachine, Integer>(); // metadata mappings for plastic machines
    machineMetadataMappings.put(EnumPlasticMachine.Microwave, 0);
    machineMetadataMappings.put(EnumPlasticMachine.Extractor, 1);
    machineMetadataMappings.put(EnumPlasticMachine.AdvExtractor, 2);
    machineMetadataMappings.put(EnumPlasticMachine.Disassembler, 3);
  
    // Register the blocks
    ModLoader.RegisterBlock(blockPlastic, ItemBlockPlastic.class);
    ModLoader.RegisterBlock(blockPlasticGoo);
    ModLoader.RegisterBlock(blockC4);
    ModLoader.RegisterBlock(blockPlexiglass, ItemBlockPlexiglass.class);
    ModLoader.RegisterBlock(blockPlexidoor);
    ModLoader.RegisterBlock(blockPlasticMachine, ItemBlockPlasticMachine.class);
    ModLoader.RegisterBlock(blockFloorMat, ItemBlockFloorMat.class);
    ModLoader.RegisterBlock(blockTap);
    ModLoader.RegisterBlock(blockRope);
    
    BlockPlasticMachine.setupTextures();
  }
  
  public static void registerRecipes() {
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
    ModLoader.AddRecipe(new ItemStack(blockPlasticMachine, 1, machineMetadataMappings.get(EnumPlasticMachine.Microwave)), new Object[] { "IPI", "GSG", "IPI", // Microwave
      'P', blockPlastic, 'I', Item.ingotIron, 'G', Block.glass, 'S', itemIntegratedCircuit });
    ModLoader.AddRecipe(new ItemStack(blockPlasticMachine, 1, machineMetadataMappings.get(EnumPlasticMachine.Extractor)), new Object[] { "PCP", "PFP", "PEP", // Extractor
      'P', blockPlastic, 'F', Block.stoneOvenIdle, 'E', blockTap, 'C', itemIntegratedCircuit });
    ModLoader.AddRecipe(new ItemStack(blockPlasticMachine, 1, machineMetadataMappings.get(EnumPlasticMachine.AdvExtractor)), new Object[] { "ICI", "PEP", "IEI", // Advanced Extractor
      'P', blockPlastic, 'I', Item.ingotIron, 'E', new ItemStack(blockPlasticMachine, 1, machineMetadataMappings.get(EnumPlasticMachine.Extractor)), 'C', itemIntegratedCircuit });
    
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
    ModLoader.AddRecipe(new ItemStack(armorFallBoots), new Object[] { "OSO", "R R", 
      'S', itemIntegratedCircuit, 'O', Block.obsidian, 'R', itemRubber });
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
  public static void addExtractorSmelting(int id, ItemStack... itemstacks) {
    RecipesExtractor.smelting().addSmelting(id, itemstacks[0]);
    try {
      RecipesExtractor.smelting().addExtraction(id, itemstacks[1]);
    } catch (Exception e) {}
  }
  
  /** Metadata-aware method for adding items to the extractor recipes. **/
  public static void addExtractorSmelting(int id, int meta, ItemStack... itemstacks) {
    RecipesExtractor.smelting().addSmelting(id, meta, itemstacks[0]);
    try {
      RecipesExtractor.smelting().addExtraction(id, meta, itemstacks[1]);
    } catch (Exception e) {}
  }
  
  /** Method for adding items to the disassembler recipes. **/
  public static void addDisassembling(int id, ItemStack... itemstacks) {
    RecipesDisassembler.uncrafting().addUncrafting(id, 
      itemstacks[0], itemstacks[1], itemstacks[2],
      itemstacks[3], itemstacks[4], itemstacks[5],
      itemstacks[6], itemstacks[7], itemstacks[8]);
  }
  
  /** Metadata-aware method for adding items to the disassembler recipes. **/
  public static void addDisassembling(int id, int meta, ItemStack... itemstacks) {
    RecipesDisassembler.uncrafting().addUncrafting(id, meta,
      itemstacks[0], itemstacks[1], itemstacks[2],
      itemstacks[3], itemstacks[4], itemstacks[5],
      itemstacks[6], itemstacks[7], itemstacks[8]);
  }
  
  public static boolean DispenseEntity(World world, double d, double d1, double d2, int i, int j, ItemStack itemstack) {
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
  
  private static void prepareProps() {
    console("Preparing /config/PlasticCraft.props");
    
    props.getInt("blockPlastic", 125);
    props.getInt("blockPlasticGoo", 126);
    props.getInt("blockC4", 127);
    props.getInt("blockPlexiglass", 128);
    props.getInt("blockPlexiglassDoor", 129);
    props.getInt("blockPlasticMachine", 130);
    props.getInt("blockFloorMat", 131);
    props.getInt("blockLatexTap", 132);
    props.getInt("blockRope", 133);
    
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
    props.getInt("guiAdvExtractorID", 252);
    props.getInt("guiUncrafterID", 253);
    
    props.getInt("c4Power", 10);
    props.getInt("c4Fuse", 6);
    props.getBoolean("c4Enhanced", true);
    
    mod_PlasticCraft.prepareProps();
  }
  
  public enum EnumPlasticMachine {
    Microwave,
    Extractor,
    AdvExtractor,
    Disassembler
  }
}