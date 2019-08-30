package qcfpunch.relics.cammy;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import qcfpunch.QCFPunch_MiscCode;
import qcfpunch.actions.DrawRandomCardToHandAction;
import qcfpunch.resources.relic_graphics.GraphicResources;

public class RedBeret extends CustomRelic {

	public static final String ID = QCFPunch_MiscCode.returnPrefix() + "Red_Beret";
	
	public static final int AMOUNT_OF_CARDS_DRAWN = 2; 
	
	public boolean effect_happened = false;
	
	public RedBeret() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.COMMON, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + AMOUNT_OF_CARDS_DRAWN + DESCRIPTIONS[1];
	}
	
	@Override
	public void onCardDraw(AbstractCard drawnCard) {
		super.onCardDraw(drawnCard);
		
		if (effectCanBeApplied()) {
			effect_happened = true;
			flash();
			AbstractDungeon.actionManager.addToBottom(
					new DrawCardAction(AbstractDungeon.player, AMOUNT_OF_CARDS_DRAWN));
			
		} else if (QCFPunch_MiscCode.abscenceOfNoDraw()) {
			AbstractDungeon.player.getPower("No Draw").flash();
		}
	}
	
	public boolean effectCanBeApplied() {
		return 	QCFPunch_MiscCode.abscenceOfNoDraw() && !effect_happened;
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new RedBeret();
	}
	
}