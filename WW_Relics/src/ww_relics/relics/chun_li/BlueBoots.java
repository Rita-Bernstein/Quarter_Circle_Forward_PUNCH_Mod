package ww_relics.relics.chun_li;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomRelic;

public class BlueBoots extends CustomRelic implements ClickableRelic {
	public static final String ID = "WW_Relics_Blue_Boots";
	private static final int NUMBER_OF_USES_PER_FIGHT = 1;
	private static final int NUMBER_OF_CHOSEN_ATTACKS = 1;
	private static final int NUMBER_OF_COPIES = 4;
	private static final int DISCOUNT_ON_COST_OF_THE_GENERATED_CARDS = 0;
	
	public int number_of_uses_left_in_this_fight;
	public int number_of_copies_left_to_use;
	public boolean card_is_selected;
	public AbstractCard card_selected;
	public AbstractCard card_copied;
	
	public BlueBoots() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.RARE, LandingSound.SOLID);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_CHOSEN_ATTACKS +
				DESCRIPTIONS[1] + DESCRIPTIONS[2] + NUMBER_OF_COPIES + DESCRIPTIONS[3] +
				DISCOUNT_ON_COST_OF_THE_GENERATED_CARDS + DESCRIPTIONS[4];
	}
	
	public void onBattleStart() {
		number_of_uses_left_in_this_fight = NUMBER_OF_USES_PER_FIGHT;
		card_is_selected = false;
		card_copied = null;
	}
	
	public void onRightClick() {
		boolean is_on_combat = AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
		boolean is_alive = !AbstractDungeon.player.isDead;
		boolean turn_wont_end_soon = !AbstractDungeon.player.endTurnQueued;
		boolean player_isnt_ending_turn = !AbstractDungeon.player.isEndingTurn;
		boolean turn_havent_ended = !AbstractDungeon.actionManager.turnHasEnded;
		boolean has_an_attack_in_hand = AbstractDungeon.player.hand.getAttacks().
				size() > 0;
		boolean have_uses_left = number_of_uses_left_in_this_fight > 0;
		
		if (is_on_combat && is_alive && turn_wont_end_soon &&
			player_isnt_ending_turn && turn_havent_ended &&
			has_an_attack_in_hand && have_uses_left) {
			
			number_of_uses_left_in_this_fight--;
			number_of_copies_left_to_use = NUMBER_OF_COPIES;
			
			CardGroup list_of_attacks = AbstractDungeon.player.masterDeck.getPurgeableCards().getAttacks();
			if (list_of_attacks.size() == 1) {
				card_copied = list_of_attacks.getTopCard();
			} else {
				AbstractDungeon.gridSelectScreen.open(
					list_of_attacks, 1,
					this.DESCRIPTIONS[0] + NUMBER_OF_CHOSEN_ATTACKS + this.DESCRIPTIONS[1],
					false, false, false, false);
		    }
		}
	}
	
	public void update()
	{
		super.update();
		if ((!this.card_is_selected) && 
			(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()))
		{
			this.card_is_selected = true;
			this.card_selected = ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
			
			card_copied = card_selected.makeCopy();
			if (card_copied.cost > 0) card_copied.cost -= 1; 
			card_copied.isCostModified = true;
			
			for (int i = 0; i < NUMBER_OF_COPIES; i++) {
				AbstractCard new_card = card_copied.makeCopy();
				
				AbstractDungeon.player.drawPile.addToRandomSpot(new_card);
			}
			
			AbstractDungeon.player.hand.moveToExhaustPile(card_selected);
		}
	}
	
	public void onPlayCard(AbstractCard c, AbstractMonster m)  {
		
		if ((card_is_selected) && (c.compareTo(card_copied) == 0)){
			
			c.cost += 1;
			c.isCostModified = false;
			number_of_copies_left_to_use--;
			
			if (number_of_copies_left_to_use == 0) {
				card_copied = null;
				card_selected = null;
				card_is_selected = false;
			}
			
		}
		
	}
	
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