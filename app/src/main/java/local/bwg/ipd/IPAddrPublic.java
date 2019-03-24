package local.bwg.ipd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Check global ip address
 */
public class IPAddrPublic  implements IPAddrInterface {
    @Override
    public String getIpAddr() {
        return sendPost();
    }

    // HTTP POST request
    private String sendPost() {
        try {
            String url = "https://myipinfo.net";
            //String url = "https://mybrowserinfo.com/detail.asp";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            con.setConnectTimeout(5000);

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "bhcp=1";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            /*
            int responseCode = con.getResponseCode();

            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);
            */

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            String rts = response.toString();
            //System.out.println(rts);
            rts = rts.substring(rts.indexOf("<p><h2>") + 7, rts.indexOf("</h2></p>"));
            //System.out.println(rts);
            return rts;
        } catch (IOException ignore) {
            return "n/d";
        }

    }
}
