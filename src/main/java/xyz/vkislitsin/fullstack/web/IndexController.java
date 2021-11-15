package xyz.vkislitsin.fullstack.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.List;

import xyz.vkislitsin.fullstack.services.ICasinoService;

@Controller
public class IndexController {

    private final ICasinoService casinoService;

    public IndexController(ICasinoService casinoService) {
        this.casinoService = casinoService;
    }

    @GetMapping({"/"})
    public String enter(Model model, HttpSession session, @RequestParam(required = false) Boolean showMeTheMoney) {
        if (session.isNew() ||
                session.getAttribute("username") == null ||
                session.getAttribute("username").toString().isEmpty()) {
            model.addAttribute("isNew", true);
        } else {
            // Hack
            Integer credits = (Integer) session.getAttribute("credits");
            if (showMeTheMoney != null) {
                credits += 100;
                session.setAttribute("credits", credits);
            }
            model.addAttribute("credits", credits);
            model.addAttribute("username", session.getAttribute("username"));
            model.addAttribute("randomComponents", casinoService.getSpinResults());
        }
        return "index";
    }

    @GetMapping({"/givememymoney"})
    public String giveMeMyMoney(Model model, HttpSession session) {
        if ((Integer) session.getAttribute("credits") >= 10) {
            model.addAttribute("credits", session.getAttribute("credits"));
            model.addAttribute("username", session.getAttribute("username"));
        }
        return "out";
    }

    @GetMapping({"/quit"})
    public RedirectView quit(Model model, HttpSession session) {
        session.invalidate();
        return new RedirectView("/", false, false, false);
    }

    @PostMapping(value = "/", consumes = {"application/x-www-form-urlencoded"})
    public View saveName(Model model, HttpSession session, @RequestParam String userName, @RequestParam(required = false) Integer credits) {
        session.setAttribute("username", userName);
        if (credits != null) {
            session.setAttribute("credits", credits);
        }
        return new RedirectView("/", false, false, false);
    }

    @PostMapping(value = "/spin", consumes = {"application/x-www-form-urlencoded"})
    public String spinAndProceed(Model model, HttpSession session) {
        Integer credits = (Integer) session.getAttribute("credits");

        List<String> spinResult = casinoService.spinWithCredits(credits);
        Integer creditsResult = casinoService.countCredits(spinResult, credits);

        if (creditsResult >= 10) {
            model.addAttribute("targetHit", true);
        } else {
            model.addAttribute("targetHit", false);
        }

        Integer finalAmount = credits + creditsResult;

        if (finalAmount > 0) {
            session.setAttribute("credits", finalAmount);
            model.addAttribute("credits", finalAmount);
            model.addAttribute("username", session.getAttribute("username"));
            model.addAttribute("randomComponents", spinResult);
        } else {
            model.addAttribute("username", session.getAttribute("username"));
            model.addAttribute("credits", 0);
        }

        return "index";
    }
}
