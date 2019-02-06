package ww_relics.relics.ryu;

import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;

public class FightingGloves extends CustomRelic {
	public static final String ID = "WW_Relics:Fighting_Gloves";
	private static final int EXTRA_UPGRADES_PER_UPGRADE = 1;
	
	public FightingGloves() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.RARE, LandingSound.SOLID);
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