import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;

public class MainFrame {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel reservationPanel;
    private JPanel showReservationsPanel;
    private JPanel nextReservationPanel;
    private final ArrayList<Movie> movies = new ArrayList<>();
    private final JButton[][] seats = new JButton[10][5];
    private JComboBox<Movie> moviesComboBox;
    private JComboBox<String> timeComboBox;

    public void frame() {
        frame = new JFrame("Kino Cwelowy");
            frame.setSize(1000, 600);
            frame.setIconImage(new ImageIcon("src/res/icon3.png").getImage());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
    }
    public void mainPanel() {
        mainPanel = createBasicPanel(true);
        frame.add(mainPanel);

        JLabel title = createBasicLabel("Kino Cwelowy", 0, 70, mainPanel.getWidth(), 60, 52, 2, true);
        mainPanel.add(title);

        JButton reservationButton = createButtonForMainPanel("Rezerwacja", 200, reservationPanel);
        mainPanel.add(reservationButton);

        JButton showReservationButton = createButtonForMainPanel("Twoje rezerwacje", 300, showReservationsPanel);
        mainPanel.add(showReservationButton);
    }
    public void reservationPanel() {
        reservationPanel = createBasicPanel(false);
        frame.add(reservationPanel);

        JLabel movieLabel = createBasicLabel("Wybierz film: ", 200, 50, 350, 30, 20, 0, false);
        reservationPanel.add(movieLabel);


        addMovies(); //dodanie filmow
        moviesComboBox = createMovieComboBox(); //wybor filmow
        reservationPanel.add(moviesComboBox);

        JLabel timeLabel = createBasicLabel("Wybierz godzinę: ", 200, 150, 350, 30, 20, 0, false);
        reservationPanel.add(timeLabel);


        timeComboBox = createTimeComboBox();//Wybor godzin
        reservationPanel.add(timeComboBox);

        JLabel seatsLabel = createBasicLabel("Wybierz miejsca: ", 200, 290, 350, 30, 20, 0, false);
        reservationPanel.add(seatsLabel);


        assignSeatsArray();  //Pętla do zrobienia tablicy przycisków do wyboru miejsc

        JButton headBackButton = createHeadBackButton(reservationPanel, mainPanel, true);
        reservationPanel.add(headBackButton);

        JButton nextButton = createNextButton();
        reservationPanel.add(nextButton);
    }
    public void nextReservationPanel() {
        nextReservationPanel = createBasicPanel(false);
        frame.add(nextReservationPanel);

        JButton headBackButton = createHeadBackButton(nextReservationPanel, reservationPanel, false);
        nextReservationPanel.add(headBackButton);
    }
    public void showReservationsPanel() {
        showReservationsPanel = createBasicPanel(false);
        frame.add(showReservationsPanel);

        JButton headBackButton = createHeadBackButton(showReservationsPanel, mainPanel, false);
        showReservationsPanel.add(headBackButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    private JButton createButtonForMainPanel(String buttonText, int y  , JPanel panel) {
        JButton button = new JButton(buttonText);
        button.setBounds((frame.getWidth() - button.getWidth())/2 - (250 /2), y, 250, 50);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.LIGHT_GRAY);
        button.addActionListener(e -> {
            panel.setVisible(true);
            mainPanel.setVisible(false);
        });
        return button;
    }
    private JButton createHeadBackButton(JPanel panelOpenedAtTheMoment, JPanel panelToHeadBack, boolean isClearingSeatsNecessary) {
        JButton headBackButton = new JButton("Wróć");
        headBackButton.setBounds((frame.getWidth() - headBackButton.getWidth())/2 - 250, 500, 200, 50);
        headBackButton.setFont(new Font("Arial", Font.BOLD, 20));
        headBackButton.setBorderPainted(false);
        headBackButton.setFocusPainted(false);
        headBackButton.setBackground(Color.BLACK);
        headBackButton.setForeground(Color.LIGHT_GRAY);
        headBackButton.addActionListener(e -> {
            panelToHeadBack.setVisible(true);
            panelOpenedAtTheMoment.setVisible(false);
            if(isClearingSeatsNecessary) {
                clearSeats();
            }
        });
        return headBackButton;
    }
    private JButton createNextButton() {
        JButton nextButton = new JButton("Dalej");
        nextButton.setBounds((reservationPanel.getWidth()- nextButton.getWidth())/2 + 50, 500, 200, 50);
        nextButton.setFont(new Font("Arial", Font.BOLD, 20));
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.LIGHT_GRAY);
        return nextButton;
    }
    private void assignSeatsArray() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 5; j++) {
                seats[i][j] = new JButton("-");
                seats[i][j].setBounds((i+14)*30, 230+j*30, 30, 30);
                seats[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                seats[i][j].setFocusPainted(false);
                seats[i][j].setBackground(Color.GREEN);
                seats[i][j].setForeground(Color.BLACK);
                final int finalI = i;
                final int finalJ = j;
                seats[i][j].addActionListener(e -> {
                    if(seats[finalI][finalJ].getBackground() == Color.GREEN) {
                        System.out.println("Siedzenie " + (finalI + 1) + "(column)" + "-" + (finalJ + 1) + "(row)" + " wybrane dla filmu " + Objects.requireNonNull(moviesComboBox.getSelectedItem())+ " o godzinie " + timeComboBox.getSelectedItem());
                        seats[finalI][finalJ].setBackground(Color.RED);
                    }
                    else if(seats[finalI][finalJ].getBackground() == Color.RED) {
                        System.out.println("Siedzenie " + (finalI + 1) + "(column)" + "-" + (finalJ + 1) + "(row)" + " wyczyszczone");
                        seats[finalI][finalJ].setBackground(Color.GREEN);
                    }
                });
                reservationPanel.add(seats[i][j]);
            }
        }
    }
    private JLabel createBasicLabel(String labelText, int x, int y, int width, int height, int fontSize, int fontStyle, boolean doesItHaveToBeCentered) {
        JLabel label;
            if(doesItHaveToBeCentered) {
                label = new JLabel(labelText, SwingConstants.CENTER);
            }
            else {
                label = new JLabel(labelText);
            }
                label.setBounds(x, y, width, height);
                switch (fontStyle) {
                    case 0:
                        label.setFont(new Font("Arial", Font.PLAIN, fontSize));
                        break;
                    case 1:
                        label.setFont(new Font("Arial", Font.BOLD, fontSize));
                        break;
                    case 2:
                        label.setFont(new Font("Arial", Font.ITALIC, fontSize));
                        break;
                }
        return label;
    }
    private JPanel createBasicPanel(boolean visibility) {
        JPanel panel = new JPanel();
            panel.setSize(frame.getWidth(), frame.getHeight());
            panel.setLayout(null);
            panel.setBackground(Color.LIGHT_GRAY);
            panel.setVisible(visibility);
        return panel;
    }
    private JComboBox<Movie> createMovieComboBox() {
        JComboBox<Movie> moviesComboBox = new JComboBox<>();
            for (Movie movie : movies) {
                moviesComboBox.addItem(movie);
            }

            //wyswietla nazwy zamiast obiektow//
            moviesComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Movie) {
                        setText(((Movie) value).title());
                    }
                    return this;
                }
            });
            //--------------------------------//

            moviesComboBox.setBounds(400, 50, 350, 30);
            moviesComboBox.setFont(new Font("Arial", Font.PLAIN, 20));
            moviesComboBox.setEditable(false);
            moviesComboBox.setBackground(Color.WHITE);
            moviesComboBox.setForeground(Color.BLACK);
            moviesComboBox.addActionListener(e -> {
                Movie selectedMovie = (Movie) moviesComboBox.getSelectedItem();
                if(selectedMovie != null) {
                    System.out.println("Wybrany film: " + selectedMovie.title());
                    clearSeats();
                }
            });
        return moviesComboBox;
    }
    private JComboBox<String> createTimeComboBox() {
        JComboBox<String> timeComboBox = new JComboBox<>();
            timeComboBox.addItem("10:00");
            timeComboBox.addItem("12:00");
            timeComboBox.addItem("14:00");
            timeComboBox.addItem("16:00");
            timeComboBox.addItem("18:00");
            timeComboBox.addItem("20:00");
            timeComboBox.addItem("22:00");
            timeComboBox.setBounds(400, 150, 350, 30);
            timeComboBox.setFont(new Font("Arial", Font.PLAIN, 20));
            timeComboBox.setEditable(false);
            timeComboBox.setBackground(Color.WHITE);
            timeComboBox.setForeground(Color.BLACK);
            timeComboBox.addActionListener(e -> {
                System.out.println("Wybrana godzina dla filmu " + Objects.requireNonNull(moviesComboBox.getSelectedItem()) + ": " + timeComboBox.getSelectedItem()); //jakies zabezpieczenie zeby nie bylo null
                clearSeats();
            });
        return timeComboBox;
    }
    private void addMovies() {
        movies.add(new Movie("The Dark Knight", 2008, "Action", 100));
        movies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", 2001, "Adventure", 120));
        movies.add(new Movie("Inception", 2010, "Sci-Fi", 140));
        movies.add(new Movie("Memento", 2000, "Mystery", 90));
        movies.add(new Movie("Gladiator", 2000, "Drama", 120));
        movies.add(new Movie("The Lion King", 1994, "Animation", 85));
        movies.add(new Movie("The Silence of the Lambs", 1991, "Mystery", 102));
        movies.add(new Movie("The Usual Suspects", 1995, "Mystery", 104));
        movies.add(new Movie("Pulp Fiction", 1994, "Crime", 154));
        movies.add(new Movie("Schindler's List", 1993, "Drama", 195));
        movies.add(new Movie("The Departed", 2006, "Crime", 162));
        movies.add(new Movie("American Psycho", 1990, "Drama", 108));
        movies.add(new Movie("The Lives of Others", 2006, "Comedy", 117));
    }
    private void clearSeats() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 5; j++) {
                seats[i][j].setBackground(Color.GREEN);
            }
        }
        System.out.println("Siedzenia wyczyszczone");
    }
}
