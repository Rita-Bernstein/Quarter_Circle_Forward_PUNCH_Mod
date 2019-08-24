package ww_relics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawRandomCardToHandAction extends AbstractGameAction {

	private AbstractPlayer player;
	
	public DrawRandomCardToHandAction() {
		
		player = AbstractDungeon.player;
		
	}

	@Override
	public void update() {
		
		if (!this.isDone) {
	    	int draw_pile_size = player.drawPile.group.size();
	    	int which_card = AbstractDungeon.cardRandomRng.random(1, draw_pile_size) - 1;
	    	AbstractCard the_card = player.drawPile.getNCardFromTop(which_card);
	    	player.drawPile.moveToHand(the_card, player.drawPile);
	    	
	    	this.isDone = true;
		}
		
	}
	
}
