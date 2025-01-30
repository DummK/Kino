import java.awt.*;
import java.util.*;
import javax.swing.*;

public class MainFrame {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel reservationPanel;
    private JPanel showReservationsPanel;
    private JPanel nextReservationPanel;
    private final ArrayList<Movie> movies = new ArrayList<>();
    private final int[][] reservedSeats = new int[10][5];
    private JComboBox<Movie> moviesComboBox;
    private JComboBox<String> timeComboBox;
    private final ArrayList<SavedSeats> savedSeats = new ArrayList<>();
    private JLabel reservationQuestion;

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

        JButton reservationButton = createButtonForMainPanel(reservationPanel);
        mainPanel.add(reservationButton);

        JButton showReservationButton = createButtonForMainPanelToShowReservationsPanel(showReservationsPanel);
        mainPanel.add(showReservationButton);
    }

    public void reservationPanel() {
        reservationPanel = createBasicPanel(false);
        frame.add(reservationPanel);

        JLabel movieLabel = createBasicLabel("Wybierz film: ", 200, 50, 350, 30, 20, 0, false);
        reservationPanel.add(movieLabel);


        addMovies(); //dodanie filmow
        moviesComboBox = createMovieComboBox();//wybor filmow
        reservationPanel.add(moviesComboBox);

        JLabel timeLabel = createBasicLabel("Wybierz godzinę: ", 200, 150, 350, 30, 20, 0, false);
        reservationPanel.add(timeLabel);

        timeComboBox = createTimeComboBox();
        reservationPanel.add(timeComboBox);

        JLabel seatsLabel = createBasicLabel("Wybierz miejsca: ", 200, 290, 350, 30, 20, 0, false);
        reservationPanel.add(seatsLabel);

        JButton headBackButton = createHeadBackButtonToMainPanel(reservationPanel, true);
        reservationPanel.add(headBackButton);

        JButton nextButton = createNextButton();
        reservationPanel.add(nextButton);
    }

    public void nextReservationPanel() {
        nextReservationPanel = createBasicPanel(false);
        frame.add(nextReservationPanel);

        JButton headBackButton = createHeadBackButtonToReservationPanel(nextReservationPanel);
        nextReservationPanel.add(headBackButton);

        JButton acceptButton = createAcceptButton();
        nextReservationPanel.add(acceptButton);
    }

    public void showReservationsPanel() {
        showReservationsPanel = createBasicPanel(false);
        frame.add(showReservationsPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }


    private JButton createButtonForMainPanel(JPanel panel) {
        JButton button = new JButton("Rezerwacja");
        button.setBounds((frame.getWidth() - button.getWidth()) / 2 - (250 / 2), 200, 250, 50);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.LIGHT_GRAY);
        button.addActionListener(e -> {
            panel.setVisible(true);
            mainPanel.setVisible(false);
            disableReservedSeats();
        });
        return button;
    }

    private JButton createButtonForMainPanelToShowReservationsPanel(JPanel panel) {
        JButton button = new JButton("Twoje rezerwacje");
        button.setBounds((frame.getWidth() - button.getWidth()) / 2 - (250 / 2), 300, 250, 50);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.LIGHT_GRAY);
        button.addActionListener(e -> {
            showReservations();
            panel.setVisible(true);
            mainPanel.setVisible(false);
        });
        return button;
    }

    private JButton createHeadBackButtonToMainPanel(JPanel panelOpenedAtTheMoment, boolean isClearingSeatsNecessary) {
        JButton headBackButton = new JButton("Wróć");
        headBackButton.setBounds((frame.getWidth() - headBackButton.getWidth()) / 2 - 250, 500, 200, 50);
        headBackButton.setFont(new Font("Arial", Font.BOLD, 20));
        headBackButton.setBorderPainted(false);
        headBackButton.setFocusPainted(false);
        headBackButton.setBackground(Color.BLACK);
        headBackButton.setForeground(Color.LIGHT_GRAY);
        headBackButton.addActionListener(e -> {
            mainPanel.setVisible(true);
            panelOpenedAtTheMoment.setVisible(false);
            if (isClearingSeatsNecessary) {
                clearSeats();
            }
        });
        return headBackButton;
    }
    private JButton createHeadBackButtonToReservationPanel(JPanel panelOpenedAtTheMoment) {
        JButton headBackButton = new JButton("Wróć");
        headBackButton.setBounds((frame.getWidth() - headBackButton.getWidth()) / 2 - 250, 500, 200, 50);
        headBackButton.setFont(new Font("Arial", Font.BOLD, 20));
        headBackButton.setBorderPainted(false);
        headBackButton.setFocusPainted(false);
        headBackButton.setBackground(Color.BLACK);
        headBackButton.setForeground(Color.LIGHT_GRAY);
        headBackButton.addActionListener(e -> {
            reservationPanel.setVisible(true);
            panelOpenedAtTheMoment.setVisible(false);
            nextReservationPanel.remove(reservationQuestion);

            clearSeats();
            clearSavedSeats();
        });
        return headBackButton;
    }
    private JButton createNextButton() {
        JButton nextButton = new JButton("Dalej");
        nextButton.setBounds((reservationPanel.getWidth() - nextButton.getWidth()) / 2 + 50, 500, 200, 50);
        nextButton.setFont(new Font("Arial", Font.BOLD, 20));
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.LIGHT_GRAY);
        nextButton.addActionListener(e -> {
            if (moviesComboBox.getSelectedIndex() == -1) {
                showErrorPopup("Gościu wybierz jakiś film!");
            } else if (timeComboBox.getSelectedIndex() == -1) {
                showErrorPopup("Gościu wybierz jakąś godzine!");
            } else if (savedSeats.isEmpty()) {
                showErrorPopup("Gościu wybierz przynajmniej jakieś miejsce!");
            } else {
                reservationPanel.setVisible(false);
                createFinalReservationQuestion();
                nextReservationPanel.setVisible(true);
            }

        });
        return nextButton;
    }
    private JButton createAcceptButton() {
        JButton acceptButton = new JButton("Zarezerwuj");
        acceptButton.setBounds((nextReservationPanel.getWidth() - acceptButton.getWidth()) / 2 + 50, 500, 200, 50);
        acceptButton.setFont(new Font("Arial", Font.BOLD, 20));
        acceptButton.setBorderPainted(false);
        acceptButton.setFocusPainted(false);
        acceptButton.setBackground(Color.BLACK);
        acceptButton.setForeground(Color.LIGHT_GRAY);
        acceptButton.addActionListener(e -> {
            mainPanel.setVisible(true);
            nextReservationPanel.setVisible(false);
            nextReservationPanel.remove(reservationQuestion);



            clearSavedSeats();
            clearSeats();
        });
        return acceptButton;
    }
    private JLabel createBasicLabel(String labelText, int x, int y, int width, int height, int fontSize, int fontStyle, boolean doesItHaveToBeCentered) {
        JLabel label;
        if (doesItHaveToBeCentered) {
            label = new JLabel(labelText, SwingConstants.CENTER);
        } else {
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
                    setText(((Movie) value).getTitle());
                }
                return this;
            }
        });
        //--------------------------------//

        moviesComboBox.setBounds(400, 50, 350, 30);
        moviesComboBox.setFont(new Font("Arial", Font.PLAIN, 20));
        moviesComboBox.setSelectedIndex(-1);
        moviesComboBox.setEditable(false);
        moviesComboBox.setBackground(Color.WHITE);
        moviesComboBox.setForeground(Color.BLACK);
        moviesComboBox.addActionListener(e -> {
            Movie selectedMovie = (Movie) moviesComboBox.getSelectedItem();
            if (selectedMovie != null) {
                reservationPanel.remove(timeComboBox);
                timeComboBox.removeAllItems();

                for (Movie movie : movies) {
                    if (movie == selectedMovie) {
                        for (int i = 0; i < movie.getHours().size(); i++) {
                            timeComboBox.addItem(movie.getHours().get(i));
                        }
                    }
                }
                System.out.println("Wybrany film: " + selectedMovie.getTitle());
                    assignSeatsArray();
                createTimeComboBox();
                reservationPanel.add(timeComboBox);
            }
        });
        return moviesComboBox;
    }
    private JComboBox<String> createTimeComboBox() {
        JComboBox<String> timeComboBox = new JComboBox<>();
        timeComboBox.setBounds(400, 150, 350, 30);
        timeComboBox.setFont(new Font("Arial", Font.PLAIN, 20));
        timeComboBox.setEditable(false);
        timeComboBox.setBackground(Color.WHITE);
        timeComboBox.setForeground(Color.BLACK);
        timeComboBox.addActionListener(e -> {
            System.out.println("Wybrana godzina dla filmu " + Objects.requireNonNull(moviesComboBox.getSelectedItem()) + ": " + timeComboBox.getSelectedItem()); //jakies zabezpieczenie zeby nie bylo null
        });
        return timeComboBox;
    }

    private void assignSeatsArray() {
        for (Movie movie : movies) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 5; j++) {
                    if (movie.getSeats()[i][j] == null) {
                        movie.getSeats()[i][j] = new JButton("-");
                        movie.getSeats()[i][j].setBounds((i + 14) * 30, 230 + j * 30, 30, 30);
                        movie.getSeats()[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                        movie.getSeats()[i][j].setFocusPainted(false);
                        movie.getSeats()[i][j].setBackground(Color.GREEN);
                        movie.getSeats()[i][j].setForeground(Color.BLACK);
                        final int finalI = i;
                        final int finalJ = j;
                        movie.getSeats()[i][j].addActionListener(e -> {
                            if (movie.getSeats()[finalI][finalJ].getBackground() == Color.GREEN) {
                                System.out.println("Siedzenie " + (finalI + 1) + "(column)" + "-" + (finalJ + 1) + "(row)" + " wybrane dla filmu " + Objects.requireNonNull(moviesComboBox.getSelectedItem()) + " o godzinie " + timeComboBox.getSelectedItem());
                                savedSeats.add(new SavedSeats(finalI + 1, finalJ + 1));
                                System.out.println("dodano siedzenie");
                                movie.getSeats()[finalI][finalJ].setBackground(Color.RED);
                            } else if (movie.getSeats()[finalI][finalJ].getBackground() == Color.RED) {
                                System.out.println("Siedzenie " + (finalI + 1) + "(column)" + "-" + (finalJ + 1) + "(row)" + " wyczyszczone");
                                if (savedSeats.removeIf(seat -> seat.getI() == finalI + 1 && seat.getJ() == finalJ + 1)) {
                                    System.out.println("Usuneło siedzenie");
                                }
                                movie.getSeats()[finalI][finalJ].setBackground(Color.GREEN);
                            }
                        });
                        movies.get(moviesComboBox.getSelectedIndex()).setSeatsMap(movies.get(moviesComboBox.getSelectedIndex()).getHours().get(timeComboBox.getSelectedIndex()), movie.getSeats());
                        reservationPanel.add(movie.getSeats()[i][j]);
                    }
                }

            }
        }

    }
    private void showReservations() {

        showReservationsPanel.removeAll();

        JButton headBackButton = createHeadBackButtonToMainPanel(showReservationsPanel, false);
        showReservationsPanel.add(headBackButton);

        createLabelsForShowReservationsPanel();

        int index = 0;


    }
    private void disableReservedSeats() {
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                if(reservedSeats[i][j] == 1) {
                    movies.get(moviesComboBox.getSelectedIndex()).getSeats()[i][j].setBackground(Color.RED);
                    movies.get(moviesComboBox.getSelectedIndex()).getSeats()[i][j].setEnabled(false);
                }
            }
        }
    }
    private void createLabelsForShowReservationsPanel() {
        JLabel movieLabel = new JLabel("Film:");
        movieLabel.setFont(new Font("Arial", Font.BOLD, 20));
        movieLabel.setBounds(105, 0, 100, 30);
        showReservationsPanel.add(movieLabel);

        JLabel hourLabel = new JLabel("Godzina:");
        hourLabel.setFont(new Font("Arial", Font.BOLD, 20));
        hourLabel.setBounds(410, 0, 100, 30);
        showReservationsPanel.add(hourLabel);

        JLabel seatsLabel = new JLabel("Miejsca:");
        seatsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        seatsLabel.setBounds(720, 0, 100, 30);
        showReservationsPanel.add(seatsLabel);
    }
    private void addMovies() {
        movies.add(new Movie("Big cock: the fifth", "Action", 2008, 100, new ArrayList<String>(Arrays.asList("10:00", "16:00", "20:00"))));
        movies.add(new Movie("Pierdolino: historia prawdziwa", "Adventure", 2001, 120, new ArrayList<String>(Arrays.asList("10:00", "16:00", "20:00"))));
        movies.add(new Movie("TitsTok", "Sci-Fi", 2010, 140, new ArrayList<String>(Arrays.asList("10:00", "16:00", "20:00"))));
        movies.add(new Movie("Nigger of the south", "Mystery", 2000, 90, new ArrayList<String>(Arrays.asList("13:00", "18:00", "20:00"))));
        movies.add(new Movie("Black nigger on the farm: true history", "Drama", 2000, 120, new ArrayList<String>(Arrays.asList("10:00", "16:00", "20:00"))));
        movies.add(new Movie("The Lion King", "Animation", 1994, 85, new ArrayList<String>(Arrays.asList("12:00", "16:00", "20:00"))));
        movies.add(new Movie("The Silence of the Lambs", "Mystery", 1991, 102, new ArrayList<String>(Arrays.asList("10:00", "16:00", "20:00"))));
        movies.add(new Movie("The Usual Suspects", "Mystery", 1995, 104, new ArrayList<String>(Arrays.asList("10:00", "16:00", "20:00"))));
        movies.add(new Movie("Pulp Fiction", "Crime", 1994, 154, new ArrayList<String>(Arrays.asList("12:00", "16:00", "23:00"))));
        movies.add(new Movie("Schindler's List", "Drama", 1993, 195, new ArrayList<String>(Arrays.asList("10:00", "14:00", "20:00"))));
        movies.add(new Movie("The Departed", "Crime", 2006, 162, new ArrayList<String>(Arrays.asList("10:00", "15:00", "20:00"))));
        movies.add(new Movie("American Psycho", "Drama", 1990, 108, new ArrayList<String>(Arrays.asList("11:00", "16:00", "20:00"))));
        movies.add(new Movie("The Lives of Others", "Comedy", 2006, 117, new ArrayList<String>(Arrays.asList("13:00", "16:00", "20:00"))));
    }
    private void clearSeats() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 5; j++) {
                movies.get(moviesComboBox.getSelectedIndex()).getSeats()[i][j].setBackground(Color.GREEN);
            }
        }
        System.out.println("Siedzenia wyczyszczone");
    }
    private void clearSavedSeats() {
        savedSeats.clear();
        System.out.println("Zapisane siedzenia wyczyszczone");
    }
    private void createFinalReservationQuestion() {
        int i = 0;
        for(SavedSeats seat : savedSeats) {
            i++;
        }

        reservationQuestion = new JLabel("Rezerwacja na " + i + " bilet/y/ów na film " + Objects.requireNonNull(moviesComboBox.getSelectedItem()) + " o godzinie " + timeComboBox.getSelectedItem(), SwingConstants.CENTER);
            reservationQuestion.setBounds(0, 200, 1000, 50);
            reservationQuestion.setFont(new Font("Arial", Font.BOLD, 25));
        nextReservationPanel.add(reservationQuestion);
    }
    private void showErrorPopup(String message) {
        JOptionPane.showMessageDialog(frame, message, "Błąd", JOptionPane.ERROR_MESSAGE);
    }
}
