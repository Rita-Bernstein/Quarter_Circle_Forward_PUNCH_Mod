package ww_relics.relics.chun_li;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import basemod.abstracts.CustomRelic;

public class SpikyBracers extends CustomRelic {
	
	public static final String ID = "WW_Relics:Spiky_Bracers";
	
	private static final int UPDATE_COST_BY = -1;
	private static final int UPDATE_COST_TEXT = -UPDATE_COST_BY;
	private static final int NUMBER_OF_CARDS_TO_APPLY_EFFECT = 2;
	
	//Doing this fix the "not-saving" problem?
	public AbstractCard[] cards_chosen;
	public boolean cards_are_selected = false;
	
	Logger logger = LogManager.getLogger(SpikyBracers.class.getName());
	
	public SpikyBracers() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.COMMON, LandingSound.HEAVY);
		
		logger.info("Cards are selected = " + cards_are_selected);
		logger.info("Cards chosen = " + cards_chosen == null);
	}
	
	public String getUpdatedDescription() {
		String base_description = DESCRIPTIONS[0] + NUMBER_OF_CARDS_TO_APPLY_EFFECT+
				DESCRIPTIONS[1] + UPDATE_COST_TEXT +
				DESCRIPTIONS[2];
		if (!cards_are_selected) {
			return base_description;
		}

		else {
			base_description += DESCRIPTIONS[3];
			base_description += FontHelper.colorString(cards_chosen[0].name, "y");
			base_description += DESCRIPTIONS[4];
			base_description += FontHelper.colorString(cards_chosen[1].name, "y");
			base_description += DESCRIPTIONS[5];
			
			return base_description;
		}
	}
	
	public String getUncoloredDescription() {
		return DESCRIPTIONS[6] + NUMBER_OF_CARDS_TO_APPLY_EFFECT+
				DESCRIPTIONS[7] + UPDATE_COST_TEXT +
				DESCRIPTIONS[8];
	}
	
	public void onEquip() {
		
		if (getValidCardGroup().getPurgeableCards().size() >= 2)
		{
			if (AbstractDungeon.isScreenUp)
			{
				AbstractDungeon.dynamicBanner.hide();
				AbstractDungeon.overlayMenu.cancelButton.hide();
				AbstractDungeon.previousScreen = AbstractDungeon.screen;
			}
			
			AbstractDungeon.getCurrRoom().phase = RoomPhase.INCOMPLETE;
			
			cards_chosen = new AbstractCard[NUMBER_OF_CARDS_TO_APPLY_EFFECT];
			AbstractDungeon.gridSelectScreen.open(getValidCardGroup(), 2, getUncoloredDescription(), false, false, false, false);
		}
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
	
	public void update()
	{
		super.update();
	    if ((!cards_are_selected) && 
	      (AbstractDungeon.gridSelectScreen.selectedCards.size() == 2))
	    {
	    	this.cards_are_selected = true;
	    	for (int i = 0; i < NUMBER_OF_CARDS_TO_APPLY_EFFECT; i++) {
	    		cards_chosen[i] = ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(i));
	    		cards_chosen[i].updateCost(UPDATE_COST_BY);
	    	}
	      
			AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			String text_for_tip = getUpdatedDescription();
			text_for_tip += DESCRIPTIONS[3];
			text_for_tip += FontHelper.colorString(cards_chosen[0].name, "y");
			text_for_tip += DESCRIPTIONS[4];
			text_for_tip += FontHelper.colorString(cards_chosen[1].name, "y");
			text_for_tip += DESCRIPTIONS[5];
			this.tips.clear();
			this.tips.add(new PowerTip(this.name, text_for_tip));
			initializeTips();
	    }
	}
	
	public void onUnequip() {
		if (cards_are_selected) {
			for (int i = 0; i < NUMBER_OF_CARDS_TO_APPLY_EFFECT; i++) {
			    
				AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.
			    		getSpecificCard(cards_chosen[i]);
				if (cardInDeck != null) {
					AbstractCard card = cards_chosen[i];
					card.updateCost(-UPDATE_COST_BY);
				}		
			}
			cards_chosen = new AbstractCard[NUMBER_OF_CARDS_TO_APPLY_EFFECT];
			cards_are_selected = false;
		}
	}
	
	public boolean canSpawn()
	{
		CardGroup powers;
		int number_of_powers_costing_2_or_more = 0;

		CardGroup skills;
		int number_of_skills_costing_2_or_more = 0;
		
		powers = AbstractDungeon.player.masterDeck.getPowers();
		powers.sortByCost(false);
		
		for (int i = 0; i < powers.size(); i++) {
			if (powers.getNCardFromTop(i).cost >= 2) number_of_powers_costing_2_or_more += 1;
		}
		
		skills = AbstractDungeon.player.masterDeck.getSkills();
		skills.sortByCost(false);
		
		for (int i = 0; i < skills.size(); i++) {
			if (skills.getNCardFromTop(i).cost >= 2) number_of_skills_costing_2_or_more += 1;
		}
		
		return (number_of_powers_costing_2_or_more + number_of_skills_costing_2_or_more)
					>= NUMBER_OF_CARDS_TO_APPLY_EFFECT; 
	}
	
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new SpikyBracers();
	}

}
