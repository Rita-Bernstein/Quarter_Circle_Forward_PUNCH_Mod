package ww_relics.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FlamingPower extends AbstractPower {

	public static final String POWER_ID = "WW_Relics:Power_Flaming";
	private static final PowerStrings powerStrings = 
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public FlamingPower(AbstractCreature owner, int amount)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		
		updateDescription();
		
		loadRegion("firebreathing");
	}
	
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
		// TODO Auto-generated method stub
		super.onAttack(info, damageAmount, target);
		AbstractDungeon.actionManager.addToBottom(
			new DamageAction(target,
							createDamageInfo(damageAmount),
							AttackEffect.FIRE));
		AbstractDungeon.actionManager.addToBottom(
			new RemoveSpecificPowerAction(AbstractDungeon.player,
					AbstractDungeon.player, this));
	}
	
	public DamageInfo createDamageInfo(int damageAmount) {
		return new DamageInfo(AbstractDungeon.player, damageAmount,
				DamageType.HP_LOSS);
	}
	
	
	
}
