package ru.itmo.soa.utls;


import ru.itmo.soa.api.OrganizationFilterServiceLocal;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EJBLookupHelper {

    private static final String REMOTE_HOST = "localhost";
    private static final String IIOP_PORT = "3700";

    private static final String JNDI_NAME = "java:app/organizations_ejb/OrganizationFilterServiceBean!ru.itmo.soa.api.OrganizationFilterServiceLocal";

    public static OrganizationFilterServiceLocal lookupRemoteOrganizationFilterService() {
        Context ctx = null;
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.enterprise.naming.SerialInitContextFactory");
            props.setProperty("org.omg.CORBA.ORBInitialHost", REMOTE_HOST);
            props.setProperty("org.omg.CORBA.ORBInitialPort", IIOP_PORT);

            ctx = new InitialContext(props);
            OrganizationFilterServiceLocal organizationFilterService = (OrganizationFilterServiceLocal) ctx.lookup(JNDI_NAME);

            return organizationFilterService;
        } catch (NamingException e) {
            throw new RuntimeException("Failed to lookup remote EJB via IIOP: " + e.getMessage(), e);
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                }
            }
        }
    }
}