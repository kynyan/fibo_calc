package endpoint;

import dto.UserDto;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.UserServiceInterface;

import javax.validation.Valid;

@RestController
public class UserEndpoint {
    @Autowired
    private UserServiceInterface userService;

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        UserDto user = new UserDto();
        modelAndView.addObject("userForm", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute("userForm") @Valid UserDto userForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUser(userForm.getUsername());
        if (user != null) {
            String errorMsg = String.format("User with username [%s] has already been registered", userForm.getUsername());
            bindingResult.rejectValue("username", "error.user", errorMsg);
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.save(new User(userForm));
            modelAndView.addObject("index",0);
            modelAndView.setViewName("login");
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
