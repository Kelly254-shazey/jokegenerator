package com.example.jokegenerator.config;

import com.example.jokegenerator.entity.Joke;
import com.example.jokegenerator.repository.JokeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JokeRepository jokeRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if no jokes exist
        if (jokeRepository.count() == 0) {
            initializeJokes();
        }
    }

    private void initializeJokes() {
        // Campus Life jokes
        addJoke("campus", "Why did the student go to the library? Because they heard the books were well-rounded!", true, false, null);
        addJoke("campus", "What do you call a student who studies in the library? Bookworm!", true, false, null);
        addJoke("campus", "Why don't students ever graduate on time? Because time is relative!", true, false, null);
        addJoke("campus", "How many students does it take to change a light bulb? One, and everyone else just tweets about how much better the old light was.", true, false, null);
        addJoke("campus", "What's the difference between a student and a pizza? A pizza can feed a family of four.", true, false, null);

        // Tech & Coding jokes
        addJoke("tech", "Why do Java developers wear glasses? Because they can't C#!", true, false, null);
        addJoke("tech", "How many programmers does it take to change a light bulb? None, that's a hardware problem!", true, false, null);
        addJoke("tech", "Why did the developer go broke? Because he used up all his cache!", true, false, null);
        addJoke("tech", "Why do programmers prefer dark mode? Because light attracts bugs!", true, false, null);
        addJoke("tech", "A SQL query walks into a bar, walks up to two tables and asks: Can I join you?", true, false, null);

        // Everyday Life jokes
        addJoke("life", "I told my computer I needed a break. Now it won't stop sending me Kit-Kat ads.", true, false, null);
        addJoke("life", "Why did the coffee file a police report? It got mugged!", true, false, null);
        addJoke("life", "I'm reading a book on the history of glue – can't put it down.", true, false, null);
        addJoke("life", "Did you hear about the guy who invented Lifesavers? He made a mint!", true, false, null);
        addJoke("life", "I told my wife she was drawing her eyebrows too high. She looked surprised.", true, false, null);

        // Motivation jokes
        addJoke("motivation", "Don't watch the clock; do what it does. Keep going!", true, false, null);
        addJoke("motivation", "The best time to plant a tree was 20 years ago. The second best time is now.", true, false, null);
        addJoke("motivation", "Your limitation—it's only your imagination. Dream big and work hard!", true, false, null);
        addJoke("motivation", "Success is not final, failure is not fatal. It's the courage to continue that counts.", true, false, null);
        addJoke("motivation", "Wake up with determination. Go to bed with satisfaction.", true, false, null);

        // General Fun jokes
        addJoke("general", "What did one ocean say to the other? Nothing, they just wave!", true, false, null);
        addJoke("general", "Why don't scientists trust atoms? Because they make up everything!", true, false, null);
        addJoke("general", "What do you call a bear with no teeth? A gummy bear!", true, false, null);
        addJoke("general", "Why did the scarecrow win an award? Because he was outstanding in his field!", true, false, null);
        addJoke("general", "What did the ocean say? Just water you doing here!", true, false, null);

        System.out.println("✅ Database initialized with 25 sample jokes!");
    }

    private void addJoke(String category, String content, boolean isSafe, boolean isPremium, Long uploadedBy) {
        Joke joke = new Joke();
        joke.setCategory(category);
        joke.setContent(content);
        joke.setSafe(isSafe);
        joke.setPremium(isPremium);
        joke.setUploadedBy(uploadedBy);
        joke.setShareCount(0);
        jokeRepository.save(joke);
    }
}
