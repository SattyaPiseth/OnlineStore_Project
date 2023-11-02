package co.devkh.onlinestore.reviewonlinestore.api.file;

import co.devkh.onlinestore.reviewonlinestore.api.file.web.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    /**
     * This method is used to download file from server by name
     * @param name of file
     * @return Resource
     */
    Resource downloadByName(String name);
    /**
     * This method is used to delete all files in server
     */
    void deleteAll();

    /**
     * This method is used to delete file from server by name
     * @param name of file
     */
    void deleteByName(String name);

    /**
     * This method is used to retrieve resource meta-data file from server
     * @return List<FileDto>
     */
    List<FileDto> findAll();

    /**
     * This method is used to retrieve resource(meta-data) files from server
     * @param name of file
     * @return FileDto
     */
    FileDto findByName(String name);

    /**
     * This method is used to upload a single file to server
     * @param file is the request file from client to upload
     * @return FileDto
     */
    FileDto uploadSingle(MultipartFile file);

    /**
     * This method is used to upload a multiple files to server at one time
     * @param files is the request file from client to uploads
     * @return List<FileDto>
     */
    List<FileDto> uploadMultiple(List<MultipartFile> files);
}
