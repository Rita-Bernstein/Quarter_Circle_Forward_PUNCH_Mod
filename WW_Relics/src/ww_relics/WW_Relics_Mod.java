package ww_relics;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.screens.custom.CustomMod;

import basemod.BaseMod;
//import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import ww_relics.events.act2.FightingNoisesEvent;
import ww_relics.modifiers.*;
import ww_relics.monsters.elites.TiredGremlinNob;
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
import ww_relics.relics.mortal_kombat.ExtraSkeleton;
import ww_relics.relics.mortal_kombat.NeverendingBlood;
import ww_relics.relics.ryu.*;

@SpireInitializer
public class WW_Relics_Mod implements AddCustomModeModsSubscriber, EditStringsSubscriber, EditRelicsSubscriber,
			EditCardsSubscriber, 
			EditKeywordsSubscriber, PostInitializeSubscriber, PostDungeonInitializeSubscriber, 
			PostCreateStartingRelicsSubscriber, StartGameSubscriber
	{

	public static final Logger logger = LogManager.getLogger(WW_Relics_Mod.class.getName()); // lets us log output

	public static final String MODNAME = "World Warriors' Relics"; // mod name
	public static final String MODID = "ww_relics";
	public static final String AUTHOR = "Clauvin aka Dungeon Explorer Lan"; // your name
	public static final String DESCRIPTION = "v0.11.43" +
			"\r\n"
		  + "\r\n Adds sixteen relics based in SF2's main characters, seven game modifiers, one event and one potion."
		  + "\r\n"
		  + "\r\n v1.0 will have 32+ relics."
		  + "\r\n"
		  + "\r\n Most of the images in the mod are temporary and will be substituted/improved on v1.0.";
	
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
		
		Gson gson = new Gson();
		String json = Gdx.files.internal("ww_relics/localization/eng/WW_Relics_Keywords.json").
						readString(String.valueOf(StandardCharsets.UTF_8)); 
		KeywordWithProper[] keywords = gson.fromJson(json, KeywordWithProper[].class);
	    
		if (keywords != null) {
            for (KeywordWithProper keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
		
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
	    LoadEventsJSON();
	    LoadMonstersJSON();
	    
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
	
	private void LoadEventsJSON() {
		String eventsStringsAddress = "ww_relics/localization/eng/WW_Relics_Events.json";
		String eventsStrings = getJsonText(eventsStringsAddress);
		
		BaseMod.loadCustomStrings(EventStrings.class, eventsStrings);
	}
	
	private void LoadMonstersJSON() {
		String monstersStringsAddress = "ww_relics/localization/eng/WW_Relics_Monsters.json";
		String monstersStrings = getJsonText(monstersStringsAddress);
		
		BaseMod.loadCustomStrings(MonsterStrings.class, monstersStrings);
	}
	
	@Override
	public void receiveEditRelics() {
		logger.info("Begin adding relics");
		addRyuRelics();
		addKenRelics();
		addChunLiRelics();
		addGuileRelics();
		addCharacterCameoRelics();
		addGameCameoRelics();
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
	
	private void addGameCameoRelics() {
		addMortalKombatRelics();
	}
	
	private void addMortalKombatRelics() {
		BaseMod.addRelic(new NeverendingBlood(), RelicType.SHARED);
		BaseMod.addRelic(new ExtraSkeleton(), RelicType.SHARED);
	}
	
	@Override
	public void receiveEditCards() {
		//logger.info("Begin adding cards");
		
		//logger.info("Done adding cards");
	}
	
	public void addMonsters() {
		BaseMod.addMonster(TiredGremlinNob.ID, () -> new TiredGremlinNob(0.0f, 0.0f));
	}
	
	@Override
	public void receiveCustomModeMods(List<CustomMod> list) {
		RelicSetModifiers.addRelicSetModifiers(list);
		EasierRunModifiers.AddEasierSetModifiers(list);
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
        if (isCustomModActive(EasierRunModifiers.LOWERING_THE_HANDICAP_ID)) {
        	EasierRunModifiers.AddLoweringTheHandicaptHealthBarEffectsToRun();
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
        logger.info("Loading World Warriors Relics data from");
    	logger.info(WW_Relics_MiscelaneaCode.classAndSaveSlotText());
        try {
            final SpireConfig config = new SpireConfig("WorldWarriorsRelicsMod", "SaveData");
            WhiteBoots.load(config);
            DuffelBag.load(config);
            FightingGloves.load(config);
            UnceasingFlame.load(config);
            SchoolBackpack.load(config);
            if ((AbstractDungeon.actNum == 1) && (AbstractDungeon.floorNum < 2)) {
            	ChallengerCoin.sanitizingActOne(config);
            }
            ChallengerCoin.load(config);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Done loading World Warriors Relics data");
	}
	
    public static void saveRunData() {
        logger.info("Saving World Warriors Relics data from");
    	logger.info(WW_Relics_MiscelaneaCode.classAndSaveSlotText());
        try {
        	final SpireConfig config = new SpireConfig("WorldWarriorsRelicsMod", "SaveData");
            WhiteBoots.save(config);
            DuffelBag.save(config);
            FightingGloves.save(config);
            UnceasingFlame.save(config);
            SchoolBackpack.save(config);
            ChallengerCoin.save(config);
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
        logger.info("Done saving World Warriors Relics data");
    }
    
    public static void clearRunData() {
    	logger.info("Clearing Saved World Warriors Relics data from");
    	logger.info(WW_Relics_MiscelaneaCode.classAndSaveSlotText());
        try {
        	final SpireConfig config = new SpireConfig("WorldWarriorsRelicsMod", "SaveData");
            WhiteBoots.clear(config);
            DuffelBag.clear(config);
            FightingGloves.clear(config);
            UnceasingFlame.clear(config);
            SchoolBackpack.clear(config);
            ChallengerCoin.clear(config);
        	config.save();

        }
        catch (IOException e) {
        	e.printStackTrace();
        }
        logger.info("Done clearing saved World Warriors Relics data");
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
		addMonsters();
		addEvents();
		
		String modBadgeAddress = "ww_relics/resources/ModBadge.png";
		Texture badgeTexture = new Texture(Gdx.files.internal(modBadgeAddress));
		//uncomment line below and replaces settingsPanel to the null when
		//the mod panel has something to show
        //ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, null);
	}
	
	public void addPotions() {
		
		logger.info("Begin adding potions");
		BaseMod.addPotion(ChallengerCoin.class, Color.LIME.cpy(), Color.PINK.cpy(), Color.BLUE.cpy(),
				ChallengerCoin.ID);
		logger.info("Done adding potions");
	}
	
	public void addEvents() {
		
		BaseMod.addEvent(FightingNoisesEvent.ID, FightingNoisesEvent.class,
				FightingNoisesEvent.TO_WHICH_ACT_ADD);
		
	}
}
