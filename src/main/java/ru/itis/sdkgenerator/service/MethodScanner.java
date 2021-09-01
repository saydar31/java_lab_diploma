package ru.itis.sdkgenerator.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.itis.sdkgenerator.annotations.AuthenticationMethod;
import ru.itis.sdkgenerator.data.MethodInfo;
import ru.itis.sdkgenerator.data.MethodParameter;
import ru.itis.sdkgenerator.data.MethodParameterType;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

@Component
public class MethodScanner {
    public MethodInfo scan(Method method, String urlPrefix) {
        if (!isRequestMapped(method)) {
            throw new RuntimeException("Error: SdkMapping is present on method that has no mapping" + method);
        }
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setHttpMethod(probeHttpMethod(method));
        methodInfo.setPath(getPath(method, urlPrefix));
        methodInfo.setName(method.getName());
        methodInfo.setResponseBodyType(method.getReturnType());
        for (Parameter parameter : method.getParameters()) {
            if (isParameter(parameter)) {
                methodInfo.addParameter(getMethodParameter(parameter));
            }
        }

        methodInfo.setAuthenticationMethod(method.isAnnotationPresent(AuthenticationMethod.class));

        methodInfo.setResponseBodyType(getRestEntityReturnType(method));

        return methodInfo;
    }

    private String getPath(Method method, String urlPrefix) {
        for (Class<? extends Annotation> mappingAnnotationsClass : MAPPING_ANNOTATIONS_CLASSES) {
            if (method.isAnnotationPresent(mappingAnnotationsClass)) {
                Annotation annotation = method.getAnnotation(mappingAnnotationsClass);
                try {
                    Method annotationValueMethod = mappingAnnotationsClass.getMethod("value");
                    Object annotationValue = annotationValueMethod.invoke(annotation);
                    if (annotationValue.getClass().isArray()) {
                        Object[] annotationValueArray = (Object[]) annotationValue;
                        if (annotationValueArray.length > 0) {
                            return urlPrefix + annotationValueArray[0].toString();
                        } else {
                            return urlPrefix;
                        }
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        return null;
    }

    private Type getRestEntityReturnType(Method method) {
        ParameterizedType genericReturnType = (ParameterizedType) method.getGenericReturnType();
        return genericReturnType.getActualTypeArguments()[0];
    }

    private MethodParameter getMethodParameter(Parameter parameter) {
        MethodParameter methodParameter = new MethodParameter();
        methodParameter.setJavaParameterName(parameter.getName());
        methodParameter.setType(parameter.getType());

        if (parameter.isAnnotationPresent(RequestBody.class)) {
            methodParameter.setMethodParameterType(MethodParameterType.REQUEST_BODY);
        }

        if (parameter.isAnnotationPresent(RequestParam.class)) {
            methodParameter.setMethodParameterType(MethodParameterType.QUERY_PARAMETER);
            String parameterName = parameter.getAnnotation(RequestParam.class).value();
            if (parameterName.equals("")) {
                parameterName = parameter.getName();
            }
            methodParameter.setRequestName(parameterName);
        }

        if (parameter.isAnnotationPresent(PathVariable.class)) {
            methodParameter.setMethodParameterType(MethodParameterType.PATH_VARIABLE);
            String parameterName = parameter.getAnnotation(PathVariable.class).value();
            if (parameterName.equals("")) {
                parameterName = parameter.getName();
            }
            methodParameter.setRequestName(parameterName);
        }

        return methodParameter;
    }

    private boolean isParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestBody.class)
                || parameter.isAnnotationPresent(RequestParam.class)
                || parameter.isAnnotationPresent(PathVariable.class);
    }

    private RequestMethod probeHttpMethod(Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            return method.getAnnotation(RequestMapping.class).method()[0];
        } else {
            for (Class<? extends Annotation> specifiedMappingAnnotation : SPECIFIED_MAPPING_ANNOTATIONS) {
                RequestMethod methodFromSpecifiedAnnotation = getMethodFromSpecifiedAnnotation(method, specifiedMappingAnnotation);
                if (methodFromSpecifiedAnnotation != null) {
                    return methodFromSpecifiedAnnotation;
                }
            }

        }
        throw new RuntimeException("No http method specified for " + method);
    }

    RequestMethod getMethodFromSpecifiedAnnotation(Method method, Class<? extends Annotation> annotationClass) {
        if (method.isAnnotationPresent(annotationClass)) {
            return annotationClass.getAnnotation(RequestMapping.class).method()[0];
        } else {
            return null;
        }
    }

    private static final List<Class<? extends Annotation>> MAPPING_ANNOTATIONS_CLASSES = Arrays.asList(
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class,
            PutMapping.class,
            DeleteMapping.class
    );

    private static final List<Class<? extends Annotation>> SPECIFIED_MAPPING_ANNOTATIONS = Arrays.asList(
            GetMapping.class,
            PostMapping.class,
            PutMapping.class,
            DeleteMapping.class
    );


    private boolean isRequestMapped(Method method) {
        for (Class<? extends Annotation> mappingAnnotationsMapping : MAPPING_ANNOTATIONS_CLASSES) {
            if (method.isAnnotationPresent(mappingAnnotationsMapping)) {
                return true;
            }
        }
        return false;
    }
}
