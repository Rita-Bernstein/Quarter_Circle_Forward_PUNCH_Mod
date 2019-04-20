package ww_relics.relics.guile;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class ArmyBoots extends CustomRelic implements OnLoseBlockRelic  {
	public static final String ID = "WW_Relics:Army_Boots";
	
	public static final Logger logger = LogManager.getLogger(ArmyBoots.class.getName());
	
	private static ArrayList<String> powers_affected_by_relic;
	private static boolean relic_effect_activated = false;

	public ArmyBoots() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.COMMON, LandingSound.SOLID);
		
		InitializeListOfRemovableDebuffs();
	}
	
	private void InitializeListOfRemovableDebuffs() {
		powers_affected_by_relic = new ArrayList<String>();
		powers_affected_by_relic.add("Frail");
		powers_affected_by_relic.add("Vulnerable");
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public void atPreBattle() {
		relic_effect_activated = false;
	}
	
	@Override
	public int onLoseBlock(DamageInfo info, int damage_amount) {
		
		boolean found_power;
		boolean showed_relic_image = false;
		if (RelicShouldWorkNow(info, damage_amount)) {
			
			AbstractPlayer player = AbstractDungeon.player;
			
			for (String power: powers_affected_by_relic){
				found_power = false;
				if (player.hasPower(power)) {
					relic_effect_activated = true;
					found_power = true;
					
					if (IsTimeToCallRelicVisualEffects(found_power, showed_relic_image)) {
						RelicVisualEffects();
						showed_relic_image = true;
					}
					
					RemoveSpecificPowerAction remove_power_action =
							new RemoveSpecificPowerAction(player, player, player.getPower(power));

					AbstractDungeon.actionManager.addToBottom(remove_power_action);
				}
			}
		}
		
		return damage_amount;
	}
	
	private boolean RelicShouldWorkNow(DamageInfo info, int damage_amount) {
		boolean relic_havent_been_activated_in_this_combat = !relic_effect_activated;
		boolean damage_received_is_normal = info.type == DamageType.NORMAL;
		boolean block_is_broken = damage_amount >= AbstractDungeon.player.currentBlock;
		
		return relic_havent_been_activated_in_this_combat &&
				damage_received_is_normal &&
				block_is_broken;
	}
	
	
	private boolean IsTimeToCallRelicVisualEffects(boolean found_power, boolean showed_relic_image) {
		boolean relic_image_havent_show_up_yet = !showed_relic_image;
		
		return found_power && relic_image_havent_show_up_yet;
	}
	
	private void RelicVisualEffects() {
		flash();
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
	}
	
	public AbstractRelic makeCopy() {
		return new ArmyBoots();
	}

}
