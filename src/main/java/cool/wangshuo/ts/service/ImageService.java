package cool.wangshuo.ts.service;

import cool.wangshuo.ts.util.FileTypeUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;

/**
 * @Classname ImageService
 * @Description TODO
 * @Date 2021/8/14 下午7:17
 * @Created by Noble
 */
@Service
public class ImageService {

    // 保存文件的根目录，当前用户的家目录下的  .typora-user-images
    public final String imageRootDir =  new StringBuilder(System.getProperty("user.home"))
            .append(File.separator)
            .append(".typora-user-images")
            .toString();

    // 当前图片计数器，每日清空，表明是每日的第几张图片
    private Integer counter = 0;

    // 当前的 day,初始值为系统开始运行的时间
    private Integer current_day = Calendar.getInstance().get(Calendar.DATE);

    // 当前的目录位置
    private String current_dir = "";


    public ImageService() {

        if (!new File(imageRootDir).exists()){
            new File(imageRootDir).mkdirs();
        }
    }

    /**
     * 上传图片
     * @param request
     * @return 返回生成的文件名
     * @throws IOException
     */
    public String uploadImage(HttpServletRequest request) throws IOException {

        String fileName = "";

        // 上传图片
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> names = multiRequest.getFileNames();
            while (names.hasNext()) {
                String iterator = names.next();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iterator);
                if (file != null) {
//                    String format =  FileTypeUtils.getFileTypeByMagicNumber(new FileInputStream(file.getBytes())); // 图片扩展名
                    String format = FileTypeUtils.getFileTypeByMagicNumber(new ByteArrayInputStream(file.getBytes()));
                    fileName = obtainImageFile(format);   // 生成可用的文件
                    File appFile = new File(current_dir, fileName);
                    file.transferTo(appFile);
                }
            }
        }
        return fileName;
    }

    /**
     * 展示上传的图片
     * @param fileName 文件名称 format : 2021-09-01-counter
     * @return
     */
    public File showImage(String fileName){
        String[] msg = fileName.split("-");

        String dirName = new StringBuilder(msg[0])
                .append("-").append(msg[1])
                .toString();

        String filePath = new StringBuilder(imageRootDir)
                .append(File.separator).append(dirName).append(File.separator).append(fileName)
                .toString();

        File file = new File(filePath);

        return file;
    }



    /**
     * 指定条件，生成一个可用的文件名
     * @return
     */
    public String obtainImageFile(String format){

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);


        // 2021-09
        String dir = new StringBuilder().append(year).append("-").append(month).toString();

        this.current_dir = new StringBuilder()
                .append(imageRootDir).append(File.separator).append(dir)
                .toString();


        // 特定的目录是否存在
        boolean flag =  false;
        for (File file : new File(imageRootDir).listFiles()){
            if (file.isDirectory() && file.getName() == dir){
                flag = true;
            }
        }
        // 目录不存在，就创建目录
        if (!flag){
            new File(new StringBuilder(imageRootDir).append(File.separator).append(dir).toString()).mkdir();
        }

        // 判断是否需要清空
        if(current_day == day){
            counter++;
        }else {
            counter = 0 ;
            current_day = day;
        }

        // 2021-09-01-counter.jpg
        String fileName = new StringBuilder()
                .append(dir).append("-").append(day).append("-").append(counter).append(".").append(format)
                .toString();

        String pathFile = new StringBuilder(imageRootDir)
                .append(File.separator).append(dir).append(File.separator).append(fileName)
                .toString();

        // 生成的文件如果已经存在
        if (new File(pathFile).exists()){
            this.obtainImageFile(format);
        }

        return fileName;
    }

}
