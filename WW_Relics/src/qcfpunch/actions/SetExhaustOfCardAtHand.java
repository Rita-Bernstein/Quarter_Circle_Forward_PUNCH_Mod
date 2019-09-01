package qcfpunch.actions;

import java.util.UUID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SetExhaustOfCardAtHand extends AbstractGameAction  {

	UUID uuid;
	boolean exhausts;
	
	public SetExhaustOfCardAtHand(UUID uuid, boolean it_exhausts) {
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
					break;
				}
			}
			
			this.isDone = true;
		}

	}
	
	//Find card
		//By name
		//By UUID
	//Make it exhaustable
	
}
