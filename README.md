![](screenshots/Screenshot_20200621-223615.png?raw=true "Title")

[![license](https://img.shields.io/github/license/DAVFoundation/captain-n3m0.svg?style=for-the-badge)](https://github.com/gerin98/NewsCast/blob/master/LICENSE) [![download](https://img.shields.io/badge/NewsCast-apk-brightgreen?style=for-the-badge&logo=android)](https://github.com/gerin98/NewsCast/releases)


## A modern News App for Android written in Kotlin
NewsCast lets you find and and read articles you care about natively within the app. Browse from a wide assortment of categories and save interesting articles for later!

<p align="center">
  <img src="screenshots/newscast_browse_view.jpg" width="200" height="400" />
  <img src="screenshots/newscast_news_paper_view.jpg" width="200" height="400" />
  <img src="screenshots/newscast_search_view.jpg" width="200" height="400" />
  <img src="screenshots/newscast_favourites_view.jpg" width="200" height="400" />
</p>

### Table of Contents
**[1. Features](#features)**<br>
**[2. Tech Stack](#tech-stack)**<br>
**[3. Api](#api)**<br>
**[4. Troubleshooting](#troubleshooting)**<br>
**[5. See it in Action](#see-it-in-action)**<br>
**[6. Credits](#credits)**<br>
**[7. Author](#author)**<br>
**[8. License](#license)**<br>

### Features :bulb:
#### Beatiful News Viewer
Read your news articles directly within the app. No redirects. No switching back and forth. Just you and your news.

#### Browse News from a variety of curated categories
Simply choose a news topic from the sidebar to browse the latest articles

#### Save important news for later
We don't always have time to read through the whole article. Double tap to add an article to your favourites and come back to it later.

#### Search at your fingertips
Can't find what you're looking for? Search for keywords to find articles for practically any topic

## Tech Stack :hammer_and_wrench:

- Language
  - [Kotlin](https://kotlinlang.org/)
- Architecture
  - [MVVM & Repository pattern](https://developer.android.com/jetpack/docs/guide#overview)
  - [Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html)
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
- Dependency Injection
  - [Koin](https://insert-koin.io/)
- Network
  - [Retrofit](https://square.github.io/retrofit/)
  - [OkHttp](https://square.github.io/okhttp/)
  - [Moshi](https://github.com/square/moshi)
- Database
  - [Room](https://developer.android.com/training/data-storage/room)
- UI
  - [Databinding](https://developer.android.com/topic/libraries/data-binding)
  - [Material Design](https://material.io/design)
  - [Icons](https://material.io/resources/icons/?style=baseline)
- Image loading
  - [Glide](https://github.com/bumptech/glide)
- Logging
  - [Timber](https://github.com/JakeWharton/timber)
- Testing
  - [Robolectric](https://github.com/robolectric/robolectric)
  - [Stetho](http://facebook.github.io/stetho/)

### Api :newspaper:
NewsCast is powered by [Event Registry's](http://eventregistry.org/) api to search and provide relevant news articles.

### Troubleshooting
If you are unable to load news articles, the token limit for my api key may have been reached. Please make an account with [Event Registry](http://eventregistry.org/) and use your own api key.

### See it in Action

<p align="center">
  <img src="screenshots/newscast_browse_video.gif" width="300" />
  <img src="screenshots/newscast_favourite_video.gif" width="300" />
</p>

### Credits
Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>

Inspired by [Fortnightly](https://material.io/design/material-studies/fortnightly.html)

### Author

<img src="https://github.com/gerin98.png" width="60" align="left">

**Gerin Amalaraj**

[![Linkedin](https://img.shields.io/badge/-linkedin-grey?logo=linkedin)](https://www.linkedin.com/in/gerin-amalaraj/)

### License
```
MIT License

Copyright (c) 2020 Gerin Amalaraj

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
