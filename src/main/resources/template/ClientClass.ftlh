package ${package}.rest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.*;
import ${package}.rest.client.model.*;
import ${package}.rest.client.service.*;

import java.io.IOException;

public class ${serviceName}Client {
private final String BASE_URL = "${baseUrl}";
private final OkHttpClient okHttpClient = new OkHttpClient();
private final ObjectMapper objectMapper = new ObjectMapper();
private String token;

private static final String AUTHENTICATION_SUFFIX = "${autheticationUrl}";

public void authenticate(String login, String password) {
${authenticationCredentialsClassName} authenticationCredentials = new ${authenticationCredentialsClassName}(login, password);
try {
String body = objectMapper.writeValueAsString(authenticationCredentials);
Request request = new Request.Builder()
.url(BASE_URL + AUTHENTICATION_SUFFIX)
.post(RequestBody.create(body, MediaType.parse("application/json")))
.build();
Call call = okHttpClient.newCall(request);
Response response = call.execute();
if (response.isSuccessful()){
${tokenWrapperClassName} tokenWrapper = objectMapper.readValue(response.body().string(), ${tokenWrapperClassName}.class);
this.token = tokenWrapper.${getTokenMethodName}();
} else {
else {
String body = response.body().string();
throw objectMapper.readValue(body, ${requestErrorClassName}.class)
}
}
} catch (JsonProcessingException e) {
throw new IllegalArgumentException(e);
} catch (IOException ioException) {
throw new IllegalStateException(ioException);
}
}


public CacheClient(String baseUrl) {
this.BASE_URL = baseUrl;
objectMapper.registerModule(new JavaTimeModule());
objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
}


public String getBaseUrl() {
return BASE_URL;
}

public OkHttpClient getOkHttpClient() {
return okHttpClient;
}

public ObjectMapper getObjectMapper() {
return objectMapper;
}

public CacheService getCacheService() {
return new CacheService(this);
}

<#list services as service>
public ${service.name}Service get ${service.name}Service() {
return new  ${service.name}Service(this);
}
</#list>


public String getToken() {
return token;
}
}
