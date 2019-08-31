package qcfpunch.relics.cammy;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Forethought;
import com.megacrit.cardcrawl.cards.green.Setup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import basemod.abstracts.CustomRelic;
import qcfpunch.QCFPunch_MiscCode;
import qcfpunch.resources.relic_graphics.GraphicResources;

public class SpecialOpsInsignia extends CustomRelic  {

	public static final String ID = QCFPunch_MiscCode.returnPrefix() +
			"Special_Ops_Insignia";
	
	public static final int AMOUNT_OF_CARDS_TO_DRAW = 4;
	
	public int number_of_usual_card_draw_per_turn;
	public int number_of_actual_normal_card_draw_per_turn;
	public int extra_cards_drawn_this_turn;
	
	public boolean effect_happened = false;
	
	public SpecialOpsInsignia() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.RARE, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + AMOUNT_OF_CARDS_TO_DRAW + DESCRIPTIONS[1];
	}

	@Override
	public void onEquip() {
		counter = 0;
	}
	
	@Override
	public void atBattleStartPreDraw() {
		
		CharSelectInfo char_info = AbstractDungeon.player.getLoadout();
		
		number_of_usual_card_draw_per_turn = char_info.cardDraw;
		
		counter = 0;
	}
	
	@Override
	public void atTurnStart() {
		
		if (AbstractDungeon.player.gameHandSize <= 
				number_of_usual_card_draw_per_turn) {
			
			number_of_actual_normal_card_draw_per_turn = 
					AbstractDungeon.player.gameHandSize;
			
		} else {
			
			number_of_actual_normal_card_draw_per_turn =
					number_of_usual_card_draw_per_turn;
			
		}
		extra_cards_drawn_this_turn = 0;
	}
	
	@Override
	public void onCardDraw(AbstractCard drawnCard) {
		
		if (number_of_actual_normal_card_draw_per_turn > 0) {
			number_of_actual_normal_card_draw_per_turn--;
		} else if (extra_cards_drawn_this_turn < AMOUNT_OF_CARDS_TO_DRAW){
			extra_cards_drawn_this_turn++;
			counter = extra_cards_drawn_this_turn;
		}
		
		if (extra_cards_drawn_this_turn >= AMOUNT_OF_CARDS_TO_DRAW) {
			AbstractCard new_setup = new Setup();
			AbstractCard new_breakthrough = new Forethought();
			
			new_setup.isEthereal = true; new_setup.exhaust = true;
			new_breakthrough.isEthereal = true; new_breakthrough.exhaust = true;
			
			flash();
			AbstractDungeon.actionManager.addToBottom(
					new MakeTempCardInHandAction(new_setup));
			AbstractDungeon.actionManager.addToBottom(
					new MakeTempCardInHandAction(new_breakthrough));
			extra_cards_drawn_this_turn = 0;
			counter = extra_cards_drawn_this_turn;
		}
		
	}
	
	@Override
	public void onPlayerEndTurn() {
		extra_cards_drawn_this_turn = 0;
		counter = extra_cards_drawn_this_turn;
	}

	@Override
	public AbstractRelic makeCopy() {
		return new SpecialOpsInsignia();
	}
	
}
