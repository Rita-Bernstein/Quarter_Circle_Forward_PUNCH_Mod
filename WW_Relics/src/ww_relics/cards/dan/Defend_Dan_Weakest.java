package ww_relics.cards.dan;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class Defend_Dan_Weakest extends CustomCard {

	public static final String ID = "WW_Relics:Strike_Dan_Defend";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    private static final int BLOCK_INIT_GAINED = 1;
	
    public Defend_Dan_Weakest() {
        super(ID, Defend_Dan_Weakest.NAME,
        		"ww_relics/images/cards/dan_defend.png", COST, Defend_Dan_Weakest.DESCRIPTION,
        		CardType.ATTACK, CardColor.COLORLESS, CardRarity.BASIC, CardTarget.ENEMY);
        
        this.baseBlock = BLOCK_INIT_GAINED;
        this.exhaust = true;
        
    }
    
	@Override
	public AbstractCard makeCopy() {
		return new Defend_Dan_Weakest();
	}

	@Override
	public void upgrade() {
		return;
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
	      AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
	}
	
	static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WW_Relics:Defend_Dan_Weakest");
        NAME = Defend_Dan_Weakest.cardStrings.NAME;
        DESCRIPTION = Defend_Dan_Weakest.cardStrings.DESCRIPTION;
    }

}
