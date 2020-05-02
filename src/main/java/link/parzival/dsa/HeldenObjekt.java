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
	private int basisinitiative;
	private int magieresistenz;
	private int basisattacke;
	private int basisparade;
	private int fernkampfbasis;
	
	private boolean ausweichenI 	= false;
	private boolean ausweichenII 	= false;
	private boolean ausweichenIII	= false;
	
	private List<TalentObjekt> talente = new ArrayList<>();
	private List<TalentObjekt> zauber  = new ArrayList<>(); 
	private List<WaffenObjekt> waffen  = new ArrayList<>();

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
	 * @return the basisattacke
	 */
	public int getBasisattacke() {
		return basisattacke;
	}

	/**
	 * @return the basisinitiative
	 */
	public int getBasisinitiative() {
		return basisinitiative;
	}

	/**
	 * @return the basisparade
	 */
	public int getBasisparade() {
		return basisparade;
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

	/**
	 * @return the fernkampfbasis
	 */
	public int getFernkampfbasis() {
		return fernkampfbasis;
	}

	/**
	 * @param eigenschaft the eigenschaft to get the value of
	 * @return the value of the eigenschaft
	 */
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
	 * @return the magieresistenz
	 */
	public int getMagieresistenz() {
		return magieresistenz;
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
	 * @param waffenName the name of the waffe
	 * @return the WaffenObjekt corresponding to the name
	 */
	public WaffenObjekt getWaffeByName(String waffenName) {
		WaffenObjekt waffenObjekt = null;
		for(WaffenObjekt waffe : getWaffen()) {
			if(waffe.getName().equalsIgnoreCase(waffenName)) {
				waffenObjekt = waffe;
				break;
			}
		}
		return waffenObjekt;
	}

	/**
	 * @return the waffen
	 */
	public List<WaffenObjekt> getWaffen() {
		return waffen;
	}
	
	/** 
	 * @return the waffenNamen
	 */
	public List<String> getWaffenNamen() {
		List<String> result = new ArrayList<>();
		for(WaffenObjekt waffe : getWaffen()) {
			result.add(waffe.getName());
		}
		
		return result;
	}

	/**
	 * @return the waffenNamen
	 */
	public String[] getWaffenNamenAsArray() {
		int size = waffen.size();
		String[] result = new String[size];
		for(int i = 0; i < size; i++) {
			result[i] = waffen.get(i).getName();
		}
		
		return result;
	}

	/**
	 * @return the waffenNamen
	 */
	public List<String> getWaffenNamenAsList() {
		return getWaffenNamen();
	}

	/**
	 * @return the talente
	 */
	public List<TalentObjekt> getZauber() {
		return zauber;
	}

	/**
	 * @return the ausweichenI
	 */
	public boolean isAusweichenI() {
		return ausweichenI;
	}

	/**
	 * @return the ausweichenII
	 */
	public boolean isAusweichenII() {
		return ausweichenII;
	}

	/**
	 * @return the ausweichenIII
	 */
	public boolean isAusweichenIII() {
		return ausweichenIII;
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
	 * @param ausweichenI the ausweichenI to set
	 */
	public void setAusweichenI(boolean ausweichenI) {
		this.ausweichenI = ausweichenI;
	}

	/**
	 * @param ausweichenII the ausweichenII to set
	 */
	public void setAusweichenII(boolean ausweichenII) {
		this.ausweichenII = ausweichenII;
	}

	/**
	 * @param ausweichenIII the ausweichenIII to set
	 */
	public void setAusweichenIII(boolean ausweichenIII) {
		this.ausweichenIII = ausweichenIII;
	}

	/**
	 * @param basisattacke the basisattacke to set
	 */
	public void setBasisattacke(int basisattacke) {
		this.basisattacke = basisattacke;
	}

	/**
	 * @param basisinitiative the basisinitiative to set
	 */
	public void setBasisinitiative(int basisinitiative) {
		this.basisinitiative = basisinitiative;
	}
	
	/**
	 * @param basisparade the basisparade to set
	 */
	public void setBasisparade(int basisparade) {
		this.basisparade = basisparade;
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
	 * @param fernkampfbasis the fernkampfbasis to set
	 */
	public void setFernkampfbasis(int fernkampfbasis) {
		this.fernkampfbasis = fernkampfbasis;
	}

	/**
	 * @param fingerfertigkeit the fingerfertigkeit to set
	 */
	public void setFingerfertigkeit(int fingerfertigkeit) {
		this.fingerfertigkeit = fingerfertigkeit;
	}

	/**
	 * @param gewandtheit the gewandtheit to set
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
	 * @param magieresistenz the magieresistenz to set
	 */
	public void setMagieresistenz(int magieresistenz) {
		this.magieresistenz = magieresistenz;
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
	 * @param waffen the waffen to set
	 */
	public void setWaffen(List<WaffenObjekt> waffen) {
		this.waffen = waffen;
	}

	/**
	 * @param zauberListe the zauberListe to set
	 */
	public void setZauber(List<TalentObjekt> zauberListe) {
		this.zauber = zauberListe;;
		
	}
}
