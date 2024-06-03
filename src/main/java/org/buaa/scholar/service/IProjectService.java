package org.buaa.scholar.service;

import org.buaa.scholar.utils.Response;
import org.springframework.http.ResponseEntity;

public interface IProjectService {
    ResponseEntity<Response> getProjectListByKeyword(String keyword, String page, String size);
}
