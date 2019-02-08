package ww_relics.relics.ryu;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
	private static DuffelBagReward duffel_bag_reward;
	
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
		
		String tempTextureAddress = "ww_relics/assets/img/modbadge/ModBadgePlaceholder.png";
		Texture tempTexture = new Texture(Gdx.files.internal(tempTextureAddress));
		
		duffel_bag_reward = new DuffelBagReward(tempTexture, DESCRIPTIONS[2], RewardType.CARD);
		duffel_bag_reward.number_of_relics = NUMBER_OF_RANDOM_COMMON_RELICS;
		
		duffel_bag_reward.claimReward();
	}
	
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new DuffelBag();
	}
	
	
}
