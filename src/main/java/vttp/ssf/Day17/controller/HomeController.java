package vttp.ssf.Day17.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.ssf.Day17.model.Country;
import vttp.ssf.Day17.service.ProcessService;

@Controller
@RequestMapping(path = "/home")
public class HomeController {

    @Autowired
    ProcessService processSvc;

    @GetMapping(path = "/booksearch")
    public String bookSearchForm() {

        return "booksearch";

    }

    // why this one dont need to do at the rest controller

    @GetMapping(path = "/countries")
    // response entity
    public ResponseEntity<String> listCountry() {

        ResponseEntity<String> result = processSvc.getCountries();

        // if i want to cast it to a JSON object, we can

        return result;

    }

    // in pom.xml, we have the glassfish jason p dependencies
    @GetMapping(path = "/countries/jsontest")
    // response entity

    public String listCountries(Model m) { // from api call

        ResponseEntity<String> result = processSvc.getCountries();

        String jsonString = result.getBody().toString(); // we only want the payload data, we dw the status so we
                                                         // .getBody()

        // to read the string as json object, we need json reader
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonOb = jsonReader.readObject();
        // System.out.println("Json object" + jsonOb); //this is the json data but it is
        // still an object

        JsonObject jsonObjectData = jsonOb.getJsonObject("data");
        System.out.println("Json object Data" + jsonObjectData);
        System.out.println("Json object size" + jsonObjectData.size());

        // create a new empty list
        List<Country> countries = new ArrayList<Country>();

        Set<Entry<String, JsonValue>> entries = jsonObjectData.entrySet();
        for (Entry<String, JsonValue> entry : entries) {
            // System.out.println(entry.getKey() + ">" + entry.getValue().toString());
            System.out.println(entry.getKey() + ">" + entry.getValue().asJsonObject().getString("country"));
            countries.add(new Country(entry.getKey(), entry.getValue().asJsonObject().getString("country")));

            m.addAttribute("countries", countries);

        }

        return "countrylist"; // is this still return the response entity or the json object?

    }

    @GetMapping(path = "/countrysearch")
    public String countrySearchForm() {

        return "countrysearch";
    }

    @GetMapping(path = "/countrysearchregion")
    public String byRegion(Model m) {
        ResponseEntity<String> result = processSvc.getCountries();
        String jsonString = result.getBody().toString();
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonOb = jsonReader.readObject();
        JsonObject jsonObjectData = jsonOb.getJsonObject("data");

        List<String> regions = new ArrayList<>();
        Set<Entry<String, JsonValue>> entries = jsonObjectData.entrySet();

        for (Entry<String, JsonValue> entry : entries) {
            String regionValue = entry.getValue().asJsonObject().getString("region");
            if (regions.size() == 0) {
                regions.add(regionValue);
            } else if (!regions.contains(regionValue)) {
                regions.add(regionValue);

            }

        }
        System.out.println(regions);
        m.addAttribute("regions", regions);
        return "countrysearchregion";

    }

}
