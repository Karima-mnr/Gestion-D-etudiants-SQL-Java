/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tpfinalihm;
import javax.swing.ImageIcon;    
import javax.swing.*;
import java.util.ArrayList;  
import java.util.Collections;



/**
 *
 * @author user22µ
 */
public class MemoryGame extends javax.swing.JFrame {

    private ArrayList<JButton> cards;
    private ArrayList<ImageIcon> icons;
    private JButton firstCard, secondCard;
    private Timer flipBackTimer, countdownTimer;
    private int moves = 0, timeLeft = 60, matchedPairs = 0;
    private int totalTime = 60; 
    private boolean gameActive = false;

    private final ImageIcon blank = new ImageIcon(getClass().getResource("/pics/blank.png"));
    private final ImageIcon comete = new ImageIcon(getClass().getResource("/pics/livre.png"));
    private final ImageIcon eclipse = new ImageIcon(getClass().getResource("/pics/nuageux.png"));
    private final ImageIcon flocons = new ImageIcon(getClass().getResource("/pics/soleil.png"));
    private final ImageIcon lune = new ImageIcon(getClass().getResource("/pics/lune.png"));
    private final ImageIcon planete = new ImageIcon(getClass().getResource("/pics/tempete.png"));
    private final ImageIcon telescope = new ImageIcon(getClass().getResource("/pics/venteux.png"));
    private final ImageIcon champignon = new ImageIcon(getClass().getResource("/pics/champignon.png"));
    private final ImageIcon sakura = new ImageIcon(getClass().getResource("/pics/sakura.png"));
    private final ImageIcon baies = new ImageIcon(getClass().getResource("/pics/baies.png"));
    private final ImageIcon coco = new ImageIcon(getClass().getResource("/pics/noix-de-coco.png"));
    private final ImageIcon raisin = new ImageIcon(getClass().getResource("/pics/raisin.png"));
    private final ImageIcon cofe = new ImageIcon(getClass().getResource("/pics/boisson-chaude.png"));
    private final ImageIcon baie = new ImageIcon(getClass().getResource("/pics/baie.png"));
    private final ImageIcon feuille = new ImageIcon(getClass().getResource("/pics/feuille.png"));
    private final ImageIcon neige = new ImageIcon(getClass().getResource("/pics/neige.png"));

    public MemoryGame() {
        initComponents();
        initializeGame();
    }

    private void initializeGame() {
        cards = new ArrayList<>();

        cards.add(carte1);
        cards.add(carte2);
        cards.add(carte3);
        cards.add(carte4);
        cards.add(carte5);
        cards.add(carte6);
        cards.add(carte7);
        cards.add(carte8);
        cards.add(carte9);
        cards.add(carte10);
        cards.add(carte11);
        cards.add(carte12);
        cards.add(carte13);
        cards.add(carte14);
        cards.add(carte15);
        cards.add(carte16);

        for (JButton card : cards) {
            card.addActionListener(e -> handleCardClick((JButton) e.getSource()));
        }
        resetGame();
    }

    
    private void setMatchingPairImages() {
        ArrayList<ImageIcon> allIcons = new ArrayList<>();
        allIcons.add(comete);
        allIcons.add(eclipse);
        allIcons.add(flocons);
        allIcons.add(lune);
        allIcons.add(planete);
        allIcons.add(telescope);
        allIcons.add(champignon);
        allIcons.add(sakura);
        allIcons.add(baies);
        allIcons.add(coco);
        allIcons.add(raisin);
        allIcons.add(cofe);
        allIcons.add(baie);
        allIcons.add(neige);
        allIcons.add(feuille);

        int numPairs = cards.size() / 2; 
        Collections.shuffle(allIcons); 
        ArrayList<ImageIcon> selectedIcons = new ArrayList<>(allIcons.subList(0, numPairs));

        icons = new ArrayList<>(selectedIcons);
        icons.addAll(selectedIcons);
        
        // Shuffle the icons to randomize positions
        Collections.shuffle(icons);
    }
    

