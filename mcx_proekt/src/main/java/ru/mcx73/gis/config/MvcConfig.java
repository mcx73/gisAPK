package ru.mcx73.gis.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
Для страниц, которые никак не обрабатываются сервером, а просто возвращают страницу
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    //Для раздачи файлов
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/docs").setViewName("docs");
        registry.addViewController("/mfc").setViewName("mfc");

    }

    //служит для раздачи всех файлов
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/home/vi4jesus/IdeaProjects/gis3/uploads/**")
                .addResourceLocations("/home/vi4jesus/IdeaProjects/gis3/uploads/");
    }





}
