package kojima.genius.deathnotes.controllers;

import kojima.genius.deathnotes.entities.User;
import kojima.genius.deathnotes.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainMenuController {

    @Autowired
    NoteRepository noteRep;

    @GetMapping("/")
    public String getPage(HttpServletRequest request, Model userDataAndNotes) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if(session != null && session.getAttribute("user") != null) {
            userDataAndNotes.addAttribute("username", user.getUsername());
            userDataAndNotes.addAttribute("logged", true);
        }else {
            userDataAndNotes.addAttribute("logged", false);
        }
        userDataAndNotes.addAttribute("notes", user);
        return "menu";
    }
}
