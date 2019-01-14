package ww_relics.relics.ryu;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;

public class RedHeadband extends CustomRelic {
	public static final String ID = "WW_Relics Red Headband";
	private static final int DRAW_PER_STATUS_OR_CURSE = 1;
	private static final int NUMBER_OF_DRAWS = 1;
	
	private static int drawn_status_and_curses_in_the_turn = 0;
	
	public RedHeadband() {
		super(ID, new Texture(), //add method for textures here.
				RelicTier.UNCOMMON, LandingSound.FLAT);
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + HP_PER_CARD + DESCRIPTIONS[1]; // DESCRIPTIONS pulls from your localization file
	}
	
	@Override
	public void onCardDraw(AbstractCard drawnCard) {
		if ((drawnCard.type == AbstractCard.CardType.CURSE) ||
			(drawnCard.type == AbstractCard.CardType.STATUS)) 
		{
			if (drawn_status_and_curses_in_the_turn  < NUMBER_OF_DRAWS) {
				drawnCard.moveToDiscardPile(); //this does what it says?
				AbstractPlayer p = AbstractDungeon.player;
				AbstractDungeon.actionManager.
					addToBottom(new DrawCardAction(p, DRAW_PER_STATUS_OR_CURSE));
				drawn_status_and_curses_in_the_turn++;
			}
		}
		
	}
	
	@Override
	public void atTurnStart() {
		drawn_status_and_curses_in_the_turn = 0;
	}
	
	@Override
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new RedHeadband();
	}
}
