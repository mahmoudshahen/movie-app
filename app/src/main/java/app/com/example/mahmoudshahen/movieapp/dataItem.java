package app.com.example.mahmoudshahen.movieapp;

/**
 * Created by mahmoud shahen on 8/9/2016
 */
public class dataItem {
    public String id;
    public String image;
    public String details;
    public String originalTitle;
    public String rating;
    public String releaseDate;
    public String backGround;
    public String runtime;
    public String imdb;
    public dataItem(String id, String image, String backGround, String details, String originalTitle,
                    String rating, String releaseDate)
    {
        this.id = id;
        this.image = image;
        this.backGround = backGround;
        this.details = details;
        this.originalTitle = originalTitle;
        this.rating = rating;
        this.releaseDate = releaseDate;

        clean();
    }
    public dataItem(){}
    public void clean()
    {
        originalTitle = originalTitle.substring(1,originalTitle.length()-1);
        details =  details.substring(1,details.length()-1);
        releaseDate = releaseDate.substring(1, 5);
        image = image.substring(1,image.length()-1);

        image= "http://image.tmdb.org/t/p/w342"+ image;
        backGround = backGround.substring(1,backGround.length()-1);
        backGround= "http://image.tmdb.org/t/p/w500"+ backGround;

    }
    public void setIMDb(String Imdb)
    {
        this.imdb = Imdb;
        imdb = imdb.substring(1, imdb.length()-1);
        imdb = "http://www.imdb.com/title/"+imdb+ "/";
    }
    public void setRunTime(String runtime)
    {
        this.runtime = runtime;
    }

    public void setFromDataBase(String id, String originalTitle, String details, String releaseDate,
                        String runtime, String rating, String image, String backGround,String imdb)
    {
        this.id = id;
        this.originalTitle = originalTitle;
        this.details = details;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.rating = rating;
        this.image = image;
        this.backGround = backGround;
        this.imdb = imdb;
    }


}
