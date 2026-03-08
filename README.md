# 📖 Sentence Mining API - Cambridge Dictionary Scraper

A REST API developed in **Java + Spring Boot** to automate the **Sentence Mining** process for language learners. The API receives a list of words, performs web scraping on the **Cambridge Dictionary**, and returns the definitions found.

Ideal for integration with study frontends and flashcard generation for **Anki**.

---

## ✨ Features

- **Real-Time Scraping:** Fetches word definitions directly from the online dictionary.
- **Batch Processing:** Ability to receive and process multiple sentences/words in a single request.
- **Block Bypass:** Uses custom HTTP headers (**User-Agent**) to avoid common bot blocking.
- **Configured CORS:** Ready to be consumed by web applications (**React / Next.js**).

---

## 🛠️ Technologies Used

- **Language:** Java (17 or higher)
- **Framework:** Spring Boot (Web)
- **Web Scraping:** Jsoup (HTML extraction and parsing)
- **Serialization:** Jackson (JSON)
- **Build Tool:** Maven

---

## 🏗️ Project Architecture

The project follows the principles of **separation of responsibilities (Clean Code)**, divided into the following main layers:

- **Controller:** Receives HTTP requests from the frontend.
- **Service:** Orchestrates the business logic, iterates over the words, and handles business errors.
- **Scraper / Client:** Responsible exclusively for making the external request to the Cambridge website and parsing the HTML using CSS selectors.

---

## 🚀 How to Run the Project

### Prerequisites

- Java **JDK 17+** installed.
- **Maven** installed.

### Step by Step

Clone the repository:

```bash
git clone https://github.com/seu-usuario/sentence-mining-api.git
cd sentence-mining-api
```

Download dependencies and start the Spring Boot server:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

## 📚 API Documentation
### POST /sentence

Processes a list of sentences and searches the Cambridge Dictionary for definitions of the target word in each sentence.

### Required Headers
- `Content-Type: application/json`

### Example request body
```json
[
  {
    "fullSentence": "The software engineer wrote a clean code.",
    "wordToMine": "engineer"
  },
  {
    "fullSentence": "She squeezed a lemon into her tea.",
    "wordToMine": "lemon"
  }
]
```

Example Success Response (Status 200 OK)
```json
[
  {
    "fullSentence": "The software engineer wrote a clean code.",
    "wordToMine": "engineer",
    "definitions": [
      "a person whose job is to design or build machines, engines, or structures",
      "a person who designs or builds software applications"
    ]
  },
  {
    "fullSentence": "She squeezed a lemon into her tea.",
    "wordToMine": "lemon",
    "definitions": [
      "a yellow citrus fruit with a sour taste",
      "a pale yellow color"
    ]
  }
]
```
### Word Not Found Behavior

If the word is not found (or the dictionary returns 404), the definition field will be returned as null or an empty array, allowing the frontend to handle it gracefully.

## 🔮 Future Improvements (Roadmap)

-[ ] **Export to Anki**: generate a file compatible with Anki for easy import of flashcards.


-[ ] **Implement Cache (Redis/Caffeine):** Store previously mined words in a database to avoid repeated requests to Cambridge (performance improvement).


-[ ] **Audio Generate:** Generate audio pronunciation for the sentences using Text-to-Speech (TTS) services, enhancing flashcards with auditory learning.


-[ ] **Grammatical Category Extraction:** Identify whether the word is a noun, verb, or adjective. Also identify if the word is A1, A2, etc., to help learners understand the difficulty level of the word.


-[ ] **Example Sentence Extraction:** Retrieve a native example sentence from the dictionary to enrich flashcards.
