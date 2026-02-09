# 🧠 BrainBreak - Complete Setup Guide

## Project Overview
BrainBreak is a fun, interactive web application featuring joke generation, multiplayer games, and a reward system with professional UI/UX.

### Key Features ✨
- **Joke Generator**: Get random jokes by category, upload your own, and react with emojis
- **Games**: Single-player and multiplayer guessing games
- **Reward System**: Earn coins by playing games, watching ads, and sharing jokes
- **Premium Features**: Ad-free experience, exclusive content, and daily bonuses
- **Dialog System**: Professional confirmation and notification dialogs
- **Toast Notifications**: Non-intrusive feedback messages
- **Dark Mode**: Built-in dark theme support
- **Responsive Design**: Mobile-first, works on all devices

---

## Installation & Setup

### Prerequisites
- Node.js (v16+)
- Java 11+ 
- Maven 3.8+
- npm or yarn

### Backend Setup

1. **Navigate to backend directory**
   ```bash
   cd backend
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Run the backend server**
   ```bash
   mvn spring-boot:run
   ```
   - Backend will run on: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`
   - API Base: `http://localhost:8080/api`

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure environment**
   - A `.env` file is already created with:
   ```
   REACT_APP_API_URL=http://localhost:8080/api
   REACT_APP_ENV=development
   ```

4. **Start the development server**
   ```bash
   npm start
   ```
   - Frontend will run on: `http://localhost:3000`

---

## API Endpoints

### Authentication
- `POST /api/auth/login` - Login user

### Jokes
- `GET /api/jokes/random` - Get random joke (params: category, isPremium)
- `GET /api/jokes/moment` - Get joke of the moment
- `GET /api/jokes/category/{category}` - Get jokes by category
- `POST /api/jokes/add` - Upload new joke
- `POST /api/jokes/{jokeId}/react` - Add emoji reaction
- `POST /api/jokes/{jokeId}/share` - Share joke and earn coins

### Games
- `POST /api/games/single/start` - Start single-player game
- `POST /api/games/single/guess/{gameId}` - Submit guess
- `POST /api/games/multi/create` - Create multiplayer room
- `POST /api/games/multi/join/{roomCode}` - Join room
- `POST /api/games/multi/guess/{roomId}` - Multiplayer guess

### Users
- `GET /api/users/{userId}` - Get user profile
- `PUT /api/users/{userId}` - Update profile

### Wallet
- `GET /api/wallet/{userId}` - Get wallet info
- `GET /api/wallet/transactions/{userId}` - Get transactions
- `POST /api/monetization/watch-ad` - Watch ad and earn coins

---

## Component Structure

### Frontend Components
```
src/
├── components/
│   ├── Navigation.js       - Fixed header with logo and theme toggle
│   ├── Home.js            - Landing page with feature grid and dialogs
│   ├── JokeGenerator.js   - Joke display and upload component
│   ├── Dialog.js          - Reusable dialog/modal component
│   ├── Toast.js           - Toast notification system
│   ├── SinglePlayerGame.js - Single-player game logic
│   ├── MultiplayerGame.js - Multiplayer game setup
│   ├── UserProfile.js     - User profile and stats
│   ├── Wallet.js          - Coin wallet management
│   ├── Settings.js        - App settings and preferences
│   └── Room.js            - Multiplayer room component
├── services/
│   └── api.js             - Centralized API client with interceptors
├── App.js                 - Main app with routing and ToastProvider
├── BrainBreak.css         - Global styles and CSS variables
└── index.js               - React entry point
```

### Backend Structure
```
src/main/java/com/example/jokegenerator/
├── JokeGeneratorApplication.java  - Spring Boot entry point
├── config/
│   ├── SecurityConfig.java        - Security configuration
│   ├── WebSocketConfig.java       - WebSocket setup
│   └── CorsConfig.java            - CORS configuration
├── controller/
│   ├── JokeController.java        - Joke endpoints
│   ├── GameController.java        - Game endpoints
│   ├── UserController.java        - User endpoints
│   └── MonetizationController.java - Monetization endpoints
├── entity/
│   ├── Joke.java                  - Joke entity
│   ├── User.java                  - User entity
│   ├── GameAttempt.java           - Game record
│   └── Others...
├── repository/
│   └── JokeRepository.java        - Data access
├── service/
│   └── JokeService.java           - Business logic
└── resources/
    └── application.properties      - Configuration
```

---

## UI Features

### Home Page
- **Header**: Animated BrainBreak logo with gradient background
- **Stats Display**: Current coins and streak days
- **Feature Grid**: 2x2 grid of main features with hover animations
- **Action Buttons**: Watch ads, get hints, upgrade to premium
- **Dialog Integration**: Confirmations for ad watching and premium upgrade

