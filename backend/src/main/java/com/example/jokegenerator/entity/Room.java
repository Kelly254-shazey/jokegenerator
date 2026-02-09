package com.example.jokegenerator.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roomCode;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(name = "player2_id")
    private User player2;

    @Column(nullable = false)
    private int secretNumber;

    @ManyToOne
    @JoinColumn(name = "current_turn", nullable = false)
    private User currentTurn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status = RoomStatus.WAITING;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private User winner;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum RoomStatus {
        WAITING, ACTIVE, FINISHED
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoomCode() { return roomCode; }
    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }

    public User getCreator() { return creator; }
    public void setCreator(User creator) { this.creator = creator; }

    public User getPlayer2() { return player2; }
    public void setPlayer2(User player2) { this.player2 = player2; }

    public int getSecretNumber() { return secretNumber; }
    public void setSecretNumber(int secretNumber) { this.secretNumber = secretNumber; }

    public User getCurrentTurn() { return currentTurn; }
    public void setCurrentTurn(User currentTurn) { this.currentTurn = currentTurn; }

    public RoomStatus getStatus() { return status; }
    public void setStatus(RoomStatus status) { this.status = status; }

    public User getWinner() { return winner; }
    public void setWinner(User winner) { this.winner = winner; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Relationship to GameAttempts
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GameAttempt> gameAttempts = new ArrayList<>();

    public List<GameAttempt> getGameAttempts() { return gameAttempts; }
    public void setGameAttempts(List<GameAttempt> gameAttempts) { this.gameAttempts = gameAttempts; }
}
