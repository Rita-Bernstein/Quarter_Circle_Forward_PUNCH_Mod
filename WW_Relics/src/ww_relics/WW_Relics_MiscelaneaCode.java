package ww_relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;

public class WW_Relics_MiscelaneaCode {

	public final static String infinite_spire_class_code = "infinitespire.InfiniteSpire";
	public final static String replay_the_spire_class_code = "replayTheSpire.ReplayTheSpireMod";
	public final static PlayerClass[] base_game_player_classes =
			{PlayerClass.IRONCLAD, PlayerClass.THE_SILENT, PlayerClass.DEFECT};

	//just stole this code from ReplayTheSpire who stole this from blank lol
    public static boolean checkForMod(final String classPath) {
        try {
            Class.forName(classPath);
            WW_Relics_Mod.logger.info("Found mod: " + classPath);
            return true;
        }
        catch (ClassNotFoundException | NoClassDefFoundError ex) {
        	WW_Relics_Mod.logger.info("Could not find mod: " + classPath);
            return false;
        }
    }
    
    //just stole this code from ReplayTheSpire who stole this from blank lol
    public static boolean silentlyCheckForMod(final String classPath) {
        try {
            Class.forName(classPath);
            return true;
        }
        catch (ClassNotFoundException | NoClassDefFoundError ex) {
            return false;
        }
    }
	
}
