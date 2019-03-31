package ww_relics.resources.relic_graphics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class GraphicResources {

	public static String graphic_resources_address;
	
	public static Texture img_red_headband;
	public static Texture bg_img_red_headband;
	
	public GraphicResources(String address) {
		graphic_resources_address = address;
	}
	
	public void LoadGraphicResources() {
		img_red_headband = ImageMaster.loadImage(graphic_resources_address);
	}
	
}
