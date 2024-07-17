package project.carservice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import project.carservice.model.dto.CarDTO;
import project.carservice.model.user.AppUserDetails;
import project.carservice.service.UserHelperService;
import project.carservice.service.session.AppUserDetailsService;
import project.carservice.service.CarService;

import java.util.List;

@Controller
public class HomeControllerImpl implements HomeController {
    private final AppUserDetailsService appUserDetailsService;
    private final UserHelperService userHelperService;
    private final CarService carService;


    public HomeControllerImpl(AppUserDetailsService appUserDetailsService, UserHelperService userHelperService, CarService carService) {
        this.appUserDetailsService = appUserDetailsService;
        this.userHelperService = userHelperService;

        this.carService = carService;
    }


    @Override
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof AppUserDetails appUserDetails) {
            model.addAttribute("welcomeMessage", appUserDetails.getFullName());
        } else {
            model.addAttribute("welcomeMessage", "Anonymous");
        }

        return "home";
    }

    @Override
    public String garage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof AppUserDetails appUserDetails) {

            List<CarDTO> myCars = carService.allOwnedBy(appUserDetails.getUsername());
            model.addAttribute("myCars", myCars);}

            return "garage";
        }

        @Override
        public String profile (Model model){
            return "my-profile";
        }

    }
