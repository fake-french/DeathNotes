package kojima.genius.deathnotes.controllers;

import kojima.genius.deathnotes.entities.Note;
import kojima.genius.deathnotes.entities.User;
import kojima.genius.deathnotes.repositories.NoteRepository;
import kojima.genius.deathnotes.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class NoteCreationController {

    NoteRepository noteRep;

    UserRepository userRep;

    NoteCreationController(NoteRepository noteRep, UserRepository userRep) {
        this.noteRep = noteRep;
        this.userRep = userRep;
    }

    @GetMapping("/createnote")
    String getPage(HttpServletRequest request, Model userData) {
        HttpSession session = request.getSession(false);
        User user;

        if(session != null && session.getAttribute("user") != null) {
            user = userRep.findByUsername( (String) session.getAttribute("user"));
            userData.addAttribute("username", user.getUsername());
            userData.addAttribute("logged", true);
        }else {
            userData.addAttribute("logged", false);
        }
        return "create-note";
    }

    @PostMapping("/createnote")
    String writeNote(Note note, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = userRep.findByUsername( (String) session.getAttribute("user"));

        user.getNotes().add(note);
        note.setUser(user);
        userRep.save(user);
        return "redirect:/";
    }
}
