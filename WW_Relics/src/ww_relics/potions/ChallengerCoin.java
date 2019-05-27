package ww_relics.potions;

import java.util.AbstractMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import ww_relics.relics.ken.UnceasingFlame;

public class ChallengerCoin extends AbstractPotion {

	private static final PotionStrings potionStrings = CardCrawlGame.
			languagePack.getPotionString("WW_Relics:Challenger_Coin");
	
	public static final String ID = "WW_Relics:Challenger_Coin";
	public static final String NAME = potionStrings.NAME;
	public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
	
	//Here it says Rare, but it's event or relic only.
	//It won't be sold at shops, at least not the base game shop.
	public static final PotionRarity RARITY = PotionRarity.RARE;
	
	//Size apparently creates the recipient of the potion.
	//Since this potion is a coin, I will have to make my own files for that.
	public static final PotionSize SIZE = PotionSize.SPHERE;
	public static final PotionColor COLOR = PotionColor.FRUIT;
	
	public static final Logger logger = LogManager.getLogger(ChallengerCoin.class.getName()); // lets us log output
	
	public ChallengerCoin() {
		super(NAME, ID, RARITY, SIZE, COLOR);
		description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
		this.isThrown = false;
		this.tips.add(new PowerTip(this.name, this.description));
	}

	
	@Override
	public int getPotency(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canUse() {
		if (AbstractDungeon.isPlayerInDungeon()) return true;
		else return false;
	}
	
	@Override
	public void use(AbstractCreature arg0) {
		logger.info("Right, working.");
		//Change next room entered to an Elite room, if it was already, 
		//add elite to a non-problematic space position in the next room

	}
	
	@Override
	public AbstractPotion makeCopy() {
		// TODO Auto-generated method stub
		return new ChallengerCoin();
	}



}
