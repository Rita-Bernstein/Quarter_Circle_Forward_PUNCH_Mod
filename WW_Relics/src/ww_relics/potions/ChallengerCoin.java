package ww_relics.potions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import basemod.abstracts.CustomPotion;

public class ChallengerCoin extends CustomPotion {

	public static final String ID = "WW_Relics:Challenger_Coin";
	
	private static final PotionStrings potionStrings = CardCrawlGame.
			languagePack.getPotionString(ID);
	
	public static final String NAME = potionStrings.NAME;
	public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
	
	//Here it says Rare, but it's event or relic only.
	//It won't be sold at shops, at least not the base game shop.
	public static final PotionRarity RARITY = PotionRarity.RARE;
	
	//Size apparently creates the recipient of the potion.
	//Since this potion is a coin, I will have to make my own files for that.
	public static final PotionSize SIZE = PotionSize.SPHERE;
	public static final PotionColor COLOR = PotionColor.ATTACK;
	
	public static final Logger logger = LogManager.getLogger(ChallengerCoin.class.getName()); // lets us log output
	
	public ChallengerCoin() {
		super(NAME, ID, RARITY, SIZE, COLOR);
		this.potency = getPotency();
		description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
		this.isThrown = false;
		this.tips.add(new PowerTip(this.name, this.description));
	}

	@Override
	public int getPotency(int ascensionLevel) {
		return 1;
	}

	@Override
	public boolean canUse() {
		logger.info("1 - " + AbstractDungeon.getCurrRoom().phase);
		
		if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMPLETE) {
			return true;
		}
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

		return new ChallengerCoin();
	}



}