    private void resetGame() {
        moves = 0;
        matchedPairs = 0;
        timeLeft = 60;
        gameActive = false; // Game is inactive until playBtn is clicked

        movesInput.setText("0");
        timerInput.setText("60");

        firstCard = null;
        secondCard = null;

        setMatchingPairImages();

        // Assign blank icons and enable all cards
        for (int i = 0; i < cards.size(); i++) {
            JButton card = cards.get(i);
            card.setIcon(blank); 
            card.setEnabled(true);
            card.putClientProperty("icon", icons.get(i)); //Each card is assigned a new random icon from the shuffled icons list.
        }
    }
    

    private void startGame() {
        gameActive = true; // Enable card flipping
        for (int i = 0; i < cards.size(); i++) {
           // Get the icon stored in the card's "icon" property and display it
            cards.get(i).setIcon((ImageIcon) cards.get(i).getClientProperty("icon"));
        }

        Timer solutionTimer = new Timer(5000, e -> {
            for (JButton card : cards) {
                card.setIcon(blank);
            }
            startCountdownTimer(); 
        });
        solutionTimer.setRepeats(false);
        solutionTimer.start();
    }
    

    private void startCountdownTimer() {
       timeLeft = totalTime; 
       timerInput.setText(String.valueOf(timeLeft)); 
    
       countdownTimer = new Timer(1000, e -> {
          timeLeft--; // Decrement time left
          timerInput.setText(String.valueOf(timeLeft)); 

            if (timeLeft <= 0) { 
              countdownTimer.stop();
              JOptionPane.showMessageDialog(this, "Time's up! You lose!");
              resetGame();
            }
        });
       countdownTimer.start();
    }
    
    private void handleCardClick(JButton clickedCard) {
        if (!gameActive || !clickedCard.isEnabled()) {
            return; // Ignore clicks if the game is inactive or card is disabled
        }

        if (firstCard == null) {
           firstCard = clickedCard;
           firstCard.setIcon((ImageIcon) firstCard.getClientProperty("icon"));
        } 
        else if (secondCard == null && clickedCard != firstCard) {
          secondCard = clickedCard;
          secondCard.setIcon((ImageIcon) secondCard.getClientProperty("icon"));
         checkMatch();
        }
    }

    private void checkMatch() {
       moves++;
       movesInput.setText(String.valueOf(moves));

       if (firstCard.getClientProperty("icon").equals(secondCard.getClientProperty("icon"))) {
          firstCard.setEnabled(false);
          secondCard.setEnabled(false);

          matchedPairs++;
          firstCard = null;
          secondCard = null;

          checkWinCondition();
        } else {
            flipBackTimer = new Timer(1000, e -> {
                firstCard.setIcon(blank); // Reset to blank
                secondCard.setIcon(blank); // Reset to blank
                firstCard = null;
                secondCard = null;
                flipBackTimer.stop();
            });
            flipBackTimer.setRepeats(false);
            flipBackTimer.start();
        }
    }


