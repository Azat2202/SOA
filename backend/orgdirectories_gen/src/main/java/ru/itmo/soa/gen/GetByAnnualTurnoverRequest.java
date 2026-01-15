
package ru.itmo.soa.gen;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="minAnnualTurnover" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="maxAnnualTurnover" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "minAnnualTurnover",
    "maxAnnualTurnover",
    "page",
    "size"
})
@XmlRootElement(name = "getByAnnualTurnoverRequest")
public class GetByAnnualTurnoverRequest {

    protected int minAnnualTurnover;
    protected int maxAnnualTurnover;
    protected int page;
    protected int size;

    /**
     * Gets the value of the minAnnualTurnover property.
     * 
     */
    public int getMinAnnualTurnover() {
        return minAnnualTurnover;
    }

    /**
     * Sets the value of the minAnnualTurnover property.
     * 
     */
    public void setMinAnnualTurnover(int value) {
        this.minAnnualTurnover = value;
    }

    /**
     * Gets the value of the maxAnnualTurnover property.
     * 
     */
    public int getMaxAnnualTurnover() {
        return maxAnnualTurnover;
    }

    /**
     * Sets the value of the maxAnnualTurnover property.
     * 
     */
    public void setMaxAnnualTurnover(int value) {
        this.maxAnnualTurnover = value;
    }

    /**
     * Gets the value of the page property.
     * 
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     * 
     */
    public void setPage(int value) {
        this.page = value;
    }

    /**
     * Gets the value of the size property.
     * 
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     */
    public void setSize(int value) {
        this.size = value;
    }

}
