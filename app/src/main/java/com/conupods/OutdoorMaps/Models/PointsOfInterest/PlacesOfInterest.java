package com.conupods.OutdoorMaps.Models.PointsOfInterest;

public class PlacesOfInterest
{
    private String[] htmlAttributions;

    private Place[] results;

    private String status;

    public String[] gethtmlAttributions()
    {
        return htmlAttributions;
    }

    public void setHtmlAttributions(String[] htmlAttributions)
    {
        this.htmlAttributions = htmlAttributions;
    }

    public Place[] getResults ()
    {
        return results;
    }

    public void setResults (Place[] results)
    {
        this.results = results;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [html_attributions = "+ htmlAttributions +", results = "+results+", status = "+status+"]";
    }
}