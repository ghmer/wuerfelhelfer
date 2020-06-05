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
    
    public HeldenDokumentParser() {
        initialize();
    }
    
    private void initialize() {
        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
        try {
            Instant initializationStart = Instant.now();
            factory.setValidating(false);
            factory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);           
            this.builder = factory.newDocumentBuilder();           
            this. xpath            = XPathFactory.newInstance().newXPath();
            Instant initializationEnd = Instant.now();
            System.out.println("Initialization: " + Duration.between(initializationStart, initializationEnd));
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    protected void gatherBasisWerte(HeldenObjekt hero, Document document)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='basiswerte gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
                case  1: hero.setLebensenergie(value);    break;
                case  2: hero.setAusdauer(value);         break;
                case  3: hero.setAstralenergie(value);    break;
                case  4: hero.setKarmalenergie(value);    break;
                case  5: hero.setMagieresistenz(value);   break;
                case  6: hero.setBasisinitiative(value);  break;
                case  7: hero.setBasisattacke(value);     break;
                case  8: hero.setBasisparade(value);      break;
                case  9: hero.setFernkampfbasis(value);   break;
            }
            
        }
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @return the Behinderung
     * @throws XPathExpressionException when there was an issue
     */
    protected int gatherBehinderung(Document document) throws Exception {
        int result = 0;
        
        String nodeExpression      = "(//table[@class='zonenruestungen gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
     * @param hero
     * @param document
     * @param xpath
     * @throws XPathExpressionException
     * @throws Exception
     */
    protected void gatherEigenschaften(HeldenObjekt hero, Document document)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='eigenschaften gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
        Document newDocument = nodeToDocument(node);
        
        NodeList nList = newDocument.getElementsByTagName("tr");
        
        for(int i = 1; i < nList.getLength(); i++) {
            Node lnode = nList.item(i);
            Node valueNode = lnode.getChildNodes().item(7);
            
            String value = valueNode.getTextContent().trim();
            
            switch(i) {
                case 1: hero.setMut(Integer.parseInt(value));               break;
                case 2: hero.setKlugheit(Integer.parseInt(value));          break;
                case 3: hero.setIntuition(Integer.parseInt(value));         break;
                case 4: hero.setCharisma(Integer.parseInt(value));          break;
                case 5: hero.setFingerfertigkeit(Integer.parseInt(value));  break;
                case 6: hero.setGewandtheit(Integer.parseInt(value));       break;
                case 7: hero.setKonstitution(Integer.parseInt(value));      break;
                case 8: hero.setKoerperkraft(Integer.parseInt(value));      break;
            }
            
        }
    }
    
    /**
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param fernwaffenListe the List of fernwaffen
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever something else failed
     */
    protected void gatherFernwaffen(Document document, List<FernwaffenObjekt> fernwaffenListe)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='fkwaffen gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
    protected void gatherGaben(Document document, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
    protected void gatherGesellschaftlich(Document document, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {       
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
    protected void gatherHandwerk(Document document, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
    protected void gatherKampftechniken(Document document, int numberOfNet, List<KampftechnikObjekt> kampftechniken)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
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
    protected void gatherKoerperlich(Document document, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
    protected void gatherNaturtalente(Document document, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
    protected void gatherParadeWaffen(Document document, List<ParadeObjekt> paradewaffenListe)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='schilde gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param numberOfNet the number of the Gitternetz
     * @param talente the List of Talente
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever someting else failed
     */
    protected void gatherSchriften(Document document, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
    protected void gatherSonderfertigkeiten(Document document, HeldenObjekt hero) throws XPathExpressionException, Exception {
        String nodeExpression      = "(//div[@class='mitte_innen']/table[@class='sonderfertigkeiten'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);

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
    protected void gatherSprachen(Document document, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
    protected void gatherWaffen(Document document, List<WaffenObjekt> waffenListe)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='nkwaffen gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
     * @param document the Document to parse
     * @param xpath the XPath to use
     * @param numberOfNet the number of the Gitternetz
     * @param talente the List of talente
     * @throws XPathExpressionException when the Expression threw an error
     * @throws Exception whenever something else failed
     */
    protected void gatherWissenstalente(Document document, int numberOfNet, List<TalentObjekt> talente)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='talentgruppe gitternetz'])["+numberOfNet+"]";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
    protected void gatherZauber(Document document, List<TalentObjekt> zauberListe)
            throws XPathExpressionException, Exception {
        String nodeExpression      = "(//table[@class='zauber gitternetz'])";
        Node node = (Node) xpath.compile(nodeExpression).evaluate(document, XPathConstants.NODE);
        
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
    
    
    protected Document nodeToDocument(Node node) throws Exception {
        Document newDocument = builder.newDocument();
        
        if(node != null) {
            node = node.getParentNode().removeChild(node);
            
            
            Node importedNode = newDocument.importNode(node, true);
            newDocument.appendChild(importedNode);
        }
        
        /*
        StringWriter writer = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(newDocument), new StreamResult(writer));
        String xml = writer.toString();
        */
        //System.out.println(xml);
        
        return newDocument;
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
        HeldenObjekt hero               = new HeldenObjekt();
        try {
            Instant initializationStart = Instant.now();

            Document document           = builder.parse(massageHtmlFile(xmlFile));
            Instant initializationEnd   = Instant.now();
            System.out.println("Parsing: " + Duration.between(initializationStart, initializationEnd));
            
            
            
            Instant eigenschaftenStart = Instant.now();
            String titleExpression = "/html/head/title/text()";
            String heroName        = xpath.compile(titleExpression).evaluate(document);
            hero.setName(heroName);
            
            gatherEigenschaften(hero, document);         
            gatherBasisWerte(hero, document);
            
            Instant eigenschaftenEnd = Instant.now();
            System.out.println("Eigenschaften: " + Duration.between(eigenschaftenStart, eigenschaftenEnd));
            
            
            
            List<TalentObjekt> talente = new ArrayList<>();
            List<KampftechnikObjekt> kampfTalente = new ArrayList<>();
            for(int i = 0; i < 10; i++) {
                String nameExpression     = "(//table[@class='talentgruppe gitternetz'])["+i+"]/tr[1]/th[1]/text()";
                String name               = xpath.compile(nameExpression).evaluate(document);
                if(name != null && !name.isEmpty()) {
                    Instant gatherStart = Instant.now();
                    
                    switch(name.substring(0, 2).toLowerCase()) {
                        case "ga" : gatherGaben(document, i, talente);               break;
                        case "ge" : gatherGesellschaftlich(document, i, talente);    break;
                        case "ha" : gatherHandwerk(document, i, talente);            break;
                        case "ka" : gatherKampftechniken(document, i, kampfTalente); break;
                        case "kö" : gatherKoerperlich(document, i, talente);         break;
                        case "na" : gatherNaturtalente(document, i, talente);        break;
                        case "sc" : gatherSchriften(document, i, talente);           break;        
                        case "sp" : gatherSprachen(document, i, talente);            break;    
                        case "wi" : gatherWissenstalente(document, i, talente);      break;            
                        default: break;
                    }
                    
                    Instant gatherEnd = Instant.now();
                    System.out.println(String.format("%s: %s", name, Duration.between(gatherStart, gatherEnd)));
                    
                }
                
                
            }
                    
            hero.setTalente(talente);
            hero.setKampftechniken(kampfTalente);
            
            Instant gatherZauberStart = Instant.now();
            List<TalentObjekt> zauber = new ArrayList<>();
            gatherZauber(document, zauber);
            
            hero.setZauber(zauber);
            Instant gatherZauberEnd = Instant.now();
            System.out.println(String.format("Zauber: %s", Duration.between(gatherZauberStart, gatherZauberEnd)));
            
            
            Instant gatherWaffenStart = Instant.now();
            List<WaffenObjekt> waffenListe = new ArrayList<>();
            gatherWaffen(document, waffenListe);            
            hero.setWaffen(waffenListe);
            Instant gatherWaffenEnd = Instant.now();
            System.out.println(String.format("Waffen: %s", Duration.between(gatherWaffenStart, gatherWaffenEnd)));
            
            Instant gatherPWaffenStart = Instant.now();
            List<ParadeObjekt> paradeObjektListe = new ArrayList<>();
            gatherParadeWaffen(document, paradeObjektListe);            
            hero.setParadeWaffen(paradeObjektListe);
            Instant gatherPWaffenEnd = Instant.now();
            System.out.println(String.format("Parade: %s", Duration.between(gatherPWaffenStart, gatherPWaffenEnd)));
            
            
            Instant gatherFWaffenStart = Instant.now();
            List<FernwaffenObjekt> fernwaffenListe = new ArrayList<>();
            gatherFernwaffen(document, fernwaffenListe);
            hero.setFernWaffen(fernwaffenListe);
            Instant gatherFWaffenEnd = Instant.now();
            System.out.println(String.format("Fernwaffen: %s", Duration.between(gatherFWaffenStart, gatherFWaffenEnd)));
            
            
            
            int behinderung = gatherBehinderung(document);
            hero.setBehinderung(behinderung);
            
            Instant gatherSonderfertigkeitenStart = Instant.now();
            gatherSonderfertigkeiten(document, hero);
            Instant gatherSonderfertigkeitenEnd = Instant.now();
            System.out.println(String.format("SF: %s", Duration.between(gatherSonderfertigkeitenStart, gatherSonderfertigkeitenEnd)));
            
            
            
            
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
