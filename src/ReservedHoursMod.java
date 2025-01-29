public class ReservedHoursMod {
    String reservedHour;

    public ReservedHoursMod(String reservedHour) {
        if(reservedHour.contains("[") && reservedHour.contains("]")) {
            this.reservedHour = reservedHour.replace("[", "").replace("]", "");
        }
    }

    @Override
    public String toString() {
        return reservedHour;
    }
}
