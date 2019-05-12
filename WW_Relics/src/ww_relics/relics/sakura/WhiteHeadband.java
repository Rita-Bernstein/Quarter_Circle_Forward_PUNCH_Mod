package ww_relics.relics.sakura;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class WhiteHeadband extends CustomRelic {

	public static final String ID = "WW_Relics:White_Headband";
	
	public WhiteHeadband() {
		super(ID, GraphicResources.LoadRelicImage("Red_Headband - headband-knot - Delapouite - CC BY 3.0.png"), 
				RelicTier.COMMON, LandingSound.FLAT);	
	}
	
	@Override
	public CustomRelic makeCopy() {
		// TODO Auto-generated method stub
		return null;
	}

}
