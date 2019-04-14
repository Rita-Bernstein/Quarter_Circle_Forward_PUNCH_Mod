package ww_relics.relics.guile;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import basemod.abstracts.CustomRelic;
import ww_relics.relics.ryu.DuffelBag;
import ww_relics.resources.relic_graphics.GraphicResources;

public class ArmyBoots extends CustomRelic  {
	public static final String ID = "WW_Relics:Army_Boots";
	
	public ArmyBoots() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.COMMON, LandingSound.SOLID);
	}
	
	public String getUpdatedDescription() {
		return "test";
	}
	
	public AbstractRelic makeCopy() {
		return new ArmyBoots();
	}	
}
