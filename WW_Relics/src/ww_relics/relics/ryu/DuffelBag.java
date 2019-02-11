package ww_relics.relics.ryu;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.cards.colorless.Panacea;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

import basemod.abstracts.CustomRelic;

public class DuffelBag extends CustomRelic {
	
	public static final Logger logger = LogManager.getLogger(DuffelBag.class.getName());
	
	public static final String ID = "WW_Relics:Duffel_Bag";
	private static final int NUMBER_OF_STATIC_CARDS = 2;
	private ArrayList<AbstractCard> reward_cards;
	private static final int NUMBER_OF_RANDOM_COMMON_RELICS = 2;

	private static final int PRETENDED_NUMBER_OF_EXTRA_REWARS = NUMBER_OF_STATIC_CARDS +
			NUMBER_OF_RANDOM_COMMON_RELICS;
	
	private int number_of_rewards_left = PRETENDED_NUMBER_OF_EXTRA_REWARS;
	
	public DuffelBag() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.UNCOMMON, LandingSound.FLAT);
		reward_cards = new ArrayList<AbstractCard>();
		
		reward_cards.add(new Panacea());
		reward_cards.add(new BandageUp());

	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_RANDOM_COMMON_RELICS +
				DESCRIPTIONS[1];
	}
	
	@Override
	public void onEquip() {

	}
	
	public void atPreBattle() {
		if (((AbstractDungeon.getCurrRoom() instanceof MonsterRoom) || 
				(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite)) &&
				number_of_rewards_left > 0){
			
			AddReward();
			number_of_rewards_left--;
			
		}
	}
	
	private void AddReward() {
		
		if (number_of_rewards_left - reward_cards.size() > 0) {
			
			int card_position = number_of_rewards_left - reward_cards.size();
			
			RewardItem card_reward = new RewardItem();
			card_reward.cards.add(reward_cards.get(card_position - 1));
			AbstractDungeon.getCurrRoom().addCardReward(card_reward);
			
		} else if (number_of_rewards_left > 0) {
			
			AbstractRelic relic = AbstractDungeon.returnRandomRelic(RelicTier.COMMON);
			AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
			
		}
		
	}
	
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new DuffelBag();
	}
	
	
}
