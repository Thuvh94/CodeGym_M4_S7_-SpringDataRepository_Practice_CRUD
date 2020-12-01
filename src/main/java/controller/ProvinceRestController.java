package controller;

import model.Customer;
import model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import service.CustomerService;
import service.ProvinceService;

import java.util.Optional;
@RestController
public class ProvinceRestController {

    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/provinces/", method = RequestMethod.GET)
    public ResponseEntity<Page<Province>> listAllProvince(@RequestParam("s") Optional<String> s, @PageableDefault(8) Pageable pageable) {
        Iterable<Province> provinces = provinceService.findAll();
        if (provinces == null) {
            return new ResponseEntity (HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity(provinces, HttpStatus.OK);
    }

//-------------------Retrieve Single province--------------------------------------------------------

    @RequestMapping(value = "/province/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Province> getProvince(@PathVariable("id") long id) {
        System.out.println("Fetching Customer with id " + id);
        Province province = provinceService.findById(id);
        if (province == null) {
            System.out.println("Customer with id " + id + " not found");
            return new ResponseEntity<Province>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Province>(province, HttpStatus.OK);
    }

    //-------------------Create a province--------------------------------------------------------

    @RequestMapping(value = "/provinces/", method = RequestMethod.POST)
    public ResponseEntity<Void> createProvince(@RequestBody Province province, UriComponentsBuilder ucBuilder) {
        provinceService.save(province);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/provinces/{id}").buildAndExpand(province.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //------------------- Update a province --------------------------------------------------------

    @RequestMapping(value = "/provinces/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Province> updateProvince(@PathVariable("id") long id, @RequestBody Province province) {
        Province currentProvince = provinceService.findById(id);

        if (currentProvince == null) {
            return new ResponseEntity<Province>(HttpStatus.NOT_FOUND);
        }

        currentProvince.setName(province.getName());
        currentProvince.setId(id);

        provinceService.save(currentProvince);
        return new ResponseEntity<>(currentProvince, HttpStatus.OK);
    }

    //------------------- Delete a province --------------------------------------------------------

    @RequestMapping(value = "/provinces/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Province> deleteProvince(@PathVariable("id") long id) {
        Province province = provinceService.findById(id);
        if (province == null) {
            return new ResponseEntity<Province>(HttpStatus.NOT_FOUND);
        }

        provinceService.remove(id);
        return new ResponseEntity<Province>(HttpStatus.NO_CONTENT);
    }
}
