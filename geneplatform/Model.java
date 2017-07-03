package geneplatform;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {
    private final static Model instance = new Model();
    private int counter=0;
    public ArrayList<BlockModel> getCurrentBlocks() {
        return blocks;
    }
    public int addBlock(BlockModel x, int id) {
        int ret;
        if(id==-1) {
            blocks.add(counter, x);
            ret = counter;
            counter++;
        }
        else
        {
            blocks.remove(id);
            blocks.add(id, x);
            ret = id;
        }
        return ret;
    }
    public static Model getInstance() {
        return instance;
    }

    private ArrayList<BlockModel> blocks = new ArrayList<>();
}