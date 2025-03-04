# Interview Trainer

## Overview
Interview Trainer is a JavaFX application designed to help users practice for job interviews. The application allows users to select question categories, answer questions, and receive feedback on their responses.

## Features
- **Welcome Screen**: Users are greeted with a welcome message and can choose to start practicing or exit the application.
- **Question Categories**: Users can select from various categories of questions, such as Technical Questions and Skill Questions.
- **Answering Questions**: Users can answer questions in a modal dialog and receive feedback on their responses.
- **Progress Tracking**: The application tracks the number of questions answered and displays them in the questions view.
- **Farewell Screen**: Upon exiting, users are presented with a farewell message wishing them good luck in their job interviews.

## Project Structure
```
InterviewTrainer
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── hi
│   │   │   │   ├── verkefni
│   │   │   │   │   ├── vidmot
│   │   │   │   │   │   ├── InterviewApplication.java
│   │   │   │   │   │   ├── VelkominnController.java
│   │   │   │   │   │   ├── SpurningarController.java
│   │   │   │   │   │   ├── SvarDialogController.java
│   │   │   │   │   │   ├── KvedjaController.java
│   │   │   │   │   │   ├── View.java
│   │   │   │   │   │   └── ViewSwitcher.java
│   │   │   │   │   ├── vinnsla
│   │   │   │   │   │   ├── Spurningar.java
│   │   │   │   │   │   └── FeedbackService.java
│   │   ├── resources
│   │   │   ├── kvedja-view.fxml
│   │   │   ├── spurningar-view.fxml
│   │   │   ├── svar-view.fxml
│   │   │   ├── velkominn-view.fxml
│   │   │   └── interview.css
├── README.md
└── .gitignore
```

## Setup Instructions
1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Open the project in your preferred IDE.
4. Build the project to resolve dependencies.
5. Run the `InterviewApplication` class to start the application.

## Usage Guidelines
- Upon launching the application, follow the prompts to navigate through the different views.
- Select a question category and answer the questions presented.
- Review the feedback provided to improve your responses.
- Exit the application when finished, and receive a farewell message.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.