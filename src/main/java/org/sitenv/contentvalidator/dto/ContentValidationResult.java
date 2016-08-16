package org.sitenv.contentvalidator.dto;

import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;

/**
 * Created by Brian on 2/14/2016.
 */
public class ContentValidationResult {
    private String message;
    private ContentValidationResultLevel contentValidationResultLevel;
    private String xpath;
    private String lineNumber;

    public ContentValidationResult(String message, ContentValidationResultLevel contentValidationResultLevel, String xpath, String lineNumber) {
        this.message = message;
        this.contentValidationResultLevel = contentValidationResultLevel;
        this.xpath = xpath;
        this.lineNumber = lineNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContentValidationResultLevel getContentValidationResultLevel() {
        return contentValidationResultLevel;
    }

    public void setContentValidationResultLevel(ContentValidationResultLevel contentValidationResultLevel) {
        this.contentValidationResultLevel = contentValidationResultLevel;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }
}
