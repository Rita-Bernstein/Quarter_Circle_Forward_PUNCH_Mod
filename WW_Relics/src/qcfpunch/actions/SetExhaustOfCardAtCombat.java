package qcfpunch.actions;

import java.util.UUID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SetExhaustOfCardAtCombat extends AbstractGameAction  {

	UUID uuid;
	boolean exhausts;
	
	public SetExhaustOfCardAtCombat(UUID uuid, boolean it_exhausts) {
		this.uuid = uuid;
		this.exhausts = it_exhausts;
	}
	
	@Override
	public void update() {
		
		if (!this.isDone) {
			
			CardGroup hand = AbstractDungeon.player.hand;
			
			for (int i = 0; i < hand.size(); i++) {
				if (hand.getNCardFromTop(i).uuid == this.uuid) {
					hand.getNCardFromTop(i).exhaust = this.exhausts;
					this.isDone = true;
					break;
				}
			}
			
			if (this.isDone) {
				return;
			}
			
			CardGroup discard = AbstractDungeon.player.discardPile;
			
			for (int i = 0; i < discard.size(); i++) {
				if (discard.getNCardFromTop(i).uuid == this.uuid) {
					discard.getNCardFromTop(i).exhaust = this.exhausts;
					this.isDone = true;
					break;
				}
			}
			
			if (this.isDone) {
				return;
			}
			
			CardGroup deck = AbstractDungeon.player.drawPile;
			
			for (int i = 0; i < deck.size(); i++) {
				if (deck.getNCardFromTop(i).uuid == this.uuid) {
					deck.getNCardFromTop(i).exhaust = this.exhausts;
					this.isDone = true;
					break;
				}
			}
			
			CardGroup exhausted = AbstractDungeon.player.exhaustPile;
			
			for (int i = 0; i < exhausted.size(); i++) {
				if (exhausted.getNCardFromTop(i).uuid == this.uuid) {
					exhausted.getNCardFromTop(i).exhaust = this.exhausts;
					this.isDone = true;
					break;
				}
			}
			
			this.isDone = true;
		}

	}

}
