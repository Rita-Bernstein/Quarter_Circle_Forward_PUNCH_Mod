package qcfpunch.relics.cammy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import basemod.abstracts.CustomRelic;
import qcfpunch.QCFPunch_MiscCode;
import qcfpunch.resources.relic_graphics.GraphicResources;

public class GreenLeotard extends CustomRelic {

	public static final String ID = QCFPunch_MiscCode.returnPrefix() + "Green_Leotard";
	
	public static final int AMOUNT_OF_BLOCK_GAINED_PER_DRAW = 2;
	
	public int number_of_usual_card_draw_per_turn;
	public int number_of_actual_card_draw_per_turn;
	
	public static final Logger logger = LogManager.getLogger(GreenLeotard.class.getName());
	
	public GreenLeotard() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.UNCOMMON, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + AMOUNT_OF_BLOCK_GAINED_PER_DRAW + DESCRIPTIONS[1];
	}
	
	@Override
	public void atBattleStartPreDraw() {
		
		CharSelectInfo char_info = AbstractDungeon.player.getLoadout();
		
		number_of_usual_card_draw_per_turn = char_info.cardDraw;
	}
	
	@Override
	public void atTurnStart() {
		if (AbstractDungeon.player.gameHandSize <= 
				number_of_usual_card_draw_per_turn) {
			
			number_of_actual_card_draw_per_turn = AbstractDungeon.player.gameHandSize;
			
		} else {
			
			number_of_actual_card_draw_per_turn = number_of_usual_card_draw_per_turn;
			
		}
	}
	
	@Override
	public void onCardDraw(AbstractCard drawnCard) {
		
		if (number_of_actual_card_draw_per_turn > 0) {
			number_of_actual_card_draw_per_turn--;
		} else {
			AbstractDungeon.actionManager.addToTop(
					new GainBlockAction(AbstractDungeon.player,
										AbstractDungeon.player,
										AMOUNT_OF_BLOCK_GAINED_PER_DRAW));
			flash();
		}
		
	}
	
	public AbstractRelic makeCopy() {
		return new GreenLeotard();
	}
}	