package ww_relics.relics.ryu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class RedHeadband extends CustomRelic {
	public static final String ID = "WW_Relics:Red_Headband";
	private static final int DRAW_PER_STATUS_OR_CURSE = 1;
	private static final int NUMBER_OF_DRAWS = 1;
	
	private static int drawn_status_and_curses_in_the_turn = 0;
	
	private Random random = new Random();
	
	private Logger logger = LogManager.getLogger(RedHeadband.class.getName());
	
	//Solution: load Texture instead of String
	public RedHeadband() {
		super(ID, GraphicResources.LoadRelicImage("Red_Headband - headband-knot - Delapouite - CC BY 3.0.png"), 
				RelicTier.COMMON, LandingSound.FLAT);
		if (img != null) logger.info("Worked.");
		else logger.info("Didn't work.");
		
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
	}
	
	public void onCardDraw(AbstractCard drawnCard) {
		if ((drawnCard.type == AbstractCard.CardType.CURSE) ||
			(drawnCard.type == AbstractCard.CardType.STATUS)) 
		{
			if (drawn_status_and_curses_in_the_turn  < NUMBER_OF_DRAWS) {
				flash();
		        AbstractDungeon.player.hand.moveToDiscardPile(drawnCard);
				
		        AbstractPlayer p = AbstractDungeon.player;

		        if (abscenceOfNoDraw()) {
			        for (int i = 0; i < DRAW_PER_STATUS_OR_CURSE; i++) {
				        if (!p.drawPile.group.isEmpty()) {
				        	int draw_pile_size = p.drawPile.group.size();
				        	int which_card = random.random(0, draw_pile_size-1);
				        	AbstractCard the_card = p.drawPile.getNCardFromTop(which_card);
				        	p.drawPile.moveToHand(the_card, p.drawPile);
				        } else { break; }
				        
						drawn_status_and_curses_in_the_turn++;
			        }
		        } else {
		        	AbstractDungeon.player.getPower("No Draw").flash();
		        }
			}
		}
		
	}
	
	public Boolean abscenceOfNoDraw() {
		return !AbstractDungeon.player.hasPower("No Draw");
	}
	
	public void atTurnStart() {
		drawn_status_and_curses_in_the_turn = 0;
	}

	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new RedHeadband();
	}
}
