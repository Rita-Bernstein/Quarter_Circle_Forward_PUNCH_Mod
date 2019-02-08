package ww_relics.relics.ryu;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.cards.colorless.Panacea;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;

import basemod.abstracts.CustomRelic;
import ww_relics.rewards.DuffelBagReward;

public class DuffelBag extends CustomRelic {
	
	public static final Logger logger = LogManager.getLogger(DuffelBag.class.getName());
	
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
	
	@Override
	public void onEquip() {
		
		logger.info("ATÉ AQUI CHEGAMOS.");
		
		DuffelBagReward duffel_bag_reward = new DuffelBagReward(null, DESCRIPTIONS[2], RewardType.CARD);
		duffel_bag_reward.number_of_relics = NUMBER_OF_RANDOM_COMMON_RELICS;
		
		ArrayList<AbstractCard> list_of_cards = new ArrayList<AbstractCard>();
		list_of_cards.add(new BandageUp());
		list_of_cards.add(new Panacea());
		
		duffel_bag_reward.cards = list_of_cards;
		
		duffel_bag_reward.claimReward();
	}
	
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new DuffelBag();
	}
	
	
}
