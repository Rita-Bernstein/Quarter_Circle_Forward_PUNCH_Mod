package qcfpunch.relics.cammy;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import qcfpunch.QCFPunch_MiscCode;
import qcfpunch.resources.relic_graphics.GraphicResources;

public class GreenLeotard extends CustomRelic {

	public static final String ID = QCFPunch_MiscCode.returnPrefix() + "Green_Leotard";
	
	public static final int AMOUNT_OF_BLOCK_GAINED_PER_DRAW = 2;
	
	public GreenLeotard() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.COMMON, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + AMOUNT_OF_BLOCK_GAINED_PER_DRAW + DESCRIPTIONS[1];
	}
	
	@Override
	public void onCardDraw(AbstractCard drawnCard) {
		
		AbstractDungeon.actionManager.addToTop(
				new GainBlockAction(AbstractDungeon.player,
									AbstractDungeon.player,
									AMOUNT_OF_BLOCK_GAINED_PER_DRAW));
		flash();

	}
	
	public AbstractRelic makeCopy() {
		return new GreenLeotard();
	}
}	