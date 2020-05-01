/**
 * 
 */
package link.parzival.dsa;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author mario
 *
 */
public class HeroXmlParser {
	
	public HeldenObjekt parseFile(File xmlFile) {
		DocumentBuilderFactory factory 	= DocumentBuilderFactory.newInstance();
		DocumentBuilder builder 		= null;
		HeldenObjekt hero 				= new HeldenObjekt();
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			
			
			Element rootElement = document.getDocumentElement();
			Node rootChild = rootElement.getFirstChild();
			if(rootChild.getNodeType() == Node.ELEMENT_NODE) {
				Element heldElement = (Element) rootChild;
				
				hero.setName(heldElement.getAttribute("name"));
				
				Element eigenschaftenElement = (Element) heldElement.getElementsByTagName("eigenschaften").item(0);
				NodeList eigenschaften = eigenschaftenElement.getChildNodes();
				for(int i = 0; i < eigenschaften.getLength(); i++) {
					Element eigenschaft = (Element) eigenschaften.item(i);
					String eName  = eigenschaft.getAttribute("name");
					String eValue = eigenschaft.getAttribute("value");
					int    intVal = Integer.parseInt(eValue);
					switch(eName.toLowerCase()) {
					case "mut" 				: hero.setMut(intVal); 				break;
					case "klugheit" 		: hero.setKlugheit(intVal); 		break;
					case "intuition" 		: hero.setIntuition(intVal); 		break;
					case "charisma"			: hero.setCharisma(intVal);			break;
					case "gewandtheit"		: hero.setGewandtheit(intVal); 		break;
					case "konstitution" 	: hero.setKonstitution(intVal); 	break;
					case "körperkraft" 	    : hero.setKoerperkraft(intVal); 	break;
					case "fingerfertigkeit" : hero.setFingerfertigkeit(intVal); break;
					case "lebensenergie"	: hero.setLebensenergie(intVal); 	break;
					case "astralenergie"	: hero.setAstralenergie(intVal); 	break;
					case "karmaenergie"		: hero.setKarmalenergie(intVal);    break;
					case "ausdauer"			: hero.setAusdauer(intVal);    		break;
					
					}
				}
				
				List<TalentObjekt> talentListe = new ArrayList<>();
				Element talentlistenElement = (Element) heldElement.getElementsByTagName("talentliste").item(0);
				NodeList talente = talentlistenElement.getChildNodes();
				for(int i = 0; i < talente.getLength(); i++) {
					Element talent = (Element) talente.item(i);
					TalentObjekt talentObjekt = new TalentObjekt();
					
					talentObjekt.setName(talent.getAttribute("name"));
					splitTalentProben(talentObjekt, talent);
					talentObjekt.setBe(talent.getAttribute("be"));
					talentObjekt.setTalentwert(Integer.valueOf(talent.getAttribute("value")));
					
					talentListe.add(talentObjekt);
				}
				hero.setTalente(talentListe);
				
				
				List<TalentObjekt> zauberListe = new ArrayList<>();
				Element zauberListenElement = (Element) heldElement.getElementsByTagName("zauberliste").item(0);
				NodeList zauber = zauberListenElement.getChildNodes();
				for(int i = 0; i < zauber.getLength(); i++) {
					Element zauberspruch = (Element) zauber.item(i);
					TalentObjekt talentObjekt = new TalentObjekt();
					
					talentObjekt.setName(zauberspruch.getAttribute("name"));
					splitTalentProben(talentObjekt, zauberspruch);
					talentObjekt.setBe(zauberspruch.getAttribute("be"));
					talentObjekt.setTalentwert(Integer.valueOf(zauberspruch.getAttribute("value")));
					
					zauberListe.add(talentObjekt);
				}
				hero.setZauber(zauberListe);
			}
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hero;
	}
	
	private void splitTalentProben(TalentObjekt talentObjekt, Element talentElement) throws Exception {
		String probe = talentElement.getAttribute("probe");
		probe  = probe.trim();
		probe  = probe.replaceAll("--", "NA");
		
		String regex = "[A-Z]{2}";
		
		Pattern p    = Pattern.compile(regex);
		Matcher matcher = p.matcher(probe);
		
		List<EigenschaftEnum> probenList = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			if(matcher.find()) {
				String s = matcher.group();
				probenList.add(EigenschaftEnum.valueOf(s));
			} else {
				System.err.println(probe);
				throw new Exception("Could not match Probe!");
			}
		}
		
		talentObjekt.setProbenTalent1(probenList.get(0));
		talentObjekt.setProbenTalent2(probenList.get(1));
		talentObjekt.setProbenTalent3(probenList.get(2));
	}
}
