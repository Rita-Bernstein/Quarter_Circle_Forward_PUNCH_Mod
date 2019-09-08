package qcfpunch.vfx.combat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

//Base code copied from SlayTheSpire's HemokinesisEffect
public class WeakestEnergyBlastEffect extends AbstractGameEffect {
	private float x;
	private float y;
  
	public WeakestEnergyBlastEffect(float sX, float sY, float tX, float tY) {
		
	    this.x = sX;
	    this.y = sY;
	    this.tX = tX;
	    this.tY = tY;
    
	    this.scale = 0.12F;
	    this.duration = 0.5F;
	}
	
	private float tX;
	private float tY;
	
	public void update() {
		
    this.scale -= Gdx.graphics.getDeltaTime();
    if (this.scale < 0.0F) {
    	
    	AbstractDungeon.effectsQueue.add(new WeakestEnergyBlastParticle(this.x + 
            MathUtils.random(60.0F, -60.0F) * Settings.scale, this.y + 
            MathUtils.random(60.0F, -60.0F) * Settings.scale, this.tX,
            this.tY, AbstractDungeon.player.flipHorizontal));

    	this.scale = 0.04F;
	} 
    
    this.duration -= Gdx.graphics.getDeltaTime();
    if (this.duration < 0.0F)
    	this.isDone = true; 
	}
  
	public void render(SpriteBatch sb) {}
  
	public void dispose() {}
}