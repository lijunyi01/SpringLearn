package allcom.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Controller
public class FileUploadController {
    private final String [] extensionForbidden = {"exe", "js", "dmg","mp3"};

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public
    @ResponseBody
    String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    String handleFileUpload(@RequestParam("file") MultipartFile file) {
        String name="";
        String fileExtension="";
        if (!file.isEmpty()) {
            name = file.getOriginalFilename();
            fileExtension = FilenameUtils.getExtension(name);
            if(!ArrayUtils.contains(extensionForbidden, fileExtension)) {
                try {
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File(name)));
                    stream.write(bytes);
                    stream.close();
                    return "You successfully uploaded " + name + "!";
                } catch (Exception e) {
                    return "You failed to upload " + name + " => " + e.getMessage();
                }
            }else{
                return "fileExtension forbidden,name: " + name ;
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

    @RequestMapping(value = "/batchupload", method = RequestMethod.POST)
    public
    @ResponseBody
    String batchFileUpload(HttpServletRequest request) {
        String name="";
        String fileExtension="";
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        for (int i =0; i< files.size(); ++i) {
            MultipartFile file = files.get(i);
            if (!file.isEmpty()) {
                name = file.getOriginalFilename();
                fileExtension = FilenameUtils.getExtension(name);
                if(!ArrayUtils.contains(extensionForbidden, fileExtension)) {
                    try {
                        byte[] bytes = file.getBytes();
                        BufferedOutputStream stream =
                                new BufferedOutputStream(new FileOutputStream(new File(name)));
                        stream.write(bytes);
                        stream.close();
                    } catch (Exception e) {
                        return "You failed to upload " + name + " => " + e.getMessage();
                    }
                }else{
                    return "fileExtension forbidden,name: " + name ;
                }
            } else {
                return "You failed to upload " + name + " because the file was empty.";
            }
        }
        return "upload successful";

    }



}
