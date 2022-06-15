package cool.wangshuo.ts.controller;

import cool.wangshuo.ts.replayModel.CommonResult;
import cool.wangshuo.ts.service.ImageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * @Classname ImageController
 * @Description TODO
 * @Date 2021/8/14 上午8:44
 * @Created by Noble
 */

@RestController
@RequestMapping(value = "/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    private String key = "admin";

    /**
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/upload-image")
    public String uploadImage() throws IOException {

        CommonResult result = new CommonResult();
        result.setCode(200);
        String fileName = imageService.uploadImage(request);
        result.setData(fileName);

        return "http://image.wangshuo.cool/image/" + fileName + "\n";
    }


    @RequestMapping(value = "/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public void showImage(@PathVariable String fileName) throws Exception {
        response.setContentType("image/jpeg");

        File file = imageService.showImage(fileName);

        InputStream is = null;
        OutputStream os = null;
        try {
            response.addHeader("Content-Length", "" + file.length());
            is = new FileInputStream(file);
            os = response.getOutputStream();
            IOUtils.copy(is, os);
        } catch (Exception e) {
            System.out.println("下载图片发生异常" + e);
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                System.out.println("关闭流发生异常" + e);
            }
        }
    }

}
