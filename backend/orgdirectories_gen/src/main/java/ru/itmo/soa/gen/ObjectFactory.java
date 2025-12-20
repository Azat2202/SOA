
package ru.itmo.soa.gen;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.itmo.soa.gen package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.itmo.soa.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetByAnnualTurnoverRequest }
     * 
     */
    public GetByAnnualTurnoverRequest createGetByAnnualTurnoverRequest() {
        return new GetByAnnualTurnoverRequest();
    }

    /**
     * Create an instance of {@link GetByType }
     * 
     */
    public GetByType createGetByType() {
        return new GetByType();
    }

    /**
     * Create an instance of {@link OrganizationWithPaging }
     * 
     */
    public OrganizationWithPaging createOrganizationWithPaging() {
        return new OrganizationWithPaging();
    }

    /**
     * Create an instance of {@link Organization }
     * 
     */
    public Organization createOrganization() {
        return new Organization();
    }

    /**
     * Create an instance of {@link Coordinates }
     * 
     */
    public Coordinates createCoordinates() {
        return new Coordinates();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
    }

}
