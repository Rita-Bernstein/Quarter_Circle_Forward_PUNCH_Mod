import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.localization.RelicStrings;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import ww_relics.relics.chun_li.Handcuffs;
import ww_relics.relics.chun_li.WhiteBoots;
import ww_relics.relics.ryu.DuffelBag;
import ww_relics.relics.ryu.RedHeadband;

@SpireInitializer
public class WW_Relics_Mod implements EditStringsSubscriber, EditRelicsSubscriber,
		PostInitializeSubscriber
		{

	//What exactly this does?
	public static final Logger logger = LogManager.getLogger(WW_Relics_Mod.class.getName()); // lets us log output

	public static final String MODNAME = "World Warriors' Relics"; // mod name
	public static final String AUTHOR = "Clauvin aka Dungeon Explorer Lan"; // your name
	public static final String DESCRIPTION = "v0.3.0" +
			"\r\n Adds three relics basd in SF2's main characters."
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
	public void receiveEditStrings()
	{
	    logger.info("begin editing strings");
	    
	    String relicStringsAddress = "ww_relics/localization/eng/WW_Relics.json";
	    String relicStrings = getJsonText(relicStringsAddress);
	    
	    if (relicStrings == "") {
	    	
	    	relicStringsAddress = "src/ww_relics/localization/eng/WW_Relics.json";
	    	relicStrings = getJsonText(relicStringsAddress);
	    	
	    }
	    
	    if (relicStrings == "") {
	    	
	    	relicStringsAddress = "localization/eng/WW_Relics.json";
	    	relicStrings = getJsonText(relicStringsAddress);
	    	
	    }
	    
	    BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
	    logger.info("done editing strings");
	}
	
	@Override
	public void receiveEditRelics() {
		logger.info("Begin adding relics");
		addChunLiRelics();
		addRyuRelics();
		logger.info("Done adding relics");
	}
	
	private void addChunLiRelics() {
		BaseMod.addRelic(new WhiteBoots(), RelicType.SHARED);
		BaseMod.addRelic(new Handcuffs(), RelicType.SHARED);
	}
	
	private void addRyuRelics() {
		BaseMod.addRelic(new DuffelBag(), RelicType.SHARED);
		//BaseMod.addRelic(new FightingGloves(), RelicType.SHARED);
		BaseMod.addRelic(new RedHeadband(), RelicType.SHARED);
	}
	
	@Override
	public void receivePostInitialize() {

		String modBadgeAddress = "ww_relics/assets/img/modbadge/ModBadgePlaceholder.png";
		Texture badgeTexture = new Texture(Gdx.files.internal(modBadgeAddress));
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
	}
}
