package com.ezerski.vladislav.easyenglish.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.ezerski.vladislav.easyenglish.FirebaseModelParsingException;

import java.util.HashMap;
import java.util.Map;

public class User implements Parcelable {

    private String email;
    private String image;
    private String name;
    private long wordsCount;
    private long testsCount;
    private long answersCount;
    private double correctAnswersCount;

    public User(String email, String image, String name, long wordsCount, long testsCount,
                long answersCount, double correctAnswersCount) {
        this.email = email;
        this.image = image;
        this.name = name;
        this.wordsCount = wordsCount;
        this.testsCount = testsCount;
        this.answersCount = answersCount;
        this.correctAnswersCount = correctAnswersCount;
    }

    public User(Map<String, Object> userMap) throws FirebaseModelParsingException {
        if (userMap == null || userMap.size() != 4) {
            throw new FirebaseModelParsingException();
        }
        Object emailObject = userMap.get(DataContract.UserDbAttributes.FIELD_EMAIL);
        Object nameObject = userMap.get(DataContract.UserDbAttributes.FIELD_NAME);
        Object imageObject = userMap.get(DataContract.UserDbAttributes.FIELD_IMAGE);
        Object wordsCountObject = userMap.get(DataContract.UserDbAttributes.FIELD_WORDS_COUNT);
        Object testsCountObject = userMap.get(DataContract.UserDbAttributes.FIELD_TESTS_COUNT);
        Object answersCountObject = userMap.get(DataContract.UserDbAttributes.FIELD_ANSWERS_COUNT);
        Object correctAnswersCountObject = userMap.get(DataContract.UserDbAttributes.FIELD_CORRECT_ANSWERS_COUNT);
        if (!(emailObject instanceof String)
                || !(nameObject instanceof String)
                || !(imageObject instanceof String || imageObject == null)
                ) {
            throw new FirebaseModelParsingException();
        }
        this.name = (String) nameObject;
        this.email = (String) emailObject;
        this.image = (String) imageObject;
        this.wordsCount = (long) wordsCountObject;
        this.testsCount = (long) testsCountObject;
        this.answersCount = (long) answersCountObject;
        this.correctAnswersCount = (double) correctAnswersCountObject;
    }

    private User(Parcel in) {
        email = in.readString();
        image = in.readString();
        name = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(long wordsCount) {
        this.wordsCount = wordsCount;
    }

    public long getTestsCount() {
        return testsCount;
    }

    public void setTestsCount(long testsCount) {
        this.testsCount = testsCount;
    }

    public long getAnswersCount() {
        return answersCount;
    }

    public void setAnswersCount(long answersCount) {
        this.answersCount = answersCount;
    }

    public double getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public void setCorrectAnswersCount(double correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(DataContract.UserDbAttributes.FIELD_EMAIL, this.email);
        resultMap.put(DataContract.UserDbAttributes.FIELD_NAME, this.name);
        resultMap.put(DataContract.UserDbAttributes.FIELD_IMAGE, this.image);
        resultMap.put(DataContract.UserDbAttributes.FIELD_WORDS_COUNT, this.wordsCount);
        resultMap.put(DataContract.UserDbAttributes.FIELD_TESTS_COUNT, this.testsCount);
        resultMap.put(DataContract.UserDbAttributes.FIELD_ANSWERS_COUNT, this.answersCount);
        resultMap.put(DataContract.UserDbAttributes.FIELD_CORRECT_ANSWERS_COUNT, this.correctAnswersCount);
        return resultMap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeLong(wordsCount);
        parcel.writeLong(testsCount);
        parcel.writeLong(answersCount);
        parcel.writeDouble(correctAnswersCount);
    }

}