package com.seldom.interfaces;

import com.seldom.exceptions.WebHookApiValidationException;

/**
 * Interface that can be implemented by objects that knows how to validate their fields
 *
 * @author zhangqi
 * @date 2021/1/16 18:55
 * @since 1.0.0
 */
public interface Validable {
    /**
     * Validates that mandatory fields are filled and optional objects
     * @throws WebHookApiValidationException If any mandatory field is empty
     */
    void validate() throws WebHookApiValidationException;
}
