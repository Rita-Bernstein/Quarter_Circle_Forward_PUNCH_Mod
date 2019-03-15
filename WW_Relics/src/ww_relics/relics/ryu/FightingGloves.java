package ww_relics.relics.ryu;

import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;

public class FightingGloves extends CustomRelic {
	public static final String ID = "WW_Relics:Fighting_Gloves";
	private static final int EXTRA_UPGRADES_PER_UPGRADE = 1;
	private static final int INITIAL_CHARGES = 0;
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
		if (positive_charges < 0) positive_charges = 0;
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

	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new FightingGloves();
	}
}