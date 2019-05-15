package ww_relics.relics.sakura;

import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;

import basemod.abstracts.CustomRelic;
import ww_relics.cards.dan.WeakestEnergyBlast;
import ww_relics.resources.relic_graphics.GraphicResources;

public class SchoolBackpack extends CustomRelic {

	public static final String ID = "WW_Relics:School_Backpack";
	
	public static final int NUMBER_OF_EXTRA_CARDS = 4;
	
	public static int number_of_cards_left = 4;
	
	public SchoolBackpack() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"), 
				RelicTier.COMMON, LandingSound.HEAVY);	
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public void atPreBattle() {
		AddReward();
	}
	
	private void AddReward() {
		
		if (number_of_cards_left > 0) {
			
			PlayerClass player_class = AbstractDungeon.player.chosenClass;
			
			//int random_class = AbstractDungeon.ran
			
			//PlayerClass reward_class = 
			
			RewardItem card_reward = new RewardItem();
			card_reward.cards.clear();
			card_reward.cards.add(new WeakestEnergyBlast());
			AbstractDungeon.getCurrRoom().addCardReward(card_reward);

		}
		
	}
	
	@Override
	public CustomRelic makeCopy() {
		return new SchoolBackpack();
	}

}
