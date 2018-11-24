package tcc.etec.needful.view.view.api;

public class Constantes {

    private final static String IP_WEBSERVICE = "18.228.43.192";
    //private final static String IP_WEBSERVICE = "localhost";
    private final static String POST = "POST";
    private final static String PUT = "PUT";
    private final static String GET = "GET";
    private final static String DELETE = "DELETE";
    private final static String USER_AGENT = "Mozilla/5.0";

    public static String getIpWebservice() {
        return IP_WEBSERVICE;
    }

    public static String getPOST() {
        return POST;
    }

    public static String getPUT() {
        return PUT;
    }

    public static String getGET() {
        return GET;
    }

    public static String getDELETE() {
        return DELETE;
    }

    public static String getUserAgent() {
        return USER_AGENT;
    }
}
