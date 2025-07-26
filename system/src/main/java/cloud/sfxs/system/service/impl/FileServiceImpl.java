package cloud.sfxs.system.service.impl;

import cloud.sfxs.system.mapper.FileMapper;
import cloud.sfxs.system.model.File;
import cloud.sfxs.system.service.FileService;
import cloud.sfxs.system.utils.AliyunOssUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 附件 服务实现类
 *
 * @author mazhaoyang
 * @since 2024/1/17
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Override
    public ResponseVo upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        String url = AliyunOssUtils.uploadFile(inputStream, originalFilename, false);

        File saveFile = new File();
        String type = file.getOriginalFilename().split("\\.")[1];
        long size = file.getSize();
        String fileID = UUID.randomUUID().toString();
        saveFile.setFileId(fileID);
        saveFile.setFileType(type);
        saveFile.setFileSize(String.valueOf(size));
        saveFile.setFileLocation(url);
        save(saveFile);

        if(url == null){
            return ResponseVo.error("图片上传失败");
        }else{
            return ResponseVo.success(url);
        }
    }
}