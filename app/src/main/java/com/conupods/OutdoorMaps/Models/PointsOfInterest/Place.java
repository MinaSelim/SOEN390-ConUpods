package com.conupods.OutdoorMaps.Models.PointsOfInterest;

public class Place
{


    private Photo[] photos;

    private String name;

    private String id;

    private String place_id;

    private Geometry geometry;

    private String photRequestURL;

    public String getPhotRequestURL() {
        return photRequestURL;
    }

    public void setPhotRequestURL(String photRequestURL) {
        this.photRequestURL = photRequestURL;
    }

    public Photo[] getPhotos ()
    {
        return photos;
    }

    public void setPhotos (Photo[] photos)
    {
        this.photos = photos;
    }

    public Geometry getGeometry ()
    {
        return geometry;
    }

    public void setGeometry (Geometry geometry)
    {
        this.geometry = geometry;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }


    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPlace_id()
    {
        return place_id;
    }

    public void setPlace_id(String place_id)
    {
        this.place_id = place_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [types = "+", icon = "+", rating = "+", photos = "+photos+", reference = "+", user_ratings_total = "+", scope = "+", name = "+name+", opening_hours = "+", geometry = "+", vicinity = "+", id = "+id+", plus_code = "+", place_id = "+ place_id +"]";
    }
}
