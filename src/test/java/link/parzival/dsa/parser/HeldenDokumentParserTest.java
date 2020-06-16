/**
 * 
 */
package link.parzival.dsa.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import link.parzival.dsa.object.FernwaffenObjekt;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.ParadeObjekt;
import link.parzival.dsa.object.WaffenObjekt;
import link.parzival.dsa.object.enumeration.DKEnum;


/**
 * @author mario
 *
 */
@TestMethodOrder(OrderAnnotation.class)
class HeldenDokumentParserTest {

    private static final String _HELDEN_NAME    = "Pedder Luminow";
    private static final String _DOCUMENT_NAME  = "PedderLuminow.html";
    private static final String _FK_WAFFE_NAME  = "Leichte Armbrust";
    private static final String _NK_WAFFE_NAME  = "Knüppel";
    private static final String _PW_WAFFE_NAME  = "Holzschild";
    private static final int _PW_LIST_SIZE      =  1;
    private static final int _BEHINDERUNG       =  1;
    private static final int _NK_LIST_SIZE      =  1;
    private static final int _FK_LIST_SIZE      =  2;
    private static final int _ZAUBER_LIST_SIZE  =  2;
    private static final int _SF_LIST_SIZE      =  6;
    private static final int _PARADE_BASIS      =  7;
    private static final int _FERNKAMPF_BASIS   =  7;
    private static final int _ATTACKE_BASIS     =  8;   
    private static final int _MAGIERESISTENZ    =  9;
    private static final int _INITIATIVE_BASIS  = 10;
    private static final int _PW_PARADE         = 10;
    private static final int _NK_WAFFE_PA       = 10;
    private static final int _FINGERFERTIGKEIT  = 11;
    private static final int _KOERPERKRAFT      = 12;
    private static final int _KONSTITUTION      = 12;
    private static final int _GEWANDTHEIT       = 12;
    private static final int _INTUITION         = 12;
    private static final int _NK_WAFFE_AT       = 12;
    private static final int _CHARISMA          = 13;
    private static final int _KLUGHEIT          = 14;
    private static final int _MUT               = 14;
    private static final int _FK_TAW            = 17;
    private static final int _ASTRALENERGIE     = 20;
    private static final int _KARMALENERGIE     = 24;
    private static final int _LEBENSENERGIE     = 28;
    private static final int _AUSDAUER          = 29;
    public static final int _TALENTE_LIST_SIZE  = 46;
    public static HeldenObjekt heldenObjekt     = null;
    public static HeldenDokumentParser parser   = null;
    public static File xmlDocumentSourceFile    = null;
    
