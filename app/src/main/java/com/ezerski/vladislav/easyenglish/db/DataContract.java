package com.ezerski.vladislav.easyenglish.db;

/**
 * Created by Vladislav on 27.05.2018.
 */

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
}
