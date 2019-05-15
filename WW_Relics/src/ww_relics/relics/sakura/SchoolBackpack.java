package ww_relics.relics.sakura;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rewards.RewardItem;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class SchoolBackpack extends CustomRelic {

	public static final String ID = "WW_Relics:School_Backpack";
	
	public static final int NUMBER_OF_EXTRA_CARDS = 4;
	
	public static int number_of_cards_left = 4;
	
	public SchoolBackpack() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"), 
				RelicTier.COMMON, LandingSound.FLAT);	
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	/*private void AddReward() {
		
		if (number_of_rewards_left - reward_cards.size() > 0) {
			
			int card_position = number_of_rewards_left - reward_cards.size();
			
			RewardItem card_reward = new RewardItem();
			card_reward.cards.clear();
			card_reward.cards.add(reward_cards.get(card_position - 1));
			AbstractDungeon.getCurrRoom().addCardReward(card_reward);
			
		} else if (number_of_rewards_left > 0) {
			
			AbstractRelic relic = AbstractDungeon.returnRandomRelic(RelicTier.COMMON);
			AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
			
		}
		
	}*/
	
	@Override
	public CustomRelic makeCopy() {
		return new SchoolBackpack();
	}

}
