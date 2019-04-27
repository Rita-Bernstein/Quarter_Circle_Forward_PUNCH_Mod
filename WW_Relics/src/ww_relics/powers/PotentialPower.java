package ww_relics.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PotentialPower extends TwoAmountPower {

	public static final String POWER_ID = "WW_Relics:Power_Potential";
	private static final PowerStrings powerStrings = 
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public int numerator;
	public int denominator;
	
	public static final Logger logger = LogManager.getLogger(PotentialPower.class.getName());
	
	public PotentialPower(AbstractCreature owner, int numerator, int denominator) {
		
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.denominator = denominator;
		this.amount = denominator;
		this.numerator = numerator;
		this.amount2 = numerator;
		this.type = AbstractPower.PowerType.BUFF;
		
		updateDescription();
		
		loadRegion("energized_green");
	}
	
	public void updateDescription()
	{
		this.description = numerator + "/" + denominator;
	}
	
	
	@Override
	public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {

		if ((power.ID == PotentialPower.POWER_ID) && (power.hashCode() != this.hashCode()) &&
				(this.owner == power.owner)) {
			PotentialPower the_new_potential = (PotentialPower) power;
			
			this.denominator = amount;
			this.numerator = amount2;
			
			logger.info("before: " + this.amount2 + "/" + this.amount);
			logger.info("before numerator: " + this.numerator);
			logger.info("before denominator: " + this.denominator);
			
			if (this.denominator == the_new_potential.denominator) {
				this.numerator += the_new_potential.numerator;
			} else {
				logger.info(this.denominator + " AAAA");
				logger.info(the_new_potential.denominator + " ARGH");
				logger.info(the_new_potential.amount2 + " PHEW");
				logger.info(the_new_potential.amount + " MAYBE");
			}
			
			logger.info("after numerator: " + this.numerator);
			logger.info("after denominator: " + this.denominator);
			
			//+1 to avoid number reducing by one, have to improve this
			this.amount = denominator+1;
			this.amount2 = numerator;
			updateDescription();
			
			logger.info("after: " + this.amount2 + "/" + this.amount);
			
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction
					(this.owner, this.owner, the_new_potential));
		}
	}
	
}