    private void checkWinCondition() {
        if (matchedPairs == icons.size() / 2) { 
            countdownTimer.stop();
           int timeTaken = totalTime - timeLeft; 
           JOptionPane.showMessageDialog(this, "You win! Moves: " + moves + " Time taken: " + timeTaken + " seconds");
            resetGame();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        carte4 = new javax.swing.JButton();
        carte2 = new javax.swing.JButton();
        carte3 = new javax.swing.JButton();
        carte1 = new javax.swing.JButton();
        carte5 = new javax.swing.JButton();
        carte6 = new javax.swing.JButton();
        carte7 = new javax.swing.JButton();
        carte8 = new javax.swing.JButton();
        carte9 = new javax.swing.JButton();
        carte10 = new javax.swing.JButton();
        carte11 = new javax.swing.JButton();
        carte12 = new javax.swing.JButton();
        carte13 = new javax.swing.JButton();
        carte14 = new javax.swing.JButton();
        carte15 = new javax.swing.JButton();
        carte16 = new javax.swing.JButton();
        playBtn = new javax.swing.JButton();
        restartBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        timerInput = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        movesInput = new javax.swing.JTextField();
        exitBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hard Memory Game ");

        jLabel1.setFont(new java.awt.Font("Sitka Heading", 3, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tpfinalihm/video-games.png"))); // NOI18N
        jLabel1.setText(" Memory Game ");

        jPanel1.setBackground(new java.awt.Color(227, 220, 220));

        carte4.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte4ActionPerformed(evt);
            }
        });

        carte2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte2ActionPerformed(evt);
            }
        });

        carte3.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte3ActionPerformed(evt);
            }
        });

        carte1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte1ActionPerformed(evt);
            }
        });

        carte5.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte5ActionPerformed(evt);
            }
        });

        carte6.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte6ActionPerformed(evt);
            }
        });

        carte7.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte7ActionPerformed(evt);
            }
        });

        carte8.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte8ActionPerformed(evt);
            }
        });

        carte9.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte9ActionPerformed(evt);
            }
        });

        carte10.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte10ActionPerformed(evt);
            }
        });

        carte11.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte11ActionPerformed(evt);
            }
        });

        carte12.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte12ActionPerformed(evt);
            }
        });

        carte13.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte13ActionPerformed(evt);
            }
        });

        carte14.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte14ActionPerformed(evt);
            }
        });

        carte15.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte15ActionPerformed(evt);
            }
        });

        carte16.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 102)));
        carte16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carte16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(carte13, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carte14, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carte15, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carte16, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(carte9, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carte10, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(carte11, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carte12, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(carte1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carte2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carte3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carte4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(carte5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carte6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carte7, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carte8, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(carte2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carte1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carte3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carte4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(carte5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(carte6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(carte8, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(carte7, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(carte9, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carte10, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carte11, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carte12, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(carte13, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carte14, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carte15, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carte16, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        playBtn.setBackground(new java.awt.Color(0, 102, 102));
        playBtn.setFont(new java.awt.Font("Segoe UI Black", 3, 15)); // NOI18N
        playBtn.setForeground(new java.awt.Color(0, 204, 204));
        playBtn.setText("Play");
        playBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 153, 153)));
        playBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playBtnActionPerformed(evt);
            }
        });

        restartBtn.setBackground(new java.awt.Color(0, 204, 204));
        restartBtn.setFont(new java.awt.Font("Segoe UI", 3, 15)); // NOI18N
        restartBtn.setForeground(new java.awt.Color(0, 102, 102));
        restartBtn.setText("Restart");
        restartBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 102, 102)));
        restartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 15)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tpfinalihm/time-and-date.png"))); // NOI18N
        jLabel2.setText("Timer :");

        timerInput.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        timerInput.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 102, 102)));
        timerInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timerInputActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 15)); // NOI18N
        jLabel4.setText("Difficulty :");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hard", "Medium", "Easy" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 102, 102)));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 15)); // NOI18N
        jLabel3.setText("Moves :");

        movesInput.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        movesInput.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 102, 102)));
        movesInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movesInputActionPerformed(evt);
            }
        });

        exitBtn.setBackground(new java.awt.Color(153, 0, 0));
        exitBtn.setFont(new java.awt.Font("Segoe UI Black", 3, 15)); // NOI18N
        exitBtn.setForeground(new java.awt.Color(211, 163, 163));
        exitBtn.setText("Exit");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timerInput, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(playBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(restartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(movesInput, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(exitBtn)
                        .addGap(87, 87, 87))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(timerInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(restartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(movesInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void carte11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte11ActionPerformed
        // TODO add your handling code here:
         handleCardClick(carte11);
    }//GEN-LAST:event_carte11ActionPerformed

    private void carte12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte12ActionPerformed
        // TODO add your handling code here:
        handleCardClick(carte12);
    }//GEN-LAST:event_carte12ActionPerformed

    private void carte4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte4ActionPerformed
        // TODO add your handling code here:
        handleCardClick(carte4);
    }//GEN-LAST:event_carte4ActionPerformed

    private void carte1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte1ActionPerformed
        // TODO add your handling code here:
         handleCardClick(carte1);
    }//GEN-LAST:event_carte1ActionPerformed

    private void carte2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte2ActionPerformed
        // TODO add your handling code here:
          handleCardClick(carte2);
    }//GEN-LAST:event_carte2ActionPerformed

    private void carte3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte3ActionPerformed
        // TODO add your handling code here:
       handleCardClick(carte3);
    }//GEN-LAST:event_carte3ActionPerformed

    private void carte5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte5ActionPerformed
        // TODO add your handling code here:
         handleCardClick(carte5);
    }//GEN-LAST:event_carte5ActionPerformed

    private void carte6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte6ActionPerformed
        // TODO add your handling code here:
       handleCardClick(carte6);
    }//GEN-LAST:event_carte6ActionPerformed

    private void carte7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte7ActionPerformed
        // TODO add your handling code here:
       handleCardClick(carte7);
    }//GEN-LAST:event_carte7ActionPerformed

    private void carte8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte8ActionPerformed
        // TODO add your handling code here:
       handleCardClick(carte8);
    }//GEN-LAST:event_carte8ActionPerformed

    private void carte9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte9ActionPerformed
        // TODO add your handling code here:
         handleCardClick(carte9);
    }//GEN-LAST:event_carte9ActionPerformed

    private void carte10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte10ActionPerformed
        // TODO add your handling code here:
        handleCardClick(carte10);
    }//GEN-LAST:event_carte10ActionPerformed

    private void timerInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timerInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timerInputActionPerformed

    private void movesInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movesInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_movesInputActionPerformed

    private void playBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playBtnActionPerformed
        // TODO add your handling code here:      
        startGame();
    }//GEN-LAST:event_playBtnActionPerformed

    private void restartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartBtnActionPerformed
        // TODO add your handling code here:
       resetGame(); // Start a new game
    }//GEN-LAST:event_restartBtnActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        String selectedDifficulty = (String) jComboBox1.getSelectedItem();
        switch (selectedDifficulty) {
        case "Medium" -> {
            // Open the MediumGame JFrame
            MediumGame mediumGame = new MediumGame();
            mediumGame.setVisible(true);
            this.dispose(); 
            }
        
        case "Easy" -> {
            EasyGame easyGame = new EasyGame();
            easyGame.setVisible(true);
            this.dispose();
            }
        
        case "Hard" ->
            this.setVisible(true); 
        
        default -> JOptionPane.showMessageDialog(this, "Invalid selection.");
    }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        // TODO add your handling code here:   
       int confirm = JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
       
         if (confirm == JOptionPane.YES_OPTION) {
             System.exit(0);
    }
    }//GEN-LAST:event_exitBtnActionPerformed

    private void carte13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte13ActionPerformed
        // TODO add your handling code here:
        handleCardClick(carte13);
    }//GEN-LAST:event_carte13ActionPerformed

    private void carte14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte14ActionPerformed
        // TODO add your handling code here:
        handleCardClick(carte14);
    }//GEN-LAST:event_carte14ActionPerformed

    private void carte15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte15ActionPerformed
        // TODO add your handling code here:
        handleCardClick(carte15);
    }//GEN-LAST:event_carte15ActionPerformed

    private void carte16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte16ActionPerformed
        // TODO add your handling code here:
        handleCardClick(carte16);
    }//GEN-LAST:event_carte16ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new MemoryGame().setVisible(true));

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton carte1;
    private javax.swing.JButton carte10;
    private javax.swing.JButton carte11;
    private javax.swing.JButton carte12;
    private javax.swing.JButton carte13;
    private javax.swing.JButton carte14;
    private javax.swing.JButton carte15;
    private javax.swing.JButton carte16;
    private javax.swing.JButton carte2;
    private javax.swing.JButton carte3;
    private javax.swing.JButton carte4;
    private javax.swing.JButton carte5;
    private javax.swing.JButton carte6;
    private javax.swing.JButton carte7;
    private javax.swing.JButton carte8;
    private javax.swing.JButton carte9;
    private javax.swing.JButton exitBtn;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField movesInput;
    private javax.swing.JButton playBtn;
    private javax.swing.JButton restartBtn;
    private javax.swing.JTextField timerInput;
    // End of variables declaration//GEN-END:variables



}

