import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.frame();
            mainFrame.reservationPanel();
            mainFrame.showReservationsPanel();
            mainFrame.nextReservationPanel();
            mainFrame.mainPanel();
        });
    }
}