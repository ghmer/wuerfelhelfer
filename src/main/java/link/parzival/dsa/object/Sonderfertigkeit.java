/**
 * 
 */
package link.parzival.dsa.object;

/**
 * @author mario
 *
 */
public class Sonderfertigkeit {
    private String name;
    
    public Sonderfertigkeit() {
        name = new String();
    }
    
    public Sonderfertigkeit(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
