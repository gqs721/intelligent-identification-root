package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.SysFile;

/**
 * Created by Mr.BH
 */
public interface SysFileService {

    /**
     * 保存文件
     * @param sysFile
     * @return
     */
    RestResult saveSysFile(SysFile sysFile);


    /**
     * 根据pid和dataType删除文件
     * @param filePid
     * @param dataType
     * @return
     */
    RestResult deleteFile(Integer filePid, String dataType);


    /**
     * 文件列表
     * @param filePid
     * @param dataType
     * @return
     */
    RestResult listFile(Integer filePid, String dataType);
}
