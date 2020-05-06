/**
 * 
 */
package link.parzival.dsa;

import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.ParadeObjekt;
import link.parzival.dsa.object.Sonderfertigkeit;
import link.parzival.dsa.object.WaffenObjekt;
import link.parzival.dsa.object.enumeration.DKEnum;

/**
 * @author mario
 *
 */
public class DsaCalculatorUtil {
	
	/**
	 * @param waffenDk
	 * @param kampfDk
	 * @return
	 */
	public static int getDistanceBetween(DKEnum waffenDk, DKEnum kampfDk) {
		int result = 0;
		result     = waffenDk.ordinal() - kampfDk.ordinal();
		
		
		return result;
	}
	
	/**
	 * @param waffenObjekt
	 * @param modificator
	 * @param useDistanceClass
	 * @param kampfDk
	 * @param waffenDk
	 * @return
	 */
	public static String getEffectiveAttackRoll(WaffenObjekt waffenObjekt, int modificator, boolean useDistanceClass, DKEnum kampfDk, DKEnum waffenDk) {
		int attack    = waffenObjekt.getAttacke();
		attack       -= modificator;
		String result = String.format("!%s Attacke (Waffe)", attack);
		
		if(useDistanceClass) {
			int effectiveDistance = getDistanceBetween(waffenDk, kampfDk);
			int distanceModificator = 0;
			switch(effectiveDistance) {
				case -1: distanceModificator = -6; break;
				case  0: distanceModificator =  0; break;
				case  1: distanceModificator = -6; break;
			}
			
			attack += distanceModificator;
			
			if(effectiveDistance != 0) {
				
				result = String.format("!%s Angriff (erschwert aufgrund falscher Distanz)", attack);
			} else {
				result = String.format("!%s Angriff (optimale Distanzklasse)", attack);
			}
		}
		
		return result;
	}
	
	/**
	 * @param initiative
	 * @return
	 */
	public static int getAdditionalParadeByInitiative(int initiative) {
		int additionalParade = 0;
		
		if(initiative > 20) {
			additionalParade += 1;
		}
		
		if(initiative > 30) {
			additionalParade += 1;
		}
		
		if(initiative > 40) {
			additionalParade += 1;
		}
		
		return additionalParade;
	}
	
	/**
	 * @param waffenObjekt
	 * @param modificator
	 * @param initiative
	 * @param useDistanceClass
	 * @param kampfDk
	 * @param waffenDk
	 * @return
	 */
	public static String getEffectiveWeaponParadeRoll(WaffenObjekt waffenObjekt, int modificator, int initiative, boolean useDistanceClass, DKEnum kampfDk, DKEnum waffenDk) {
		int parade    = waffenObjekt.getParade();
		int iniParade = getAdditionalParadeByInitiative(initiative);
		parade		 += iniParade;
		parade		 -= modificator;
		String result = String.format("!%s Parade (Waffe)", parade);
		
		if(useDistanceClass) {
			int effectiveDistance 	= getDistanceBetween(waffenDk, kampfDk);
			int distanceModificator = 0;
			switch(effectiveDistance) {
				case -1: distanceModificator =  0; break;
				case  0: distanceModificator =  0; break;
				case  1: distanceModificator = -6; break;
			}		
			parade += distanceModificator;
			switch(effectiveDistance) {
			case -3: result = String.format("!%s Parade (falsche Distanz, keine Erschwerung auf Parade)", parade);	break;
			case -2: result = String.format("!%s Parade (falsche Distanz, keine Erschwerung auf Parade)", parade);	break;
			case -1: result = String.format("!%s Parade (falsche Distanz, keine Erschwerung auf Parade)", parade); break;
			case  0: result = String.format("!%s Parade (optimale Distanzklasse)", parade);break;
			case  1: result = String.format("!%s Parade (erschwert aufgrund falscher Distanz)", parade); break;
			}
						
			if(effectiveDistance != 0) {
				if(effectiveDistance == -1) {
					
				} else {
					
				}
			} else {
				
			}
		}
		
		return result;
	}
	
	/**
	 * @param waffenObjekt
	 * @param paradeObjekt
	 * @param modificator
	 * @param initiative
	 * @param useDistanceClass
	 * @param kampfDk
	 * @param waffenDk
	 * @return
	 */
	public static String getEffectiveShieldParadeRoll(WaffenObjekt waffenObjekt, ParadeObjekt paradeObjekt, int modificator, int initiative, boolean useDistanceClass, DKEnum kampfDk, DKEnum waffenDk) {
		int parade = 0;
		switch(paradeObjekt.getTyp()) {
			case Schild 		: {
				parade	= paradeObjekt.getParade();
				break;
			}
			case Paradewaffe	: {
				parade	= waffenObjekt.getParade();
				int paradeMod = paradeObjekt.getWaffenModifikatorParade();
				parade += paradeMod;
				break;
			}
		}
		
		int iniParade = getAdditionalParadeByInitiative(initiative);
		parade		 += iniParade;
		
		parade -= modificator;
		String result = String.format("!%s Parade (%s)", parade, paradeObjekt.getTyp().name());
		
		
		if(useDistanceClass) {
			int effectiveDistance = getDistanceBetween(waffenDk, kampfDk);
			int distanceModificator = 0;
			switch(effectiveDistance) {
				case -1: distanceModificator =  0; break;
				case  0: distanceModificator =  0; break;
				case  1: distanceModificator = -6; break;
			}
			parade += distanceModificator;
			if(effectiveDistance != 0) {
				result = String.format("!%s Parade (erschwert aufgrund falscher Distanz)", parade);
			} else {
				result = String.format("!%s Parade (optimale Distanzklasse)", parade);
			}
		}
		
		return result;
	}
	
