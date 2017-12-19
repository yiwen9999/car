package com.hex.car.controller;

import com.alibaba.fastjson.JSONArray;
import com.hex.car.domain.Car;
import com.hex.car.domain.CarType;
import com.hex.car.domain.Parameter;
import com.hex.car.domain.Personnel;
import com.hex.car.service.*;
import com.hex.car.utils.ExcelUtil;
import com.hex.car.utils.ResultUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: hexuan
 * Date: 2017/10/30
 * Time: 上午10:36
 */
@RestController
public class ExcelController {

    @Autowired
    private CarService carService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private CarTypeService carTypeService;

    @Autowired
    private PersonnelService personnelService;

    @PostMapping(value = "/importCarExcel")
    public Object importCarExcel(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        Row row;
        Cell cell;
        Car car;
        List<Car> cars = new ArrayList<>();
        Map<String, Parameter> parameterMap = new HashMap<>();
        for (Parameter parameter : parameterService.findParametersByStateOrderBySort(new Integer(2))) {
            parameterMap.put(parameter.getName(), parameter);
        }
        Map<String, CarType> carTypeMap = new HashMap<>();
        for (CarType carType : carTypeService.findCarTypesByStateOrderBySort(new Integer(2))) {
            carTypeMap.put(carType.getName(), carType);
        }
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 跳过第一行标题行，所以i从1开始
            row = sheet.getRow(i);
            car = new Car();
            for (int j = 0; j <= row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (cell != null) {
                    String cellString = getCellValue(cell).trim();
                    switch (j) {
                        case 1:
                            car.setName(cellString);
                            break;
                        case 2:
                            if (!cellString.equals("")) {
                                car.setDisplacement(Double.parseDouble(cellString));
                            }
                            break;
                        case 3:
                            if (!cellString.trim().equals(""))
                                car.setYear(Integer.parseInt(cellString));
                            break;
                        case 4:
                            car.setBrand(brandService.findFirstByName(cellString));
                            break;
                        case 5:
                            car.setModel(modelService.findFirstByName(cellString));
                            break;
                        case 6:
                            car.setCarType(carTypeMap.get(cellString));
                            break;
                        case 7:
                            car.setEngineType(parameterMap.get(cellString));
                            break;
                        case 8:
                            car.setDrivetrain(parameterMap.get(cellString));
                            break;
                        case 9:
                            car.setTransmission(parameterMap.get(cellString));
                            break;
                        case 10:
                            car.setFuelType(parameterMap.get(cellString));
                            break;
                        case 11:
                            car.setBodyType(parameterMap.get(cellString));
                            break;
                        case 12:
                            car.setSeats(parameterMap.get(cellString));
                            break;
                    }
                }
            }
            cars.add(car);
        }
        carService.saveCarList(cars);
        return ResultUtil.success();
    }

    @GetMapping(value = "exportPersonnelExcel")
    public Object exportPersonnelExcel(HttpServletResponse response) throws IOException {
        List<Personnel> personnels = personnelService.findAllOrderByCreateTimeDesc();
        int num = 1;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String title = "会员列表";
        Map<String, String> headMap = new LinkedHashMap<>();
        headMap.put("number", "序号");
        headMap.put("username", "登录名");
        headMap.put("name", "姓名");
        headMap.put("nickname", "昵称");
        headMap.put("mobile", "手机号");
        headMap.put("createTime", "创建时间");
        headMap.put("state", "状态");
        JSONArray ja = new JSONArray();
        for (Personnel personnel : personnels) {
            String username = "";
            if (null != personnel.getUser()) {
                username = personnel.getUser().getUsername();
            }
            String state = "停用";
            if (2 == personnel.getState()) {
                state = "启用";
            }

            SimpleData simpleData = new SimpleData();
            simpleData.setNumber(num++);
            simpleData.setUsername(username);
            simpleData.setName(personnel.getName());
            simpleData.setNickname(personnel.getNickname());
            simpleData.setMobile(personnel.getMobile());
            simpleData.setCreateTime(simpleDateFormat.format(personnel.getCreateTime()));
            simpleData.setState(state);
            ja.add(simpleData);
        }
        ExcelUtil.downloadExcelFile(title, headMap, ja, response);
        return "success";
    }

    protected static String getCellValue(Cell cell) {
        String cellValue = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = sdf.format(cell.getDateCellValue());
                    } else {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String temp = cell.getStringCellValue();
                        // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                        if (temp.indexOf(".") > -1) {
                            cellValue = String.valueOf(new Double(temp)).trim();
                        } else {
                            cellValue = temp.trim();
                        }
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = cell.getCellFormula();
                    break;
                default:
                    cellValue = "";
            }
        }
        return cellValue;
    }
}

class SimpleData {
    private Integer number;
    private String username;
    private String name;
    private String nickname;
    private String mobile;
    private String createTime;
    private String state;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}