    @BeforeAll
    static void setupBefore() {
        parser = new HeldenDokumentParser();        
        ClassLoader cl = HeldenDokumentParserTest.class.getClassLoader();
        
        xmlDocumentSourceFile = new File(cl.getResource(_DOCUMENT_NAME).getFile());
        assertTrue(xmlDocumentSourceFile.exists());
        
        try {
            heldenObjekt = parser.parseFile(xmlDocumentSourceFile);
            assertNotNull(heldenObjekt); 
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(heldenObjekt); 
    }
    

    @Test
    @Order(13)
    void testAstralenergie() {
        assertEquals(heldenObjekt.getAstralenergie(), _ASTRALENERGIE);
    }
    
    @Test
    @Order(12)
    void testAusdauer() {
        assertEquals(heldenObjekt.getAusdauer(), _AUSDAUER);
    }
    
    @Test
    @Order(17)
    void testBasisattacke() {
        assertEquals(heldenObjekt.getBasisattacke(), _ATTACKE_BASIS);
    }
    
    @Test
    @Order(19)
    void testBasisfernkampf() {
        assertEquals(heldenObjekt.getFernkampfbasis(), _FERNKAMPF_BASIS);
    }
    
    @Test
    @Order(16)
    void testBasisinitiative() {
        assertEquals(heldenObjekt.getBasisinitiative(), _INITIATIVE_BASIS);
    }
    
    @Test
    @Order(18)
    void testBasisparade() {
        assertEquals(heldenObjekt.getBasisparade(), _PARADE_BASIS);
    }
    
    @Test
    @Order(6)
    void testCharisma() {
        assertEquals(heldenObjekt.getCharisma(), _CHARISMA);
    }
    
    @Test
    @Order(7)
    void testFingerfertigkeit() {
        assertEquals(heldenObjekt.getFingerfertigkeit(), _FINGERFERTIGKEIT);
    }
    
    @Test
    @Order(8)
    void testGewandtheit() {
        assertEquals(heldenObjekt.getGewandtheit(), _GEWANDTHEIT);
    }
    
    @Test
    @Order(5)
    void testIntuition() {
        assertEquals(heldenObjekt.getIntuition(), _INTUITION);
    }
    
    @Test
    @Order(14)
    void testKarmalenergie() {
        assertEquals(heldenObjekt.getKarmalenergie(), _KARMALENERGIE);
    }
    
    @Test
    @Order(4)
    void testKlugheit() {
        assertEquals(heldenObjekt.getKlugheit(), _KLUGHEIT);
    }
    
    @Test
    @Order(10)
    void testKoerperkraft() {
        assertEquals(heldenObjekt.getKoerperkraft(), _KOERPERKRAFT);
    }
    
    @Test
    @Order(9)
    void testKonstitution() {
        assertEquals(heldenObjekt.getKonstitution(), _KONSTITUTION);
    }
    
    @Test
    @Order(11)
    void testLebensenergie() {
        assertEquals(heldenObjekt.getLebensenergie(), _LEBENSENERGIE);
    }
    
    @Test
    @Order(15)
    void testMagieresistenz() {
        assertEquals(heldenObjekt.getMagieresistenz(), _MAGIERESISTENZ);
    }
    
    @Test
    @Order(3)
    void testMut() {
        assertEquals(heldenObjekt.getMut(), _MUT);
    }
    
    @Test
    @Order(2)
    void testName() {
        assertEquals(heldenObjekt.getName(), _HELDEN_NAME);
    }
    
    @Test
    @Order(20)
    void testSonderfertigkeitenListSize() {
        assertEquals(heldenObjekt.getSonderfertigkeiten().size(), _SF_LIST_SIZE);
    }
    
    @Test
    @Order(21)
    void testTalenteListSize() {
        assertEquals(heldenObjekt.getTalente().size(), _TALENTE_LIST_SIZE);
    }
    
    @Test
    @Order(22)
    void testZauberListSize() {
        assertEquals(heldenObjekt.getZauber().size(), _ZAUBER_LIST_SIZE);
    }
    
    @Test
    @Order(23)
    void testNahkampfWaffen() {
        List<WaffenObjekt> waffenListe = heldenObjekt.getWaffen();
        assertNotNull(waffenListe);
        assertEquals(waffenListe.size(), _NK_LIST_SIZE);
        
        WaffenObjekt waffe = waffenListe.get(0);
        assertNotNull(waffe);
        assertEquals(waffe.getName(), _NK_WAFFE_NAME);
        assertEquals(waffe.getAttacke(), _NK_WAFFE_AT);
        assertEquals(waffe.getParade(),  _NK_WAFFE_PA);
        assertEquals(waffe.getDistanzklassen().get(0), DKEnum.N);
        
    }
    
    @Test
    @Order(24)
    void testFernkampfWaffen() {
        List<FernwaffenObjekt> waffenListe = heldenObjekt.getFernWaffen();
        assertNotNull(waffenListe);
        assertEquals(waffenListe.size(), _FK_LIST_SIZE);
        
        FernwaffenObjekt waffe = waffenListe.get(0);
        assertNotNull(waffe);
        assertEquals(waffe.getName(), _FK_WAFFE_NAME);
        assertEquals(waffe.getFk(), _FK_TAW);
        
    }
    
    @Test
    @Order(25)
    void TestParadeObjekt() {
        List<ParadeObjekt> waffenListe = heldenObjekt.getParadeWaffen();
        assertNotNull(waffenListe);
        assertEquals(waffenListe.size(), _PW_LIST_SIZE);
        
        ParadeObjekt waffe = waffenListe.get(0);
        assertNotNull(waffe);
        assertEquals(waffe.getName(), _PW_WAFFE_NAME);
        assertEquals(waffe.getParade(), _PW_PARADE);
        
    }
    
    @Test
    @Order(26)
    void testBehinderung() {
        assertEquals(heldenObjekt.getBehinderung(), _BEHINDERUNG);
        
    }
}
