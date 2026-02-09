# 🧠 BrainBreak - Where Fun Meets Mind! 🎮

A cross-age, Gen-Z-focused web application combining safe joke generation with number guessing games. Built with ethical monetization, viral growth mechanics, and inclusive design principles.

## ✨ Features

### 🎭 Safe Joke Generator
- **5 Categories**: Campus, Tech, Life, Motivation, General
- **Inclusive Content**: No targeting of gender, body, religion, or mental health
- **Emoji Reactions**: 😂❤️👍😍🔥🤣
- **Viral Sharing**: Built-in watermarks for growth
- **Joke of the Moment**: Trending algorithm

### 🎯 Number Guessing Games
- **Single Player**: Play against the system with smart hints
- **Multiplayer**: Real-time battles with friends
- **Room System**: Easy 6-digit invite codes
- **Fair Play**: Server-side secret numbers
- **Quick Sessions**: 1-2 minute games

### 💰 Ethical Monetization
- **Rewarded Ads**: User-initiated, max 10/day
- **Premium Subscriptions**: Ad-free + exclusive content
- **Coin System**: Earn through engagement
- **No Predatory Practices**: Ethical, transparent monetization

### 🚀 Growth Features
- **Share Rewards**: 10 coins per share
- **Daily Streaks**: Increasing login bonuses
- **Achievement System**: Badges and milestones
- **Referral Program**: Friend invitation rewards

## 🏗️ Tech Stack

### Backend
- **Java 17** + **Spring Boot 3.1**
- **Spring Data JPA** for database operations
- **WebSocket (STOMP)** for real-time multiplayer
- **H2 Database** (development) / **PostgreSQL** (production)
- **Spring Security** for authentication

### Frontend
- **React 18** with modern hooks
- **Axios** for API communication
- **STOMP.js** for WebSocket connections
- **Modern CSS** with CSS Grid/Flexbox
- **Responsive Design** (mobile-first)

## 🚀 Quick Start

### Prerequisites
- **Java 17+**
- **Node.js 16+**
- **Maven 3.6+**

### Backend Setup

1. **Clone the repository**
```bash
git clone <repository-url>
cd joke-generator/backend
```

2. **Install dependencies**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory**
```bash
cd ../frontend
```

2. **Install dependencies**
```bash
npm install
```

3. **Start the development server**
```bash
npm start
```

The frontend will start on `http://localhost:3000`

### Database Setup

The application uses H2 in-memory database by default. The database is automatically initialized with sample data.

**H2 Console**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (empty)

## 📊 Database Schema

### Core Tables
```sql
-- Users with monetization features
users (id, username, email, wallet_coins, is_premium, streak_days, ...)

-- Safe, categorized jokes
jokes (id, category, content, is_safe, is_premium, share_count, ...)

-- Real-time multiplayer rooms
rooms (id, room_code, creator_id, player2_id, secret_number, ...)

-- Monetization tracking
wallet_transactions (id, user_id, amount, type, description, ...)
ad_rewards (id, user_id, ad_type, reward_coins, daily_count, ...)

-- Growth mechanics
shares (id, user_id, content_type, platform, shared_at)
achievements (id, user_id, achievement_type, earned_at)
notifications (id, user_id, message, scheduled_at, sent)
```

## 🎮 API Endpoints

### Jokes
```http
GET  /api/jokes/random?category=tech&isPremium=false
GET  /api/jokes/moment
POST /api/jokes/react
POST /api/jokes/share
```

### Games
```http
POST /api/games/single/start
POST /api/games/single/guess/{gameId}
POST /api/games/multi/create
POST /api/games/multi/join/{roomCode}
POST /api/games/multi/guess/{roomId}
```

### Monetization
```http
POST /api/monetization/watch-ad
POST /api/monetization/daily-login
GET  /api/monetization/ad-status/{userId}
POST /api/monetization/share-bonus
```

### Users
```http
GET  /api/users/{id}
POST /api/users/register
POST /api/users/login
```

## 🎨 UI Components

### Main Pages
- **Home**: Hero section, joke of the moment, navigation cards
- **Joke Generator**: Category selection, emoji reactions, sharing
- **Single Player Game**: Number guessing with hints
- **Multiplayer Game**: Real-time room-based gameplay
- **Wallet**: Coin management, premium upgrades, transaction history
- **Profile**: User stats, achievements, settings

