package ww_relics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import ww_relics.map_generation.PostMapGenerationManager;

@SpirePatch(clz= AbstractDungeon.class, method = "generateMap")
public class PostMapGenerationManagerPatch {
	
	public static void Postfix() {
		
		if (PostMapGenerationManager.initialized) {
			
			PostMapGenerationManager.doIfPossiblePostMapGenerationChangers();
			
		}
    }

}