	/**
	 * @param hero The HeroObjekt to use
	 * @param waffe the WaffenObjekt to use
	 * @param paradeObjekt the ParadeObjekt to use
	 * @return
	 */
	public static String getEffectiveInitiativeRoll(HeldenObjekt hero, WaffenObjekt waffe, ParadeObjekt paradeObjekt) {
		String result = null;
		boolean bladedancer = false;
		int basis  = hero.getBasisinitiative();
		
		for(Sonderfertigkeit sf : hero.getSonderfertigkeiten()) {
			if(sf.getName().equalsIgnoreCase("kampfreflexe")) {
				basis += 4;
			}
			
			if(sf.getName().equalsIgnoreCase("kampfgespür")) {
				basis += 2;
			}
			
			if(sf.getName().equalsIgnoreCase("klingentänzer")) {
				bladedancer = true;
			}
		}
		
		if(waffe != null) {
			basis += waffe.getInitiative();
		}
		
		if(paradeObjekt != null) {
			basis += paradeObjekt.getIni();
		}
		
		result = String.format("(%sw6+%s) Initiative",
				(bladedancer ? 2:1),
				basis);
		
		return result;
	}
	
	/**
	 * @param hero The HeroObjekt to use
	 * @param enemyCount the number of enemies 
	 * @param gezieltesAusweichen whether to use gezieltes Ausweichen
	 * @param withDk whether to use Distanzklassen
	 * @param distanzklasse the Distanzklasse to use
	 * @return the rollCommand for evasion
	 */
	public static String getEffectiveEvadingRoll(HeldenObjekt hero, int enemyCount, boolean gezieltesAusweichen, boolean withDk, DKEnum distanzklasse) {
		String result 	= null;
		int effective   = 0;
		int basis     	= hero.getBasisparade();
		int behinderung = hero.getBehinderung();
		int sfMod		= 0;
		int enemyBe		= (2 * enemyCount) - 2;
		int distanzklassenErschwernis = 0;
		
		for(Sonderfertigkeit sf : hero.getSonderfertigkeiten()) {
			if(sf.getName().equalsIgnoreCase("Ausweichen I")) {
				sfMod += 3;
			}
			
			if(sf.getName().equalsIgnoreCase("Ausweichen II")) {
				sfMod += 3;
			}
			
			if(sf.getName().equalsIgnoreCase("Ausweichen III")) {
				sfMod += 3;
			}
		}
		
		if(withDk) {
			int multiplier = gezieltesAusweichen ? 2 : 1;
			switch(distanzklasse) {
			case H: distanzklassenErschwernis = 4 * multiplier; break;
			case N: distanzklassenErschwernis = 2 * multiplier; break;
			case S: distanzklassenErschwernis = 1 * multiplier; break;
			case P: distanzklassenErschwernis = 0; break;
			}
		}
		
		effective = (((basis - behinderung) - enemyBe) - distanzklassenErschwernis) + sfMod;
		result    = String.format("!%s Ausweichen", effective);
		
		return result;
	}

	/**
	 * @param waffenObjekt the WaffenObjekt to use
	 * @param verdoppeln whether to double the stakes
	 * @param verkuerzen whether to get closer to the enemy
	 * @return the rollCommand for the distance change
	 */
	public static String getChangeDistanceEffectiveRoll(WaffenObjekt waffenObjekt, boolean verdoppeln, boolean verkuerzen) {
		String result 	= "";
		int attacke 	= 0;
		if(verkuerzen) {
			if(verdoppeln) {
				attacke = waffenObjekt.getAttacke() - 8;
			} else {
				attacke = waffenObjekt.getAttacke();
			}
		} else {
			if(verdoppeln) {
				attacke = waffenObjekt.getAttacke() - 8;
			} else {
				attacke = waffenObjekt.getAttacke() - 4;
			}
		}
		
		result = String.format("!%s Angriff - %s um %s Distanzklassen",
				attacke, 
				verkuerzen ? "Annährung" : "Entfernen",
				verdoppeln ? 2 : 1);
		
		return result;
	}

}
