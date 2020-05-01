/**
 * 
 */
package link.parzival.dsa;

/**
 * @author mario
 *
 */
public class WaffenObjekt {
	private String name;
	private String trefferpunkte;
	private int attacke;
	private int parade;
	private int initiative;
	/**
	 * @return the attacke
	 */
	public int getAttacke() {
		return attacke;
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
	 * @param attacke the attacke to set
	 */
	public void setAttacke(int attacke) {
		this.attacke = attacke;
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
	
}
