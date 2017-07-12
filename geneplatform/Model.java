/*
Author:				Muhamed Mahrous
Date:				15/10/2016
Version:			1.0
Project ID:			I.E. Framework
CS Class:			N/A
Programming Language:		JAVA
OS/Hardware dependencies:	None

Problem Description:
The MVC model of the framework.

Overall Design:
System structure:          A singleton class to help the framework main scene "Scenetwo" to communicate with the knowledge
                           space it holds.
Data representation:
                        1.Model: the singleton instance.
                        2.blocks: holds all knowledge spaces.
                        3.counter: holds the number of knowledge spaces.
Algorithms:	        None.


Program Assumptions and Restrictions :
None.
*/

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