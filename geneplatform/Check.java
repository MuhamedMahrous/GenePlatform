/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneplatform;

/**
 *
 * @author elazab
 */
public class Check {

    
    public static String checktype(String First,String relation,String Last)
    {
        if(First=="File")
        {
           if(relation=="0")
        {
            //one to one
            if(Last=="File")
        {
              return "File";
        }
            else
            {
                //error
                return "ERROR";
            }
        }  
           if(relation=="1")
        {
            // one to many
             //error
             return "ERROR";
        }  
           if(relation=="2")
        {
            //many to one
            if(Last=="Generic")
        {
            return "File";
        }
            if(Last=="String")
        {
            return "File";
        }
         else
          {
                 //error  
               return "ERROR";
          }
        }  
           if(relation=="3")
        {
            // many to many
            if(Last=="File")
        {
            return "File";
        }
            else
            {
                //error
                 return "ERROR";
            }
        }  
        }
         if(First=="Generic")
        {
            if(relation=="0")
        {
            
            //one to one
            if(Last=="Generic")
        {
            return "Generic";
           
        }
            if(Last=="String")
        {
            return "String";
        }
            else
            {
                //error
                 return "ERROR";
            }
        }  
           if(relation=="1")
        {
            // one to many
             if(Last=="File")
        {
            return "File";
        }
             else
             {
                 //error
                  return "ERROR";
             }
        }  
           if(relation=="2")
        {
            //many to one
            //error
             return "ERROR";
        }  
           if(relation=="3")
        {
            // many to many
            //error
             return "ERROR";
        }  
        }
          if(First=="String")
        {
            if(relation=="0")
        {
            //one to one
            if(Last=="Generic")
        {
            return "String";
           
        }
            if(Last=="String")
        {
            return "String";
        }
            else
            {
                //error
                 return "ERROR";
            }
        }  
           if(relation=="1")
        {
            // one to many
            if(Last=="File")
        {
            return "File";
        }
             else
             {
                 //error
                  return "ERROR";
             }
        }  
           if(relation=="2")
        {
            //many to one
            //error
             return "ERROR";
        }  
           if(relation=="3")
        {
            // many to many
            // error
             return "ERROR";
        }  
        }
          else
          {
              //error
               return "ERROR";
          }
           
        return null;
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        System.out.println(checktype("File", "1", "String"));
    }
    
}
