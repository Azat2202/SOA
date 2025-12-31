
package ru.itmo.soa.gen;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="id" type="{http://www.itmo.ru/soa/gen}createOrganizationWorkflowId"/&gt;
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
    "id"
})
@XmlRootElement(name = "organizationStatusRequest")
public class OrganizationStatusRequest {

    @XmlElement(required = true)
    protected CreateOrganizationWorkflowId id;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link CreateOrganizationWorkflowId }
     *     
     */
    public CreateOrganizationWorkflowId getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateOrganizationWorkflowId }
     *     
     */
    public void setId(CreateOrganizationWorkflowId value) {
        this.id = value;
    }

}
