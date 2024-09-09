package org.buaa.scholar.service;

import org.buaa.scholar.utils.Response;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IProjectService {
    ResponseEntity<Response<Map<String, Object>>> getProjectListByKeyword(String keyword, String page, String size);
}
