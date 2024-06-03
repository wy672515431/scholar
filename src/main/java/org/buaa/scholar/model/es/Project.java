package org.buaa.scholar.model.es;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "project")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Project {
    @Id
    @Field(type = FieldType.Auto)
    private String id;
    @Field(type = FieldType.Auto)
    private String achievementID;
    @Field(type = FieldType.Auto, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String authors;
    @Field(type = FieldType.Auto, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String chineseTitle;
    @Field(type = FieldType.Auto, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String conference;
    @Field(type = FieldType.Auto)
    private String doi;
    @Field(type = FieldType.Auto)
    private String doiUrl;
    @Field(type = FieldType.Auto)
    private String downloadHref;
    @Field(type = FieldType.Auto)
    private String enAbstract;
    @Field(type = FieldType.Auto)
    private String enKeyword;
    @Field(type = FieldType.Auto)
    private String englishTitle;
    @Field(type = FieldType.Auto)
    private String fieldCode;
    @Field(type = FieldType.Auto)
    private String fulltext;
    @Field(type = FieldType.Auto, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String fundProject;
    @Field(type = FieldType.Auto)
    private String fundProjectCode;
    @Field(type = FieldType.Auto)
    private String fundProjectNo;
    @Field(type = FieldType.Auto, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String journal;
    @Field(type = FieldType.Auto, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String organization;
    @Field(type = FieldType.Auto)
    private String organizationId;
    @Field(type = FieldType.Auto)
    private String outputSubIrSource;
    @Field(type = FieldType.Auto)
    private String pageRange;
    @Field(type = FieldType.Auto)
    private String productType;
    @Field(type = FieldType.Auto)
    private String publishDate;
    @Field(type = FieldType.Auto)
    private String source;
    @Field(type = FieldType.Auto)
    private String supportType;
    @Field(type = FieldType.Auto, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String supportTypeName;
    @Field(type = FieldType.Auto)
    private String year;
    @Field(type = FieldType.Auto, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String zhAbstract;
    @Field(type = FieldType.Auto, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String zhKeyword;
}
