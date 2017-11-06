package com.hex.car.controller;

import com.hex.car.domain.Evaluate;
import com.hex.car.domain.ImgEvaluate;
import com.hex.car.domain.User;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.EvaluateService;
import com.hex.car.service.ImgEvaluateService;
import com.hex.car.service.ProductService;
import com.hex.car.utils.FileUtil;
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
import java.util.List;
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
    private ProductService productService;

    @Autowired
    private ImgEvaluateService imgEvaluateService;

    @Value("${web.upload-path}")
    private String path;

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
    @GetMapping(value = "/getEvaluateListByIdentity")
    private Object getEvaluateListByIdentity(HttpServletRequest request) {
        Object object = request.getSession().getAttribute("user");
        if (null == object) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        User user = (User) object;
        if (user.getId().equals("root")) {
            return ResultUtil.success(evaluateService.findAllEvaluateList());
        } else if (null != user.getShop()) {
            return ResultUtil.success(evaluateService.findEvaluatesByProductShop(user.getShop()));
        } else {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
    }

    /**
     * 保存修改文章
     *
     * @param evaluate  文章
     * @param productId 对应商品id
     * @param file      文章头图
     * @return
     */
    @PostMapping(value = "/saveEvaluate")
    private Object saveEvaluate(Evaluate evaluate,
                                String productId,
                                @RequestParam(value = "file") MultipartFile file) {
        // TODO 商品相关做好后需要恢复以下代码，页面需要添加快捷搜索功能
//        if (null == productId || productId.equals("")) {
//            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
//        }
//        Product product = productService.findProductById(productId);
//        if (null == product) {
//            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
//        }
//        evaluate.setProduct(product);

        if (file != null) {
            if (null == evaluate.getId() && !evaluate.getId().equals("")) {
                evaluate = evaluateService.findEvaluateById(evaluate.getId());
                File deleteFile = new File(path + evaluate.getImgEvaluate().getFileName());
                deleteFile.delete();
                imgEvaluateService.deleteImgEvaluate(evaluate.getImgEvaluate());
            }

            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            ImgEvaluate imgEvaluate;
            try {
                FileUtil.uploadFile(file.getBytes(), path, fileName);
                imgEvaluate = new ImgEvaluate();
                imgEvaluate.setFileName(fileName);
                evaluate.setImgEvaluate(imgEvaluate);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
            }
        }
        return ResultUtil.success(evaluateService.saveEvaluate(evaluate));
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
     * 获取4个最新文章集合（前台页面用）
     *
     * @return
     */
    @GetMapping("/front/get4EvaluateList")
    public Object get4EvaluateList() {
        List<Evaluate> evaluates = evaluateService.findTop4ByStateOrderByCreateTimeDesc(new Integer(2));
//        List<ImgEvaluate> imgEvaluates = new ArrayList<>();
//        List<ImgUser> imgUsers = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        for (int i = 0; i < evaluates.size(); i++) {
//            imgEvaluates.add(evaluates.get(i).getImgEvaluate());
//            imgUsers.add(evaluates.get(i).getProduct().getShop().getUser().getImgUser());
//        }
//        map.put("evaluates", evaluates);
//        map.put("imgs", imgEvaluates);
//        map.put("headImgs", imgUsers);
        return ResultUtil.success(evaluates);
    }

}
