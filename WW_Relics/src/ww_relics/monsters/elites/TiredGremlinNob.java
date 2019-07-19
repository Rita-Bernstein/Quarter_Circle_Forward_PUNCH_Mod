package ww_relics.monsters.elites;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AngerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomMonster;

public class TiredGremlinNob extends CustomMonster {

	public static final String ID = "WWRelics:TiredGremlinNob";
	private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
	public static final String NAME = monsterstrings.NAME;
	public static final String[] DIALOG = monsterstrings.DIALOG;
	private static final int HP_MIN = 98;
	private static final int HP_MAX = 106;
	private static final float INITIAL_HEALTH_PERCENTAGE = 0.5f;
	
	private static final int INITIAL_STR_BUFF = 9;
	private static final int INITIAL_ANGRY_BUFF = 2;
	
	private static final byte ANGRY_SCREAM = 1;
	private static final byte ARM_SMASH = 2;
	private static final byte BODY_BLOW = 3;
	private static final byte HEAVY_BREATHING = 4;
	
	private static final int ANGRY_SCREAM_STR_BUFF = 2;
	
	
	public TiredGremlinNob(float x, float y) {
		
		super(NAME, "Tired Gremlin Nob",  86, -70.0F, -10.0F, 270.0F, 380.0F, null, x, y);
	    this.intentOffsetX = -30.0F * Settings.scale;
	    this.type = EnemyType.ELITE;
	    this.dialogX = -60.0F * Settings.scale;
	    this.dialogY = 50.0F * Settings.scale;
	    
	    loadAnimation("images/monsters/theBottom/nobGremlin/skeleton.atlas", "images/monsters/theBottom/nobGremlin/skeleton.json", 1.0F);
		
	    AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
	    e.setTime(e.getEndTime() * MathUtils.random());
	    
	    this.type = EnemyType.ELITE;
	    
	    setHp(HP_MIN, HP_MAX);
	    
	    this.currentHealth = (int)(this.currentHealth * INITIAL_HEALTH_PERCENTAGE);
	    
	    this.nextMove = 1;
	}

	public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("ELITE");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this,
        		new StrengthPower(this, INITIAL_STR_BUFF)));
        if (!Settings.FAST_MODE) {
        	AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this,
        		new AngerPower(this, INITIAL_ANGRY_BUFF), INITIAL_ANGRY_BUFF));
    }
	
	@Override
	protected void getMove(int arg0) {
		AngryScreamIntent();
	}

	@Override
	public void takeTurn() {
		switch(this.nextMove) {
			case ANGRY_SCREAM:
				AngryScreamMove();
				break;
			case ARM_SMASH:
				ArmSmashMove();
				break;
			case BODY_BLOW:
				BodyBlowMove();
				break;
			case HEAVY_BREATHING:
				HeavyBreathingMove();
				break;
			default:
				
		}

	}
	
	private void playSfx() {
		int roll = MathUtils.random(2);
		if (roll == 0) {
			AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1A"));
	    } else if (roll == 1) {
	    	AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1B"));
	    } else {
	    	AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1C"));
	    } 
	}
	
	public void AngryScreamMove() {
		playSfx();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this,
        		new StrengthPower(this, ANGRY_SCREAM_STR_BUFF)));		
	}
	
	public void AngryScreamIntent() {
        setMove(ANGRY_SCREAM, Intent.BUFF);
	}
	
	public void ArmSmashMove() {
		playSfx();
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage
              .get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
	}
	
	public void ArmSmashIntent() {
		
	}
	
	public void BodyBlowMove() {
		
	}
	
	public void BodyBlowIntent() {
		
	}
	
	public void HeavyBreathingMove() {
		
	}
	
	public void HeavyBreathingIntent() {
		
	}

}
