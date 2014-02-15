package application.skuarch.clientrestful;

import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App {

    //==========================================================================
    public static void main(String[] args) throws Exception {

        System.out.println("sending jsonobject");

        JSONObject jsono = new JSONObject("{\"request\":\"connectivity\", \"url\":\"http://192.168.208.9:8080/sam5/webservices/v1/notifications\",\"expectedReturn\":\"true\"}");
        //jsono.accumulate("request", "connectivity");        

        ClientRestfulPost clientRestful = new ClientRestfulPost("http://192.168.208.9:8080/proxy/restful/v1/webserviceRemoteConnection");
        clientRestful.send(jsono);
        System.out.println("receiving json: " + clientRestful.receive());        
        
    }
}
