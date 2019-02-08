package ww_relics.rewards;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.cards.colorless.Panacea;

import basemod.abstracts.CustomReward;

public class DuffelBagReward extends CustomReward{

	public static final Logger logger = LogManager.getLogger(DuffelBagReward.class.getName());
	
	public int number_of_relics;
	
	public DuffelBagReward(Texture icon, String text, RewardType type) {
		super(icon, text, type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean claimReward() {
		
		for (int i = 0; i < number_of_relics; i++) {
			AbstractRelic relic = AbstractDungeon.returnRandomRelic(RelicTier.COMMON);
			AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
		}
		
		RewardItem panacea = new RewardItem();
		panacea.cards = new ArrayList<AbstractCard>();
		panacea.cards.add(new Panacea());
		
		RewardItem bandage_up = new RewardItem();
		bandage_up.cards = new ArrayList<AbstractCard>();
		bandage_up.cards.add(new BandageUp());
		
		AbstractDungeon.getCurrRoom().addCardReward(panacea);
		AbstractDungeon.getCurrRoom().addCardReward(bandage_up);
		
		return true;
	}

}
