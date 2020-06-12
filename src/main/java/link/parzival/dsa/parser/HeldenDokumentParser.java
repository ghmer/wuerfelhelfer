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
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
public class HeldenDokumentParser {
    protected DocumentBuilder builder;
    protected XPath xpath;
    
    public static final Logger _LOG = Logger.getLogger(HeldenDokumentParser.class.getName());
    
    /**
     * Konstruktor
     */
    public HeldenDokumentParser() {
        initialisieren();
    }
    
    /**
     * Initialisiert die Document- und XPath Instanzen
     */
    private void initialisieren() {
        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
        try {
            Instant initializationStart = Instant.now();
            factory.setValidating(false);
            factory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);           
            this.builder = factory.newDocumentBuilder();           
            this.xpath   = XPathFactory.newInstance().newXPath();
            Instant initializationEnd = Instant.now();
            if(_LOG.isLoggable(Level.FINE)) {
                _LOG.fine("Initialization: " + Duration.between(initializationStart, initializationEnd));
        	}
            
        } catch(Exception e) {
            _LOG.severe(e.getMessage());
        }
    }
    
    /**
     * Parsed das Dokument auf Basiswerte:
     * Lebensenergie, Ausdauer, Astralenergie, Karmalenergie, Magieresistenz, BasisInitiative, BasisAttacke, BasisParade, Fernkampfbasis
     * @param heldenDokument das HeldenDokument
     * @param heldenObjekt das HeldenObjekt
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeBasisWerte(Document heldenDokument, HeldenObjekt heldenObjekt)
            throws Exception {
        String nodeExpression      = "(//table[@class='basiswerte gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        
        NodeList nList = newDocument.getElementsByTagName("tr");
        
        
        for(int i = 1; i < nList.getLength(); i++) {
           
            Node lnode = nList.item(i);
            
            Node valueNode = lnode.getChildNodes().item(9);
            
            String parsedVal = valueNode.getTextContent().trim();
            int value = 0;
            if(parsedVal != null && !parsedVal.isEmpty()) {
                value = Integer.parseInt(parsedVal);
            }
            
            switch(i) {
                case  1: heldenObjekt.setLebensenergie(value);    break;
                case  2: heldenObjekt.setAusdauer(value);         break;
                case  3: heldenObjekt.setAstralenergie(value);    break;
                case  4: heldenObjekt.setKarmalenergie(value);    break;
                case  5: heldenObjekt.setMagieresistenz(value);   break;
                case  6: heldenObjekt.setBasisinitiative(value);  break;
                case  7: heldenObjekt.setBasisattacke(value);     break;
                case  8: heldenObjekt.setBasisparade(value);      break;
                case  9: heldenObjekt.setFernkampfbasis(value);   break;
            }
            
        }
    }
    
    /**
     * @param heldenDokument das zu Parsende Dokument
     * @return die Behinderung
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected int holeBehinderung(Document heldenDokument) throws Exception {
        int result = 0;
        
        String nodeExpression      = "(//table[@class='zonenruestungen gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+14) {
            String name = nList.item(i).getTextContent().trim();
            String be   = nList.item(i+13).getTextContent().trim();
            
            if(name != null && !name.isEmpty() && name.equalsIgnoreCase("gesamt")) {
                if(be != null && !be.isEmpty()) {
                    result = Integer.parseInt(be);
                }
            }                
        }
        
        return result;
    }

    /**
     * @param heldenDokument das HeldenDokument
     * @param heldenObjekt das HeldenObjekt
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeEigenschaften(Document heldenDokument, HeldenObjekt heldenObjekt)
            throws Exception {
        String nodeExpression      = "(//table[@class='eigenschaften gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        
        NodeList nList = newDocument.getElementsByTagName("tr");
        
        for(int i = 1; i < nList.getLength(); i++) {
            Node lnode = nList.item(i);
            Node valueNode = lnode.getChildNodes().item(7);
            
            String value = valueNode.getTextContent().trim();
            
            switch(i) {
                case 1: heldenObjekt.setMut(Integer.parseInt(value));               break;
                case 2: heldenObjekt.setKlugheit(Integer.parseInt(value));          break;
                case 3: heldenObjekt.setIntuition(Integer.parseInt(value));         break;
                case 4: heldenObjekt.setCharisma(Integer.parseInt(value));          break;
                case 5: heldenObjekt.setFingerfertigkeit(Integer.parseInt(value));  break;
                case 6: heldenObjekt.setGewandtheit(Integer.parseInt(value));       break;
                case 7: heldenObjekt.setKonstitution(Integer.parseInt(value));      break;
                case 8: heldenObjekt.setKoerperkraft(Integer.parseInt(value));      break;
            }
            
        }
    }
    
    /**
     * @param heldenDokument das HeldenDokument
     * @param fernwaffenListe die Liste der Fernwaffen
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeFernwaffen(Document heldenDokument, List<FernwaffenObjekt> fernwaffenListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='fkwaffen gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+7) {
            String name             = nList.item(i).getTextContent().trim();
            String typBe            = nList.item(i+1).getTextContent().trim();
            String tp               = nList.item(i+2).getTextContent().trim();
            String dist             = nList.item(i+3).getTextContent().trim();
            String tpDist           = nList.item(i+4).getTextContent().trim();
            String fk               = nList.item(i+5).getTextContent().trim();
            
            if(name != null && !name.isEmpty()) {
                FernwaffenObjekt fernwaffenObjekt = new FernwaffenObjekt();
                fernwaffenObjekt.setName(name);
                fernwaffenObjekt.setTp(tp);
                fernwaffenObjekt.setFk(Integer.parseInt(fk));
                setzeFernwaffenTypUndBehinderung(fernwaffenObjekt, typBe);
                setzeFernwaffenEntfernung(fernwaffenObjekt, dist);
                setzeFernwaffenTrefferpunkte(fernwaffenObjekt, tpDist);
                
                fernwaffenListe.add(fernwaffenObjekt);
            }                
        }
        
    }

    /**
     * @param heldenDokument das Dokument zum Parsen
     * @param gitternetzNummer die Nummer des zu parsenden Gitternetzes im Dokument
     * @param talentListe die Liste der Talente
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeGaben(Document heldenDokument, int gitternetzNummer, List<TalentObjekt> talentListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+gitternetzNummer+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+3) {
            
            String name     = nList.item(i).getTextContent().trim();
            String probe    = nList.item(i+1).getTextContent().trim();
            String taw      = nList.item(i+2).getTextContent().trim();
 
            TalentObjekt talent = new TalentObjekt();
            talent.setName(name);
            talent.setBe("");
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);
            
            talentListe.add(talent);                  
        }
    }
    
    /**
     * @param heldenDokument das Dokument zum Parsen
     * @param gitternetzNummer die Nummer des zu parsenden Gitternetzes im Dokument
     * @param talentListe die Liste der Talente
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeGesellschaftlich(Document heldenDokument, int gitternetzNummer, List<TalentObjekt> talentListe)
            throws Exception {       
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+gitternetzNummer+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+3) {
            
            String name     = nList.item(i).getTextContent().trim();
            String probe    = nList.item(i+1).getTextContent().trim();
            String taw      = nList.item(i+2).getTextContent().trim();
 
            TalentObjekt talent = new TalentObjekt();
            talent.setName(name);
            talent.setBe("");
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);
            
            talentListe.add(talent);                  
        }
    }
    
    /**
     * @param heldenDokument das Dokument zum Parsen
     * @param gitternetzNummer die Nummer des zu parsenden Gitternetzes im Dokument
     * @param talentListe die Liste der Talente
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeHandwerk(Document heldenDokument, int gitternetzNummer, List<TalentObjekt> talentListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+gitternetzNummer+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+3) {
            
            String name     = nList.item(i).getTextContent().trim();
            String probe    = nList.item(i+1).getTextContent().trim();
            String taw      = nList.item(i+2).getTextContent().trim();
 
            TalentObjekt talent = new TalentObjekt();
            talent.setName(name);
            talent.setBe("");
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);
            
            talentListe.add(talent);                  
        }
    }
    
    /**
     * @param heldenDokument das Dokument zum Parsen
     * @param gitternetzNummer die Nummer des zu parsenden Gitternetzes im Dokument
     * @param kampftechnikListe die Liste der Kampftechniken
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeKampftechniken(Document heldenDokument, int gitternetzNummer, List<KampftechnikObjekt> kampftechnikListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+gitternetzNummer+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        Document newDocument = nodeToDocument(node);
        
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+6) {            
            String name = nList.item(i).getTextContent().trim();
            String be   = nList.item(i+1).getTextContent().trim();
            String type = nList.item(i+2).getTextContent().trim();
            String at   = nList.item(i+3).getTextContent().trim();
            String pa   = nList.item(i+4).getTextContent().trim();
            String taw  = nList.item(i+5).getTextContent().trim();
            
            KampftechnikObjekt kampftechnik = new KampftechnikObjekt();
            kampftechnik.setName(name);
            kampftechnik.setType(type);
            kampftechnik.setBe(be);
            kampftechnik.setAttacke(Integer.parseInt(at));
            kampftechnik.setParade(Integer.parseInt(pa));
            kampftechnik.setTalentwert(Integer.parseInt(taw));
            
            kampftechnikListe.add(kampftechnik);                 
        }
        
    }
       
    /**
     * @param heldenDokument das Dokument zum Parsen
     * @param gitternetzNummer die Nummer des zu parsenden Gitternetzes im Dokument
     * @param talentListe die Liste der Talente
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeKoerperlich(Document heldenDokument, int gitternetzNummer, List<TalentObjekt> talentListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+gitternetzNummer+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+4) {
            
            String name     = nList.item(i).getTextContent().trim();
            String probe    = nList.item(i+1).getTextContent().trim();
            String be       = nList.item(i+2).getTextContent().trim();
            String taw      = nList.item(i+3).getTextContent().trim();
 
            TalentObjekt talent = new TalentObjekt();
            talent.setName(name);
            talent.setBe(be);
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);
            
            talentListe.add(talent);                  
        }
    }
    
    /**
     * @param heldenDokument das Dokument zum Parsen
     * @param gitternetzNummer die Nummer des zu parsenden Gitternetzes im Dokument
     * @param talentListe die Liste der Talente
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeNaturtalente(Document heldenDokument, int gitternetzNummer, List<TalentObjekt> talentListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+gitternetzNummer+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+3) {
            
            String name     = nList.item(i).getTextContent().trim();
            String probe    = nList.item(i+1).getTextContent().trim();
            String taw      = nList.item(i+2).getTextContent().trim();
 
            TalentObjekt talent = new TalentObjekt();
            talent.setName(name);
            talent.setBe("");
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);
            
            talentListe.add(talent);                  
        }
    }
    
    /**
     * @param heldenDokument the Document to parse
     * @param paradewaffenListe die Liste der ParadeObjekte
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeParadeWaffen(Document heldenDokument, List<ParadeObjekt> paradewaffenListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='schilde gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+7) {
            String name             = nList.item(i).getTextContent().trim();
            String typ              = nList.item(i+1).getTextContent().trim();
            String ini              = nList.item(i+2).getTextContent().trim();
            String wm               = nList.item(i+3).getTextContent().trim();
            String pa               = nList.item(i+4).getTextContent().trim();
            String minBf            = nList.item(i+5).getTextContent().trim();
            String aktBf            = nList.item(i+6).getTextContent().trim();
            
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
     * @param heldenDokument das Dokument zum Parsen
     * @param gitternetzNummer die Nummer des zu parsenden Gitternetzes im Dokument
     * @param talentListe die Liste der Talente
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeSchriften(Document heldenDokument, int gitternetzNummer, List<TalentObjekt> talentListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+gitternetzNummer+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+3) {
            
            String name     = nList.item(i).getTextContent().trim();
            String taw      = nList.item(i+2).getTextContent().trim();
 
            TalentObjekt talent = new TalentObjekt();
            talent.setName(name);
            talent.setBe("");
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, "(--/--/--)");
            
            talentListe.add(talent);                  
        }
    }
    
    /**
     * @param heldenDokument das zu parsende Dokument
     * @param heldenObjekt das HeldenObjekt
     * @throws Exception wenn etwas schief lief
     */
    protected void holeSonderfertigkeiten(Document heldenDokument, HeldenObjekt heldenObjekt) throws Exception {
        String nodeExpression      = "(//div[@class='mitte_innen']/table[@class='sonderfertigkeiten'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);

        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        List<Sonderfertigkeit> sonderfertigkeiten = new ArrayList<>();
        
        for(int i = 0; i < nList.getLength(); i=i+2) {
            
            String leftValue        = nList.item(i).getTextContent().trim();
            String rightValue       = nList.item(i+1).getTextContent().trim();
            
            Sonderfertigkeit sf1    = new Sonderfertigkeit(leftValue);
            Sonderfertigkeit sf2    = new Sonderfertigkeit(rightValue);
            
            sonderfertigkeiten.add(sf1);
            sonderfertigkeiten.add(sf2);
        }
        
        heldenObjekt.setSonderfertigkeiten(sonderfertigkeiten);
    }
    
    /**
     * @param heldenDokument das Dokument zum Parsen
     * @param gitternetzNummer die Nummer des zu parsenden Gitternetzes im Dokument
     * @param talentListe die Liste der Talente
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeSprachen(Document heldenDokument, int gitternetzNummer, List<TalentObjekt> talentListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+gitternetzNummer+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+3) {
            
            String name     = nList.item(i).getTextContent().trim();
            String taw      = nList.item(i+2).getTextContent().trim();
 
            TalentObjekt talent = new TalentObjekt();
            talent.setName(name);
            talent.setBe("");
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, "(--/--/--)");
            
            talentListe.add(talent);                  
        }
    }

    /**
     * @param heldenDokument das zu parsende Dokument
     * @param waffenListe die Liste der WaffenObjekte
     * @throws Exception wenn etwas schief lief
     */
    protected void holeWaffen(Document heldenDokument, List<WaffenObjekt> waffenListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='nkwaffen gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+12) {
            String name             = nList.item(i).getTextContent().trim();
            String dk               = nList.item(i+2).getTextContent().trim();
            String iniStr           = nList.item(i+5).getTextContent().trim();
            String atStr            = nList.item(i+7).getTextContent().trim();
            String paStr            = nList.item(i+8).getTextContent().trim();
            String tpStr            = nList.item(i+9).getTextContent().trim();
            

            
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
     * @param heldenDokument das Dokument zum Parsen
     * @param gitternetzNummer die Nummer des zu parsenden Gitternetzes im Dokument
     * @param talentListe die Liste der Talente
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeWissenstalente(Document heldenDokument, int gitternetzNummer, List<TalentObjekt> talentListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+gitternetzNummer+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+3) {
            
            String name     = nList.item(i).getTextContent().trim();
            String probe    = nList.item(i+1).getTextContent().trim();
            String taw      = nList.item(i+2).getTextContent().trim();
 
            TalentObjekt talent = new TalentObjekt();
            talent.setName(name);
            talent.setBe("");
            talent.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(talent, probe);
            
            talentListe.add(talent);                  
        }
    }

    /**
     * @param heldenDokument das Dokument zum Parsen
     * @param zauberListe die Liste der Zauber
     * @throws Exception wenn beim Parsen etwas schief lief
     */
    protected void holeZauber(Document heldenDokument, List<TalentObjekt> zauberListe)
            throws Exception {
        String nodeExpression      = "(//table[@class='zauber gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(heldenDokument, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        NodeList nList = newDocument.getElementsByTagName("td");
        
        for(int i = 0; i < nList.getLength(); i=i+8) {
            
            String name     = nList.item(i).getTextContent().trim();
            String probe    = nList.item(i+1).getTextContent().trim();
            String taw      = nList.item(i+2).getTextContent().trim();
 
            TalentObjekt zauber     = new TalentObjekt();
            zauber.setName(name);
            zauber.setBe("");
            zauber.setTalentwert(Integer.parseInt(taw));
            splitTalentProben(zauber, probe);
            
            zauberListe.add(zauber);      
        }
    }

    /**
     * @param originalDateiObjekt die Originaldatei
     * @return eine modifizierte Datei
     */
    protected File massageHtmlFile(File originalDateiObjekt) {
        File result       = null;
        BufferedReader br = null;
        BufferedWriter wr = null;
        try {
            result = File.createTempFile("dsa_", ".tmp");
            result.deleteOnExit();
            
            br = new BufferedReader(new FileReader(originalDateiObjekt));
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
            _LOG.severe(e.getMessage());
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                	_LOG.severe(e.getMessage());
                }
            }
            
            if(wr != null) {
                try {
                    wr.close();
                } catch (IOException e) {
                	_LOG.severe(e.getMessage());
                }
            }
        }
        
        
        return result;
    }
    
    
    /**
     * @param node die Node, die zu einem Document transformiert werden soll
     * @return ein Document Objekt
     * @throws Exception wenn etwas schief lief
     */
    protected Document nodeToDocument(Node node) throws Exception {
        Document newDocument = builder.newDocument();
        
        if(node != null) {
          //node = node.getParentNode().removeChild(node);
            node = node.cloneNode(true);
            
            Node importedNode = newDocument.importNode(node, true);
            newDocument.appendChild(importedNode);
        }
        
        return newDocument;
    }
    

    /**
     * @param distanzklasse der zu parsende String
     * @return die Liste der geparsten Distanzklassen
     */
    protected List<DKEnum> parseDistanzklassen(String distanzklasse) {
        List<DKEnum> distanzklassen = new ArrayList<>();
        String parseString = distanzklasse.trim();
        for(String s : parseString.split(" ")) {
            distanzklassen.add(DKEnum.valueOf(s));
        }
        
        return distanzklassen;
    }

    /**
     * @param xmlFile die Datei, die geparsed werden soll
     * @return ein HeldenObjekt
     * @throws Exception wenn etwas schief lief
     */
    public HeldenObjekt parseFile(File xmlFile) throws Exception {
        HeldenObjekt hero               = new HeldenObjekt();
        try {
            Instant initializationStart = Instant.now();
            Document document           = builder.parse(massageHtmlFile(xmlFile));
            Instant initializationEnd   = Instant.now();
            
            if(_LOG.isLoggable(Level.FINE)) {
        		_LOG.fine("Parsing: " + Duration.between(initializationStart, initializationEnd));
        	}
            
            Instant eigenschaftenStart 	= Instant.now();
            String titleExpression 		= "/html/head/title/text()";
            String heroName        		= xpath.compile(titleExpression).evaluate(document);
            hero.setName(heroName);            
            holeEigenschaften(document, hero);         
            holeBasisWerte(document, hero);
            Instant eigenschaftenEnd 	= Instant.now();
            
            if(_LOG.isLoggable(Level.FINE)) {
        		 _LOG.fine("Eigenschaften: " + Duration.between(eigenschaftenStart, eigenschaftenEnd));
        	}
           
            List<TalentObjekt> talente = new ArrayList<>();
            List<KampftechnikObjekt> kampfTalente = new ArrayList<>();
            for(int i = 0; i < 10; i++) {
                String nameExpression     = "(//table[@class='talentgruppe gitternetz'])["+i+"]/tr[1]/th[1]/text()";
                String name               = xpath.compile(nameExpression).evaluate(document);
                if(name != null && !name.isEmpty()) {
                    Instant gatherStart = Instant.now();
                    
                    switch(name.substring(0, 2).toLowerCase()) {
                        case "ga" : holeGaben(document, i, talente);               break;
                        case "ge" : holeGesellschaftlich(document, i, talente);    break;
                        case "ha" : holeHandwerk(document, i, talente);            break;
                        case "ka" : holeKampftechniken(document, i, kampfTalente); break;
                        case "kö" : holeKoerperlich(document, i, talente);         break;
                        case "na" : holeNaturtalente(document, i, talente);        break;
                        case "sc" : holeSchriften(document, i, talente);           break;        
                        case "sp" : holeSprachen(document, i, talente);            break;    
                        case "wi" : holeWissenstalente(document, i, talente);      break;            
                        default: break;
                    }
                    
                    Instant gatherEnd = Instant.now();
                    if(_LOG.isLoggable(Level.FINE)) {
                		_LOG.fine(String.format("%s: %s", name, Duration.between(gatherStart, gatherEnd)));
                	}    
                }               
            }
                    
            hero.setTalente(talente);
            hero.setKampftechniken(kampfTalente);
            
            Instant gatherZauberStart = Instant.now();
            List<TalentObjekt> zauber = new ArrayList<>();
            holeZauber(document, zauber);            
            hero.setZauber(zauber);
            Instant gatherZauberEnd = Instant.now();
            
            if(_LOG.isLoggable(Level.FINE)) {
        		_LOG.fine(String.format("Zauber: %s", Duration.between(gatherZauberStart, gatherZauberEnd)));
            
        	}

            Instant gatherWaffenStart = Instant.now();
            List<WaffenObjekt> waffenListe = new ArrayList<>();
            holeWaffen(document, waffenListe);            
            hero.setWaffen(waffenListe);            
            Instant gatherWaffenEnd = Instant.now();
            
            if(_LOG.isLoggable(Level.FINE)) {
        		_LOG.fine(String.format("Waffen: %s", Duration.between(gatherWaffenStart, gatherWaffenEnd)));
        	}
            
            Instant gatherPWaffenStart = Instant.now();
            List<ParadeObjekt> paradeObjektListe = new ArrayList<>();
            holeParadeWaffen(document, paradeObjektListe);            
            hero.setParadeWaffen(paradeObjektListe);
            Instant gatherPWaffenEnd = Instant.now();
            
            if(_LOG.isLoggable(Level.FINE)) {
        		_LOG.fine(String.format("Parade: %s", Duration.between(gatherPWaffenStart, gatherPWaffenEnd)));
        	}
            
            
            Instant gatherFWaffenStart = Instant.now();
            List<FernwaffenObjekt> fernwaffenListe = new ArrayList<>();
            holeFernwaffen(document, fernwaffenListe);
            hero.setFernWaffen(fernwaffenListe);
            Instant gatherFWaffenEnd = Instant.now();
            
            if(_LOG.isLoggable(Level.FINE)) {
        		_LOG.fine(String.format("Fernwaffen: %s", Duration.between(gatherFWaffenStart, gatherFWaffenEnd)));
        	}
            
            int behinderung = holeBehinderung(document);
            hero.setBehinderung(behinderung);
            
            Instant gatherSonderfertigkeitenStart = Instant.now();
            holeSonderfertigkeiten(document, hero);
            Instant gatherSonderfertigkeitenEnd = Instant.now();
            
            if(_LOG.isLoggable(Level.FINE)) {
        		_LOG.fine(String.format("SF: %s", Duration.between(gatherSonderfertigkeitenStart, gatherSonderfertigkeitenEnd)));
        	}
            
        } catch (ParserConfigurationException | SAXException | IOException e) {
            _LOG.severe(e.getMessage());
        } catch (XPathExpressionException e) {
        	_LOG.severe(e.getMessage());
        }
        
        return hero;
    }
    
    /**
     * @param wm der zu parsende Waffenmodifikator
     * @param paradeObjekt das ParadeObjekt
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
     * @param fernwaffenObjekt das zu verwendende FernwaffenObjekt
     * @param dist die Distanzen der Fernwaffe
     */
    protected void setzeFernwaffenEntfernung(FernwaffenObjekt fernwaffenObjekt, String dist) {
        String[] distances = dist.split("/");
        for(String distance : distances) {
            fernwaffenObjekt.addEntfernung(Integer.parseInt(distance.trim()));
        }        
    }
    
    /**
     * @param fernwaffenObjekt das FernwaffenObjekt
     * @param tpDist Die Trefferpunkte pro Distanz
     */
    protected void setzeFernwaffenTrefferpunkte(FernwaffenObjekt fernwaffenObjekt, String tpDist) {
        String[] distances = tpDist.split("/");
        for(String distance : distances) {
            fernwaffenObjekt.addTpEntfernung(Integer.parseInt(distance.trim()));
        }        
    }
    
    
    /**
     * @param fernwaffenObjekt das FernwaffenObjekt
     * @param typBe Typ und Behinderung
     */
    protected void setzeFernwaffenTypUndBehinderung(FernwaffenObjekt fernwaffenObjekt, String typBe) {
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
     * @param talentObjekt das TalentObjekt
     * @param probe die zu splittende Probe
     * @throws Exception wenn etwas schief lief
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
                _LOG.severe("Fehler in Probe: " + probe);
                _LOG.severe("Lokale Variable: " + localprobe);
                throw new Exception("Could not match Probe!");
            }
        }
        
        talentObjekt.setProbenTalent1(probenList.get(0));
        talentObjekt.setProbenTalent2(probenList.get(1));
        talentObjekt.setProbenTalent3(probenList.get(2));
    }
}
