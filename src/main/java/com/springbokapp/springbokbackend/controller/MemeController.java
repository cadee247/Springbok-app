package com.springbokapp.springbokbackend.controller;

import com.springbokapp.springbokbackend.model.Comment;
import com.springbokapp.springbokbackend.model.Meme;
import com.springbokapp.springbokbackend.repository.CommentRepository;
import com.springbokapp.springbokbackend.repository.MemeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/memes")
public class MemeController {

    @Autowired
    private MemeRepository memeRepo;

    @Autowired
    private CommentRepository commentRepo;

    // Upload folder in project root: demoAspringbok-backend/uploads/memes
    private final Path UPLOAD_DIR = Paths.get("uploads/memes");

    // ============================
    // GET ALL MEMES
    // ============================
    @GetMapping
    public String getMemes(Model model, HttpServletRequest request) {
        List<Meme> memes = memeRepo.findAll();
        model.addAttribute("memes", memes);

        // Inject CSRF token for Thymeleaf forms
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);

        return "memes"; // Thymeleaf template memes.html
    }

    // ============================
    // UPLOAD MEME (WITH IMAGE)
    // ============================
    @PostMapping("/upload")
    public String uploadMeme(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("username") String username,
                             @RequestParam("image") MultipartFile image) throws IOException {

        if (image.isEmpty()) {
            throw new IOException("No image file selected");
        }

        // Sanitize filename
        String originalName = image.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
        String fileName = System.currentTimeMillis() + "_" + originalName;

        // Ensure folder exists
        Files.createDirectories(UPLOAD_DIR);

        // Save file to uploads/memes
        Path filePath = UPLOAD_DIR.resolve(fileName);
        Files.write(filePath, image.getBytes());

        // Save meme info to database
        Meme meme = new Meme();
        meme.setTitle(title);
        meme.setDescription(description);
        meme.setUsername(username);
        meme.setImageUrl("/uploads/memes/" + fileName); // must match WebConfig path
        meme.setLaughs(0);
        memeRepo.save(meme);

        return "redirect:/memes";
    }

    // ============================
    // ADD LAUGH (LIKE)
    // ============================
    @PostMapping("/{id}/laugh")
    @ResponseBody
    public String laughAtMeme(@PathVariable Long id) {
        return memeRepo.findById(id)
                .map(meme -> {
                    meme.setLaughs(meme.getLaughs() + 1);
                    memeRepo.save(meme);
                    return "success";
                })
                .orElse("Meme not found");
    }

    // ============================
    // DELETE MEME (ADMIN ONLY)
    // ============================
    @PostMapping("/delete/{id}")
    public String deleteMeme(@PathVariable Long id) throws IOException {
        Meme meme = memeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meme not found"));

        // Delete image file
        String filename = Paths.get(meme.getImageUrl()).getFileName().toString();
        Path filePath = UPLOAD_DIR.resolve(filename);
        Files.deleteIfExists(filePath);

        memeRepo.delete(meme);
        return "redirect:/memes";
    }

    // ============================
    // EDIT MEME (ADMIN ONLY)
    // ============================
    @GetMapping("/edit/{id}")
    public String editMeme(@PathVariable Long id, Model model, HttpServletRequest request) {
        Meme meme = memeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meme not found"));

        model.addAttribute("meme", meme);

        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);

        return "edit-meme"; // Thymeleaf template edit-meme.html
    }

    @PostMapping("/update/{id}")
    public String updateMeme(@PathVariable Long id,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description) {
        Meme meme = memeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meme not found"));
        meme.setTitle(title);
        meme.setDescription(description);
        memeRepo.save(meme);
        return "redirect:/memes";
    }

    // ============================
    // ADD COMMENT TO MEME
    // ============================
    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam("username") String username,
                             @RequestParam("content") String content) {
        Meme meme = memeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meme not found"));

        Comment comment = new Comment();
        comment.setUsername(username);
        comment.setContent(content);
        comment.setMeme(meme);

        commentRepo.save(comment);
        return "redirect:/memes";
    }

    // ============================
    // GET COMMENTS FOR A MEME
    // ============================
    @GetMapping("/{id}/comments")
    public String getComments(@PathVariable Long id, Model model) {
        Meme meme = memeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meme not found"));

        List<Comment> comments = commentRepo.findAllByMeme(meme);
        model.addAttribute("meme", meme);
        model.addAttribute("comments", comments);

        return "comments"; // Thymeleaf template for comments
    }
}
