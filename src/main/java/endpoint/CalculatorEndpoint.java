package endpoint;

import model.Calculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "/calculator")
public class CalculatorEndpoint {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView inputForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("index", 0);
        modelAndView.setViewName("calculator");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity calculateFiboNumber(@RequestParam("index") int index) {
        System.out.println("inside calculateFiboNumber() method with index = " + index);
        Calculator calculator = new Calculator(index);
        System.out.println("Fibonacci number is: " + calculator.getFiboNumber());
        return ResponseEntity.ok(calculator.getFiboNumber());
    }
}
