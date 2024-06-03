package org.buaa.scholar.repository;

import org.buaa.scholar.model.es.Project;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends ElasticsearchRepository<Project, String> {
}
