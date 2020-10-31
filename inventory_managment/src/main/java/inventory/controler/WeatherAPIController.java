package inventory.controler;

import inventory.model.Users;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class WeatherAPIController {
    static final Logger logger = Logger.getLogger(WeatherAPIController.class);

    @GetMapping("/weatherAPI")
    public String normal(Model model) {
//        model.addAttribute("weatherForm", "");
        String strResponse = sendGET();
        JSONObject jsonObject = new JSONObject(strResponse);

        float speed = jsonObject.getJSONObject("wind").getFloat("speed");
        logger.info("-----speed: " + speed);
        return "weather/weather";
    }

    private String sendGET(){
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=0166fbd6ecc6111a6303f706b91b94bc");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                return response.toString();
            }
            else {
                System.out.println("GET request not worked");
            }
        }catch (IOException e) {
            logger.info("Exception error" + e);
        };
        return "";
    }
}
