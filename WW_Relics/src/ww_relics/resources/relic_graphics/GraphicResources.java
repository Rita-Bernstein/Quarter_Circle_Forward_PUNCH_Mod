package ww_relics.resources.relic_graphics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class GraphicResources {

	public static final String GRAPHIC_RESOURCES_ADDRESS = "ww_relics/resources/";
	public static final String RELIC_GRAPHICS_SUBADDRESS = "relic_graphics/";
	
	public static Texture img_red_headband;
	public static Texture bg_img_red_headband;
	
	public static void LoadGraphicResources() {
		String relic_full_address = GRAPHIC_RESOURCES_ADDRESS + RELIC_GRAPHICS_SUBADDRESS;
		img_red_headband = ImageMaster.loadImage(relic_full_address +
				"Red_Headband - headband-knot - Delapouite - CC BY 3.0.png");
		bg_img_red_headband = ImageMaster.loadImage(relic_full_address +
				"Red_Headband outline - headband-knot - Delapouite - CC BY 3.0.png");
	}
	
}
