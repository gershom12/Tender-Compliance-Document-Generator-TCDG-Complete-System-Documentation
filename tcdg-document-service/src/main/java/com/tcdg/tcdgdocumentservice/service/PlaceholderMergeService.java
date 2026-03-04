package com.tcdg.tcdgdocumentservice.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlaceholderMergeService {

    public String merge(String template,  Map<String, Object> data) {

        String result = template;

        for (Map.Entry<String, Object> entry : data.entrySet()) {

            String placeholder = "{{" + entry.getKey() + "}}";
            result = result.replace(placeholder,
                    String.valueOf(entry.getValue()));
        }

        return result;
    }
}