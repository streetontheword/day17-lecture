package vttp.ssf.Day17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import vttp.ssf.Day17.service.ProcessService;


//will not process any HTML file 
@RestController
@RequestMapping(path="/process")
public class ProcessController {

    @Autowired
    ProcessService processSvc;

    @PostMapping(path="/searchBook")
    
        public String processBookSearch(@RequestBody MultiValueMap<String, String> form){
            
            String author = form.getFirst("name");
            System.out.println("hello: " + author);

           String result = processSvc.searchBook(author);

            return result;

        }

@PostMapping(path="/searchCountry")
public String processCountrySearch(@RequestBody MultiValueMap<String, String> form){
    //control layer will call the service layer cus there is where the logic is 
    
    ResponseEntity<String> results = processSvc.filterCountries(form.getFirst("searchName")); 

    return results.getBody();

}

@PostMapping(path="/searchCountryRegion")
public String processCountrySearchRegion(@RequestBody MultiValueMap<String, String> form){

    ResponseEntity<String> results = processSvc.filterCountriesByRegion(form.getFirst("regions")); 

    return results.getBody();


}

    
}
