package ww_relics.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import ww_relics.relics.chun_li.SpikyBracers;

public class XBalanceCheckPower extends AbstractPower {

	public static final String POWER_ID = "WW_Relics:XBalanceCheckPower";
	private static final PowerStrings powerStrings = 
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	static final Logger logger = LogManager.getLogger(XBalanceCheckPower.class.getName());
	
	public AbstractCard x_card;
	
	public XBalanceCheckPower(AbstractCreature owner, int amount, AbstractCard card)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.DEBUFF;
		this.x_card = card;
		
		updateDescription();
		
		loadRegion("energized_green");
	}
	
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public void onAfterUseCard(AbstractCard card, UseCardAction action) {
		if (AbstractDungeon.player.hasRelic("Spiky Bracers")){
			logger.info("A should should appear.");
			boolean test = SpikyBracers.cardHasBeenChosenAlready(card);
			logger.info(test + " worked");
			if (test) {
				logger.info("worked");
				amount -= 1;
				if (amount <= 0) {
					AbstractPower p = this;
					this.owner.powers.remove(p);
					
				}
			}
		}
	}
	
}
