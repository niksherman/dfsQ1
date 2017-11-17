package skeleton;

import cucumber.api.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.*;
import java.util.Properties;

public class Stepdefs {
    @Given("^I have a property file and excel payload$")
    public void i_have_a_property_file_and_excel_payload() throws Exception {

       Properties propString = getProps();
        String stringCsv = readCsv();
        callApi((String) propString.get("url"), (String) propString.get("Content-Type"), stringCsv );

    }

    public Properties getProps() throws IOException {
        InputStream inputStream = null;
        String filePath = "";
        String url = "";
        Properties prop = new Properties();

        try {
            filePath = "config.properties";
            inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
            prop.load(inputStream);

            inputStream.close();
            url = prop.getProperty("url");
            String reqType = prop.getProperty("request_type");
            String contentType = prop.getProperty("Content-Type");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }


    public String readCsv() throws UnsupportedEncodingException {

        String filecsv = null;
        InputStream inputCsv = null;
        inputCsv = this.getClass().getClassLoader().getResourceAsStream("payload.csv");

        BufferedReader br = null;
        String line = null;
        String payload = "";

        try {
            br = new BufferedReader(new InputStreamReader(inputCsv, "UTF-8"));
            while ((line = br.readLine()) != null) {
                payload = payload.concat(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("no file");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(payload);
        return payload;

    }


    public void callApi(String url, String contentType, String payload) {
        String restAPIUrl = url;
        RestAssured.urlEncodingEnabled = true;
        Response apiResp = RestAssured.given().header("content-type",contentType).when().get(restAPIUrl);
        System.out.println("Ouput Resp:");
        System.out.println(apiResp.getBody().prettyPrint());

    }


}
