package com.cfysu.springboot.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConfigDO {

    @Value("${test.key.name}")
    private String key;

    @Override
    public String toString() {
        return "ConfigDO:" + key;
    }
}
