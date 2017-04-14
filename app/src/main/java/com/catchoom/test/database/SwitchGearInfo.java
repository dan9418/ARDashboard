package com.catchoom.test.database;

/**
 * Created by van on 3/13/17.
 * @author Vinayak Nesarikar
 * Encapsulating class that holds all the data for the SwitchGear
 */

public class SwitchGearInfo {
    private String[] components;

    /**
     * Constructor
     * @param components array of all the components in the switch gear
     */
    public SwitchGearInfo(String[] components){
        this.components= components;
    }

    /**
     * Gets all the components in the switch gear
     * @return the array of all the components in the switch gear
     */
    public String[] getComponents(){
        return components;
    }

    /**
     * Finds the name corresponding to an id
     * @param id the components id
     * @return the corresponding name
     */
    public String findName(String id){
        for (int i=0; i<components.length; i++){
            String [] names = components[i].split("_");
            if(names[1].equalsIgnoreCase(id)){
                return components[i];
            }
        }
        return null;
    }
    /**
     * Finds the all the name corresponding to a tag
     * @param name the components tag
     * @return All the corresponding name
     */
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
