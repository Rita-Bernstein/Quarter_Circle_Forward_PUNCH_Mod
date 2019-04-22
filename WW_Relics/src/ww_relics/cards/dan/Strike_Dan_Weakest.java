package ww_relics.cards.dan;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strike_Dan_Weakest extends AbstractCard {

	public static final String ID = "WW_Relics:Strike_Dan_Weakest";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_DMG = 1;
	
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Strike_R");
        NAME = Strike_Red.cardStrings.NAME;
        DESCRIPTION = Strike_Red.cardStrings.DESCRIPTION;
    }
    
	@Override
	public AbstractCard makeCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void upgrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void use(AbstractPlayer arg0, AbstractMonster arg1) {
		// TODO Auto-generated method stub

	}

}
