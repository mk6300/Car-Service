package project.carservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.carservice.model.dto.AddDTOs.RegisterUserDTO;
import project.carservice.service.UserService;

@Controller
public class UserControllerImpl implements UserController {

private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerUserDTO")
    public RegisterUserDTO registerUserDTO() {
        return new RegisterUserDTO();
    }

    @Override
    public String login(Model model) {
        return "login";
    }

    @Override
    public String register(Model model) {

        return "register";
    }

    @Override
    public String registerConfirm(RegisterUserDTO registerUserDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())) {
            result.addError(
                    new FieldError(
                            "differentConfirmPassword",
                            "confirmPassword",
                            "Passwords must be the same."));
        }

        if (result.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("registerUserDTO", registerUserDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.registerUserDTO", result);

            return "register";
        }

        this.userService.register(registerUserDTO);
        return "redirect:/";
    }

}