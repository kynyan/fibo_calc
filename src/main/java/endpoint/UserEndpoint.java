package endpoint;

import dao.UserRepository;
import model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class UserEndpoint {
    private UserRepository userRepository;

    public UserEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userRepository.findById(id));
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("userForm", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userRepository.findByUsername(userForm.getUsername());
        if (user != null) {
            String errorMsg = String.format("User with username [%s] has already been registered", userForm.getUsername());
            bindingResult.rejectValue("username", "error.user", errorMsg);
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userRepository.save(userForm);
            modelAndView.setViewName("calculator");
        }
        return modelAndView;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            System.out.println("Error: "+ error);
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");
        return model;
    }
}
