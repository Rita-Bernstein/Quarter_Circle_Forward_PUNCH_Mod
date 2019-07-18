package ww_relics.monsters.elites;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
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
	}
	
	public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("ELITE");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this,
        		new StrengthPower(this, INITIAL_STR_BUFF)));
    }
	
	@Override
	protected void getMove(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeTurn() {
		// TODO Auto-generated method stub

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
	
	public void AngryScreamingMove() {
		
	}
	
	public void AngryScreamingIntent() {
		
	}
	
	public void ArmSmashMove() {
		
	}
	
	public void ArmSmashIntent() {
		
	}
	
	public void BodyBlowMove() {
		
	}
	
	public void BodyBlowIntent() {
		
	}
	
	public void HeavyBreeathingMove() {
		
	}
	
	public void HeavyBreathingIntent() {
		
	}

}
