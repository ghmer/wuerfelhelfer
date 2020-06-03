/**
 * 
 */
package link.parzival.dsa;

import java.util.List;

import link.parzival.dsa.object.FernwaffenObjekt;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.KampftechnikObjekt;
import link.parzival.dsa.object.ParadeObjekt;
import link.parzival.dsa.object.Sonderfertigkeit;
import link.parzival.dsa.object.TalentObjekt;
import link.parzival.dsa.object.WaffenObjekt;
import link.parzival.dsa.object.enumeration.DKEnum;

/**
 * @author Mario Enrico Ragucci
 *
 */
public class BerechnungsHelfer {
    
    /**
     * @param initiative the initiative
     * @return the additional parade by the initiative
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
     * @param waffenObjekt the WaffenObjekt to use
     * @param verdoppeln whether to double the stakes
     * @param verkuerzen whether to get closer to the enemy
     * @return the rollCommand for the distance change
     */
    public static String getChangeDistanceEffectiveRoll(WaffenObjekt waffenObjekt, boolean verdoppeln, boolean verkuerzen) {
        String result = "";
        int attacke   = 0;
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
    
    /**
     * @param waffenDk the distanzklasse of the current weapon
     * @param kampfDk the distanzklasse of the combat (aka current distanzklasse)
     * @return integer defining the distance between the current weapon distanzklasse and the combat distanzklasse
     */
    public static int getDistanceBetween(DKEnum waffenDk, DKEnum kampfDk) {
        int result = 0;
        result     = waffenDk.ordinal() - kampfDk.ordinal();
        
        
        return result;
    }
    
    /**
     * @param waffenObjekt the WaffenObjekt
     * @param modificator another modificator
     * @param useDistanceClass whether to use Distanzklassen
     * @param kampfDk Distanzklasse of the combat
     * @param waffenDk Distanzklasse of the weapon
     * @return the rollCommand
     */
    public static String getEffectiveWeaponAttackRoll(WaffenObjekt waffenObjekt, int modificator, boolean useDistanceClass, DKEnum kampfDk, DKEnum waffenDk) {
        int attack      = waffenObjekt.getAttacke();
        attack         -= modificator;
        String result   = String.format("!%s Attacke (Waffe)", attack);
        
        result = applyDistanceClasses(useDistanceClass, kampfDk, waffenDk, attack, result);
        
        return result;
    }

    /**
     * @param useDistanceClass use distance classes
     * @param kampfDk the current distance class
     * @param waffenDk the distance class of the weapon of kampftechnik
     * @param attack the attack TaW
     * @param result the result if DK is not applied
     * @return the actual result
     */
    private static String applyDistanceClasses(boolean useDistanceClass, DKEnum kampfDk, DKEnum waffenDk, int attack,
            String result) {
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
     * @param kampftechnik the Kampftechnik to use
     * @param modificator the Modifikator
     * @param useDistanceClass whether to use Distance Classes
     * @param kampfDk the current distance class
     * @param waffenDk the distance class of the Kampftechnik
     * @return the roll command
     */
    public static String getEffectiveTechnicalAttackRoll(KampftechnikObjekt kampftechnik, int modificator, boolean useDistanceClass, DKEnum kampfDk, DKEnum waffenDk) {
        int attack    = kampftechnik.getAttacke();
        attack       -= modificator;
        String result = String.format("!%s Attacke (Kampftechnik: %s)", attack, kampftechnik.getName());
        
        result = applyDistanceClasses(useDistanceClass, kampfDk, waffenDk, attack, result);
        
        return result;
    }
    
    /**
     * @param attackeBasis the attacke before any modifications
     * @param modificator a Modifikator
     * @param useDistanceClass whether to use distance classes
     * @param kampfDk the current distance class
     * @param waffenDk the distance class of the weapon or kampftechnik
     * @return the roll command
     */
    public static String getEffectiveAttackRoll(int attackeBasis, int modificator, boolean useDistanceClass, DKEnum kampfDk, DKEnum waffenDk) {
        int attack    = attackeBasis;
        attack       -= modificator;
        String result = String.format("!%s Attacke", attack);
        
        result = applyDistanceClasses(useDistanceClass, kampfDk, waffenDk, attack, result);
        
        return result;
    }
    
    /**
     * @param hero The HeroObjekt to use
     * @param initiative the initiative of the hero in the fight
     * @param enemyCount the number of enemies 
     * @param gezieltesAusweichen whether to use gezieltes Ausweichen
     * @param withDk whether to use Distanzklassen
     * @param distanzklasse the Distanzklasse to use
     * @return the rollCommand for evasion
     */
    public static String getEffectiveEvadingRoll(HeldenObjekt hero, int initiative, int enemyCount, boolean gezieltesAusweichen, boolean withDk, DKEnum distanzklasse) {
        String result   = null;
        int effective   = 0;
        int basis       = hero.getBasisparade();
        int behinderung = hero.getBehinderung();
        int iniParade   = getAdditionalParadeByInitiative(initiative);
        int sfMod       = 0;
        int enemyBe     = (2 * enemyCount) - 2;
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
        
        // Wenn Athletik >= 9, alle drei weiteren Punkte ein Punkt mehr
        int athletikMod = 0;
        TalentObjekt athletik = hero.getTalentByName("Athletik");
        
        if(athletik != null) {
            int athletikTaw = athletik.getTalentwert();
            if(athletikTaw >= 9) {
                athletikMod     = (athletikTaw - 9) / 3;
            }            
        }
        
        effective = (((((basis - behinderung) - enemyBe) - distanzklassenErschwernis) + sfMod) + athletikMod) + iniParade;
        result    = String.format("!%s Ausweichen", effective);
        
        return result;
    }
    
    /**
     * @param hero The HeroObjekt to use
     * @param waffe the WaffenObjekt to use
     * @param paradeObjekt the ParadeObjekt to use
     * @return the rollCommand
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
     * @param paradeBasis the basis parade value
     * @param modificator the Modifikator
     * @param initiative the initiative in the current combat
     * @param useDistanceClass whether to use distance classes
     * @param combatWeaponDistance the current distance class in the combat
     * @param typeOfParade the type of Parade (Ringen/Raufen)
     * @return the rollCommand
     */
    public static String getEffectiveParadeRoll(int paradeBasis, int modificator,
            int initiative, boolean useDistanceClass, DKEnum combatWeaponDistance, String typeOfParade) {
        int parade    = paradeBasis;
        int iniParade = getAdditionalParadeByInitiative(initiative);
        parade       += iniParade;
        parade       -= modificator;
        String result = String.format("!%s Parade (%s)", parade, typeOfParade);
        
        if(useDistanceClass) {
            int effectiveDistance   = getDistanceBetween(DKEnum.H, combatWeaponDistance);
            int distanceModificator = 0;
            switch(effectiveDistance) {
                case -1: distanceModificator =  0; break;
                case  0: distanceModificator =  0; break;
                case  1: distanceModificator = -6; break;
            }        
            parade += distanceModificator;
            switch(effectiveDistance) {
            case -3: result = String.format("!%s Parade (falsche Distanz, keine Erschwerung auf Parade)", parade); break;
            case -2: result = String.format("!%s Parade (falsche Distanz, keine Erschwerung auf Parade)", parade); break;
            case -1: result = String.format("!%s Parade (falsche Distanz, keine Erschwerung auf Parade)", parade); break;
            case  0: result = String.format("!%s Parade (optimale Distanzklasse)", parade);                        break;
            case  1: result = String.format("!%s Parade (erschwert aufgrund falscher Distanz)", parade);           break;
            }
        }
        
        return result;
    }
    
    /**
     * @param waffenObjekt the WaffenObjekt
     * @param paradeObjekt the ParadeObjekt
     * @param modificator another modificator
     * @param initiative the initiative to use
     * @param useDistanceClass whether to use Distanzklassen
     * @param kampfDk Distanzklasse of the combat
     * @param waffenDk Distanzklasse of the weapon
     * @return the rollCommand
     */
    public static String getEffectiveShieldParadeRoll(WaffenObjekt waffenObjekt, ParadeObjekt paradeObjekt, int modificator, int initiative, boolean useDistanceClass, DKEnum kampfDk, DKEnum waffenDk) {
        int parade = 0;
        switch(paradeObjekt.getTyp()) {
            case Schild        : {parade = paradeObjekt.getParade(); break;}
            case Paradewaffe: {
                parade         = waffenObjekt.getParade();
                int paradeMod  = paradeObjekt.getWaffenModifikatorParade();
                parade        += paradeMod;
                break;
            }
        }
        
        
        int iniParade = getAdditionalParadeByInitiative(initiative);
        parade += iniParade;      
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
     * @param kampftechnikObjekt the KampftechnikObjekt to use
     * @param modificator the Modifikator
     * @param initiative the initiative in the current combat
     * @param useDistanceClass whether to use distance classes
     * @param combatWeaponDistance the current distance class in the combat
     * @param typeOfParade the type of Parade (Ringen/Raufen)
     * @return the rollCommand
     */
    public static String getEffectiveTechnicalParadeRoll(KampftechnikObjekt kampftechnikObjekt, int modificator,
            int initiative, boolean useDistanceClass, DKEnum combatWeaponDistance, String typeOfParade) {
        int paradeBasis = kampftechnikObjekt.getParade();
        return getEffectiveParadeRoll(paradeBasis, modificator, initiative, useDistanceClass, combatWeaponDistance, typeOfParade);
    }
    
    /**
     * @param waffenObjekt the WaffenObjekt
     * @param modificator another modificator
     * @param initiative the initiative to use
     * @param useDistanceClass whether to use Distanzklassen
     * @param kampfDk Distanzklasse of the combat
     * @param waffenDk Distanzklasse of the weapon
     * @return the rollCommand
     */
    public static String getEffectiveWeaponParadeRoll(WaffenObjekt waffenObjekt, int modificator, int initiative, boolean useDistanceClass, DKEnum kampfDk, DKEnum waffenDk) {
        int parade      = waffenObjekt.getParade();
        int iniParade   = getAdditionalParadeByInitiative(initiative);
        parade         += iniParade;
        parade         -= modificator;
        String result   = String.format("!%s Parade (Waffe)", parade);
        
        if(useDistanceClass) {
            int effectiveDistance   = getDistanceBetween(waffenDk, kampfDk);
            int distanceModificator = 0;
            switch(effectiveDistance) {
                case -1: distanceModificator =  0; break;
                case  0: distanceModificator =  0; break;
                case  1: distanceModificator = -6; break;
            }        
            parade += distanceModificator;
            switch(effectiveDistance) {
            case -3: result = String.format("!%s Parade (falsche Distanz, keine Erschwerung auf Parade)", parade); break;
            case -2: result = String.format("!%s Parade (falsche Distanz, keine Erschwerung auf Parade)", parade); break;
            case -1: result = String.format("!%s Parade (falsche Distanz, keine Erschwerung auf Parade)", parade); break;
            case  0: result = String.format("!%s Parade (optimale Distanzklasse)", parade);                        break;
            case  1: result = String.format("!%s Parade (erschwert aufgrund falscher Distanz)", parade);           break;
            }
        }
        
        return result;
    }
    
    /**
     * @param bewegung Bewegung des Ziels
     * @param anzahlGegnerInDistanzH Anzahl der Gegner in Distanz H (bei Kampfgetümmel)
     * @param anzahlGegnerInDistanzNS Anzahl der Gegner in Distanz NS (bei Kampfgetümmel)
     * @return Erschwernis
     */
    public static int getFernkampfBewegungsModifikator(String bewegung, int anzahlGegnerInDistanzH, int anzahlGegnerInDistanzNS) {
        int result = 0;
        switch(bewegung.toLowerCase()) {
        case "unbewegliches / fest montiertes ziel"         : result += -4; break;
        case "stillstehendes ziel"                          : result += -2; break;
        case "leichte bewegung des ziels"                   : result +=  0; break;
        case "schnelle bewegung des ziels"                  : result +=  2; break;
        case "sehr schnelle bewegung / ausweichbewegungen"  : result +=  4; break;
        case "kampfgetümmel": {
            result += (anzahlGegnerInDistanzH * 2) + (anzahlGegnerInDistanzNS * 3);
        }
        }
        
        return result;
    }
    
    /**
     * @param entfernung Entfernung zum Ziel
     * @return Erschwernis
     */
    public static int getFernkampfEntfernungsModifikator(String entfernung) {
        int result = 0;
        switch(entfernung.toLowerCase()) {
        case "sehr nah"     : result += -2; break;
        case "nah"          : result +=  0; break;
        case "mittel"       : result +=  4; break;
        case "weit"         : result +=  8; break;
        case "sehr weit"    : result += 12; break;
        }
        
        return result;
    }
    
    /**
     @param schuetzentyp Schützentyp (Normal, Scharfschütze, Meisterschütze)
     * @param humanoidesZiel ist das Ziel Humanoid oder ein Tier
     * @param inBewegung ist das Körperteil in Bewegung?
     * @param trefferzone gewählte Trefferzone
     * @param zielgroesse Größe des Ziels (benötigt für Schwanztreffer bei Tieren)
     * @return Erschwernis
     */
    public static int getFernkampfGezielterSchussModifikator(String schuetzentyp, boolean humanoidesZiel, boolean inBewegung, String trefferzone, String zielgroesse) {
        int result = 0;
        int zielgroeseInt = 0;
        switch(zielgroesse.toLowerCase()) {
        case "winzig"       : zielgroeseInt += 8; break;
        case "sehr klein"   : zielgroeseInt += 6; break;
        case "klein"        : zielgroeseInt += 4; break;
        case "mittel"       : zielgroeseInt += 2; break;
        case "groß"         : zielgroeseInt += 0; break;
        case "sehr groß"    : zielgroeseInt += 0; break;
        }
        
        if(humanoidesZiel) {
            switch(trefferzone.toLowerCase()) {
            case "kopf"  : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result +=  7; break;
                case "meisterschütze"   : result +=  5; break;
                default: result += 10; break;
                }
                break;
            }
            case "brust" : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result += 4; break;
                case "meisterschütze"   : result += 3; break;
                default: result += 6; break;
                }
                break;
            }
            case "arme"  : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result +=  7; break;
                case "meisterschütze"   : result +=  5; break;
                default: result += 10; break;
                }
                break;
            }
            case "bauch" : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result += 4; break;
                case "meisterschütze"   : result += 3; break;
                default: result += 6; break;
                }
                break;
            }
            case "beine" : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result += 5; break;
                case "meisterschütze"   : result += 4; break;
                default: result += 8; break;
                }
                break;
            }
            case "hand"  : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result += 11; break;
                case "meisterschütze"   : result +=  8; break;
                default: result += 16; break;
                }
                break;
            }
            case "fuß"   : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result += 11; break;
                case "meisterschütze"   : result +=  8; break;
                default: result += 16; break;
                }
                break;
            }
            case "auge"  : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result += 13; break;
                case "meisterschütze"   : result += 10; break;
                default: result += 20; break;
                }
                break;
            }
            case "herz"  : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result += 13; break;
                case "meisterschütze"   : result += 10; break;
                default: result += 20; break;
                }
                break;
            }
            }
        } else {

            switch(trefferzone.toLowerCase()) {
            case "rumpf"  : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result += 3; break;
                case "meisterschütze"   : result += 2; break;
                default: result += 4; break;
                }
                break;
            }
            case "bein" : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result +=  7; break;
                case "meisterschütze"   : result +=  5; break;
                default: result += 10; break;
                }
                break;
            }
            case "verwundbare stelle"  : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result +=  8; break;
                case "meisterschütze"   : result +=  6; break;
                default: result += 12; break;
                }
                break;
            }
            case "kopf" : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result += 11; break;
                case "meisterschütze"   : result +=  8; break;
                default: result += 16; break;
                }
                break;
            }
            case "schwanz" : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result += (8 + zielgroeseInt); break;
                case "meisterschütze"   : result += (5 + zielgroeseInt); break;
                default: result += (4 + zielgroeseInt); break;
                }
                break;
            }
            case "sinnesorgane"  : {
                switch(schuetzentyp.toLowerCase()) {
                case "scharfschütze"    : result += 11; break;
                case "meisterschütze"   : result +=  8; break;
                default: result += 16; break;
                }
                break;
            }
            }
        }
        
        if(inBewegung) {
            result += 2;
        }
        
        return result;
    }

    /**
     * @param groesse groesse des Ziels
     * @param modifikator zusätzliche Erschwernis
     * @param halbdeckung Ziel hinter Halbdeckung
     * @param dreivierteldeckung Ziel hinter Dreivierteldeckung
     * @return Erschwernis
     */
    public static int getFernkampfGroessenModifikator(String groesse, int modifikator, boolean halbdeckung, boolean dreivierteldeckung) {
        int result = 0;
        switch(groesse.toLowerCase()) {
        case "winzig"       : result +=  8; break;
        case "sehr klein"   : result +=  6; break;
        case "klein"        : result +=  4; break;
        case "mittel"       : result +=  2; break;
        case "groß"         : result +=  0; break;
        case "sehr groß"    : result += -2; break;
        }
        
        result += modifikator;
        if(halbdeckung) result += 2;
        if(dreivierteldeckung) result += 4;
            
        return result;
    }
    
    /**
     * @param modifikatoren Liste der gewählten Modifikatoren
     * @param schuetzentyp Schützentyp (Normal, Scharfschütze, Meisterschütze)
     * @param ansage Freiwillige Ansage
     * @param zielen Erleichterung durch Zielen
     * @return Erschwernis
     */
    public static int getFernkampfModifikatorenModifikator(List<String> modifikatoren, String schuetzentyp, int ansage, int zielen) {
        int result = 0;
        int schuetzentypInt = 0;
        
        switch(schuetzentyp.toLowerCase()) {
        case "scharfschütze" : schuetzentypInt = 1;
        case "meisterschütze": schuetzentypInt = 2;
        default: schuetzentypInt = 0;
        }
        
        for(String mod : modifikatoren) {
            if(mod.equals("Steilschuss nach unten"))         result += 2;
            if(mod.equals("Steilwurf nach unten"))           result += 2;
            if(mod.equals("Steilschuss nach oben"))          result += 4;
            if(mod.equals("Steilwurf nach oben"))            result += 8;
            if(mod.equals("böiger Seitenwind"))              result += 4;
            if(mod.equals("starker böiger Seitenwind"))      result += 8;
            if(mod.equals("Schnellschuss"))                  result += (2 - schuetzentypInt);
            if(mod.equals("Gegner unter Wasser"))            result += 5;
            if(mod.equals("zweiter Schuss pro KR"))          result += 4;
            if(mod.equals("zweiter Wurf pro KR"))            result += 2;
            if(mod.equals("Schuss von stehendem Tier"))      result += 2;
            if(mod.equals("Wurf von stehendem Tier"))        result += 1;
            if(mod.equals("Schuss vom Reittier im Schritt")) result += 4;
            if(mod.equals("Wurf vom Reittier im Schritt"))   result += 2;
            if(mod.equals("Schuss im Galopp"))               result += 8;
            if(mod.equals("Wurf im Galopp"))                 result += 4;
            if(mod.equals("Schuss ohne Sattel"))             result += 4;
            if(mod.equals("Wurf ohne Sattel"))               result += 2;
        }
        
        result += ansage;
        result -= zielen;
        
        return result;
    }
    
    public static String getFernkampfRollCommand(int fk, int erschwernis) {
        String result     = null;
        result    = String.format("!%s,+%s Fernkampf", fk, erschwernis);
        
        return result;
    }

    /**
     * @param lichtquelle Die aktuellen Lichtverhältnisse
     * @param dunst Ist es Dunstig
     * @param nebel Ist es Neblig
     * @param entfernungssinn Hat man den Vorteil Entfernungssinn 
     * @param daemmerungssicht Hat man den Vorteil Dämmerungssicht
     * @param nachtsicht Hat man den Vorteil Nachtsicht
     * @param einaeugig Hat man den Nachteil Einäugig
     * @param farbenblind Hat man den Nachteil Farbenblind
     * @param kurzsichtig Hat man den Nachteil Kurzsichtig
     * @param nachtblind Hat man den Nachteil Nachtblind
     * @param entfernung die Entfernung zum Ziel
     * @param fernwaffe das FernwaffenObjekt
     * @return Erschwernis
     */
    public static int getFernkampfSichtModifikator(String lichtquelle,
            boolean dunst, 
            boolean nebel, 
            boolean entfernungssinn,
            boolean daemmerungssicht,
            boolean nachtsicht,
            boolean einaeugig,
            boolean farbenblind,
            boolean kurzsichtig,
            boolean nachtblind,
            String entfernung, 
            FernwaffenObjekt fernwaffe) 
    {
        int result = 0;
        
        switch(lichtquelle.toLowerCase()) {
        case "tageslicht"       : result += 0; break;
        case "dämmerung"        : result += (nachtblind) ? 4 : (daemmerungssicht || nachtsicht) ? 1 : 2; break;
        case "mondlicht"        : result += (nachtblind) ? 8 : (daemmerungssicht || nachtsicht) ? 2 : 4; break;
        case "sternenlicht"     : result += (nachtblind) ? 8 : (daemmerungssicht || nachtsicht) ? 3 : 6; break;
        case "finsternis"       : result += (nachtblind) ? 8 : (daemmerungssicht || nachtsicht) ? 4 : 8; break;
        case "unsichtbares ziel": result += 8; break;
        }
        
        int entfernungMax = fernwaffe.getEntfernungByName(entfernung);        
        if(einaeugig) {
            if(entfernungMax <= 10) {
                result += 4;
            }
        }
        
        if(farbenblind) {
            if(entfernungMax >= 50) {
                result += 4;
            }
        }
        
        if(kurzsichtig) {
            if(entfernungMax >= 100) {
                result += 10;
            }
        }
        
        if(dunst) result += 2;
        if(nebel) result += 4;
        
        return result;
    }

    /**
     * @param modifier the modifier to apply
     * @param behinderung the current behinderung of the character
     * @param talent the talent to calculate the modifier for
     * @return the effective modifier
     */
    public static int calculateModifier(int modifier, int behinderung, TalentObjekt talent) {
        int result = 0;
        
        if(talent.getBe() != null) {
            String talentBehinderung = talent.getBe();
            if(talentBehinderung.startsWith("BE")) {
                if(talentBehinderung.trim().equalsIgnoreCase("BE")) {
                    result += behinderung;
                } else {
                    char operand = talentBehinderung.charAt(2);
                    
                    int length = talentBehinderung.length();
                    int modInt = Integer.parseInt(talentBehinderung.substring(length-1, length));
                    
                    switch(operand) {
                    case '+' : result = behinderung + modInt; break;
                    case '-' : result = behinderung - modInt; break;
                    case 'x' : result = behinderung * modInt; break;
                    case '/' : result = behinderung / modInt; break;
                    }
                    
                    if(result < 0) {
                        result = 0;
                    }
                }
            }
        }
        
        result += modifier;
        
        return result;
    }

    public static String getPatzerWurf() {
        String result = "(2w6) Patzer";
        
        return result;
    }
}
