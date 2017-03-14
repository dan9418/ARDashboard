package com.catchoom.test;

/**
 * Created by van on 3/13/17.
 */

public class SwitchGearInfo {
    private String name;
    private String[] components;
    private int[] locations;
    private int[] powers;
    public SwitchGearInfo(String name, String[] components, int[] locations, int[] powers){
        this.name = name;
        this.components= components;
        this.locations=locations;
        this.powers=powers;
    }
    public String getName(){
        return name;
    }
    public String[] getComponents(){
        return components;
    }
    public int[] getLocations(){
        return locations;
    }
    public int[] getPowers(){
        return powers;
    }
}
