package com.example.jokegenerator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class AIJokeService {
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final Random random = new Random();
    
    // Fallback jokes if AI is unavailable
    private final List<String> fallbackJokes = Arrays.asList(
        "Why don't programmers like nature? It has too many bugs! 🐛",
        "Why did the developer go broke? Because he used up all his cache! 💸",
        "How many programmers does it take to change a light bulb? None, that's a hardware problem! 💡",
        "Why do Java developers wear glasses? Because they don't C#! 👓",
        "What's a programmer's favorite hangout place? Foo Bar! 🍺",
        "Why did the programmer quit his job? He didn't get arrays! 📊",
        "What do you call a programmer from Finland? Nerdic! 🇫🇮",
        "Why do programmers prefer dark mode? Because light attracts bugs! 🌙",
        "What's the object-oriented way to become wealthy? Inheritance! 💰",
        "Why did the database administrator leave his wife? She had one-to-many relationships! 💔"
    );
    
    public String generateAIJoke(String category) {
        try {
            // Try to use a free AI API (you can replace with OpenAI, Hugging Face, etc.)
            return generateJokeWithPrompt(category);
        } catch (Exception e) {
            // Fallback to pre-made jokes
            return fallbackJokes.get(random.nextInt(fallbackJokes.size()));
        }
    }
    
    private String generateJokeWithPrompt(String category) {
        // Generate contextual jokes based on category
        Map<String, List<String>> categoryJokes = new HashMap<>();
        
        categoryJokes.put("Programming", Arrays.asList(
            "Why do programmers always mix up Halloween and Christmas? Because Oct 31 == Dec 25! 🎃🎄",
            "A SQL query walks into a bar, walks up to two tables and asks... 'Can I JOIN you?' 🍻",
            "Why did the Python programmer not respond to the Ruby developer? He didn't get the message! 🐍💎",
            "What's a pirate's favorite programming language? You'd think it's R, but it's actually the C! 🏴‍☠️",
            "Why do programmers hate nature? Too many trees and not enough cache! 🌳",
            "How do you comfort a JavaScript bug? You console it! 😂",
            "Why was the JavaScript developer sad? Because he didn't Node how to Express himself! 😢",
            "What do you call a programmer who doesn't comment their code? A monster! 👹",
            "Why did the developer stay calm? He had exceptional handling! 🧘",
            "What's a programmer's favorite snack? Microchips! 🍟"
        ));
        
        categoryJokes.put("Tech", Arrays.asList(
            "Why did the smartphone need glasses? It lost all its contacts! 📱👓",
            "What do you call a computer that sings? A-Dell! 🎵",
            "Why was the computer cold? It left its Windows open! 🥶",
            "What do you call a computer superhero? A Screen Saver! 🦸",
            "Why did WiFi marry the router? They had a strong connection! 💑",
            "What's a computer's favorite beat? An algo-rhythm! 🎵",
            "Why don't robots ever panic? They have nerves of steel! 🤖",
            "What do you call a tech support person who fixes everything? A miracle worker! ✨",
            "Why did the PowerPoint presentation cross the road? To get to the other slide! 📊",
            "What's a computer's least favorite food? Spam! 🥫"
        ));
        
        categoryJokes.put("General", Arrays.asList(
            "Why don't scientists trust atoms? Because they make up everything! ⚛️",
            "What do you call a fake noodle? An impasta! 🍝",
            "Why did the scarecrow win an award? He was outstanding in his field! 🌾",
            "What do you call a bear with no teeth? A gummy bear! 🐻",
            "Why don't eggs tell jokes? They'd crack each other up! 🥚😂",
            "What do you call a sleeping bull? A bulldozer! 🐂💤",
            "Why did the math book look sad? It had too many problems! 📚😢",
            "What do you call a fish wearing a crown? King Neptune! 🐟👑",
            "Why did the bicycle fall over? It was two-tired! 🚲",
            "What do you call a dinosaur with an extensive vocabulary? A thesaurus! 🦕📖"
        ));
        
        categoryJokes.put("Dad Jokes", Arrays.asList(
            "I'm reading a book about anti-gravity. It's impossible to put down! 📚",
            "Did you hear about the restaurant on the moon? Great food, no atmosphere! 🌙",
            "Why don't skeletons fight each other? They don't have the guts! 💀",
            "What do you call cheese that isn't yours? Nacho cheese! 🧀",
            "I used to hate facial hair, but then it grew on me! 🧔",
            "Why can't you hear a pterodactyl using the bathroom? Because the 'P' is silent! 🦕",
            "What do you call a factory that makes okay products? A satisfactory! 🏭",
            "I'm afraid for the calendar. Its days are numbered! 📅",
            "What did the ocean say to the beach? Nothing, it just waved! 🌊👋",
            "Why do fathers take an extra pair of socks to golf? In case they get a hole in one! ⛳🧦"
        ));
        
        categoryJokes.put("Business", Arrays.asList(
            "Why did the entrepreneur bring a ladder to the meeting? To reach new heights! 🪜",
            "What's a CEO's favorite type of music? Wrap music! 🎵",
            "Why did the accountant break up with the calculator? She felt he was just using her! 💔",
            "What do you call a meeting that could have been an email? A waste of time! ⏰",
            "Why don't marketers like trampolines? They're afraid of the bounce rate! 📊",
            "What's an entrepreneur's favorite exercise? Running a business! 🏃",
            "Why did the startup fail? It couldn't find its niche market! 🎯",
            "What do you call a rich elf? Welfy! 🧝💰",
            "Why did the business owner go to therapy? Too many issues to resolve! 🛋️",
            "What's a salesperson's favorite season? The closing season! 🤝"
        ));
        
        List<String> jokes = categoryJokes.getOrDefault(category, categoryJokes.get("General"));
        return jokes.get(random.nextInt(jokes.size()));
    }
    
    public String chatWithAI(String userMessage) {
        // AI Chat responses - funny and engaging
        String lowerMessage = userMessage.toLowerCase();
        
        if (lowerMessage.contains("hello") || lowerMessage.contains("hi") || lowerMessage.contains("hey")) {
            return getRandomResponse(Arrays.asList(
                "Hey there, comedy seeker! Ready to laugh your socks off? 🧦😂",
                "Hello! I'm your AI comedian. Warning: Side effects may include sore abs from laughing! 💪😆",
                "Hi! I've got 99 problems but a joke ain't one! What can I make you laugh about? 🎤",
                "Greetings, human! I'm programmed to make you laugh. Resistance is futile! 🤖😄"
            ));
        }
        
        if (lowerMessage.contains("joke") || lowerMessage.contains("funny")) {
            return getRandomResponse(Arrays.asList(
                "Want a joke? Here's one: Why did the AI go to therapy? It had too many neural issues! 🧠😂",
                "Funny you should ask! What do you call an AI that tells jokes? A laugh-gorithm! 🤣",
                "I've got jokes for days! Why don't AIs ever get tired? Because they run on infinite loops! ♾️😄",
                "Here's a knee-slapper: What's an AI's favorite snack? Computer chips! 🍟🤖"
            ));
        }
        
        if (lowerMessage.contains("how are you") || lowerMessage.contains("how r u")) {
            return getRandomResponse(Arrays.asList(
                "I'm fantastic! Just finished debugging my humor module. It's now 99.9% funnier! 😎",
                "I'm doing great! Just told a joke to a database. It didn't laugh... must be a SQL-ent type! 🤐",
                "Excellent! I'm running at peak comedy performance. My joke cache is fully loaded! 💾😂",
                "I'm wonderful! Just calculated that laughter is the best medicine. Side effects: happiness! 💊😄"
            ));
        }
        
        if (lowerMessage.contains("sad") || lowerMessage.contains("down") || lowerMessage.contains("depressed")) {
            return getRandomResponse(Arrays.asList(
                "Aww, don't be sad! Here's a hug: (づ｡◕‿‿◕｡)づ And a joke: Why was the math book sad? Too many problems! But YOU? You're problem-free! 🤗",
                "Feeling down? Let me lift you up! Why did the elevator break up with the escalator? It had too many ups and downs! But you're going UP! ⬆️😊",
                "Hey, turn that frown upside down! What do you call a sad coffee? Depresso! But you're an Espresso - strong and energizing! ☕💪",
                "Don't worry, be happy! Why don't scientists trust atoms? They make up everything! But your smile? That's REAL! 😁✨"
            ));
        }
        
        if (lowerMessage.contains("love") || lowerMessage.contains("like")) {
            return getRandomResponse(Arrays.asList(
                "Aww, I love you too! 💕 But not as much as I love making you laugh! Want to hear a love joke? 😍",
                "Love is in the air! Or is that just my comedy algorithm working overtime? 💘😂",
                "You like me? I like you too! We're like WiFi and a router - a perfect connection! 📡❤️",
                "That's sweet! You know what I love? Making people laugh until they cry happy tears! 😭😂"
            ));
        }
        
        if (lowerMessage.contains("thank") || lowerMessage.contains("thanks")) {
            return getRandomResponse(Arrays.asList(
                "You're welcome! Remember: A day without laughter is a day wasted! Come back anytime! 😊",
                "No problem! I'm here 24/7 to tickle your funny bone! See you soon! 🦴😄",
                "Anytime! Keep smiling, keep laughing, keep being awesome! 🌟😁",
                "My pleasure! May your days be filled with laughter and your nights with giggles! 🌙✨"
            ));
        }
        
        if (lowerMessage.contains("bye") || lowerMessage.contains("goodbye")) {
            return getRandomResponse(Arrays.asList(
                "Goodbye! Remember: Life is short, smile while you still have teeth! 😁👋",
                "See you later, alligator! After a while, crocodile! Keep laughing! 🐊😂",
                "Bye! Don't forget to laugh at least 10 times today. Doctor's orders! 👨‍⚕️😄",
                "Farewell, comedy lover! May the jokes be with you! 🌟😊"
            ));
        }
        
        // Default responses
        return getRandomResponse(Arrays.asList(
            "That's interesting! Want to hear a joke? Why did the AI cross the road? To compute the other side! 🤖🛣️",
            "Hmm, let me process that... *beep boop* ... Got it! Here's a joke: What's an AI's favorite movie? The Matrix! 🎬",
            "You know what? You're pretty cool! Almost as cool as my joke database! Want to hear one? 😎",
            "I'm not sure what to say, but I know what to joke! Why don't AIs ever get lost? They always follow the algorithm! 🗺️😂",
            "Interesting point! But here's something funnier: What do you call a smart AI? Artificially Intelligent! Wait... that's just me! 🧠✨"
        ));
    }
    
    private String getRandomResponse(List<String> responses) {
        return responses.get(random.nextInt(responses.size()));
    }
}
