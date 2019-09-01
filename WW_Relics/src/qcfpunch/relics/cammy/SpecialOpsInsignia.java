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
import qcfpunch.actions.SetEtherealOfCardAtCombat;
import qcfpunch.actions.SetExhaustOfCardAtCombat;
import qcfpunch.resources.relic_graphics.GraphicResources;

public class SpecialOpsInsignia extends CustomRelic  {

	public static final String ID = QCFPunch_MiscCode.returnPrefix() +
			"Special_Ops_Insignia";
	
	public static final int CARDS_TO_DRAW_TO_APPLY_EFFECT = 4;
	
	public int usual_hand_per_turn_start;
	public int actual_hand_per_turn_start;
	public int extra_cards_drawn_this_turn;
	
	public SpecialOpsInsignia() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.RARE, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + CARDS_TO_DRAW_TO_APPLY_EFFECT + DESCRIPTIONS[1];
	}

	@Override
	public void onEquip() {
		counter = 0;
	}
	
	@Override
	public void atBattleStartPreDraw() {
		
		CharSelectInfo char_info = AbstractDungeon.player.getLoadout();
		
		usual_hand_per_turn_start = char_info.cardDraw;
		
		counter = 0;
	}
	
	@Override
	public void atTurnStart() {
		
		if (AbstractDungeon.player.gameHandSize <= usual_hand_per_turn_start)
			actual_hand_per_turn_start = AbstractDungeon.player.gameHandSize;
		else actual_hand_per_turn_start = usual_hand_per_turn_start;
			
		extra_cards_drawn_this_turn = 0;
		counter = 0;
	}
	
	@Override
	public void onCardDraw(AbstractCard drawnCard) {
		
		if (actual_hand_per_turn_start > 0) actual_hand_per_turn_start--;
		else if (extra_cards_drawn_this_turn < CARDS_TO_DRAW_TO_APPLY_EFFECT){
			extra_cards_drawn_this_turn++;
			counter = extra_cards_drawn_this_turn;
		}
		
		if (extra_cards_drawn_this_turn >= CARDS_TO_DRAW_TO_APPLY_EFFECT) {
			AbstractCard new_setup = new Setup();
			AbstractCard new_breakthrough = new Forethought();
			
			flash();
			AbstractDungeon.actionManager.addToBottom(
				new MakeTempCardInHandAction(new_setup, false, true));
			AbstractDungeon.actionManager.addToBottom(
				new MakeTempCardInHandAction(new_breakthrough, false, true));
			
			AbstractDungeon.actionManager.addToBottom(
				new SetExhaustOfCardAtCombat(new_setup.uuid, true));
			AbstractDungeon.actionManager.addToBottom(
				new SetEtherealOfCardAtCombat(new_setup.uuid, true));
			AbstractDungeon.actionManager.addToBottom(
				new SetExhaustOfCardAtCombat(new_breakthrough.uuid, true));
			AbstractDungeon.actionManager.addToBottom(
				new SetEtherealOfCardAtCombat(new_breakthrough.uuid, true));
			
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
