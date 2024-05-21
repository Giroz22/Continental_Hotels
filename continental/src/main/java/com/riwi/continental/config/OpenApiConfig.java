package com.riwi.continental.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Api  for administration hotels and it's entityes", version = "1.0", description = "Documentación api de administración de empresas y vacantes"))
public class OpenApiConfig {
    
}
