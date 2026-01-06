# Quick Start Guide - API Integration

## 🚀 Getting Started

### Prerequisites
- Android Studio with Android SDK 29+
- Node.js 14+ for backend
- MySQL 5.7+
- Kotlin 1.8+

### Step 1: Setup Backend

```bash
# Navigate to backend directory
cd backend

# Install dependencies
npm install

# Start MySQL (ensure service is running)
# Create database
mysql -u root -p < create_database.sql

# Start backend server
npm start
```

Backend will run on `http://localhost:5000`

Verify it's working:
```bash
curl http://localhost:5000/api/health
# Response: {"status":"OK"}
```

### Step 2: Open Project in Android Studio

1. Open `/ALP/event` folder in Android Studio
2. Wait for Gradle sync to complete
3. Ensure no compilation errors (should be 0)

### Step 3: Run Android App

```bash
# From Android Studio:
# 1. Click "Run" or press Shift + F10
# OR via terminal:
./gradlew build
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 📱 Testing the Integration

### 1. Event List Screen
- App starts with loading spinner
- After ~2 seconds, event list appears
- If no events: "No events found" message
- If error: Red error box with retry button

### 2. Event Details Screen
- Click an event or "See Events" button
- Event details load with reviews below
- "Add Reviews" button to create review
- "See Events" button to go back to list

### 3. Add Event Form
- Click "Add Events" button on event list
- Enter event details:
  - Name: at least 3 characters
  - Date (location): at least 3 characters
  - Description: at least 10 characters
- Form validates on submit
- Shows error messages if validation fails
- Success: returns to event list

### 4. Add Review Form
- From event details, click "Add Reviews"
- Enter reviewer details:
  - Name: at least 2 characters
  - Rating: select 1-5 stars
  - Comment: at least 5 characters
- Form validates on submit
- Success: returns to event details with new review

## 🔧 API Endpoints (Quick Reference)

### Events
```bash
# Get all events
GET http://localhost:5000/api/events

# Get specific event
GET http://localhost:5000/api/events/1

# Create event
POST http://localhost:5000/api/events
Content-Type: application/json
{
  "name": "Football Match",
  "location": "Stadium",
  "description": "Exciting football match description"
}

# Update event
PUT http://localhost:5000/api/events/1
Content-Type: application/json
{...}

# Delete event
DELETE http://localhost:5000/api/events/1
```

### Reviews
```bash
# Get all reviews
GET http://localhost:5000/api/reviews

# Get reviews for event
GET http://localhost:5000/api/reviews/event/1

# Create review
POST http://localhost:5000/api/reviews
Content-Type: application/json
{
  "eventId": 1,
  "userName": "John",
  "rating": 4.5,
  "comment": "Great event!"
}

# Update review
PUT http://localhost:5000/api/reviews/1
Content-Type: application/json
{...}

