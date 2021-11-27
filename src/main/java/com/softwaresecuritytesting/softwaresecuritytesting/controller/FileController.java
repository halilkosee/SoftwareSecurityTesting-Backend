package com.softwaresecuritytesting.softwaresecuritytesting.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.softwaresecuritytesting.softwaresecuritytesting.model.FileInfo;
import com.softwaresecuritytesting.softwaresecuritytesting.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    FilesStorageService storageService;


    @GetMapping("")
    public String homepage(Model model) {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());
        model.addAttribute("listFiles", fileInfos);
        return "welcome";
    }


    @PostMapping("/upload")
    public Object uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        String message = "";
        try {
            storageService.save(file);
            attributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + '!');
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return "redirect:/file";
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            attributes.addFlashAttribute("message", "Could not upload the file: " + file.getOriginalFilename() + '!');

            return "redirect:/file";
        }
    }

    @PostMapping("/analyse")
    public String fileAnalyse() {
        System.out.println("geldiii");
        return "redirect:/file";
    }
    @PostMapping("/delete")
    public String delete() {
        storageService.deleteByName("deletedfilename");
        return "redirect:/file";
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();

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
