package qcfpunch.actions;

import java.util.UUID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SetEtherealOfCardAtHand extends AbstractGameAction {

	UUID uuid;
	boolean ethereal;
	
	public SetEtherealOfCardAtHand(UUID uuid, boolean will_be_ethereal) {
		this.uuid = uuid;
		this.ethereal = will_be_ethereal;
	}
	
	@Override
	public void update() {
		
		if (!this.isDone) {
			
			CardGroup hand = AbstractDungeon.player.hand;
			
			for (int i = 0; i < hand.size(); i++) {
				if (hand.getNCardFromTop(i).uuid == this.uuid) {
					hand.getNCardFromTop(i).isEthereal = this.ethereal;
					break;
				}
			}
			
			this.isDone = true;
		}

	}
	
}
