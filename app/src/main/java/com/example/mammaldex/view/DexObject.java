package com.example.mammaldex.view;

/**
 * Create a Dex List Object that would get
 */
public class DexObject {

    private String dexId;
    private String dexTitle;
    private int levelInDex;
    private boolean isExpanded;

    public DexObject(String dexId, String dexTitle, int levelInDex, boolean isExpanded){
        this.dexId = dexId;
        this.dexTitle = dexTitle;
        this.levelInDex = levelInDex;
        this.isExpanded = isExpanded;
    }

    /**
     * Getter Methods
     */
    public String getDexId(){
        return dexId;
    }

    public String getDexTitle(){
        return dexTitle;
    }

    public int getLevel(){
        return levelInDex;
    }

    public boolean isExpanded(){
        return isExpanded;
    }





}
