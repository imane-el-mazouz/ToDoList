//package com.gestion.config;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.util.StdDateFormat;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.gestion.model.CustomOffsetDateTimeDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.OffsetDateTime;
//import java.util.TimeZone;
//
////@Configuration
////public class JacksonConfig {
////    @Bean
////    public ObjectMapper objectMapper() {
////        ObjectMapper mapper = new ObjectMapper();
////        JavaTimeModule module = new JavaTimeModule();
////        mapper.registerModule(module);
////        return mapper;
////    }
////}
//
//@Configuration
//public class JacksonConfig {
////    @Bean
////    public ObjectMapper objectMapper() {
////        ObjectMapper mapper = new ObjectMapper();
////        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
////        mapper.registerModule(new JavaTimeModule());
////        mapper.setDateFormat(new StdDateFormat().withTimeZone(TimeZone.getDefault()));
////        return mapper;
////    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        JavaTimeModule module = new JavaTimeModule();
//        module.addDeserializer(OffsetDateTime.class, new CustomOffsetDateTimeDeserializer());
//        objectMapper.registerModule(module);
//        return objectMapper;
//    }
//
//
//}
package com.ToDoList.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gestion.model.CustomOffsetDateTimeDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Enregistrement du module JavaTimeModule pour la gestion des dates/temps
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(OffsetDateTime.class, new CustomOffsetDateTimeDeserializer());
        objectMapper.registerModule(module);

        // Désactivation de FAIL_ON_EMPTY_BEANS pour éviter l'erreur de sérialisation avec des beans vides
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        return objectMapper;
    }
}
