package com.example.mammaldex.view;

import com.example.mammaldex.model.database.entities.Species;

/**
 * Create a Dex List Object that would get
 */
public class DexObject {

    private final String dexId;
    private final String dexTitle;
    private final int levelInDex;
    private boolean isExpanded;

    private Species speciesObject;

    public DexObject(String dexId, String dexTitle, int levelInDex, boolean isExpanded){
        this.dexId = dexId;
        this.dexTitle = dexTitle;
        this.levelInDex = levelInDex;
        this.isExpanded = isExpanded;
    }

    /**
     * Setter methods
     */
    public void setExpanded(boolean isExpanded){
        this.isExpanded = isExpanded;
    }

    public void setSpecies(Species species){
        speciesObject = species;
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

    public Species getSpecies(){
        return speciesObject;
    }





}
