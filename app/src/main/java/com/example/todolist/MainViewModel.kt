package com.example.todolist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = NoteDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    fun remove(note: Note) {
        val disposable = db.notesDao().remove(note.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Action {
                override fun run() {
                    Log.d("MainViewModel", "note ${note.id} was removed")
                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable) {
                    Log.d("MainViewModel", "ERROR REMOVE")
                }
            })
        compositeDisposable.add(disposable)
    }

    fun getNotes(): LiveData<List<Note>> {
        return db.notesDao().getNotes()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}