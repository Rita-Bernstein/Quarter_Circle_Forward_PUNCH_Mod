package ww_relics.relics.ryu;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.cards.colorless.Panacea;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomReward;

public class DuffelBag extends CustomRelic {
	public static final String ID = "WW_Relics:Duffel_Bag";
	private static final int NUMBER_OF_RANDOM_COMMON_RELICS = 2;
	
	public DuffelBag() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.UNCOMMON, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_RANDOM_COMMON_RELICS +
				DESCRIPTIONS[1];
	}
	
	public void OnEquip() {
		CustomReward duffel_bag_reward = new CustomReward(null, DESCRIPTIONS[2], RewardType.CARD) {
			
			@Override
			public boolean claimReward() {
				if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
					AbstractDungeon.cardRewardScreen.open(this.cards, this, "It works.");
					AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
				}
				return false;
			}
		};
		
		ArrayList<AbstractCard> list_of_cards = new ArrayList<AbstractCard>();
		list_of_cards.add(new BandageUp());
		list_of_cards.add(new Panacea());
		
		duffel_bag_reward.cards = list_of_cards;

	}
	
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new DuffelBag();
	}
	
	
}
