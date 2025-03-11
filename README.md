# Students Grade App

This project reads student data from a Google Sheets spreadsheet, calculates each student's situation based on their grades and absences, and writes the results back to the spreadsheet.

🔴 **Important:** You cannot run this application using the API provided here unless you configure your own Google Sheets API credentials.

## Requirements

- Java 17
- Google Sheets API credentials (credentials.json) placed in `src/main/resources`
- Maven
- IDE (e.g., IntelliJ IDEA or Eclipse)
- A Google Sheet configured with the following columns: Enrollment, Student, Absences, P1, P2, P3, Status, and Final Approval Grade.

## Setup Instructions

1. **Google Sheets API Setup**:
    - Create a project in Google Cloud Console.
    - Enable the Google Sheets API.
    - Create OAuth 2.0 credentials and download the `credentials.json` file.
    - Place `credentials.json` in the `src/main/resources` folder of the project.

2. **Clone the Repository**:
    ```bash
    git clone https://github.com/thalytadiniz/student-grades.git
    ```


3. **Install Dependencies and Build the Project**:
    ```bash
    mvn clean install
    ```

4. **Run the Application**:
    ```bash
    java -jar target/students-grade-app.jar
    ```

Please make sure you have your own API credentials set up before attempting to run the application. Without the correct credentials, the API will not function. ❗
