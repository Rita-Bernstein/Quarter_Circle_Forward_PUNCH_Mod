package ww_relics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

public class DrawRandomCardToHandAction extends AbstractGameAction {

	private Random random;
	private AbstractPlayer player;
	
	public DrawRandomCardToHandAction() {
		
		random = new Random();
		player = AbstractDungeon.player;
		
	}

	@Override
	public void update() {
		
		if (!this.isDone) {
	    	int draw_pile_size = player.drawPile.group.size();
	    	int which_card = random.random(0, draw_pile_size-1);
	    	AbstractCard the_card = player.drawPile.getNCardFromTop(which_card);
	    	player.drawPile.moveToHand(the_card, player.drawPile);
	    	
	    	this.isDone = true;
		}
		
	}
	
}
