package com.itis.spring_boot.active_lead.annotation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerConfiguration {

    private final Configuration configuration;
    public FreemarkerConfiguration() {
        configuration= new Configuration(Configuration.VERSION_2_3_28);  configuration.setClassForTemplateLoading(this.getClass(),  "/templates");
        configuration.setDefaultEncoding("UTF-8");

        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
    }
    public void createHtmlFromTemplate(Form form, String path) {  try {
        Template template = configuration.getTemplate("form.ftlh");
        FileWriter writer = new FileWriter(new File(path));
        Map<String, Object> map = new HashMap<>();
        map.put("form", form);
        template.process(map, writer);
    } catch (IOException | TemplateException e) {
        throw new IllegalArgumentException(e);
    }
    }
}

