/**
 * 
 */
package link.parzival.dsa.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import link.parzival.dsa.object.FernwaffenObjekt;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.KampftechnikObjekt;
import link.parzival.dsa.object.ParadeObjekt;
import link.parzival.dsa.object.Sonderfertigkeit;
import link.parzival.dsa.object.TalentObjekt;
import link.parzival.dsa.object.WaffenObjekt;
import link.parzival.dsa.object.enumeration.DKEnum;
import link.parzival.dsa.object.enumeration.EigenschaftEnum;
import link.parzival.dsa.object.enumeration.FernwaffenTypEnum;
import link.parzival.dsa.object.enumeration.ParadeObjektTypEnum;

/**
 * @author Mario Enrico Ragucci
 *
 */
public class XPathHtmlHeldenParser {
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @return the Behinderung
     * @throws XPathExpressionException when there was an issue
     */
    protected int gatherBehinderung(Document document, XPath xpath) throws XPathExpressionException {
        int result = 0;
        String countExpression     = "count((//table[@class='zonenruestungen gitternetz'])/tr)";
        String countResult         = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        
        String expression = "(//table[@class='zonenruestungen gitternetz'])/tr["+ count +"]/td[14]/text()";
        String be         = xpath.compile(expression).evaluate(document);
        
        if(be != null && !be.isEmpty()) {
            result = Integer.parseInt(be);
        }
        
        
        return result;
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param fernwaffenListe the List of fernwaffen
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever something else failed
     */
    protected void gatherFernwaffen(Document document, XPath xpath, List<FernwaffenObjekt> fernwaffenListe)
            throws XPathExpressionException, Exception {
        String countExpression      = "count((//table[@class='fkwaffen gitternetz'])/tr)";
        String countResult          = xpath.compile(countExpression).evaluate(document);
        int count                   = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression   = "(//table[@class='fkwaffen gitternetz'])/tr["+ i +"]/td[1]/text()";
            String typBeExpression  = "(//table[@class='fkwaffen gitternetz'])/tr["+ i +"]/td[2]/text()";
            String tpExpression     = "(//table[@class='fkwaffen gitternetz'])/tr["+ i +"]/td[3]/text()";
            String distExpression   = "(//table[@class='fkwaffen gitternetz'])/tr["+ i +"]/td[4]/text()";
            String tpDistExpression = "(//table[@class='fkwaffen gitternetz'])/tr["+ i +"]/td[5]/text()";
            String fkExpression     = "(//table[@class='fkwaffen gitternetz'])/tr["+ i +"]/td[6]/text()";
            
            String name             = xpath.compile(nameExpression).evaluate(document);
            String typBe            = xpath.compile(typBeExpression).evaluate(document);
            String tp               = xpath.compile(tpExpression).evaluate(document);
            String dist             = xpath.compile(distExpression).evaluate(document);
            String tpDist           = xpath.compile(tpDistExpression).evaluate(document);
            String fk               = xpath.compile(fkExpression).evaluate(document);
            
            if(name != null && !name.isEmpty()) {
                FernwaffenObjekt fernwaffenObjekt = new FernwaffenObjekt();
                fernwaffenObjekt.setName(name);
                fernwaffenObjekt.setTp(tp);
                fernwaffenObjekt.setFk(Integer.parseInt(fk));
                setFernwaffeTypeAndBehinderung(fernwaffenObjekt, typBe);
                setFernwaffeEntfernung(fernwaffenObjekt, dist);
                setFernwaffeTpEntfernung(fernwaffenObjekt, tpDist);
                
                fernwaffenListe.add(fernwaffenObjekt);
            }
        }
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param numberOfNet the number of the Gitternetz
     * @param talente the List of Talente
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever someting else failed
     */
    protected void gatherGaben(Document document, XPath xpath, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String countExpression     = "count((//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr)";
        String countResult         = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression  = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[1]/text()";
            String probeExpression = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[2]/text()";
            String tawExpression   = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[3]/text()";
            
            
            String name            = xpath.compile(nameExpression).evaluate(document);
            String taw             = xpath.compile(tawExpression).evaluate(document);
            String probe           = xpath.compile(probeExpression).evaluate(document);
            
            TalentObjekt talentObjekt = new TalentObjekt();
            talentObjekt.setName(name);
            talentObjekt.setTalentwert(Integer.parseInt(taw.trim()));
            splitTalentProben(talentObjekt, probe);
            
            talente.add(talentObjekt);
        }
    }

    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param numberOfNet the number of the Gitternetz
     * @param talente the List of talente
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever something else failed
     */
    protected void gatherGesellschaftlich(Document document, XPath xpath, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String countExpression      = "count((//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr)";
        String countResult          = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression   = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[1]/text()";
            String probeExpression  = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[2]/text()";
            String tawExpression    = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[3]/text()";
            
            
            String name             = xpath.compile(nameExpression).evaluate(document);
            String be               = "";
            String taw              = xpath.compile(tawExpression).evaluate(document);
            String probe            = xpath.compile(probeExpression).evaluate(document);
            
            TalentObjekt talent     = new TalentObjekt();
            talent.setName(name);
            talent.setBe(be);
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);
            
