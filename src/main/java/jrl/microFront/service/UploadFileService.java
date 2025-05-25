package jrl.microFront.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface UploadFileService {
    public Resource load(String file) throws MalformedURLException;
    public String copy(MultipartFile file) throws IOException;
    public boolean delete(String file);
    public void deleteAll();
    public void init() throws IOException;
}
