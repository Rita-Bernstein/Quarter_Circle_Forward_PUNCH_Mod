package qcfpunch.relics.cammy;

import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import qcfpunch.QCFPunch_MiscCode;
import qcfpunch.resources.relic_graphics.GraphicResources;

public class SpecialOpsBrooch extends CustomRelic  {

	public static final String ID = QCFPunch_MiscCode.returnPrefix() +
			"Special_Ops_Brooch";
	
	public static final int AMOUNT_OF_CARDS_TO_DRAW = 4;
	public int cards_drawn_this_turn;
	
	public boolean effect_happened = false;
	
	public SpecialOpsBrooch() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.RARE, LandingSound.FLAT);
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new SpecialOpsBrooch();
	}
	
}