# Delete review
DELETE http://localhost:5000/api/reviews/1
```

## 🧪 Using Postman (Optional)

Import these requests to test API:

```json
{
  "info": {
    "name": "Event App API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Get All Events",
      "request": {
        "method": "GET",
        "url": "http://localhost:5000/api/events"
      }
    },
    {
      "name": "Create Event",
      "request": {
        "method": "POST",
        "url": "http://localhost:5000/api/events",
        "body": {
          "mode": "raw",
          "raw": "{\"name\":\"Test Event\",\"location\":\"Test\",\"description\":\"Test description here\"}"
        }
      }
    }
  ]
}
```

## 📊 Database Schema

### Events Table
```sql
CREATE TABLE events (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  location VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  photoUrl VARCHAR(500),
  rating FLOAT DEFAULT 0,
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Reviews Table
```sql
CREATE TABLE reviews (
  id INT PRIMARY KEY AUTO_INCREMENT,
  eventId INT NOT NULL,
  userName VARCHAR(255) NOT NULL,
  rating FLOAT NOT NULL,
  comment TEXT NOT NULL,
  photoUrl VARCHAR(500),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (eventId) REFERENCES events(id) ON DELETE CASCADE
);
```

## ⚠️ Troubleshooting

### Problem: "Connection refused"
**Solution**: 
```bash
# Check if backend is running
lsof -i :5000

# If not running, start it
cd backend && npm start
```

### Problem: "Validation error" on form submit
**Solution**: Check the error message shown in red box
- Event name needs 3+ characters
- Event location/date needs 3+ characters
- Event description needs 10+ characters
- Review comment needs 5+ characters
- Rating must be 1-5 stars

### Problem: Emulator can't reach backend
**Solution**: In `ApiClient.kt`, change:
```kotlin
// From:
private const val BASE_URL = "http://localhost:5000/api/"

// To:
private const val BASE_URL = "http://10.0.2.2:5000/api/"
```

### Problem: Build fails with dependencies
**Solution**: 
```bash
# From Android Studio terminal:
./gradlew build --refresh-dependencies
./gradlew clean build
```

### Problem: Gradle sync fails
**Solution**:
1. File → Invalidate Caches / Restart
2. File → Sync Project with Gradle Files
3. Or from terminal: `./gradlew sync`

## 🎯 Architecture Overview

```
┌─────────────────────────────────────────┐
│         Android UI (Compose)            │
│  EventDetailView, EventListView, etc.   │
└────────────────┬────────────────────────┘
                 │
┌─────────────────▼────────────────────────┐
│         ViewModels                       │
│  EventDetailVM, EventListVM, etc.       │
└────────────────┬────────────────────────┘
                 │
┌─────────────────▼────────────────────────┐
│      Retrofit HTTP Client                │
│  ApiClient, EventApiService, etc.        │
└────────────────┬────────────────────────┘
                 │ (Network)
                 ▼
        ┌────────────────┐
        │  HTTP Request  │
        │  Port 5000     │
        └────────────────┘
                 │
┌─────────────────▼────────────────────────┐
│      Express.js Backend                  │
│  Routes → Services → Repositories        │
└────────────────┬────────────────────────┘
                 │
┌─────────────────▼────────────────────────┐
│      MySQL Database                      │
│  events, reviews tables                  │
└─────────────────────────────────────────┘
```

## 📋 File Locations

```
/ALP/event/
├── app/src/main/java/com/example/event/
│   ├── data/service/
│   │   ├── ApiClient.kt (Retrofit setup)
│   │   └── ApiService.kt (Interfaces & DTOs)
│   ├── ui/viewmodel/
│   │   ├── EventDetailViewModel.kt
│   │   ├── EventListViewModel.kt
│   │   ├── AddEventViewModel.kt
│   │   └── AddReviewViewModel.kt
│   ├── ui/view/
│   │   ├── EventDetailView.kt
│   │   ├── EventListView.kt
│   │   ├── AddEventView.kt
│   │   └── AddReviewView.kt
│   └── MainActivity.kt
├── API_INTEGRATION_GUIDE.md (Detailed docs)
├── INTEGRATION_COMPLETE.md (Summary)
└── build.gradle.kts (With Retrofit deps)

/backend/
├── src/
│   ├── config/database.js
│   ├── models/Event.js, Review.js
│   ├── repository/*.js
│   ├── service/*.js
│   └── routes/*.js
├── index.js (Entry point)
├── .env (Configuration)
└── package.json
```

## ✅ Verification Checklist

Before considering integration complete:

- [ ] Backend running on port 5000
- [ ] Android app opens without errors
- [ ] Event list loads on first screen
- [ ] Can navigate to event details
- [ ] Can add new event successfully
- [ ] Can add new review successfully
- [ ] Error messages appear when validation fails
- [ ] Loading spinners display during API calls
- [ ] No unhandled exceptions in logcat
- [ ] Navigation works between all 4 screens

## 🎨 UI States

### Loading State
- Spinner in center
- Buttons disabled
- Inputs disabled
- "Loading..." appearance

### Error State
- Red error box
- Descriptive error message
- Retry/refresh button
- Usually shows after 1-2 seconds

### Empty State
- "No events found" message
- "Add an event to get started" suggestion
- Add button still functional

### Success State
- Data displayed normally
- All buttons enabled
- No error messages
- User can interact normally

## 📞 Support

For issues:
1. Check logcat for detailed error messages
2. Verify backend is running: `curl http://localhost:5000/api/health`
3. Check MySQL is running: `mysql -u root -p -e "SELECT 1"`
4. Verify internet connection
5. Review error messages in red boxes on screen

---

**Ready to test!** Start with backend, then run Android app. Enjoy! 🎉
