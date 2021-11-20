package com.softwaresecuritytesting.softwaresecuritytesting.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.softwaresecuritytesting.softwaresecuritytesting.model.FileInfo;
import com.softwaresecuritytesting.softwaresecuritytesting.service.FilesStorageService;
import com.softwaresecuritytesting.softwaresecuritytesting.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FilesController {

    @Autowired
    FilesStorageService storageService;


    @GetMapping("/fileUpload")
    public String homepage() {
        return "fileUpload";
    }


    @PostMapping("/upload")
    public Object uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        String message = "";
        try {
            storageService.save(file);
            attributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + '!');
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return "redirect:/";
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            attributes.addFlashAttribute("message", "Could not upload the file: " + file.getOriginalFilename() + '!');

            return "redirect:/";
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
