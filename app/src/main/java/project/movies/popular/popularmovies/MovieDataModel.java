package project.movies.popular.popularmovies;

public class MovieDataModel {
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = "http://image.tmdb.org/t/p/w780/"+ poster;
    }

    String poster;
}
