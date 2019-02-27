package ww_relics.relics.chun_li;

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
	
	private static AbstractCard[] cards_chosen;
	private boolean cards_are_selected = false;
	
	public SpikyBracers() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.UNCOMMON, LandingSound.HEAVY);
		
		cards_chosen = new AbstractCard[NUMBER_OF_CARDS_TO_APPLY_EFFECT];
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_CARDS_TO_APPLY_EFFECT+
				DESCRIPTIONS[1] + UPDATE_COST_TEXT +
				DESCRIPTIONS[2];
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
			
			AbstractDungeon.gridSelectScreen.open(getValidCardGroup(), 2, getUpdatedDescription(), false, false, false, false);
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
	      String text_for_tip = this.description;
	      text_for_tip += DESCRIPTIONS[3];
	      text_for_tip += FontHelper.colorString(cards_chosen[0].name, "y");
	      text_for_tip += DESCRIPTIONS[4];
	      text_for_tip += FontHelper.colorString(cards_chosen[0].name, "y");
	      text_for_tip += DESCRIPTIONS[5];
	      this.tips.clear();
	      this.tips.add(new PowerTip(this.name, this.description));
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
