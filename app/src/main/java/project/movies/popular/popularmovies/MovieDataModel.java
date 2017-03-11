package project.movies.popular.popularmovies;

public class MovieDataModel {
    private String poster;
    private String title;
    private String overview;
    private String releaseDate;
    private String voteAverage;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = String.format("http://image.tmdb.org/t/p/w185/%s", poster);
    }

    public void setPosterForDB(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }
}
