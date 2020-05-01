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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author mario
 *
 */
public class HeroHtmlParser {
	
	private int gatherBehinderung(Document document, XPath xpath) throws XPathExpressionException {
		// TODO Auto-generated method stub
		// <table class="zonenruestungen gitternetz">
		int result = 0;
		String countExpression 	= "count((//table[@class='zonenruestungen gitternetz'])/tr)";
		String countResult 		= xpath.compile(countExpression).evaluate(document);
		int count = Integer.parseInt(countResult);
		
		String expression 	= "(//table[@class='zonenruestungen gitternetz'])/tr["+ count +"]/td[14]/text()";
		String be 	 		= xpath.compile(expression).evaluate(document);
		
		if(be != null && !be.isEmpty()) {
			result = Integer.parseInt(be);
		}
		
		
		return result;
	}

	private void gatherGesellschaftlich(Document document, XPath xpath, List<TalentObjekt> talente)
			throws XPathExpressionException, Exception {
		//Koerperliche Talente
		// get count
		String countExpression 	= "count((//table[@class='talentgruppe gitternetz'])[3]/tr)";
		String countResult 		= xpath.compile(countExpression).evaluate(document);
		int count = Integer.parseInt(countResult);
		
		for(int i = 2; i <= count; i++) {
			String nameExpression 	= "(//table[@class='talentgruppe gitternetz'])[3]/tr["+ i +"]/td[1]/text()";
			String probeExpression  = "(//table[@class='talentgruppe gitternetz'])[3]/tr["+ i +"]/td[2]/text()";
			String tawExpression    = "(//table[@class='talentgruppe gitternetz'])[3]/tr["+ i +"]/td[3]/text()";
			
			
			String name  = xpath.compile(nameExpression).evaluate(document);
			String be 	 = "";
			String taw 	 = xpath.compile(tawExpression).evaluate(document);
			String probe = xpath.compile(probeExpression).evaluate(document);
			
			TalentObjekt talent = new TalentObjekt();
			talent.setName(name);
			talent.setBe(be);
			talent.setTalentwert(Integer.parseInt(taw));
			splitTalentProben(talent, probe);
			
			talente.add(talent);
		}
	}

	private void gatherHandwerk(Document document, XPath xpath, List<TalentObjekt> talente)
			throws XPathExpressionException, Exception {
		//Koerperliche Talente
		// get count
		String countExpression 	= "count((//table[@class='talentgruppe gitternetz'])[8]/tr)";
		String countResult 		= xpath.compile(countExpression).evaluate(document);
		int count = Integer.parseInt(countResult);
		
		for(int i = 2; i <= count; i++) {
			String nameExpression 	= "(//table[@class='talentgruppe gitternetz'])[8]/tr["+ i +"]/td[1]/text()";
			String probeExpression  = "(//table[@class='talentgruppe gitternetz'])[8]/tr["+ i +"]/td[2]/text()";
			String tawExpression    = "(//table[@class='talentgruppe gitternetz'])[8]/tr["+ i +"]/td[3]/text()";
			
			
			String name  = xpath.compile(nameExpression).evaluate(document);
			String be 	 = "";
			String taw 	 = xpath.compile(tawExpression).evaluate(document);
			String probe = xpath.compile(probeExpression).evaluate(document);
			
			TalentObjekt talent = new TalentObjekt();
			talent.setName(name);
			talent.setBe(be);
			talent.setTalentwert(Integer.parseInt(taw));
			splitTalentProben(talent, probe);
			
			talente.add(talent);
		}
	}
	
	private void gatherKampftechniken(Document document, XPath xpath, List<TalentObjekt> talente)
			throws XPathExpressionException, Exception {
		//Kampftechniken
		// get count
		String countExpression 	= "count((//table[@class='talentgruppe gitternetz'])[1]/tr)";
		String countResult 		= xpath.compile(countExpression).evaluate(document);
		int count = Integer.parseInt(countResult);
		
		for(int i = 2; i <= count; i++) {
			String nameExpression 	= "(//table[@class='talentgruppe gitternetz'])[1]/tr["+ i +"]/td[1]/text()";
			String beExpression		= "(//table[@class='talentgruppe gitternetz'])[1]/tr["+ i +"]/td[3]/text()";
			String tawExpression    = "(//table[@class='talentgruppe gitternetz'])[1]/tr["+ i +"]/td[6]/text()";
			
			String name  = xpath.compile(nameExpression).evaluate(document);
			String be 	 = xpath.compile(beExpression).evaluate(document);
			String taw 	 = xpath.compile(tawExpression).evaluate(document);
			String probe = " (--/--/--)";
			
			TalentObjekt talent = new TalentObjekt();
			talent.setName(name);
			talent.setBe(be);
			talent.setTalentwert(Integer.parseInt(taw));
			splitTalentProben(talent, probe);
			
			talente.add(talent);
		}
	}
	
