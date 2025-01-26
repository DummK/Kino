public record SavedSeats(int i, int j) {
    public int getI() {
        return i;
    }
    public int getJ() {
        return j;
    }

    @Override
    public String toString() {
        return "Seats: (" + i + ", " + j + ")";
    }
}
