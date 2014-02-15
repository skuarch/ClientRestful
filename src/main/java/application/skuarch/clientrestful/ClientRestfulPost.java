package application.skuarch.clientrestful;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONObject;

/**
 * Wrapper to RestEasy library.
 * @author skuarch
 */
public final class ClientRestfulPost implements ClientRestful {

    private ClientRequest clientRequest = null;
    private ClientResponse<String> clientResponse = null;
    private int status = 0;

    //==========================================================================
    /**
     * create a instance.
     * @param url 
     */
    public ClientRestfulPost(String url) {

        clientRequest = new ClientRequest(url);

    } // end ClientRestful

    //==========================================================================
    @Override
    public void send(JSONObject json) throws Exception {

        if (json == null || json.length() < 0) {
            throw new IllegalArgumentException("json is null or empty");
        }

        clientRequest.accept(MediaType.APPLICATION_JSON);
        clientRequest.body(MediaType.APPLICATION_JSON, json.toString());
        clientRequest.post(String.class);

    } // end send

    //==========================================================================
    @Override
    public String receive() throws Exception {

        StringBuilder sb = new StringBuilder();
        String inputString = null;
        BufferedReader br = null;
        ByteArrayInputStream bais = null;

        try {

            clientResponse = clientRequest.post(String.class);
            status = clientResponse.getStatus();

            if (status != 200) {
                throw new Exception("bad response from server " + status);
            }

            bais = new ByteArrayInputStream(clientResponse.getEntity().getBytes());
            br = new BufferedReader(new InputStreamReader(bais));

            while ((inputString = br.readLine()) != null) {
                sb.append(inputString);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            closeBufferedReader(br);
            closeInputStream(bais);
        }

        return sb.toString();

    } // end receive

    //==========================================================================
    private void closeBufferedReader(BufferedReader bufferedReader) {

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    } // end closeBufferedReader

    //==========================================================================
    private void closeInputStream(InputStream inputStream) {

        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    } // end closeInputStream

} // end class
