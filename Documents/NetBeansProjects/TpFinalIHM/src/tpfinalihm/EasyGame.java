/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tpfinalihm;
import javax.swing.ImageIcon;    
import javax.swing.*;
import java.util.ArrayList;  
import java.util.Collections;
import tpfinalihm.MediumGame;
import tpfinalihm.MemoryGame;
/**
 *
 * @author user22µ
 */
public class EasyGame extends javax.swing.JFrame {

    /**
     * Creates new form EasyGame
     */
    private ArrayList<JButton> cards;
    private ArrayList<ImageIcon> icons;
    private JButton firstCard, secondCard;
    private Timer flipBackTimer, countdownTimer;
    private int moves = 0, timeLeft = 60, matchedPairs = 0;
    private boolean gameActive = false; // Flag to track if the game has started

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

    public EasyGame() {
        initComponents();
        initializeGame();
    }

    private void initializeGame() {
        cards = new ArrayList<>();
        icons = new ArrayList<>();

        cards.add(carte1);
        cards.add(carte2);
        cards.add(carte3);
        cards.add(carte4);
        cards.add(carte5);
        cards.add(carte6);
        cards.add(carte7);
        cards.add(carte8);

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

        // Create matching pairs
        icons = new ArrayList<>(selectedIcons);
        icons.addAll(selectedIcons); // Duplicate for pairs
        
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
            card.putClientProperty("icon", icons.get(i)); 
        }
    }

    private void startGame() {
        gameActive = true; // Enable card flipping
        for (int i = 0; i < cards.size(); i++) {
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


    private int totalTime = 60; 

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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        movesInput = new javax.swing.JTextField();
        exitBtn = new javax.swing.JButton();
        playBtn = new javax.swing.JButton();
        restartBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        timerInput = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        carte4 = new javax.swing.JButton();
        carte2 = new javax.swing.JButton();
        carte3 = new javax.swing.JButton();
        carte1 = new javax.swing.JButton();
        carte5 = new javax.swing.JButton();
        carte6 = new javax.swing.JButton();
        carte7 = new javax.swing.JButton();
        carte8 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Easy Memory Game");

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

        jLabel1.setFont(new java.awt.Font("Sitka Heading", 3, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tpfinalihm/video-games.png"))); // NOI18N
        jLabel1.setText(" Memory Game ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 15)); // NOI18N
        jLabel4.setText("Difficulty :");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addContainerGap(76, Short.MAX_VALUE))
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(carte8, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(carte7, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Easy", "Medium", "Hard" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 102, 102)));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 15)); // NOI18N
        jLabel3.setText("Moves :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(253, 253, 253)
                            .addComponent(jLabel1))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(53, 53, 53)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(timerInput, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(82, 82, 82)
                            .addComponent(playBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(restartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(movesInput, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(exitBtn)
                .addGap(87, 87, 87))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(timerInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(restartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(movesInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void movesInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movesInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_movesInputActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_exitBtnActionPerformed

    private void playBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playBtnActionPerformed
        // TODO add your handling code here:
        startGame();
    }//GEN-LAST:event_playBtnActionPerformed

    private void restartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartBtnActionPerformed
        // TODO add your handling code here:
        resetGame(); // Start a new game
    }//GEN-LAST:event_restartBtnActionPerformed

    private void timerInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timerInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timerInputActionPerformed

    private void carte4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte4ActionPerformed
        // TODO add your handling code here:
        handleCardClick(carte4);
    }//GEN-LAST:event_carte4ActionPerformed

    private void carte2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte2ActionPerformed
        // TODO add your handling code here:
        handleCardClick(carte2);
    }//GEN-LAST:event_carte2ActionPerformed

    private void carte3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte3ActionPerformed
        // TODO add your handling code here:
        handleCardClick(carte3);
    }//GEN-LAST:event_carte3ActionPerformed

    private void carte1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carte1ActionPerformed
        // TODO add your handling code here:
        handleCardClick(carte1);
    }//GEN-LAST:event_carte1ActionPerformed

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

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:   
            String selectedDifficulty = (String) jComboBox1.getSelectedItem();
        
        // Check the selected difficulty level and navigate to the corresponding game
        switch (selectedDifficulty) {
            case "Easy":
                // Stay in the current Easy game
                break;

            case "Medium":
                // Open the MediumGame JFrame
                MediumGame mediumGame = new MediumGame();
                mediumGame.setVisible(true);
                this.setVisible(false); // Hide the current frame (EasyGame)
                break;

            case "Hard":
                // Open the HardMemoryGame JFrame
                MemoryGame memoryGame = new MemoryGame();
                memoryGame.setVisible(true);
                this.setVisible(false); // Hide the current frame (EasyGame)
                break;

            default:
                JOptionPane.showMessageDialog(this, "Invalid selection.");
                break;
        }
    
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EasyGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EasyGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EasyGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EasyGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EasyGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton carte1;
    private javax.swing.JButton carte2;
    private javax.swing.JButton carte3;
    private javax.swing.JButton carte4;
    private javax.swing.JButton carte5;
    private javax.swing.JButton carte6;
    private javax.swing.JButton carte7;
    private javax.swing.JButton carte8;
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
