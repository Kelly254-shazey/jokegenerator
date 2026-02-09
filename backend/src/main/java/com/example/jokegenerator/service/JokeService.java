package com.example.jokegenerator.service;

import com.example.jokegenerator.entity.Joke;
import com.example.jokegenerator.entity.JokeReaction;
import com.example.jokegenerator.entity.Share;
import com.example.jokegenerator.repository.JokeRepository;
import com.example.jokegenerator.repository.JokeReactionRepository;
import com.example.jokegenerator.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class JokeService {

    @Autowired private JokeRepository jokeRepository;
    @Autowired private JokeReactionRepository jokeReactionRepository;
    @Autowired private ShareRepository shareRepository;
    @Autowired private MonetizationService monetizationService;

    private final Random random = new Random();

    /**
     * Get random joke by category with premium filter
     */
    public Joke getRandomJoke(String category, boolean isPremium) {
        List<Joke> jokes;
        
        if (category == null || category.isEmpty()) {
            // No category filter
            jokes = isPremium ? jokeRepository.findByIsSafeTrue() : 
                              jokeRepository.findByIsSafeTrueAndIsPremiumFalse();
        } else {
            // Filter by category
            jokes = isPremium ? jokeRepository.findByCategoryAndIsSafeTrue(category) :
                              jokeRepository.findByCategoryAndIsSafeTrueAndIsPremiumFalse(category);
        }
        
        if (jokes == null || jokes.isEmpty()) {
            return null;
        }
        
        return jokes.get(random.nextInt(jokes.size()));
    }

    /**
     * Get trending joke of the moment
     */
    public Joke getJokeOfTheMoment() {
        List<Joke> trending = jokeRepository.findTrendingJokes();
        if (trending != null && !trending.isEmpty()) {
            return trending.get(0);
        }
        return getRandomJoke(null, false);
    }

    /**
     * Add reaction to joke
     */
    @Transactional
    public void addReaction(Long userId, Long jokeId, String reactionType) {
        if (jokeReactionRepository.existsByUserIdAndJokeIdAndReactionType(userId, jokeId, reactionType)) {
            return; // User already reacted with this emoji
        }

        JokeReaction reaction = new JokeReaction();
        reaction.setUserId(userId);
        reaction.setJokeId(jokeId);
        reaction.setReactionType(reactionType);
        jokeReactionRepository.save(reaction);
    }

    /**
     * Share joke on social platform
     */
    @Transactional
    public String shareJoke(Long userId, Long jokeId, String platform) {
        Joke joke = jokeRepository.findById(jokeId).orElse(null);
        if (joke == null) {
            return null;
        }

        // Record share
        Share share = new Share();
        share.setUserId(userId);
        share.setContentType("joke");
        share.setContentId(jokeId);
        share.setPlatform(platform != null ? platform : "unknown");
        shareRepository.save(share);

        // Update joke share count
        joke.setShareCount(joke.getShareCount() + 1);
        jokeRepository.save(joke);

        // Award share bonus
        if (monetizationService != null) {
            monetizationService.awardShareBonus(userId, "joke");
        }

        return generateShareableContent(joke);
    }

    /**
     * Generate shareable content with watermark
     */
    private String generateShareableContent(Joke joke) {
        return joke.getContent() + "\n\n🧠 Shared from BrainBreak - Where Fun Meets Mind! 🎮\n#BrainBreak #CleanJokes";
    }

    /**
     * Add new joke
     */
    @Transactional
    public Joke addJoke(String category, String content, boolean isSafe, boolean isPremium, Long uploadedBy) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }

        Joke joke = new Joke();
        joke.setCategory(category.toLowerCase().trim());
        joke.setContent(content.trim());
        joke.setSafe(isSafe);
        joke.setPremium(isPremium);
        joke.setUploadedBy(uploadedBy);
        joke.setShareCount(0);
        
        return jokeRepository.save(joke);
    }

    /**
     * Get jokes by category
     */
    public List<Joke> getJokesByCategory(String category, boolean isPremium) {
        if (isPremium) {
            return jokeRepository.findByCategoryAndIsSafeTrue(category);
        } else {
            return jokeRepository.findByCategoryAndIsSafeTrueAndIsPremiumFalse(category);
        }
    }

    /**
     * Get all jokes (admin)
     */
    public List<Joke> getAllJokes() {
        return jokeRepository.findAll();
    }

    /**
     * Delete joke (admin/owner only)
     */
    @Transactional
    public void deleteJoke(Long jokeId) {
        jokeRepository.deleteById(jokeId);
    }
}