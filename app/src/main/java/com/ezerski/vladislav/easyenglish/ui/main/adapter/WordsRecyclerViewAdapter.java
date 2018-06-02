package com.ezerski.vladislav.easyenglish.ui.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezerski.vladislav.easyenglish.R;
import com.ezerski.vladislav.easyenglish.db.Words;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class WordsRecyclerViewAdapter extends RecyclerView.Adapter<WordsRecyclerViewAdapter.WordsViewHolder>{

    private List<Words> wordsList;

    public WordsRecyclerViewAdapter(List<Words> wordsList) {
        this.wordsList = wordsList;
    }

    @NonNull
    @Override
    public WordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_words_in_list, parent, false);
        return new WordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordsViewHolder holder, int position) {
        holder.setWords(wordsList.get(position));
    }

    public void updateWordsList(List<Words> wordsList){
        if (this.wordsList == null){
            this.wordsList = wordsList;
        } else {
            for (Words words : wordsList){
                if (!this.wordsList.contains(words)){
                    this.wordsList.add(words);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (this.wordsList != null) {
            this.wordsList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return wordsList == null ? 0 : wordsList.size();
    }

    static class WordsViewHolder extends RecyclerView.ViewHolder{
        private TextView tvOriginalWord;
        private TextView tvTranslatedWord;
        private TextView tvUploadDate;

        WordsViewHolder(View itemView) {
            super(itemView);
            tvOriginalWord = itemView.findViewById(R.id.tv_or_word);
            tvTranslatedWord = itemView.findViewById(R.id.tv_tr_word);
            tvUploadDate = itemView.findViewById(R.id.tv_upload_date);
        }

        private void setWords(Words words){
            tvOriginalWord.setText(words.getOriginalWord());
            tvTranslatedWord.setText(words.getTranslatedWord());
            SimpleDateFormat simpleDateFormat
                    = new SimpleDateFormat("d MMM HH:mm", Locale.getDefault());
            String dateString = simpleDateFormat.format(words.getDate());
            tvUploadDate.setText(dateString);
        }
    }
}
