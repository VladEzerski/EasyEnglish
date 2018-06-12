package com.ezerski.vladislav.easyenglish.db;

public interface DataContract {

    interface WordsDbAttributes {
        String WORDS_COLLECTION_NAME = "WORDS";
        String FIELD_DATE = "date";
        String FIELD_ORIGINAL_WORD = "originalWord";
        String FIELD_TRANSLATED_WORD = "translatedWord";
        String FIELD_DOCUMENT_ID = "documentId";
        String FIELD_USER_NAME = "userName";
        String FIELD_USER_ID = "userId";
        String FIELD_USER_EMAIL = "userEmail";
    }

    interface UserDbAttributes {
        String USERS_COLLECTION_NAME = "USERS";
        String FIELD_EMAIL = "email";
        String FIELD_NAME = "name";
        String FIELD_IMAGE = "image";
        String FIELD_WORDS_COUNT = "wordsCount";
        String FIELD_TESTS_COUNT = "testsCount";
        String FIELD_ANSWERS_COUNT = "answersCount";
        String FIELD_CORRECT_ANSWERS_COUNT = "correctAnswersCount";
    }
}