package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface FilesMapper {
    @Select("SELECT * FROM files")
    List<File> findAll();

    @Select("SELECT * FROM files WHERE userid = #{userid}")
    List<File> findByUserId(Long userid);

    @Select("SELECT * FROM files WHERE fileid = #{id} AND userid = #{userid}")
    File findById(Long id, Long userid);

    @Select("SELECT * FROM files WHERE filename = #{fileName}")
    File findByNameAndUserId(String fileName);

    @Insert("INSERT INTO files (filename, contenttype, filesize, filedata, userid) VALUES (#{file.fileName}, #{file.contentType}, #{file.fileSize}, #{file.fileData}, #{userid})")
    Integer inset(@Param("file") File file, Long userid);

    @Update("UPDATE files SET filename = #{file.fileName}, contenttype = #{file.contentType}, filesize = #{file.fileSize}, filedata = #{file.fileData} WHERE fileid = #{file.fileId} AND userid = #{userid}")
    Integer update(@Param("file") File file, Long userid);

    @Delete("DELETE FROM files WHERE fileId = #{id}")
    Integer delete(Long id);

}
