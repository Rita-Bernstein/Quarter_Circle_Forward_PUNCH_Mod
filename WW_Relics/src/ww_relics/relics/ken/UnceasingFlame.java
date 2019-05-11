package ww_relics.relics.ken;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class UnceasingFlame extends CustomRelic {

	public static final String ID = "WW_Relics:Unceasing_Flame";
	public static final int NUMBER_OF_ATTACKS_TO_TRIGGER_CHARGE_UP = 3;
	public static final int HOW_MUCH_CHARGE_INCREASES_PER_TRIGGER = 1;
	public static final int MAX_NUMBER_OF_CHARGES = 6;
	
	public UnceasingFlame() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.RARE, LandingSound.MAGICAL);
		
		setCounter(0);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public AbstractRelic makeCopy() {
		return new UnceasingFlame();
	}

}
