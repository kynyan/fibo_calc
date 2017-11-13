package endpoint;

import model.Calculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorEndpoint {

    @RequestMapping(method = RequestMethod.GET, path = "/fibonacci-number")
    public ResponseEntity getFiboNumber(@RequestParam int index) {
        return ResponseEntity.ok(new Calculator(index).getFiboNumber());
    }
}
