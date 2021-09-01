package ru.itis.sdkgenerator.data;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class MethodInfo {
    private RequestMethod httpMethod;
    private String name;
    private String path;
    private Type responseBodyType;
    private List<MethodParameter> parameters;
    private List<MethodParameter> pathVars;
    private List<MethodParameter> queryParams;
    private MethodParameter bodyParameter;
    private boolean authenticationMethod;

    public boolean isWithBody() {
        return Arrays.asList(RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH)
                .contains(httpMethod);
    }

    public void addParameter(MethodParameter methodParameter) {
        parameters.add(methodParameter);
        if (methodParameter.getMethodParameterType().equals(MethodParameterType.QUERY_PARAMETER)) {
            queryParams.add(methodParameter);
        } else {
            if (methodParameter.getMethodParameterType().equals(MethodParameterType.PATH_VARIABLE)) {
                pathVars.add(methodParameter);
            } else {
                bodyParameter = methodParameter;
            }
        }
    }

    public RequestMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(RequestMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getResponseBodyType() {
        return responseBodyType;
    }

    public void setResponseBodyType(Type responseBodyType) {
        this.responseBodyType = responseBodyType;
    }

    public List<MethodParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<MethodParameter> parameters) {
        this.parameters = parameters;
    }

    public List<MethodParameter> getPathVars() {
        return pathVars;
    }

    public void setPathVars(List<MethodParameter> pathVars) {
        this.pathVars = pathVars;
    }

    public List<MethodParameter> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<MethodParameter> queryParams) {
        this.queryParams = queryParams;
    }

    public MethodParameter getBodyParameter() {
        return bodyParameter;
    }

    public void setBodyParameter(MethodParameter bodyParameter) {
        this.bodyParameter = bodyParameter;
    }

    public boolean isAuthenticationMethod() {
        return authenticationMethod;
    }

    public void setAuthenticationMethod(boolean authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
