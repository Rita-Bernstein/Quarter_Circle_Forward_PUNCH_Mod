package ww_relics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import ww_relics.relics.ryu.RedHeadband;

@SpireInitializer
public class WW_Relics_Mod implements PostInitializeSubscriber,
		EditRelicsSubscriber{

	//What exactly this does?
	public static final Logger logger = LogManager.getLogger(WW_Relics_Mod.class.getName()); // lets us log output

	public static final String MODNAME = "World Warriors' Relics"; // mod name
	public static final String AUTHOR = "Clauvin aka Dungeon Explorer Lan"; // your name
	public static final String DESCRIPTION = "v0.0.1" +
			"\r\n Adds a relic based in SF's Ryu to the game.";
	
	public WW_Relics_Mod() {
		BaseMod.subscribe(this);
	}

	public void receivePostInitialize() {

		//Mod badge
		//Texture badgeTexture = new Texture();
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(null, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
		
	}
	
	public void receiveEditRelics() {
		logger.info("Begin adding relics");
		BaseMod.addRelic(new RedHeadband(), RelicType.SHARED);
		logger.info("Done adding relics");
	}
}
