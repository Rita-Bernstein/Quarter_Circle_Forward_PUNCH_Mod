package ww_relics.cards.dan;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class WeakestFlyingKick extends CustomCard {

	public static final String ID = "WW_Relics:WeakestFlyingKick";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    
	public WeakestFlyingKick() {
		super(ID, StrikeWeakest.NAME, "ww_relics/images/cards/temp_attack.png",
				WeakestFlyingKick.COST, WeakestFlyingKick.DESCRIPTION, CardType.ATTACK,
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
        				AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	}
	
	static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = WeakestFlyingKick.cardStrings.NAME;
        DESCRIPTION = WeakestFlyingKick.cardStrings.DESCRIPTION;
    }

}
