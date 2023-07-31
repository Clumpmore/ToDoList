package com.example.todolist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val dbNotesDao = NoteDatabase.getInstance(application).notesDao()
    private val shouldCloseScreen = MutableLiveData<Boolean>()
    private val compositeDisposable = CompositeDisposable()

    fun getShouldCloseScreen(): LiveData<Boolean> {
        return shouldCloseScreen
    }

    fun saveNote(note: Note) {
        val disposable = dbNotesDao.add(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Action {
                override fun run() {
                    shouldCloseScreen.value = true
                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable) {
                    Log.d("AddNoteViewModel", "ERROR SAVE")
                }

            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}