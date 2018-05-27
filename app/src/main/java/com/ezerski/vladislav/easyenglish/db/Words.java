package com.ezerski.vladislav.easyenglish.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.ezerski.vladislav.easyenglish.FirebaseModelParsingException;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Words implements Parcelable {

    private String originalWord;
    private String translatedWord;
    private Date date;
    private String documentId;
    private String userName;
    private String userId;
    private String userEmail;

    public Words(String originalWord, String translatedWord, Date date,
                 String userName, String userId, String userEmail) {
        this.originalWord = originalWord;
        this.translatedWord = translatedWord;
        this.date = date;
        this.userName = userName;
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public Words(Map<String, Object> wordsMap, String documentId) throws FirebaseModelParsingException {
        if (wordsMap == null || wordsMap.size() != 5) {
            throw new FirebaseModelParsingException();
        }
        Object dateObject = wordsMap.get(DataContract.WordsDbAttributes.FIELD_DATE);
        Object originalWordObject = wordsMap.get(DataContract.WordsDbAttributes.FIELD_ORIGINAL_WORD);
        Object translatedWordObject = wordsMap.get(DataContract.WordsDbAttributes.FIELD_TRANSLATED_WORD);
        Object userNameObject = wordsMap.get(DataContract.WordsDbAttributes.FIELD_USER_NAME);
        Object userIdObject = wordsMap.get(DataContract.WordsDbAttributes.FIELD_USER_ID);
        Object userEmailObject = wordsMap.get(DataContract.WordsDbAttributes.FIELD_USER_EMAIL);
        if (!(userIdObject instanceof String)
                || !(dateObject instanceof Date)
                || !(originalWordObject instanceof String)
                || !(translatedWordObject instanceof String)
                || !(userNameObject instanceof String)
                || !(userEmailObject instanceof String)) {
            throw new FirebaseModelParsingException();
        }
        this.userId = (String) userIdObject;
        this.date = (Date) dateObject;
        this.originalWord = (String) originalWordObject;
        this.translatedWord = (String) translatedWordObject;
        this.documentId = documentId;
        this.userName = (String) userNameObject;
        this.userEmail = (String) userEmailObject;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Words post = (Words) obj;
        return Objects.equals(userId, post.userId) &&
                Objects.equals(date, post.date) &&
                Objects.equals(originalWord, post.originalWord) &&
                Objects.equals(translatedWord, post.translatedWord) &&
                Objects.equals(documentId, post.documentId) &&
                Objects.equals(userName, post.userName) &&
                Objects.equals(userEmail, post.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, date, originalWord, translatedWord, documentId,
                userName, userEmail);
    }

    public static final Creator<Words> CREATOR = new Creator<Words>() {
        @Override
        public Words createFromParcel(Parcel in) {
            return new Words(in);
        }

        @Override
        public Words[] newArray(int size) {
            return new Words[size];
        }
    };

    private Words(Parcel in) {
        userId = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        originalWord = in.readString();
        translatedWord = in.readString();
        documentId = in.readString();
        userName = in.readString();
        userEmail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(originalWord);
        parcel.writeString(translatedWord);
        parcel.writeString(documentId);
        parcel.writeString(userName);
        parcel.writeString(userId);
        parcel.writeString(userEmail);
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public void setTranslatedWord(String translatedWord) {
        this.translatedWord = translatedWord;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
