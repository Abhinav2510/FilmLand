package org.filmland.catalog.configs;

import org.filmland.catalog.entity.CategorySubscriptions;
import org.filmland.catalog.web.dto.SubscribedCategoryResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
/**
 * GlobalConfig
 * Provides global configs which were not big enough to have their separate configuration classes
 */
public class GlobalConfig {

    /**
     * provide BCryptPasswordEncoder which will be used to Authenticate users by spring security
     * @return BCryptPasswordEncoder
     */
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * ModelMapper bean used by web layer to map Entities to DTO classes with some custom mapping to handle non-obvious scenarios
     * @return modelMapper instance
     */
    @Bean
    ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<CategorySubscriptions, SubscribedCategoryResponseDTO>() {
            @Override
            protected void configure() {
                map().setStartDate(source.getSubscribedOn());
                map().setName(source.getFilmCategory().getName());
            }
        });
        return modelMapper;
    }


}

