package ru.itis.sdkgenerator.data;

import org.springframework.http.HttpMethod;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class MethodInfo {
    private HttpMethod httpMethod;
    private String methodName;
    private Type responseBodyType;
    private List<MethodParameter> parameters;
    private List<MethodParameter> pathVars;
    private List<MethodParameter> queryParams;
    private MethodParameter bodyParameter;
    private boolean authenticationMethod;

    public boolean isWithBody() {
        return Arrays.asList(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH)
                .contains(httpMethod);
    }
}