	private void gatherKoerperlich(Document document, XPath xpath, List<TalentObjekt> talente)
			throws XPathExpressionException, Exception {
		//Koerperliche Talente
		// get count
		String countExpression 	= "count((//table[@class='talentgruppe gitternetz'])[2]/tr)";
		String countResult 		= xpath.compile(countExpression).evaluate(document);
		int count = Integer.parseInt(countResult);
		
		for(int i = 2; i <= count; i++) {
			String nameExpression 	= "(//table[@class='talentgruppe gitternetz'])[2]/tr["+ i +"]/td[1]/text()";
			String probeExpression  = "(//table[@class='talentgruppe gitternetz'])[2]/tr["+ i +"]/td[2]/text()";
			String beExpression		= "(//table[@class='talentgruppe gitternetz'])[2]/tr["+ i +"]/td[3]/text()";
			String tawExpression    = "(//table[@class='talentgruppe gitternetz'])[2]/tr["+ i +"]/td[4]/text()";
			
			
			String name  = xpath.compile(nameExpression).evaluate(document);
			String be 	 = xpath.compile(beExpression).evaluate(document);
			String taw 	 = xpath.compile(tawExpression).evaluate(document);
			String probe = xpath.compile(probeExpression).evaluate(document);
			
			TalentObjekt talent = new TalentObjekt();
			talent.setName(name);
			talent.setBe(be);
			talent.setTalentwert(Integer.parseInt(taw));
			splitTalentProben(talent, probe);
			
			talente.add(talent);
		}
	}
	
	private void gatherNaturtalente(Document document, XPath xpath, List<TalentObjekt> talente)
			throws XPathExpressionException, Exception {
		//Koerperliche Talente
		// get count
		String countExpression 	= "count((//table[@class='talentgruppe gitternetz'])[4]/tr)";
		String countResult 		= xpath.compile(countExpression).evaluate(document);
		int count = Integer.parseInt(countResult);
		
		for(int i = 2; i <= count; i++) {
			String nameExpression 	= "(//table[@class='talentgruppe gitternetz'])[4]/tr["+ i +"]/td[1]/text()";
			String probeExpression  = "(//table[@class='talentgruppe gitternetz'])[4]/tr["+ i +"]/td[2]/text()";
			String tawExpression    = "(//table[@class='talentgruppe gitternetz'])[4]/tr["+ i +"]/td[3]/text()";
			
			
			String name  = xpath.compile(nameExpression).evaluate(document);
			String be 	 = "";
			String taw 	 = xpath.compile(tawExpression).evaluate(document);
			String probe = xpath.compile(probeExpression).evaluate(document);
			
			TalentObjekt talent = new TalentObjekt();
			talent.setName(name);
			talent.setBe(be);
			talent.setTalentwert(Integer.parseInt(taw));
			splitTalentProben(talent, probe);
			
			talente.add(talent);
		}
	}
	
	private void gatherSchriften(Document document, XPath xpath, List<TalentObjekt> talente)
			throws XPathExpressionException, Exception {
		//Koerperliche Talente
		// get count
		String countExpression 	= "count((//table[@class='talentgruppe gitternetz'])[7]/tr)";
		String countResult 		= xpath.compile(countExpression).evaluate(document);
		int count = Integer.parseInt(countResult);
		
		for(int i = 2; i <= count; i++) {
			String nameExpression 	= "(//table[@class='talentgruppe gitternetz'])[7]/tr["+ i +"]/td[1]/text()";
			//String probeExpression  = "(//table[@class='talentgruppe gitternetz'])[6]/tr["+ i +"]/td[2]/text()";
			String tawExpression    = "(//table[@class='talentgruppe gitternetz'])[7]/tr["+ i +"]/td[3]/text()";
			
			
			String name  = xpath.compile(nameExpression).evaluate(document);
			String be 	 = "";
			String taw 	 = xpath.compile(tawExpression).evaluate(document);
			String probe = " (--/--/--)";
			
			TalentObjekt talent = new TalentObjekt();
			talent.setName(name);
			talent.setBe(be);
			talent.setTalentwert(Integer.parseInt(taw));
			splitTalentProben(talent, probe);
			
			talente.add(talent);
		}
	}
	
