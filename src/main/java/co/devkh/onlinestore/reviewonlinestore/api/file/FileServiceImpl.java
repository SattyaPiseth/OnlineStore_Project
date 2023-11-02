package co.devkh.onlinestore.reviewonlinestore.api.file;

import co.devkh.onlinestore.reviewonlinestore.api.file.web.FileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService{
    @Value("${file.server-path}")
    private String serverPath;
    @Value("${file.base-uri}")
    private String fileBaseUri;
    @Value("${file.download-uri}")
    private String fileDownloadUri;

    @Override
    public Resource downloadByName(String name) {
        Path path = Paths.get(serverPath+name);
        if (Files.exists(path)){
            return UrlResource.from(path.toUri());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found!");
    }

    @Override
    public void deleteAll() {
        Path path = Paths.get(serverPath);
        try {
                Stream<Path> pathStream = Files.list(path);
                List<Path> pathList = pathStream.toList();
              //  System.out.println(pathList);
                for (Path p : pathList){
                 //   System.out.println(p);
                    Files.delete(p);
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByName(String name) {
        Path path = Paths.get(serverPath+name);
        try {
            if (Files.deleteIfExists(path)){
                return;
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FileDto> findAll() {
        Path path = Paths.get(serverPath);
        List<FileDto> fileDtoList = new ArrayList<>();
        try {
                Stream<Path> pathStream = Files.list(path);
                List<Path> pathList = pathStream.toList();

                for (Path p : pathList){
/*
                    System.out.println(p.getFileName());
                    System.out.println(fileBaseUri+p.toFile().getName());
                    System.out.println(p.toFile().length());
                    System.out.println(getExtension(p.toFile().getName()));
*/
        // Method 1 : Use get Meta-data without Resource resource = UrlResource.from(serverPath);
        /*            fileDtoList.add(FileDto.builder()
                            .name(p.toFile().getName())
                            .uri(fileBaseUri+p.toFile().getName())
                            .size(p.toFile().length())
                            .extension(getExtension(p.toFile().getName()))
                            .build());
         */
        //------------------------------------------------------------------------------------------
                    Resource resource = UrlResource.from(p.toUri());
                    fileDtoList.add(FileDto.builder()
                                    .name(resource.getFilename())
                                    .uri(fileBaseUri+resource.getFilename())
                                    .downloadUri(fileDownloadUri+resource.getFilename())
                                    .size(resource.contentLength())
                                    .extension(getExtension(resource.getFilename()))
                                    .build());
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileDtoList;
    }

    @Override
    public FileDto findByName(String name) {
        Path path = Paths.get(serverPath+name);
        if (Files.exists(path)){
            // Start loading information about files
           return FileDto.builder()
                   .name(name)
                   .uri(fileBaseUri+name)
                   .downloadUri(fileDownloadUri+path.toFile().getName())
                   .size(path.toFile().length())
                   .extension(getExtension(name))
                   .build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found!");
    }

    @Override
    public FileDto uploadSingle(MultipartFile file) {
        return this.save(file);
    }
    private String getExtension(String fileName){
        // Get file extension
        int lastDotIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastDotIndex+1);
    }
    private FileDto save(MultipartFile file){

        String extension = this.getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        String name = UUID.randomUUID()+"."+extension;
        String uri = fileBaseUri + name;
        Long size = file.getSize();

        // Create file path (absolute path)
        Path path = Paths.get(serverPath+name);

        // Copy path
        try {
            Files.copy(file.getInputStream(),path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FileDto.builder()
                .name(name)
                .uri(uri)
                .downloadUri(fileDownloadUri+name)
                .size(size)
                .extension(extension)
                .build();
    }

    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {
       return files.stream().map(this::save)
                      .collect(Collectors.toList());
    }
}