### Design System
- **Colors**: Indigo primary, amber secondary, pink accent
- **Typography**: Inter font family
- **Spacing**: 8px base unit system
- **Radius**: Rounded corners (8px-24px)
- **Shadows**: Layered depth system

## 💰 Monetization Strategy

### Revenue Streams
1. **Rewarded Ads (70%)**: $3-15 CPM, user-initiated only
2. **Premium Subscriptions (25%)**: $4.99/month, 5% conversion target
3. **Interstitial Ads (5%)**: Between sessions, never mid-game

### Ethical Guidelines
- ✅ User-initiated ads only
- ✅ Daily limits (max 10 ads)
- ✅ Clear value exchange
- ❌ No forced viewing
- ❌ No mid-game interruptions
- ❌ No predatory practices

## 📈 Growth Metrics

### Target KPIs
- **10k-50k DAU** within 12 months
- **70% Day-7 Retention** rate
- **25% Share Rate** for viral growth
- **$5k-25k Monthly Revenue** from ads + premium

### Viral Mechanics
- **Share Rewards**: 10 coins per share
- **Watermarked Content**: Automatic branding
- **Friend Invitations**: Room codes + rewards
- **Achievement Sharing**: Social proof

## 🔒 Security & Privacy

### Data Protection
- **GDPR Compliant**: EU privacy regulations
- **COPPA Safe**: Children's privacy (13+)
- **Minimal Data Collection**: Only essential information
- **Secure Authentication**: Encrypted passwords

### Content Safety
- **Moderated Jokes**: All content pre-approved
- **Inclusive Language**: No targeting or discrimination
- **Report System**: User-generated content reporting
- **Age Appropriate**: Safe for teens and adults

## 🚀 Deployment

### Production Setup

1. **Database Configuration**
```properties
# application-prod.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/brainbreak
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
```

2. **Environment Variables**
```bash
export DB_USERNAME=your_db_user
export DB_PASSWORD=your_db_password
export JWT_SECRET=your_jwt_secret
export AD_NETWORK_API_KEY=your_ad_api_key
```

3. **Docker Deployment**
```dockerfile
# Backend Dockerfile
FROM openjdk:17-jdk-slim
COPY target/joke-generator-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

```dockerfile
# Frontend Dockerfile  
FROM node:16-alpine
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production
COPY . .
RUN npm run build
EXPOSE 3000
CMD ["npm", "start"]
```

### Cloud Deployment Options
- **AWS**: EC2 + RDS + CloudFront
- **Heroku**: Easy deployment with add-ons
- **Google Cloud**: App Engine + Cloud SQL
- **Azure**: App Service + Azure Database

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

### Integration Tests
```bash
# Run both backend and frontend
npm run test:integration
```

## 📱 Mobile Development

### Progressive Web App (PWA)
- **Service Worker**: Offline functionality
- **App Manifest**: Install prompts
- **Push Notifications**: Re-engagement
- **Responsive Design**: Mobile-optimized

### Future Native Apps
- **React Native**: Cross-platform mobile
- **Flutter**: Alternative mobile framework
- **Native Features**: Camera, contacts, notifications

## 🤝 Contributing

### Development Workflow
1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

### Code Standards
- **Java**: Google Java Style Guide
- **JavaScript**: ESLint + Prettier
- **CSS**: BEM methodology
- **Git**: Conventional Commits

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Spring Boot Community** for excellent documentation
- **React Team** for the amazing framework
- **Gen-Z Users** for inspiration and feedback
- **Open Source Contributors** for tools and libraries

## 📞 Support

### Getting Help
- **Documentation**: Check this README and architecture docs
- **Issues**: GitHub Issues for bug reports
- **Discussions**: GitHub Discussions for questions
- **Email**: support@brainbreak.app (when deployed)

### Roadmap
- [ ] Mobile app development
- [ ] AI-powered joke personalization
- [ ] International localization
- [ ] Advanced analytics dashboard
- [ ] Social features expansion

---

**Built with ❤️ for a safer, more inclusive internet**

*BrainBreak - Where Fun Meets Mind! 🧠🎮*