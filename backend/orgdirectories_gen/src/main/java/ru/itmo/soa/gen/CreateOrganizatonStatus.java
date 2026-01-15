
package ru.itmo.soa.gen;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createOrganizatonStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="createOrganizatonStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="INITIATED"/&gt;
 *     &lt;enumeration value="MONEY_TAKEN"/&gt;
 *     &lt;enumeration value="ORGANIZATION_CREATED"/&gt;
 *     &lt;enumeration value="MONEY_RETURNING"/&gt;
 *     &lt;enumeration value="NOT_ENOUGH_MONEY"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "createOrganizatonStatus")
@XmlEnum
public enum CreateOrganizatonStatus {

    INITIATED,
    MONEY_TAKEN,
    ORGANIZATION_CREATED,
    MONEY_RETURNING,
    NOT_ENOUGH_MONEY;

    public String value() {
        return name();
    }

    public static CreateOrganizatonStatus fromValue(String v) {
        return valueOf(v);
    }

}
