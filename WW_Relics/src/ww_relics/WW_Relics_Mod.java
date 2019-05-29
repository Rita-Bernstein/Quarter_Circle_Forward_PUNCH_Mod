package ww_relics;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.screens.custom.CustomMod;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import ww_relics.modifiers.*;
import ww_relics.potions.ChallengerCoin;
import ww_relics.relics.character_cameos.dan.NotStrongestFightingStyleGuidebook;
import ww_relics.relics.character_cameos.sakura.SchoolBackpack;
import ww_relics.relics.chun_li.*;
import ww_relics.relics.guile.ArmyBoots;
import ww_relics.relics.guile.ChainWithNametags;
import ww_relics.relics.guile.CombatFatigues;
import ww_relics.relics.ken.BlackTrainingShirt;
import ww_relics.relics.ken.RedGi;
import ww_relics.relics.ken.UnceasingFlame;
import ww_relics.relics.ryu.*;

@SpireInitializer
public class WW_Relics_Mod implements AddCustomModeModsSubscriber, EditStringsSubscriber, EditRelicsSubscriber,
			EditCardsSubscriber,
			EditKeywordsSubscriber, PostInitializeSubscriber, PostDungeonInitializeSubscriber, 
			PostCreateStartingRelicsSubscriber, StartGameSubscriber
	{

	public static final Logger logger = LogManager.getLogger(WW_Relics_Mod.class.getName()); // lets us log output

	public static final String MODNAME = "World Warriors' Relics"; // mod name
	public static final String AUTHOR = "Clauvin aka Dungeon Explorer Lan"; // your name
	public static final String DESCRIPTION = "v0.10.6" +
			"\r\n Adds fourteen relics based in SF2's main characters, + 8 game modifiers."
		  + "\r\n v1.0 will have 32+ relics."
		  + "\r\n The images in the mod are temporary and will be substituted/improved on version 1.0.";
	
	public WW_Relics_Mod() {
		BaseMod.subscribe(this);
	}

	public static void initialize()	{
	    new WW_Relics_Mod();
	}
	
	public String getJsonText(String filepath) {
		return Gdx.files.internal(filepath).readString(String.valueOf(StandardCharsets.UTF_8));
	}
	
	//Yes, I know. I will refactor this hard coded part later.
	@Override
	public void receiveEditKeywords() {
		
		logger.info("begin editing keywords");
		
		BaseMod.addKeyword(new String[] {"unsteady"}, "Block is reduced each turn.");
		BaseMod.addKeyword(new String[] {"stunned"}, "Affected does nothing.");
		BaseMod.addKeyword(new String[] {"Potential", "potential"},
				"Whole parts are spent to give the equivalent in Energy.");
		BaseMod.addKeyword(new String[] {"Weakest", "weakest"},
				"Colorless cards that are weak, and have Exhaust and Ethereal.");
		
		logger.info("done editing keywords");
		
	}
	
	@Override
	public void receiveEditStrings()
	{
	    logger.info("begin editing strings");
	    
	    LoadRelicsJSON();
	    LoadPowersJSON();
	    LoadModifiersJSON();
	    LoadCardsJSON();
	    LoadPotionsJSON();
	    
	    logger.info("done editing strings");
	}
	
	private void LoadRelicsJSON() {
		String relicStringsAddress = "ww_relics/localization/eng/WW_Relics_Relics.json";
	    String relicStrings = getJsonText(relicStringsAddress);
	    
	    BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
	}
	
	private void LoadPowersJSON() {
		String powerStringsAddress = "ww_relics/localization/eng/WW_Relics_Powers.json";
	    String powerStrings = getJsonText(powerStringsAddress);
	    
	    BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
	}
	
	private void LoadModifiersJSON() {
		String modifiersStringsAddress = "ww_relics/localization/eng/WW_Relics_Modifiers.json";
	    String modifiersStrings = getJsonText(modifiersStringsAddress);
	    
	    BaseMod.loadCustomStrings(RunModStrings.class, modifiersStrings);
	}
	
	private void LoadCardsJSON() {
		String cardsStringsAddress = "ww_relics/localization/eng/WW_Relics_Cards.json";
	    String cardsStrings = getJsonText(cardsStringsAddress);
	    
	    BaseMod.loadCustomStrings(CardStrings.class, cardsStrings);
	}
	
	private void LoadPotionsJSON() {
		String potionsStringsAddress = "ww_relics/localization/eng/WW_Relics_Potions.json";
		String potionsStrings = getJsonText(potionsStringsAddress);
		
		BaseMod.loadCustomStrings(PotionStrings.class, potionsStrings);
	}
	
	@Override
	public void receiveEditRelics() {
		logger.info("Begin adding relics");
		addRyuRelics();
		addKenRelics();
		addChunLiRelics();
		addGuileRelics();
		addCharacterCameoRelics();
		logger.info("Done adding relics");
	}

	private void addRyuRelics() {
		BaseMod.addRelic(new DuffelBag(), RelicType.SHARED);
		BaseMod.addRelic(new FightingGloves(), RelicType.SHARED);
		BaseMod.addRelic(new RedHeadband(), RelicType.SHARED);
	}
	
	private void addKenRelics() {
		BaseMod.addRelic(new BlackTrainingShirt(), RelicType.SHARED);
		BaseMod.addRelic(new RedGi(), RelicType.SHARED);
		BaseMod.addRelic(new UnceasingFlame(), RelicType.SHARED);
	}
	
	private void addChunLiRelics() {
		BaseMod.addRelic(new Handcuffs(), RelicType.SHARED);
		BaseMod.addRelic(new SpikyBracers(), RelicType.SHARED);
		BaseMod.addRelic(new WhiteBoots(), RelicType.SHARED);
	}
	
	private void addGuileRelics() {
		BaseMod.addRelic(new ArmyBoots(), RelicType.SHARED);
		BaseMod.addRelic(new ChainWithNametags(), RelicType.SHARED);
		BaseMod.addRelic(new CombatFatigues(), RelicType.SHARED);
	}
	
	private void addCharacterCameoRelics() {
		addDanRelics();
		addSakuraRelics();
	}
	
	private void addDanRelics() {
		BaseMod.addRelic(new NotStrongestFightingStyleGuidebook(), RelicType.SHARED);
	}
	
	private void addSakuraRelics() {
		BaseMod.addRelic(new SchoolBackpack(), RelicType.SHARED);
	}
	
	@Override
	public void receiveEditCards() {
		logger.info("Begin adding cards");
		
		logger.info("Done adding cards");
	}
	
	@Override
	public void receiveCustomModeMods(List<CustomMod> list) {
		RelicSetModifiers.addRelicSetModifiers(list);
		HarderRunModifiers.AddHarderSetModifiers(list);
	 }
	 
	@Override
	public void receivePostCreateStartingRelics(AbstractPlayer.PlayerClass playerClass, ArrayList<String> relics) {
		if (isCustomModActive(RelicSetModifiers.WANDERING_WARRIOR_ID)) {
			RelicSetModifiers.addWanderingWarriorRelicsToCustomRun(relics);
        }	
		
		if (isCustomModActive(RelicSetModifiers.BLUE_JADE_ID)) {
			RelicSetModifiers.addBlueJadeRelicsToCustomRun(relics);
        }
		
		if (isCustomModActive(RelicSetModifiers.INDESTRUCTIBLE_FORTRESS_ID)) {
			RelicSetModifiers.addIndestructibleFortressToCustomRun(relics);
		}
		
		if (isCustomModActive(RelicSetModifiers.BLAZING_FIST_ID)) {
			RelicSetModifiers.addBlazingFistToCustomRun(relics);
		}
	}
	
    @Override
    public void receivePostDungeonInitialize() {
        if (isCustomModActive(HarderRunModifiers.WAIT_NO_REST_BETWEEN_ROUNDS_ID)) {
        	HarderRunModifiers.AddNoRestBetweenRoundsEffectsToRun();
        }

		if (isCustomModActive(HarderRunModifiers.FRESH_START_ID)) {
			HarderRunModifiers.AddFreshStartEffectsToRun();
		}
		
		if (isCustomModActive(HarderRunModifiers.HALF_HEALTH_BAR_ID)) {
			HarderRunModifiers.AddHaltHealthBarEffectsToRun();
		}
		
		if (isCustomModActive(HarderRunModifiers.QUARTER_HEALTH_BAR_ID)) {
			HarderRunModifiers.AddQuarterHealthBarEffectsToRun();
		}
    }
    
    
	
	public static void loadRunData() {
        logger.info("Loading Save Data");
        try {
            final SpireConfig config = new SpireConfig("WorldWarriorsRelicsMod", "SaveData");
            WhiteBoots.load(config);
            FightingGloves.load(config);
            DuffelBag.load(config);
            UnceasingFlame.load(config);
            SchoolBackpack.load(config);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Done Loading Save Data");
	}
	
    public static void saveRunData() {
        logger.info("Saving Data");
        try {
        	final SpireConfig config = new SpireConfig("WorldWarriorsRelicsMod", "SaveData");
            WhiteBoots.save(config);
            FightingGloves.save(config);
            DuffelBag.save(config);
            UnceasingFlame.save(config);
            SchoolBackpack.save(config);
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
        logger.info("Done Saving Data");
    }
    
    public static void clearRunData() {
    	logger.info("Clearing Saved Data");
        try {
        	final SpireConfig config = new SpireConfig("WorldWarriorsRelicsMod", "SaveData");
        	config.clear();
            WhiteBoots.clear(config);
            FightingGloves.clear(config);
            DuffelBag.clear(config);
            UnceasingFlame.clear(config);
            SchoolBackpack.clear(config);
        	config.save();

        }
        catch (IOException e) {
        	e.printStackTrace();
        }
        logger.info("Done Clearing Saved Data");
    }
    
    public void receiveStartGame() {
    	loadRunData();
    }

    public static boolean isCustomModActive(String ID) {
        return CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(ID);
    }
    
	@Override
	public void receivePostInitialize() {

		addPotions();
		
		String modBadgeAddress = "ww_relics/assets/img/modbadge/ModBadgePlaceholder.png";
		Texture badgeTexture = new Texture(Gdx.files.internal(modBadgeAddress));
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
	}
	
	public void addPotions() {
		
		logger.info("Begin adding potions");
		BaseMod.addPotion(ChallengerCoin.class, Color.RED.cpy(), Color.CORAL.cpy(), Color.BLUE.cpy(),
				ChallengerCoin.ID);
		logger.info("Done adding potions");
	}
}
