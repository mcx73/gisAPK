package ru.mcx73.gis.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mcx73.gis.entity.Docs;
import ru.mcx73.gis.entity.User;
import ru.mcx73.gis.repository.DocsRepository;
import ru.mcx73.gis.service.FileService;
import ru.mcx73.gis.service.MediaTypeUtils;
import ru.mcx73.gis.service.RoleServiceImpl;
import ru.mcx73.gis.service.UserService;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
public class FileController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    DocsRepository docsRepository;

    private static final String DIRECTORY = "/home/vi4jesus/IdeaProjects/gis3/uploads/";

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "/downloadfile")
    public ResponseEntity<InputStreamResource> downloadFile1(
            @RequestParam(required = true, defaultValue = "" ) Long docId) throws IOException {
        Optional<Docs> doc = docsRepository.findById(docId);
        Docs docs = doc.orElse(new Docs());
        String fileName = docs.getFilename();
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        String realDirectory = DIRECTORY+"/"+docs.getAuthorName();
        File file = new File(realDirectory + "/" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/docs")
    public String main(Model model) {
        Iterable<Docs> docs = docsRepository.findAll();

        model.addAttribute("docum", docs);

        return "docs";
    }
    @PostMapping("/docs/delete")
    public String deleteDocs(@RequestParam(required = true, defaultValue = "" ) Long docId,
                             @RequestParam(required = true, defaultValue = "" ) String action,
                             Model model)
    {
        if (action.equals("delete")){
            fileService.deleteFile(docId);
        }
        return "redirect:/docs";
    }
    @PostMapping("/docs")
    public String addDocs(@AuthenticationPrincipal User user,
                          @RequestParam("file") MultipartFile file,
                          Model model) throws IOException {

        Docs doc = new Docs(user);

        String newUploadPath = "";

        newUploadPath = DIRECTORY+"/"+user.getUsername().toString();

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(newUploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "~" + fileService.toTranslit(file.getOriginalFilename());
            File transerFile = new File(newUploadPath + "/" + resultFilename);
            file.transferTo(transerFile);
            doc.setFilename(resultFilename);
        }
        docsRepository.save(doc);
        Iterable<Docs> docum = docsRepository.findAll();
        model.addAttribute("docum", docum);

        return "docs";
    }
}
