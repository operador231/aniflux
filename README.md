# AniFlux

**AniFlux** is an Android client for anime tracking built with **Jetpack Compose**.

> 🚧 **Project Status:** Active development  
> The project is still experimental. APIs, features, and architecture may change.

---

## 🛠️ Tech Stack

- **UI:** Jetpack Compose
- **Architecture:** Clean Architecture + MVVM
- **Dependency Injection:** Hilt
- **Networking:** Retrofit, Apollo + KotlinX Serialization
- **Async:** Kotlin Coroutines & Flow
- **Pagination:** Paging 3
- **Caching:** Room
- **Image Loading:** Coil

## 🏗️ Project Structure

- `:app`
  - Application entry point
  - DI setup
  - Navigation host
- `:core`
  - `:domain`
    - Business models
    - Repository contracts
    - Use cases
    - Pure kotlin
  - `:network`
    - API services
    - DTO models
    - Serialization setup
  - `:navigation`
    - Navigation contracts
    - Cross-feature navigation abstractions
  - `:ui`
    - Design system
    - Common UI components
- `:feature`
  - `:catalog`
    - `:api`
      - Public feature contracts
    - `:impl`
      - UI screens
      - ViewModels
      - Feature-specific logic

---

## ⚖️ License
This project is open-source and licensed under the [GNU General Public License v3.0](LICENSE).