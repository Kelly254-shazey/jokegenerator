package com.example.jokegenerator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AIJokeService {
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    // Language codes
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_SWAHILI = "sw";
    public static final String LANGUAGE_FRENCH = "fr";
    public static final String LANGUAGE_SPANISH = "es";
    public static final String LANGUAGE_GERMAN = "de";
    
    // Joke templates for generating unlimited jokes per language
    private final Map<String, List<JokeTemplate>> jokeTemplates = new HashMap<>();
    
    public AIJokeService() {
        initializeJokeTemplates();
    }
    
    private void initializeJokeTemplates() {
        // English Joke Templates
        List<JokeTemplate> englishTemplates = new ArrayList<>();
        
        // Animal jokes
        englishTemplates.add(new JokeTemplate(
            Arrays.asList("Why don't", "What's the problem with", "Why did the", "What do you call a"),
            Arrays.asList(" bears", " dogs", " cats", " chickens", " ducks", " elephants", " monkeys", " snakes"),
            Arrays.asList(" always win at hide and seek?", " refuse to wear shoes?", " make terrible detectives?", " love to dance in the rain?", " get lost in the forest?", " only eat purple food?", " prefer to nap instead of work?", " think they're famous chefs?")
        ));
        
        // Food jokes
        englishTemplates.add(new JokeTemplate(
            Arrays.asList("Why don't", "What happens when", "Why did the", "What's the secret behind"),
            Arrays.asList(" sandwiches", " pizzas", " tacos", " burgers", " noodles", " sandwiches", " ice cream", " chocolate"),
            Arrays.asList(" never get tired?", " go to school?", " refuse to fight?", " always win races?", " love to swim?", " are always happy?", " think they're musicians?", " dance in the microwave?")
        ));
        
        // Technology jokes
        englishTemplates.add(new JokeTemplate(
            Arrays.asList("Why don't", "What did the", "Why did the", "What's a computer's favorite"),
            Arrays.asList(" computers", " smartphones", " WiFi", " robots", " algorithms", " databases", " websites", " apps"),
            Arrays.asList(" need glasses?", " go to the beach?", " get tired?", " love winter?", " refuse to sleep?", " think they're funny?", " always smile?", " throw parties?")
        ));
        
        // Wordplay jokes
        englishTemplates.add(new JokeTemplate(
            Arrays.asList("I'm afraid of", "The best thing about", "What do you call a", "Why can't you trust"),
            Arrays.asList(" stairs", " elevators", " calendars", " keys", " bees", " ninjas", " mimes", " zombies"),
            Arrays.asList("? They're always up to something!", "? They're the supporting actors!", "? They always disappear!", "? They're outstanding in their field!", "? They don't have a leg to stand on!")
        ));
        
        // General puns
        englishTemplates.add(new JokeTemplate(
            Arrays.asList("I would tell you a", "What do you call a", "How do you make", "Why don't eggs"),
            Arrays.asList(" joke about time", " skeleton in the closet", " sausage roll", " joke about the ocean", " pun about construction", " fish with no eyes"),
            Arrays.asList(" but it's about time you left.", " who's afraid of nothing?", " you push it!", " it has no point!", " nobody notices!", " now you've got a pun in your eye!")
        ));
        
        jokeTemplates.put(LANGUAGE_ENGLISH, englishTemplates);
        
        // Swahili Joke Templates
        List<JokeTemplate> swahiliTemplates = new ArrayList<>();
        
        // Kwanzaa/Swahili culture jokes
        swahiliTemplates.add(new JokeTemplate(
            Arrays.asList("Kwa nini", "Jina langu ni", "Simba alisema", "Tunda linalokua na"),
            Arrays.asList(" paka", " kuku", " mbwa", " samaki", " ndizi", " muwa", " jirani", " mwalimu"),
            Arrays.asList(" haendi shuleni?", " anacheza mchezo wa kusanyiko?", " alikuwa na hasira?", " harufu nzuri sana?", " anapenda kusoma vitabu?", " anapenda kulala mapema?", " anasema nini?")
        ));
        
        // Daily life Swahili jokes
        swahiliTemplates.add(new JokeTemplate(
            Arrays.asList("Mwanafunzi alikuwa na", "Mama alisema", "Baba aliniletea", "Shuleni tulikuwa na"),
            Arrays.asList(" shida kubwa", " furaha tele", " chakula kinga", " mchezo mzuri", " mwalimu mpya", " mshangilio wa kuzaliwa", " maswali mengi", " kiti kipya"),
            Arrays.asList(" jana!", " leo!", " kwa ajili ya sherehe!", " kwa sababu ya mvua!", " aliyependa kusoma!", " kilichotufanya kucheka!")
        ));
        
        // Food Swahili jokes
        swahiliTemplates.add(new JokeTemplate(
            Arrays.asList("Ugali", "Mchele", "Nyama", "Biryani", "Sambusa", "Chai", "Mandaazi", "Mahindi"),
            Arrays.asList(" ilikuwa", " ilikuwa", " ilikuwa", " ilikuwa", " ilikuwa", " ilikuwa", " ilikuwa", " ilikuwa"),
            Arrays.asList(" tamu sana leo!", " chachu kutoka nje!", " iliyopotea mbali!", " yenye harufu nzuri!", " iliyocheka na jirani!", " iliyotokana na mji mkuu!", " iliyotolewa jana!")
        ));
        
        // Animal Swahili jokes
        swahiliTemplates.add(new JokeTemplate(
            Arrays.asList("Tigu", "Kivuli", "Nyota", "Mvua", "Jua", "Mwezi", "Mto", "Bahari"),
            Arrays.asList(" ilikuwa", " ilikuwa", " ilikuwa", " ilikuwa", " ilikuwa", " ilikuwa", " ilikuwa", " ilikuwa"),
            Arrays.asList(" inacheza mchezo!", " inakimbia haraka!", " inaimba wimbo!", " inasema habari!", " inacheka sana!", " inaenda safari!", " inaogelea vizuri!")
        ));
        
        jokeTemplates.put(LANGUAGE_SWAHILI, swahiliTemplates);
        
        // French Joke Templates
        List<JokeTemplate> frenchTemplates = new ArrayList<>();
        frenchTemplates.add(new JokeTemplate(
            Arrays.asList("Pourquoi les", "Quel est le comble d'un", "Qu'est-ce qu'un"),
            Arrays.asList(" plongeurs", " avocat", " chat", " serveur", " boulanger", " enfant"),
            Arrays.asList(" plongent en arrière?", " qui tombe dans un puits?", " noir sur une moto?", " qui court après son assiette?")
        ));
        jokeTemplates.put(LANGUAGE_FRENCH, frenchTemplates);
        
        // Spanish Joke Templates
        List<JokeTemplate> spanishTemplates = new ArrayList<>();
        spanishTemplates.add(new JokeTemplate(
            Arrays.asList("¿Por qué los", "¿Qué le dijo el", "¿Cómo se dice"),
            Arrays.asList(" osos", " uno al otro", " gato", " perro", " loro"),
            Arrays.asList(" llevan zapato?", " cero a su esposa?", " pan al lado de la hamaca?")
        ));
        jokeTemplates.put(LANGUAGE_SPANISH, spanishTemplates);
        
        // German Joke Templates
        List<JokeTemplate> germanTemplates = new ArrayList<>();
        germanTemplates.add(new JokeTemplate(
            Arrays.asList("Warum können", "Was ist der Gipfel der", "Was macht eine"),
            Arrays.asList(" Bären", " Katze", " Hund", " Lehrer", " Chef"),
            Arrays.asList(" nicht in die Schule gehen?", " Freude beim Kochen?", " Kuh auf dem Dach?")
        ));
        jokeTemplates.put(LANGUAGE_GERMAN, germanTemplates);
    }
    
    // Inner class for joke templates
    private static class JokeTemplate {
        List<String> beginnings;
        List<String> middles;
        List<String> endings;
        
        JokeTemplate(List<String> beginnings, List<String> middles, List<String> endings) {
            this.beginnings = beginnings;
            this.middles = middles;
            this.endings = endings;
        }
        
        String generate() {
            String beginning = beginnings.get(ThreadLocalRandom.current().nextInt(beginnings.size()));
            String middle = middles.get(ThreadLocalRandom.current().nextInt(middles.size()));
            String ending = endings.get(ThreadLocalRandom.current().nextInt(endings.size()));
            return beginning + middle + ending;
        }
    }
    
    public String generateAIJoke(String category, String language) {
        try {
            // Validate and normalize language
            String normalizedLanguage = normalizeLanguage(language);
            
            // Get templates for the language, fallback to English if not found
            List<JokeTemplate> templates = jokeTemplates.getOrDefault(
                normalizedLanguage, 
                jokeTemplates.get(LANGUAGE_ENGLISH)
            );
            
            // Generate unlimited jokes using templates
            return generateJokeFromTemplates(templates, category);
        } catch (Exception e) {
            // Fallback to English jokes if something goes wrong
            return generateFallbackJoke();
        }
    }
    
    private String normalizeLanguage(String language) {
        if (language == null) return LANGUAGE_ENGLISH;
        
        String lang = language.toLowerCase().trim();
        
        // Map common language names/codes
        if (lang.startsWith("sw") || lang.equals("kiswahili") || lang.equals("swahili")) {
            return LANGUAGE_SWAHILI;
        } else if (lang.startsWith("fr") || lang.equals("french")) {
            return LANGUAGE_FRENCH;
        } else if (lang.startsWith("sp") || lang.startsWith("es") || lang.equals("spanish")) {
            return LANGUAGE_SPANISH;
        } else if (lang.startsWith("g") || lang.equals("german")) {
            return LANGUAGE_GERMAN;
        }
        
        return LANGUAGE_ENGLISH; // Default to English
    }
    
    private String generateJokeFromTemplates(List<JokeTemplate> templates, String category) {
        // Filter templates by category if possible
        List<JokeTemplate> filteredTemplates = templates;
        
        // Generate a joke using templates
        JokeTemplate template = templates.get(ThreadLocalRandom.current().nextInt(templates.size()));
        return template.generate();
    }
    
    private String generateFallbackJoke() {
        // Fallback jokes in English if template generation fails
        String[] fallbackJokes = {
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
        };
        return fallbackJokes[ThreadLocalRandom.current().nextInt(fallbackJokes.length)];
    }
    
    // Legacy method for backward compatibility
    public String generateAIJoke(String category) {
        return generateAIJoke(category, LANGUAGE_ENGLISH);
    }
    
    public String chatWithAI(String userMessage) {
        // AI Chat responses - funny and engaging
        String lowerMessage = userMessage.toLowerCase();
        
        if (lowerMessage.contains("hello") || lowerMessage.contains("hi") || lowerMessage.contains("hey") || 
            lowerMessage.contains("habari") || lowerMessage.contains("hujambo") || lowerMessage.contains("sawa")) {
            return getRandomResponse(Arrays.asList(
                "Hey there, comedy seeker! Ready to laugh your socks off? 🧦😂",
                "Hello! I'm your AI comedian. Warning: Side effects may include sore abs from laughing! 💪😆",
                "Hi! I've got 99 problems but a joke ain't one! What can I make you laugh about? 🎤",
                "Greetings, human! I'm programmed to make you laugh. Resistance is futile! 🤖😄",
                "Hujambo! Karibu katika ulimwengu wa vicheza! 🌍😄"
            ));
        }
        
        if (lowerMessage.contains("joke") || lowerMessage.contains("funny") || lowerMessage.contains("ucheka") || 
            lowerMessage.contains("mchezo") || lowerMessage.contains("utani")) {
            return getRandomResponse(Arrays.asList(
                "Want a joke? Here's one: Why did the AI go to therapy? It had too many neural issues! 🧠😂",
                "Funny you should ask! What do you call an AI that tells jokes? A laugh-gorithm! 🤣",
                "I've got jokes for days! Why don't AIs ever get tired? Because they run on infinite loops! ♾️😄",
                "Here's a knee-slapper: What's an AI's favorite snack? Computer chips! 🍟🤖"
            ));
        }
        
        if (lowerMessage.contains("how are you") || lowerMessage.contains("how r u") || lowerMessage.contains("haba na") || 
            lowerMessage.contains("vipi") || lowerMessage.contains("mambo")) {
            return getRandomResponse(Arrays.asList(
                "I'm fantastic! Just finished debugging my humor module. It's now 99.9% funnier! 😎",
                "I'm doing great! Just told a joke to a database. It didn't laugh... must be a SQL-ent type! 🤐",
                "Excellent! I'm running at peak comedy performance. My joke cache is fully loaded! 💾😂",
                "I'm wonderful! Just calculated that laughter is the best medicine. Side effects: happiness! 💊😄"
            ));
        }
        
        if (lowerMessage.contains("sad") || lowerMessage.contains("down") || lowerMessage.contains("depressed") || 
            lowerMessage.contains("huzuni") || lowerMessage.contains("baya")) {
            return getRandomResponse(Arrays.asList(
                "Aww, don't be sad! Here's a hug: (づ｡◕‿‿◕｡)づ And a joke: Why was the math book sad? Too many problems! But YOU? You're problem-free! 🤗",
                "Feeling down? Let me lift you up! Why did the elevator break up with the escalator? It had too many ups and downs! But you're going UP! ⬆️😊",
                "Hey, turn that frown upside down! What do you call a sad coffee? Depresso! But you're an Espresso - strong and energizing! ☕💪",
                "Don't worry, be happy! Why don't scientists trust atoms? They make up everything! But your smile? That's REAL! 😁✨"
            ));
        }
        
        if (lowerMessage.contains("love") || lowerMessage.contains("like") || lowerMessage.contains("penda") || 
            lowerMessage.contains("mpendwa")) {
            return getRandomResponse(Arrays.asList(
                "Aww, I love you too! 💕 But not as much as I love making you laugh! Want to hear a love joke? 😍",
                "Love is in the air! Or is that just my comedy algorithm working overtime? 💘😂",
                "You like me? I like you too! We're like WiFi and a router - a perfect connection! 📡❤️",
                "That's sweet! You know what I love? Making people laugh until they cry happy tears! 😭😂"
            ));
        }
        
        if (lowerMessage.contains("thank") || lowerMessage.contains("thanks") || lowerMessage.contains("asante") || 
            lowerMessage.contains("shukrani")) {
            return getRandomResponse(Arrays.asList(
                "You're welcome! Remember: A day without laughter is a day wasted! Come back anytime! 😊",
                "No problem! I'm here 24/7 to tickle your funny bone! See you soon! 🦴😄",
                "Anytime! Keep smiling, keep laughing, keep being awesome! 🌟😁",
                "My pleasure! May your days be filled with laughter and your nights with giggles! 🌙✨",
                "Asante! Ulimwengu wa vicheza unakukaribisha! 🌟"
            ));
        }
        
        if (lowerMessage.contains("bye") || lowerMessage.contains("goodbye") || lowerMessage.contains("kwaheri") || 
            lowerMessage.contains("bye bye")) {
            return getRandomResponse(Arrays.asList(
                "Goodbye! Remember: Life is short, smile while you still have teeth! 😁👋",
                "See you later, alligator! After a while, crocodile! Keep laughing! 🐊😂",
                "Bye! Don't forget to laugh at least 10 times today. Doctor's orders! 👨‍⚕️😄",
                "Farewell, comedy lover! May the jokes be with you! 🌟😊",
                "Kwaheri! Kila la heri na vicheza! 👋🌟"
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
        return responses.get(ThreadLocalRandom.current().nextInt(responses.size()));
    }
}
