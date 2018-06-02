package com.ezerski.vladislav.easyenglish.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ezerski.vladislav.easyenglish.R;
import com.ezerski.vladislav.easyenglish.db.DataContract;
import com.ezerski.vladislav.easyenglish.db.Words;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private EditText etOriginalWord;
    private EditText etTranslatedWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etOriginalWord = findViewById(R.id.et_original_word);
        etTranslatedWord = findViewById(R.id.et_translated_word);
        Button btnCreateWords = findViewById(R.id.btn_create_words);
        btnCreateWords.setOnClickListener(this);
    }

    private void createWords(){
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null && !currentUser.isAnonymous()){
            db.collection(DataContract.WordsDbAttributes.WORDS_COLLECTION_NAME)
                    .add(new Words(etOriginalWord.getText().toString(),
                            etTranslatedWord.getText().toString(),
                            new Date(),
                            "vlad",
                            currentUser.getUid(),
                            currentUser.getEmail()))
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                task.getResult().
                                        update(DataContract.WordsDbAttributes.FIELD_DOCUMENT_ID,
                                                task.getResult().getId());
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        createWords();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_post_creation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
