package com.catchoom.test.communication;

/**
 * Created by Dan on 3/14/2017.
 */

public class SwitchgearUnit {

    // NAMES
    // Row 1
    private static final String UNIT1_1A_NAME = "TC100";
    private static final String UNIT1_2A_NAME = "PXM8000";
    private static final String UNIT1_3A_NAME = "PX Dashboard";
    private static final String UNIT1_4A_NAME = "Meters";

    // Row 2
    private static final String UNIT1_1B_1C_NAME = "Old Meter";
    private static final String UNIT1_2B_NAME = "Magnum Breaker";
    private static final String UNIT1_3B_NAME = "Magnum Breaker";
    private static final String UNIT1_4B_NAME = "Magnum Breaker";

    // Row 3
    private static final String UNIT1_2C_NAME = "Empty";
    private static final String UNIT1_3C_NAME = "Empty";
    private static final String UNIT1_4C_NAME = "Empty";

    //IDs
    // Row 1
    private static final String UNIT1_1A_ID = "1_1A";
    private static final String UNIT1_2A_ID = "1_2A";
    private static final String UNIT1_3A_ID = "1_3A";
    private static final String UNIT1_4A_ID = "1_4A";

    // Row 2
    private static final String UNIT1_1B_1C_ID = "1_1B_1C";
    private static final String UNIT1_2B_ID = "1_2B";
    private static final String UNIT1_3B_ID = "1_3B";
    private static final String UNIT1_4B_ID = "1_4B";

    // Row 3
    private static final String UNIT1_2C_ID = "1_2C";
    private static final String UNIT1_3C_ID = "1_3C";
    private static final String UNIT1_4C_ID = "1_4C";

    public static String getName(String ID) {
        if(ID.equals(UNIT1_1A_ID)) {
            return UNIT1_1A_NAME;
        }
        else if(ID.equals(UNIT1_2A_ID)) {
            return UNIT1_2A_NAME;
        }
        else if(ID.equals(UNIT1_3A_ID)) {
            return UNIT1_3A_NAME;
        }
        else if(ID.equals(UNIT1_4A_ID)) {
            return UNIT1_4A_NAME;
        }
        else if(ID.equals(UNIT1_1B_1C_ID)) {
            return UNIT1_1B_1C_NAME;
        }
        else if(ID.equals(UNIT1_2B_ID)) {
            return UNIT1_2B_NAME;
        }
        else if(ID.equals(UNIT1_3B_ID)) {
            return UNIT1_3B_NAME;
        }
        else if(ID.equals(UNIT1_4B_ID)) {
            return UNIT1_4B_NAME;
        }
        else if(ID.equals(UNIT1_2C_ID)) {
            return UNIT1_2C_NAME;
        }
        else if(ID.equals(UNIT1_3C_ID)) {
            return UNIT1_3C_NAME;
        }
        else if(ID.equals(UNIT1_4C_ID)) {
            return UNIT1_4C_NAME;
        }
        else {
            return "Not Found";
        }
    }

    public static String getView(String ID) {
        if(ID.equals(UNIT1_1A_ID)) {
            return UNIT1_1A_NAME;
        }
        else if(ID.equals(UNIT1_2A_ID)) {
            return UNIT1_2A_NAME;
        }
        else if(ID.equals(UNIT1_3A_ID)) {
            return UNIT1_3A_NAME;
        }
        else if(ID.equals(UNIT1_4A_ID)) {
            return UNIT1_4A_NAME;
        }
        else if(ID.equals(UNIT1_1B_1C_ID)) {
            return UNIT1_1B_1C_NAME;
        }
        else if(ID.equals(UNIT1_2B_ID)) {
            return UNIT1_2B_NAME;
        }
        else if(ID.equals(UNIT1_3B_ID)) {
            return UNIT1_3B_NAME;
        }
        else if(ID.equals(UNIT1_4B_ID)) {
            return UNIT1_4B_NAME;
        }
        else if(ID.equals(UNIT1_2C_ID)) {
            return UNIT1_2C_NAME;
        }
        else if(ID.equals(UNIT1_3C_ID)) {
            return UNIT1_3C_NAME;
        }
        else if(ID.equals(UNIT1_4C_ID)) {
            return UNIT1_4C_NAME;
        }
        else {
            return "Not Found";
        }
    }

}
