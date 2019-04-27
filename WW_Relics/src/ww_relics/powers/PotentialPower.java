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
		setFraction(numerator, denominator);
		this.type = AbstractPower.PowerType.BUFF;
		
		updateDescription();
		
		loadRegion("energized_green");
	}
	
	public void setFraction(int numerator, int denominator) {
		setNumerator(numerator);
		setDenominator(denominator);
	}

	public void setNumerator(int value) {
		this.numerator = value;
		this.amount2 = value;
	}
	
	public int getNumerator() {
		return numerator;
	}
	
	public void setDenominator(int value) {
		this.denominator = value;
		this.amount = value;
	}
	
	public int getDenominator() {
		return denominator;
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

			sumWithOtherPotential(the_new_potential);
			
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
	
	public void sumWithOtherPotential(PotentialPower other) {
		
		if (this.denominator == other.denominator) {
			this.numerator += other.numerator;
		} else {
			logger.info("Sum between Potentials with different denominators is not implemented.");
		}
	}
	
}
