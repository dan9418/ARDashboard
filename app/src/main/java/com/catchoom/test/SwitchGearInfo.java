package com.catchoom.test;

/**
 * Created by van on 3/13/17.
 */

public class SwitchGearInfo {
    private String[] components;
    public SwitchGearInfo(String[] components){
        this.components= components;
    }
    public String[] getComponents(){
        return components;
    }
    public String findName(String id){
        for (int i=0; i<components.length; i++){
            String [] names = components[i].split("_");
            if(names[1].equalsIgnoreCase(id)){
                return components[i];
            }
        }
        return null;
    }
    public String[] findNames(String name){
        String names = "names";
        for (int i=0; i<components.length; i++){
            String [] Allnames = components[i].split("_");
            if(Allnames[0].equalsIgnoreCase(name)){
               names=names+","+components[i];
            }
        }
        try{
            return names.split(",");
        }catch (Exception e){
            return null;
        }

    }

}
