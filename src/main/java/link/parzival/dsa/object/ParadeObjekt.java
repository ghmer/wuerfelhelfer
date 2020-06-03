/**
 * 
 */
package link.parzival.dsa.object;

import link.parzival.dsa.object.enumeration.ParadeObjektTypEnum;

/**
 * @author Mario Enrico Ragucci
 *
 */
public class ParadeObjekt {
    private String name;
    private ParadeObjektTypEnum typ;
    private int ini;
    private int waffenModifikatorAttacke;
    private int waffenModifikatorParade;
    private int parade;
    private int minBf;
    private int aktBf;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the typ
     */
    public ParadeObjektTypEnum getTyp() {
        return typ;
    }
    /**
     * @return the ini
     */
    public int getIni() {
        return ini;
    }
    /**
     * @return the waffenModifikatorAttacke
     */
    public int getWaffenModifikatorAttacke() {
        return waffenModifikatorAttacke;
    }
    /**
     * @return the waffenModifikatorParade
     */
    public int getWaffenModifikatorParade() {
        return waffenModifikatorParade;
    }
    /**
     * @return the parade
     */
    public int getParade() {
        return parade;
    }
    /**
     * @return the minBf
     */
    public int getMinBf() {
        return minBf;
    }
    /**
     * @return the aktBf
     */
    public int getAktBf() {
        return aktBf;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @param typ the typ to set
     */
    public void setTyp(ParadeObjektTypEnum typ) {
        this.typ = typ;
    }
    /**
     * @param ini the ini to set
     */
    public void setIni(int ini) {
        this.ini = ini;
    }
    /**
     * @param waffenModifikatorAttacke the waffenModifikatorAttacke to set
     */
    public void setWaffenModifikatorAttacke(int waffenModifikatorAttacke) {
        this.waffenModifikatorAttacke = waffenModifikatorAttacke;
    }
    /**
     * @param waffenModifikatorParade the waffenModifikatorParade to set
     */
    public void setWaffenModifikatorParade(int waffenModifikatorParade) {
        this.waffenModifikatorParade = waffenModifikatorParade;
    }
    /**
     * @param parade the parade to set
     */
    public void setParade(int parade) {
        this.parade = parade;
    }
    /**
     * @param minBf the minBf to set
     */
    public void setMinBf(int minBf) {
        this.minBf = minBf;
    }
    /**
     * @param aktBf the aktBf to set
     */
    public void setAktBf(int aktBf) {
        this.aktBf = aktBf;
    }
}
