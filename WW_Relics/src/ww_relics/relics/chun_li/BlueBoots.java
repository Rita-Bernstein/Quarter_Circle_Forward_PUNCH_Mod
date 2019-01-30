package ww_relics.relics.chun_li;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomRelic;

public class BlueBoots extends CustomRelic {
	public static final String ID = "WW_Relics_Blue_Boots";
	private static final int NUMBER_OF_USES_PER_FIGHT = 1;
	private static final int NUMBER_OF_CHOSEN_ATTACKS = 1;
	private static final int NUMBER_OF_COPIES = 4;
	private static final int DISCOUNT_ON_COST_OF_THE_GENERATED_CARDS = 0;
	
	private Random random = new Random();
	
	public BlueBoots() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.RARE, LandingSound.SOLID);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_CHOSEN_ATTACKS +
				DESCRIPTIONS[1] + NUMBER_OF_COPIES + DESCRIPTIONS[2] +
				DISCOUNT_ON_COST_OF_THE_GENERATED_CARDS;
	}
	
	/*public void OnRightClick() {
		boolean is_on_combat = AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
		boolean is_alive = !AbstractDungeon.player.isDead;
		boolean turn_wont_end_soon = !AbstractDungeon.player.endTurnQueued;
		boolean player_isnt_ending_turn = !AbstractDungeon.player.isEndingTurn;
		boolean turn_havent_ended = !AbstractDungeon.actionManager.turnHasEnded;
		boolean has_an_attack_in_hand = AbstractDungeon.player.hand.getAttacks().
				size() > 0;
		
		if (is_on_combat && is_alive && turn_wont_end_soon &&
			player_isnt_ending_turn && turn_havent_ended &&
			has_an_attack_in_hand) {
			
			//
			
		      AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck
		    	        .getPurgeableCards().getAttacks(), 1, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
		    	    }
			
		}
			
	}*/
	
	public boolean canSpawn()
	{
		return CardHelper.hasCardType(AbstractCard.CardType.ATTACK);
	}

	//how it works:
	//when used, once per combat,
	//Choose an attack in your hand.
	//Create four copies of it, place them in your draw pile, then exhaust the card.
	//The copies cost 1-less the first time they are used.
	
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new BlueBoots();
	}
}