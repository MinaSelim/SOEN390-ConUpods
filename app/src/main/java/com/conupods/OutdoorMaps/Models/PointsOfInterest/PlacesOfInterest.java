package com.conupods.OutdoorMaps.Models.PointsOfInterest;

public class PlacesOfInterest {
    private String[] html_attributions;

    private Place[] results;

    private String status;

    public String[] gethtmlAttributions() {
        return html_attributions;
    }

    public void setHtml_attributions(String[] html_attributions) {
        this.html_attributions = html_attributions;
    }

    public Place[] getResults() {
        return results;
    }

    public void setResults(Place[] results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [html_attributions = " + html_attributions + ", results = " + results + ", status = " + status + "]";
    }
}