package com.example.look;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ChatbotController {

    @FXML
    private VBox chatBox;

    @FXML
    private TextField inputField;

    @FXML
    private Button sendButton;

    private String userName = "";
    private String userGender = "";
    private String userSeason = "";
    private String userClothingType = "";
    private boolean needsRecommendation = false;

    @FXML
    public void initialize() {
        addMessageToChat("Bienvenue ! Je suis votre assistante MatchyBot. Quel est votre nom ?", false);
    }

    @FXML
    public void sendMessage(ActionEvent event) {
        String userMessage = inputField.getText().trim();

        if (userName.isEmpty()) {
            userName = userMessage;
            addMessageToChat("Ravi de vous rencontrer, " + userName + " ! Avez-vous besoin de recommandations ?", false);
        } else if (userMessage.equalsIgnoreCase("oui") || userMessage.equalsIgnoreCase("non")) {
            needsRecommendation = userMessage.equalsIgnoreCase("oui");
            addMessageToChat("Quel est votre genre (homme, femme) ?", false);
        } else if (userGender.isEmpty()) {
            userGender = userMessage.toLowerCase();
            addMessageToChat("Quelle est la saison dans laquelle vous vous trouvez ?", false);
        } else if (userSeason.isEmpty()) {
            userSeason = userMessage.toLowerCase();
            addMessageToChat("Souhaitez-vous des recommandations pour un pull ou une chemise ?", false);
        } else if (userClothingType.isEmpty()) {
            userClothingType = userMessage.toLowerCase();
            addMessageToChat("Merci " + userName + ". Voici mes recommandations pour vous, en tant que " + userGender + " en saison " + userSeason + " pour un(e) " + userClothingType + " :", false);
            // Ajouter ici la logique pour donner des recommandations
            addMessageToChat("J'espère que ces recommandations vous ont été utiles. N'hésitez pas à me contacter si vous avez d'autres besoins.", false);
            needsRecommendation = false;
        } else {
            addMessageToChat(userMessage, true);
            String chatbotResponse = getChatbotResponse(userMessage);
            addMessageToChat(chatbotResponse, false);
        }
        inputField.clear();
    }

    private void addMessageToChat(String message, boolean isUserMessage) {
        Text messageText = new Text(message);
        messageText.getStyleClass().add(isUserMessage ? "user-message" : "chatbot-message");
        chatBox.getChildren().add(messageText);
    }

    private String getChatbotResponse(String userMessage) {
        if (userMessage.toLowerCase().contains("recommandation")) {
            needsRecommendation = true;
            return "Très bien, je peux vous donner des recommandations personnalisées. Pour cela, j'ai besoin de savoir votre genre (homme, femme, autre) ?";
        } else if (userMessage.toLowerCase().contains("contacte")) {
            return "Pas de problème, vous pouvez consulter notre page de contact pour nous joindre.";
        } else {
            return "Je ne suis pas sûre de comprendre. Pouvez-vous reformuler votre demande ?";
        }
    }
}