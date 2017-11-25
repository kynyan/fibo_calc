package endpoint;

import model.Calculator;
import org.springframework.validation.BindingResult;
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
    public ModelAndView calculateFiboNumber(@ModelAttribute("index") int index, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Calculator calculator = new Calculator(index);
            modelAndView.addObject("result", calculator.getFiboNumber());
        } catch (IllegalArgumentException e) {
            bindingResult.reject( "error.index", e.getMessage());
        }
        modelAndView.setViewName("calculator");
        return modelAndView;
    }
}
