package com.labinvent.telegram_notifier.dto;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class EurekaResponse {
    private Applications applications;

    public String message() {
        return applications.getApplication().stream().map(application -> {
            String name = application.getName();
            String status = application.instance.stream().map(Instance::getStatus).toList().get(0);
            return "%s - %s".formatted(name, status);
        }).sorted().collect(Collectors.joining("\n"));
    }
}

@Data
class Applications {
    private List<Application> application;
}

@Data
class Application {
    public String name;
    public List<Instance> instance;
}

@Data
class Instance {
    public String status;
}