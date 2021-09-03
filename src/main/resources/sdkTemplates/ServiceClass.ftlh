<#ftl output_format="plainText">
package ${package}.rest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import ${package}.rest.client.*;
import ${package}.rest.client.model.*;

import java.io.IOException;
import java.util.List;

public class ${name}Service {
    private final ${appName}Client client;

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public ${name}Service(${appName}Client client){
        this.client = client;
        this.okHttpClient = client.getOkHttpClient();
        this.objectMapper = client.getObjectMapper();
    }

    <#list methods as method>
        public ${method.responseBodyType} ${method.name}(<#list method.parameters as param>${param.type} ${param.javaParameterName}<#sep>, </#sep></#list>) {
            try {
                String urlString = client.getBaseUrl() + "${method.path}";

                <#list method.pathVars>
                    urlString = urlString
                    <#items as pathVar>
                        .replace("${pathVar.requestName}", String.valueOf(${pathVar.javaParameterName}))
                    </#items>;
                 </#list>

                HttpUrl.Builder urlBuilder = HttpUrl.parse(urlString).newBuilder();
                <#list method.queryParams as queryParam>
                    urlBuilder.addQueryParameter("${queryParam.requestName}",${queryParam.javaParameterName});
                </#list>
                <#if method.withBody>
                    String jsonBody = objectMapper.writeValueAsString(${method.bodyParameter.javaParameterName});
                </#if>
                Request request = new Request.Builder()
                    .url(urlBuilder.build().url())
                    .${method.httpMethodString}(<#if method.withBody>RequestBody.create(jsonBody, MediaType.parse("application/json"))</#if>)
                    .build();
                Call call = okHttpClient.newCall(request);
                Response response = call.execute();
                if (response.isSuccessful()) {
                <#if method.returnTypeNotVoid>
                    String body = response.body().string();
                    return objectMapper.readValue(body, new TypeReference<>() {});
                </#if>
                } else {
                    String body = response.body().string();
                    throw objectMapper.readValue(body, ${requestErrorClassName}.class)
                }
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            } catch (IOException ioException) {
                throw new IllegalStateException(ioException);
            }
        }
    </#list>

}