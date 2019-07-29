package ww_relics.map_generation;

import ww_relics.interfaces.IPostMapGenerationAddStuff;

public class PostMapGenerationChange implements Comparable<PostMapGenerationChange> {

	public int counter;
	
	public IPostMapGenerationAddStuff post_map_gen_changer_object;

	@Override
	public int compareTo(PostMapGenerationChange compared) {
        return this.counter-compared.counter;
    }
	
}
