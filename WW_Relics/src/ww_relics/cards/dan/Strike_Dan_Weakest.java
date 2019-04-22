package ww_relics.cards.dan;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strike_Dan_Weakest extends AbstractCard {

	public static final String ID = "WW_Relics:Strike_Dan_Weakest";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_DMG = 1;
	
    public Strike_Dan_Weakest() {
        super("WW_Relics:Strike_Dan_Weakest", Strike_Dan_Weakest.NAME,
        		"ww_relics/images/cards/dan_strike.png", COST, Strike_Dan_Weakest.DESCRIPTION,
        		CardType.ATTACK, CardColor.COLORLESS, CardRarity.BASIC, CardTarget.ENEMY);
        
        this.baseDamage = ATTACK_DMG;
        this.tags.add(CardTags.STRIKE);
    }
    
    
    
	@Override
	public AbstractCard makeCopy() {
		return new Strike_Dan_Weakest();
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
        
        //Forgetful effect should enter here, for now, Exhaust.
       AbstractDungeon.actionManager.addToBottom(
    		   new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        		

	}
	
	static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WW_Relics:Strike_Dan_Weakest");
        NAME = Strike_Dan_Weakest.cardStrings.NAME;
        DESCRIPTION = Strike_Dan_Weakest.cardStrings.DESCRIPTION;
    }

}
