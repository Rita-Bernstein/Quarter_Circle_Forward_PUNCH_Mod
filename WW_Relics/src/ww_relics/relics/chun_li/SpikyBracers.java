package ww_relics.relics.chun_li;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomRelic;

public class SpikyBracers extends CustomRelic {
	
	public static final String ID = "WW_Relics:Spiky_Bracers";
	
	private static final int UPDATE_COST_BY = -1;
	private static final int UPDATE_COST_TEXT = -UPDATE_COST_BY;
	private static final int NUMBER_OF_CARDS_TO_APPLY_EFFECT = 2;
	
	public AbstractCard[] cards_chosen;
	public static int NUMBER_OF_CARDS_CHOSEN = 0;
	
	public static boolean cards_are_selected = false;
	public boolean power_tip_updated = false;
	
	static Logger logger = LogManager.getLogger(SpikyBracers.class.getName());
	
	public SpikyBracers() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.COMMON, LandingSound.HEAVY);
		NUMBER_OF_CARDS_CHOSEN = 0;
		cards_chosen = new AbstractCard[NUMBER_OF_CARDS_TO_APPLY_EFFECT];
	}
	
	public String getUpdatedDescription() {
		
		String base_description = DESCRIPTIONS[0] + NUMBER_OF_CARDS_TO_APPLY_EFFECT+
				DESCRIPTIONS[1] + UPDATE_COST_TEXT +
				DESCRIPTIONS[2];

		if (NUMBER_OF_CARDS_CHOSEN > 0 && cards_chosen != null) {
			base_description += DESCRIPTIONS[3];
			
			for (int i = 0; i < cards_chosen.length; i++) {
				if (cards_chosen[i] == null) {
					base_description += DESCRIPTIONS[4];
				} else {
					base_description += FontHelper.colorString(cards_chosen[i].name, "y");
				}
				if (i == 0) {
					base_description += DESCRIPTIONS[5];
				} else if (i == 1){
					base_description += DESCRIPTIONS[6];
				}

			}
		}
		
		return base_description;
	}
	
	public String getUncoloredDescription() {
		return DESCRIPTIONS[6] + NUMBER_OF_CARDS_TO_APPLY_EFFECT+
				DESCRIPTIONS[7] + UPDATE_COST_TEXT +
				DESCRIPTIONS[8];
	}
	
	public void updateTipPostCardsChosen() {
		String text_for_tip = getUpdatedDescription();
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, text_for_tip));
		initializeTips();
	}
		
	public CardGroup getValidCardGroup() {
		
		CardGroup valid_card_group = new CardGroup(CardGroupType.UNSPECIFIED);
		
		CardGroup powers = AbstractDungeon.player.masterDeck.getPowers();
		CardGroup skills = AbstractDungeon.player.masterDeck.getSkills();
		
		for (int i = 0; i < powers.size(); i++) {
			if (powers.getNCardFromTop(i).cost >= 2) {
				valid_card_group.addToTop(powers.getNCardFromTop(i));
			}
		}
		
		for (int i = 0; i < skills.size(); i++) {
			if (skills.getNCardFromTop(i).cost >= 2) {
				valid_card_group.addToTop(skills.getNCardFromTop(i));
			}
		}
		
		return valid_card_group;

	}
	
	@SuppressWarnings("static-access")
	public void update()
	{
		super.update();

	}
	
	public void onUnequip() {
		resetRelic();
	}
	
	public void onEnterRoom(AbstractRoom room) {
		resetRelic();
	}
	
	public void resetRelic() {
		if (cards_are_selected) {		
			cards_chosen = new AbstractCard[NUMBER_OF_CARDS_TO_APPLY_EFFECT];
			cards_are_selected = false;
			power_tip_updated = false;
			NUMBER_OF_CARDS_CHOSEN = 0;
		}
	}
	
	public void onUseCard(AbstractCard card, UseCardAction action) {
		if ((NUMBER_OF_CARDS_CHOSEN < cards_chosen.length) && (NUMBER_OF_CARDS_CHOSEN < NUMBER_OF_CARDS_TO_APPLY_EFFECT)) {
			if (cardCanReceiveEffect(card)){
				cards_chosen[NUMBER_OF_CARDS_CHOSEN] = card.makeCopy();
				card.modifyCostForCombat(UPDATE_COST_BY);
				NUMBER_OF_CARDS_CHOSEN++;
				cards_are_selected = true;
				updateTipPostCardsChosen();
			}
		}
	}
	
	public boolean cardCanReceiveEffect(AbstractCard card) {
		boolean power_or_skill = (card.type == CardType.POWER) || (card.type == CardType.SKILL);
		boolean cost_equal_or_higher_than_2 = card.cost >= 2;
		
		boolean has_been_chosen_already = cardHasBeenChosenAlready(card);
		
		return power_or_skill && cost_equal_or_higher_than_2 && !has_been_chosen_already;
	}
	
	public boolean cardHasBeenChosenAlready(AbstractCard card) {
		boolean has_been_chosen_already = false;
		
		for (AbstractCard card_chosen: cards_chosen) {
			if (card_chosen != null) {
				if (card_chosen.uuid == card.uuid) {
					has_been_chosen_already = true;
					break;
				}
			}
		}
		
		return has_been_chosen_already;
	}
	
	public boolean canSpawn()
	{
		CardGroup powers;
		int number_of_powers_costing_2_or_more = 0;

		CardGroup skills;
		int number_of_skills_costing_2_or_more = 0;
		
		powers = AbstractDungeon.player.masterDeck.getPowers();
		number_of_powers_costing_2_or_more = countNumberOfValidCards(powers);
		
		skills = AbstractDungeon.player.masterDeck.getSkills();
		number_of_skills_costing_2_or_more = countNumberOfValidCards(skills);
		
		return (number_of_powers_costing_2_or_more + number_of_skills_costing_2_or_more)
					>= NUMBER_OF_CARDS_TO_APPLY_EFFECT; 
	}
	
	public int countNumberOfValidCards(CardGroup card_group) {
		int number_of_cards_costing_2_or_more = 0;
		
		card_group.sortByCost(false);
		
		for (int i = 0; i < card_group.size(); i++) {
			if (card_group.getNCardFromTop(i).cost >= 2) number_of_cards_costing_2_or_more += 1;
		}
		
		return number_of_cards_costing_2_or_more;
	}
	
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new SpikyBracers();
	}

}
