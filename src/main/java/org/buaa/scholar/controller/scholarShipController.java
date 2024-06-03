package org.buaa.scholar.controller;

import org.buaa.scholar.service.IProjectService;
import org.buaa.scholar.service.impl.ProjectService;
import org.buaa.scholar.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scholarship")
public class scholarShipController {
    private IProjectService projectService;

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/getProjectListByKeyword/{keyword}/{page}/{size}")
    public ResponseEntity<Response> getProjectListByKeyword(@PathVariable("keyword") String keyword,
                                                            @PathVariable("page") String page,
                                                            @PathVariable("size") String size) {
        return projectService.getProjectListByKeyword(keyword, page, size);
    }
}
