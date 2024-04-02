
package practicaviaje;

import java.io.Serializable;
/**
 *
 * @author Alexander
 */
public class viaje implements Serializable {
  private int id;
    private String start;
    private String end;
    private int distance;

    public viaje(int id, String start, String end, int distance) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    
    
}