### Joke Generator
- **Category Filter**: Dropdown to filter jokes by category
- **Upload Form**: Share your own jokes with validation
- **Emoji Reactions**: 6 reaction options (😄 😂 🤣 😍 🥰 🔥)
- **Share Button**: Copy joke to clipboard
- **Loading Skeleton**: Professional loading state
- **Toast Notifications**: Success/error feedback

### Dialog System
- **Types**: Info, Success, Warning, Error
- **Auto-dismissal**: Notifications auto-close after timeout
- **Smooth Animations**: Slide-in and bounce effects
- **Dark Mode**: Full dark mode support
- **Mobile Responsive**: Optimized for all screen sizes

### Navigation
- **Fixed Header**: Always visible with fixed positioning
- **Logo Animation**: Bouncing effect on page load
- **Dark Mode Toggle**: Switch between light and dark themes
- **Active Route Highlighting**: Shows current page

---

## Styling System

### CSS Variables (in BrainBreak.css)
```css
--primary-color: #6C5CE7
--secondary-color: #00CEC9
--accent-color: #FD79A8
--success-color: #27ae60
--error-color: #e74c3c
--warning-color: #f39c12
```

### Responsive Breakpoints
- **Mobile**: < 480px
- **Tablet**: 480px - 768px
- **Desktop**: > 768px

### Design Principles
- Modern, colorful, professional
- Smooth animations and transitions
- Accessibility: ARIA labels, semantic HTML
- Performance: Optimized images, lazy loading

---

## Database

### H2 Database (Development)
- **Type**: In-memory database
- **Console**: `http://localhost:8080/h2-console`
- **Auto-initialization**: DataInitializer.java loads 25 sample jokes on startup

### Database Tables
- `JOKE` - Joke content with reactions and shares
- `USER` - User accounts and profiles
- `GAME_ATTEMPT` - Game session records
- `JOKE_REACTION` - Emoji reactions on jokes
- `SHARE` - Joke share history

---

## Running the Full Application

### Option 1: Sequential (Terminal 1 & 2)

**Terminal 1 - Backend:**
```bash
cd "c:\Users\w\Desktop\joke generator\backend"
mvn spring-boot:run
```

**Terminal 2 - Frontend:**
```bash
cd "c:\Users\w\Desktop\joke generator\frontend"
npm start
```

### Option 2: Using npm concurrently
From project root:
```bash
npm install -g concurrently
concurrently "cd backend && mvn spring-boot:run" "cd frontend && npm start"
```

---

## Testing the Features

### 1. Home Page Interactions
- ✅ Click feature cards → Navigate to respective pages
- ✅ Click "Get Hints" → Dialog appears asking for coins
- ✅ Click "Watch & Earn" → Simulated ad with coin reward
- ✅ Click "Go Ad-Free" → Premium upgrade dialog
- ✅ Toggle dark mode → Theme switches instantly

### 2. Joke Generator
- ✅ Select category → Filter jokes
- ✅ Click emoji → Save reaction (if backend returns without error)
- ✅ Click share → Copy to clipboard
- ✅ Upload joke → Form validation and API call
- ✅ Toast notifications → Feedback messages appear

### 3. API Communication
- ✅ Jokes load from backend
- ✅ CORS headers allow frontend-backend requests
- ✅ Error handling with user-friendly messages
- ✅ Loading states show during API calls

---

## Troubleshooting

### Frontend won't load jokes
1. Check backend is running: `http://localhost:8080/h2-console`
2. Verify `.env` has correct API URL
3. Check browser console for CORS errors
4. Ensure CorsConfig.java is in config folder

### Backend won't start
1. Check Java version: `java -version` (need 11+)
2. Clear Maven cache: `mvn clean install`
3. Check port 8080 isn't in use
4. Review application.properties database settings

### Styles not applying
1. Clear browser cache
2. Restart dev server: `npm start`
3. Check CSS file paths in imports
4. Verify Dark mode toggle in Navigation.js

### Toast notifications not showing
1. Check ToastProvider wraps App.js
2. Verify Toast.js and Toast.css are imported
3. Check z-index in Toast.css (should be 1000+)

---

## Browser Support
- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+

---

## Performance Tips
- Enable gzip compression in Spring Boot
- Use CDN for static assets
- Implement pagination for large joke lists
- Cache API responses where appropriate
- Optimize images (use WebP format)

---

## Future Enhancements
- [ ] User authentication system
- [ ] Social sharing (Twitter, Facebook)
- [ ] Leaderboard system
- [ ] Push notifications
- [ ] PWA support
- [ ] Offline mode
- [ ] Payment integration (Stripe)
- [ ] Advanced analytics
- [ ] Admin dashboard

---

## Support
For issues or questions, please check the console logs and ensure both backend and frontend are running properly.

**Happy Coding! 🚀**
