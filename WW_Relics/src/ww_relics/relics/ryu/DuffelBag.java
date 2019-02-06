package ww_relics.relics.ryu;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import basemod.abstracts.CustomRelic;

public class DuffelBag extends CustomRelic {
	public static final String ID = "WW_Relics:Duffel_Bag";
	private static final int NUMBER_OF_RANDOM_COMMON_RELICS = 2;
	
	public DuffelBag() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.UNCOMMON, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_RANDOM_COMMON_RELICS +
				DESCRIPTIONS[1];
	}
	
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new DuffelBag();
	}
	
	
}
