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
import ww_relics.relics.ryu.RedHeadband;

@SpireInitializer
public class WW_Relics_Mod implements EditStringsSubscriber, EditRelicsSubscriber,
		PostInitializeSubscriber
		{

	//What exactly this does?
	public static final Logger logger = LogManager.getLogger(WW_Relics_Mod.class.getName()); // lets us log output

	public static final String MODNAME = "World Warriors' Relics"; // mod name
	public static final String AUTHOR = "Clauvin aka Dungeon Explorer Lan"; // your name
	public static final String DESCRIPTION = "v0.0.7" +
			"\r\n Adds a relic based in SF's Ryu to the game.";
	
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
	    
	    logger.info("Here are the relicStrings: " + relicStrings);
	    
	    if (relicStrings == "") {
	    	
	    	relicStringsAddress = "src/ww_relics/localization/eng/WW_Relics.json";
	    	relicStrings = getJsonText(relicStringsAddress);
	    	
	    	logger.info("Now here are the relicStrings: " + relicStrings);		
	    			
	    }
	    
	    if (relicStrings == "") {
	    	
	    	relicStringsAddress = "localization/eng/WW_Relics.json";
	    	relicStrings = getJsonText(relicStringsAddress);
	    	
	    	logger.info("NOW here are the relicStrings: " + relicStrings);	
	    }
	    
	    BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
	    logger.info("done editing strings");
	}
	
	@Override
	public void receiveEditRelics() {
		logger.info("Begin adding relics");
		BaseMod.addRelic(new RedHeadband(), RelicType.SHARED);
		logger.info("Done adding relics");
	}
	
	@Override
	public void receivePostInitialize() {

		String modBadgeAddress = "ww_relics/assets/img/modbadge/ModBadgePlaceholder.png";
		Texture badgeTexture = new Texture(Gdx.files.internal(modBadgeAddress));
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
	}
}
