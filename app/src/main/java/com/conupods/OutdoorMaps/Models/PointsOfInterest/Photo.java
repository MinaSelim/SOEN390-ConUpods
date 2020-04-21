package com.conupods.OutdoorMaps.Models.PointsOfInterest;

public class Photo
{
    private String photo_reference;

    private String width;

    private String height;

    public String getPhoto_reference()
    {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference)
    {
        this.photo_reference = photo_reference;
    }

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }


    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [photo_reference = "+ photo_reference +", width = "+width+", html_attributions = "+", height = "+height+"]";
    }
}

