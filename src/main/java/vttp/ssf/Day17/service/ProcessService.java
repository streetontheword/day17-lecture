package vttp.ssf.Day17.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProcessService {

    String url_booksearch = "https://openlibrary.org/search.json?author=";
    String url_countries = "https://api.first.org/data/v1/countries";
    RestTemplate template = new RestTemplate();

    public String searchBook(String author) {

        url_booksearch += author;
        String result = template.getForObject(url_booksearch, String.class);

        return result;
        // getObject will return you a string

    }

    // why dont need parameters?

    public ResponseEntity<String> getCountries() {

        ResponseEntity<String> result = template.getForEntity(url_countries, String.class);

        return result;
        // getEntity will return you a responsentity

    }

    // different example search country
    public ResponseEntity<String> filterCountries(String name) {
        String urlCall = url_countries;
        urlCall += "?q=" + name;
        ResponseEntity<String> result = template.getForEntity(urlCall, String.class);
        return result;

    }

    public ResponseEntity<String> filterCountriesByRegion(String name) {
        String urlCall = url_countries;
        urlCall += "?region=" + name;
        ResponseEntity<String> result = template.getForEntity(urlCall, String.class);

        return result;

    }

}
