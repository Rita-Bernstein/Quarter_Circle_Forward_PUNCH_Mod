package ww_relics.relics.ryu;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;

public class RedHeadband extends CustomRelic {
	public static final String ID = "WW_Relics:Red_Headband";
	private static final int DRAW_PER_STATUS_OR_CURSE = 1;
	private static final int NUMBER_OF_DRAWS = 1;
	
	private static int drawn_status_and_curses_in_the_turn = 0;
	
	private Random random = new Random();
	
	public RedHeadband() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.COMMON, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
	}
	
	public void onCardDraw(AbstractCard drawnCard) {
		if ((drawnCard.type == AbstractCard.CardType.CURSE) ||
			(drawnCard.type == AbstractCard.CardType.STATUS)) 
		{
			if (drawn_status_and_curses_in_the_turn  < NUMBER_OF_DRAWS) {
		        AbstractDungeon.player.hand.moveToDiscardPile(drawnCard);
				
		        AbstractPlayer p = AbstractDungeon.player;
		        
		        for (int i = 0; i < DRAW_PER_STATUS_OR_CURSE; i++) {
			        if (!p.drawPile.group.isEmpty()) {
			        	int draw_pile_size = p.drawPile.group.size();
			        	int which_card = random.random(0, draw_pile_size-1);
			        	AbstractCard the_card = p.drawPile.getNCardFromTop(which_card);
			        	p.drawPile.moveToHand(the_card, p.drawPile);
			        } else { break; }
			        
					drawn_status_and_curses_in_the_turn++;
		        }

			}
		}
		
	}
	
	public void atTurnStart() {
		drawn_status_and_curses_in_the_turn = 0;
	}

	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new RedHeadband();
	}
}
