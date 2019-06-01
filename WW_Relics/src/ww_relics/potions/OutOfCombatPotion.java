package ww_relics.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import basemod.abstracts.CustomPotion;

public class OutOfCombatPotion extends CustomPotion {

	public OutOfCombatPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionColor color) {
		super(name, id, rarity, size, color);
	}

	@Override
	public int getPotency(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AbstractPotion makeCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void use(AbstractCreature arg0) {
		// TODO Auto-generated method stub
		
	}

}
