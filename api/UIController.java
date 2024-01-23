package homework3.api;

import homework3.model.Book;
import homework3.model.Issue;
import homework3.services.UIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class UIController {

    private final UIService uiService;

    @GetMapping("/books")
    public String getBooks(Model model) {
        List<String> books = uiService.getBookStrings();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/readers")
    public String getReaders(Model model) {
        List<String> readers = uiService.getReaderStrings();
        model.addAttribute("readers", readers);
        return "readers";
    }

    @GetMapping("/issues")
    public String getIssues(Model model) {
        List<List<String>> issues = uiService.getIssues();
        model.addAttribute("issues", issues);
        return "issues";
    }

    @GetMapping("/reader/{id}")
    public String getIssuesOfReader(Model model, @PathVariable long id) {
        String name = uiService.getReaderName(id);
        List<String> books = uiService.getBookStringsOfReader(id);
        model.addAttribute("name", name);
        model.addAttribute("books", books);
        return "readerBooks";
    }
}
