package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NotesMapper {
    @Select("SELECT * FROM NOTES")
    List<Note> findAll();

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    public List<Note> findByUserId(long userid);

    @Insert("INSERT INTO notes (notetitle, notedescription, userid) VALUES (#{note.noteTitle}, #{note.noteDescription}, #{userid})")
    public int insertNote(@Param("note") Note note, long userid);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid}")
    public int deleteNote(int noteid, long userid);

    @Update("UPDATE notes SET notetitle = #{note.noteTitle}, notedescription = #{note.noteDescription} WHERE noteid = #{note.noteId} AND userid = #{userid}")
    public int updateNote(@Param("note") Note note, long userid);

    @Select("SELECT * FROM notes WHERE noteid = #{id}")
    Note findById(int id);

}