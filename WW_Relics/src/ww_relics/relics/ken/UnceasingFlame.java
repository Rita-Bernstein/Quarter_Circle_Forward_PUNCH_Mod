package ww_relics.relics.ken;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import basemod.abstracts.CustomRelic;
import ww_relics.powers.FlamingPower;
import ww_relics.resources.relic_graphics.GraphicResources;

public class UnceasingFlame extends CustomRelic implements ClickableRelic {

	public static final String ID = "WW_Relics:Unceasing_Flame";
	public static final int NUMBER_OF_ATTACKS_TO_TRIGGER_CHARGE_UP = 3;
	public static final int HOW_MUCH_CHARGE_INCREASES_PER_TRIGGER = 1;
	public static final int MAX_NUMBER_OF_CHARGES = 6;
	public static final int MINIMUM_DAMAGE_FROM_FLAMING = 2; 
	public static int charges = 0;
	public static int charges_at_battle_start;
	
	public static boolean is_player_turn = false;
	
	public static final Logger logger = LogManager.getLogger(UnceasingFlame.class.getName()); // lets us log output
	
	public UnceasingFlame() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.RARE, LandingSound.MAGICAL);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + MINIMUM_DAMAGE_FROM_FLAMING + 
			    DESCRIPTIONS[1] + MAX_NUMBER_OF_CHARGES +
			    DESCRIPTIONS[2];
	}
	
	@Override
	public void atBattleStartPreDraw() {
		charges = charges_at_battle_start;
		fixCounter();
		if (charges == MAX_NUMBER_OF_CHARGES) {
			flash();
		}
	}
	
	public void fixCounter() {
		counter = charges;
	}
	
	@Override
	public void atTurnStart() {
		is_player_turn = true;
	}
	
	@Override
	public void onPlayerEndTurn() {
		is_player_turn = false;
	}
	
	@Override
	public void onVictory() {
		is_player_turn = false;
		charges_at_battle_start = charges;
	}
	 
	@Override
	public void onPlayCard(AbstractCard card, AbstractMonster m) {

		boolean went_to_max_amount_of_charges = false;
		
		if (canAChargeBeAdded(card)) {
			counter++; charges++;
			if (counter == MAX_NUMBER_OF_CHARGES) went_to_max_amount_of_charges = true;
			fixCounter();
		}
		
		if (went_to_max_amount_of_charges) {
			flash();
		}
		
	}
	
	public boolean canAChargeBeAdded(AbstractCard card) {
		return (card.type == CardType.ATTACK) && (counter < MAX_NUMBER_OF_CHARGES);
	}
	
	@Override
	public void onRightClick() {
		
		if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
			if (effectCanBeTriggered()) {
				
				if (counter == 6) {
					counter -= 6; charges -= 6; fixCounter();
					AbstractDungeon.actionManager.addToBottom(applyFlamingPowerAction());
				}
			}
		}			
	}
	
	private boolean effectCanBeTriggered() {
		boolean can_be_triggered = false;
		
		if (is_player_turn
				&& !AbstractDungeon.getCurrRoom().isBattleOver
				&& !AbstractDungeon.getCurrRoom().isBattleEnding()
				&& AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT){
			can_be_triggered = true;
		}
		
		return can_be_triggered;
	}
	
	private ApplyPowerAction applyFlamingPowerAction() {
		return new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
			new FlamingPower(AbstractDungeon.player, 1, MINIMUM_DAMAGE_FROM_FLAMING),
			1);
	}

	public static void save(final SpireConfig config) {

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
    		logger.info("Started saving Unceasing Flame information");

            config.setInt("Unceasing_Flame_number_of_charges_at_battle_start",
            		UnceasingFlame.charges_at_battle_start);
            
            try {
				config.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
            logger.info("Finished saving White Boots info.");
        }
        else {
        	clear(config);
        }

    }
	
	public static void load(final SpireConfig config) {
		
		logger.info("Loading Unceasing Flame info.");
		if (AbstractDungeon.player.hasRelic(ID) && 
				config.has("Unceasing_Flame_number_of_charges_at_battle_start")) {

			charges_at_battle_start = config.getInt("Unceasing_Flame_number_of_charges_at_battle_start");
			
            try {
				config.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("Finished loading Unceasing Flame info.");
        }
		
		else
		{
			logger.info("There's no info, setting variables accordingly.");

			logger.info("Finished setting Unceasing Flame variables.");
		}
		
		
    }
	
	public static void clear(final SpireConfig config) {
		logger.info("Clearing Unceasing Flame variables.");
        config.remove("Unceasing_Flame_number_of_charges_at_battle_start");
        logger.info("Finished clearing Unceasing Flame variables.");
	}
	
	public AbstractRelic makeCopy() {
		return new UnceasingFlame();
	}



}
