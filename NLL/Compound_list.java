/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLL;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Eslam
 */
public class Compound_list {

    private LinkedList<GUI_Node> GUI_link;

    public Compound_list() {
        GUI_link = new LinkedList<>();

    }
    public void add_index(int i,GUI_Node node)
    {
        GUI_link.add(i,node);
    }
    public void remove_element(int i)
    {
        GUI_link.remove(i);
    }

    public LinkedList<GUI_Node> get_list() {
        return GUI_link;

    }
    public void set_list(LinkedList<GUI_Node> list) {
        this.GUI_link=list;
        return;
    }
    public void remove_list() {
         this.GUI_link=new LinkedList<GUI_Node>();
         return;
    }

    public void add_file(String path) {

        File_Node file = new File_Node();
        file.path = path;
        file.type = "File";
        GUI_link.add(file);

    }

    public void add_Generic(String name) {

        Generic_Node gen = new Generic_Node();
        gen.type = "Generic";
        gen.name = name;
        GUI_link.add(gen);

    }

    public void add_String(String string){
    
    String_Node str = new String_Node();
    str.type="String";
    str.string=string;
    GUI_link.add(str);
    
    }

    public void add_Relation(String name) {

        Relation_Node relation = new Relation_Node();
        relation.type = "Relation";
        relation.name = name;
        GUI_link.add(relation);

    }

    public String get_index_type(int index) {

        return GUI_link.get(index).type;

    }

    public String[] get_index_filelist(int index) {

        String[] list = null;
        if (get_index_type(index) == "File") {
            String path2 = get_index_info(index);

            file file_temp = new file();
            list = file_temp.get_file(path2);

        }
        return list;
    }

    public String get_index_info(int index) {
        String type = GUI_link.get(index).type;
        if (type == "File") {
            File_Node file_temp = new File_Node();
            file_temp = (File_Node) GUI_link.get(index);
            return file_temp.path;
        } else if (type == "Generic") {

            Generic_Node gen_temp = new Generic_Node();
            gen_temp = (Generic_Node) GUI_link.get(index);
            return gen_temp.name;

        } else if (type == "Relation") {
            Relation_Node relation_temp = new Relation_Node();
            relation_temp = (Relation_Node) GUI_link.get(index);
            return relation_temp.name;
        }else if(type=="String"){
        String_Node str_temp = new String_Node();
        str_temp=(String_Node)GUI_link.get(index);
        return str_temp.string;
        
        }
        return "false";

    }
    
    public int get_size(){
    
    int size; 
    size=GUI_link.size();
    return size;}
}
