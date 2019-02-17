package ww_relics.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
			CardCrawlGame.languagePack.getPowerStrings("WW_Relics:Power_Unsteady");
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public DamageInfo unsteady_block_reducer;
	
	public UnsteadyPower(AbstractCreature owner, int amount)
	{
		unsteady_block_reducer = new DamageInfo(owner, amount);
		unsteady_block_reducer.base = amount;
		unsteady_block_reducer.name = "Unsteady";
		unsteady_block_reducer.output = amount;
		unsteady_block_reducer.type = DamageType.NORMAL;
		
		this.name = NAME;
		this.ID = "WW_Relics:Power_Unsteady";
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		loadRegion("WW_Relics:Power_Unsteady");
		
		this.type = AbstractPower.PowerType.DEBUFF;
	}
	
	  public void updateDescription()
	  {
	    this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
	  }
	
	  @Override
	  public void onGainedBlock(float blockAmount) {
		  
		  if (blockAmount < amount) {
			  
			  unsteady_block_reducer.base = (int)blockAmount;
			  unsteady_block_reducer.output = (int)blockAmount;
			  
			  AbstractDungeon.actionManager.addToTop(new DamageAction(owner, unsteady_block_reducer));
			  
		  }
		  else {
			  
			  unsteady_block_reducer.base = (int)amount;
			  unsteady_block_reducer.output = (int)amount;
			  
			  AbstractDungeon.actionManager.addToTop(new DamageAction(owner, unsteady_block_reducer));
			  
		  }
		  
	  }
	  
}
