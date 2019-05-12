package ww_relics.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import ww_relics.actions.FlamingAction;

public class FlamingPower extends AbstractPower {

	public static final String POWER_ID = "WW_Relics:Power_Flaming";
	private static final PowerStrings powerStrings = 
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public static final int MINIMUM_DAMAGE = 2;
	
	public boolean fire_burned_enemy = false;
	public AbstractMonster enemy_targeted;
	public int hp_before = 0;
	
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
	public void onPlayCard(AbstractCard card, AbstractMonster m) {
		if (cardCanTriggerEffect(card)) {
			fire_burned_enemy = true;
			enemy_targeted = m;
		}
	}
	
	public boolean cardCanTriggerEffect(AbstractCard card) {
		return (card.type == CardType.ATTACK) &&
				((card.target == CardTarget.ENEMY) || (card.target == CardTarget.SELF_AND_ENEMY));
	}
	
	@Override
	public void onAfterCardPlayed(AbstractCard usedCard) {
		if (fire_burned_enemy) {
			if (enemy_targeted.isDead) {
				fire_burned_enemy = false;
				enemy_targeted = null;
			} else {
				
				AbstractDungeon.actionManager.addToBottom(
						new FlamingAction(enemy_targeted, hp_before, MINIMUM_DAMAGE));

			}
		}
	}
	
	public DamageInfo createDamageInfo(int damageAmount) {
		return new DamageInfo(AbstractDungeon.player, damageAmount,
				DamageType.HP_LOSS);
	}
	

	
}
