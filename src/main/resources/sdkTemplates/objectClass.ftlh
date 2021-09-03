package ${package}.rest.client.model;

public class ${className} <#if isErrorObject> extends RuntimeException</#if>{

<#list fields as field>
    private ${fullTypeName} ${name};
</#list>


public ${className}() {
}

<#list fields as field>
    public ${fullTypeName} ${getterName}(){
        return ${name};
    }
    public void ${setterName}(${fullTypeName} ${name}) {
        this.${name} = ${name};
    }
</#list>

}
