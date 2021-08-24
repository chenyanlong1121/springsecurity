package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.List;

public interface ElasticsearchService {
    void createIndex(String idxName);
    void createDoc(String idxName, String path);
    void createDocs(String idxName, List<String> paths);
    void searchDocs(String idxName,List<String> list);
    void searchDocss(String idxName,String str);
    void searchDocsss(String idxName, String str, String dateFormat);
    void searchall(String idxName);
    void deleteDoc(String index,String type,String id);
    void searchFuzziness(String str);
    void searchTermbyAge();
}
