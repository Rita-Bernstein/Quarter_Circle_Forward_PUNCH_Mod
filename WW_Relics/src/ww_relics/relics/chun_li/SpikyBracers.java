package ww_relics.relics.chun_li;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;

public class SpikyBracers extends CustomRelic {
	
	public static final String ID = "WW_Relics:Spiky_Bracers";
	
	private static final int REDUCE_COST_BY = 1;
	private static final int NUMBER_OF_CARDS_TO_APPLY_EFFECT = 2;
	
	private static boolean effect_applied = false;
	
	public SpikyBracers() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.UNCOMMON, LandingSound.HEAVY);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_CARDS_TO_APPLY_EFFECT+
				DESCRIPTIONS[1] + REDUCE_COST_BY +
				DESCRIPTIONS[2];
	}
	
	
	
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
