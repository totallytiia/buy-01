package com.tiia.buy_01.utils;

import com.tiia.buy_01.exceptions.DataNotValidatedException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Component
public class FileValidator {

    public boolean validateFile(MultipartFile file) {
        boolean returnValue = false;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (isSupportedExtension(extension)) {
            returnValue = true;
        }
        else {
            Error error = new Error("invalid file data");
            throw new DataNotValidatedException(error);
        }
        return returnValue;
    }


    public boolean isSupportedExtension(String extension) {
        boolean returnValue = false;
        Optional<String> extensionOptional = Optional.ofNullable(extension);
        if (extensionOptional.isPresent()) {
            if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
                returnValue = true;
            }
        }
        return returnValue;
    }
}
