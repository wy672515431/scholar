package org.buaa.scholar.service.impl;

import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScoreQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import org.buaa.scholar.model.es.Project;
import org.buaa.scholar.service.IProjectService;
import org.buaa.scholar.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService implements IProjectService {

    private ElasticsearchOperations operations;

    @Autowired
    public void setOperations(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Override
    public ResponseEntity<Response> getProjectListByKeyword(String keyword, String page, String size) {
        int pageNum = Integer.parseInt(page);
        int sizeNum = Integer.parseInt(size);

        Pageable pageable = PageRequest.of(pageNum - 1, sizeNum);


        MatchQuery orgnizationMatchQuery = MatchQuery.of(builder -> builder.field("organization").query(keyword));
        MatchQuery authorsMatchQuery = MatchQuery.of(builder -> builder.field("authors").query(keyword));
        MatchQuery journalMatchQuery = MatchQuery.of(builder -> builder.field("journal").query(keyword));
        MatchQuery fundProject = MatchQuery.of(builder -> builder.field("fundProject").query(keyword));
        BoolQuery boolQuery = BoolQuery.of(builder -> builder.should(orgnizationMatchQuery._toQuery(),
                authorsMatchQuery._toQuery(),
                journalMatchQuery._toQuery(), fundProject._toQuery()));
        FunctionScoreQuery functionScoreQuery = FunctionScoreQuery.of(builder -> builder.query(boolQuery._toQuery()));
        HighlightParameters highlightParameters = HighlightParameters.builder().withPreTags("<span style='color:red"
                + "'>").withPostTags("</span>").build();
        HighlightField organizationHighlightField = new HighlightField("organization");
        HighlightField authorsHighlightField = new HighlightField("authors");
        HighlightField journalHighlightField = new HighlightField("journal");
        HighlightField fundProjectHighlightField = new HighlightField("fundProject");
        Highlight highlight = new Highlight(highlightParameters, Arrays.asList(organizationHighlightField,
                authorsHighlightField, journalHighlightField, fundProjectHighlightField));
        HighlightQuery highlightQuery = new HighlightQuery(highlight, null);
        NativeQuery searchQuery = NativeQuery.builder().withQuery(functionScoreQuery._toQuery())
                .withPageable(pageable).withSort(SortOptionsBuilders.score().order(SortOrder.Desc).build()._toSortOptions())
                .withHighlightQuery(highlightQuery)
                .build();
        //取消10000最大数量限制
        searchQuery.setTrackTotalHits(true);
        SearchHits<Project> search = operations.search(searchQuery, Project.class);
        List<SearchHit<Project>> searchHits = search.getSearchHits();
        List<Project> projects = new ArrayList<>();
        for (SearchHit<Project> searchHit : searchHits) {
            Project project = searchHit.getContent();
            searchHit.getHighlightField("organization");
            project.setOrganization(searchHit.getHighlightField("organization").isEmpty() ?
                    project.getOrganization() : searchHit.getHighlightField("organization").get(0));
            project.setAuthors(searchHit.getHighlightField("authors").isEmpty() ? project.getAuthors() :
                    searchHit.getHighlightField("authors").get(0));
            project.setJournal(searchHit.getHighlightField("journal").isEmpty() ? project.getJournal() :
                    searchHit.getHighlightField("journal").get(0));
            project.setFundProject(searchHit.getHighlightField("fundProject").isEmpty() ? project.getFundProject() :
                    searchHit.getHighlightField("fundProject").get(0));
            projects.add(project);
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("projectList", projects);
        responseMap.put("total", search.getTotalHits());
        return ResponseEntity.ok(new Response(responseMap));
    }
}
