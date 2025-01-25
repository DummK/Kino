public record Movie(String title, int year, String genre, int durationInMinutes) {
    @Override
    public String toString() {
        return title;
    }
}
