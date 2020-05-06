/**
 * 
 */
package link.parzival.dsa;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mario
 *
 */
public class WaffenObjekt {
	public enum Distanzklasse {
		H,N,S,P
	}
	private String name;
	private String trefferpunkte;
	private int attacke;
	private int parade;
	private int initiative;
	private int waffenModAttacke;
	private int waffenModParade;
	private List<Distanzklasse> distanzklassen = new ArrayList<>();
	
	/**
	 * @return the attacke
	 */
	public int getAttacke() {
		return attacke;
	}
	/**
	 * @return the distanzklassen
	 */
	public List<Distanzklasse> getDistanzklassen() {
		return distanzklassen;
	}
	/**
	 * @return the initiative
	 */
	public int getInitiative() {
		return initiative;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the parade
	 */
	public int getParade() {
		return parade;
	}
	/**
	 * @return the trefferpunkte
	 */
	public String getTrefferpunkte() {
		return trefferpunkte;
	}
	/**
	 * @return the waffenModAttacke
	 */
	public int getWaffenModAttacke() {
		return waffenModAttacke;
	}
	/**
	 * @return the waffenModParade
	 */
	public int getWaffenModParade() {
		return waffenModParade;
	}
	/**
	 * @param attacke the attacke to set
	 */
	public void setAttacke(int attacke) {
		this.attacke = attacke;
	}
	/**
	 * @param distanzklassen the distanzklassen to set
	 */
	public void setDistanzklassen(List<Distanzklasse> distanzklassen) {
		this.distanzklassen = distanzklassen;
	}
	/**
	 * @param initiative the initiative to set
	 */
	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param parade the parade to set
	 */
	public void setParade(int parade) {
		this.parade = parade;
	}
	/**
	 * @param trefferpunkte the trefferpunkte to set
	 */
	public void setTrefferpunkte(String trefferpunkte) {
		this.trefferpunkte = trefferpunkte;
	}
	/**
	 * @param waffenModAttacke the waffenModAttacke to set
	 */
	public void setWaffenModAttacke(int waffenModAttacke) {
		this.waffenModAttacke = waffenModAttacke;
	}
	/**
	 * @param waffenModParade the waffenModParade to set
	 */
	public void setWaffenModParade(int waffenModParade) {
		this.waffenModParade = waffenModParade;
	}
	
	public void addDistanzklasse(Distanzklasse distanzklasse) {
		this.distanzklassen.add(distanzklasse);
	}
	
	public String[] getDistanzklassenAsArray() {
		int size = distanzklassen.size();
		String[] result = new String[size];
		for(int i = 0; i < size; i++) {
			result[i] = distanzklassen.get(i).name();
		}
		
		return result;
	}
	
}
