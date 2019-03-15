package ww_relics.relics.ryu;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;

public class FightingGloves extends CustomRelic {
	
	public static final Logger logger = LogManager.getLogger(FightingGloves.class.getName());
	
	public static final String ID = "WW_Relics:Fighting_Gloves";
	private static final int EXTRA_UPGRADES_PER_UPGRADE = 1;
	private static final int INITIAL_CHARGES = 1;
	private static int positive_charges = INITIAL_CHARGES;
	
	public FightingGloves() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.RARE, LandingSound.SOLID);
		belowZeroCheck();
	}
	
	public static int getCharges() {
		return positive_charges;
	}
	
	public static void setCharges(int value) {
		positive_charges = value;
		belowZeroCheck();
	}
	
	public static void belowZeroCheck() {
		if (positive_charges < 0) {
			logger.info("WARNING - For some reason, FightingGloves.positive_charges was with " +
					positive_charges + " value.");
			positive_charges = 0;
			logger.info("And it's now 0. Tell the developer about it? Thanks!");
		}
		
	}
	
	public static void addCharges(int value_to_add) {
		positive_charges += value_to_add;
		belowZeroCheck();
	}
	
	public static void removeCharges(int value_to_subtract) {
		positive_charges -= value_to_subtract;
		belowZeroCheck();
	}
	
	@SuppressWarnings("unused")
	public String getUpdatedDescription() {
		String description = "Something wrong happened, please warn the programmer!";
		
		if (EXTRA_UPGRADES_PER_UPGRADE < 2) {
			description = DESCRIPTIONS[0] + EXTRA_UPGRADES_PER_UPGRADE +
					DESCRIPTIONS[1];
		} else {
			description = DESCRIPTIONS[0] + EXTRA_UPGRADES_PER_UPGRADE +
					DESCRIPTIONS[2];
		} 
		return description;
		
	}
	
	public static void save(final SpireConfig config) {


        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
    		logger.info("Started saving Fighting Gloves information");

            config.setInt("fighting_gloves_1", positive_charges);
            
            try {
				config.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
            logger.info("Finished saving Fighting Gloves info.");
        }
        else {
        	clear(config);
        }

    }
	
	public static void clear(final SpireConfig config) {
        config.remove("fighting_gloves_1");
	}

	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new FightingGloves();
	}
}