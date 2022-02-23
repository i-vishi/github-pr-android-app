# GitHub PR Android App
An android app to see closed pull requests og a public GitHub Repository


## Overview
The application contains list of all closed pull requests of this repo - [JetBrains(kotlin)](https://github.com/JetBrains/kotlin). 

A list of all closed PRs is shown. As the list is scrolled, more items are fetched and added to the list.

## Project Setup

### Clone and install

Clone this repository and import into Android Studio
```
git clone https://github.com/i-vishi/github-pr-android-app.git
```

### Configuration

- The default repository can be changed in file `GitHubApiService.kt` by changing `USER_NAME` and `REPO_NAME`:
```kotlin
private const val USER_NAME = "JetBrains"
private const val REPO_NAME = "kotlin"
```

## Built With
- Kotlin
- GitHub API
- Retrofit


---

<p align="center"> Made with :blue_heart: by <a href="https://github.com/i-vishi">Vishal Gaur</a></p>