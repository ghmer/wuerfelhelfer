/**
 * 
 */
package link.parzival.dsa.object;

import java.util.ArrayList;
import java.util.List;

import link.parzival.dsa.object.enumeration.FernwaffenTypEnum;

/**
 * @author mario
 *
 */
public class FernwaffenObjekt {

	private String name;
	private FernwaffenTypEnum typ;
	private String be;
	private List<Integer> entfernungList = new ArrayList<>();
	private String tp;
	private List<Integer> tpEnfernungList = new ArrayList<>();
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the typ
	 */
	public FernwaffenTypEnum getTyp() {
		return typ;
	}
	/**
	 * @return the be
	 */
	public String getBe() {
		return be;
	}
	/**
	 * @return the entfernungList
	 */
	public List<Integer> getEntfernungList() {
		return entfernungList;
	}
	
	public int getEntfernungByName(String entfernung) {
		int index  = 0;
		
		switch(entfernung.toLowerCase()) {
		case "sehr nah"	: index = 0; break;
		case "nah"		: index = 1; break;
		case "mittel"	: index = 2; break;
		case "weit"		: index = 3; break;
		case "sehr weit": index = 4; break;
		default: index = 0;
		}
		
		return getEntfernungList().get(index);
	}
	/**
	 * @return the tp
	 */
	public String getTp() {
		return tp;
	}
	/**
	 * @return the tpEnfernungList
	 */
	public List<Integer> getTpEnfernungList() {
		return tpEnfernungList;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param typ the type to set
	 */
	public void setTyp(FernwaffenTypEnum typ) {
		this.typ = typ;
	}
	/**
	 * @param be the be to set
	 */
	public void setBe(String be) {
		this.be = be;
	}
	/**
	 * @param entfernungList the entfernungList to set
	 */
	public void setEntfernungList(List<Integer> entfernungList) {
		this.entfernungList = entfernungList;
	}
	/**
	 * @param tp the tp to set
	 */
	public void setTp(String tp) {
		this.tp = tp;
	}
	/**
	 * @param tpEnfernungList the tpEnfernungList to set
	 */
	public void setTpEnfernungList(List<Integer> tpEnfernungList) {
		this.tpEnfernungList = tpEnfernungList;
	}
	
	public void addTpEntfernung(Integer tpEntfernung) {
		this.tpEnfernungList.add(tpEntfernung);
	}
	
	public void addEntfernung(Integer entfernung) {
		this.entfernungList.add(entfernung);
	}

}
