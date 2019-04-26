package ww_relics.powers;

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
	
	public PotentialPower(AbstractCreature owner, int numerator, int denominator) {
		
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = denominator;
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
			
			if (this.denominator == the_new_potential.denominator) {
				this.numerator += the_new_potential.numerator;
			} else {
				//Add in the future code to sum fractions with different denominators
			}
			
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction
					(this.owner, this.owner, the_new_potential));
		}
	}
	
}
