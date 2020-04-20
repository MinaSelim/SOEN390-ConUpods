package com.conupods.OutdoorMaps.Models.PointsOfInterest;

public class Photo
{
    private String photoReference;

    private String width;

    private String height;

    public String getPhotoReference()
    {
        return photoReference;
    }

    public void setPhotoReference(String photoReference)
    {
        this.photoReference = photoReference;
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
        return "ClassPojo [photo_reference = "+ photoReference +", width = "+width+", html_attributions = "+", height = "+height+"]";
    }
}

