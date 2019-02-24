package ww_relics.relics.chun_li;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import basemod.abstracts.CustomRelic;

public class SpikyBracers extends CustomRelic {
	
	public static final String ID = "WW_Relics:Spiky_Bracers";
	
	private static final int REDUCE_COST_BY = 1;
	private static final int NUMBER_OF_CARDS_TO_APPLY_EFFECT = 2;
	
	private static AbstractCard[] cards_chosen;
	
	private static boolean effect_applied = false;
	
	public SpikyBracers() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.UNCOMMON, LandingSound.HEAVY);
		
		cards_chosen = new AbstractCard[2];
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_CARDS_TO_APPLY_EFFECT+
				DESCRIPTIONS[1] + REDUCE_COST_BY +
				DESCRIPTIONS[2];
	}
	
	public void onEquip() {
		
		if (AbstractDungeon.player.masterDeck.getPurgeableCards().getAttacks().size() > 0)
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
	
	public void onUnequip() {
		
	}
	
	/*public void update()
	{
		super.update();
		if ((!this.cardSelected) && 
		  (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()))
		{
			this.cardSelected = true;
			this.card = ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
			this.card.inBottleFlame = true;
			AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.description = (this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3]);
			this.tips.clear();
			this.tips.add(new PowerTip(this.name, this.description));
			initializeTips();
	    }
	}*/
	
	public boolean canSpawn()
	{
		CardGroup powers;
		CardGroup powers_costing_2_or_more;
		int number_of_powers_costing_2_or_more = 0;

		CardGroup skills;
		CardGroup skills_costing_2_or_more;
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
