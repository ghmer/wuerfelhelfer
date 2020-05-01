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
public class HeldenObjekt {

	private String name;
	private int mut;
	private int klugheit;
	private int intuition;
	private int charisma;
	private int fingerfertigkeit;
	private int gewandtheit;
	private int konstitution;
	private int koerperkraft;
	private int behinderung;
	private int ausdauer;
	private int lebensenergie;
	private int astralenergie;
	private int karmalenergie;
	
	private List<TalentObjekt> talente = new ArrayList<>();
	private List<TalentObjekt> zauber  = new ArrayList<>(); 

	/**
	 * @return the astralenergie
	 */
	public int getAstralenergie() {
		return astralenergie;
	}

	/**
	 * @return the ausdauer
	 */
	public int getAusdauer() {
		return ausdauer;
	}

	/**
	 * @return the behinderung
	 */
	public int getBehinderung() {
		return behinderung;
	}

	/**
	 * @return the charisma
	 */
	public int getCharisma() {
		return charisma;
	}

	public int getFertigkeit(EigenschaftEnum eigenschaft) {
		int result = 0;
		switch(eigenschaft) {
		case MU: result = getMut(); 			 break;
		case KL: result = getKlugheit(); 		 break;
		case IN: result = getIntuition(); 		 break;
		case CH: result = getCharisma(); 		 break;
		case FF: result = getFingerfertigkeit(); break;
		case GE: result = getGewandtheit();      break;
		case KO: result = getKonstitution(); 	 break;
		case KK: result = getKoerperkraft(); 	 break;
		default:
			break;
		}
		return result;
	}

	/**
	 * @return the fingerfertigkeit
	 */
	public int getFingerfertigkeit() {
		return fingerfertigkeit;
	}

	/**
	 * @return the gewandheit
	 */
	public int getGewandtheit() {
		return gewandtheit;
	}

	/**
	 * @return the intuition
	 */
	public int getIntuition() {
		return intuition;
	}

	public int getKarmalenergie() {
		return karmalenergie;
	}

	/**
	 * @return the klugheit
	 */
	public int getKlugheit() {
		return klugheit;
	}

	/**
	 * @return the koerperkraft
	 */
	public int getKoerperkraft() {
		return koerperkraft;
	}

	/**
	 * @return the konstitution
	 */
	public int getKonstitution() {
		return konstitution;
	}

	/**
	 * @return the lebensenergie
	 */
	public int getLebensenergie() {
		return lebensenergie;
	}

	/**
	 * @return the mut
	 */
	public int getMut() {
		return mut;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the talente
	 */
	public List<TalentObjekt> getTalente() {
		return talente;
	}
	
	/**
	 * @return the talente
	 */
	public List<TalentObjekt> getZauber() {
		return zauber;
	}

	/**
	 * @param astralenergie the astralenergie to set
	 */
	public void setAstralenergie(int astralenergie) {
		this.astralenergie = astralenergie;
	}

	/**
	 * @param ausdauer the ausdauer to set
	 */
	public void setAusdauer(int ausdauer) {
		this.ausdauer = ausdauer;
	}

	/**
	 * @param behinderung the behinderung to set
	 */
	public void setBehinderung(int behinderung) {
		this.behinderung = behinderung;
	}

	/**
	 * @param charisma the charisma to set
	 */
	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}
	
	/**
	 * @param fingerfertigkeit the fingerfertigkeit to set
	 */
	public void setFingerfertigkeit(int fingerfertigkeit) {
		this.fingerfertigkeit = fingerfertigkeit;
	}

	/**
	 * @param gewandheit the gewandheit to set
	 */
	public void setGewandtheit(int gewandtheit) {
		this.gewandtheit = gewandtheit;
	}

	/**
	 * @param intuition the intuition to set
	 */
	public void setIntuition(int intuition) {
		this.intuition = intuition;
	}

	public void setKarmalenergie(int karmalenergie) {
		this.karmalenergie = karmalenergie;
	}

	/**
	 * @param klugheit the klugheit to set
	 */
	public void setKlugheit(int klugheit) {
		this.klugheit = klugheit;
	}

	/**
	 * @param koerperkraft the koerperkraft to set
	 */
	public void setKoerperkraft(int koerperkraft) {
		this.koerperkraft = koerperkraft;
	}

	/**
	 * @param konstitution the konstitution to set
	 */
	public void setKonstitution(int konstitution) {
		this.konstitution = konstitution;
	}

	/**
	 * @param lebensenergie the lebensenergie to set
	 */
	public void setLebensenergie(int lebensenergie) {
		this.lebensenergie = lebensenergie;
	}

	/**
	 * @param mut the mut to set
	 */
	public void setMut(int mut) {
		this.mut = mut;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param talente the talente to set
	 */
	public void setTalente(List<TalentObjekt> talente) {
		this.talente = talente;
	}

	/**
	 * @param talente the talente to set
	 */
	public void setZauber(List<TalentObjekt> zauberListe) {
		this.zauber = zauberListe;;
		
	}
}
