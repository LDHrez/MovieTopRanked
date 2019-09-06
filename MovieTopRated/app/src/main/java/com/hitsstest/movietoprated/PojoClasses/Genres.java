
package com.hitsstest.movietoprated.PojoClasses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Genres {

    private List<Genre> genres = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
