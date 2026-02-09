package com.example.jokegenerator.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jokegenerator.entity.GameAttempt;
import com.example.jokegenerator.entity.Room;
import com.example.jokegenerator.entity.SinglePlayerGame;
import com.example.jokegenerator.entity.User;
import com.example.jokegenerator.repository.GameAttemptRepository;
import com.example.jokegenerator.repository.RoomRepository;
import com.example.jokegenerator.repository.SinglePlayerGameRepository;

@Service
public class GameService {

    @Autowired
    private SinglePlayerGameRepository singlePlayerGameRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GameAttemptRepository gameAttemptRepository;

    @Autowired
    private UserService userService;

    private final Random random = new Random();

    // Single-player game
    public SinglePlayerGame startSinglePlayerGame(User user) {
        SinglePlayerGame game = new SinglePlayerGame();
        game.setUser(user);
        game.setSecretNumber(random.nextInt(100) + 1); // 1-100
        return singlePlayerGameRepository.save(game);
    }

    public String guessSinglePlayer(Long gameId, int guess, User user) {
        Optional<SinglePlayerGame> optGame = singlePlayerGameRepository.findById(gameId);
        if (optGame.isEmpty() || !optGame.get().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Game not found or not yours");
        }
        SinglePlayerGame game = optGame.get();
        if (game.getStatus() != SinglePlayerGame.GameStatus.ACTIVE) {
            return "Game already finished";
        }
        game.setAttempts(game.getAttempts() + 1);
        String feedback;
        if (guess == game.getSecretNumber()) {
            feedback = "Correct! You won!";
            game.setStatus(SinglePlayerGame.GameStatus.WON);
            userService.updateWallet(user, 10); // Reward coins
        } else if (guess < game.getSecretNumber()) {
            feedback = isClose(guess, game.getSecretNumber()) ? "Too low, but very close! 🔥" : "Too low";
        } else {
            feedback = isClose(guess, game.getSecretNumber()) ? "Too high, but very close! 🔥" : "Too high";
        }
        singlePlayerGameRepository.save(game);
        return feedback;
    }

    // Multiplayer
    public Room createRoom(User creator) {
        Room room = new Room();
        room.setRoomCode(generateRoomCode());
        room.setCreator(creator);
        room.setSecretNumber(random.nextInt(100) + 1);
        room.setCurrentTurn(creator);
        return roomRepository.save(room);
    }

    public Room joinRoom(String roomCode, User player2) {
        Optional<Room> optRoom = roomRepository.findByRoomCode(roomCode);
        if (optRoom.isEmpty() || optRoom.get().getPlayer2() != null) {
            throw new RuntimeException("Room not found or full");
        }
        Room room = optRoom.get();
        room.setPlayer2(player2);
        room.setStatus(Room.RoomStatus.ACTIVE);
        return roomRepository.save(room);
    }

    public String guessMultiplayer(Long roomId, int guess, User user) {
        Optional<Room> optRoom = roomRepository.findById(roomId);
        if (optRoom.isEmpty()) {
            throw new RuntimeException("Room not found");
        }
        Room room = optRoom.get();
        if (!room.getCurrentTurn().getId().equals(user.getId()) || room.getStatus() != Room.RoomStatus.ACTIVE) {
            return "Not your turn or game not active";
        }
        GameAttempt attempt = new GameAttempt();
        attempt.setRoom(room);
        attempt.setUser(user);
        attempt.setGuess(guess);
        attempt.setAttemptNumber(room.getGameAttempts().size() + 1);
        String feedback;
        if (guess == room.getSecretNumber()) {
            feedback = "Correct! " + user.getUsername() + " wins!";
            room.setWinner(user);
            room.setStatus(Room.RoomStatus.FINISHED);
            userService.updateWallet(user, 20); // Reward more for multiplayer win
        } else if (guess < room.getSecretNumber()) {
            feedback = isClose(guess, room.getSecretNumber()) ? "Too low, but very close! 🔥" : "Too low";
        } else {
            feedback = isClose(guess, room.getSecretNumber()) ? "Too high, but very close! 🔥" : "Too high";
        }
        attempt.setFeedback(feedback);
        gameAttemptRepository.save(attempt);
        // Switch turn
        room.setCurrentTurn(room.getCurrentTurn().getId().equals(room.getCreator().getId()) ? room.getPlayer2() : room.getCreator());
        roomRepository.save(room);
        return feedback;
    }

    private String generateRoomCode() {
        return String.valueOf(100000 + random.nextInt(900000)); // 6-digit code
    }

    private boolean isClose(int guess, int target) {
        return Math.abs(guess - target) <= 5;
    }
}
