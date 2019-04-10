package ww_relics.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class XBalanceCheckPower extends AbstractPower {

	public static final String POWER_ID = "WW_Relics:XBalanceCheckPower";
	private static final PowerStrings powerStrings = 
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public static final Logger logger = LogManager.getLogger(XBalanceCheckPower.class.getName());
	
	public XBalanceCheckPower(AbstractCreature owner, int amount)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.DEBUFF;
		
		updateDescription();
		
		loadRegion("energized_green");
	}
	
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void onAfterUseCard(AbstractCard card, UseCardAction action) {
		
	}
	
}
