package br.com.mbarbosa.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.mbarbosa.blog.controllers"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageForGET())
                .globalResponseMessage(RequestMethod.POST, responseMessageForPOST());
    }

    private List<ResponseMessage> responseMessageForPOST() {
        return new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder()
                    .code(201)
                    .message("Recurso criado com sucesso.")
                    .responseModel(new ModelRef("Sucesso"))
                    .build());
        }};
    }

    private List<ResponseMessage> responseMessageForGET()
    {
        return new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder()
                    .code(500)
                    .message("Erro interno do servidor.")
                    .responseModel(new ModelRef("Error"))
                    .build());
            add(new ResponseMessageBuilder()
                    .code(404)
                    .message("Recurso não encontrado.")
                    .build());
        }};
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API de gerenciamento de posts.")
                .description("\"REST API para gerenciamento de posts do blog.\"")
                .version("0.0.1")
                .license("Apache License Version 2.0")
                .licenseUrl("\"https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Marcos Barbosa", "https://https://github.com/marcosbarbosadev", "barbosa.mg@gmail.com"))
                .build();
    }



}