	private void gatherSprachen(Document document, XPath xpath, List<TalentObjekt> talente)
			throws XPathExpressionException, Exception {
		//Koerperliche Talente
		// get count
		String countExpression 	= "count((//table[@class='talentgruppe gitternetz'])[6]/tr)";
		String countResult 		= xpath.compile(countExpression).evaluate(document);
		int count = Integer.parseInt(countResult);
		
		for(int i = 2; i <= count; i++) {
			String nameExpression 	= "(//table[@class='talentgruppe gitternetz'])[6]/tr["+ i +"]/td[1]/text()";
			//String probeExpression  = "(//table[@class='talentgruppe gitternetz'])[6]/tr["+ i +"]/td[2]/text()";
			String tawExpression    = "(//table[@class='talentgruppe gitternetz'])[6]/tr["+ i +"]/td[3]/text()";
			
			
			String name  = xpath.compile(nameExpression).evaluate(document);
			String be 	 = "";
			String taw 	 = xpath.compile(tawExpression).evaluate(document);
			String probe = " (--/--/--)";
			
			TalentObjekt talent = new TalentObjekt();
			talent.setName(name);
			talent.setBe(be);
			talent.setTalentwert(Integer.parseInt(taw));
			splitTalentProben(talent, probe);
			
			talente.add(talent);
		}
	}
	
	private void gatherWaffen(Document document, XPath xpath, List<WaffenObjekt> waffenListe)
			throws XPathExpressionException, Exception {
		//Koerperliche Talente
		// get count
		String countExpression 	= "count((//table[@class='nkwaffen gitternetz'])/tr)";
		String countResult 		= xpath.compile(countExpression).evaluate(document);
		int count = Integer.parseInt(countResult);
		
		for(int i = 2; i <= count; i++) {
			String nameExpression 	= "(//table[@class='nkwaffen gitternetz'])/tr["+ i +"]/td[1]/text()";
			String iniExpression    = "(//table[@class='nkwaffen gitternetz'])/tr["+ i +"]/td[6]/text()";
			String atExpression     = "(//table[@class='nkwaffen gitternetz'])/tr["+ i +"]/td[8]/text()";
			String paExpression     = "(//table[@class='nkwaffen gitternetz'])/tr["+ i +"]/td[9]/text()";
			String tpExpression     = "(//table[@class='nkwaffen gitternetz'])/tr["+ i +"]/td[10]/text()";
			
			
			String name  	= xpath.compile(nameExpression).evaluate(document);
			String iniStr 	= xpath.compile(iniExpression).evaluate(document);
			String atStr 	= xpath.compile(atExpression).evaluate(document);
			String paStr 	= xpath.compile(paExpression).evaluate(document);
			String tpStr	= xpath.compile(tpExpression).evaluate(document);
			
			if(name != null && !name.isEmpty()) {
				WaffenObjekt waffe = new WaffenObjekt();
				waffe.setName(name);
				waffe.setTrefferpunkte(tpStr);
				waffe.setAttacke(Integer.parseInt(atStr));
				waffe.setParade(Integer.parseInt(paStr));
				waffe.setInitiative(Integer.parseInt(iniStr));
				
				waffenListe.add(waffe);
			}
		}
	}
	
	private void gatherWissenstalente(Document document, XPath xpath, List<TalentObjekt> talente)
			throws XPathExpressionException, Exception {
		//Koerperliche Talente
		// get count
		String countExpression 	= "count((//table[@class='talentgruppe gitternetz'])[5]/tr)";
		String countResult 		= xpath.compile(countExpression).evaluate(document);
		int count = Integer.parseInt(countResult);
		
		for(int i = 2; i <= count; i++) {
			String nameExpression 	= "(//table[@class='talentgruppe gitternetz'])[5]/tr["+ i +"]/td[1]/text()";
			String probeExpression  = "(//table[@class='talentgruppe gitternetz'])[5]/tr["+ i +"]/td[2]/text()";
			String tawExpression    = "(//table[@class='talentgruppe gitternetz'])[5]/tr["+ i +"]/td[3]/text()";
			
			
			String name  = xpath.compile(nameExpression).evaluate(document);
			String be 	 = "";
			String taw 	 = xpath.compile(tawExpression).evaluate(document);
			String probe = xpath.compile(probeExpression).evaluate(document);
			
			TalentObjekt talent = new TalentObjekt();
			talent.setName(name);
			talent.setBe(be);
			talent.setTalentwert(Integer.parseInt(taw));
			splitTalentProben(talent, probe);
			
			talente.add(talent);
		}
	}
	
	private void gatherZauber(Document document, XPath xpath, List<TalentObjekt> zauberListe)
			throws XPathExpressionException, Exception {
		//Koerperliche Talente
		// get count
		String countExpression 	= "count((//table[@class='zauber gitternetz'])/tr)";
		String countResult 		= xpath.compile(countExpression).evaluate(document);
		int count = Integer.parseInt(countResult);
		
		for(int i = 2; i <= count; i++) {
			String nameExpression 	= "(//table[@class='zauber gitternetz'])/tr["+ i +"]/td[1]/text()";
			String probeExpression  = "(//table[@class='zauber gitternetz'])/tr["+ i +"]/td[2]/text()";
			String tawExpression    = "(//table[@class='zauber gitternetz'])/tr["+ i +"]/td[3]/text()";
			
			
			String name  = xpath.compile(nameExpression).evaluate(document);
			String be 	 = "";
			String taw 	 = xpath.compile(tawExpression).evaluate(document);
			String probe = xpath.compile(probeExpression).evaluate(document);
			
			TalentObjekt zauber = new TalentObjekt();
			zauber.setName(name);
			zauber.setBe(be);
			zauber.setTalentwert(Integer.parseInt(taw));
			splitTalentProben(zauber, probe);
			
			zauberListe.add(zauber);
		}
	}
	
