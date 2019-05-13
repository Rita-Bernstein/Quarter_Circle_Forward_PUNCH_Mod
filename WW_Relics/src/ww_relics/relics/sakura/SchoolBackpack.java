package ww_relics.relics.sakura;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class SchoolBackpack extends CustomRelic {

	public static final String ID = "WW_Relics:School_Backpack";
	
	public SchoolBackpack() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"), 
				RelicTier.COMMON, LandingSound.FLAT);	
	}
	
	@Override
	public CustomRelic makeCopy() {
		// TODO Auto-generated method stub
		return null;
	}

}
