package ww_relics.rewards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomReward;

public class DuffelBagReward extends CustomReward{

	public DuffelBagReward(Texture icon, String text, RewardType type) {
		super(icon, text, type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean claimReward() {
		AbstractDungeon.previousScreen = AbstractDungeon.screen;
		AbstractDungeon.cardRewardScreen.open(this.cards, this, "It works.");
		return true;
	}

}
