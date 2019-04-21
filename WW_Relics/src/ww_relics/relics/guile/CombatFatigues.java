package ww_relics.relics.guile;

import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class CombatFatigues extends CustomRelic {

	public static final String ID = "WW_Relics:Combat_Fatigues";
	
	public CombatFatigues() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.RARE, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return "test";
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new CombatFatigues();
	}
	
}
