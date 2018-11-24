package tcc.etec.needful.view.view.api;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class WebService extends AsyncTask<String, String, String> {

    String json;
    String url;
    String method;

    private final static String USER_AGENT = "Mozilla/5.0";

    public WebService(String url, String json, String methor) {
        this.json = json;
        this.method = methor;
        this.url = url;
    }

    public WebService(String url, String methor) {
        this.method = methor;
        this.url = url;
    }


    @Override
    protected String doInBackground(String... strings) {
        StringBuffer response = new StringBuffer();

        try {
            if (method.equals("GET")) {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod(method);
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Charset", "UTF-8");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

            } else if (method.equals("DELETE")) {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod(method);
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Charset", "ISO-8859-1");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Type", "application/json;charset=ISO-8859-1");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'DELETE' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

            } else if (method.equals("POST")) {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod(method);
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-Type", "application/json;charset=ISO-8859-1");

                con.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
                // DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                out.write(json);
                out.flush();
                out.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


            } else if (method.equals("PUT")) {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod(method);
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-Type", "application/json;charset=ISO-8859-1");

                con.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
                // DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                out.write(json);
                out.flush();
                out.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'PUT' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(response);
        return response.toString();
    }
}
