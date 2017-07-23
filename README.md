Android application to automatically aggregate financial data, with Chatbot interface
-Developed at Barclays Open Minds hackathon

Features included:
*Keyword based SMS data extraction
*ChatBot
*Material UI

The application enables automatic capture of all financial data by mining SMS.
The application takes Card number to track; tracking expenditure per card.

The IncomingSMS.java file is a SMS Broadcast receiver that captures the SMS and runs a key-word based data extractor.

The current keyword extractor handles limited key-words. 
The scoope of the key-word extraction can be expanded by adding more keys to the 'keys' array in IncomingSMS class.

To add additional atributes to be captured through the SMS, edit the ChatMessage and ChatAdapter classes (And make corresponding changes in Database handler) 

SQLite acts as interface between SMS captured and Chatbot.
ChatActivity handles the Chatbot UI. The chatbot requests are handled by key-word extraction.


