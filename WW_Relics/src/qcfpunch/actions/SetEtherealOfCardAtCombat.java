package qcfpunch.actions;

import java.util.UUID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SetEtherealOfCardAtCombat extends AbstractGameAction {

	UUID uuid;
	boolean ethereal;
	
	public SetEtherealOfCardAtCombat(UUID uuid, boolean will_be_ethereal) {
		this.uuid = uuid;
		this.ethereal = will_be_ethereal;
	}
	
	@Override
	public void update() {
		
		if (!this.isDone) {
			
			CardGroup hand = AbstractDungeon.player.hand;
			CheckGroupForCardToSet(hand);
			if (this.isDone) return;
			
			CardGroup discard = AbstractDungeon.player.discardPile;
			CheckGroupForCardToSet(discard);
			if (this.isDone) return;
			
			CardGroup deck = AbstractDungeon.player.drawPile;
			CheckGroupForCardToSet(deck);
			if (this.isDone) return;
			
			CardGroup exhausted = AbstractDungeon.player.exhaustPile;
			CheckGroupForCardToSet(exhausted);
			this.isDone = true;
			
		}

	}
	
	private void CheckGroupForCardToSet(CardGroup card_group) {
		for (int i = 0; i < card_group.size(); i++) {
			if (card_group.getNCardFromTop(i).uuid == this.uuid) {
				card_group.getNCardFromTop(i).isEthereal = this.ethereal;
				this.isDone = true;
				break;
			}
		}
	}
	
}
