package ww_relics.relics.ryu;

import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.cards.colorless.Panacea;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import basemod.abstracts.CustomRelic;

public class DuffelBag extends CustomRelic {
	public static final String ID = "WW_Relics:Duffel_Bag";
	private static final int NUMBER_OF_RANDOM_COMMON_RELICS = 2;
	
	public DuffelBag() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.UNCOMMON, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_RANDOM_COMMON_RELICS +
				DESCRIPTIONS[1];
	}
	
	public void OnEquip() {
		 AbstractDungeon.effectList.add(
				 new ShowCardAndObtainEffect(new BandageUp(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
		 AbstractDungeon.effectList.add(
				 new ShowCardAndObtainEffect(new Panacea(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	}
	
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new DuffelBag();
	}
	
	
}
