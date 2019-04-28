package ww_relics.cards.dan;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import ww_relics.powers.PotentialPower;

public class WeakestEnergyBlast extends CustomCard {

	public static final String ID = "WW_Relics:WeakestEnergyBlast";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_DMG = 3;
    private static final int POTENTIAL_NUMERATOR = 1;
    private static final int POTENTIAL_DENOMINATOR = 3;
	
	public WeakestEnergyBlast() {
		super(ID, WeakestEnergyBlast.NAME, "ww_relics/images/cards/temp_attack.png",
				WeakestEnergyBlast.COST, WeakestEnergyBlast.DESCRIPTION, CardType.ATTACK,
				CardColor.COLORLESS, CardRarity.COMMON, CardTarget.ENEMY);

        this.baseDamage = ATTACK_DMG;
        this.exhaust = true;
	}
    
	@Override
	public void upgrade() {
		return;
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractDungeon.actionManager.addToBottom(
        		new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn),
        				AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        
        AbstractDungeon.actionManager.addToBottom(
        		new ApplyPowerAction(player, player, 
        				new PotentialPower(player, POTENTIAL_NUMERATOR, POTENTIAL_DENOMINATOR)));
	}
	
	static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = WeakestEnergyBlast.cardStrings.NAME;
        DESCRIPTION = WeakestEnergyBlast.cardStrings.DESCRIPTION;
    }

}