	public HeldenObjekt parseFile(File xmlFile) throws Exception {
		DocumentBuilderFactory factory 	= DocumentBuilderFactory.newInstance();
		DocumentBuilder builder 		= null;
		HeldenObjekt hero 				= new HeldenObjekt();
		try {
			factory.setValidating(false);
			factory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		    
			builder = factory.newDocumentBuilder();

			Document document = builder.parse(xmlFile);
			
			XPath xpath = XPathFactory.newInstance().newXPath();
			String titleExpression = "/html/head/title/text()";
			String heroName = xpath.compile(titleExpression).evaluate(document);
			hero.setName(heroName);
			
			for(int i = 2; i < 10; i++) {
				String valueExpression = "//table[@class='eigenschaften gitternetz']/tr["+i+"]/td[4]/text()";
				String value = xpath.compile(valueExpression).evaluate(document);

				switch(i) {
				case 2: hero.setMut(Integer.parseInt(value));  				break;
				case 3: hero.setKlugheit(Integer.parseInt(value));  		break;
				case 4: hero.setIntuition(Integer.parseInt(value)); 		break;
				case 5: hero.setCharisma(Integer.parseInt(value)); 			break;
				case 6: hero.setFingerfertigkeit(Integer.parseInt(value)); 	break;
				case 7: hero.setGewandtheit(Integer.parseInt(value)); 		break;
				case 8: hero.setKonstitution(Integer.parseInt(value)); 		break;
				case 9: hero.setKoerperkraft(Integer.parseInt(value));      break;
				}
			}
			
			for(int i = 2; i < 8; i++) {
				String valueExpression = "//table[@class='basiswerte gitternetz']/tr["+i+"]/td[5]/text()";
				String value = xpath.compile(valueExpression).evaluate(document);
				if(value.isEmpty()) {
					value = "0";
				}
				
				switch(i) {
				case 2: hero.setLebensenergie(Integer.parseInt(value));  	break;
				case 3: hero.setAusdauer(Integer.parseInt(value));  		break;
				case 4: hero.setAstralenergie(Integer.parseInt(value)); 	break;
				case 5: hero.setKarmalenergie(Integer.parseInt(value)); 	break;
				case 6: hero.setMagieresistenz(Integer.parseInt(value));    break;
				case 7: hero.setBasisinitiative(Integer.parseInt(value)); 	break;
				}
			}
			
			List<TalentObjekt> talente = new ArrayList<>();
			gatherKampftechniken(document, xpath, talente);
			gatherKoerperlich(document, xpath, talente);
			gatherGesellschaftlich(document, xpath, talente);
			gatherNaturtalente(document, xpath, talente);
			gatherWissenstalente(document, xpath, talente);
			gatherSprachen(document, xpath, talente);
			gatherSchriften(document, xpath, talente);
			gatherHandwerk(document, xpath, talente);
			
			hero.setTalente(talente);
			
			List<TalentObjekt> zauber = new ArrayList<>();
			gatherZauber(document, xpath, zauber);
			
			hero.setZauber(zauber);
			
			List<WaffenObjekt> waffenListe = new ArrayList<>();
			gatherWaffen(document, xpath, waffenListe);
			
			hero.setWaffen(waffenListe);
			
			int behinderung = gatherBehinderung(document, xpath);
			hero.setBehinderung(behinderung);
			
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hero;
	}
	
	private void splitTalentProben(TalentObjekt talentObjekt, String probe) throws Exception {
		String localprobe = probe.trim();
		localprobe  	  = localprobe.replaceAll("--", "NA").replaceAll("\\*\\*", "NA");
		
		String regex = "[A-Z]{2}";
		
		Pattern p    = Pattern.compile(regex);
		Matcher matcher = p.matcher(localprobe);
		
		List<EigenschaftEnum> probenList = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			if(matcher.find()) {
				String s = matcher.group();
				probenList.add(EigenschaftEnum.valueOf(s));
			} else {
				System.err.println(localprobe);
				throw new Exception("Could not match Probe!");
			}
		}
		
		talentObjekt.setProbenTalent1(probenList.get(0));
		talentObjekt.setProbenTalent2(probenList.get(1));
		talentObjekt.setProbenTalent3(probenList.get(2));
	}

}
