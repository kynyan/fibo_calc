package endpoint;

import dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserEndpoint {

    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userRepository.findById(id));
    }

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
//                              @RequestParam(value = "logout", required = false) String logout) {
//
//        ModelAndView model = new ModelAndView();
//        if (error != null) {
//            model.addObject("error", "Invalid username and password!");
//        }
//
//        if (logout != null) {
//            model.addObject("msg", "You've been logged out successfully.");
//        }
//        model.setViewName("login");
//
//        return model;
//
//    }
}
