/**
 * 
 */
package link.parzival.dsa.object;

import link.parzival.dsa.object.enumeration.EigenschaftEnum;

/**
 * @author mario
 *
 */
public class TalentObjektPlatzhalter extends TalentObjekt {

    /**
     * 
     */
    public TalentObjektPlatzhalter() {
        setBe("");
        setName("Keine Probe gewählt");
        setProbenTalent1(EigenschaftEnum.NA);
        setProbenTalent2(EigenschaftEnum.NA);
        setProbenTalent3(EigenschaftEnum.NA);
        setTalentwert(0);
    }

}
