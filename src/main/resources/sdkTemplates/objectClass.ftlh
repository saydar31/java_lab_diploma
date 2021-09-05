package ${package}.rest.client.model;

public class ${className} <#if isErrorObject> extends RuntimeException</#if>{

<#list properties as property>
    private ${property.propertyType} ${property.name};
</#list>


public ${className}() {
}

<#list properties as property>
    public ${property.propertyType} ${property.readMethod.name}(){
    return ${property.name};
    }
    public void ${property.writeMethod.name}(${property.propertyType}  ${property.name}) {
    this.${property.name} = ${property.name};
    }
</#list>

}
