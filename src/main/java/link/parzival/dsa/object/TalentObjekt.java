/**
 * 
 */
package link.parzival.dsa.object;

/**
 * @author mario
 *
 */
public class TalentObjekt {
	private String name;
	private String be;
	private EigenschaftEnum probenTalent1;
	private EigenschaftEnum probenTalent2;
	private EigenschaftEnum probenTalent3;
	private int talentwert;
	/**
	 * @return the be
	 */
	public String getBe() {
		return be;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the probenTalent1
	 */
	public EigenschaftEnum getProbenTalent1() {
		return probenTalent1;
	}
	/**
	 * @return the probenTalent2
	 */
	public EigenschaftEnum getProbenTalent2() {
		return probenTalent2;
	}
	/**
	 * @return the probenTalent3
	 */
	public EigenschaftEnum getProbenTalent3() {
		return probenTalent3;
	}
	/**
	 * @return the value
	 */
	public int getTalentwert() {
		return talentwert;
	}
	/**
	 * @param be the be to set
	 */
	public void setBe(String be) {
		this.be = be;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param probenTalent1 the probenTalent1 to set
	 */
	public void setProbenTalent1(EigenschaftEnum probenTalent1) {
		this.probenTalent1 = probenTalent1;
	}
	/**
	 * @param probenTalent2 the probenTalent2 to set
	 */
	public void setProbenTalent2(EigenschaftEnum probenTalent2) {
		this.probenTalent2 = probenTalent2;
	}
	/**
	 * @param probenTalent3 the probenTalent3 to set
	 */
	public void setProbenTalent3(EigenschaftEnum probenTalent3) {
		this.probenTalent3 = probenTalent3;
	}
	/**
	 * @param talentwert the talentwert to set
	 */
	public void setTalentwert(int talentwert) {
		this.talentwert = talentwert;
	}
		
}
