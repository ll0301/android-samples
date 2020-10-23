package com.example.androidsamples.architectureCopmonent.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * @Entity() -> SQLite table / specify the name of the table
 * @PrimaryKey -> Every entity needs a primary key
 * @ColumnInfo -> specify the name of column and set type to String
 *
 * 데이터베이스에 저장된 모든 속성은 public 이며 kotlin 의 기본 설정이다.
**/
@Entity(tableName = "word_table")
data class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)