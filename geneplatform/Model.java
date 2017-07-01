package geneplatform;

import java.util.HashMap;

public class Model {
    private final static Model instance = new Model();

    public HashMap<Integer,BlockModel> getCurrentBlocks() {
        return blocks;
    }
    public void addBlock(BlockModel x, int key) {
        blocks.put((Integer)key,x);
        return;
    }
    public static Model getInstance() {
        return instance;
    }

    private HashMap<Integer,BlockModel> blocks = new HashMap<>();
}