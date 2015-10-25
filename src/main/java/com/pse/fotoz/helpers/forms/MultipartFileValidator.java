/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.helpers.Configuration.ConfigurationHelper;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 * Validates a MultiPartFile
 *
 * @author Gijs
 */
public class MultipartFileValidator {

    /**
     * Method to validate a MultiPartFile, in this case a picture
     *
     * @param file MultiPartFile to be validated
     * @param errormessages messages to be returned on failure of validation
     * @return List of errormessages. Empty when validation succeeded
     */
    public static List<String> validate(MultipartFile file,
            Map<String, String> errormessages) {

        List<String> errors = new ArrayList<>();
        boolean validated = true;

        // empty uploads		
        if (file.isEmpty()) {
            errors.add(errormessages.get("error_multipartfile_emptyfile"));
            validated = false;
        }

        // empty file names
        if (validated && file.getOriginalFilename().isEmpty()) {
            errors.add(errormessages.get("error_multipartfile_emptyfilename"));
            validated = false;
        }

        // whitelist-based validation of file extensions
        if (validated) {
            String lowerCaseFileName = file.getOriginalFilename().toLowerCase();
            boolean isAllowedExtension = false;

            for (String allowedExtension : ConfigurationHelper.getExtensionwhitelist()) {
                if (lowerCaseFileName.endsWith(allowedExtension)) {
                    isAllowedExtension = true;
                    break;
                }
            }

            if (!isAllowedExtension) {
                errors.add(errormessages.get("error_multipartfile_wrongextension"));
                validated = false;
            }
        }

        //check filesize in kb
        if (validated && (file.getSize() / 1000)
                > ConfigurationHelper.getMaxmultipartsizeinkb()) {
            errors.add(
                MessageFormat.format(
                        errormessages.get("error_multipartfile_sizeexceeded"),
                        ConfigurationHelper.getMaxmultipartsizeinkb()));
        }

        return errors;
    }
}
