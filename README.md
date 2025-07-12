# ⏱️ Minimal Timer App

A beautifully minimal and responsive timer app built in **Kotlin** for Android. Features a retro-style display with a real-time foreground **dynamic notification** that shows the timer even when the app is closed.

![Timer UI](./preview.png)

---

## 🚀 Features

- Start, pause, and reset the timer with clean, circular buttons
- Millisecond-precision timer
- Retro pixel font for a nostalgic look
- Foreground **notification service** that:
  - Displays the live timer
  - Continues even when app is closed
  - Reflects pause/reset states in real time
- Custom UI with purple-themed aesthetic

---

## 🧠 Tech Stack

- **Kotlin**
- **Android SDK**
- **Handler & Runnable** for timekeeping
- **Foreground Service** for persistent notifications
- **NotificationChannel** (Android O+)
- **Custom XML layout** for styling and animation

---

## 🧩 How it works

### Timer Logic:
- Uses `SystemClock.elapsedRealtime()` to measure time accurately
- Ticks every **10ms** for smooth hundredths display
- Stops/resumes based on pause/play buttons

### Notification Sync:
- `TimerService` runs in the background
- Updates notification text via a handler every 10ms
- Reacts to `START`, `PAUSE`, and `RESET` intents from the app

---

## 🖱️ Controls

| Button  | Function |
|---------|----------|
| ◯ (Start)  | Starts the timer and notification |
| ⏸️ (Pause)  | Pauses both the timer and the notification |
| ❌ (Reset)  | Resets time to 00:00:00 everywhere |

---

## 📷 Preview

> <img width="322" height="567" alt="image" src="https://github.com/user-attachments/assets/952ccae0-c0cf-4992-9204-343a0fa48d66" />


---

## 🛠️ Setup Instructions

1. Clone the repo or download the project
2. Open in Android Studio
3. Build & run the app on a physical/emulated device (Android 8.0+)
4. Grant **notification permissions** if prompted

---

## 📦 Dependencies

- [AndroidX Core & AppCompat](https://developer.android.com/jetpack/androidx)
- `NotificationCompat` for backwards-compatible notifications
- Custom font and vector assets from `res/`

---

## ✅ TODO / Future Plans

- [ ] Add a flowing side menu (optional)
- [ ] Export timer sessions to file or share
- [ ] Dark mode support

---

## 📄 License

This app is a personal learning project. Feel free to fork and modify it. Attribution appreciated!

---

> Designed & developed with 💜 by Aoun Raza
