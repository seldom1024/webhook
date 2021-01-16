package com.seldom.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seldom.objects.ApiResponse;
import com.seldom.objects.ResponseParameters;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * WebHookApiRequestException
 *
 * @author zhangqi
 * @date 2021/1/16 18:37
 * @since 1.0.0
 */
public class WebHookApiRequestException extends WebHookApiException {

    private static final Logger log = LoggerFactory.getLogger(WebHookApiRequestException.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String ERROR_DESCRIPTION_FIELD = "description";
    private static final String ERROR_CODE_FIELD = "error_code";
    private static final String PARAMETERS_FIELD = "parameters";

    private String apiResponse = null;
    private Integer errorCode = 0;

    private ResponseParameters parameters;

    public WebHookApiRequestException(String message) {
        super(message);
    }

    public WebHookApiRequestException(String message, JSONObject object) {
        super(message);
        apiResponse = object.getString(ERROR_DESCRIPTION_FIELD);
        errorCode = object.getInt(ERROR_CODE_FIELD);
        if (object.has(PARAMETERS_FIELD)) {
            try {
                parameters = OBJECT_MAPPER.readValue(object.getJSONObject(PARAMETERS_FIELD).toString(), ResponseParameters.class);
            } catch (IOException e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }
    }

    public WebHookApiRequestException(String message, ApiResponse<?> response) {
        super(message);
        apiResponse = response.getErrorDescription();
        errorCode = response.getErrorCode();
        parameters = response.getParameters();
    }

    public WebHookApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }


    public String getApiResponse() {
        return apiResponse;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public ResponseParameters getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        if (apiResponse == null) {
            return super.toString();
        } else if (errorCode == null) {
            return super.toString() + ": " + apiResponse;
        } else {
            return super.toString() + ": [" + errorCode + "] " + apiResponse;
        }
    }


}
