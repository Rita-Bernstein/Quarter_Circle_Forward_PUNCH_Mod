package ww_relics.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class UnsteadyPower extends AbstractPower {

	public static final String POWER_ID = "WW_Relics:Power_Unsteady";
	private static final PowerStrings powerStrings = 
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public static final Logger logger = LogManager.getLogger(UnsteadyPower.class.getName());
	
	public DamageInfo unsteady_block_reducer;
	
	public UnsteadyPower(AbstractCreature owner, int amount)
	{
		logger.info("1");
		
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.DEBUFF;
		
		logger.info("2");
		
		updateDescription();
		
		logger.info("3");
		
		loadRegion("frail");
		
		logger.info("4");
		
		unsteady_block_reducer = new DamageInfo(owner, amount, DamageType.NORMAL);
		unsteady_block_reducer.name = "Unsteady";
	}
	
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void atEndOfTurn(boolean isPlayer) {
		  
		float blockAmount = owner.currentBlock;
		  
		if (blockAmount < amount) {
			logger.info("AQUI 2");
			unsteady_block_reducer.base = (int)blockAmount;
			unsteady_block_reducer.output = (int)blockAmount;
		}
		
		logger.info("AQUI 3");
			  
		AbstractDungeon.actionManager.addToBottom(new DamageAction(owner, unsteady_block_reducer));
	}
	  
}
