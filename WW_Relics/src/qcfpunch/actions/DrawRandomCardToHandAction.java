package qcfpunch.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import qcfpunch.relics.ryu.FightingGloves;

public class DrawRandomCardToHandAction extends AbstractGameAction {

	private AbstractPlayer player;
	
	public static final Logger logger = LogManager.getLogger(FightingGloves.class.getName());
	
	public DrawRandomCardToHandAction() {
		
		player = AbstractDungeon.player;
		
	}

	@Override
	public void update() {
		
		if (!this.isDone) {
	    	int draw_pile_size = player.drawPile.group.size();
	    	
	    	int which_card = (int)AbstractDungeon.cardRandomRng.randomLong();
	    	
	    	if (which_card <0) which_card *= -1;

	    	if (draw_pile_size > 0) {
		    	which_card %= draw_pile_size;
		    	logger.info(which_card);
		    	AbstractCard the_card = player.drawPile.getNCardFromTop(which_card);
		    	player.drawPile.moveToHand(the_card, player.drawPile);
	    	}

	    	
	    	this.isDone = true;
		}
		
	}
	
}
