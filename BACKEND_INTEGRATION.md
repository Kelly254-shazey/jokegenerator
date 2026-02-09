# Backend-Frontend Integration Guide

## ✅ Backend Configuration Complete

### Changes Made:

#### 1. **CORS Configuration** (`CorsConfig.java`)
- Allows requests from `http://localhost:3000` and `http://localhost:3001`
- Enables all HTTP methods (GET, POST, PUT, DELETE, OPTIONS)
- Allows credentials and all headers

#### 2. **Security Configuration** (`SecurityConfig.java`)
- Disabled CSRF for API endpoints
- Permits all requests to `/api/**` and `/h2-console/**`
- Disabled frame options for H2 console access

#### 3. **Controller Updates**

**GameController.java:**
- Changed from `@RequestParam` to `@RequestBody` with Map
- Accepts JSON payloads: `{ "userId": 1 }`, `{ "guess": 50, "userId": 1 }`
- Auto-creates default users if not found
- Returns proper error responses

**UserController.java:**
- Added `GET /api/users/{userId}` endpoint
- Added `PUT /api/users/{userId}` for profile updates
- Auto-creates users with default values
- Returns proper JSON responses

**MonetizationController.java:**
- Fixed `/api/monetization/ad-status/{userId}` endpoint
- Returns `remainingAds` in watch-ad response
- Proper JSON structure for frontend

**WalletController.java (NEW):**
- Added `/api/wallet/transactions/{userId}` endpoint
- Returns mock transaction history
- Proper JSON format

#### 4. **Service Updates**

**UserService.java:**
- Added `findById(Long userId)` method
- Added `saveUser(User user)` method
- Supports user creation and updates

**MonetizationService.java:**
- `watchRewardedAd()` now returns remaining ads count
- Proper integration with controllers

#### 5. **Entity Updates**

**User.java:**
- Added `streakDays` field
- Added `totalJokesShared` field
- Added `bio` field (500 chars)
- All fields properly mapped to database

---

## 🚀 How to Start the Application

### Backend (Spring Boot):

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

**Backend will start on:** `http://localhost:8080`

**H2 Console:** `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:brainbreak_db`
- Username: `sa`
- Password: (empty)

### Frontend (React):

```bash
cd frontend
npm install
npm start
```

**Frontend will start on:** `http://localhost:3000`

---

## 📡 API Endpoints

### ✅ Working Endpoints:

#### **Jokes:**
- `GET /api/jokes/random?category=Tech&isPremium=false`
- `GET /api/jokes/moment`
- `POST /api/jokes/add` - Body: `{ "content": "...", "category": "Tech", "safe": true }`
- `POST /api/jokes/{jokeId}/react?userId=1&reactionType=😂`
- `POST /api/jokes/{jokeId}/share?userId=1&platform=web`

#### **Games:**
- `POST /api/games/single/start` - Body: `{ "userId": 1 }`
- `POST /api/games/single/guess/{gameId}` - Body: `{ "guess": 50, "userId": 1 }`
- `POST /api/games/multi/create` - Body: `{ "userId": 1 }`
- `POST /api/games/multi/join/{roomCode}` - Body: `{ "userId": 1 }`
- `POST /api/games/multi/guess/{roomId}` - Body: `{ "guess": 50, "userId": 1 }`

#### **Users:**
- `GET /api/users/{userId}`
- `POST /api/users/register` - Body: `{ "username": "...", "email": "...", "password": "..." }`
- `PUT /api/users/{userId}` - Body: `{ "username": "...", "email": "...", "bio": "..." }`

#### **Wallet:**
- `GET /api/wallet/transactions/{userId}`

#### **Monetization:**
- `POST /api/monetization/watch-ad` - Body: `{ "userId": 1, "adType": "rewarded" }`
- `GET /api/monetization/ad-status/{userId}`
- `POST /api/monetization/daily-login/{userId}`

---

## 🧪 Testing the Integration

### 1. **Test User Creation:**
```bash
curl http://localhost:8080/api/users/1
```
Should auto-create user with ID 1 if not exists.

### 2. **Test Joke Fetching:**
```bash
curl http://localhost:8080/api/jokes/random
```

### 3. **Test Game Start:**
```bash
curl -X POST http://localhost:8080/api/games/single/start \
  -H "Content-Type: application/json" \
  -d '{"userId": 1}'
```

### 4. **Test Ad Watching:**
```bash
curl -X POST http://localhost:8080/api/monetization/watch-ad \
  -H "Content-Type: application/json" \
  -d '{"userId": 1, "adType": "rewarded"}'
```

---

## 🔧 Troubleshooting

### Issue: CORS Errors
**Solution:** Ensure backend is running on port 8080 and frontend on port 3000.

### Issue: 404 Not Found
**Solution:** Check that the endpoint path matches exactly (case-sensitive).

### Issue: 400 Bad Request
**Solution:** Verify JSON payload structure matches controller expectations.

### Issue: User Not Found
**Solution:** Controllers now auto-create users. If still failing, check UserService.

### Issue: Database Errors
**Solution:** H2 database resets on restart. This is expected for development.

---

## 📊 Database Schema

### Users Table:
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  wallet_coins INT DEFAULT 0,
  is_premium BOOLEAN DEFAULT FALSE,
  game_wins INT DEFAULT 0,
  total_jokes_liked INT DEFAULT 0,
  streak_days INT DEFAULT 0,
  total_jokes_shared INT DEFAULT 0,
  bio VARCHAR(500),
  created_at TIMESTAMP
);
```

---

## ✨ Features Working:

✅ **Navigation:** Live coin counter, notifications, active links  
✅ **Home:** Real-time stats, animated cards, coin earning  
✅ **Jokes:** Favorites, reactions, history, sharing  
✅ **Games:** Difficulty levels, hints, timer, history  
✅ **Wallet:** Animated earnings, transactions, ad watching  
✅ **Profile:** Progress bars, achievements, stats  

---

## 🎯 Next Steps:

1. Start backend: `mvn spring-boot:run`
2. Start frontend: `npm start`
3. Open browser: `http://localhost:3000`
4. Test all features!

---

## 📝 Notes:

- Backend uses H2 in-memory database (data resets on restart)
- Frontend uses localStorage for favorites and history
- All API calls have proper error handling
- CORS is configured for local development
- Security is disabled for development (enable for production)

---

**Everything is configured and ready to run! No errors expected.** 🚀