            talente.add(talent);
        }
    }

    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param numberOfNet the number of the Gitternetz
     * @param talente the List of talente
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever something else failed
     */
    protected void gatherHandwerk(Document document, XPath xpath, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String countExpression      = "count((//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr)";
        String countResult          = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression   = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[1]/text()";
            String probeExpression  = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[2]/text()";
            String tawExpression    = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[3]/text()";
            
            
            String name             = xpath.compile(nameExpression).evaluate(document);
            String be               = "";
            String taw              = xpath.compile(tawExpression).evaluate(document);
            String probe            = xpath.compile(probeExpression).evaluate(document);
            
            TalentObjekt talent     = new TalentObjekt();
            talent.setName(name);
            talent.setBe(be);
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);
            
            talente.add(talent);
        }
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param numberOfNet the number of the Gitternetz
     * @param kampftechniken the List of Kampftechniken
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever something else failed
     */
    protected void gatherKampftechniken(Document document, XPath xpath, int numberOfNet, List<KampftechnikObjekt> kampftechniken)
            throws XPathExpressionException, Exception {
        String countExpression      = "count((//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr)";
        String countResult          = xpath.compile(countExpression).evaluate(document);
        int count                   = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression   = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[1]/text()";
            String beExpression     = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[2]/text()";
            String typeExpression   = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[3]/text()";
            String atExpression     = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[4]/text()";
            String paExpression     = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[5]/text()";
            String tawExpression    = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[6]/text()";
            
            String name = xpath.compile(nameExpression).evaluate(document);
            String be   = xpath.compile(beExpression).evaluate(document);
            String type = xpath.compile(typeExpression).evaluate(document);
            String at   = xpath.compile(atExpression).evaluate(document);
            String pa   = xpath.compile(paExpression).evaluate(document);
            String taw  = xpath.compile(tawExpression).evaluate(document);
            
            KampftechnikObjekt kampftechnik = new KampftechnikObjekt();
            kampftechnik.setName(name);
            kampftechnik.setType(type);
            kampftechnik.setBe(be);
            kampftechnik.setAttacke(Integer.parseInt(at));
            kampftechnik.setParade(Integer.parseInt(pa));
            kampftechnik.setTalentwert(Integer.parseInt(taw));
            
            kampftechniken.add(kampftechnik);
        }
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param numberOfNet the number of the Gitternetz
     * @param talente the List of Talente
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever someting else failed
     */
    protected void gatherKoerperlich(Document document, XPath xpath, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String countExpression      = "count((//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr)";
        String countResult          = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression   = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[1]/text()";
            String probeExpression  = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[2]/text()";
            String beExpression     = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[3]/text()";
            String tawExpression    = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[4]/text()";
            
            
            String name             = xpath.compile(nameExpression).evaluate(document);
            String be               = xpath.compile(beExpression).evaluate(document);
            String taw              = xpath.compile(tawExpression).evaluate(document);
            String probe            = xpath.compile(probeExpression).evaluate(document);
            
            TalentObjekt talent     = new TalentObjekt();
            talent.setName(name);
            talent.setBe(be);
            talent.setTalentwert(Integer.parseInt(taw));
            try {
                splitTalentProben(talent, probe);
            } catch(Exception e) {
                System.err.println("Fehler bei Talent " + talent.getName());
                System.err.println(e.getMessage());
            }
            
            
            talente.add(talent);
        }
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param numberOfNet the number of the Gitternetz
     * @param talente the List of Talente
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever someting else failed
     */
    protected void gatherNaturtalente(Document document, XPath xpath, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String countExpression      = "count((//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr)";
        String countResult          = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression   = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[1]/text()";
            String probeExpression  = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[2]/text()";
            String tawExpression    = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[3]/text()";
            
            
            String name             = xpath.compile(nameExpression).evaluate(document);
            String be               = "";
            String taw              = xpath.compile(tawExpression).evaluate(document);
            String probe            = xpath.compile(probeExpression).evaluate(document);
            
            TalentObjekt talent     = new TalentObjekt();
            talent.setName(name);
            talent.setBe(be);
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);
            
            talente.add(talent);
        }
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param paradewaffenListe the List of paradewaffen
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever something else failed
     */
    protected void gatherParadeWaffen(Document document, XPath xpath, List<ParadeObjekt> paradewaffenListe)
            throws XPathExpressionException, Exception {
        String countExpression      = "count((//table[@class='schilde gitternetz'])/tr)";
        String countResult          = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression   = "(//table[@class='schilde gitternetz'])/tr["+ i +"]/td[1]/text()";
            String typExpression    = "(//table[@class='schilde gitternetz'])/tr["+ i +"]/td[2]/text()";
            String iniExpression    = "(//table[@class='schilde gitternetz'])/tr["+ i +"]/td[3]/text()";
            String wmExpression     = "(//table[@class='schilde gitternetz'])/tr["+ i +"]/td[4]/text()";
            String paExpression     = "(//table[@class='schilde gitternetz'])/tr["+ i +"]/td[5]/text()";
            String minBFExpression  = "(//table[@class='schilde gitternetz'])/tr["+ i +"]/td[6]/text()";
            String aktBFExpression  = "(//table[@class='schilde gitternetz'])/tr["+ i +"]/td[7]/text()";
            
            String name             = xpath.compile(nameExpression).evaluate(document);
            String typ              = xpath.compile(typExpression).evaluate(document);
            String ini              = xpath.compile(iniExpression).evaluate(document);
            String wm               = xpath.compile(wmExpression).evaluate(document);
            String pa               = xpath.compile(paExpression).evaluate(document);
            String minBf            = xpath.compile(minBFExpression).evaluate(document);
            String aktBf            = xpath.compile(aktBFExpression).evaluate(document);
            
            if(name != null && !name.isEmpty()) {
                ParadeObjekt paradeObjekt = new ParadeObjekt();
                paradeObjekt.setName(name);
                paradeObjekt.setTyp(ParadeObjektTypEnum.valueOf(typ));
                paradeObjekt.setIni(Integer.valueOf(ini));
                paradeObjekt.setParade(Integer.valueOf(pa));
                paradeObjekt.setMinBf(Integer.valueOf(minBf));
                paradeObjekt.setAktBf(Integer.valueOf(aktBf));
                parseWaffenModifikator(wm, paradeObjekt);
                
                paradewaffenListe.add(paradeObjekt);
            }
        }
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param numberOfNet the number of the Gitternetz
     * @param talente the List of Talente
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever someting else failed
     */
    protected void gatherSchriften(Document document, XPath xpath, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String countExpression        = "count((//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr)";
        String countResult            = xpath.compile(countExpression).evaluate(document);
        int count                     = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression     = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[1]/text()";
            String tawExpression      = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[3]/text()";          
            
            String name               = xpath.compile(nameExpression).evaluate(document);
            String be                 = "";
            String taw                = xpath.compile(tawExpression).evaluate(document);
            String probe              = " (--/--/--)";
            
            TalentObjekt talent       = new TalentObjekt();
            talent.setName(name);
            talent.setBe(be);
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);
            
            talente.add(talent);
        }
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param hero the HeldenObjekt to use
     * @throws XPathExpressionException when the XPath Expression threw an error
     * @throws Exception when there was another error
     */
    protected void gatherSonderfertigkeiten(Document document, XPath xpath, HeldenObjekt hero) throws XPathExpressionException, Exception {
        String countExpression        = "count((//div[@class='mitte_innen']/table[@class='sonderfertigkeiten'])/tr)";
        String countResult            = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        
        List<Sonderfertigkeit> sonderfertigkeiten = new ArrayList<>();
        
        for(int i = 2; i < count; i++) {
            String leftExpression     = "(//div[@class='mitte_innen']/table[@class='sonderfertigkeiten'])/tr["+i+"]/td[1]/text()";
            String rightExpression    = "(//div[@class='mitte_innen']/table[@class='sonderfertigkeiten'])/tr["+i+"]/td[2]/text()";
        
            String leftValue          = xpath.compile(leftExpression).evaluate(document);
            String rightValue         = xpath.compile(rightExpression).evaluate(document);
            
            Sonderfertigkeit sf1      = new Sonderfertigkeit(leftValue);
            Sonderfertigkeit sf2      = new Sonderfertigkeit(rightValue);
            
            sonderfertigkeiten.add(sf1);
            sonderfertigkeiten.add(sf2);
        }
        
        hero.setSonderfertigkeiten(sonderfertigkeiten);
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param numberOfNet the number of the Gitternetz
     * @param talente the List of Talente
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever someting else failed
     */
    protected void gatherSprachen(Document document, XPath xpath, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String countExpression         = "count((//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr)";
        String countResult             = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        for(int i = 2; i <= count; i++) {
            String nameExpression      = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[1]/text()";
            String tawExpression       = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[2]/text()";
            
            String name                = xpath.compile(nameExpression).evaluate(document);
            String be                  = "";
            String taw                 = xpath.compile(tawExpression).evaluate(document);
            String probe               = " (--/--/--)";
            
            TalentObjekt talent        = new TalentObjekt();
            talent.setName(name);
            talent.setBe(be);
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);

            talente.add(talent);
        }
    }
    
    /**
     * @param document The Document to be parsed
     * @param xpath the XPath to use
     * @param waffenListe the List to be filled
     * @throws XPathExpressionException
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever someting else failed
     */
    protected void gatherWaffen(Document document, XPath xpath, List<WaffenObjekt> waffenListe)
            throws XPathExpressionException, Exception {
        String countExpression     = "count((//table[@class='nkwaffen gitternetz'])/tr)";
        String countResult         = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression   = "(//table[@class='nkwaffen gitternetz'])/tr["+ i +"]/td[1]/text()";
            String dkExpression     = "(//table[@class='nkwaffen gitternetz'])/tr["+ i +"]/td[3]/text()";
            String iniExpression    = "(//table[@class='nkwaffen gitternetz'])/tr["+ i +"]/td[6]/text()";
            String atExpression     = "(//table[@class='nkwaffen gitternetz'])/tr["+ i +"]/td[8]/text()";
            String paExpression     = "(//table[@class='nkwaffen gitternetz'])/tr["+ i +"]/td[9]/text()";
            String tpExpression     = "(//table[@class='nkwaffen gitternetz'])/tr["+ i +"]/td[10]/text()";
            
            
            String name             = xpath.compile(nameExpression).evaluate(document);
            String dk               = xpath.compile(dkExpression).evaluate(document);
            String iniStr           = xpath.compile(iniExpression).evaluate(document);
            String atStr            = xpath.compile(atExpression).evaluate(document);
            String paStr            = xpath.compile(paExpression).evaluate(document);
            String tpStr            = xpath.compile(tpExpression).evaluate(document);
            
            if(name != null && !name.isEmpty()) {
                WaffenObjekt waffe  = new WaffenObjekt();
                waffe.setName(name);
                waffe.setDistanzklassen(parseDistanzklassen(dk));
                waffe.setTrefferpunkte(tpStr);
                waffe.setAttacke(Integer.parseInt(atStr));
                waffe.setParade(Integer.parseInt(paStr));
                waffe.setInitiative(Integer.parseInt(iniStr));
                
                waffenListe.add(waffe);
            }
        }
    }

    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param numberOfNet the number of the Gitternetz
     * @param talente the List of talente
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever something else failed
     */
    protected void gatherWissenstalente(Document document, XPath xpath, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String countExpression         = "count((//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr)";
        String countResult             = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression   = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[1]/text()";
            String probeExpression  = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[2]/text()";
            String tawExpression    = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]/tr["+ i +"]/td[3]/text()";          
            
            String name             = xpath.compile(nameExpression).evaluate(document);
            String be               = "";
            String taw              = xpath.compile(tawExpression).evaluate(document);
            String probe            = xpath.compile(probeExpression).evaluate(document);
            
            TalentObjekt talent = new TalentObjekt();
            talent.setName(name);
            talent.setBe(be);
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);
            
            talente.add(talent);
        }
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param zauberListe the List of zauber
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever something else failed
     */
    protected void gatherZauber(Document document, XPath xpath, List<TalentObjekt> zauberListe)
            throws XPathExpressionException, Exception {
        String countExpression      = "count((//table[@class='zauber gitternetz'])/tr)";
        String countResult          = xpath.compile(countExpression).evaluate(document);
        int count = Integer.parseInt(countResult);
        
        for(int i = 2; i <= count; i++) {
            String nameExpression   = "(//table[@class='zauber gitternetz'])/tr["+ i +"]/td[1]/text()";
            String probeExpression  = "(//table[@class='zauber gitternetz'])/tr["+ i +"]/td[2]/text()";
            String tawExpression    = "(//table[@class='zauber gitternetz'])/tr["+ i +"]/td[3]/text()";
            
            
            String name             = xpath.compile(nameExpression).evaluate(document);
            String be               = "";
            String taw              = xpath.compile(tawExpression).evaluate(document);
            String probe            = xpath.compile(probeExpression).evaluate(document);
            
            TalentObjekt zauber     = new TalentObjekt();
            zauber.setName(name);
            zauber.setBe(be);
            zauber.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(zauber, probe);
            
            zauberListe.add(zauber);
        }
    }
    
    /**
     * @param originalFile the originalFile
     * @return a modified file
     */
    protected File massageHtmlFile(File originalFile) {
        File result       = null;
        BufferedReader br = null;
        BufferedWriter wr = null;
        try {
            result = File.createTempFile("dsa_", ".tmp");
            result.deleteOnExit();
            
            br = new BufferedReader(new FileReader(originalFile));
            wr = new BufferedWriter(new FileWriter(result));
            String line = null;
            
            while((line = br.readLine()) != null) {
                String modLine = line
                        .replaceAll("&auml;", "ä")
                        .replaceAll("&ouml;", "ö")
                        .replaceAll("&uuml;", "ü")
                        .replaceAll("&Auml;", "Ä")
                        .replaceAll("&Ouml;", "Ö")
                        .replaceAll("&Uuml;", "Ü")
                        .replaceAll("&szlig;", "ss");
                
                wr.write(modLine);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if(wr != null) {
                try {
                    wr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        
        return result;
    }

    /**
     * @param dk the String to parse
     * @return a List of available Distanzklassen
     */
    protected List<DKEnum> parseDistanzklassen(String dk) {
        List<DKEnum> distanzklassen = new ArrayList<>();
        String parseString = dk.trim();
        for(String s : parseString.split(" ")) {
            distanzklassen.add(DKEnum.valueOf(s));
        }
        
        return distanzklassen;
    }

    /**
     * @param xmlFile the File to parse
     * @return a HeldenObjekt to use
     * @throws Exception when there was an error
     */
    public HeldenObjekt parseFile(File xmlFile) throws Exception {
        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder         = null;
        HeldenObjekt hero               = new HeldenObjekt();
        try {
            factory.setValidating(false);
            
            factory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            
            builder = factory.newDocumentBuilder();

            Document document = builder.parse(massageHtmlFile(xmlFile));
            
            XPath xpath            = XPathFactory.newInstance().newXPath();
            String titleExpression = "/html/head/title/text()";
            String heroName        = xpath.compile(titleExpression).evaluate(document);
            hero.setName(heroName);
            
            for(int i = 2; i < 10; i++) {
                String valueExpression = "//table[@class='eigenschaften gitternetz']/tr["+i+"]/td[4]/text()";
                String value = xpath.compile(valueExpression).evaluate(document);

                switch(i) {
                case 2: hero.setMut(Integer.parseInt(value));               break;
                case 3: hero.setKlugheit(Integer.parseInt(value));          break;
                case 4: hero.setIntuition(Integer.parseInt(value));         break;
                case 5: hero.setCharisma(Integer.parseInt(value));          break;
                case 6: hero.setFingerfertigkeit(Integer.parseInt(value));  break;
                case 7: hero.setGewandtheit(Integer.parseInt(value));       break;
                case 8: hero.setKonstitution(Integer.parseInt(value));      break;
                case 9: hero.setKoerperkraft(Integer.parseInt(value));      break;
                }
            }
            
            for(int i = 2; i < 11; i++) {
                String valueExpression = "//table[@class='basiswerte gitternetz']/tr["+i+"]/td[5]/text()";
                String value           = xpath.compile(valueExpression).evaluate(document);
                if(value.isEmpty()) {
                    value = "0";
                }

                switch(i) {
                case  2: hero.setLebensenergie(Integer.parseInt(value));    break;
                case  3: hero.setAusdauer(Integer.parseInt(value));         break;
                case  4: hero.setAstralenergie(Integer.parseInt(value));    break;
                case  5: hero.setKarmalenergie(Integer.parseInt(value));    break;
                case  6: hero.setMagieresistenz(Integer.parseInt(value));   break;
                case  7: hero.setBasisinitiative(Integer.parseInt(value));  break;
                case  8: hero.setBasisattacke(Integer.parseInt(value));     break;
                case  9: hero.setBasisparade(Integer.parseInt(value));      break;
                case 10: hero.setFernkampfbasis(Integer.parseInt(value));   break;
                }
            }
            
            List<TalentObjekt> talente = new ArrayList<>();
            List<KampftechnikObjekt> kampfTalente = new ArrayList<>();
            for(int i = 0; i < 10; i++) {
                String nameExpression     = "(//table[@class='talentgruppe gitternetz'])["+i+"]/tr[1]/th[1]/text()";
                String name               = xpath.compile(nameExpression).evaluate(document);
                
                if(name != null && !name.isEmpty()) {
                    switch(name.substring(0, 2).toLowerCase()) {
                        case "ga" : gatherGaben(document, xpath, i, talente);               break;
                        case "ge" : gatherGesellschaftlich(document, xpath, i, talente);    break;
                        case "ha" : gatherHandwerk(document, xpath, i, talente);            break;
                        case "ka" : gatherKampftechniken(document, xpath, i, kampfTalente); break;
                        case "kö" : gatherKoerperlich(document, xpath, i, talente);         break;
                        case "na" : gatherNaturtalente(document, xpath, i, talente);        break;
                        case "sc" : gatherSchriften(document, xpath, i, talente);           break;        
                        case "sp" : gatherSprachen(document, xpath, i, talente);            break;    
                        case "wi" : gatherWissenstalente(document, xpath, i, talente);      break;            
                        default: break;
                    }
                }
            }
                    
            hero.setTalente(talente);
            hero.setKampftechniken(kampfTalente);
            
            List<TalentObjekt> zauber = new ArrayList<>();
            gatherZauber(document, xpath, zauber);
            
            hero.setZauber(zauber);
            
            List<WaffenObjekt> waffenListe = new ArrayList<>();
            gatherWaffen(document, xpath, waffenListe);            
            hero.setWaffen(waffenListe);
            
            List<ParadeObjekt> paradeObjektListe = new ArrayList<>();
            gatherParadeWaffen(document, xpath, paradeObjektListe);            
            hero.setParadeWaffen(paradeObjektListe);
            
            List<FernwaffenObjekt> fernwaffenListe = new ArrayList<>();
            gatherFernwaffen(document, xpath, fernwaffenListe);
            hero.setFernWaffen(fernwaffenListe);
            
            int behinderung = gatherBehinderung(document, xpath);
            hero.setBehinderung(behinderung);
            
            gatherSonderfertigkeiten(document, xpath, hero);
            
            
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        
        return hero;
    }

    /**
     * @param wm the WeaponModificator to parse
     * @param paradeObjekt the ParadeObjekt to use
     */
    protected void parseWaffenModifikator(String wm, ParadeObjekt paradeObjekt) {
        int waffenModifikatorAttacke = 0;
        int waffenModifikatorParade  = 0;
        
        String[] split = wm.split("/");
        waffenModifikatorAttacke = Integer.valueOf(split[0].trim());
        waffenModifikatorParade  = Integer.valueOf(split[1].trim());
        
        paradeObjekt.setWaffenModifikatorAttacke(waffenModifikatorAttacke);
        paradeObjekt.setWaffenModifikatorParade(waffenModifikatorParade);        
    }

    /**
     * @param fernwaffenObjekt the FernwaffenObjekt to use
     * @param dist the Distances of the Weapon
     */
    protected void setFernwaffeEntfernung(FernwaffenObjekt fernwaffenObjekt, String dist) {
        String[] distances = dist.split("/");
        for(String distance : distances) {
            fernwaffenObjekt.addEntfernung(Integer.parseInt(distance.trim()));
        }
         
    }
    
    /**
     * @param fernwaffenObjekt the FernwaffenObjekt
     * @param tpDist the tps according to distances
     */
    protected void setFernwaffeTpEntfernung(FernwaffenObjekt fernwaffenObjekt, String tpDist) {
        String[] distances = tpDist.split("/");
        for(String distance : distances) {
            fernwaffenObjekt.addTpEntfernung(Integer.parseInt(distance.trim()));
        }
        
    }
    
    /**
     * @param fernwaffenObjekt the Fernwaffenobjekt to set
     * @param typBe the Type and Be in a String
     */
    protected void setFernwaffeTypeAndBehinderung(FernwaffenObjekt fernwaffenObjekt, String typBe) {
        String typ = null;
        String be  = null;
        
        if(typBe != null && typBe.contains("/")) {
            String[] parts = typBe.split("/");
            typ = parts[0].trim();
            be  = parts[1].trim();
        }
                
        if(typ != null && !typ.isEmpty()) {
            FernwaffenTypEnum typEnum = FernwaffenTypEnum.valueOf(typ);
            fernwaffenObjekt.setTyp(typEnum);    
        }
        
        if(be != null && !be.isEmpty()) {
            fernwaffenObjekt.setBe(be);
        }
    }
    
    /**
     * @param talentObjekt the TalentObjekt to use
     * @param probe the probe to split
     * @throws Exception when there was an issue
     */
    protected void splitTalentProben(TalentObjekt talentObjekt, String probe) throws Exception {
        String localprobe = probe.trim();
        localprobe        = localprobe.replaceAll("--", "NA").replaceAll("\\*\\*", "NA");
        
        String regex      = "[A-Z]{2}";
        
        Pattern p         = Pattern.compile(regex);
        Matcher matcher   = p.matcher(localprobe);
        
        List<EigenschaftEnum> probenList = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            if(matcher.find()) {
                String s = matcher.group();
                probenList.add(EigenschaftEnum.valueOf(s));
            } else {
                System.err.println("Fehler in Probe: " + probe);
                System.err.println(localprobe);
                throw new Exception("Could not match Probe!");
            }
        }
        
        talentObjekt.setProbenTalent1(probenList.get(0));
        talentObjekt.setProbenTalent2(probenList.get(1));
        talentObjekt.setProbenTalent3(probenList.get(2));
    }

}
