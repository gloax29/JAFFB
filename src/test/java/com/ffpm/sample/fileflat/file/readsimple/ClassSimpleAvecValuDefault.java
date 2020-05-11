/*
 * Creation : 12 mars 2018
 */
package com.ffpm.sample.fileflat.file.readsimple;

import java.sql.Timestamp;

import javax.flat.bind.annotation.positinal.PositionalJavaTypeAdapter;
import javax.flat.bind.annotation.positinal.PositionalMappingParse;

public class ClassSimpleAvecValuDefault {

    @PositionalMappingParse(offset = 1, length = 1, DefaultValue = "}")
    private String en_tete;
    @PositionalMappingParse(offset = 2, length = 1, DefaultValue = "1")
    private String version;
    @PositionalMappingParse(offset = 3, length = 4)
    @PositionalJavaTypeAdapter(value = NumberConvertQuatre0.class, DefaultValue = ValueLong159.class)
    private Integer longueur;
    @PositionalMappingParse(offset = 7, length = 4)
    private String crc;
    @PositionalMappingParse(offset = 11, length = 4)
    @PositionalJavaTypeAdapter(value = NumberConvertQuatre0.class, DefaultValue = ValueRandom.class)
    private Integer numSequence;
    @PositionalMappingParse(offset = 15, length = 18)
    @PositionalJavaTypeAdapter(value = DateConvertyyyyMMddHHmmsssSSS.class, DefaultValue = ValueTimestamp.class)
    private Timestamp horodatageSaig;

    /**
     * Getter en_tete
     * 
     * @return the en_tete
     */
    public String getEn_tete() {
        return en_tete;
    }

    /**
     * Setter en_tete
     * 
     * @param en_tete the en_tete to set
     */
    public void setEn_tete(String en_tete) {
        this.en_tete = en_tete;
    }

    /**
     * Getter version
     * 
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Setter version
     * 
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Getter longueur
     * 
     * @return the longueur
     */
    public Integer getLongueur() {
        return longueur;
    }

    /**
     * Setter longueur
     * 
     * @param longueur the longueur to set
     */
    public void setLongueur(Integer longueur) {
        this.longueur = longueur;
    }

    /**
     * Getter crc
     * 
     * @return the crc
     */
    public String getCrc() {
        return crc;
    }

    /**
     * Setter crc
     * 
     * @param crc the crc to set
     */
    public void setCrc(String crc) {
        this.crc = crc;
    }

    /**
     * Getter numSequence
     * 
     * @return the numSequence
     */
    public Integer getNumSequence() {
        return numSequence;
    }

    /**
     * Setter numSequence
     * 
     * @param numSequence the numSequence to set
     */
    public void setNumSequence(Integer numSequence) {
        this.numSequence = numSequence;
    }

    /**
     * Getter horodatageSaig
     * 
     * @return the horodatageSaig
     */
    public Timestamp getHorodatageSaig() {
        return horodatageSaig;
    }

    /**
     * Setter horodatageSaig
     * 
     * @param horodatageSaig the horodatageSaig to set
     */
    public void setHorodatageSaig(Timestamp horodatageSaig) {
        this.horodatageSaig = horodatageSaig;
    }

}
