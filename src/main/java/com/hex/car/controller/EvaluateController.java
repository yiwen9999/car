package com.hex.car.controller;

import com.hex.car.domain.Evaluate;
import com.hex.car.domain.ImgEvaluate;
import com.hex.car.domain.User;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.EvaluateService;
import com.hex.car.service.ImgEvaluateService;
import com.hex.car.service.ProductService;
import com.hex.car.utils.FileUtil;
import com.hex.car.utils.HexUtil;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * User: hexuan
 * Date: 2017/10/24
 * Time: 下午4:15
 */
@RestController
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;

    @Autowired
    private ImgEvaluateService imgEvaluateService;

    @Autowired
    private ProductService productService;

    @Value("${web.upload-path}")
    private String path;

    @Value("${web.zip-file-limit}")
    private Long zipFileLimit;

    /**
     * 获取全部文章集合
     *
     * @return
     */
    @GetMapping(value = "/getAllEvaluateList")
    private Object getAllEvaluateList() {
        return ResultUtil.success(evaluateService.findAllEvaluateList());
    }

    /**
     * 根据身份获取对应文章集合（管理员查看全部，4s店获取本店文章）
     *
     * @param request request获取登录账号
     * @return
     */
    @GetMapping(value = "/searchEvaluateListByIdentity")
    private Object searchEvaluateListByIdentity(@RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "1000") Integer size,
                                                @RequestParam(defaultValue = "createTime") String sortStr,
                                                @RequestParam(defaultValue = "desc") String asc,
                                                HttpServletRequest request) {
        Object object = request.getSession().getAttribute("user");
        if (null == object) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        User user = (User) object;
        Map<String, Object> condition = new HashMap<>();
        if (user.getId().equals("root")) {
            return ResultUtil.success(evaluateService.findEvaluates(condition, HexUtil.getPageRequest(page, size, sortStr, asc)));
        } else if (null != user.getShop()) {
            // shopId作为条件时，会查询与该店所售车辆配对的文章，以及创建人为该店的文章
            condition.put("shopId", user.getShop().getId());
            return ResultUtil.success(evaluateService.findEvaluates(condition, HexUtil.getPageRequest(page, size, sortStr, asc)));
        } else {
            condition.put("creatorId", user.getId());
            return ResultUtil.success(evaluateService.findEvaluates(condition, HexUtil.getPageRequest(page, size, sortStr, asc)));
        }
    }

    /**
     * 保存修改文章
     *
     * @param evaluate 文章
     * @param file     文章头图
     * @return
     */
    @PostMapping(value = "/saveEvaluate")
    private Object saveEvaluate(Evaluate evaluate,
                                @RequestParam(value = "file", required = false) MultipartFile file,
                                @RequestParam(value = "authorFile", required = false) MultipartFile authorFile,
                                HttpServletRequest request) {
        Evaluate saveEvaluate = new Evaluate();
        if (!HexUtil.validateString(evaluate.getTitle())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "文章标题" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateString(evaluate.getIntro())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "文章简介" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateString(evaluate.getContent())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "文章内容" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (null != evaluate.getId() && !evaluate.getId().equals("")) {
            saveEvaluate = evaluateService.findEvaluateById(evaluate.getId());
            if (null != file && null != saveEvaluate.getImgEvaluate()) {
                File deleteFile = new File(path + saveEvaluate.getImgEvaluate().getFileName());
                if (deleteFile.exists())
                    deleteFile.delete();
                imgEvaluateService.deleteImgEvaluate(saveEvaluate.getImgEvaluate());
            }
            /**
             * 已存在作者头像，且头像不是默认头像的，删除旧头像文件
             */
            if (null != authorFile && null != saveEvaluate.getImgAuthor() && !"".equals(saveEvaluate.getImgAuthor()) && saveEvaluate.getImgAuthor().indexOf("default") < 0) {
                File deleteFile = new File(path + saveEvaluate.getImgAuthor());
                if (deleteFile.exists())
                    deleteFile.delete();
            }
        } else {
            if (null == file) {
                return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "文章图片" + ResultEnum.ERROR_NULLPARAM.getMsg());
            }
        }

        saveEvaluate.setTitle(evaluate.getTitle());
        saveEvaluate.setIntro(evaluate.getIntro());
        saveEvaluate.setContent(evaluate.getContent());
        saveEvaluate.setCreator(HexUtil.getUser(request));

        if (null != file) {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            ImgEvaluate imgEvaluate;
            try {
                FileUtil.uploadImgFile(file, path, fileName, zipFileLimit);
                imgEvaluate = new ImgEvaluate();
                imgEvaluate.setFileName(fileName);
                saveEvaluate.setImgEvaluate(imgEvaluate);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
            }
        }

        if (null != authorFile) {
            String fileName = authorFile.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            try {
                FileUtil.uploadImgFile(authorFile, path, fileName, zipFileLimit);
                saveEvaluate.setImgAuthor(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
            }
        }

        return ResultUtil.success(evaluateService.saveEvaluate(saveEvaluate));
    }

    /**
     * 停启用文章
     *
     * @param id 文章id
     * @return
     */
    @PostMapping(value = "/updateEvaluateState")
    public Object updateEvaluateState(String id) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Evaluate evaluate = evaluateService.findEvaluateById(id);
        if (null == evaluate) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (evaluate.getState() == 2) {
            evaluate.setState(new Integer(-1));
        } else {
            evaluate.setState(new Integer(2));
        }
        return ResultUtil.success(evaluateService.saveEvaluate(evaluate));
    }

    /**
     * 删除文章
     *
     * @param id 文章id
     * @return
     */
    @PostMapping(value = "/deleteEvaluate")
    public Object deleteEvaluate(String id) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Evaluate evaluate = evaluateService.findEvaluateById(id);
        if (null == evaluate) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        File deleteFile = new File(path + evaluate.getImgEvaluate().getFileName());
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
        deleteFile = new File(path + evaluate.getImgAuthor());
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
        evaluateService.deleteEvaluate(evaluate);
        return ResultUtil.success();
    }

    /**
     * 获取文章信息
     *
     * @param id 文章id
     * @return
     */
    @PostMapping(value = "/getEvaluateInfo")
    public Object getEvaluateInfo(String id) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Evaluate evaluate = evaluateService.findEvaluateById(id);
        if (null == evaluate) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(evaluate);
    }

    /**
     * 文章与商品配对
     *
     * @param id         文章id
     * @param productIds 商品id集合
     * @return
     */
    @PostMapping(value = "/evaluateChooseProducts")
    public Object evaluateChooseProducts(String id, String[] productIds) {
        if (null == id || "".equals(id)) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Evaluate evaluate = evaluateService.findEvaluateById(id);
        if (null == evaluate) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        evaluate.setProducts(productService.findProductsByIdIn(productIds));
        return ResultUtil.success(evaluateService.saveEvaluate(evaluate));
    }

//    @GetMapping(value = "/getProductsByEvaluate")
//    public Object getProductsByEvaluate(String id){
//        if (null == id || "".equals(id)) {
//            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
//        }
//        Evaluate evaluate = evaluateService.findEvaluateById(id);
//        if (null == evaluate) {
//            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
//        }
//        return ResultUtil.success(evaluate.getProducts());
//    }
}
