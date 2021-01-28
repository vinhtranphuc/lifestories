package com.tranphucvinh.payload;

import java.util.List;
import java.util.Map;

public class PageCategories {

    private String pageName;

    private List<Map<String, Object>> categories;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public List<Map<String, Object>> getCategories() {
        return categories;
    }

    public void setCategories(List<Map<String, Object>> categories) {
        this.categories = categories;
    }
}
