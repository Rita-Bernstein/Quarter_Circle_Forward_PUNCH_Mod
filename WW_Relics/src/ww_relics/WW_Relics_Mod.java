package ww_relics;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.StartGameSubscriber;
import ww_relics.relics.chun_li.Handcuffs;
import ww_relics.relics.chun_li.SpikyBracers;
import ww_relics.relics.chun_li.WhiteBoots;
import ww_relics.relics.ryu.DuffelBag;
import ww_relics.relics.ryu.RedHeadband;

@SpireInitializer
public class WW_Relics_Mod implements EditStringsSubscriber, EditRelicsSubscriber,
			EditKeywordsSubscriber, PostInitializeSubscriber, StartGameSubscriber
	{

	//What exactly this does?
	public static final Logger logger = LogManager.getLogger(WW_Relics_Mod.class.getName()); // lets us log output

	public static final String MODNAME = "World Warriors' Relics"; // mod name
	public static final String AUTHOR = "Clauvin aka Dungeon Explorer Lan"; // your name
	public static final String DESCRIPTION = "v0.4.0" +
			"\r\n Adds four relics basd in SF2's main characters."
		  + "\r\n v1.0 will have 16+ relics.";
	
	public WW_Relics_Mod() {
		BaseMod.subscribe(this);
	}

	public static void initialize()	{
	    new WW_Relics_Mod();
	}
	
	public String getJsonText(String filepath) {
		return Gdx.files.internal(filepath).readString(String.valueOf(StandardCharsets.UTF_8));
	}
	
	@Override
	public void receiveEditKeywords() {
		BaseMod.addKeyword(new String[] {"unsteady"}, "Block is reduced each turn.");
		BaseMod.addKeyword(new String[] {"stunned"}, "Affected does nothing.");
	}
	
	@Override
	public void receiveEditStrings()
	{
	    logger.info("begin editing strings");
	    
	    LoadRelicsJSON();
	    LoadPowersJSON();
	    
	    logger.info("done editing strings");
	}
	
	private void LoadRelicsJSON() {
		String relicStringsAddress = "ww_relics/localization/eng/WW_Relics_Relics.json";
	    String relicStrings = getJsonText(relicStringsAddress);
	    
	    if (relicStrings == "") {
	    	
	    	relicStringsAddress = "src/ww_relics/localization/eng/WW_Relics_Relics.json";
	    	relicStrings = getJsonText(relicStringsAddress);
	    	
	    }
	    
	    if (relicStrings == "") {
	    	
	    	relicStringsAddress = "localization/eng/WW_Relics_Relics.json";
	    	relicStrings = getJsonText(relicStringsAddress);
	    	
	    }
	    
	    BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
	}
	
	private void LoadPowersJSON() {
		String powerStringsAddress = "ww_relics/localization/eng/WW_Relics_Powers.json";
	    String powerStrings = getJsonText(powerStringsAddress);
	    
	    if (powerStrings == "") {
	    	
	    	powerStringsAddress = "src/ww_relics/localization/eng/WW_Relics_Powers.json";
	    	powerStrings = getJsonText(powerStringsAddress);
	    	
	    }
	    
	    if (powerStrings == "") {
	    	
	    	powerStringsAddress = "localization/eng/WW_Relics_Powers.json";
	    	powerStrings = getJsonText(powerStringsAddress);
	    	
	    }
	    
	    BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
	}
	
	@Override
	public void receiveEditRelics() {
		logger.info("Begin adding relics");
		addChunLiRelics();
		addRyuRelics();
		logger.info("Done adding relics");
	}
	
	private void addChunLiRelics() {
		BaseMod.addRelic(new Handcuffs(), RelicType.SHARED);
		BaseMod.addRelic(new SpikyBracers(), RelicType.SHARED);
		BaseMod.addRelic(new WhiteBoots(), RelicType.SHARED);
	}
	
	private void addRyuRelics() {
		BaseMod.addRelic(new DuffelBag(), RelicType.SHARED);
		//BaseMod.addRelic(new FightingGloves(), RelicType.SHARED);
		BaseMod.addRelic(new RedHeadband(), RelicType.SHARED);
	}
	
	public static void loadRunData() {
        logger.info("Loading Save Data");
        try {
            final SpireConfig config = new SpireConfig("WorldWarriorsRelicsMod", "SaveData");
            SpikyBracers.load(config);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    public static void saveRunData() {
        logger.info("Saving Data");
        try {
        	final SpireConfig config = new SpireConfig("WorldWarriorsRelicsMod", "SaveData");
            SpikyBracers.save(config);
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    public static void clearRunData() {
    	logger.info("Clearing Saved Data");
        try {
        	final SpireConfig config = new SpireConfig("WorldWarriorsRelicsMod", "SaveData");
        	config.clear();
            SpikyBracers.clear(config);
        	config.save();

        }
        catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    public void receiveStartGame() {
    	loadRunData();
    }

	@Override
	public void receivePostInitialize() {

		String modBadgeAddress = "ww_relics/assets/img/modbadge/ModBadgePlaceholder.png";
		Texture badgeTexture = new Texture(Gdx.files.internal(modBadgeAddress));
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
	}
}
