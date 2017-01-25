package project.movies.popular.popularmovies;

public class MovieDataModel {
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = String.format("http://image.tmdb.org/t/p/w780/%s", poster);
    }

    String poster;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    String overview;

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    String releaseDate;

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    String voteAverage;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = String.format("http://image.tmdb.org/t/p/w185/%s", thumbnail);
    }

    String thumbnail;
}
