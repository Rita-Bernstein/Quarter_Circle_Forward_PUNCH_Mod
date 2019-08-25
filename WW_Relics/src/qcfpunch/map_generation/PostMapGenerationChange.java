package qcfpunch.map_generation;

import qcfpunch.interfaces.IPostMapGenerationAddStuff;

public class PostMapGenerationChange implements Comparable<PostMapGenerationChange> {

	public int counter;
	
	public IPostMapGenerationAddStuff post_map_gen_changer_object;

	@Override
	public int compareTo(PostMapGenerationChange compared) {
        return this.counter-compared.counter;
    }
	
}
