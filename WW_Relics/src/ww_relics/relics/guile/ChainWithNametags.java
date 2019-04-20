package ww_relics.relics.guile;

import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class ChainWithNametags extends CustomRelic {
	public static final String ID = "WW_Relics:Chain_With_Nametags";
	
	public ChainWithNametags() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.UNCOMMON, LandingSound.CLINK);
	}
	
	public String getUpdatedDescription() {
		return "test";
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new ChainWithNametags();
	}

}
